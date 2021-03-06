package org.vegbank.plots.datasource;
import java.util.*;
import java.sql.*;


/**
 * plugin to allow access to plot data stored in the VegBank - plots 
 * database <br> <br>
 *
 *	
 *	'$Author: farrell $' <br>
 *	'$Date: 2003-08-21 21:16:45 $' <br>
 *	'$Revision: 1.5 $' <br>
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
 
public class VegBankDataSourcePlugin implements PlotDataSourceInterface
{
	


	// THE PLUGIN PROPERTIES FILE
	private ResourceBundle rb;

	//variables below are for VEGBANK on postgresql
	private String dbUrl = "";
	private String dbUser = "";
	private String dbPassword;
	private String dbDriverClass = "";
	public Connection con = null;
	
	/**
	 * constructor -- assume that if this constructor is being called then the 
	 * default database is to be accessed.  There may be times when the calling
	 * class wants to specify the database type -- like the class that supports
	 * access to the Access VegBank access version where the class wants to 
	 * specify the database type as an access database type
	 */
	public VegBankDataSourcePlugin()
	{
		//use the default database -- postgresql
		this("postgresql");
	}
	
	/**
	 * constructor for this class.  The input parameter is the database type
	 * so that the class can determine the type of database parameters to 
	 * use
	 * @param databaseType -- includes: postgresql, oracle, msaccess -- for now
	 */
	public VegBankDataSourcePlugin(String databaseType)
	{
		System.out.println("VegBankDataSourcePlugin > using database type: " + databaseType);
		try 
		{
			// 	GET THE PARAMETERS FROM THE PROPERTIES FILE
			rb = ResourceBundle.getBundle("plugin");
			this.dbDriverClass =rb.getString("databaseDriverClass");
			this.dbUrl =rb.getString("databaseConnectString");
			this.dbUser  =rb.getString("databaseUser");
			this.dbPassword =rb.getString("databaseUserPassword");
			System.out.println("VegBankDataSource init > dbUrl: " + this.dbUrl );
			if (databaseType.equals("msaccess"))
			{
				dbDriverClass = "sun.jdbc.odbc.JdbcOdbcDriver";
				dbUrl = "jdbc:odbc:vegbank_access";
			}
			//else use the default database settings
			con = this.getConnection();
			DatabaseMetaData dma = con.getMetaData();
			System.out.println("VegBankDataSourcePlugin > Connected to " + dma.getURL() );
			System.out.println("VegBankDataSourcePlugin > Driver       " + dma.getDriverName() );
			System.out.println("VegBankDataSourcePlugin > Version      " + dma.getDriverVersion() );
			System.out.println("VegBankDataSourcePlugin > Catalog      " + con.getCatalog() );
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   // All other types of exceptions
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex + "<BR>");
			ex.printStackTrace();
		}
	}
	

	
	  
	/**
	 * utility method that will return a database connection for use with the 
	 * database
	 */
	private Connection getConnection()
	{
		Connection c = null;
		try 
 		{
			Class.forName(dbDriverClass);
			//the vegbank database
			c = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPassword);
		}
		catch ( Exception e )
		{
			System.out.println("VegBankDataSourcePlugin > Exception : " +e.getMessage());
			e.printStackTrace();
		}
		return(c);
	}
 
 
 	/** 
	 * method that returns the accession number associated with a plot id
	 * the input plot id is the unique identifier of the plot as used by 
	 * the RDBMS
	 * @param plotId -- the RDBMS unique plot ID
	 */
	public String getAccessionValue(String plotId)
	{
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select  accession_number "
			+" from PLOT where PLOT_ID = " + plotId );
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
		// All other types of exceptions
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	

	public Vector getObservationContributors ( String plotName )
	{
		return new Vector();
	}
	
	public String getObservationContributorSalutation(String contributorWholeName)
	{
		return null;
	}
	
	public String getObservationContributorGivenName(String contributorWholeName) 
	{
		return null;
	}
	
	public String getObservationContributorMiddleName(String contributorWholeName)
	{
		return null;
	}
	
	public String getObservationContributorSurName(String contributorWholeName)
	{
		return null;
	}
	
	public String getObservationContributorOrganizationName(String contributorWholeName){
		return null;
	}
	
	public String getObservationContributorContactInstructions(String contributorWholeName)
	{
		return null;
	}
	
	public String getObservationContributorPhoneNumber(String contributorWholeName)
	{
		return null;
	}
	
	public String getObservationContributorCellPhoneNumber(String contributorWholeName)
	{
		return null;
	}
	
	public String getObservationContributorFaxPhoneNumber(String contributorWholeName)
	{
		return null;
	}
	
	public String getObservationContributorOrgPosition(String contributorWholeName)
	{
		return null;
	}
	
	public String getObservationContributorEmailAddress(String contributorWholeName)
	{
		return null;
	}
	
	public String getObservationContributorDeliveryPoint(String contributorWholeName)
	{
		return null;
	}
	
	public String getObservationContributorCity(String contributorWholeName)
	{
		return null;
	}
	
	public String getObservationContributorAdministrativeArea(String contributorWholeName)
	{
		return null;
	}
	
	public String getObservationContributorPostalCode(String contributorWholeName)
	{
		return null;
	}
	
	public String getObservationContributorCountry(String contributorWholeName)
	{
		return null;
	}
	
	public String getObservationContributorCurrentFlag(String contributorWholeName)
	{
		return null;
	}
	
	public String getObservationContributorAddressStartDate(String contributorWholeName)
	{
		return null;
	}
	
	public String getObservationContributorRole(String contributorWholeName)
	{
		return null;
	}
	
	/** 
	 * method that returns the accession number associated with a plot obs
	 * the input plot id is the unique identifier of the plot observation 
	 * as used by the RDBMS.  This method is to be revisited in Nov 2002 
	 * and changed so that the plot id and a sequence number can be traced.
	 * @param plotId -- the RDBMS unique plot ID
	 */
	public String getObservationAccessionNumber(String plotId)
	{
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select  obsaccessionnumber "
			+" from OBSERVATION where PLOT_ID = " + plotId );
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
	
    // We want to append the current date onto this string ( business rule ?) 
    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    String DATE_FORMAT = "ddMMMyyyy-HH:mm:ss";
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    String date = sdf.format(cal.getTime());
		System.out.println("--->>> VegBankDataSourcePlugin > " + date + " and " + s);
    return(s + "." + date);
	}
		
  /**
	 * method to return the taxonCovet from a data source using as input
	 * the scientific plant name -- or the plant name that comes from
	 * the 'getPlantTaxaNames' method
	 *
	 * @param plantName -- the scientific plantName
	 */
	public String getPlantTaxonCover(String plantName, String plotName)
	{
		String taxonCover = "";
		StringBuffer sb = new StringBuffer();
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			sb.append("select TAXONCOVER from TAXONOBSERVATION where CHEATPLANTNAME = '"+plantName+"'"); 
			ResultSet rs = stmt.executeQuery(sb.toString());
			while (rs.next()) 
			{
				taxonCover = rs.getString(1);
			}
		}
		catch (java.lang.Exception ex) 
		{   
			// All other types of exceptions
			System.out.println("VegBankDataSourcePlugin > Exception finding taxonCover: " + ex );
			System.out.println("sql: " + sb.toString() );
			ex.printStackTrace();
		}
	 	return(taxonCover);
	}
	

	
  /**
	 * method to return the taxa code from a data source using as input
	 * the scientific plant name -- or the plant name that comes from
	 * the 'getPlantTaxaNames' method
	 *
	 * @param plantName -- the scientific plantName
	 */
	public String getPlantTaxonCode(String plantName)
	{
		String code = "";
		StringBuffer sb = new StringBuffer();
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			sb.append("select CHEATPLANTCODE from TAXONOBSERVATION where CHEATPLANTNAME = '"+plantName+"'"); 
			ResultSet rs = stmt.executeQuery(sb.toString());
			while (rs.next()) 
			{
				code = rs.getString(1);
			}
		}
		catch (java.lang.Exception ex) 
		{   
			// All other types of exceptions
			System.out.println("VegBankDataSourcePlugin > Exception finding taxonCode: " + ex );
			System.out.println("sql: " + sb.toString() );
			ex.printStackTrace();
		}
	 	return(code);
	}
	
	
	/**
	 * method that returns a vector of PLOT ID'S (Plot table Primary keys) 
	 * from the target VegBank Database
	 */
	public Vector getPlotIDs()
	{
		Vector v = new Vector();
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select distinct PLOT_ID from PLOT");
							
			while (rs.next()) 
			{
				String s = rs.getString(1);
				v.addElement(s);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
			// All other types of exceptions
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(v);
	}
	
	
	/**
	 * method that returns a vector of plot codes which are the 
	 * VegBank Accession Numbers, for now it will be the authorPlotCode
	 * 
	 */
	public Vector getPlotCodes()
	{
		Vector v = new Vector();
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select distinct authorPlotCode from PLOT");
							
			while (rs.next()) 
			{
				String s = rs.getString(1);
				v.addElement(s);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
			// All other types of exceptions
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(v);
	}
 	

	/**
 	 * returns the soil drainage of a plot
 	 * @param plotName -- the plot
 	 */
 	public String getSoilDrainage(String plotName)
 	{
 		String s = this.getObservationElement(plotName, "soildrainage");
		return(s);
 	}
 	
 	/**
 	 * returns the author's observation code
 	 * @param plotName -- the plot
 	 */
 	public String getAuthorObsCode(String plotName)
 	{
 		return this.getObservationElement(plotName, "authorObsCode");
 	}
 	
 	/**
 	 * returns the start date for the plot observation
 	 * @param plotName -- the plot
 	 */
 	public String getObsStartDate(String plotName)
 	{
		String s = null;
		StringBuffer sb = new StringBuffer();
		Statement stmt = null;
		try 
		{
			sb.append("select OBSSTARTDATE from OBSERVATION where PLOT_ID = " + plotName);
			stmt = con.createStatement();
			System.out.println("VegBankDataSourcePlugin > query: " + sb.toString() );
			ResultSet rs = stmt.executeQuery(sb.toString());
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
		// All other types of exceptions
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
 	}
 	
 	/**
 	 * returns the stop date for the plot observation
 	 * @param plotName -- the plot
 	 */
 	public String getObsStopDate(String plotName)
 	{
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select obsenddate "
			+" from OBSERVATION where PLOT_ID = " + plotName );
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
		// All other types of exceptions
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
 	}
 	
 	/**
 	 * returns the soil depth of a plot
 	 * @param plotName -- the plot
 	 */
 	public String getSoilDepth(String plotName) 
 	{
		return this.getObservationElement(plotName, "soilDepth");
 	}
	
	
	//START
	
	/**
	 * utlility method for querying the observation table and returning 
	 * each of the elements as a string
	 * @param plotName -- the name of the plot 
	 * @param elementname -- the attribute name of the desired attribute 
	 * @return elementValue -- the value of the desired attribute 
	 */
	 private String getObservationElement(String plotName, String elementName )
	 {
		return getElement(plotName,  "OBSERVATION" , elementName, null);
	 }

 
	 
	/**
	 * utlility method for querying the project table and returning 
	 * each of the elements as a string
	 * @param plotName -- the name of the plot 
	 * @param elementname -- the attribute name of the desired attribute 
	 */
	 private String getProjectElement(String plotName, String elementName )
	 {		
    String subQuery = " where PROJECT_ID = (select project_id from PLOT where PLOT_ID =";
		return getElement(plotName,  "PROJECT" , elementName, subQuery);
	 }	 
	 
	/**
	 * utlility method for querying the plot table and returning 
	 * each of the elements as a string
	 * @param plotName -- the name of the plot 
	 * @param elementname -- the attribute name of the desired attribute 
	 */
	 private String getPlotElement(String plotName, String elementName )
	 {
		return getElement(plotName,  "PLOT" , elementName, null);
	 }
	
  /**
	 * utlility method for querying the commclass table and returning 
	 * each of the elements as a string
	 * @param plotName -- the name of the plot 
	 * @param elementname -- the attribute name of the desired attribute 
	 * @return elementValue -- the value of the desired attribute
	 */
	 private String getCommunityElement(String plotName, String elementName )
	 {
    // Need a subquery because FK is Observation_id not plot_id
    String subQuery = " where OBSERVATION_ID = "
      + "(select OBSERVATION_ID from OBSERVATION where PLOT_ID = ";
		return getElement(plotName,  "COMMCLASS" , elementName, subQuery);
	 }
	 
	 /**
	  *  Ruturns the results of a SQL query in a string
	  * 
		* @param plotName -- the name of the plot 
	  * @param tableName -- the name of the table
	 	* @param elementName -- the attribute name of the desired attribute 
	  */
	 private String getElement( String plotName, String tableName, String elementName, String subQuery )
	 {
			String retVal = "";		// Empty string better than null here??
			Statement stmt = null;
			StringBuffer sb = new StringBuffer();
			try 
			{
				stmt = con.createStatement();
				sb.append("select ");
				sb.append(elementName);
				sb.append(" from " + tableName);
				
				if ( subQuery == null) 
				{
 					sb.append(" where PLOT_ID = ");
					sb.append(plotName);
				}
				else 
				{				
					sb.append( subQuery + plotName + ")" );
				}
				
				// Get the results of the query
				ResultSet rs = stmt.executeQuery(  sb.toString() );
				while (rs.next()) 
				{
					// Only expect one value in this result set.
					retVal = rs.getString(1);
					if (retVal == null)
						retVal = "";
				}
				rs.close();
				stmt.close();
			}
			catch (SQLException ex) 
			{
				System.out.println("VegBankDataSourcePlugin > query: " + sb.toString() );
				this.handleSQLException( ex );
			}
			catch (java.lang.Exception ex) 
			{
				System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
				ex.printStackTrace();
			}
			System.out.println("VegbankDataSource > Searching for '" + elementName 
																		+ "' in '" + tableName + "' FOUND: '" + retVal + "'");
			return(retVal);	 	
	 }
	 	 
	/**
	*/
	public String getObsDateAccuracy(String plotName)
	{
		String s = this.getObservationElement(plotName, "dateaccuracy");
		return(s);
	}
	 
	/**
	*/
	public Hashtable getCoverMethod(String plotName)
	{
		Hashtable  s = new Hashtable();
		return(s);
	}
	
	/**
	 */
	public Hashtable getStratumMethod(String plotName)
	{
		Hashtable s = new Hashtable();
		return(s);
	}
	
	/**
	 */
	public String getStemSizeLimit(String plotName)
	{
		return this.getObservationElement(plotName, "stemsizelimit");
	}

	/**
	 */
	public String getMethodNarrative(String plotName)
	{
		return this.getObservationElement(plotName, "methodnarrative");
	}
	
	/**
	 */
	public String getTaxonObservationArea(String plotName)
	{
		return this.getObservationElement(plotName, "taxonobservationarea");
	}
	
	/**
	 */
	public String getCoverDispersion(String plotName )
	{
		String s = this.getObservationElement(plotName, "coverdispersion");
		return(s);
	}
	
	/**
	 */
	public boolean getAutoTaxonCover(String plotName)
	{
		// FIXME -- not looking up the value
		String autoTaxonCover = this.getObservationElement(plotName, "autoTaxonCover");	
		return getBooleanFromString(autoTaxonCover);
	}
	
	/*
	 *  Convert a String to a Boolean
	 */
	 private boolean getBooleanFromString(String s) 
	 {
	 		boolean retVal = false; 
	 		if (s ==  "t") 
	 		{
	 			retVal = true;
	 		} 
	 		else if ( s == "f")
	 		{
	 				retVal = false;
	 		}
	 		
	 		return retVal;
	 }
	
	/**
	 */
	public String getStemObservationArea(String plotName)
	{
		String s = this.getObservationElement(plotName, "stemobservationarea");
		return(s);
	}
	
	/**
	 */
	public String getStemSampleMethod(String plotName)
	{
		return this.getObservationElement(plotName, "stemSampleMethod");
	}
	
	/**
	 */
	public String getOriginalData(String plotName )
	{
		String s = this.getObservationElement(plotName, "originaldata");
		return(s);
	}
	
	/**
	 */
	public String getEffortLevel( String plotName )
	{
		String s = this.getObservationElement(plotName, "effortlevel");
		return(s);
	}
//END


	/**
 	 * returns true if the plot is a permanent plot or false if not
 	 * @param plotName -- the plot
 	 */
 	 public boolean isPlotPermanent(String plotName)
 	 {
		boolean b = true; //default is true
 	 	String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select permanence "
			+" from PLOT where PLOT_ID = " + plotName );
			while (rs.next()) 
			{
				 s = rs.getString(1);
				 if ( s.toUpperCase().equals("FALSE") || s.toUpperCase().startsWith("F")  )
				 {
					 b = false;
				 }
			}
		}
		catch (java.lang.Exception ex) 
		{
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(b);
 	 }
  
  /**
 	* returns the soil taxon for the plot -- this is the USDA class
 	* heirarchy (eg., Order, Group, Family, Series etc..)
 	* @param plotName -- the plot
 	*/
 	public String getSoilTaxon(String plotName)
 	{
 		// FIXME: Not searching for real data
 		return("");
 	}
 	
 	/**
 	 * returns how the soil taxon was determined (eg., field observation
 	 * mapping, other ...)
 	 * @param plotName -- the plot
	 * @deprecated -- this element has been removed from the database -- 20020717
 	 */
 	public String getSoilTaxonSource(String plotName)
 	{
 		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("select SOILTAXONSRC from OBSERVATION where PLOT_ID = "+plotName);
			ResultSet rs = stmt.executeQuery( sb.toString() );			
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (java.lang.Exception ex) 
		{   
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
 	}

//START
public String getPlotValidationLevel(String plotName)
{
		String s = this.getObservationElement(plotName, "plotvalidationlevel");
		return(s);
}

public String  getFloristicQuality(String plotName)
{
		String s = this.getObservationElement(plotName, "floristicquality");
		return(s);
}

public String  getBryophyteQuality(String plotName)
{
		String s = this.getObservationElement(plotName, "bryophytequality");
		return(s);
}

public String  getLichenQuality(String plotName)
{
		String s = this.getObservationElement(plotName, "lichenquality");
		return(s);
}

public String  getObservationNarrative(String plotName)
{
		String s = this.getObservationElement(plotName, "observationnarrative");
		return(s);
}

public String  getHomogeneity(String plotName)
{
		String s = this.getObservationElement(plotName, "homogeneity");
		return(s);
}

public String  getRepresentativeness(String plotName)
{
		String s = this.getObservationElement(plotName, "representativeness");
		return(s);
}

public String  getBasalArea(String plotName)
{
		String s = this.getObservationElement(plotName, "basalarea");
		return(s);
}

public String  getSoilMoistureRegime(String plotName)
{
		String s = this.getObservationElement(plotName, "soilmoistureregime");
		return(s);
}

public String  getWaterSalinity(String plotName)
{
		String s = this.getObservationElement(plotName, "watersalinity");
		return(s);
}

public String  getShoreDistance(String plotName)
{
		String s = this.getObservationElement(plotName, "shoredistance");
		return(s);
}

public String  getOrganicDepth(String plotName)
{
		String s = this.getObservationElement(plotName, "organicdepth");
		return(s);
}

public String  getPercentBedRock(String plotName)
{
		String s = this.getObservationElement(plotName, "percentbedrock");
		return(s);
}

public String  getPercentRockGravel(String plotName)
{
		String s = this.getObservationElement(plotName, "percentrockgravel");
		return(s);
}

public String  getPercentWood(String plotName)
{
		String s = this.getObservationElement(plotName, "percentwood");
		return(s);
}
public String  getPercentLitter(String plotName)
{
		String s = this.getObservationElement(plotName, "percentlitter");
		return(s);
}

public String  getPercentBareSoil(String plotName)
{
		String s = this.getObservationElement(plotName, "percentbaresoil");
		return(s);
}

public String  getPercentWater(String plotName)
{
		String s = this.getObservationElement(plotName, "percentwater");
		return(s);
}

public String  getPercentOther(String plotName)
{
		String s = this.getObservationElement(plotName, "percentother");
		return(s);
}

public String  getNameOther(String plotName)
{
		String s = this.getObservationElement(plotName, "nameother");
		return(s);
}

public String  getStandMaturity(String plotName)
{
		String s = this.getObservationElement(plotName, "standmaturity");
		return(s);
}


public String  getLandscapeNarrative(String plotName)
{		
  String s = this.getObservationElement(plotName, "landscapeNarrative");
	return(s);

}

public String  getPhenologicalAspect(String plotName)
{
	// FIXME: There is a different name for this attribute in the db vs. xml
		String s = this.getObservationElement(plotName, "phenologicAspect");
		return(s);
}

public String  getWaterDepth(String plotName)
{
		String s = this.getObservationElement(plotName, "waterDepth");
		return(s);
}

public String  getFieldHt(String plotName)
{
		String s = this.getObservationElement(plotName, "fieldHt");
		return(s);
}

public String  getSubmergedHt(String plotName)
{
		String s = this.getObservationElement(plotName, "submergedHt");
		return(s);
}

public String  getTreeCover(String plotName)
{
		String s = this.getObservationElement(plotName, "treeCover");
		return(s);
}

public String  getShrubCover(String plotName)
{
		String s = this.getObservationElement(plotName, "shrubCover");
		return(s);
}

public String  getFieldCover(String plotName)
{
		String s = this.getObservationElement(plotName, "fieldCover");
		return(s);
}

public String  getNonvascularCover(String plotName)
{
		String s = this.getObservationElement(plotName, "nonvascularCover");
		return(s);
}

public String  getSuccessionalStatus(String plotName)
{
		String s = this.getObservationElement(plotName, "successionalstatus");
		return(s);
}

public String  getTreeHt(String plotName)
{
		String s = this.getObservationElement(plotName, "treeht");
		return(s);
}

public String  getShrubHt(String plotName)
{
		String s = this.getObservationElement(plotName, "shrubht");
		return(s);
}

public String  getNonvascularHt(String plotName)
{
		String s = this.getObservationElement(plotName, "nonvascularht");
		return(s);
}

public String  getFloatingCover(String plotName)
{
		String s = this.getObservationElement(plotName, "floatingcover");
		return(s);
}

public String  getSubmergedCover(String plotName)
{
		String s = this.getObservationElement(plotName, "submergedcover");
		return(s);
}

public String  getDominantStratum(String plotName)
{
		String s = this.getObservationElement(plotName, "dominantstratum");
		return(s);
}

public String  getGrowthform1Type(String plotName)
{
		String s = this.getObservationElement(plotName, "growthform1type");
		return(s);
}

public String  getGrowthform2Type(String plotName)
{
	String s = this.getObservationElement(plotName, "growthform2type");
		return(s);
}

public String  getGrowthform3Type(String plotName)
{
	String s = this.getObservationElement(plotName, "growthform3type");
		return(s);
}

public String  getGrowthform1Cover(String plotName)
{
	String s = this.getObservationElement(plotName, "growthform1cover");
		return(s);
}

public String  getGrowthform2Cover(String plotName)
{
	String s = this.getObservationElement(plotName, "growthform2cover");
		return(s);
}

public String  getGrowthform3Cover(String plotName)
{
	String s = this.getObservationElement(plotName, "growthform3cover");
		return(s);
}

public boolean  getNotesPublic(String plotName)
{
	// FIXME returns a default
	String s = this.getObservationElement(plotName, "notespublic");
	return(false);
}

public boolean  getNotesMgt(String plotName)
{
	// FIXME returns a default
	String s = this.getObservationElement(plotName, "notesmgt");
	return(false);
}

public boolean  getRevisions(String plotName)
{
	// FIXME returns a default
	return(false);
}
//END

	//returns all the plots stored in the access file
	public Vector getPlotNames()
	{
		Vector v = new Vector();
		v.addElement("test-plot");
		return(v);
	}
	
	//returns the project name 
	public String getProjectName(String plotName)
	{ 
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("select  projectname from PROJECT where PROJECT_ID = (");
			sb.append(" select project_id from PLOT where PLOT_ID = "+plotName+")");
			ResultSet rs = stmt.executeQuery( sb.toString() );			
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (java.lang.Exception ex) 
		{   
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		/*
		if ( s == null )
			s = "My Name is Bob";
		*/
		return(s);
	}
	
	//returns a vector with the project contributors
	public Vector getProjectContributors(String plotName)
	{
		Vector contributors = new Vector();
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("select givenName, surName from PARTY where PARTY_ID in (");
			sb.append(" select PARTY_ID from PROJECTCONTRIBUTOR where PROJECT_ID = (");
			sb.append(" select PROJECT_ID from PLOT where PLOT_ID = "+plotName+"))");
			ResultSet rs = stmt.executeQuery( sb.toString() );			
			while (rs.next()) 
			{
				 s = rs.getString(1);
				 s = s+" "+rs.getString(2);
				 contributors.addElement(s);
			}
		}
		catch (java.lang.Exception ex) 
		{   
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(contributors);
	}
	
	//retuns the person's salutation based on their full name which is the
	//concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorSalutation(String contributorWholeName)
	{
		String s = "";
		Statement stmt = null;
		try 
		{
			// FIRST GET THE SURNAME AND GIVEN NAME
			StringTokenizer st = new StringTokenizer(contributorWholeName);
			String givenName = st.nextToken(); 
			String surName = st.nextToken();
			// WRITE THE QUERY
			stmt = con.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("select salutation from PARTY where surname like '"+surName+"' ");
			sb.append("and givenname like '"+givenName+"'");
			ResultSet rs = stmt.executeQuery( sb.toString() );			
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (java.lang.Exception ex) 
		{   
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	
	//retuns the person's given based on their full name which is the
	//concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorGivenName(String contributorWholeName)
	{
		String s = "";
		try 
		{
			StringTokenizer st = new StringTokenizer(contributorWholeName);
			s = st.nextToken(); 
		}
		catch (java.lang.Exception ex) 
		{   
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	//retuns the person's middle name based on their full name which is the
	//concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorMiddleName(String contributorWholeName)
	{
		// FIXME -- returning a default
		return("");
	}
	
	//retuns the person's surName based on their full name which is the
	//concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorSurName(String contributorWholeName)
	{
		String s = "";
		try 
		{
			StringTokenizer st = new StringTokenizer(contributorWholeName);
			s = st.nextToken();
			s = st.nextToken();
		}
		catch (java.lang.Exception ex) 
		{   
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	//retuns the name of an org. that a person is associated with based on 
	//their full name which is the concatenated givename and surname of the 
	//user like 'bob peet'
	public String getProjectContributorOrganizationName(String contributorWholeName)
	{
		String s = "";
		Statement stmt = null;
		try 
		{
			// FIRST GET THE SURNAME AND GIVEN NAME
			StringTokenizer st = new StringTokenizer(contributorWholeName);
			String givenName = st.nextToken(); 
			String surName = st.nextToken();
			// WRITE THE QUERY
			stmt = con.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("select ORGANIZATIONNAME from PARTY where surname like '"+surName+"' ");
			sb.append("and givenname like '"+givenName+"'");
			ResultSet rs = stmt.executeQuery( sb.toString() );			
			while (rs.next()) 
			{
				s = rs.getString(1);
			}
		}
		catch (java.lang.Exception ex) 
		{   
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	//retuns the person's contactinstructions based on their full name which is the
	//concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorContactInstructions(String contributorWholeName)
	{
		String s = "";
		Statement stmt = null;
		try 
		{
			// FIRST GET THE SURNAME AND GIVEN NAME
			StringTokenizer st = new StringTokenizer(contributorWholeName);
			String givenName = st.nextToken(); 
			String surName = st.nextToken();
			// WRITE THE QUERY
			stmt = con.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("select CONTACTINSTRUCTIONS from PARTY where surname like '"+surName+"' ");
			sb.append("and givenname like '"+givenName+"'");
			ResultSet rs = stmt.executeQuery( sb.toString() );			
			while (rs.next()) 
			{
				s = rs.getString(1);
			}
		}
		catch (java.lang.Exception ex) 
		{   
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	//retuns the person's phone number based on their full name which is the
	//concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorPhoneNumber(String contributorWholeName)
	{
		String s = "";
		Statement stmt = null;
		try 
		{
			// FIRST GET THE SURNAME AND GIVEN NAME
			StringTokenizer st = new StringTokenizer(contributorWholeName);
			String givenName = st.nextToken(); 
			String surName = st.nextToken();
			// WRITE THE QUERY
			stmt = con.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("select phonenumber from telephone where party_id in ( " );
			sb.append("select party_id from PARTY where surname like '"+surName+"' ");
			sb.append("and givenname like '"+givenName+"')");
			ResultSet rs = stmt.executeQuery( sb.toString() );			
			while (rs.next()) 
			{
				s = rs.getString(1);
			}
			rs.close();
			stmt.close();
		}
		catch (java.lang.Exception ex) 
		{   
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	//retuns the person's cellPhone based on their full name which is the
	//concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorCellPhoneNumber(String contributorWholeName)
	{
		// FIXME -- the following methods return a default --- 
			return("");
	}
	//retuns the person's fax phone number based on their full name which is the
	//concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorFaxPhoneNumber(String contributorWholeName)
	{
			return("");
	}
	//retuns the party's position within an organization based on their full 
	// name which is the concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorOrgPosition(String contributorWholeName)
	{
			return("");
	}
	//retuns the person's email based on their full name which is the
	//concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorEmailAddress(String contributorWholeName)
	{
		String s = "";
		Statement stmt = null;
		try 
		{
			// FIRST GET THE SURNAME AND GIVEN NAME
			StringTokenizer st = new StringTokenizer(contributorWholeName);
			String givenName = st.nextToken(); 
			String surName = st.nextToken();
			// WRITE THE QUERY
			stmt = con.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("select email from PARTY where surname like '"+surName+"' ");
			sb.append("and givenname like '"+givenName+"'");
			ResultSet rs = stmt.executeQuery( sb.toString() );			
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (java.lang.Exception ex) 
		{   
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	//retuns the person's address line based on their full name which is the
	//concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorDeliveryPoint(String contributorWholeName)
	{
		String s = "";
		Statement stmt = null;
		try 
		{
			// FIRST GET THE SURNAME AND GIVEN NAME
			StringTokenizer st = new StringTokenizer(contributorWholeName);
			String givenName = st.nextToken(); 
			String surName = st.nextToken();
			// WRITE THE QUERY
			stmt = con.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("select deliverypoint from address where party_id in ( " );
			sb.append("select party_id from PARTY where surname like '"+surName+"' ");
			sb.append("and givenname like '"+givenName+"')");
			ResultSet rs = stmt.executeQuery( sb.toString() );			
			while (rs.next()) 
			{
				s = rs.getString(1);
			}
		}
		catch (java.lang.Exception ex) 
		{   
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	//retuns the person's city based on their full name which is the
	//concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorCity(String contributorWholeName)
	{
		String s = "";
		Statement stmt = null;
		try 
		{
			// FIRST GET THE SURNAME AND GIVEN NAME
			StringTokenizer st = new StringTokenizer(contributorWholeName);
			String givenName = st.nextToken(); 
			String surName = st.nextToken();
			// WRITE THE QUERY
			stmt = con.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("select city from address where party_id in ( " );
			sb.append("select party_id from PARTY where surname like '"+surName+"' ");
			sb.append("and givenname like '"+givenName+"')");
			ResultSet rs = stmt.executeQuery( sb.toString() );			
			while (rs.next()) 
			{
				s = rs.getString(1);
			}
		}
		catch (java.lang.Exception ex) 
		{   
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	//returns the administrative area, or state that a party is from
	public String getProjectContributorAdministrativeArea(String contributorWholeName)
	{
		String s = "";
		Statement stmt = null;
		try 
		{
			// FIRST GET THE SURNAME AND GIVEN NAME
			StringTokenizer st = new StringTokenizer(contributorWholeName);
			String givenName = st.nextToken(); 
			String surName = st.nextToken();
			// WRITE THE QUERY
			stmt = con.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("select administrativearea from address where party_id in ( " );
			sb.append("select party_id from PARTY where surname like '"+surName+"' ");
			sb.append("and givenname like '"+givenName+"')");
			ResultSet rs = stmt.executeQuery( sb.toString() );			
			while (rs.next()) 
			{
				s = rs.getString(1);
			}
		}
		catch (java.lang.Exception ex) 
		{   
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	//retuns the zip code for a party
	public String getProjectContributorPostalCode(String contributorWholeName)
	{
		String s = "";
		Statement stmt = null;
		try 
		{
			// FIRST GET THE SURNAME AND GIVEN NAME
			StringTokenizer st = new StringTokenizer(contributorWholeName);
			String givenName = st.nextToken(); 
			String surName = st.nextToken();
			// WRITE THE QUERY
			stmt = con.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("select postalcode from address where party_id in ( " );
			sb.append("select party_id from PARTY where surname like '"+surName+"' ");
			sb.append("and givenname like '"+givenName+"')");
			ResultSet rs = stmt.executeQuery( sb.toString() );			
			while (rs.next()) 
			{
				s = rs.getString(1);
			}
		}
		catch (java.lang.Exception ex) 
		{   
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	//retuns the person's country based on their full name which is the
	//concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorCountry(String contributorWholeName)
	{
		String s = "";
		Statement stmt = null;
		try 
		{
			// FIRST GET THE SURNAME AND GIVEN NAME
			StringTokenizer st = new StringTokenizer(contributorWholeName);
			String givenName = st.nextToken(); 
			String surName = st.nextToken();
			// WRITE THE QUERY
			stmt = con.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("select country from address where party_id in ( " );
			sb.append("select party_id from PARTY where surname like '"+surName+"' ");
			sb.append("and givenname like '"+givenName+"')");
			ResultSet rs = stmt.executeQuery( sb.toString() );			
			while (rs.next()) 
			{
				s = rs.getString(1);
			}
		}
		catch (java.lang.Exception ex) 
		{   
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	//retuns a boolean 'true' if it is a party's current address based on their 
	//full name which is the concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorCurrentFlag(String contributorWholeName)
	{
		return("");
	}
	//retuns the date that the address became current for a party based on 
	//their full name which is the concatenated givename and surname of the 
	//user like 'bob peet'
	public String getProjectContributorAddressStartDate(String contributorWholeName)
	{
			return("");
	}
	
	//returns the start date for the project
	public String getProjectStartDate(String plotName)
	{
		String s = "";
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("select startdate from project where project_id = ");
			sb.append("( select project_id from plot where plot_id = "+plotName+") ");
			ResultSet rs = stmt.executeQuery( sb.toString() );			
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (java.lang.Exception ex) 
		{   
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		
		/*
		if (s == null || s == "" )
			s = "2/2/78";
		*/
		
		return(s);
	}
	
	//returns the stop date for the project
	public String getProjectStopDate(String plotName )
	{
		String s = "";
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("select stopdate from project where project_id = ");
			sb.append("( select project_id from plot where plot_id = "+plotName+") ");
			ResultSet rs = stmt.executeQuery( sb.toString() );			
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (java.lang.Exception ex) 
		{   
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		
		/*
		if (s == null || s == "" )
			s = "2/5/78";		
		*/
		return(s);
	}
	
	//returns the placeNames each name can be used to retrieve
	//other information about a place
	public Vector getPlaceNames(String plotName)
	{
		Vector placeNames = new Vector();
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("select  placeName from NAMEDPLACE where NAMEDPLACE_ID = (");
			sb.append(" select NAMEDPLACE_ID from PLACE where PLOT_ID = "+plotName+")");
			ResultSet rs = stmt.executeQuery( sb.toString() );			
			while (rs.next()) 
			{
				 s = rs.getString(1);
				 placeNames.addElement(s);
			}
		}
		catch (java.lang.Exception ex) 
		{   
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(placeNames);
	}
	
	//retuns a description of a place based on a placeName
	public String getPlaceDescription(String placeName)
	{
		if ( placeName.startsWith("Marina") )
			return("marina park by the sunken ship imitation");
		else if (placeName.startsWith("Ventura"))
			return("the ventura harbor on the north side");
		else return("");
	}
	//returns a placeCode based on a placeName
	public String getPlaceCode(String placeName)
	{
		if ( placeName.startsWith("Marina") )
			return("MarinePrk");
		else if (placeName.startsWith("Ventura"))
			return("VenHbr");
		else return("");
	}
	//returns a place system based on a placeName
	public String getPlaceSystem(String placeName)
	{
		return("unknown system");
	}
	//returns the owner of a place based on the name of a place
	public String getPlaceOwner(String placeName)
	{
		if ( placeName.startsWith("Marina") )
			return("Ventura county");
		else if (placeName.startsWith("Ventura"))
			return("State of California");
		else return("");
	}
	
	
	//returns the project description
	public String getProjectDescription(String plotName )
	{
		return this.getProjectElement(plotName, "projectdescription");
	}
	
	
	/**
	 * private method that returns the project ids in the 
	 * form of a vector for a given plot
	 * 
	 */
	//private Vector 
	
	
	
	//returns the plot code for the current plot
	public String getPlotCode(String plotName)
	{ 
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select  authorPlotCode "
			+" from PLOT where PLOT_ID = " + plotName );
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
		// All other types of exceptions
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	//returns the easting 
	public String getXCoord(String plotName)
	{ 
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select  authore "
			+" from PLOT where PLOT_ID = " + plotName );
							
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
		// All other types of exceptions
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	//returns the northing
	public String getYCoord(String plotName)
	{ 
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select  authorn "
			+" from PLOT where PLOT_ID = " + plotName );
							
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
		// All other types of exceptions
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	/**
	 * method that retuns the latitude for an input plotid
	 * @param plotName -- the VegBank PLOTID
	 * @return latitude -- the validated latitude of that plot
	 */
	public String getLatitude(String plotName)
	{
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select  LATITUDE "
			+" from PLOT where PLOT_ID = " + plotName );
							
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
		// All other types of exceptions
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
	return(s);
	}
	
	/**
	 * method that retuns the latitude for an input plotid
	 * @param plotName -- the VegBank PLOTID
	 * @return longitude -- the validated latitude of that plot
	 */
	public String getLongitude(String plotName)
	{
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select  LONGITUDE "
			+" from PLOT where PLOT_ID = " + plotName );
							
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
		// All other types of exceptions
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	
	//returns the geographic zone
	public String getUTMZone(String plotName)
	{ 
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select  authorzone "
			+" from PLOT where PLOT_ID = " + plotName );
							
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
		// All other types of exceptions
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	public String getDatumType(String plotName)
	{
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("select AUTHORDATUM from PLOT where PLOT_ID = " +plotName);
			ResultSet rs = stmt.executeQuery( sb.toString() );
			while (rs.next()) 
			{
				s = rs.getString(1);
			}
		}
		catch (java.lang.Exception ex) 
		{   
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	
	//returns the plot shape
	public String getPlotShape(String plotName)
	{ 
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select  shape "
			+" from PLOT where PLOT_ID = " + plotName );
							
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
		// All other types of exceptions
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	//returns the plot area -n m^2
	public String getPlotArea(String plotName)
	{ 
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select  area "
			+" from PLOT where PLOT_ID = " + plotName );
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
		// All other types of exceptions
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	public String getAzimuth(String plotName) 
	{
		String s = this.getPlotElement(plotName, "azimuth");
		return(s);
	}	
 
	public String getDSGPoly(String plotName)
	{
		String s = this.getPlotElement(plotName, "dsgpoly");
		return(s);
	}
	
	public String getLocationNarrative(String plotName)
	{
	  return this.getPlotElement(plotName, "locationnarrative");
	}
	
	public String getLayoutNarrative( String plotName )
	{
		// FIXME: layoutNarrative vs. layoutnarative in database
	  return this.getPlotElement(plotName, "layoutnarative");
	}	
	
	//returns the state for the current plot
	public String getState(String plotName)
	{ 
		return this.getPlotElement(plotName, "state");	
	}
	
	//returns the state for the current plot
	public String getCommunityName(String plotName)
	{ 
		return this.getCommunityElement(plotName, "commname");
	}
	
	/**
   *  gets the community classification notes
   **/
  public String getClassNotes(String plotName)
	{ 
		return this.getCommunityElement(plotName, "classnotes");
	}
	
	/**
	 * returns the community code for the named plot
	 */
	public String getCommunityCode(String plotName)
	{
		return this.getCommunityElement(plotName, "commcode");
	}
	
	/**
	 * returns the community level of the framework for the named plot
	 */
	public String getCommunityLevel(String plotName)
	{
		return this.getCommunityElement(plotName, "commlevel");
	}
	
    
	/**
	 * returns the community framework for the named plot
	 */
	public String getCommunityFramework(String plotName)
	{
		return this.getCommunityElement(plotName, "commframework");
	}
	
	
	//retuns the topo position
	public String getTopoPosition(String plotName)
	{ 
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select  topoposition "
			+" from PLOT where PLOT_ID = " + plotName );
							
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
		// All other types of exceptions
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	//returns the hydrologic regime
	public String getHydrologicRegime(String plotName)
	{ 
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select  hydrologicregime "
			+" from observation where  PLOT_ID = " + plotName );
							
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
		// All other types of exceptions
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	/**
	 * method that retuns the latitude for an input plotid
	 * @param plotName -- the VegBank PLOTID
	 * @return geology -- the validated surface geology of that plot
	 */
	public String getSurfGeo(String plotName)
	{
		return null;
	}
	
	//returns the country
	public String getCountry(String plotName)
	{ 
		return this.getPlotElement(plotName, "country");
	}
	
	//returns the slope aspect
	public String getSlopeAspect(String plotName)
	{
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select  slopeaspect "
			+" from PLOT where PLOT_ID = " + plotName );
							
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
		// All other types of exceptions
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	//returns the slope gradient
	public String getSlopeGradient(String plotName)
	{
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select  slopegradient "
			+" from PLOT where PLOT_ID = " + plotName );
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (java.lang.Exception ex) 
		{   
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	//returns the size of the stand -- extensive etc..
	public String getStandSize(String plotName)
	{
		return this.getPlotElement( plotName, "standSize");
	}
	
	//returns the location as described by the author
	public String getAuthorLocation(String plotName)
	{
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select authorlocation "
			+" from PLOT where PLOT_ID = " + plotName );
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (java.lang.Exception ex) 
		{
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	//returns the landForm
	public String getLandForm(String plotName)
	{
		return this.getPlotElement( plotName, "landform");
	}
	
	//retuns the elevation
	public String getElevation(String plotName)
	{
		return this.getPlotElement(plotName, "elevation");
	}
	
	//returns the elevation accuracy
	public String getElevationAccuracy(String plotName)
	{
		return this.getPlotElement(plotName, "elevationAccuracy");
  }
	
	//return the confidentiality reason -- not null
	public String	getConfidentialityReason(String plotName)
	{  
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select confidentialityReason "
			+" from PLOT where PLOT_ID = " + plotName );
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (java.lang.Exception ex) 
		{
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	//return the confidentiality status -- not null 
	public String getConfidentialityStatus(String plotName)
	{
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select confidentialitystatus "
			+" from PLOT where PLOT_ID = " + plotName );
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (java.lang.Exception ex) 
		{
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	/**
	 * method that retuns the unique names for a plotID
	 * @param plotName -- the VegBank PLOTID
	 * @return v -- a vector containg the unique strata for that plot
	 *
	 */
	public Vector getUniqueStrataNames(String plotName)
	{
		Vector v = new Vector();
		Statement stmt = null;
		try 
		{
			Vector observationIDs = this.getObservationIDs( plotName );
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select distinct STRATUMNAME "
			+" from STRATUM where OBSERVATION_ID = " + 
			(String)observationIDs.elementAt(0) );
							
			while (rs.next()) 
			{
				String s = rs.getString(1);
				v.addElement(s);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
			// All other types of exceptions
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(v);
	}
	
	
	/**
	 * method that returns the observation ids that are children
	 * of a plot id
	 * @param plotName -- the VegBank plotId
	 * @return v -- a vector containg all the observtaions for that specific
	 *		plot id provided as input
	 */
	 private  Vector getObservationIDs(String plotName)
	{
		Vector v = new Vector();
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select OBSERVATION_ID from OBSERVATION "
			+"where PLOT_ID = " + plotName );
							
			while (rs.next()) 
			{
				String s = rs.getString(1);
				v.addElement(s);
			}
			
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
			// All other types of exceptions
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		return(v);
	}
	
	
	
	/**
	 * method that takes a plot code and populates the public varaibles with
	 * data related to that specific plot
	 *
	 * @param plotName -- the name of a plot for which to retrieve data for
	 */
	 public void getPlotData(String plotName)
	 {
		 //there is only one plot here  so this method should be blank
	 }
	 
	
		
		/**
		 * method that returns a vector with the unique plant 
		 * taxa names for a given plot -- this method is defined
		 * in the plugin interface
		 */
		 public Vector getPlantTaxaNames(String plotName)
		 {
				Vector v = new Vector();
				Statement stmt = null;
				try 
				{
					Vector observationIDs = this.getObservationIDs( plotName );
					stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery("select distinct CHEATPLANTNAME "
					+" from TAXONOBSERVATION where OBSERVATION_ID = " + 
					(String)observationIDs.elementAt(0) );
							
					while (rs.next()) 
					{
						String s = rs.getString(1);
						v.addElement(s);
					}
				}
				catch (SQLException ex) 
				{
					this.handleSQLException( ex );
				}
				catch (Exception ex) 
				{   
					// All other types of exceptions
					System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
					ex.printStackTrace();
				}
				return(v);
		 }
		 


	/**
	 * method that returns the strata in which the input plant
	 * exists within the input plot
	 * @param plantName 
	 * @param plotName
	 */
	 public Vector getTaxaStrataExistence(String plantName, String plotName)
	 {
		 StringBuffer sb = new StringBuffer();
		 Vector v = new Vector();
		try
		{
			sb.append( " SELECT distinct(STRATUMTYPE) FROM PLOTSPECIESSUM where  " );
			sb.append(" PLOT_ID = " + plotName +" and AUTHORNAMEID LIKE '"+plantName+"'");
			System.out.println("sql: " + sb.toString() );
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery( sb.toString() );
			while (rs.next()) 
			{
				String buf = rs.getString(1);
				v.addElement(buf);		
			}
		}
		catch (java.lang.Exception ex) 
		{
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
		}
		 return(v);
	 }
	 
	 
	 /**
	 * method to return the cover for a given strata for a given 
	 * plot
	 */
	 public String getStrataCover(String plotName, String strataName)
	 {
		 String s = null;
			Statement stmt = null;
			try 
			{
				stmt = con.createStatement();
				StringBuffer sb = new StringBuffer();
				sb.append("select STRATUMCOVER from STRATUM where STRATUMNAME like '"+strataName+"' and OBSERVATION_ID = (");
				sb.append(" select OBSERVATION_ID from OBSERVATION where PLOT_ID = "+plotName+")");
				ResultSet rs = stmt.executeQuery( sb.toString() );			
				while (rs.next()) 
				{
					s = rs.getString(1);
				}
			}
			catch (java.lang.Exception ex) 
			{   
				System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
				ex.printStackTrace();
			}
			return(s);
	 }
	 
	 /** 
	  * method the returns the base height of a strata based on the 
		* name of that starta and the plot for which that strata is included
		*/
		public String getStrataBase(String plotName, String strataName)
		{
			String s = null;
			Statement stmt = null;
			try 
			{
				stmt = con.createStatement();
				StringBuffer sb = new StringBuffer();
				sb.append("select STRATUMBASE from STRATUM where STRATUMNAME like '"+strataName+"' and OBSERVATION_ID = (");
				sb.append(" select OBSERVATION_ID from OBSERVATION where PLOT_ID = "+plotName+")");
				ResultSet rs = stmt.executeQuery( sb.toString() );			
				while (rs.next()) 
				{
					s = rs.getString(1);
				}
			}
			catch (java.lang.Exception ex) 
			{   
				System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
				ex.printStackTrace();
			}
			return(s);
		}
		
		/**
		 * method that returns the upper height of a starata based on 
		 * the name of the strata and the plot inwhich the strata exists
		 */
		public String getStrataHeight(String plotName, String strataName)
		{
			String s = null;
			Statement stmt = null;
			try 
			{
				stmt = con.createStatement();
				StringBuffer sb = new StringBuffer();
				sb.append("select STRATUMHEIGHT from STRATUM where STRATUMNAME like '"+strataName+"' and OBSERVATION_ID = (");
				sb.append(" select OBSERVATION_ID from OBSERVATION where PLOT_ID = "+plotName+")");
				ResultSet rs = stmt.executeQuery( sb.toString() );			
				while (rs.next()) 
				{
					s = rs.getString(1);
				}
			}
			catch (java.lang.Exception ex) 
			{   
				System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
				ex.printStackTrace();
			}
			return(s);
		}
		
	 
	 /**
	 * method that returns the strata cover for a given plant, strata, and 
	 * plot
	 */
	 public String getTaxaStrataCover(String plantName, String plotName, String stratum)
	 {
		 String cover = "";
		 StringBuffer sb = new StringBuffer();
		 try
		 {
        // FIXME: Get data from normalized tables ?
			 	// SELECT THE COVER VALUE DIRECTLY FROM THE DENORMALIZED TABLES
				sb.append("SELECT PERCENTCOVER from PLOTSPECIESSUM WHERE ");
				sb.append(" PLOT_ID = "+plotName+" and STRATUMTYPE like '"+stratum+"' and ");
				sb.append(" AUTHORNAMEID like '"+plantName+"'");
				//System.out.println("sql: " + sb.toString() );
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery( sb.toString() );
				while (rs.next()) 
				{
					cover = rs.getString(1);
				}
		 }
		 catch (java.lang.Exception ex) 
			{   
				System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
				ex.printStackTrace();
			}
			return(cover);
	 }
	 
	/**
	 * method that retuns the cummulative cover accoss all strata for a given 
	 * plant taxa in a given plot
	 */
	public String getCummulativeStrataCover( String plantName, String plotName )
	{
		System.out.println("VegBankDataSourcePlugin > looking cum. strata cover for plot: " + plotName);
		String s = null;
		Statement stmt = null;
		StringBuffer sb = new StringBuffer();
		try 
		{
			stmt = con.createStatement();
			sb.append("select TAXONCOVER from TAXONOBSERVATION where OBSERVATION_ID = ");
			sb.append(" (select OBSERVATION_ID from OBSERVATION where PLOT_ID = "+plotName+" ) ");
			sb.append(" and CHEATPLANTNAME like '"+plantName+"' ");
			ResultSet rs = stmt.executeQuery( sb.toString() );
			while (rs.next()) 
			{
				 s = rs.getString(1);
				 // IF WE GET A NULL VALUE THEN TAKE THE MAX OF THE SPECIES
				 if ( s == null )
				 {
					 sb = new StringBuffer();
					 sb.append(" select max(PERCENTCOVER) from PLOTSPECIESSUM where ");
					 sb.append(" AUTHORNAMEID like '"+plantName+"' and PLOT_ID = "+ plotName  );
					 ResultSet rs1 = stmt.executeQuery( sb.toString() );
					 while (rs1.next()) 
					 {
						 s = rs1.getString(1);
						 if (s == null )
						 {
							 s = "";
						 }
					 }
				 }
			}
			stmt.close();
			rs.close();
		}
		catch (java.lang.Exception ex) 
		{
			System.out.println("VegBankDataSourcePlugin > Exception: " + ex );
			ex.printStackTrace();
			System.out.println("VegBankDataSource > query which failed: " + sb.toString() );
		}
		return(s);
	}

	
	
	/**
	 * method for handling the sql exceptions thrown 
	 */
	private void handleSQLException(SQLException ex)
	{
		try
		{
			while (ex != null)
			{
				System.out.println ("VegBankDataSourcePlugin > ErrorCode: " + ex.getErrorCode () );
				System.out.println ("VegBankDataSourcePlugin > SQLState:  " + ex.getSQLState () );
				System.out.println ("VegBankDataSourcePlugin > Message:   " + ex.getMessage () );
				ex.printStackTrace();
				ex = ex.getNextException();
			}
		}
		catch (java.lang.Exception x) 
		{   
			// All other types of exceptions
			System.out.println("VegBankDataSourcePlugin > Exception: " + x + "<BR>");
			x.printStackTrace();
		}
	}
	
	
	
/**
 * main method for testing --
 */
	public static void main(String[] args)
	{
		if (args.length == 1) 
		{
			String plotName = args[0];
		//	VegBankDataSourcePlugin db = new VegBankDataSourcePlugin("postgresql");
		//	System.out.println(" VegBankDataSourcePlugin > getting info for: " + plotName );
		}
		else
		{
		//	VegBankDataSourcePlugin db = new VegBankDataSourcePlugin("postgresql");
		//	System.out.println( db.getPlotCodes().toString() );
		//	System.out.println( db.getPlotIDs().toString() );
		}
	}

	/**
	 * @see PlotDataSourceInterface#getCommunityInspection(java.lang.String)
	 */
	public String getCommunityInspection(String plotName)
	{
		return this.getCommunityElement(plotName, "inspection");
	}

	/**
	 * @see PlotDataSourceInterface#getCommunityPublication(java.lang.String)
	 */
	public String getCommunityPublication(String plotName)
	{
		return null;
	}

	/**
	 * @see PlotDataSourceInterface#getCommunityStartDate(java.lang.String)
	 */
	public String getCommunityStartDate(String plotName)
	{
		return this.getCommunityElement(plotName, "classstartdate");
	}

	/**
	 * @see PlotDataSourceInterface#getCommunityStopDate(java.lang.String)
	 */
	public String getCommunityStopDate(String plotName)
	{
		return this.getCommunityElement(plotName, "classstopdate");
	}

	/**
	 * @see PlotDataSourceInterface#getCommunityExpertSystem(java.lang.String)
	 */
	public String getCommunityExpertSystem(String plotName)
	{
		return this.getCommunityElement(plotName, "expertsystem");		
	}

	/**
	 * @see PlotDataSourceInterface#getCommunityMultiVariateAnalysis(java.lang.String)
	 */
	public String getCommunityMultiVariateAnalysis(String plotName)
	{
		return this.getCommunityElement(plotName, "multivariateanalysis");
	}

	/**
	 * @see PlotDataSourceInterface#getCommunityTableAnalysis(java.lang.String)
	 */
	public String getCommunityTableAnalysis(String plotName)
	{
		return this.getCommunityElement(plotName, "tableanalysis");
	}

	/* (non-Javadoc)
	 * @see org.vegbank.plots.datasource.PlotDataSourceInterface#getRockType(java.lang.String)
	 */
	public String getRockType(String plotName)
	{
		String s = this.getPlotElement(plotName, "rocktype");
		return(s);
	}

	/* (non-Javadoc)
	 * @see org.vegbank.plots.datasource.PlotDataSourceInterface#getSurficialDeposits(java.lang.String)
	 */
	public String getSurficialDeposits(String plotName)
	{
		String s = this.getPlotElement(plotName, "surficialdeposits");
		return(s);
	}

	/* (non-Javadoc)
	 * @see org.vegbank.plots.datasource.PlotDataSourceInterface#getStratumMethodName(java.lang.String)
	 */
	public String getStratumMethodName(String plotName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.vegbank.plots.datasource.PlotDataSourceInterface#getCoverMethodName(java.lang.String)
	 */
	public String getCoverMethodName(String plotName)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
