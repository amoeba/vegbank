<?xml version="1.0"?> 
<!--
  * resultset.xsl
  *
  *      Authors: John Harris
  *    Copyright: 2000 Regents of the University of California and the 
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *      Created: 2002 Spring
  * 
  * This is an XSLT (http://www.w3.org/TR/xslt) stylesheet designed to
  * convert an XML file showing the resultset of a query
  * into an HTML format suitable for rendering with modern web browsers.
-->


<!--Style sheet for transforming plot xml files, specifically
	for the servlet transformation to show summary results
	to the web browser-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
 <xsl:output method="html"/>
  <xsl:template match="/vegPlotPackage">

<!--  
  <html>
      <head>
      
             <link rel="stylesheet" type="text/css" 
              href="@web-base-url@/default.css" />
        
	</head>
	<body>
	</body>
 </html>
-->


<html>
      <head>
      <!--
             <link rel="stylesheet" type="text/css" 
              href="@web-base-url@/default.css" />
        -->
	<script LANGUAGE="JavaScript">
<!-- Modified By:  Steve Robison, Jr. (stevejr@ce.net) -->

<!-- This script and many more are available free online at -->
<!-- The JavaScript Source!! http://javascript.internet.com -->
<xsl:text disable-output-escaping="yes">
&lt;!-- Begin
var checkflag = "false";
function check(field) {
if (checkflag == "false") {
for (i = 0; i &lt; field.length; i++) {
field[i].checked = true;}
checkflag = "true";
return "Uncheck All"; }
else {
for (i = 0; i &lt; field.length; i++) {
field[i].checked = false; }
checkflag = "false";
return "Check All"; }
}
//  End -->
</xsl:text>
</script>

  
<script language="javascript" alt="JavaScript not enabled!">
 <xsl:text disable-output-escaping="yes">
   &lt;!-- 
    today = new Date()
    document.write("(Accessed: " + today +" to: ")
    document.write(location.host.toLowerCase()+") \n")
   //--&gt;
   </xsl:text>
 </script>

 
</head>




<body bgcolor="FFFFFF">



<br></br>
<xsl:number value="count(project/plot)" /> documents found.


<!-- set up the form which is required by netscape 4.x browsers -->
<form name="myform" action="viewData" method="post">
<input type="submit" name="downLoadAction" value="Continue to Download Wizard" /> 

<!-- set up a table -->
<table width="100%">


           <tr colspan="1" bgcolor="CCCCFF" align="left" valign="top">
             <th class="tablehead">Identification</th>
             <th class="tablehead">Location</th>
             <th class="tablehead">Taxa</th>
           </tr>

	<!-- Header and row colors -->
        <xsl:variable name="evenRowColor">#C0D3E7</xsl:variable>
        <xsl:variable name="oddRowColor">#FFFFFF</xsl:variable>
	
	   
	<xsl:for-each select="project/plot">
	<xsl:sort select="authorPlotCode"/>
	
	<tr valign="top">
             
     
     <!--if even row -->
     <xsl:if test="position() mod 2 = 1">
			
    	
		<!-- First Cell-->
		<td  width="20%" colspan="1" bgcolor="{$evenRowColor}" align="left" valign="middle">
			<!--grab the plot name and store as a varibale for later-->
			<xsl:variable name="PLOT">
  			<xsl:value-of select="authorPlotCode"/>
			</xsl:variable>
			
			<xsl:variable name="PLOTID">
  			<xsl:value-of select="plotId"/>
			</xsl:variable>
			
			<a> <b>Vegbank code: </b> 	
				<FONT SIZE="-2" FACE="arial">
					<xsl:value-of select="plotAccessionNumber"/> <br> </br>
				</FONT>
			</a>
			<a> <b>Author code: </b> <xsl:value-of select="authorPlotCode"/> <br> </br></a>
			<!-- THE LIST OF NAMES ASSOCIATED WITH THE PROJECT CONTRIBUTORS -->
			<!--
			<xsl:for-each select="../projectContributor">
						<a><b>Contributor:</b> <xsl:value-of select="wholeName"/> <br> </br> </a>
			</xsl:for-each>
			-->
		
		<input name="plotName" type="checkbox" value="{$PLOTID}" checked="yes">download</input>
			<xsl:number value="position()"/>
		</td>
        	
		<td  width="20%" colspan="1" bgcolor="{$evenRowColor}" align="left" valign="middle">
					<!-- CREATE A MAP -  GET THE LATS AND LONGS INTO A VARIABLE -->
					<xsl:variable name="LATITUDE">
  				<xsl:value-of select="latitude"/>
					</xsl:variable>
					<xsl:variable name="LONGITUDE">
  				<xsl:value-of select="longitude"/>
					</xsl:variable>
					<a href="/mapplotter/servlet/mapplotter?action=mapsinglecoordinate&amp;longitude={$LONGITUDE}&amp;latitude={$LATITUDE}"> 
					<img align="center" border="0" src="/vegbank/images/small_globe.gif" alt="Location"> </img> 
					</a>
					<br> </br>
					
					<a><b>State:</b> <FONT SIZE="-2" FACE="arial">  <xsl:value-of select="state"/> </FONT>  <br></br> </a>
					<a><b>Country:</b> <FONT SIZE="-2" FACE="arial">  <xsl:value-of select="country"/> </FONT>   <br></br> </a>
				
					<a><b>Area:</b> <FONT SIZE="-2" FACE="arial">  <xsl:value-of select="area"/> </FONT>  <br></br> </a>
					<a><b>Topo Position:</b> <FONT SIZE="-2" FACE="arial">  <xsl:value-of select="topoPosition"/> </FONT>  <br></br> </a>
					<a><b>Slope Aspect:</b> <FONT SIZE="-2" FACE="arial">  <xsl:value-of select="slopeAspect"/> </FONT>  <br></br> </a>
          <a><b>Slope Gradient:</b> <FONT SIZE="-2" FACE="arial"> <xsl:value-of select="slopeGradient"/> </FONT>  <br></br></a>
	     		<a><b>Elevation:</b> <FONT SIZE="-2" FACE="arial"> <xsl:value-of select="elevation"/> </FONT> <br> </br> </a>
					<a><b>Geology:</b> <FONT SIZE="-2" FACE="arial"> <xsl:value-of select="geology"/> </FONT>  <br> </br> </a>
					
		</td>
	    

	  <td  width="60%" colspan="1" bgcolor="{$evenRowColor}" align="left" valign="top">
      <i><FONT SIZE="-1" FACE="arial">
				<b>Top species: </b>
				</FONT> 
			</i>
			<FONT SIZE="-2" FACE="arial">
				<xsl:for-each select="observation/taxonObservation">
	    	<xsl:value-of select="authorNameId"/>; </xsl:for-each>
	 		</FONT>
    	<br> </br>
			<i><FONT SIZE="-1" FACE="arial">
				<b>Veg Community: </b>
				</FONT> 
			</i>
			<xsl:for-each select="observation/communityClassification">
			<FONT SIZE="-2" FACE="arial">
			<xsl:value-of select="className"/> --  
			<xsl:value-of select="classCode"/>
			</FONT>
			</xsl:for-each>
	</td>
		
	 </xsl:if>
	 
	 
	

  <xsl:if test="position() mod 2 = 0">
			
    	
		<!-- First Cell-->
		<td  width="20%" colspan="1" bgcolor="{$oddRowColor}" align="left" valign="middle">
			<!--grab the plot name and store as a varibale for later-->
			<xsl:variable name="PLOT">
  			<xsl:value-of select="authorPlotCode"/>
			</xsl:variable>
			
			<xsl:variable name="PLOTID">
  			<xsl:value-of select="plotId"/>
			</xsl:variable>
		<!--	
			<a> <b>Plot code: </b> VegBank-<xsl:value-of select="plotId"/> <br> </br></a>
			-->
			<a> <b>Vegbank code: </b> 	
				<FONT SIZE="-2" FACE="arial">
					<xsl:value-of select="plotAccessionNumber"/> <br> </br>
				</FONT>
			</a>
			<a> <b>Author code: </b> <xsl:value-of select="authorPlotCode"/> <br> </br></a>
			<!-- THE LIST OF NAMES ASSOCIATED WITH THE PROJECT CONTRIBUTORS -->
			<!--
			<xsl:for-each select="../projectContributor">
						<a><b>Contributor:</b> <xsl:value-of select="wholeName"/> <br> </br> </a>
			</xsl:for-each>
			-->
			<input name="plotName" type="checkbox" value="{$PLOTID}" checked="yes">download</input>
			<xsl:number value="position()"/>
		</td>
        	
		<td  width="20%" colspan="1" bgcolor="{$oddRowColor}" align="left" valign="middle">
					<!-- CREATE A MAP -  GET THE LATS AND LONGS INTO A VARIABLE -->
					<xsl:variable name="LATITUDE">
  				<xsl:value-of select="latitude"/>
					</xsl:variable>
					<xsl:variable name="LONGITUDE">
  				<xsl:value-of select="longitude"/>
					</xsl:variable>
					<a href="/mapplotter/servlet/mapplotter?action=mapsinglecoordinate&amp;longitude={$LONGITUDE}&amp;latitude={$LATITUDE}"> 
					<img align="center" border="0" src="/vegbank/images/small_globe.gif" alt="Location"> </img> 
					</a>
					<br> </br>
					<a><b>State:</b> <xsl:value-of select="state"/>  <br></br> </a>
					<a><b>Country:</b> <xsl:value-of select="country"/>  <br></br> </a>
					<a><b>Area:</b> <xsl:value-of select="area"/>  <br></br> </a>
					<a><b>Topo Position:</b> <xsl:value-of select="topoPosition"/>  <br></br> </a>
					<a><b>Slope Aspect:</b> <xsl:value-of select="slopeAspect"/> <br></br> </a>
          <a><b>Slope Gradient:</b> <xsl:value-of select="slopeGradient"/>  <br></br></a>
	     		<a><b>Slope Position:</b> <xsl:value-of select="slopePosition"/> <br> </br> </a>
					<a><b>Geology:</b> <xsl:value-of select="geology"/> <br> </br> </a>
		</td>
	    

	  <td  width="60%" colspan="1" bgcolor="{$oddRowColor}" align="left" valign="top">
      <i><FONT SIZE="-1" FACE="arial">
				<b>Top species: </b>
				</FONT> 
			</i>
			<FONT SIZE="-2" FACE="arial">
				<xsl:for-each select="observation/taxonObservation">
	    	<xsl:value-of select="authorNameId"/>; </xsl:for-each>
	 		</FONT>
    </td>
	 </xsl:if>
	 </tr>    
	</xsl:for-each>

</table>
<input type="submit" name="downLoadAction" value="Continue to Download Wizard" /> 
</form>
	


</body>
</html> 

</xsl:template>


</xsl:stylesheet>
