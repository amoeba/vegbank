/*
 *	'$RCSfile: AccessionCode.java,v $'
 *	Authors: @author@
 *	Release: @release@
 *
 *	'$Author: anderson $'
 *	'$Date: 2005-05-02 11:11:06 $'
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
 
package org.vegbank.common.utility;

import java.io.*;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vegbank.common.utility.AccessionGen;


/**
 * Everything you want to know about and do with 
 * an accession code.
 * 
 * @author anderson
 */
public class AccessionCode
{
	protected static Log log = LogFactory.getLog(AccessionCode.class);

    private String ac = null;
    private String dbId = null;
    private String entityCode = null;
    private Long entityId = null;
    private String confirmation = null;


    /**
     *
     */
    public AccessionCode(String ac) {
        this.ac = ac;
        if (!Utility.isStringNullOrEmpty(ac)) {
            String[] parts = this.ac.split("\\.");
            dbId = parts[0];
            entityCode = parts[1];
            entityId = Long.valueOf(parts[2]);
            confirmation = parts[3];
        }
    }

    /**
     * Gets the database ID, the prefix.
     */
    public String getDatabaseId() {
        /*
        if (Utility.isStringNullOrEmpty(ac)) { return null; }
        return ac.substring(0, ac.indexOf("."));
        */
        return dbId;
    }

    /**
     * Gets the entity code, the second field.
     */
    public String getEntityCode() {
        /*
        if (Utility.isStringNullOrEmpty(ac)) { return null; }
        int p1 = ac.indexOf(".");
        int p2 = ac.indexOf(".", p1);
        return ac.substring(p1, p2-p1);
        */
        return entityCode;
    }

    /**
     * Gets the entity (table) name, the second field.
     */
    public String getEntityName() {
        AccessionGen ag = new AccessionGen();
        return ag.getTableName(getEntityCode());
    }

    /**
     * Gets the record ID, the PK, the third field.
     */
    public Long getEntityId() {
        /*
        if (Utility.isStringNullOrEmpty(ac)) { return 0; }
        int p1 = ac.indexOf(".");
        p1 = ac.indexOf(".", p1);
        int p2 = ac.indexOf(".", p1);
        return Long.parseLong(ac.substring(p1, p2-p1));
        */
        return entityId;
    }

    /**
     * Gets the confirmation code, the suffix.
     */
    public String getConfirmation() {
        /*
        if (Utility.isStringNullOrEmpty(ac)) { return null; }
        return ac.substring(ac.lastIndexOf(".")+1);
        */
        return confirmation;
    }

    /**
     *
     */
    public String toString() {
        return ac;
    }

    /**
     *
     */
    public boolean equals(AccessionCode otherAc) {
        return ac.equals(otherAc.toString());
    }

    /**
     *
     */
    public boolean equals(String otherAc) {
        return ac.equals(otherAc);
    }

}

