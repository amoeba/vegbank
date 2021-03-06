@webpage_top_html@
@stdvegbankget_jspdeclarations@
@webpage_head_html@
<title>VegBank</title>
<script type="text/javascript">
function getHelpPageId() {
  return "find-plots";
}

</script>

@webpage_masthead_html@

    <div class="spacer">&nbsp;</div>
    <%@ include file="includes/display_user_messages.jsp" %>

    <div id="content-3col-a">
    <fieldset id="tut_findplots">
    <legend>Find Plots</legend>
    <ul>

    <li><a href="@browseplotspage@">Browse plots</a></li>
    <li><a href="@plotquery_page_simple@">Simple search</a></li>
    <li><a href="@views_link@plot-query-bymap.jsp">Search with a map</a></li>
    <li><a href="@plotquery_page_advanced@">Advanced plot search</a></li>
    </ul>
     <%@ include file="includes/plot-map-northamerica-home.jsp" %>
     <%@ include file="includes/plot-map-northamerica-key.jsp" %>
    </fieldset>
    <br />
    <fieldset id="tut_recentlyaddedplots">
      <legend>Recently Added Plots</legend>
      <!--bean:include id="recentprojects_new" href="@machine_url@@views_link@raw/raw_recentprojects.jsp?blah=true" /-->
      <!--bean:write name="recentprojects_new" filter="false" /-->
      <%@ include file="views/includeviews/sub_recentprojects.jsp" %>
    </fieldset>
    </div>


    <div id="content-3col-b">
    <fieldset id="tut_planttaxa">
    <legend>Plant Taxa</legend>
    <ul>
    <li><a href="@general_link@faq.html#whatconcept">What is a plant concept?</a></li>
    <li><a href="@general_link@browseplants.jsp">Browse plants</a></li>
    <li><a href="@forms_link@PlantQuery.jsp">Search plants</a></li>
    <li><a href="@DisplayUploadPlotAction@">Submit plants</a></li>
    </ul>
    </fieldset>

    <br />
    <fieldset id="tut_plantcommunities">
    <legend>Plant Communities</legend>
    <ul>
    <li><a href="@general_link@faq.html#whatcommunity">What is a community?</a></li>
    <li><a href="@forms_link@CommQuery.jsp">Search communities</a></li>
    <!--li><a href="xxx">Browse communities</a></li-->
    <li><a href="@DisplayUploadPlotAction@">Submit communities</a></li>
    </ul>
    </fieldset>

    <br />
    <fieldset id="tut_supplemental">
    <legend>Supplemental Data</legend>
    <ul>
    <li><a href="@get_link@std/party">People</a></li>
    <li><a href="@get_link@std/stratummethod">Stratum methods</a></li>
    <li><a href="@get_link@std/covermethod">Cover methods</a></li>
    <li><a href="@get_link@std/project">Projects</a></li>
    <li><a href="@get_link@std/reference">References</a></li>
    <!-- there isn't actually any more data there : <li><a href="@general_link@metadata.html">More Data</a></li> -->
    <li><a href="@forms_link@metasearch.jsp">Search supplemental data</a></li>
    </ul>
    </fieldset>
     <br/>
    <fieldset id="tut_datainvegbank">
      <legend>Data in VegBank</legend>
      <ul><li>
      <!--bean:include id="countdata" page="/cache/views/raw/raw_countdata.jsp" /-->
      <!--bean:write name="countdata" filter="false" /-->
      <%@ include file="views/includeviews/sub_countdata_cache.jsp" %>
      </li></ul>
    </fieldset>


    </div>



    <div id="content-3col-c">

     <%@ include file="includes/news.html" %>
        <br />


        <fieldset id="tut_myaccount">
        <legend>My VegBank Account</legend>
        <ul>
        <li><a href="@web_context@LoadUser.do">Edit profile information</a></li>
        <li><a href="@general_link@datasets.html">Manage datasets</a></li>
        </ul>
        </fieldset>
        <br />


    <fieldset id="tut_learnabout">
    <legend>Learn About VegBank</legend>
    <ul>
    <li><a href="@general_link@info.html">What is VegBank?</a></li>
    <li><a href="@general_link@faq.html#whatisplot">What is a plot?</a></li>
    <li><a href="@general_link@faq.html">FAQ</a></li>
    <li><a href="@jspHelpPage@">Tutorial</a></li>
    <li><a href="@general_link@cite.html">Cite or link to VegBank</a></li>
    <li><a href="@general_link@terms.html">Terms of use</a></li>
    <li><a href="@general_link@sitemap.html">Site map</a></li>
    <li><a href="@general_link@contact.html">Contact</a></li>
    </ul>
    </fieldset>

    <br />
    <fieldset id="tut_contributeplots">
    <legend>Contribute Plot Data</legend>
    <ul>
    <li><a href="@DisplayUploadPlotAction@">Submit plots</a></li>
    <li><a  href="@general_link@annotate.html">Annotate plots</a></li>
   <!--
    add this back when we can do this -->
    <!-- <li><a href="???">Classify plots</a></li> -->
    </ul>
    </fieldset>

    <br />
    <fieldset id="tut_tools">
    <legend>Tools</legend>
    <ul>
    <!--<li><a href="/nvcrs/">Vegetation Classification</a></li>-->
    <li><a href="@views_link@map_userplots.jsp">Map your own plots</a></li>
    <li><a href="@vegbranch_link@vegbranch.html">VegBranch client database</a></li>
    <li><a href="@vegbranch_link@docs/normalizer/normalizer.html">Data matrix normalizer</a></li>
    </ul>
    </fieldset>



    <%
      Boolean isAdmin = (Boolean)(request.getSession().getAttribute("isAdmin"));

      if (isAdmin != null) {
        if (isAdmin.booleanValue()) {
    %>
      <br/>
       <!-- Admin -->
        <fieldset id="tut_adminmenu">
        <legend>ADMINISTRATION</legend>
        <ul>
        <li><html:link action="AdminMenu.do">Admin Menu</html:link></li>
        <li>See the current <a href="@forms_link@system-status.jsp">System Status</a></li>
        <li>Check <a href="@forms_link@businessrules.jsp">Business Rules</a></li>
        </ul>
        </fieldset>

    <%
        }
      }
%>
 </div>


@webpage_footer_html@
