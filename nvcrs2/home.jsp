<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ page import="org.vegbank.nvcrs.web.*" %>
<%
	BeanManager manager=(BeanManager)pageContext.getAttribute("beanManager", PageContext.SESSION_SCOPE);
	boolean b=true;
	if(manager==null) b=false;
	else b=manager.isRegistered();
	
	
%>
<img src="image/title_sep_line.gif">
<font face=Arial >
<table width=630 border=0 cellpadding=0 cellspacing=0>
<tr><td width=30></td><td>
<b>Welcome to NVCRS Revision System
</td></tr>
</table >
<br>
<br>
<Table width=630 border=0 cellpadding=0 cellspacing=0>
	<tr>
		<td width=30></td>
		<td width=600 valign=bottom>
		<font size=2 >National Vegetation Classification Revision System(NVCRS) is an online vegtation type revision system for ecologists to submit their proposals, 
for peer-viewers to evaluate submitted proposals and for panel members to make the final decisions to 
accept or decline a revision proposal. <br><br>
<!--
More infomation about the design of this system cound be found at 
<a href="http://tekka.nceas.ucsb.edu:8080/wiki/Wiki.jsp?page=Main" target="_blank">the project wiki page</a><br><br>
Some user accounts for testing:NVC Revision System is now under construction. Your comments are highly appreciated. Add your comments <a href="http://tekka.nceas.ucsb.edu:8080/wiki/Edit.jsp?page=Comments" target="_blank">here</a>
<LH>Current games you can play are:
<LI>Register a new user</li>
<LI>View & edit your user information</li>
<LI>Logoff the system</li>
<LI>Login as an Author: username:au, password:au
<LI>Login as a Peer-viewer: username:pv, password:pv
<LI>Login as a Manager: username:mg, password:mg
<LI>Login as both an Author and a Peer-viewer: username:au_pv, password:au_pv
<LI>Login as both an Author and a Manager: username:au_mg, password:au_mg
<LI>Login as both a Peer-viewer and a Manager: username:pv_mg, password:pv_mg
<LI>Login as an Author, a Peer-viewer and a Manager: username:au_pv_mg, password:au_pv_mg
<LI>Add new proposal as an Author
</LH> -->


		</font>
		</td>
	</tr>
<% 
if(!b)
{
%> 

	<tr>
		<td width=30></td>
		<td width=600 valign=bottom>
			<%@ include file="login_body.jsp" %>
		</font>
		</td>
	</tr>
<%
}
%>
</table>
</font>
<br>

<!--

<font face=Arial size=2>
<br>
<br>
<center>
<table border=0 cellpadding=0 cellspacing=0 width=500 >
<tr>
	<th align=left colspan=4><font face=Arial size=2>Here are some user accounts for testing:
<tr>	
	<td width=30>
	<Td><font size=2 color=white>&nbsp;
	<Td><font size=2 color=white>&nbsp;
	<td><font size=2 color=white>&nbsp;
<tr bgcolor=blue>
	<td width=30>	
	<Td><font size=2 color=white>Roles
	<Td ><font size=2 color=white>login name
	<td ><font size=2 color=white>password
<TR bgcolor="#cacaca">
	<td width=30>
	<td><font size=2 >author
	<td><font size=2 >au
	<td><font size=2 >au
<TR bgcolor="#d8d8d8">	
	<td width=30>
	<td><font size=2 >author & peer-viewer
	<td><font size=2 >pv1
	<td><font size=2 >pv1
<TR bgcolor="#cacaca">	
		<td width=30>
	<td><font size=2 >author & peer-viewer
	<td><font size=2 >pv2
	<td><font size=2 >pv2
<TR bgcolor="#d8d8d8">	
	<td width=30>
	<td><font size=2 >manager
	<td><font size=2 >mg
	<td><font size=2 >mg
</table>
</center>
<br><br>
</font>

-->
