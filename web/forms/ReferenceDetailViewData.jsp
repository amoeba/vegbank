<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<HTML>
<!--
*   '$RCSfile: ReferenceDetailViewData.jsp,v $'
*   Purpose: View details of all references in vegbank
*   Copyright: 2000 Regents of the University of California and the
*               National Center for Ecological Analysis and Synthesis
*   Authors: @author@
*
*  '$Author: mlee $'
*  '$Date: 2004-04-08 05:44:28 $'
*  '$Revision: 1.3 $'
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
*
-->
<head>@defaultHeadToken@

<title>View Current References -- Details</title>
<link rel="stylesheet" href="@stylesheet@" type="text/css"/>
  <style type="text/css">
  .oddrow { background-color : #FFFFCC }
  .evenrow {background-color : #FFFFFF }
  </style>
  <meta http-equiv="Content-Type" content="text/html; charset=">
  </head>

  <body>

  <!--xxx -->
  @vegbank_header_html_normal@
  <!--xxx -->

<h2>View Current References -- Details</h2>




    <!-- data -->

    <%
    //**************************************************************************************
    //  Set up alternating row colors
    //**************************************************************************************
    String bgColor = "#FFFFF";
    %>

    <logic:iterate id="reference" name="genericBean" type="org.vegbank.common.model.Reference">

    <%
    //**************************************************************************************
    //  Set up alternating row colors
    //**************************************************************************************
    bgColor = bgColor.equals("#FFFFF")? "#FFFFC" : "#FFFFF";
    %>

<table border="1" cellspacing="0" cellpadding="0">


 <tr>
   <td class="grey"><p><span class="category">ID</span></p></td>
   <td valign="top" bgcolor="<%= bgColor %>"><span class="item"><bean:write name="reference" property="reference_id"/>&nbsp;</span></td>
 </tr>
 <tr>
   <td class="grey"><p><span class="category">Short Name</span></p></td>
   <td valign="top" bgcolor="<%= bgColor %>"><span class="item"><bean:write name="reference" property="shortname"/>&nbsp;</span></td>
 </tr>
 <tr>
   <td class="grey"><p><span class="category">Reference Type</span></p></td>
   <td valign="top" bgcolor="<%= bgColor %>"><span class="item"><bean:write name="reference" property="referencetype"/>&nbsp;</span></td>
 </tr>
 <tr>
   <td class="grey"><p><span class="category">Title</span></p></td>
   <td valign="top" bgcolor="<%= bgColor %>"><span class="item"><bean:write name="reference" property="title"/>&nbsp;</span></td>
 </tr>
 <tr>
   <td class="grey"><p><span class="category">Title Superior</span></p></td>
   <td valign="top" bgcolor="<%= bgColor %>"><span class="item"><bean:write name="reference" property="titlesuperior"/>&nbsp;</span></td>
 </tr>
 <tr>
   <td class="grey"><p><span class="category">Publication Date</span></p></td>
   <td valign="top" bgcolor="<%= bgColor %>"><span class="item"><bean:write name="reference" property="pubdate"/>&nbsp;</span></td>
 </tr>
 <tr>
   <td class="grey"><p><span class="category">Access Date</span></p></td>
   <td valign="top" bgcolor="<%= bgColor %>"><span class="item"><bean:write name="reference" property="accessdate"/>&nbsp;</span></td>
 </tr>
 <tr>
   <td class="grey"><p><span class="category">Conference Date</span></p></td>
   <td valign="top" bgcolor="<%= bgColor %>"><span class="item"><bean:write name="reference" property="conferencedate"/>&nbsp;</span></td>
 </tr>
 <tr>
   <td class="grey"><p><span class="category">Journal</span></p></td>
<!-- translate journal ID -->

   <td valign="top" bgcolor="<%= bgColor %>"><span class="item">
<a href='/vegbank@url2get_referencejournal_v_dtl_pk@<bean:write name="reference" property="referencejournal_id"/>'>
      <!-- get referenceJournal_ID translation -->
	  		<bean:define id="current__refjournalid" name="reference" property="referencejournal_id"/>
	  		<bean:include id="current__refjournalname"
			   page='<%= "@url2get_referencejournal_v_pktranslate_pk@" + current__refjournalid %>' />
	          <bean:write name="current__refjournalname" filter="false" />
		</a>&nbsp;
</a>
&nbsp;</span></td>

 </tr>
 <tr>
   <td class="grey"><p><span class="category">Volume</span></p></td>
   <td valign="top" bgcolor="<%= bgColor %>"><span class="item"><bean:write name="reference" property="volume"/>&nbsp;</span></td>
 </tr>
 <tr>
   <td class="grey"><p><span class="category">Issue</span></p></td>
   <td valign="top" bgcolor="<%= bgColor %>"><span class="item"><bean:write name="reference" property="issue"/>&nbsp;</span></td>
 </tr>
 <tr>
   <td class="grey"><p><span class="category">Page Range</span></p></td>
   <td valign="top" bgcolor="<%= bgColor %>"><span class="item"><bean:write name="reference" property="pagerange"/>&nbsp;</span></td>
 </tr>
 <tr>
   <td class="grey"><p><span class="category">Total Pages</span></p></td>
   <td valign="top" bgcolor="<%= bgColor %>"><span class="item"><bean:write name="reference" property="totalpages"/>&nbsp;</span></td>
 </tr>
 <tr>
   <td class="grey"><p><span class="category">Publisher</span></p></td>
   <td valign="top" bgcolor="<%= bgColor %>"><span class="item"><bean:write name="reference" property="publisher"/>&nbsp;</span></td>
 </tr>
 <tr>
   <td class="grey"><p><span class="category">Publication Place</span></p></td>
   <td valign="top" bgcolor="<%= bgColor %>"><span class="item"><bean:write name="reference" property="publicationplace"/>&nbsp;</span></td>
 </tr>
 <tr>
   <td class="grey"><p><span class="category">ISBN</span></p></td>
   <td valign="top" bgcolor="<%= bgColor %>"><span class="item"><bean:write name="reference" property="isbn"/>&nbsp;</span></td>
 </tr>
 <tr>
   <td class="grey"><p><span class="category">Edition</span></p></td>
   <td valign="top" bgcolor="<%= bgColor %>"><span class="item"><bean:write name="reference" property="edition"/>&nbsp;</span></td>
 </tr>
 <tr>
   <td class="grey"><p><span class="category">Number of Volumes</span></p></td>
   <td valign="top" bgcolor="<%= bgColor %>"><span class="item"><bean:write name="reference" property="numberofvolumes"/>&nbsp;</span></td>
 </tr>
 <tr>
   <td class="grey"><p><span class="category">Chapter Number</span></p></td>
   <td valign="top" bgcolor="<%= bgColor %>"><span class="item"><bean:write name="reference" property="chapternumber"/>&nbsp;</span></td>
 </tr>
 <tr>
   <td class="grey"><p><span class="category">Report Number</span></p></td>
   <td valign="top" bgcolor="<%= bgColor %>"><span class="item"><bean:write name="reference" property="reportnumber"/>&nbsp;</span></td>
 </tr>
 <tr>
   <td class="grey"><p><span class="category">Communication Type</span></p></td>
   <td valign="top" bgcolor="<%= bgColor %>"><span class="item"><bean:write name="reference" property="communicationtype"/>&nbsp;</span></td>
 </tr>
 <tr>
   <td class="grey"><p><span class="category">Degree</span></p></td>
   <td valign="top" bgcolor="<%= bgColor %>"><span class="item"><bean:write name="reference" property="degree"/>&nbsp;</span></td>
 </tr>
 <tr>
   <td class="grey"><p><span class="category">URL</span></p></td>
   <td valign="top" bgcolor="<%= bgColor %>"><span class="item"><bean:write name="reference" property="url"/>&nbsp;</span></td>
 </tr>
 <tr>
   <td class="grey"><p><span class="category">DOI</span></p></td>
   <td valign="top" bgcolor="<%= bgColor %>"><span class="item"><bean:write name="reference" property="doi"/>&nbsp;</span></td>
 </tr>
 <tr>
   <td class="grey"><p><span class="category">Additional Information</span></p></td>
   <td valign="top" bgcolor="<%= bgColor %>"><span class="item"><bean:write name="reference" property="additionalinfo"/>&nbsp;</span></td>
 </tr>
 <!-- unpackage reference contribs for this reference -->
 <tr>
   <td class="grey"><p><span class="category">Reference Contributor(s)</span></p></td>
   <td valign="top" bgcolor="<%= bgColor %>">

	 		<bean:define id="current__ref" name="reference" property="reference_id"/>
	 		<bean:include id="currentref_contrib"
	 page='<%= "@url2get_referencecontributor_v_austere@&WHERE=where_reference_pk&wparam=" + current__ref %>' />
	         <bean:write name="currentref_contrib" filter="false" /></td>
 </tr>

  </table>

  <br/>

    </logic:iterate>


  <!-- VEGBANK FOOTER -->
  @vegbank_footer_html_tworow@
  <!-- END FOOTER -->

  </body>
  </html>
