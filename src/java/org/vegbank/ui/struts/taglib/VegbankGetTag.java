/*
 *	'$RCSfile: VegbankGetTag.java,v $'
 *	Authors: @author@
 *	Release: @release@
 *
 *	'$Author: anderson $'
 *	'$Date: 2004-10-12 17:50:40 $'
 *	'$Revision: 1.10 $'
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
 */

package org.vegbank.ui.struts.taglib;


import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.Format;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;
import org.apache.struts.taglib.bean.WriteTag;

import org.vegbank.common.command.GenericCommand;
import org.vegbank.common.utility.Utility;

/**
 * Tag that queries the DB and puts a map or bean in the 
 * page context's servlet request object.
 *
 * @author P. Mark Anderson
 * @version $Revision: 1.10 $ $Date: 2004-10-12 17:50:40 $
 */

public class VegbankGetTag extends VegbankTag {

	private static final Log log = LogFactory.getLog(VegbankGetTag.class);


    /**
     * Process the start tag.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {

		//log.debug("Using <vegbank:get>");

		if (Utility.isStringNullOrEmpty(beanName)) {
			beanName = "map";
		}

		// Set up genericBean in the request scope with a list of Taxoninterpretation objects
		// execute() PARAMS:
		//   1: the HTTP request
		//   2: the SQL select key in SQLStore.properties
		//   3: the SQL where key in SQLStore.properties
		//   4: the name of the model bean to generate
		//   5: any SQL where parameters; can be an array too
		try {
			GenericCommand gc = new GenericCommand();

			log.debug("Setting GC's id, numItems, pageNumber, perPage and pager");
			gc.setId(getId());

			gc.setPageNumber(getPageNumber());
			gc.setNumItems(findAttribute("numItems"));
			gc.setPerPage(getPerPage());
			gc.setWhereSubquery(getWhereSubquery());

			try { gc.setPager(Boolean.valueOf(getPager()).booleanValue()); 
			} catch (Exception ex) { gc.setPager(false); }


			log.debug("Setting up WHERE clause");
			setupWhereClause( (HttpServletRequest)pageContext.getRequest() );

			log.debug("Calling gc.execute()");
			gc.execute(
					(HttpServletRequest)pageContext.getRequest(), 
					getSelect(), 
					getWhere(), 
					getBeanName(),
					getWparam());

		} catch (Exception ex) { 
			log.error("Problem running GenericCommand", ex);
		}
		
        // Continue processing this page
        return SKIP_BODY;
    }


    /**
     * Chooses the proper SQL where clause to use.
	 * Sets it in the request.
     */
    private void setupWhereClause(HttpServletRequest request) {

		try {
			//
			// If the URL does not contain 'where' then set it
			//
			
			String where = request.getParameter("where");
			if (Utility.isStringNullOrEmpty(where)) {
				String params = request.getParameter("params");
				if (Utility.isStringNullOrEmpty(params)) {
					// this doesn't need to happen if /get/VIEW/ENTITY is used
					// but it supports direct calls to the jsp
					params = request.getParameter("wparam");
				}

				log.debug("Checking numerocity of params: " + params);
				if (Utility.isNumericList(params)) {
					// use a PK
					where = getWhereNumeric();
					log.debug("got numeric where: " + where);

					if (Utility.isStringNullOrEmpty(where)) {
						// still null, set default numeric
						where = "where_" + request.getParameter("entity") + "_pk";
						log.debug("was null so is now: " + where);
					}

				} else {
					// use an AC
					where = getWhereNonNumeric();
					log.debug("got non-numeric where: " + where);

					if (Utility.isStringNullOrEmpty(where)) {
						// still null, set default non-numeric
						where = "where_ac";
						log.debug("was null so is now: " + where);
					}
				}

				request.setAttribute("where", where);

			}

		} catch (Exception ex) {
			log.error("Error while setting where clause key", ex);
		}

    }




    /**
     * Key in SQLStore.properties that defines SQL select clause.
     */
	protected String select;

    public String getSelect() {
        return findAttribute("select", this.select);
    }

    public void setSelect(String s) {
        this.select = s;
    }

    /**
     * Key in SQLStore.properties that defines SQL where clause.
     */
	protected String where;

    public String getWhere() {
        return findAttribute("where", this.where);
    }

    public void setWhere(String s) {
        this.where = s;
    }

    /**
     * Name of VBModelBean class, or "map" to use a HashMap.
     */
	protected String beanName;

	/**
	 * Returns pageNumber that has been set,
	 * or finds pageNumber attribute in any scope.
	 */
    public String getBeanName() {
        return findAttribute("beanName", this.beanName);
    }

    public void setBeanName(String s) {
        this.beanName = s;
    }

    /**
     * 
     */
	protected String pageNumber;

	/**
	 * Returns pageNumber that has been set,
	 * or finds pageNumber attribute in any scope.
	 */
    public String getPageNumber() {
        return findAttribute("pageNumber", this.pageNumber);
    }

    public void setPageNumber(String s) {
        this.pageNumber = s;
    }

    /**
     * 
     */
	protected String perPage;

    public String getPerPage() {
		return findAttribute("perPage", this.perPage);
    }

    public void setPerPage(String s) {
        this.perPage = s;
    }

    /**
     * Key in SQLStore.properties that defines SQL select clause.
     */
	protected String wparam;

    public String getWparam() {
		String s = findAttribute("wparam", this.wparam);
		if (s != null) {
			return s.toLowerCase();
		} else {
			return s;
		}
    }

    public void setWparam(String s) {
		this.wparam = s;
	}

    /**
     * 
     */
	protected String id;

    public String getId() {
		return findAttribute("id", this.id);
    }

    public void setId(String s) {
        this.id = s;
    }

    /**
     * 
     */
	protected String pager;

    public String getPager() {
		return findAttribute("pager", this.pager);
    }

    public void setPager(String s) {
        this.pager = s;
    }

    /**
     * 
     */
	protected String whereNumeric;

    public String getWhereNumeric() {
		return findAttribute("whereNumeric", this.whereNumeric);
    }

    public void setWhereNumeric(String s) {
        this.whereNumeric = s;
    }

    /**
     * 
     */
	protected String whereNonNumeric;

    public String getWhereNonNumeric() {
        return findAttribute("whereNonNumeric", this.whereNonNumeric);
    }

    public void setWhereNonNumeric(String s) {
        this.whereNonNumeric = s;
    }

    /**
     * 
     */
	protected String whereSubquery;

    public String getWhereSubquery() {
        return findAttribute("whereSubquery", this.whereSubquery);
    }

    public void setWhereSubquery(String s) {
        this.whereSubquery = s;
    }


}
