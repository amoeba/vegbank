/*
var loadFrom = false;
function include(aFile)
{
    if (loadFrom && loadFrom.parent) {
        loadFrom.parent.removeChild(loadFrom);
    }
    loadFrom=document.createElement("script");
    loadFrom.setAttribute("src",aFile);
    document.getElementsByTagName('head').item(0).appendChild(loadFrom);
}
*/
var hIncludes = null;
function include(sURI)
{
  if (document.getElementsByTagName)
  {
    if (!hIncludes)
    {
      hIncludes = {};
      var cScripts = document.getElementsByTagName("script");
      for (var i=0,len=cScripts.length; i < len; i++)
        if (cScripts[i].src) hIncludes[cScripts[i].src] = true;
    }
    if (!hIncludes[sURI])
    {
      var oNew = createElement("script");
      oNew.type = "text/javascript";
      oNew.src = sURI;
      hIncludes[sURI]=true;
      document.getElementsByTagName("head")[0].appendChild(oNew);
    }
  }
}

function toggle(e) {
	if (e.checked) {
		highlight(e);
	} else {
		unhighlight(e);
	}
}

function checkAll(checkboxName) {
	var form = document.theform;
	var len = form.elements.length;
	for (var i = 0; i < len; i++) {
		var e = form.elements[i];
		if (e.name == checkboxName) {
			check(e);
		}
	}
}

function clearAll(checkboxName) {
	var form = document.theform;
	var len = form.elements.length;
	for (var i = 0; i < len; i++) {
		var e = form.elements[i];
		if (e.name == checkboxName) {
			clear(e);
		}
	}
}

function highlight(e) {
	var r = null;
	if (e.parentNode && e.parentNode.parentNode) {
		r = e.parentNode.parentNode;
	}
	else if (e.parentElement && e.parentElement.parentElement) {
		r = e.parentElement.parentElement;
	}
	if (r) {
		if (r.className == "listRowA") {
			r.className = "listRowA-Hi";
		} else if (r.className == "listRowB") {
			r.className = "listRowB-Hi";
		}
	}
}

function unhighlight(e) {
	var r = null;
	if (e.parentNode && e.parentNode.parentNode) {
		r = e.parentNode.parentNode;
	}
	else if (e.parentElement && e.parentElement.parentElement) {
		r = e.parentElement.parentElement;
	}
	if (r) {
		if (r.className == "listRowA-Hi") {
			r.className = "listRowA";
		} else if (r.className == "listRowB-Hi") {
			r.className = "listRowB";
		}
	}
}

function check(e) {
	e.checked = true;
	highlight(e);
}

function clear(e) {
	e.checked = false;
	unhighlight(e);
}

function refreshHighlight(checkboxName) {
	var form = document.theform;
	var len = form.elements.length;
	for (var i = 0; i < len; i++) {
		var e = form.elements[i];
		if (e.name == checkboxName && e.checked == true) {
			highlight(e);
		}
	}
}


function MM_jumpMenu(targ,selObj,restore){ //v3.0
  eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
  if (restore) selObj.selectedIndex=0;
}


/************************/
/* MOUSE OVER FUNCTIONS */
/************************/
function MM_preloadImages() { //v3.0
	  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
		      var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
				      if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
	  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
	  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
		      d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
	    if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
		  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
		    if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
	  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
		     if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}


/*************************************************************************************************/
/*  the following from http://www.kryogenix.org/code/browser/sorttable/   on about 2004-DEC-01   */
/*************************************************************************************************/

addEvent(window, "load", sortables_init);

var SORT_COLUMN_INDEX;

function sortables_init() {
    // Find all tables with class sortable and make them sortable
    if (!document.getElementsByTagName) return;
    tbls = document.getElementsByTagName("table");
    for (ti=0;ti<tbls.length;ti++) {
        thisTbl = tbls[ti];
        if (((' '+thisTbl.className+' ').indexOf("sortable") != -1) && (thisTbl.id)) {
            //initTable(thisTbl.id);
            ts_makeSortable(thisTbl);
        }
    }
}

function ts_makeSortable(table) {
    if (table.rows && table.rows.length > 0) {
        var firstRow = table.rows[0];
    }
    if (!firstRow) return;
    
    // We have a first row: assume it's the header, and make its contents clickable links
    for (var i=0;i<firstRow.cells.length;i++) {
        var cell = firstRow.cells[i];
        var txt = ts_getInnerText(cell);
        cell.innerHTML = '<a href="#" class="sortlink" onclick="ts_resortTable(this);return false;">'+txt+'<span class="sortarrow"></span></a>';
    }
}

function ts_getInnerText(el) {
	if (typeof el == "string") return el;
	if (typeof el == "undefined") { return el };
	if (el.innerText) return el.innerText;	//Not needed but it is faster
	var str = "";
	
	var cs = el.childNodes;
	var l = cs.length;
	for (var i = 0; i < l; i++) {
		switch (cs[i].nodeType) {
			case 1: //ELEMENT_NODE
				str += ts_getInnerText(cs[i]);
				break;
			case 3:	//TEXT_NODE
				str += cs[i].nodeValue;
				break;
		}
	}
	return str;
}

function ts_resortTable(lnk) {
    // get the span
    var span;
    for (var ci=0;ci<lnk.childNodes.length;ci++) {
        if (lnk.childNodes[ci].tagName && lnk.childNodes[ci].tagName.toLowerCase() == 'span') span = lnk.childNodes[ci];
    }
    var spantext = ts_getInnerText(span);
    var td = lnk.parentNode;
    var column = td.cellIndex;
    var table = getParent(td,'TABLE');
    
    // Work out a type for the column
    if (table.rows.length <= 1) return;
    var itm = ts_getInnerText(table.rows[1].cells[column]);
    sortfn = ts_sort_caseinsensitive;
    if (itm.match(/^\d\d[\/-]\d\d[\/-]\d\d\d\d$/)) sortfn = ts_sort_date;
    if (itm.match(/^\d\d[\/-]\d\d[\/-]\d\d$/)) sortfn = ts_sort_date;
    if (itm.match(/^[�$]/)) sortfn = ts_sort_currency;
    if (itm.match(/^[\d\.]+$/)) sortfn = ts_sort_numeric;
    if (itm.match(/^[\s]*[\d\.]+[ \%]*/)) sortfn = ts_sort_numeric;
    SORT_COLUMN_INDEX = column;
    var firstRow = new Array();
    var newRows = new Array();
    for (i=0;i<table.rows[0].length;i++) { firstRow[i] = table.rows[0][i]; }
    for (j=1;j<table.rows.length;j++) { newRows[j-1] = table.rows[j]; }

    newRows.sort(sortfn);

    if (span.getAttribute("sortdir") == 'down') {
        ARROW = '&uarr;';
        newRows.reverse();
        span.setAttribute('sortdir','up');
    } else {
        ARROW = '&darr;';
        span.setAttribute('sortdir','down');
    }
    
    // We appendChild rows that already exist to the tbody, so it moves them rather than creating new ones
    // don't do sortbottom rows
    for (i=0;i<newRows.length;i++) { if (!newRows[i].className || (newRows[i].className && (newRows[i].className.indexOf('sortbottom') == -1))) table.tBodies[0].appendChild(newRows[i]);}
    // do sortbottom rows only
    for (i=0;i<newRows.length;i++) { if (newRows[i].className && (newRows[i].className.indexOf('sortbottom') != -1)) table.tBodies[0].appendChild(newRows[i]);}
    
    // Delete any other arrows there may be showing
    var allspans = document.getElementsByTagName("span");
    for (ci=0;ci<allspans.length;ci++) {
        if (allspans[ci].className == 'sortarrow') {
            if (getParent(allspans[ci],"table") == getParent(lnk,"table")) { // in the same table as us?
                allspans[ci].innerHTML = '';
            }
        }
    }
        
    span.innerHTML = ARROW;
}

function getParent(el, pTagName) {
	if (el == null) return null;
	else if (el.nodeType == 1 && el.tagName.toLowerCase() == pTagName.toLowerCase())	// Gecko bug, supposed to be uppercase
		return el;
	else
		return getParent(el.parentNode, pTagName);
}
function ts_sort_date(a,b) {
    // y2k notes: two digit years less than 50 are treated as 20XX, greater than 50 are treated as 19XX
    aa = ts_getInnerText(a.cells[SORT_COLUMN_INDEX]);
    bb = ts_getInnerText(b.cells[SORT_COLUMN_INDEX]);
    if (aa.length == 10) {
        dt1 = aa.substr(6,4)+aa.substr(3,2)+aa.substr(0,2);
    } else {
        yr = aa.substr(6,2);
        if (parseInt(yr) < 50) { yr = '20'+yr; } else { yr = '19'+yr; }
        dt1 = yr+aa.substr(3,2)+aa.substr(0,2);
    }
    if (bb.length == 10) {
        dt2 = bb.substr(6,4)+bb.substr(3,2)+bb.substr(0,2);
    } else {
        yr = bb.substr(6,2);
        if (parseInt(yr) < 50) { yr = '20'+yr; } else { yr = '19'+yr; }
        dt2 = yr+bb.substr(3,2)+bb.substr(0,2);
    }
    if (dt1==dt2) return 0;
    if (dt1<dt2) return -1;
    return 1;
}

function ts_sort_currency(a,b) { 
    aa = ts_getInnerText(a.cells[SORT_COLUMN_INDEX]).replace(/[^0-9.]/g,'');
    bb = ts_getInnerText(b.cells[SORT_COLUMN_INDEX]).replace(/[^0-9.]/g,'');
    return parseFloat(aa) - parseFloat(bb);
}

function ts_sort_numeric(a,b) { 
    aa = parseFloat(ts_getInnerText(a.cells[SORT_COLUMN_INDEX]));
    if (isNaN(aa)) aa = 0;
    bb = parseFloat(ts_getInnerText(b.cells[SORT_COLUMN_INDEX])); 
    if (isNaN(bb)) bb = 0;
    return aa-bb;
}

function ts_sort_caseinsensitive(a,b) {
    aa = ts_getInnerText(a.cells[SORT_COLUMN_INDEX]).toLowerCase();
    bb = ts_getInnerText(b.cells[SORT_COLUMN_INDEX]).toLowerCase();
    if (aa==bb) return 0;
    if (aa<bb) return -1;
    return 1;
}

function ts_sort_default(a,b) {
    aa = ts_getInnerText(a.cells[SORT_COLUMN_INDEX]);
    bb = ts_getInnerText(b.cells[SORT_COLUMN_INDEX]);
    if (aa==bb) return 0;
    if (aa<bb) return -1;
    return 1;
}


// addEvent and removeEvent
// cross-browser event handling for IE5+,  NS6 and Mozilla
// By Scott Andrew
function addEvent(elm, evType, fn)
{
    if (evType.substring(0,2) == "on") {
        evType = evType.substring(2);
    }

    if (elm.addEventListener){
        elm.addEventListener(evType, fn, true);
        return true;
    } else if (elm.attachEvent){
        var r = elm.attachEvent("on"+evType, fn);
        return r;
    } else {
        alert("Handler could not be removed");
    }

    return false;
}


/****************************************************************************/
/*  the preceding from http://www.kryogenix.org/code/browser/sorttable/     */
/****************************************************************************/

function removeEvent(obj, evType, fn, useCapture){
  if (obj.removeEventListener){
    obj.removeEventListener(evType, fn, useCapture);
    return true;
  } else if (obj.detachEvent){
    var r = obj.detachEvent("on"+evType, fn);
    return r;
  } else {
    alert("Handler could not be removed");
  }
}



/***********************************************************************************************/
/*  the following from http://johnkerry.com/js/kerry.js  on 2004-DEC-03   function showtwo     */
/***********************************************************************************************/


function showorhidediv(theid)
{
	var el = document.getElementById(theid);
	if (el.style.display == 'none')
	{
		el.style.display = 'block';

	} else {
		el.style.display = 'none';
		
	}
}


/****************************************************************************/
/*  the preceding from http://johnkerry.com/js/kerry.js  on 2004-DEC-03     */
/****************************************************************************/


function defaultOnLoad() {
    try { 
        // this function must be defined somewhere else
        customOnLoad(); 
    } catch (e) { 
        // forget about it
    }
}


/**
 * Incredibly useful function.
 */
function getURLParam(strParamName) {
    var strReturn = "";
    var strHref = window.location.href.toLowerCase();
    if (strHref.indexOf("?") > -1) {
        var strQueryString = strHref.substr(strHref.indexOf("?")).toLowerCase();
        var aQueryString = strQueryString.split("&");
        for ( var iParam = 0; iParam < aQueryString.length; iParam++ ) {
            if (aQueryString[iParam].indexOf(strParamName.toLowerCase() + "=") > -1 ) {
                var aParam = aQueryString[iParam].split("=");
                strReturn = aParam[1];
                break;
            }
        }
    }
    return strReturn;
}



//
// Initialize the ajax engine
//
function initAjax() {
    var ajax = new XHConn();
    if (!ajax) {
        // this client lacks XMLHttp support
        alert("Sorry, there was a problem using VegBank. " +
                "Please upgrade your browser to take advantage of many features on the Web.");
    }
	return ajax;
}


//
// Use this to get the items in a list
//
function getValuesFromList(thelist, getValueOrText) {
  //the list is the form object that is a <select>
  // getValueOrText is either "value" or "text"
  //  -- if "value" then the values are listed (i.e. <option value="THIS">not this</option> )
  //  -- if "text"  then the text shown are listed (i.e. <option value="not this">BUT THIS</option> )
  listvalues = "";
  hasvalues = "false"; /*default*/
  strSeparator = ", ";
  for (var i=0;i<thelist.length;i++) {
    if (thelist.options[i].selected==true) {
      hasvalues = "true"; /* has some values, make sure to return something */
      if ( getValueOrText == "value" ) {
        listvalues = listvalues + strSeparator + thelist.options[i].value ;
      }  
      if ( getValueOrText == "text" ) {
        if ( thelist.options[i].text != "--ANY--" ) 
          {
            listvalues = listvalues + strSeparator + thelist.options[i].text ;
          }
      }  
      
    }
  }
  if ( hasvalues == "true" ) {
    /* window.alert("value is:>" + listvalues.substring(strSeparator.length) + "<"); */
    return(listvalues.substring(strSeparator.length));  /* remove initial strSeparator if there */
  } else {
      /* return nothing, but should edit list to selected something with value = "" .  This just helps the xwhere_query.  */
        fixednull = "false";
        for (var j=0;j<thelist.length;j++) {
          if (thelist.options[j].value=="" && fixednull == "false" ) {
            thelist.options[j].selected = true;
            fixednull = "true" ;
          }
        }
      
      return("");
  
  }
}


/** * @(#)isNumeric.js * * Copyright (c) 2000 by Sundar Dorai-Raj
  * * @author Sundar Dorai-Raj
  * * Email: sdoraira@vt.edu
  * * This program is free software; you can redistribute it and/or
  * * modify it under the terms of the GNU General Public License 
  * * as published by the Free Software Foundation; either version 2 
  * * of the License, or (at your option) any later version, 
  * * provided that any use properly credits the author. 
  * * This program is distributed in the hope that it will be useful,
  * * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
  * * GNU General Public License for more details at http://www.gnu.org * * */

  var numbers=".0123456789";
  function isNumeric(y) {
    // is x a String or a character?
     var x = y+"";
    if(x.length>1) {
      // remove negative sign
      x=Math.abs(x)+"";
      for(j=0;j<x.length;j++) {
        // call isNumeric recursively for each character
        number=isNumeric(x.substring(j,j+1));
        if(!number) return number;
      }
      return number;
    }
    else {
      // if x is number return true
      if(numbers.indexOf(x)>=0) return true;
      return false;
    }
  }
  
  
  

  
  
  function VB_isDate(y) {
      // function written by Michael Lee 2005-05-07 (and that IS a valid date)
      // is this a valid date?
     blnFine=false;
     var x = y+"";
       var mytool_array=x.split("/"); 
       
       if ( mytool_array.length == 3 ) 
       {
         if  ( isNumeric(mytool_array[0]) && isNumeric(mytool_array[1]) && isNumeric(mytool_array[2]) )                
         {  
  
           if ( mytool_array[0]>0 && mytool_array[0]<13 &&  mytool_array[1]>0 && mytool_array[1]<32  &&  mytool_array[2]>0 && mytool_array[2]<10000) {
           // assume ok
           blnFine = true;
                if ( mytool_array[1]==31 && ( mytool_array[0]==6 || mytool_array[0]==4 ||mytool_array[0]==9 ||mytool_array[0]==11  ) ) {
                // not ok for 31 in these months
                blnFine = false;
                }
                if (mytool_array[1]>29 && mytool_array[0]==2) {
                //not ok in Feb
                blnFine = false;
                }
                if (mytool_array[1]==29 && ( (mytool_array[2])/4 != Math.floor(mytool_array[2]/4) ) ) {
                   blnFine = false ; //not leap year
                }
  
           }
  
  
         }
  
       }   
    return blnFine;
  }
  

//******** JAVASCRIPT  FOR  FORM  VALIDATION ***********************//


function validateThisForm(thisform) {
       /* This function validates this form as best it can, for now that means checking to see
       if numeric fields are numeric (based on className=number) and dates are in right format, too */
              blnIsValid = true;
              var numelements = thisform.elements.length;
              var item;
              for (var i=0 ; i<numelements ; i++) {
                  item = thisform.elements[i];
                  if ( (item.className == "number" || item.className == "errNumber" ) && blnIsValid ) {
                  // should be number and only check until there is one error
                        // check to see if numeric:
                        if( isNumeric( item.value) || item.value == null || item.value == "" ) {
                            //is ok if numeric, null or empty string
                            item.className = "number";
                          }
                          else
                          {
                        item.className = "errNumber" ;
                        item.focus();
                        alert("You entered an invalid number: " + item.value + " Please fix this and try again.");  
                        
                        blnIsValid = false;
                        }  
                  } 
                  //check dates:
                  if ( (item.className == "date" || item.className == "errDate" ) && blnIsValid ) {
                         // should be number and only check until there is one error
                            // check to see if date:
                            if( VB_isDate(item.value) || item.value == null || item.value == "" ) {
                                //is ok if numeric, null or empty string
                                item.className = "date";
                              }
                              else
                              {
                            item.className = "errDate" ;
                            item.focus();
                            alert("You entered an invalid date: " + item.value + " Please format like MM/DD/YYYY, example: 06/30/1977.");  
                            
                            blnIsValid = false;
                            }  
                  }
              }
        return blnIsValid;    
}


function delay(ms) {
    var d = new Date(), mill, diff;
    while (1) {
        mill = new Date();
        diff = mill - d;
        if(diff > ms) { break; }
    }
}


function postNewParam(theName,theVal) {
  // this uses the resubmitForm Form in the page that should be put there by vegbank(colon)resubmitForm
  // to send user to new location, but using the posted instead of URL parameters.
  resubmitform = document.forms.resubmitForm ;
  var numelements = resubmitform.elements.length;
  
  var wasDone = "false" ;
    for (var i=0 ; i<numelements ; i++) {
      if (resubmitform.elements[i].name == theName ) {
        resubmitform.elements[i].value = theVal ;
        wasDone = "true" ;
      }
    }
    
  if ( wasDone == "false") {
    //didnt get done, add new input to form (tricky)
    resubmitform.placeholder.name = theName ;
    resubmitform.placeholder.value = theVal ;
  }
  resubmitform.submit();

}

function highlightADiv(divToHighlight) {
  /* highlights the div */
  document.getElementById(divToHighlight).className="highlight" ;
}


/*
createElement function found at http://simon.incutio.com/archive/2003/06/15/javascriptWithXML
*/
function createElement(element) {
    if (typeof document.createElementNS != 'undefined') {
        return document.createElementNS('http://www.w3.org/1999/xhtml', element);
    }
    if (typeof document.createElement != 'undefined') {
        return document.createElement(element);
    }
    return false;
}


