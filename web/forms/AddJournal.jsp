<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<html>
  <!--
  *  '$RCSfile: AddJournal.jsp,v $'
  *  Purpose:
  *  Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  Authors: @author@
  *
  *  '$Author: farrell $'
  *  '$Date: 2003-08-21 21:16:43 $'
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
  VegBank - Add a New Journal
  </title>
</head>
<body>

    @vegbank_header_html_normal@

    <br/>



    <table>
      <tr>
	<td>
	  
	  <html:form action="/AddJournal" onsubmit="return validateAddJournal(this)" >


	  <table align="left" border="0" width="90%" cellspacing="0" cellpadding="0">
	    <tr>
	      <td colspan="2" bgcolor="white">
		<img align="center" border="0" height="144" src="/vegbank/images/owlogoBev.jpg" alt="[VegBank logo]" />
	      </td>
	      <td align="left" valign="middle">
		<table border="0" cellpadding="5">
		  <tr>
		    <td align="left" valign="bottom">
		      <font face="Helvetica,Arial,Verdana" size="6" color="#23238E">Add a New Journal</font>
		      <blockquote>
		      <font color="#23238E" face="Helvetica,Arial,Verdana" size="2">
		      <b>
		      <frm_instr>Use this form to add a new journal to VegBank. <br />
		      <font color="red">*</font> Indicates a required field.</frm_instr>
		      </b>
		      </font>
		      </blockquote>
		    </td>

		  </tr>
		</table>
	      </td>
	    </tr>
	    <tr>
	      <td width="10%" bgcolor="white" align="left" valign="top" />
	      <td width="5%" bgcolor="white" align="left" valign="top" />
	      <td align="left">
		<!--variables that are used by the servlet to figure out which query(s) to issue -->
		<input name="requestDataFormatType" type="hidden" value="html" />
		<input name="clientType" type="hidden" value="browser" />
		<input name="requestDataType" type="hidden" value="vegPlot" />
		<input name="resultType" type="hidden" value="identity" />
		<!--yellow group header :  back one indent -->
	      </td>
	    </tr>
	    <tr bgcolor="#FFFFCC">
	      <td width="10%">&nbsp;</td>

	      <td colspan="2">
		<p>
		<font face="Helvetica,Arial,Verdana" size="3">
		<b>&nbsp;</b>
		</font>
		</p>
	      </td>
	    </tr>
	    <tr>
	      <td colspan="2">&nbsp;</td>
	      <td>
    <html:errors/>
		<table border="0" width="100%" bgcolor="#DFE5FA">
		  <tr>
		    <td align="left" valign="top" width="5%" colspan="2">
		      <font face="Helvetica,Arial,Verdana" size="3" color="#23238E">
		      <b>Journal Details</b>
		      </font>
		    </td>
		  </tr>
		  <tr>
		    <td align="center" width="5%">
		      <img src="/vegbank/images/icon_cat31.gif" alt="[!]" width="15" height="15" />
		    </td>
		    <td class="item">
		      <p>Please enter any information that is appropriate for the journal that you would like to add to VegBank.</p>
		    </td>
		  </tr>
		</table>

		<!--Data gathering :-->
		<table>
		  <tr>
		    <td>
		      <p>
		      <span class="category">
		      <a href="/vegbank/dbdictionary/dd~table~referencejournal~field~journal~type~tableview.html">Journal Name</a>:<font color="red">*</font>
		      </span>
		      </p>
		    </td>
		    <td>
		      <html:text property="referenceJournal.journal" size="50"/>
		    </td>
		  </tr>
		  <tr>
		    <td colspan="2">
		      <p>
		      <span class="item">The official name of the journal.</span>
		      </p>
		    </td>
		  </tr>

		  <tr>
		    <td>
		      <p>
		      <span class="category">
		      <a href="/vegbank/dbdictionary/dd~table~referencejournal~field~issn~type~tableview.html">ISSN</a>:</span>
		      </p>
		    </td>
		    <td>
		      <html:text property="referenceJournal.issn" size="50"/>
		    </td>
		  </tr>
		  <tr>
		    <td colspan="2">
		      <p>
		      <span class="item">International Standard Serial Number</span>
		      </p>

		    </td>
		  </tr>
		  <tr>
		    <td>
		      <p>
		      <span class="category">
		      <a href="/vegbank/dbdictionary/dd~table~referencejournal~field~abbreviation~type~tableview.html">Journal Abbreviation</a>:</span>
		      </p>

		    </td>
		    <td>
		      <html:text property="referenceJournal.abbreviation" size="50"/>
		    </td>
		  </tr>
		  <tr>
		    <td colspan="2">
		      <p>
		      <span class="item">The standard abbreviated name for the journal, examples: Can. J. Bot./Rev. Can. Bot., JAMA</span>

		      </p>
		    </td>
		  </tr>
		</table>
		<hr size="0.5" />
		<table border="0" width="100%" bgcolor="#DFE5FA">
		  <tr>
		    <td align="left" valign="top" width="5%" colspan="2">
		      <font face="Helvetica,Arial,Verdana" size="3" color="#23238E">

		      <b>Submit to Vegbank</b>
		      </font>
		    </td>
		  </tr>
		  <tr>
		    <td align="center" width="5%">
		      <img src="/vegbank/images/icon_cat31.gif" alt="[!]" width="15" height="15" />
		    </td>

		    <td class="item">
		      <p>Press the button on the left to submit this data to VegBank.  Press "reset" to clear this form and start over.</p>
		    </td>
		  </tr>

		</table>
		<!--Data gathering :-->

		<html:submit property="submit" value="--Add this Journal to VegBank--" /> | <html:reset/> 
		
	      </td>
	    </tr>
	    
	    
	  </table>
	  
	  </html:form>
	</td>
      </tr>
      <tr>
	<td>
    
	  @vegbank_footer_html_tworow@
	</td>
      </tr>
    </table>
    
  </body>
</html>
