<bean:define id="thisviewid"><%= request.getRequestURI().substring(1+request.getRequestURI().lastIndexOf("/"),request.getRequestURI().indexOf(".")) %></bean:define>
<!-- view: ><bean:write name="thisviewid" />< -->

<!-- this file set the default styles to use for jsp, and edits them 
   according to cookie's contents -->

<!-- plant names are a simple case: -->
  <bean:define id="plantNamesToShowBean">taxobs_curr_scinamenoauth</bean:define > <!-- default -->
  <!-- looking for full -->
  <logic:present cookie="taxon_name_full">
    <!-- getting the cookie value which IS present: -->
    <bean:cookie id="plantNamesToShowCookie" name="taxon_name_full"  value="taxobs_curr_scinamenoauth" /> 
    <!-- if cookie was set, then set to define new bean -->
    <bean:define id="plantNamesToShowBean"><bean:write name="plantNamesToShowCookie" property="value" /></bean:define>
  </logic:present>
  <!-- DEBUG: getcookies got: plantNamesToShowBean: <bean:write name="plantNamesToShowBean" /> -->


 <style type="text/css">
 .<bean:write name="plantNamesToShowBean" /> {visibility: visible;}
 <logic:notEqual name="plantNamesToShowBean" value="taxonobservation_authorplantname" >.taxonobservation_authorplantname { display:none; } </logic:notEqual>
 <logic:notEqual name="plantNamesToShowBean" value="taxonobservation_int_origplantscifull" >.taxonobservation_int_origplantscifull { display:none; }</logic:notEqual>
 <logic:notEqual name="plantNamesToShowBean" value="taxonobservation_int_origplantscinamenoauth" >.taxonobservation_int_origplantscinamenoauth { display:none; }</logic:notEqual>
 <logic:notEqual name="plantNamesToShowBean" value="taxonobservation_int_origplantcode" >.taxonobservation_int_origplantcode { display:none; }</logic:notEqual>
 <logic:notEqual name="plantNamesToShowBean" value="taxonobservation_int_origplantcommon" >.taxonobservation_int_origplantcommon { display:none; }</logic:notEqual>
 <logic:notEqual name="plantNamesToShowBean" value="taxonobservation_int_currplantscifull" >.taxonobservation_int_currplantscifull { display:none; }</logic:notEqual>
 <logic:notEqual name="plantNamesToShowBean" value="taxonobservation_int_currplantscinamenoauth" >.taxonobservation_int_currplantscinamenoauth { display:none; } </logic:notEqual>
 <logic:notEqual name="plantNamesToShowBean" value="taxonobservation_int_currplantcode" >.taxonobservation_int_currplantcode { display:none; }</logic:notEqual>
 <logic:notEqual name="plantNamesToShowBean" value="taxonobservation_int_currplantcommon" >.taxonobservation_int_currplantcommon { display:none; }</logic:notEqual>
 </style> 
 
 <% String theCookieVal=null; %>
 <!-- cookies controlled by db table -->
 <vegbank:get id="cookie" select="dba_cookie" beanName="map" pager="false" perPage="-1"
   where="where_cookie_view" wparam="thisviewid" />
 <logic:notEmpty name="cookie-BEANLIST">
   <!-- comments need to stay up here, not in style part -->
    
   <% 
    // get the cookies, but only once 
    Cookie[] cookies = request.getCookies(); 
    %>
   
   <style type="text/css">
     <logic:iterate id="onerowofcookie" name="cookie-BEANLIST">
       <bean:define id="checkforcookie"><bean:write name="onerowofcookie" property="fullcookiename" /></bean:define>
       <bean:define id="defaultval"><bean:write name="onerowofcookie" property="defaultvalue" /></bean:define>
       <% 
       theCookieVal=null; 
       for(int i=0;i<cookies.length;i++){ 
            if(cookies[i].getName().equals(checkforcookie)){ 
             theCookieVal = cookies[i].getValue(); 
             
            } 
       }
        if (theCookieVal == null) {
           theCookieVal=defaultval ;
           
       } 
        if (theCookieVal.equals("hide")) {
        %>
         .<bean:write name="onerowofcookie" property="cookiename" /> { display:none; }
        <%
        }
       
       %>

     </logic:iterate>
   </style>
 </logic:notEmpty>
  
  <!--logic:equal name="stemTableToShowBean" value="false"> .table_stemsize { display:none;} < /logic:equal-->
  <!--logic:equal name="stemGraphicToShowBean" value="false"> .stemsize_graphic { display:none;} < /logic:equal--> 
  
  </style>