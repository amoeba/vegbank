/*
 *	'$RCSfile: VegbankChangeParamTag.java,v $'
 *	Authors: @author@
 *	Release: @release@
 *
 *	'$Author: anderson $'
 *	'$Date: 2005-04-15 07:12:03 $'
 *	'$Revision: 1.1 $'
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


import java.util.*;
import javax.servlet.jsp.*;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;

import org.vegbank.common.utility.Utility;
import org.vegbank.common.utility.ServletUtility;

/**
 * Tag that replaces one request parameter value with another
 * and returns an absolute or relative URL back to the current
 * address with the new parameter value.
 *
 * @author P. Mark Anderson
 * @version $Revision: 1.1 $ $Date: 2005-04-15 07:12:03 $
 */

public class VegbankChangeParamTag extends VegbankTag {

	private static final Log log = LogFactory.getLog(VegbankChangeParamTag.class);


    /**
     * Process the start tag.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {


		StringBuffer newLinkHTML = new StringBuffer(128);

		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		Map urlParams;

		try {
            if (getAbsolute()) {
                newLinkHTML.append(request.getRequestURL()).append("?");
            } else {
                newLinkHTML.append(request.getRequestURI()).append("?");
            }

			urlParams = ServletUtility.parameterHash(request);


            String n = getParamName();
            String v = getParamValue();
            urlParams.put(n, v);

            boolean first = true;
            Iterator kit = urlParams.keySet().iterator();
            while (kit.hasNext()) {
                if (first) {
                    first = false;
                } else {
                    newLinkHTML.append("&");
                }

                String key = (String)kit.next();
                newLinkHTML.append(key)
                    .append("=")
				    .append(java.net.URLEncoder.encode((String)urlParams.get(key), "UTF-8"));
            }

			pageContext.getOut().println(newLinkHTML.toString());

		} catch (Exception ex) {
			log.error("Error while changing params", ex);
		}
		
        return SKIP_BODY;
    }


    /**
     * 
     */
	protected String paramName;

    public String getParamName() {
		return this.paramName;
    }

    public void setParamName(String s) {
        this.paramName = s;
    }

    /**
     * 
     */
	protected String paramValue;

    public String getParamValue() {
		return findAttribute("paramValue", this.paramValue);
    }

    public void setParamValue(String s) {
        this.paramValue = s;
    }

    /**
     * 
     */
	protected boolean absolute = false;

    public boolean getAbsolute() {
		if (this.absolute) {
			return true;
		}

        setAbsolute(findAttribute("absolute"));
        return this.absolute;
    }

    public void setAbsolute(String s) {
        setAbsolute(Utility.isStringTrue(s));
    }

    public void setAbsolute(boolean b) {
        this.absolute = b;
    }

}