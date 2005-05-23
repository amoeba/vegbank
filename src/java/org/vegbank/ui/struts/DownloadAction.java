package org.vegbank.ui.struts;

import java.io.*;
import java.util.*;
import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.DynaActionForm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vegbank.common.model.Observation;
import org.vegbank.common.utility.Utility;
import org.vegbank.common.utility.DateUtility;
import org.vegbank.common.utility.ServletUtility;
import org.vegbank.common.utility.XMLUtil;
import org.vegbank.plots.datasink.ASCIIReportsHelper;
import org.vegbank.plots.datasource.DBModelBeanReader;
import org.vegbank.xmlresource.transformXML;

import com.Ostermiller.util.LineEnds;

/*
 * '$RCSfile: DownloadAction.java,v $'
 *	Authors: @author@
 *	Release: @release@
 *
 *	'$Author: anderson $'
 *	'$Date: 2005-05-23 07:50:52 $'
 *	'$Revision: 1.2 $'
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 */
/**
 * @author farrell
 *
 * Struts action to download file(s) based on id.
 */
public class DownloadAction extends Action
{
	private static Log log = LogFactory.getLog(DownloadAction.class);
	private static ResourceBundle sqlStore = 
			ResourceBundle.getBundle("org.vegbank.common.SQLStore");

	// Supported FormatTypes
	private static final String XML_FORMAT_TYPE = "xml";
	private static final String FLAT_FORMAT_TYPE = "flat";
	private static final String VEGBRANCH_FORMAT_TYPE = "vegbranch";
	
	// Supported dataTypes
	private static final String ALL_DATA_TYPE = "all";
	private static final String SPECIES_DATA_TYPE = "species";
	private static final String ENVIRONMENTAL_DATA_TYPE = "environmental";
	
	// Response content Types
	private static final String ZIP_CONTENT_TYPE = "application/x-zip";
	private static final String DOWNLOAD_CONTENT_TYPE = "application/x-zip";
	public static final String EXPORT_CHARSET = "UTF-16";
	private static final String VEGBRANCH_CONTENT_TYPE = "text/text; charset=" + EXPORT_CHARSET;
	private static final String VEGBRANCH_ZIP_CONTENT_TYPE = "application/x-zip; charset=" + EXPORT_CHARSET;

	// Resource paths
	private static ResourceBundle res = ResourceBundle.getBundle("vegbank");
	private static final String VEGBRANCH_XSL_PATH = res.getString("vegbank2vegbranch_xsl");

	
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
	{
		log.debug(" In DownloadAction ");
		ActionErrors errors = new ActionErrors();
		String fwd = null; // Do not go forward if successfull, download file
		
		// Get the form
		DynaActionForm thisForm = (DynaActionForm) form;
		
		Long dsId = (Long)thisForm.get("dsId");
		String formatType = (String)thisForm.get("formatType");
		String dataType = (String)thisForm.get("dataType");
		String[] selectedPlots = (String[])thisForm.get("selectedPlots");
		
		log.debug("dsId = " + dsId + ", formatType = " + formatType);
		
		try
		{
			// Handle the format type
			if ( formatType.equalsIgnoreCase( XML_FORMAT_TYPE) )
			{
				// Store the returned ModelBean Trees
				Collection plotObservations = this.getPlotObservations(selectedPlots);
		
				// wrap in XML
				String xml = XMLUtil.getVBXML(plotObservations);
				
				// Place into file for download
				//this.initResponseForFileDownload( response, "vegbank_plots.xml", DOWNLOAD_CONTENT_TYPE);
				//this.sendFileToBrowser(xml, response);

				/////////////////
				// ZIP the XML doc
				/////////////////
				this.initResponseForFileDownload(response, "vegbank_plots_xml.zip", ZIP_CONTENT_TYPE);
				
				Hashtable nameContent = new Hashtable();
				nameContent.put("vb_plot_observation.xml", xml);
				OutputStream responseOutputStream = response.getOutputStream();
				responseOutputStream.flush();
				
				// TODO: Get the OS of user if possible and return a native file	
				// For now use DOS style, cause those idiots would freak with anything else ;)					
				ServletUtility.zipFiles( nameContent, responseOutputStream, LineEnds.STYLE_DOS );
				/////////////////
			} else if ( formatType.equalsIgnoreCase( FLAT_FORMAT_TYPE ) ) {

                // exec psql queries to create files
                // zip files into one
                // redir to that file
                String taxaSQL = sqlStore.getString("csv_taxonimportance");
                String envSQL = sqlStore.getString("csv_plotandobservation");
                String where = sqlStore.getString("where_inuserdataset_pk_obs");
                String[] sqlParams = new String[1];
                sqlParams[0] = dsId.toString();
                Hashtable nameContent = new Hashtable();

                try {

                    // TAXA
                    nameContent.put("plot_taxa.csv", getCSVResults(taxaSQL, where, sqlParams));

                    // ENV
                    nameContent.put("plot_env.csv", getCSVResults(envSQL, where, sqlParams));


                    this.initResponseForFileDownload(response, "vegbank_export_csv.zip", ZIP_CONTENT_TYPE);
                    OutputStream responseOutputStream = response.getOutputStream();
                    responseOutputStream.flush();
                    
                    ServletUtility.zipFiles(nameContent, responseOutputStream, LineEnds.STYLE_DOS);

                } catch (SecurityException sex) {
                    log.error("security exception while executing psql", sex);
                } catch (IOException ioe) {
                    log.error("IO exception while executing psql", ioe);
                } catch (NullPointerException npe) {
                    log.error("null pointer exception while executing psql", npe);
                } catch (IllegalArgumentException iae) {
                    log.error("illegal argument exception while executing psql", iae);
                }

                
                /*
                String environmentalData = null;
                String speciesData = null;
                Collection plotObservations = this.getPlotObservations(selectedPlots);
                if (dataType.equalsIgnoreCase(ENVIRONMENTAL_DATA_TYPE)
                        || dataType.equalsIgnoreCase(ALL_DATA_TYPE)) { 
                    environmentalData = ASCIIReportsHelper.getEnvironmentalData(plotObservations);
                }
                if (dataType.equalsIgnoreCase(SPECIES_DATA_TYPE)
                        || dataType.equalsIgnoreCase(ALL_DATA_TYPE)) {
                    speciesData =
                        ASCIIReportsHelper.getSpeciesData(plotObservations);
                }

                // Place generated file in ZIP archive
                if ( dataType.equalsIgnoreCase(ALL_DATA_TYPE) )
                {
                    this.initResponseForFileDownload(response, "VegbankPlotsFlat.zip", ZIP_CONTENT_TYPE);
                    
                    Hashtable nameContent = new Hashtable();
                    nameContent.put("environmentalData.txt",environmentalData);
                    nameContent.put("speciesData.txt", speciesData);
                    OutputStream responseOutputStream = response.getOutputStream();
                    responseOutputStream.flush();
                    
                    // TODO: Get the OS of user if possible and return a native file	
                    // For now use DOS style, cause those idiots would freak with anything else ;)					
                    ServletUtility.zipFiles( nameContent, responseOutputStream, LineEnds.STYLE_DOS );
                    
                }
                // Just downLoad the generated file
                else 
                {
                    this.initResponseForFileDownload( response, "VegbankDownload.txt", DOWNLOAD_CONTENT_TYPE);
                    if ( environmentalData != null ) {
                        this.sendFileToBrowser( environmentalData.toString() , response);
                    } else if ( speciesData != null ) {
                        this.sendFileToBrowser( speciesData.toString() , response);
                    }
                }
                */

			} else if ( formatType.equalsIgnoreCase( VEGBRANCH_FORMAT_TYPE) ) {
				// Store the returned ModelBean Trees
				Collection plotObservations = this.getPlotObservations(selectedPlots);
		
				// wrap in XML
				String xml = XMLUtil.getVBXML(plotObservations);

				// transform
				String vegbranchCSV = null;
				log.debug("finding XSL file: " + VEGBRANCH_XSL_PATH);
				File f = new File(VEGBRANCH_XSL_PATH);
				if (f.exists()) {
					transformXML transformer = new transformXML();
					//////// Get as string, not UTF-16
					vegbranchCSV = transformer.getTransformedFromString(xml, VEGBRANCH_XSL_PATH);
					
					// use a writer
					//Writer writer = new Writer();
					//transformer.getTransformedFromString(xml, VEGBRANCH_XSL_PATH, writer);

				} else {
					errors.add(Globals.ERROR_KEY,
						new ActionMessage("errors.action.failed",
							"Problem creating VegBranch download file: no XSL"));
				}

				/*
				if (Utility.isStringNullOrEmpty(vegbranchCSV)) {
					errors.add(Globals.ERROR_KEY,
						new ActionMessage("errors.action.failed",
							"Problem creating VegBranch download file: empty file"));
				}
				*/

				
				boolean doZip = true;
				if (doZip) {
					/////////////////
					// ZIP the XML doc
					/////////////////
					this.initResponseForFileDownload(response, "vegbranch_import.zip", VEGBRANCH_ZIP_CONTENT_TYPE);
					
					Hashtable nameContent = new Hashtable();
					nameContent.put("vegbranch_import.csv", vegbranchCSV);
					OutputStream responseOutputStream = response.getOutputStream();
					responseOutputStream.flush();
					
					// TODO: Get the OS of user if possible and return a native file	
					// For now use DOS style, cause those idiots would freak with anything else ;)					
					log.debug("Zipping VegbranchImport.zip");
					ServletUtility.zipFiles( nameContent, responseOutputStream, LineEnds.STYLE_DOS, EXPORT_CHARSET);
					/////////////////

				} else {
					this.initResponseForFileDownload( response, "vegbranch_import.csv", VEGBRANCH_CONTENT_TYPE);
					//response.setCharacterEncoding(EXPORT_CHARSET);
					this.sendFileToBrowser( vegbranchCSV , response);

				}


			}
			else
			{
				// Invalid Request
				errors.add(
					Globals.ERROR_KEY,
					new ActionMessage("errors.action.failed", "formatType = '" + formatType +  "' not supported"));
			}
		}
		catch (Exception e)
		{
			log.debug("DownloadAction: " + e.getMessage(), e);
			e.printStackTrace();
			errors.add(
				Globals.ERROR_KEY,
				new ActionMessage("errors.action.failed", e.getMessage() ));
		}
		
		if ( ! errors.isEmpty() )
		{
			saveErrors(request, errors);
			// Need to put selectedPlots into the request 
			//TODO: use the form bean instead 
			request.setAttribute("selectedPlots", selectedPlots);
			return (mapping.getInputForward());	
		}
		
		return mapping.findForward(fwd);
	}
	
	/**
	 * @param selectedPlots
	 * @return
	 */
	private Collection getPlotObservations(String[] selectedPlots) throws NumberFormatException, Exception
	{
		Collection plotObsersevations = new ArrayList();
		DBModelBeanReader dbmbReader = new DBModelBeanReader();
		
		// Get the plots
		for ( int i = 0; i < selectedPlots.length ; i++ )
		{
			log.debug("DownloadAction : Downloading " + selectedPlots[i]);
			Observation observation = (Observation) dbmbReader.getVBModelBean( selectedPlots[i]  );
			plotObsersevations.add(observation);
		}
		dbmbReader.releaseConnection();
		return plotObsersevations;
	}
	
	private void initResponseForFileDownload(HttpServletResponse response, String downloadFileName , String contentType) 
	{
		response.setContentType(contentType);
		response.setHeader("Content-Disposition","attachment; filename="+downloadFileName+";"); 
	}
	
	private void sendFileToBrowser(String fileContents, HttpServletResponse response ) throws IOException
	{
		//log.debug(fileContents);
		response.getWriter().write(fileContents);
	}

	private void sendFileToBrowser(InputStream fileIn, HttpServletResponse response) throws IOException
	{
        String line;
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(fileIn));
                //new InputStreamReader(fileIn, EXPORT_CHARSET));
        PrintWriter out = response.getWriter();

        /*
        char[] cbuf = new char[1024];
        while (reader.read(cbuf, 0, 1024) != -1) {
		    out.write(cbuf);
        }
        */

        while ((line=reader.readLine()) != null) {
            log.debug(line);
		    out.println(line);
        }

	}


    /**
     * Returns a path to a directory in the public webspace.
     * Calls mkdirs().
     */
    private String buildOutputFilePath() throws IOException {
        String path = Utility.VB_EXPORT_DIR +
                DateUtility.getTimestamp() + "/" + this.hashCode() + "/";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return path;
    }

    /**
     *
     */
    private String streamToString(InputStream in) throws IOException {
        String line;
        StringBuffer sb = new StringBuffer(4096);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(in));
                //new InputStreamReader(in, EXPORT_CHARSET));

        while ((line=reader.readLine()) != null) {
		    sb.append(line);
        }

        in.flush();
        in.close();

        return sb.toString();
	}


    /**
     *
     */
    private String getCSVResults(String sqlSelect, String sqlWhere, String[] sqlParams) 
            throws IOException {
        String psqlPath = Utility.dbPropFile.getString("psqlPath");
        String dbUser = Utility.dbPropFile.getString("user");
        String dbName = Utility.DATABASE_NAME;
        String inputFilePathTaxa, inputFilePathEnv, outputFilePath;

        MessageFormat format = new MessageFormat(sqlWhere);
        sqlWhere = format.format(sqlParams);
        String sqlQuery = sqlSelect + " AND " + sqlWhere;

        inputFilePathTaxa = Utility.writeTempFile(new StringReader(sqlQuery));
        //outputFilePath = buildOutputFilePath() + "vegbank_export.csv";

        String cmd = psqlPath + " -U " + dbUser + " -d " + dbName + 
                " -f " + inputFilePathTaxa +
                //" -o " + outputFilePath +
                " -n -A -F , " +
                " -P footer=false";

        log.debug("executing shell command: " + cmd);
        Runtime shell = Runtime.getRuntime();
        Process proc = shell.exec(cmd);
        log.debug("deleting file: " + inputFilePathTaxa);
        Utility.deleteFile(inputFilePathTaxa);

					//this.initResponseForFileDownload( response, "vegbank_export.csv", VEGBRANCH_CONTENT_TYPE);
					//this.sendFileToBrowser(proc.getInputStream(), response);

                    // Create a file in the webspace
                    //String webPath = "/" + outputFilePath.substring(outputFilePath.indexOf(Utility.EXPORT_DIRNAME));
					//this.sendFileToBrowser("<a href='" + webPath + "'>download now</a>", response);


        return streamToString(proc.getInputStream());
    }

}