<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<HTML>
<!--
*   '$RCSfile: RegisterNewUser.jsp,v $'
*   Purpose: Add a new reference to vegbank
*   Copyright: 2000 Regents of the University of California and the
*               National Center for Ecological Analysis and Synthesis
*   Authors: @author@
*
*  '$Author: anderson $'
*  '$Date: 2004-08-06 23:55:32 $'
*  '$Revision: 1.9 $'
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

<HEAD>@defaultHeadToken@
 
<TITLE>VegBank Registration</TITLE>
<link rel="stylesheet" href="@stylesheet@" type="text/css">
 
<meta http-equiv="Content-Type" content="text/html; charset=">

</HEAD>
<BODY>

@vegbank_header_html_normal@

  <br/>
<!-- table that limits width of text -->
<table cellspacing="2" cellpadding="0" border="0" width="725">
<tr>
 <td colspan="8">
		<h2 align="center">VegBank Registration</h2>
		&nbsp;
 </td>
</tr>


<!-- FORM -->
 <html:form action="RegisterNewUser.do" onsubmit="return validateRegisterNewUserForm(this)">

<tr>
<td valign="top">
<span class="vegbank_large">Register here.</h3>
  <br/>
  <br/>
  <span class="vegbank_small">
  <html:errors/>
  </span>

  <TABLE width="315" border="0">
    <TR> 
      <TD>
	  	<b><span class="vegbank_normal">Login Information</span></b>
	  </TD>
    </TR>

    <TR> 
      <TD>
	  	<font color="#FF0000">*</font>
	  	<span class="vegbank_small">e-Mail Address (login name): 
		<br/>
	  	<html:text property="usr.email_address" size="30"/>
      </TD>
    </TR>

    <TR> 
      <TD>
		<font color="#FF0000">*</font>
		<span class="vegbank_small">Password:</span>
		<br/>
		<html:password property="password1" size="30" redisplay="true"/>
      </TD>
      </TD>
    </TR>

    <TR> 
      <TD>
	 	 	<font color="#FF0000">*</font>
            <span class="vegbank_small">Confirm Password: </span>
			<br/>
        	<html:password property="password2" size="30" redisplay="true"/>
      </TD>
    </TR>

    <TR>
      <TD>&nbsp;</TD>
    </TR>

    <TR>
      <TD> 
         <b><span class="vegbank_normal">Personal Information</span></b>
      </TD>
    </TR>

    <!-- first name-->
    <TR> 
      <TD>
	 	  <font color="#FF0000">*</font>
          <span class="vegbank_small">First Name: </span>
		  <br/>
		  <html:text property="party.givenname" size="30"/>
      </TD>
    </TR>

    <!-- last name -->
    <TR> 
      <TD>
	 	  <font color="#FF0000">*</font>
          <span class="vegbank_small">Last Name:</span>
		  <br/>
		  <html:text property="party.surname" size="30"/>
      </TD>
    </TR>
  </TABLE>
  &nbsp;

<!-- TERMS -->
<table bgcolor="#EEEEEE" cellpadding="5" border="0" width="90%">
<tr>
	<td>
		<span class="psmall">Acceptance of the  
		<a href="@general_link@terms.html">VegBank terms of use</a>
		is required.
		</span>
  </td>
</tr>
</table>

  <table cellspacing="2" cellpadding="2" width="90%" border="1">
    <tr> 
      <td>
          <font color="red">*</font> 
          <html:radio value="accept" property="termsaccept"/> 
          <span class="vegbank_small">I accept these terms</span>
      </td>
      <td>
           <html:radio value="decline" property="termsaccept"/> 
           <span class="vegbank_small">I decline</span>
      </td>
    </tr>

  </table>


          <p><b><font color="red">*</font> 
		  <span class="vegbank_small">denotes required field</span>
		  </b></p>

		<html:submit property="submit" value="Register Now" />

	</td>
</html:form>

<!-- SPACER -->
<td bgcolor="#AAAAAA" width="1"><img src="@image_server@pix_clear" width="1" height="1"></td>
<td bgcolor="#DDDDDD" width="1"><img src="@image_server@pix_clear" width="1" height="1"></td>
<td width="1"><img src="@image_server@pix_clear" width="8" height="1"></td>

<!-- TEXT -->
<td valign="top">
<h3>Learn more here.</h3>
  <br/>
<p>Although you may 
<html:link action="Logon.do?username=GUESTUSER@VEGBANK.ORG&password=nopassword">login as a guest</html:link>
to use VegBank without registration, registered users are able to...</p>

<ul>
  <li class="vegbank_small">add your data and annotate extant data (certification required)</li>
  <li class="vegbank_small">build personal datasets and queries (coming soon)
  <li class="vegbank_small">request permission to view non-public data (coming soon)</li>
</ul>

<p>Visitors to vegbank.org are <b>guaranteed privacy</b>. 
  Information collected on users and their activities 
  at vegbank.org is kept private and <b>never shared</b> with other organizations 
  or persons.</p>
  
  <p>Any data, comments, interpretations, or the like that you 
  submit or affix to a record in the VegBank database will be considered public 
  except where confidentiality has been formally requested to protect endangered 
  species or rights of private land owners.</p>

<p>Permission to use certain advanced features requires 
registration and <html:link action="LoadCertification.do">certification</html:link>.
</p>
	</td>
</tr>
</table>

<br>
<br>
@vegbank_footer_html_onerow@
</BODY>
</HTML>
