@stdvegbankget_jspdeclarations@
<% String rowClass = "evenrow" ; %>

<!-- this is the raw one -->
<!--  request.getRequestURL() |  request.getQueryString()  -->
<vegbank:get id="commclass" select="commclass" beanName="map" 
    where="where_observation_pk" wparam="observation_pk" pager="false" />
<%@ include file="../includeviews/sub_commclass_summary.jsp" %>