/*
 *	'$RCSfile: VegbankPagerTag.java,v $'
 *	Authors: @author@
 *	Release: @release@
 *
 *	'$Author: anderson $'
 *	'$Date: 2004-09-15 03:29:08 $'
 *	'$Revision: 1.3 $'
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
import org.vegbank.common.utility.ServletUtility;

/**
 * Tag that builds a page selector in HTML.
 *
 * @author P. Mark Anderson
 * @version $Revision: 1.3 $ $Date: 2004-09-15 03:29:08 $
 */

public class VegbankPagerTag extends VegbankTag {

	private static final Log log = LogFactory.getLog(VegbankPagerTag.class);
	public static final int DEF_PER_BATCH = 10;


    /**
     * Process the start tag.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {

		int iNumItems;
		int iPerPage;
		int iPageNumber;
		int iPerBatch;
		int iCurBatch;
		int numPagesInBatch;
		int numTotalPages;
		int numTotalBatches;
		int firstPageInBatch;
		int lastPageInBatch;
		boolean hasMoreBatchesBefore, hasMoreBatchesAfter;

		String pagerHTML = "";
		String queryString;

		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		Map urlParams;


		try {
			urlParams = ServletUtility.parameterHash(request);

			iNumItems = convStringToInt(getNumItems(), 0);
			log.debug("numItems: " + iNumItems);
			if (iNumItems == 0) {
				return SKIP_BODY; 	// no deal if no numItems given
			}

			iPerPage = convStringToInt(getPerPage(), 0);
			log.debug("perPage: " + iPerPage);
			if (iPerPage == 0) {
				return SKIP_BODY; 	// no deal if no perPage given
			}

			iPageNumber = convStringToInt(getPageNumber(), 1);
			log.debug("pageNumber: " + iPageNumber);

			iPerBatch = convStringToInt(getPerBatch(), DEF_PER_BATCH);
			log.debug("perBatch: " + iPerBatch);

			//iCurBatch = convStringToInt(findAttribute("curBatch"), 1);
			//log.debug("curBatch: " + iCurBatch);

		
			// calculate shit
			numTotalPages = (int)Math.round(Math.ceil(iNumItems / iPerPage));
			numTotalBatches = (int)Math.round(Math.ceil(numTotalPages / iPerBatch));

			if (numTotalPages % iPerBatch == 0) {
				numTotalBatches = numTotalPages / iPerBatch;
			} else {
				numTotalBatches = (int)(numTotalPages / iPerBatch) + 1;
			}

			if (iPageNumber % iPerBatch == 0) {
				iCurBatch = iPageNumber / iPerBatch;
			} else {
				iCurBatch = iPageNumber / iPerBatch + 1;
			}

			log.debug("numTotalPages: " + numTotalPages);
			log.debug("numTotalBatches: " + numTotalBatches);

			if (numTotalPages > iPerBatch) {
				// there is more than one batch of pages
				if (iCurBatch > 1 && iPageNumber > iPerBatch) {
					hasMoreBatchesBefore = true;
				} else {
					hasMoreBatchesBefore = false;
				}

				if (iCurBatch < numTotalBatches) {
					hasMoreBatchesAfter = true;
					numPagesInBatch = iPerBatch;
				} else {
					hasMoreBatchesAfter = false;
					numPagesInBatch = numTotalPages - ((numTotalBatches-1) * iPerBatch);
				}

				firstPageInBatch = ((iCurBatch - 1) * iPerBatch) + 1;
				lastPageInBatch = firstPageInBatch + numPagesInBatch - 1;

			} else {
				hasMoreBatchesBefore = false;
				hasMoreBatchesAfter = false;
				firstPageInBatch = 1;
				lastPageInBatch = numTotalPages;
				numPagesInBatch = numTotalPages;
			}

			// fix the page number
			if (iPageNumber > lastPageInBatch) {
				iPageNumber = lastPageInBatch;
			} else if (iPageNumber < firstPageInBatch) {
				iPageNumber = firstPageInBatch;
			}

			log.debug("CORRECTED pageNumber: " + iPageNumber);
			log.debug("num pages in batch: " + numPagesInBatch);
			log.debug("first page in batch: " + firstPageInBatch);
			log.debug("last page in batch: " + lastPageInBatch);

			// set up the next page request
			urlParams.put("perBatch", Integer.toString(iPerBatch));
			urlParams.put("perPage", Integer.toString(iPerPage));
			///////urlParams.put("numItems", Integer.toString(iNumItems));


			////////////////
			// MORE
			////////////////
			if (hasMoreBatchesBefore) {
				// render the "get last batch" link
				urlParams.put("pageNumber", Integer.toString(firstPageInBatch - 1));
				//urlParams.put("curBatch", Integer.toString(iCurBatch - 1));
				queryString = ServletUtility.buildQueryString(urlParams);
				pagerHTML += "&laquo;&laquo; <a href='" + request.getRequestURI() + 
						queryString + "'>more</a> <font color='#AAAAAA'>||</font> &nbsp; ";
			}

			////////////////
			// PREVIOUS
			////////////////
			if (iPageNumber > 1) {
				urlParams.put("pageNumber", Integer.toString(iPageNumber - 1));
				queryString = ServletUtility.buildQueryString(urlParams);
				pagerHTML += "&laquo; <a href='" + request.getRequestURI() + queryString + "'>previous</a>&nbsp; <font color='#AAAAAA'>||</font> ";
			}


			////////////////
			// PAGE NUMBERS
			////////////////
			for (int i=firstPageInBatch; i<=lastPageInBatch; i++) {

				if (iPageNumber == i) {
					pagerHTML += " page <font color='red' size='+1'>" + i + "</font> ";

				} else {
					urlParams.put("pageNumber", Integer.toString(i));
					queryString = ServletUtility.buildQueryString(urlParams);
					pagerHTML += "<a href='" + request.getRequestURI() + queryString + "'>" + i + "</a>";
				}

				if (i < lastPageInBatch) {
					pagerHTML += " <font color='#AAAAAA'>|</font> ";
				}
			}

			
			////////////////
			// NEXT
			////////////////
			if (iPageNumber < numTotalPages) {
				urlParams.put("pageNumber", Integer.toString(iPageNumber + 1));
				queryString = ServletUtility.buildQueryString(urlParams);
				pagerHTML += " <font color='#AAAAAA'>||</font> &nbsp;<a href='" + request.getRequestURI() + queryString + "'>next</a> &raquo;";
			}

			////////////////
			// MORE
			////////////////
			if (hasMoreBatchesAfter) {
				// render the "get next batch" link
				urlParams.put("pageNumber", Integer.toString(lastPageInBatch + 1));
				//urlParams.put("curBatch", Integer.toString(iCurBatch + 1));
				queryString = ServletUtility.buildQueryString(urlParams);
				pagerHTML += "&nbsp; &nbsp; <font color='#AAAAAA'>||</font> <a href='" + request.getRequestURI() + 
						queryString + "'>more</a> &raquo;&raquo;";
			}

			int firstItemNumber = iPerPage * (iPageNumber - 1) + 1;

			pageContext.getOut().println("<p align='center'><strong>" + pagerHTML + 
					"</strong><br/><font color='#AAAAAA'>" + firstItemNumber + " through " 
					+ (firstItemNumber+iPerPage-1) + " of " + iNumItems + "</font></p>");

		} catch (Exception ex) {
			log.error("Error while creating pager", ex);
		}

		
        return SKIP_BODY;
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
	protected String numItems;

    public String getNumItems() {
		return findAttribute("numItems", this.numItems);
    }

    public void setNumItems(String s) {
		this.numItems = s;
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
     * 
     */
	protected String perBatch;

    public String getPerBatch() {
        return findAttribute("perBatch", this.perBatch);
    }

    public void setPerBatch(String s) {
        this.perBatch = s;
    }


}
