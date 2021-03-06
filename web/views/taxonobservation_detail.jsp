@webpage_top_html@
  @stdvegbankget_jspdeclarations@
  @webpage_head_html@


<TITLE>View VegBank Data: Taxon Observations - Detail</TITLE>
<script type="text/javascript">
function getHelpPageId() {
  return "interpret-taxon-on-plot";
}

</script>


      <%@ include file="includeviews/inlinestyles.jsp" %> 
 @webpage_masthead_html@
      @possibly_center@
<TABLE width="100%"><TR><TD>
        <h2>View VegBank Taxon Observations</h2>
        </TD><TD class="center">

   <strong>Configure View</strong> <br/>
   <a href="javascript:void setupConfig('<bean:write name='thisviewid' />');">Configure data displayed on this page</a>
     </TD></TR></TABLE>
        
         <% String rowClassReset = "evenrow"; %>
        <vegbank:get id="taxonobservation" select="taxonobservation" beanName="map" pager="true" 
           whereNumeric="where_taxonobservation_pk"/>
<!--Where statement removed from preceding: -->
<vegbank:pager /><logic:empty name="taxonobservation-BEANLIST">
<p>  Sorry, no Taxon Observations found.</p>
</logic:empty>
<logic:notEmpty name="taxonobservation-BEANLIST">
<logic:iterate id="onerowoftaxonobservation" name="taxonobservation-BEANLIST">
<bean:define id="taxonobservation_pk" name="onerowoftaxonobservation" property="taxonobservation_id" />

<!-- iterate over all records in set : new table for each -->
<TABLE cellpadding="2" class="outsideborder" >
<TR><TH colspan="5" class="major">Taxon:<bean:write name='onerowoftaxonobservation' property='authorplantname' /></TH></TR>
<TR><TD colspan="5">

<!-- taxon Obs -->
<table class="leftrightborders" cellpadding="2" width="100%">
        <%@ include file="autogen/taxonobservation_detail_data.jsp" %>
    </table>
</TD>
</TR>


<TR>
<!-- 2 HUGE CAPITALIZED CELLS: TaxInterpret | taxonObs | taxonImportance (w/ stems too) -->
<TD valign="top"><!-- taxoninterpret -->

<vegbank:get id="taxoninterpretation" select="taxoninterpretation_nostem" beanName="map" pager="false" where="where_taxonobservation_pk" wparam="taxonobservation_pk" perPage="-1" />
<table class="leftrightborders" cellpadding="2">
<tr><th colspan="19">Interpretations of this Taxon:</th></tr>
<logic:empty name="taxoninterpretation-BEANLIST">
<tr><td class="@nextcolorclass@">  ERROR! no Taxon Interpretations found.</td></tr>
</logic:empty>
<logic:notEmpty name="taxoninterpretation-BEANLIST">
<!--<tr>
<th>More</th>
<%@ include file="autogen/taxoninterpretation_summary_head.jsp" %>
</tr>-->
<logic:iterate id="onerowoftaxoninterpretation" name="taxoninterpretation-BEANLIST">
<tr class="@nextcolorclass@">
<td>
<a href="@get_link@detail/taxoninterpretation/@subst_lt@bean:write name='onerowoftaxoninterpretation' property='taxoninterpretation_id' /@subst_gt@">
                           MORE DETAILS
                            </a>
</td>
</tr>
<!-- IMPORTANT COMMENT, IT DOES SOMETHING!! This updates the color class so that the next row is the same color as this one: @nextcolorclass@ -->
<tr class="@nextcolorclass@">
<td><!-- all of this in one cell -->
<%@ include file="autogen/taxoninterpretation_notblshort_data.jsp" %>
</td>
</tr>
</logic:iterate>
</logic:notEmpty>

<!-- wanna interpret this differently? -->
<!-- <br/><br/>
<table class="noborder"> -->
<tr>
<td colspan="19" class="useraction">ACTION:
<a href="@web_context@InterpretTaxonObservation.do?tobsAC=<bean:write name='onerowoftaxonobservation' property='accessioncode' />">
Interpret This Plant</a>
</td></tr>
<!-- </table> -->

</table>

</TD>

<TD valign="top"><!-- importance values -->
  
  <vegbank:get id="taxonimportance" select="taxonimportance" beanName="map" pager="false" where="where_taxonimportance_taxonobservation_fk" wparam="taxonobservation_pk" perPage="-1" />
  
  <table class="leftrightborders" cellpadding="2">
  <tr><th colspan="9">Taxon Importance Values:</th></tr>
  <logic:empty name="taxonimportance-BEANLIST">
  <tr><td class="@nextcolorclass@">  Sorry, no Taxon Importances found.</td></tr>
  </logic:empty>
  <logic:notEmpty name="taxonimportance-BEANLIST">
  <tr>
  <%@ include file="autogen/taxonimportance_summary_head.jsp" %>
  <th  class="table_stemsize">Stems:</th><th class="graphic_stemsize">Stem Diameters (graphically):</th>
  </tr>
  <logic:iterate id="onerowoftaxonimportance" name="taxonimportance-BEANLIST">
  <bean:define id="taxonimportance_pk" name="onerowoftaxonimportance" property="taxonimportance_id" />
  <% rowClass = rowClassReset ; %> <!-- reset rowClass, which got off in stems -->
  <tr class="@nextcolorclass@">
  
  <%@ include file="autogen/taxonimportance_summary_data.jsp" %>
  <% rowClassReset = rowClass ; %> <!-- remember where to reset this -->
  <td  class="table_stemsize">
    <!-- start stems -->
    <!-- THIS token 'bumps' the rowClass var to start stems off in same color as last stuff: @nextcolorclass@ -->
    <vegbank:get id="stemcount" select="stemcount" beanName="map" pager="false" 
     where="where_stemcount_taxonimportance_fk" wparam="taxonimportance_pk" perPage="-1" 
     allowOrderBy="true" orderBy="xorderby_stemdiameter_asc" />
  <bean:define id="graphicalStems" value="<!-- init -->" />
  <table class="leftrightborders" cellpadding="2">
	
	<logic:empty name="stemcount-BEANLIST">
	<tr  class="@nextcolorclass@"><td>-none-</td></tr>
	</logic:empty>
	<logic:notEmpty name="stemcount-BEANLIST">
	<tr>
	<%@ include file="autogen/stemcount_summary_head.jsp" %>
	</tr>
	
	<logic:iterate id="onerowofstemcount" name="stemcount-BEANLIST">
	
	<tr class="@nextcolorclass@">
	<%@ include file="autogen/stemcount_summary_data.jsp" %>
	</tr>
  <!-- store graphical stems for later: -->
  <bean:define id="graphicalStems"><bean:write name="graphicalStems" filter="false" />
    <%@ include file="includeviews/sub_stemcount_graphical.jsp" %>
  </bean:define>
  
	</logic:iterate>
	</logic:notEmpty>
	</table>

  <!-- end stems -->
  </td>
  <!-- graphically display stems: -->
  <td class="graphic_stemsize">
   <bean:write name="graphicalStems" filter="false" />
  </td>
  
  </tr>
  </logic:iterate>
  </logic:notEmpty>
  </table>

</TD>
</TR>
</TABLE>
<p>&nbsp;</p>
</logic:iterate>
</logic:notEmpty>
<br />
<vegbank:pager />

 @webpage_footer_html@
