<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache"> 
<META HTTP-EQUIV="expires" CONTENT="0"> 
<html:html>
 <head>
 <script language="javascript" >
days = new Array(7);
days[1] = "Sunday";
days[2] = "Monday";
days[3] = "Tuesday"; 
days[4] = "Wednesday";
days[5] = "Thursday";
days[6] = "Friday";
days[7] = "Saturday";
months = new Array(12);
months[1] = "January";
months[2] = "February";
months[3] = "March";
months[4] = "April";
months[5] = "May";
months[6] = "June";
months[7] = "July";
months[8] = "August";
months[9] = "September";
months[10] = "October"; 
months[11] = "November";
months[12] = "December";
today = new Date(); 
day=today.getDay() + 1;
date = today.getDate()
weekday = days[day];
month = months[today.getMonth() + 1];
year=today.getYear(); 
if (year < 2000)
year = year + 1900;

var datestr=month+" "+date+","+year
</script>
  <title>nvcrs</title>
  <html:base/>
 </head>
 <body topmargin="0" leftmargin="0" bgcolor="#FFFFFF">
 <center>
 <Table width=800 border=0 cellpadding=0 cellspacing=0>
 	 	<tr><td><img src="image/banner.gif"></td></tr>
 	<tr><td>
 		<Table width=800 border=0 cellpadding=0 cellspacing=0>
 			<tr>
 				<td width=150><img src="image/coner.gif" ></td>
				<td width=24><img src="image/menu1.gif" ></td>
				<td width=618 background="image/menu_bg.gif" ><tiles:insert attribute="menu"/></td>
				<td width=8><img src="image/menu2.gif" ></td>
 			</tr>
 		</table>
 	</td></tr>
 	<tr><td>
 		<Table width=800 border=0 cellpadding=0 cellspacing=0 bgcolor=B38EDD>
 			<tr >
 				<td width=15 bgcolor=B38EDD><img src="image/date1.gif" ></td>
				<td width=111 background="image/date_bg.gif" >
				<font face="Arial" size="1"><center>
				<script> document.write(datestr);
				</script></center></font>
				</td>
				<td width=24><img src="image/date2.gif" ></td>
<!-- 				<td width=650 bgcolor=B38EDD><tiles:insert attribute="userbar"/></td> -->
 				<td width=650 background="image/userbar_bg.gif"><tiles:insert attribute="userbar"/></td>
				
 			</tr>
 		</table>
 	</td></tr>
 </table>
 <Table border=0 cellpadding=0 cellspacing=0 width=800>
	<tr>
		<!-- contents -->
		<td width="1" background="image/vsep_line.gif"></td> 
		<td width="150" valign="top" align="center">
			<Table border=0 cellpadding=0 cellspacing=0>
			<!-- <tr><td><img src="image/content1.gif"></td></tr> 
			<tr><td background="image/content_bg.gif"> -->
			
			<tr><td>
				<tiles:insert attribute="content"/>
			</td></tr>
<!--			<tr><td><img src="image/content2.gif"></td></tr> -->
			</Table>
		</td>
		<td width="1" background="image/vsep_line.gif"></td> 
	<!--	<td width="17" background="image/bgmain.gif"></td>  -->
		<!-- main -->
		<td width="647" valign="top"><tiles:insert attribute="main"/></td>
		<td width="1" background="image/vsep_line.gif"></td>
	</tr>
 	<!-- footer -->
 	<tr><td></td></tr>
 	<tr><td></td></tr>
 </table>			
 <Table border=0 cellpadding=0 cellspacing=0 width=800>
 	 	<tr>
 	
 	<td bgcolor=96B5E3><tiles:insert attribute="footer"/></td>
 	
 	</tr>
 </table>
 </center>
</body>
</html:html>
