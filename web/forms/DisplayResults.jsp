<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<!-- 
*   '$RCSfile: DisplayResults.jsp,v $'
*     Purpose: web page for displaying the plots stored in vegbank
*   Copyright: 2000 Regents of the University of California and the
*               National Center for Ecological Analysis and Synthesis
*     Authors: @author@
*
*    '$Author: anderson $'
*      '$Date: 2003-10-23 23:27:38 $'
*  '$Revision: 1.4 $'
*
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
-->
<head>
<link rel="STYLESHEET" href="@stylesheet@" type="text/css" />
  <title>
  VEGBANK - Display Plots Summary
  </title>
  </head>

  <body>
  @vegbank_header_html_normal@



  <!-- format the word plot to be plural if not 1 plot -->

  <bean:size id="PlotResultsSize" name="PlotsResults"/>
  <logic:equal value="1" name="PlotsResultsSize">
    <span class="category"><font color="red">
      There was one match to your search criteria:<br/>
    </font></span>
  </logic:equal>
  <logic:notEqual name="PlotsResultsSize" value="1">

    <span class="category"><font color="red">
      
	<bean:write name="PlotResultsSize"/> plot<logic:notEqual name="PlotResultsSize" value="1">s</logic:notEqual> matched your search criteria<br/>
    </font></span>
  
      <!-- SOME NOTES ABOUT THE USE OF ICONS-->
      <br/>
      <span class="intro">Available Reports:
        <img src="/vegbank/images/report_sm.gif"></img>=Summary
        <img src="/vegbank/images/comprehensive_sm.gif"></img>=Comprehensive
        <img src="/vegbank/images/small_globe.gif"></img>=Location 
      </span>

      <!-- set up the form which is required by netscape 4.x browsers -->
      <form name="myform" action="@viewdataservlet@" method="post">

	 Choose plots to download from the search results below.
	<br/>
       <input type="submit" name="downLoadAction" value="Continue to Download Wizard" /> 
	<br/>&nbsp;
       <!-- set up a table -->
    <table cellspacing="0" cellpadding="1">
	<tr><td>&nbsp; &nbsp; &nbsp; &nbsp; </td>
	<td bgcolor="#000000">
    <table width="550" cellspacing="0" cellpadding="3">


	 <tr colspan="1" bgcolor="#336633" align="left" valign="top">
	   <th align="center" nowrap> SEARCH RESULTS </th>
	   <th align="center"> Vegbank Accession Code </th>
	   <th align="center"> Author Accession Code </th>
	 </tr>



	 <%
	 //**************************************************************************************
	 //  Set up alternating row colors
	 //**************************************************************************************
	 boolean toggle = true;
	 String bgColor, marginBgColor;
	 %>
	
	 <logic:iterate id="row" name="PlotsResults" >

	 <%
	 //**************************************************************************************
	 //  Set up alternating row colors
	 //**************************************************************************************
	 if (toggle) {
		 bgColor = "#FFFFCC";
		 marginBgColor = "#CCCCCC";
	 } else {
		 bgColor = "#FFFFFF";
		 marginBgColor = "#EEEEEE";
	 }
	 toggle = !toggle;

	 %>
	 
     <tr valign="top" bgcolor="<%= bgColor %>" >

	    <!-- First Cell-->
	    <td width="20%" bgcolor="<%= marginBgColor %>" align="center">
	      
	      <!-- THE LINK TO THE SUMMARY-->
	      <!--
	      <a href="/vegbank/servlet/DataRequestServlet?requestDataFormatType=html&amp;clientType=browser&amp;requestDataType=vegPlot&amp;resultType=summary&amp;queryType=simple&amp;vbaccessionnumber=<bean:write property="vegbankAccessionNumber" name="row"/>"> 
	        <img align="center" border="0" src="/vegbank/images/report_sm.gif" alt="Summary view"> </img> 
              </a>
              -->
             <a href="/vegbank/servlet/DataRequestServlet?requestDataFormatType=html&amp;clientType=browser&amp;requestDataType=vegPlot&amp;resultType=summary&amp;queryType=simple&amp;plotId=<bean:write property="plotId" name="row"/>"><img align="center" border="0" 
	     	src="/vegbank/images/report_sm.gif" alt="Summary view"></img></a>
	      &#160;

	      <!-- THE LINK TO THE COMPREHENSIVE REPORT-->
	      <!--
	      <a href="/vegbank/servlet/DataRequestServlet?requestDataFormatType=html&amp;clientType=browser&amp;requestDataType=vegPlot&amp;resultType=full&amp;queryType=simple&amp;vbaccessionnumber=<bean:write property="vegbankAccessionNumber" name="row"/>"> 
	        <img align="center" border="0" src="/vegbank/images/comprehensive_sm.gif" alt="Comprehensive view"> </img> 
	      </a>
	      -->
	      <a href="/vegbank/servlet/DataRequestServlet?requestDataFormatType=html&amp;clientType=browser&amp;requestDataType=vegPlot&amp;resultType=full&amp;queryType=simple&amp;plotId=<bean:write property="plotId" name="row"/>"><img align="center" border="0" 
	      	src="/vegbank/images/comprehensive_sm.gif" alt="Comprehensive view"></img></a>
	      &#160;
	      
	      <!-- THE LINK TO THE LOCATION -->
	      <a href="/mapplotter/servlet/mapplotter?action=mapsinglecoordinate&amp;longitude=<bean:write name="row" property="longitude"/>&amp;latitude=<bean:write name="row" property="latitude"/>"><img align="center" border="0" 
	      	src="/vegbank/images/small_globe.gif" alt="Location"></img></a>
	      
		<br/>
              <input name="plotName" type="checkbox" value="<bean:write property="plotId" name="row"/>" checked="yes"> 
                <span class="item"> <font color="#0000CC"> download</font> </span> 
              </input>

	    </td>

	    <td bgcolor="<%= bgColor %>" align="center" valign="middle">
	        <span class="category">
	          <bean:write name="row" property="vegbankAccessionNumber"/>   
	        </span>
         </td>		 
	    <td bgcolor="<%= bgColor %>" align="center" valign="middle">
	        <span class="category">
	          <bean:write name="row" property="authorObservationCode"/>   
	        </span>
         </td>		 
	  </tr>

  
	</logic:iterate>

	 <tr bgcolor="#336633">
	   <td colspan="3">&nbsp; </td>
	 </tr>
      </table>
	  </td></tr>
      </table>

	<br/>
	 Choose plots to download from the search results above.
	<br/>
       <input type="submit" name="downLoadAction" value="Continue to Download Wizard" /> 
	<br/>&nbsp;
      
  </logic:notEqual>
  
  </font>
  </span>
  
  <!-- VEGBANK FOOTER -->
  @vegbank_footer_html_tworow@
  </body>
</html>
