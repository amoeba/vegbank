package servlet.stylesheet;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.math.*;
import java.net.URL;
import java.sql.*;

import org.apache.tools.mail.MailMessage;
import servlet.util.ServletUtility;
import xmlresource.utils.transformXML;
import servlet.util.GetURL;



/**
 * servlet class to allow a user to creat a default 
 * style sheet to be stored in the profile
 *
 */

public class StyleSheetGenerator extends HttpServlet 
{
	
	//this is the tmp file that will be written
	private String fileName = "/usr/local/devtools/jakarta-tomcat/webapps/framework/WEB-INF/lib/test.xsl";
	private ServletUtility util = new ServletUtility();
	private String userEmail = null;
	private transformXML transformer = new transformXML();
	private GetURL gurl = new GetURL();
	
	/**
	 * constructor method
	 */
	public StyleSheetGenerator()
	{
		System.out.println("init: StyleSheetGenerator");
	}
	
	
	/** Handle "POST" method requests from HTTP clients */
	public void doPost(HttpServletRequest request,
		HttpServletResponse response)
 	 throws IOException, ServletException 	
		{
			doGet(request, response);
		}

		
		
	/** Handle "GET" method requests from HTTP clients */ 
	public void doGet(HttpServletRequest request, 
		HttpServletResponse response)
		throws IOException, ServletException  
		{
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			try 
			{
				//enumeration is needed for the attributes
				Enumeration enum =request.getParameterNames();
				Hashtable params = this.parameterHash( request );
				
				
				//THE INPUT PARAMETERS
				System.out.println("StyleSheetGenerator > INPUT PARAMS: "
				+  params.toString() );
				
				//first get the cookie value and action etc
				this.userEmail = this.getCookieValue(request);
				System.out.println("StyleSheetGenerator > user: " + this.userEmail );
				String action = getAction(params);
				System.out.println("StyleSheetGenerator > action: " + action );
				
				//figure out what should be done based on the action
				if ( action != null )
				{
					if ( action.equals("deletedefault") )
					{
						System.out.println("StyleSheetGenerator > deleting default style " );
						System.out.println("StyleSheetGenerator > results: " + this.deleteSavedStyleSheets() );
						
					}
				}
				//IF NO ACTION THEN ASSUME THE USER WANTS TO CREAT AND REGISTER
				// A NEW STYLESHEET
				else
				{
					//the temp file name
					StringBuffer fileContents = new StringBuffer();
				
					//print the xslt file in order from left to right
					fileContents.append( getHeader().toString() );
					fileContents.append( this.getIdentificationAttributes(params).toString()  );
					//fileContents.append( getBody(params).toString() );
					fileContents.append( this.getSiteAttributes(params).toString() );
					fileContents.append( this.getObservationAttributes(params).toString() );
					fileContents.append( this.getTaxaAttributes(params).toString() );
					fileContents.append( getFooter().toString() );
					printFile( fileContents );
				
					//register the document
					this.registerDocument(this.fileName, this.userEmail, "stylesheet");
				
					//SHOW THE USER THE STYLE SHEET THAT THE GENERATED 
					out.println( this.showTransformedDataSet() );
				}
			}
		catch( Exception e ) 
		{
			System.out.println("Exception:  "
			+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	/**
	 * method that returns the action parameter value passed
	 * to the servlet from the client using as input a hashtable
	 * with all the parameters
	 *
	 * @param params -- the hashtable containing all the parameters
	 *
	 */
	 private String getAction(Hashtable params)
	 {
			String s = null;
			try
			{
				if (params.containsKey("action") )
				{
					s = (String)params.get("action");
				}
				else
				{
					System.out.println("StyleSheetGenerator > no action passed" );
				}
			}
			catch( Exception e ) 
			{
				System.out.println("Exception:  "
				+e.getMessage());
				e.printStackTrace();
			}
			return(s);
	 }
	 
	
	/**
	 * method allowing the user to delete the current
	 * style sheet
	 */
	 private boolean deleteSavedStyleSheets()
	 { 
		 try
		 {
				String styleSheet = this.getUserDefaultStyle(this.userEmail);
				System.out.println("StyleSheetGenerator > stylesheet file name: " + styleSheet );
				
				String htmlResults = null;
				
				//set up for the file deletion through the data exchange servlet
				StringBuffer sb = new StringBuffer();
      	sb.append("?action=deletefiletype&username="+this.userEmail+"&filetype=stylesheet");
			
      	//connect to the dataExchaneServlet
				String uri = "http://vegbank.nceas.ucsb.edu/framework/servlet/dataexchange"+sb.toString().trim();
				System.out.println("StyleSheetGenerator > sent to servlet: " + uri);
      	int port=80;
      	String requestType="POST";
      	htmlResults = gurl.requestURL(uri);
				
				  //public void doPost(HttpServletRequest req, HttpServletResponse res)
					//else if (action.equals("deletefile") && ( req.getParameter("username") != null) )
				
		 }
		 catch(Exception e )
		 {
			 System.out.println("Exception: " + e.getMessage() );
			 e.printStackTrace();
		 }
		 return(true);
	 }
	
	
	/**
	 * method to request the users default style sheet from the 
	 * profile database
	 */
	 private String getUserDefaultStyle(String userName)
	 {
			String htmlResults = null;
    	try
    	{
      	//create the parameter string to be passed to the DataRequestServlet -- 
				//this first part has the data request type stuff
      	StringBuffer sb = new StringBuffer();
      	sb.append("?action=userdefaultstyle&username="+userName);
			
      	//connect to the dataExchaneServlet
				String uri = "http://vegbank.nceas.ucsb.edu/framework/servlet/dataexchange"+sb.toString().trim();
				System.out.println("StyleSheetGenerator > sent to servlet: " + uri);
      	int port=80;
      	String requestType="POST";
      	htmlResults = gurl.requestURL(uri);
    	}
    	catch( Exception e )
    	{
     	 System.out.println("** failed :  "
     	 +e.getMessage());
    	}
    	return(htmlResults);
	 }
	
	
	
	
	/**
	 * method that registers the stylesheet document with 
	 * the datafile database as the user's default stylesheet
	 * for viewing database results through
	 *
	 * @param file -- the file to register 
	 * @param user -- the email address of the user
	 * @param fileType -- the type of file specified in the database, which 
	 *	will be used for querying
	 */
	 private void registerDocument(String file, String user, String fileType )
	 {
		 try
		 {
		 	System.out.println("StyleSheetGenerator > registering the stylesheet document ###");
		 	util.uploadFileDataExcahgeServlet(file, user, fileType);
		 }
		 catch(Exception e )
		 {
			 System.out.println("Exception: " + e.getMessage() );
			 e.printStackTrace();
		 }
		}
	
	/**
	 * method that returns the cookie value associated with the 
	 * current browser
	 */
	private String getCookieValue(HttpServletRequest req)
	{
		
		//get the cookies - if there are any
		String cookieName = null;
		String cookieValue = null;
		try
		{
			Cookie[] cookies = req.getCookies();
			//determine if the requested page should be shown
    	if (cookies.length > 0) 
			{
				for (int i = 0; i < cookies.length; i++) 
				{
      		Cookie cookie = cookies[i];
					//out.print("Cookie Name: " +cookie.getName()  + "<br>");
       	 cookieName=cookie.getName();
					//out.println("  Cookie Value: " + cookie.getValue() +"<br><br>");
					cookieValue=cookie.getValue();
					System.out.println("StyleSheetGenerator > cleint passing the cookie name: "+cookieName+" value: "
					+cookieValue);
				}
  		}
		}
		catch(Exception e)
		{
			System.out.println("Exception: " + e.getMessage() );
			e.printStackTrace();
		}
		return(cookieValue);
	}
	
	
	/**
	 * method that returns the head for the style  sheet
	 */
	private StringBuffer getHeader()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("<?xml version=\"1.0\"?> \n");
		sb.append("<xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\"> \n");
		sb.append("<xsl:output method=\"html\"/> \n");
		sb.append("<xsl:template match=\"/vegPlotPackage\"> \n");

		sb.append("<html> \n");
		sb.append("<head> \n");
		sb.append("		<title> default style for "+ this.userEmail +"</title> \n");
		sb.append("</head> \n");
		sb.append("<body bgcolor=\"FFFFFF\"> \n");

		sb.append("<br></br> \n");
		sb.append("<xsl:number value=\"count(project/plot)\" /> PLOTS IN RESULT SET \n");
	
		// set up the form which is required by netscape 4.x browsers 
		sb.append("<form name=\"myform\" action=\"viewData\" method=\"post\">");


		sb.append("<!-- set up a table --> \n");
		sb.append("<table width=\"100%\"> \n");

		//correct the widths for the 4 collumns of data
    sb.append("       <tr colspan=\"1\" bgcolor=\"CCCCFF\" align=\"left\" valign=\"top\"> \n");
    sb.append("      		<th class=\"tablehead\">Identification</th>   \n");
    sb.append("      		<th class=\"tablehead\">Site</th>  \n");
    sb.append("      		<th class=\"tablehead\">Observation</th>   \n");
		sb.append("      		<th class=\"tablehead\">Taxa</th>    \n");
    sb.append("       </tr> \n");

		sb.append("<!-- Header and row colors --> \n");
  	sb.append("      <xsl:variable name=\"evenRowColor\">#C0D3E7</xsl:variable> \n");
	  sb.append("      <xsl:variable name=\"oddRowColor\">#FFFFFF</xsl:variable> \n");
	
	   
		sb.append("	<xsl:for-each select=\"project/plot\"> \n");
		sb.append("	<xsl:sort select=\"authorPlotCode\"/> \n");
	
		sb.append("	<tr valign=\"top\"> \n");
		
		return(sb);
	}
	
	
	/**
	 * method that returns the body of the stylesheet
	 * @param params -- the hashtable that contains the common name and the
	 * attribute name
	 */
	private StringBuffer getBody(Hashtable params)
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("    <!--if even row --> \n");
		sb.append("    <xsl:if test=\"position() mod 2 = 1\"> \n");
		
		sb.append("		<td colspan=\"1\" bgcolor=\"{$evenRowColor}\" align=\"left\" valign=\"top\"> \n ");
		
		sb.append("	<!-- Site data here --> \n ");
		sb.append("	<xsl:variable name=\"PLOT\"> \n ");
  	sb.append("		<xsl:value-of select=\"authorPlotCode\"/> \n");
		sb.append("	</xsl:variable> \n");
		
		sb.append("	<xsl:variable name=\"PLOTID\"> \n");
  	sb.append("		<xsl:value-of select=\"plotId\"/> \n");
		sb.append("	</xsl:variable> \n");

            
		sb.append("	<xsl:number value=\"position()\"/> \n");
		
		
		//get the attribute xsl lines
		sb.append( getSiteAttributes(params).toString()  );

		//replace this with a method that returns the lines for processing 
		// the observational data
		sb.append("	<!--Observation data hera --> \n ");
	  sb.append("   <td colspan=\"1\"  bgcolor=\"{$evenRowColor}\" align=\"left\" valign=\"top\"> \n");
    sb.append("   	<a> <b>observation code: </b><br></br> <font color=\"FF0066\"> <xsl:value-of select=\"plotObservation/authorObsCode\"/> </font> <br> </br></a> \n");
		sb.append("			<b>community Name:</b> <br></br> <font color=\"FF0066\"> <xsl:value-of select=\"plotObservation/communityAssignment/communityName\"/> </font>\n");
    sb.append("   </td> ");
	 

	 sb.append("<!-- Species data here --> \n");
	 sb.append("   	<td colspan=\"1\" bgcolor=\"{$evenRowColor}\" align=\"left\" valign =\"top\">  \n");
   sb.append("     <i><FONT color=\"green\" SIZE=\"-1\" FACE=\"arial\"> \n");
	 sb.append("			<b>Top species:</b> <xsl:for-each select=\"plotObservation/taxonObservation\"> \n");
	 sb.append("    	 <xsl:value-of select=\"authNameId\"/>; </xsl:for-each> \n");
	 sb.append("				</FONT></i> \n"); 
   sb.append("    	  </td> \n");
	 sb.append("				</xsl:if> \n");
	 
	 
	 //IF ODD ROW
	sb.append("    <!--if odd row --> \n");
	sb.append("    <xsl:if test=\"position() mod 2 = 0\"> \n");
		
	sb.append("		<td colspan=\"1\" bgcolor=\"{$oddRowColor}\" align=\"left\" valign=\"top\"> \n ");
	
		sb.append("	<!-- Site data here --> \n ");
		sb.append("	<xsl:variable name=\"PLOT\"> \n ");
  	sb.append("		<xsl:value-of select=\"authorPlotCode\"/> \n");
		sb.append("	</xsl:variable> \n");
		
		sb.append("	<xsl:variable name=\"PLOTID\"> \n");
  	sb.append("		<xsl:value-of select=\"plotId\"/> \n");
		sb.append("	</xsl:variable> \n");

            
		sb.append("	<xsl:number value=\"position()\"/> \n");
		
		
		//get the attribute xsl lines
		sb.append( getSiteAttributes(params).toString()  );
	
		sb.append("	<!--Observation data hera --> \n ");
	  sb.append("   <td colspan=\"1\"  bgcolor=\"{$oddRowColor}\" align=\"left\" valign=\"top\"> \n");
    sb.append("   	<a> <b>observation code: </b><br></br> <font color=\"FF0066\"> <xsl:value-of select=\"plotObservation/authorObsCode\"/> </font> <br> </br></a> \n");
		sb.append("			<b>community Name:</b> <br></br> <font color=\"FF0066\"> <xsl:value-of select=\"plotObservation/communityAssignment/communityName\"/> </font>\n");
    sb.append("   </td> ");
	 

	 	sb.append("<!-- Species data here --> \n");
	 	sb.append("   	<td colspan=\"1\" bgcolor=\"{$oddRowColor}\" align=\"left\" valign =\"top\">  \n");
   	sb.append("     <i><FONT color=\"green\" SIZE=\"-1\" FACE=\"arial\"> \n");
	 	sb.append("			<b>Top species:</b> <xsl:for-each select=\"plotObservation/taxonObservation\"> \n");
	 	sb.append("    	 <xsl:value-of select=\"authNameId\"/>; </xsl:for-each> \n");
	 	sb.append("				</FONT></i> \n"); 
   	sb.append("    	  </td> \n");
	 	sb.append("				</xsl:if> \n");
	 
	 
		return(sb);
	}
	
	
	
	/**
	 * this method generated the site attributes collumn of the stylesheet
	 */
	private StringBuffer getObservationAttributes( Hashtable params )
	{
		StringBuffer sb = new StringBuffer();
		
		//System.out.println("StyleSheetGenerator > site params: "+params.toString() );
		//get all the keys
	 	Enumeration paramlist = params.keys();
 
		//START THE COLUMN
		sb.append("<!--if even row --> \n");
		sb.append("<xsl:if test=\"position() mod 2 = 1\"> \n");
		sb.append("<td width=\"20%\" colspan=\"1\" bgcolor=\"{$evenRowColor}\" align=\"left\" valign=\"top\"> \n ");
		sb.append(" Plot Code: ");
		sb.append(" <xsl:value-of select=\"authorPlotCode\"/> <br> </br> \n");
		sb.append(" Project Name: ");
		sb.append("<xsl:value-of select=\"../projectName\"/> <br> </br>  \n");
		
 		while (paramlist.hasMoreElements()) 
 		{
			String element = (String)paramlist.nextElement();
   		String value  = (String)params.get(element);
			if ( value.trim().startsWith("observation") )
			{
				
				sb.append(" "+element+": ");
				sb.append("<xsl:value-of select=\"observation/"+element+"\"/> <br> </br> \n");
			}
		}
		
		
		//END THE COLUMN
		sb.append("	</td> \n");
		sb.append("	</xsl:if> \n");
		return(sb);
	}
	
	
	
	/**
	 * this method generated the site attributes collumn of the stylesheet
	 */
	private StringBuffer getIdentificationAttributes( Hashtable params )
	{
		StringBuffer sb = new StringBuffer();
		
		//System.out.println("StyleSheetGenerator > site params: "+params.toString() );
		//get all the keys
	 	Enumeration paramlist = params.keys();
		
		//SET UP THE VARIABLE THAT WILL BE SUED IN THIS FORM
		sb.append("	<xsl:variable name=\"PLOT\"> \n ");
  	sb.append("		<xsl:value-of select=\"authorPlotCode\"/> \n");
		sb.append("	</xsl:variable> \n");
		
		sb.append("	<xsl:variable name=\"PLOTID\"> \n");
  	sb.append("		<xsl:value-of select=\"plotId\"/> \n");
		sb.append("	</xsl:variable> \n");
		
		
 
		//START THE COLUMN
		sb.append("<!--if even row --> \n");
		sb.append("<xsl:if test=\"position() mod 2 = 1\"> \n");
		sb.append("<td width=\"20%\" colspan=\"1\" bgcolor=\"{$evenRowColor}\" align=\"left\" valign=\"top\"> \n ");
		
		//PUT IN THE VARAIBLES USED TO BE PASSED TO THE DOWNLOAD SERVLET
		sb.append("<input name=\"plotName\" type=\"checkbox\" value=\"{$PLOTID}\" checked=\"yes\">download</input> ");
		sb.append("	<xsl:number value=\"position()\"/> ");
		
		sb.append(" Plot Code: ");
		sb.append(" <xsl:value-of select=\"authorPlotCode\"/> <br> </br> \n");
		sb.append(" Project Name: ");
		sb.append("<xsl:value-of select=\"../projectName\"/> <br> </br>  \n");
		
		
		
		
 		while (paramlist.hasMoreElements()) 
 		{
			String element = (String)paramlist.nextElement();
   		String value  = (String)params.get(element);
			if ( value.trim().startsWith("identification") )
			{
				
				sb.append(" "+element+": ");
				sb.append("<xsl:value-of select=\"../"+element+"\"/> <br> </br> \n");
			}
		}
		
		
		//END THE COLUMN
		sb.append("	</td> \n");
		sb.append("	</xsl:if> \n");
		return(sb);
	}
	
	
		
	/**
	 * this method generated the site attributes collumn of the stylesheet
	 */
	private StringBuffer getTaxaAttributes( Hashtable params )
	{
		StringBuffer sb = new StringBuffer();
		
		//System.out.println("StyleSheetGenerator > site params: "+params.toString() );
		//get all the keys
	 	Enumeration paramlist = params.keys();
 
		//START THE COLUMN
		sb.append("<!--if even row --> \n");
		sb.append("<xsl:if test=\"position() mod 2 = 1\"> \n");
		sb.append("<td width=\"20%\" colspan=\"1\" bgcolor=\"{$evenRowColor}\" align=\"left\" valign=\"top\"> \n ");
		sb.append(" Plot Code: ");
		sb.append(" <xsl:value-of select=\"authorPlotCode\"/> <br> </br> \n");
		sb.append(" Project Name: ");
		sb.append("<xsl:value-of select=\"../projectName\"/> <br> </br>  \n");
		
 		while (paramlist.hasMoreElements()) 
 		{
			String element = (String)paramlist.nextElement();
   		String value  = (String)params.get(element);
			if ( value.trim().startsWith("taxonomy") )
			{
				
				sb.append(" "+element+": ");
				//sb.append("<xsl:for-each select=\"observation/taxonObservation/"+element+"\"/> <br> </br> \n");
				
				sb.append("	 <xsl:for-each select=\"observation/taxonObservation\"> \n");
	 			sb.append("  <xsl:value-of select=\""+element+"\"/>; </xsl:for-each> \n");
			}
		}
		
		
		//END THE COLUMN
		sb.append("	</td> \n");
		sb.append("	</xsl:if> \n");
		return(sb);
	}
	
	
	
	/**
	 * this method generated the site attributes collumn of the stylesheet
	 */
	private StringBuffer getSiteAttributes( Hashtable params )
	{
		StringBuffer sb = new StringBuffer();
		
		//System.out.println("StyleSheetGenerator > site params: "+params.toString() );
		//get all the keys
	 	Enumeration paramlist = params.keys();
 
		//START THE COLUMN
		sb.append("<!--if even row --> \n");
		sb.append("<xsl:if test=\"position() mod 2 = 1\"> \n");
		sb.append("<td width=\"20%\" colspan=\"1\" bgcolor=\"{$evenRowColor}\" align=\"left\" valign=\"top\"> \n ");
		sb.append(" Plot Code: ");
		sb.append(" <xsl:value-of select=\"authorPlotCode\"/> <br> </br> \n");
		sb.append(" Project Name: ");
		sb.append("<xsl:value-of select=\"../projectName\"/> <br> </br>  \n");
		
 		while (paramlist.hasMoreElements()) 
 		{
			String element = (String)paramlist.nextElement();
   		String value  = (String)params.get(element);
			if ( value.trim().startsWith("site") )
			{
				
				sb.append(" "+element+": ");
				sb.append("<xsl:value-of select=\" "+element+"\"/> <br> </br> \n");
			}
		}
		
		
		//END THE COLUMN
		sb.append("	</td> \n");
		sb.append("	</xsl:if> \n");
		return(sb);
	}
	
	/**
	* the method that returns the footer to the style sheet
	*/
	private StringBuffer getFooter()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("	</tr>  \n "); 
		sb.append("</xsl:for-each> \n ");  
		sb.append("</table> \n");
		
		//THE BUTTON TO DOWNLOAD THE DATA
		sb.append(" <input type=\"submit\" name=\"downLoadAction\" value=\"start downLoad\" /> "); 
		sb.append("</form> \n"); 
		sb.append("</body> \n"); 
		sb.append("</html> \n"); 
		sb.append("</xsl:template> \n"); 
		sb.append("</xsl:stylesheet> \n"); 
		return(sb);
	}
	
	
	/**
	 * this is a method that prints the stylesheet, whose contents are stored
	 * in a string buffer
	 *
	 */
	private void printFile(StringBuffer sb)
	{
	  try
     {
      PrintWriter out = new PrintWriter(new FileWriter(this.fileName));
     		out.println(sb.toString() );
     		out.close(); 
				System.out.println("StyleSheetGenerator > done printing to the file system");
		 }
     catch(Exception e)
     {
				System.out.println( "Caught Exception: "+ e.getMessage() ); 
     }
		}	 
	 

	 /**
	 * method to stick the parameters from the client 
	 * into a hashtable - where each attribute is stored 
	 * as a string in the hashtable -- maybe change this in
	 * the future to a vector for each attribute so that multiple
	 * attribute vales can be stored
	 */
	private Hashtable parameterHash(HttpServletRequest request) 
	{
		Hashtable params = new Hashtable();
		try 
		{
			Enumeration enum =request.getParameterNames();
 			while (enum.hasMoreElements()) 
			{
				String name = (String) enum.nextElement();
				String values[] = request.getParameterValues(name);
				if (values != null) 
				{
					for (int i=0; i<values.length; i++) 
					{
						params.put(name,values[i]);
					}
				}
 			}
		}
		catch( Exception e ) 
		{
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return(params);
	}
	
	/**
	 * method that shows the user what the data transformed with the stylesheet
	 * will look like -- this is also for testing that the generated stylesheet
	 * is valid and that it does not throw any exceptions
	 */
	 	private String showTransformedDataSet()
		{
			String s = null;
			try
			{
				
				String teststyle = "/usr/local/devtools/jakarta-tomcat/webapps/framework/WEB-INF/lib/test.xsl";
				transformer.getTransformed("/usr/local/devtools/jakarta-tomcat/webapps/framework/WEB-INF/lib/test-summary.xml", teststyle);
				StringWriter transformedData= transformer.outTransformedData;
				
				s= transformedData.toString();
				
			}
			catch (Exception e)
			{
				System.out.println("Exception: " + e.getMessage() );
			}
			return(s);
		}
	
}
