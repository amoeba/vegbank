@webpage_top_html@
  @stdvegbankget_jspdeclarations@
  @webpage_head_html@
  <!-- variables for this page -->
<% String rowClass = "evenrow"; %>
<% String currentNum = ""; %> <!-- what number am I on? -->
<% String allNums = "12345"; %> <!-- what set of string represents all numbers? -->
<% String howManyPerPage = "6" ; %> <!-- how many records to show perPage intially? -->
<% String howManyPerPageMore = "20" ; %> <!-- how many records to show if only showing one item? -->
 
<TITLE>Browse VegBank Data</TITLE>




@webpage_masthead_html@
  <h1>Browse VegBank Data</h1>

 <!-- can pass param (or any combination): getonly=0 (all)
                      getonly=1 (namedplace)
                      getonly=2 (party)
                      getonly=3 (project)
                      getonly=4 (plants)
                      getonly=5 (community) -->
  <logic:notPresent parameter="getonly">
    <!-- write all if this is absent -->
    <bean:define id="writetops" value="<%= allNums %>" />
  </logic:notPresent>
  <logic:present parameter="getonly">
    <bean:parameter id="writetops" name="getonly" /> <!-- default to param value -->
  <logic:equal parameter="getonly" value="0">
     <!-- write all if this is 0 -->
     <bean:define id="writetops" value="<%= allNums %>" />
  </logic:equal>
  </logic:present>
  
  <logic:notEqual name="writetops" value="<%= allNums %>">
    <p><a href="@views_link@topdata.jsp?getonly=0&perPage=<%= howManyPerPage %>" >Show all types of data</a></p>
  </logic:notEqual>
  
  <%  currentNum = "1"; %>
  
  <logic:match name="writetops" value="<%= currentNum %>">
  <vegbank:get id="namedplace" select="browsenamedplacebystate" beanName="map" 
    pager="true" />
  <logic:notEmpty name="namedplace-BEANLIST">

  <TABLE cellpadding="5" cellspacing="3">
  <TR><TD colspan="3"><h3>Where Plots Are:</h3></TD></TR>
  <TR valign="top"><TD>

  
  <table class="thinlines" cellpadding="2"> 
   <%@ include file="includeviews/sub_namedplace_states.jsp" %>
  
     
   </table>
   
  
  <logic:notEqual name="writetops" value="<%= currentNum %>">
    <p class="psmall"><a href="@views_link@topdata.jsp?getonly=<%= currentNum %>&perPage=<%= howManyPerPageMore %>">More Places...</a></p>
    </logic:notEqual>
 <logic:equal name="writetops" value="<%= currentNum %>">
   <vegbank:pager />
 </logic:equal>
 

 
 
 </TD><TD>
 <bean:include id="mapfile" href="@forms_link@plot-map-northamerica-sml.jsp" />
 <bean:write name="mapfile" filter="false" />
 </TD></TR>
 </TABLE>
 </logic:notEmpty> 
 </logic:match>
 <!-- end of section 1 -->
 
 
 <!-- section 2 who -->
 <%  currentNum = "2"; %>
 <logic:match name="writetops" value="<%= currentNum %>">
  <h3>Who contributed plots:</h3>
   
     <vegbank:get id="browseparty" select="browseparty" 
         beanName="map" pager="true"  xwhereEnable="false" />
     <bean:define id="onlytotalplots" value="yes" />
     <%@ include file="includeviews/sub_party_plotcount.jsp" %>
   
    <logic:notEqual name="writetops" value="<%= currentNum %>">
       <p class="psmall"><a href="@views_link@topdata.jsp?getonly=<%= currentNum %>&perPage=<%= howManyPerPageMore %>">More People...</a></p>  
    </logic:notEqual>
    <logic:equal name="writetops" value="<%= currentNum %>">
      <vegbank:pager />
    </logic:equal>
   
   
  
  </logic:match>
  
  <%  currentNum = "3"; %>
  <logic:match name="writetops" value="<%= currentNum %>">
  <!-- projects -->
  <h3>Data Projects:</h3>
    <vegbank:get id="project" select="project" beanName="map" pager="true" xwhereEnable="false" /> 
    <table class="thinlines" cellpadding="2">
      <tr><th>Project</th><th>Plots</th></tr>
      <logic:iterate id="onerowofproject" name="project-BEANLIST">
        <tr  class='@nextcolorclass@'>
          <td><a href='@get_link@std/project/<bean:write name="onerowofproject" property="project_id" />'><bean:write name="onerowofproject" property="projectname" /></a></td>
          <td class="numeric"><a href="@get_link@summary/observation/<bean:write name='onerowofproject' property='project_id' />?where=where_project_pk"><bean:write name="onerowofproject" property="countobs" /></a></td>
        </tr>
      </logic:iterate>
      
    </table>
      <logic:notEqual name="writetops" value="<%= currentNum %>">
         <p class="psmall"><a href="@views_link@topdata.jsp?getonly=<%= currentNum %>&perPage=<%= howManyPerPageMore %>">More Projects...</a></p> 
      </logic:notEqual>
      <logic:equal name="writetops" value="<%= currentNum %>">
        <vegbank:pager />
    </logic:equal>
  
  
  </logic:match>
  
  <%  currentNum = "4"; %>
  <logic:match name="writetops" value="<%= currentNum %>">
  <!-- spp -->
 <h3>Common Species:</h3>
   <vegbank:get id="browsecommonplants" select="browsecommonplants" 
          beanName="map" pager="true"  xwhereEnable="false" />
     <table class="thinlines" cellpadding="2">
      <tr><th>Plant</th><th>Plots</th></tr>
      <logic:iterate id="onerowofbrowsecommonplants" name="browsecommonplants-BEANLIST">
	          <tr class='@nextcolorclass@'>
	            <td><a href="@get_link@std/plantconcept/<bean:write name='onerowofbrowsecommonplants' property='plantconcept_id' />"><bean:write name="onerowofbrowsecommonplants" property="plantconcept_id_transl" /></td>
	            <td class="numeric"><a href="@get_link@summary/observation/<bean:write name='onerowofbrowsecommonplants' property='plantconcept_id' />?where=where_plantconcept_observation_complex"><bean:write name="onerowofbrowsecommonplants" property="countobs" /></a></td>
	          </tr>
      </logic:iterate>
     
     </table>
     
           <logic:notEqual name="writetops" value="<%= currentNum %>">
	          <p class="psmall"><a href="@views_link@topdata.jsp?getonly=<%= currentNum %>&perPage=<%= howManyPerPageMore %>">More Plants...</a></p> 
	       </logic:notEqual>
	       <logic:equal name="writetops" value="<%= currentNum %>">
	         <vegbank:pager />
           </logic:equal>
    </logic:match>
   
   <%  currentNum = "5"; %>
   <logic:match name="writetops" value="<%= currentNum %>">
   <!-- comms -->
   <h3>Common Communities:</h3>
   <vegbank:get id="browsecommoncomms" select="browsecommoncomms" 
             beanName="map" pager="true"  xwhereEnable="false" />
        <table class="thinlines" cellpadding="2">
         <tr><th>Community</th><th>Plots</th></tr>
         <logic:iterate id="onerowofbrowsecommoncomms" name="browsecommoncomms-BEANLIST">
   	          <tr class='@nextcolorclass@'>
   	            <td><a href="@get_link@std/commconcept/<bean:write name='onerowofbrowsecommoncomms' property='commconcept_id' />"><bean:write name="onerowofbrowsecommoncomms" property="commconcept_id_transl" /></a></td>
   	            <td class="numeric"><a href="@get_link@summary/observation/<bean:write name='onerowofbrowsecommoncomms' property='commconcept_id' />?where=where_commconcept_observation_complex"><bean:write name="onerowofbrowsecommoncomms" property="countobs" /></a></td>
   	          </tr>
         </logic:iterate>
        
        </table>
        
           <logic:notEqual name="writetops" value="<%= currentNum %>">
	          <p class="psmall"><a href="@views_link@topdata.jsp?getonly=<%= currentNum %>&perPage=<%= howManyPerPageMore %>">More Communities...</a></p> 
	       </logic:notEqual>
	       <logic:equal name="writetops" value="<%= currentNum %>">
	         <vegbank:pager />
           </logic:equal>
    </logic:match>
   
 


@webpage_footer_html@