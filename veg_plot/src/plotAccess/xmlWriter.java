import java.lang.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.sql.*;



/**
 * This class will write out an xml file containing the results from database
 * queries.  These xml files will be consistent with either vegPlot.dtd, or the
 * community dtd file or the plant taxonomy dtd file
 *
 *
 */

public class  xmlWriter
{

private StringBuffer printString = new StringBuffer(); //this is the print string

/**
 * Method to print the summary information returned about one or a group of
 * vegetation communities.  Currently this method is to take as input a vector
 * containing the information and the output file and then print such data as
 * the name, nvc level, abiCode, and parentName of the returned community(s)
 *
 * @param communitySummary - the vector that contains the information 
 * @param outFile - the output file 
 */
public void writeCommunitySummary(Vector communitySummary, String outFile)
{
try {
		
//set up the output query file called query.xml	using append mode 
PrintStream out  = new PrintStream(new FileOutputStream(outFile, false));

String commName=null;
String commDesc=null;
String abiCode=null;
String parentCommName=null;
String nullValue = "-999.25";

for (int i=0;i<communitySummary.size(); i++) {

//tokenize each line of the vector
StringTokenizer t = new StringTokenizer(communitySummary.elementAt(i).toString().trim(), "|");
commName=t.nextToken().trim();
abiCode=t.nextToken();
commDesc=t.nextToken();
parentCommName=t.nextToken();

printString.append("<?xml version=\"1.0\"?> \n");
printString.append("<!DOCTYPE vegPlot SYSTEM \"vegCommunity.dtd\">     \n");
printString.append("<vegCommunity> \n");
printString.append("   <commName>"+commName+"</commName> \n");
printString.append("   <commDesc>"+commDesc+"</commDesc> \n");
printString.append("   <abiCode>"+abiCode+"</abiCode> \n");
printString.append("   <classCode>"+nullValue+"</classCode> \n");
printString.append("   <classLevel>"+nullValue+"</classLevel> \n");
printString.append("   <originDate>"+nullValue+"</originDate> \n");
printString.append("   <updateDate>"+nullValue+"</updateDate> \n");

printString.append("   <parentComm> \n");
printString.append("     <commName>"+parentCommName+"</commName> \n");
printString.append("     <abiCode>"+nullValue+"</abiCode> \n");
printString.append("     <commDesc>"+nullValue+"</commDesc> \n");
printString.append("   </parentComm>");

printString.append("</vegCommunity>");

}
//print to the output file
out.println( printString.toString() );



}
catch (Exception e) {System.out.println("failed in xmlWriter.writePlotSummary" + 
	e.getMessage());
}
}


	
	
public void writePlotSummary(String plotSummary[], int plotSummaryNum, String outFile)
{
try {
String nullValue="nullValue";
	
//set up the output query file called query.xml	using append mode 
PrintStream out  = new PrintStream(new FileOutputStream(outFile, false)); 	
	
//print the header stuff
out.println("<?xml version=\"1.0\"?>       ");
out.println("<!DOCTYPE vegPlot SYSTEM \"vegPlot.dtd\">     ");
out.println("<vegPlot>");
	
//tokenize the incomming string array into respective elements
//make the token map and put into cvs
for (int i=0;i<=plotSummaryNum; i++) {	
	 StringTokenizer t = new StringTokenizer(plotSummary[i], "|");
	 String plotId=t.nextToken().trim();
	 String authorPlotCode=t.nextToken();
	 String project_id=t.nextToken();
	 String surfGeo=t.nextToken();
	 String plotType=t.nextToken();
	 String origLat=t.nextToken();
	 String origLong=t.nextToken();
	 
	String plotShape=t.nextToken();
	String plotSize=t.nextToken();
	String plotSizeAcc=t.nextToken();
	String altValue=t.nextToken();
	String altPosAcc=t.nextToken();
	String slopeAspect=t.nextToken();
	String slopeGradient=t.nextToken();
	String slopePosition=t.nextToken();
	String hydrologicRegime=t.nextToken();
	String soilDrainage=t.nextToken();
	String state=t.nextToken();
	String currentCommunity=t.nextToken();
	
	 
	 //the remaining elements are species so grab the species names and 
	 //stick into a array
	 String buf=null;
	 String speciesArray[]=new String[100000];
	 int speciesArrayNum=0;
	 while (t.hasMoreTokens()) {
		buf=t.nextToken();
		//System.out.println("writer "+buf);
		speciesArray[speciesArrayNum]=buf;
		speciesArrayNum++;
	 }
	 
//print to elements in the correct order
out.println("<project>");
out.println("	<projectName>"+authorPlotCode+"</projectName>");
out.println("	<projectDescription>TNC PROJECT:</projectDescription>");
out.println("	<projectContributor>");
out.println("		<role>"+nullValue+"</role>");
out.println("		<party>");
out.println("			<salutation>"+nullValue+" </salutation>");
out.println("			<givenName>"+nullValue+"  </givenName>");
out.println("			<surName>"+nullValue+" </surName>");
out.println("			<organizationName>"+nullValue+" </organizationName>");
out.println("			<positionName>"+nullValue+" </positionName>");
out.println("			<hoursOfService>"+nullValue+" </hoursOfService>");
out.println("			<contactInstructions>"+nullValue+" </contactInstructions>");
out.println("			<onlineResource>        ");
out.println("				<linkage>"+nullValue+"</linkage>       ");
out.println("				<protocol>"+nullValue+"</protocol>       ");
out.println("				<name>"+nullValue+"</name>       ");
out.println("				<applicationProfile>"+nullValue+"</applicationProfile>       ");
out.println("				<description>"+nullValue+"</description>       ");
out.println("			</onlineResource>        ");
out.println("			<telephone>        ");
out.println("				<phoneNumber>nullValue</phoneNumber>        ");
out.println("				<phoneType>"+nullValue+"</phoneType>       ");
out.println("			</telephone>        ");
out.println("			<email>        ");
out.println("				<emailAddress>"+nullValue+"</emailAddress>       ");
out.println("			</email>        ");
out.println("			<address>        ");
out.println("				<deliveryPoint>"+nullValue+"</deliveryPoint>       ");
out.println("				<city>"+nullValue+"</city>       ");
out.println("				<administrativeArea>"+nullValue+"</administrativeArea>       ");
out.println("				<postalCode>"+nullValue+"</postalCode>       ");
out.println("				<country>"+nullValue+"</country>       ");
out.println("				<currentFlag>"+nullValue+"</currentFlag>       ");
out.println("			</address>        ");
out.println("		</party>");
out.println("	</projectContributor>        ");
out.println("	<plot>        ");
out.println("		<plotId>"+ plotId +"</plotId>       ");
out.println("		<authorPlotCode>"+ authorPlotCode+"</authorPlotCode>       ");
out.println("		<parentPlot>"+nullValue+"</parentPlot>       ");
out.println("		<plotType>"+plotType+"</plotType>       ");
out.println("		<samplingMethod>"+nullValue+"</samplingMethod>       ");
out.println("		<coverScale>"+nullValue+"</coverScale>       ");
out.println("		<plotOriginLat>"+origLat+" </plotOriginLat>       ");
out.println("		<plotOriginLong>"+origLong+"</plotOriginLong>       ");
out.println("		<plotShape>"+plotShape+" </plotShape>       ");
out.println("		<plotSize>"+plotSize+"</plotSize>       ");
out.println("		<plotSizeAcc>"+plotSizeAcc+"</plotSizeAcc>       ");
out.println("		<altValue>"+altValue+" </altValue>       ");
out.println("		<altPosAcc>"+altPosAcc+"</altPosAcc>       ");
out.println("		<slopeAspect>"+slopeAspect+"</slopeAspect>       ");
out.println("		<slopeGradient>"+slopeGradient+"</slopeGradient>       ");
out.println("		<slopePosition>"+slopePosition+" </slopePosition>       ");
out.println("		<hydrologicRegime>"+hydrologicRegime+" </hydrologicRegime>       ");
out.println("		<soilDrainage>"+soilDrainage+" </soilDrainage>       ");
out.println("		<surfGeo>"+surfGeo+" </surfGeo>       ");
out.println("		<state>"+state+" </state>       ");
out.println("		<currentCommunity>"+currentCommunity+" </currentCommunity>       ");


out.println("		<plotObservation>        ");
out.println("			<previousPlot>"+nullValue+"</previousPlot>       ");
out.println("			<plotStartDate>"+nullValue+" </plotStartDate>       ");
out.println("			<plotStopDate>"+nullValue+"</plotStopDate>       ");
out.println("			<dateAccuracy>"+nullValue+"</dateAccuracy>       ");
out.println("			<effortLevel>"+nullValue+"</effortLevel>       ");

out.println("			<strata>        ");
out.println("				<stratumType>"+nullValue+"</stratumType>       ");	
out.println("				<stratumCover>"+nullValue+"</stratumCover>       ");
out.println("				<stratumHeight>"+nullValue+"</stratumHeight>       ");
out.println("			</strata>        ");




/*this is where to print out the list of different species found in the resultset
* for now just print the unique ones*/
utility m = new utility();
m.getUniqueArray(speciesArray, speciesArrayNum);

//print the unique species types
for (int h=0; h<m.outArrayNum; h++) {
if (h>5) {}
else 
{	
	out.println("                   <taxonObservations>        ");
	out.println("                           <authNameId>"+m.outArray[h]+"</authNameId>       ");
	out.println("                           <originalAuthority>"+nullValue+"</originalAuthority>       ");
	out.println("                           <strataComposition>        ");
	out.println("                                   <strataType>"+nullValue+"</strataType>       ");
	out.println("                                   <percentCover>"+nullValue+"</percentCover>       ");
	out.println("                           </strataComposition>        ");
	out.println("                           <interptretation>        ");
	out.println("                                   <circum_id>"+nullValue+"</circum_id>       ");
	out.println("                                   <role>"+nullValue+"</role>       ");
	out.println("                                   <date>"+nullValue+"</date>       ");
	out.println("                                   <notes>"+nullValue+"</notes>       ");
	out.println("                           </interptretation>        ");
	out.println("                   </taxonObservations>        ");
}
}



out.println("			<communityType>        ");
out.println("				<classAssociation>"+nullValue+"</classAssociation>       ");
out.println("				<classQuality>"+nullValue+"</classQuality>       ");
out.println("				<startDate>"+nullValue+"</startDate>       ");
out.println("				<stopDate>"+nullValue+"</stopDate>       ");
out.println("			</communityType>        ");

out.println("			<citation>        ");
out.println("				<title>"+nullValue+"</title>       ");
out.println("				<altTitle>"+nullValue+"</altTitle>       ");
out.println("				<pubDate>"+nullValue+"</pubDate>       ");
out.println("				<edition>"+nullValue+"</edition>       ");
out.println("				<editionDate>"+nullValue+"</editionDate>       ");
out.println("				<seriesName>"+nullValue+"</seriesName>       ");
out.println("				<issueIdentification>"+nullValue+"</issueIdentification>       ");
out.println("				<otherCredentials>"+nullValue+"</otherCredentials>       ");
out.println("				<page>"+nullValue+"</page>       ");
out.println("				<isbn>"+nullValue+"</isbn>       ");
out.println("				<issn>"+nullValue+"</issn>       ");

out.println("				<citationContributor>        ");
out.println("				</citationContributor>        ");

out.println("			</citation>        ");
out.println("		</plotObservation>        ");

out.println("		<namedPlace>        ");
out.println("			<placeName>"+nullValue+"</placeName>       ");
out.println("			<placeDescription>"+nullValue+"</placeDescription>       ");
out.println("			<gazeteerRef>"+nullValue+"</gazeteerRef>       ");
out.println("		</namedPlace>        ");

out.println("		<stand>        ");
out.println("			<standSize>"+nullValue+"</standSize>       ");
out.println("			<standDescription>"+nullValue+"</standDescription>       ");
out.println("			<standName>"+nullValue+"</standName>       ");
out.println("		</stand>        ");

out.println("		<graphic>        ");
out.println("			<browseName>"+nullValue+"</browseName>       ");
out.println("			<browseDescription>"+nullValue+"</browseDescription>       ");
out.println("			<browseType>"+nullValue+"</browseType>       ");
out.println("			<browseData>"+nullValue+"</browseData>       ");
out.println("		</graphic>        ");

out.println("		<plotContributor>        ");
out.println("			<role>"+nullValue+"</role>       ");
out.println("			<party>"+nullValue+"</party>       ");
out.println("		</plotContributor>        ");
out.println("	</plot>");
out.println("</project>");


}
//print the end of the file
out.println("</vegPlot>");
	
	
}
catch (Exception e) {System.out.println("failed in xmlWriter.writePlotSummary" + 
	e.getMessage());}

}	
	
}
