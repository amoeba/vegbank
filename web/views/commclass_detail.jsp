@stdvegbankget_jspdeclarations@

<html>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
@defaultHeadToken@
 
<TITLE>View VegBank Data: Community Classifications - Detail</TITLE>
<link rel="stylesheet" href="@stylesheet@" type="text/css" />
</HEAD>
<body>
      @vegbank_header_html_normal@
      @possibly_center@
        <h2>View VegBank Community Classifications</h2>
<!--Get standard declaration of rowClass as string: -->
        <% String rowClass = "evenrow"; %>
        <vegbank:get id="commclass" select="commclass" beanName="map" pager="true" />
<!--Where statement removed from preceding: -->
<vegbank:pager /><logic:empty name="commclass-BEANLIST">
<p>  Sorry, no Community Classifications found.</p>
</logic:empty>
<logic:notEmpty name="commclass-BEANLIST">
<logic:iterate id="onerowofcommclass" name="commclass-BEANLIST">
<!-- iterate over all records in set : new table for each -->
<table class="leftrightborders" cellpadding="2">
        <%@ include file="autogen/commclass_detail_data.jsp" %>
        <bean:define id="commclass_pk" name="onerowofcommclass" property="commclass_id" />
<!--Insert a nested get statement here:
   example:   

<vegbank@_colon_@get id="related_table" select="related_table" beanName="map" pager="false" perPage="-1" where="where_commclass_pk" wparam="commclass_pk" />-->
<TR><TD COLSPAN="2">
<vegbank:get id="comminterpretation" select="comminterpretation" beanName="map" pager="false" where="where_commclass_pk" wparam="commclass_pk" perPage="-1" />
<table class="leftrightborders" cellpadding="2">
<tr><th colspan="9">Community Interpretations:</th></tr>
<logic:empty name="comminterpretation-BEANLIST">
<tr><td class="@nextcolorclass@">  Sorry, no Community Interpretations found.</td></tr>
</logic:empty>
<logic:notEmpty name="comminterpretation-BEANLIST">
<tr>
<%@ include file="autogen/comminterpretation_summary_head.jsp" %>
</tr>
<logic:iterate id="onerowofcomminterpretation" name="comminterpretation-BEANLIST">
<tr class="@nextcolorclass@">
<%@ include file="autogen/comminterpretation_summary_data.jsp" %>
</tr>
</logic:iterate>
</logic:notEmpty>
</table>
</TD></TR>
<TR><TD COLSPAN="2">
<vegbank:get id="classcontributor" select="classcontributor" beanName="map" pager="false" where="where_commclass_pk" wparam="commclass_pk" perPage="-1" />
<table class="leftrightborders" cellpadding="2">
<tr><th colspan="9">Classification Contributors:</th></tr>
<logic:empty name="classcontributor-BEANLIST">
<tr><td class="@nextcolorclass@">  Sorry, no Classification Contributors found.</td></tr>
</logic:empty>
<logic:notEmpty name="classcontributor-BEANLIST">
<tr>
<%@ include file="autogen/classcontributor_summary_head.jsp" %>
</tr>
<logic:iterate id="onerowofclasscontributor" name="classcontributor-BEANLIST">
<tr class="@nextcolorclass@">
<%@ include file="autogen/classcontributor_summary_data.jsp" %>
</tr>
</logic:iterate>
</logic:notEmpty>
</table>
</TD></TR>
</table>
<p>&nbsp;</p>
</logic:iterate>
</logic:notEmpty>
<br />
<vegbank:pager />
</body></html>
          @vegbank_footer_html_tworow@
