package org.vegbank.nvcrs.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleException;
import org.apache.struts.util.MessageResources;
import org.apache.commons.beanutils.PropertyUtils;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import org.vegbank.nvcrs.util.*;

public class SuperForm extends ActionForm{
	String primaryKey;
	ArrayList fields;
	ArrayList records;
	String tableName;
	Database database;
    
    public SuperForm()
    {
    	super();
    	primaryKey="";
    	fields=new ArrayList();
		records=new ArrayList();    	
		database=new Database();
    }
    
    public ArrayList getRecords()
    {
    	return records;
    }
    public int getRecordCount()
    {
    	if(records==null) return 0;
    	return records.size();
    }
    public void setPrimaryKey(String key)
    {
    	primaryKey=key;
    }
    public void setFields(ArrayList list)
    {
    	fields.clear();
    	fields=list;
    } 
    public String getPrimaryKey()
    {
    	return primaryKey;
    }
    
    public ArrayList getFields()
    {
    	return fields;
    }
    
    public void setTableName(String tbname)
    {
    	tableName=tbname;
    }
    public String getTableName()
    {
    	return tableName;
    }
    
    public void addField(String fldName)
    {
    	fields.add(fldName);
    }
    public void updateRecord() throws Exception
    {
    Connection con=null;
    	try
        {
        	con=database.getConnection();
	        
	        String updateStatement ="update "+tableName + " set ";
	        int num=fields.size();
	        for(int i=0; i<num;i++)
	        {
	        	String fldName=(String)fields.get(i);
	        	updateStatement += fldName + "='" + getFieldValue(fldName).trim()+"'";
	        	if(i<num-1) 
	        		updateStatement += ",";
	        }
	        updateStatement +=" where "+primaryKey +"=" + getFieldValue(primaryKey).trim();
	        PreparedStatement prepStmt = 
	                con.prepareStatement(updateStatement);
	   
	        int rowCount = prepStmt.executeUpdate();
	        prepStmt.close();
	        con.close();
	   
	        if (rowCount == 0)
	        {
	   			throw new Exception("Failed to update the record: " + getFieldValue(primaryKey));	
	        }
	    }
	    catch(Exception e)
	    {
	    	if(con!=null && !con.isClosed())
	    		con.close();
	      	throw e;
	    }
    }
    public void addRecord() throws Exception
    {
    	Connection con=null;
    	try
        {
			con=database.getConnection();
	        
	        String updateStatement ="insert into "+tableName + " ( ";
	        int num=fields.size();
	        for(int i=0; i<num;i++)
	        {
	        	String fldName=(String)fields.get(i);
	        	updateStatement += fldName;
	        	if(i<num-1) 
	        		updateStatement += ",";
	        	else 
	        		updateStatement += ") values (";
	        }
	        for(int i=0; i<num;i++)
	        {
	        	String fldName=(String)fields.get(i);
	        	updateStatement += "'" + getFieldValue(fldName).trim()+"'";
	        	if(i<num-1) 
	        		updateStatement += ",";
	        	else
	        	 	updateStatement += " )";
	        }
	        
	        
	        PreparedStatement prepStmt = 
	                con.prepareStatement(updateStatement);
	   
	        try{
	        	   int rowCount = prepStmt.executeUpdate();
	       	}
	       	catch(Exception e)
	       	{
	       		prepStmt.close();
	        	con.close();
	       		throw new Exception(updateStatement+"\nSystem error:" + e.getMessage());
	       	}
	        prepStmt.close();
	        setFieldValue(primaryKey,DBHelper.getMaxId(con,tableName,primaryKey));
	        con.close();
	     }
	     catch(Exception e)
	     {
	     	if(con!=null && !con.isClosed())
	     		con.close();
	     	throw e;
	     }
    }
    public void deleteRecord() throws Exception
    {
    	Connection con=null;
    	try
    	{
			con=database.getConnection();
	        String deleteStatement =
	                "delete from " + tableName + " where " + primaryKey + "=" +getFieldValue(primaryKey);
	        PreparedStatement prepStmt =
	                con.prepareStatement(deleteStatement);
	   
	        prepStmt.executeUpdate();
	        prepStmt.close();
	        con.close();
	    }
	    catch(Exception e)
	    {
	    	if(con!=null && !con.isClosed())
	     		con.close();
	     	throw e;  		
	    }
    }
    public void findRecordByPrimaryKey(String primaryKeyValue) throws Exception
    {
     	
     	
     	Connection con=null;
     	try
		{
		    con=database.getConnection();
	        String selectStatement = "select * from "+tableName + " where "+ primaryKey + "=" + primaryKeyValue.trim();
	        PreparedStatement prepStmt =  con.prepareStatement(selectStatement);
	        ResultSet rs = prepStmt.executeQuery();
	   
	        if (rs.next()) 
	        {
	        	int num=fields.size();
	        	for(int i=0;i<num;i++)
	        	{
	        		String fldName=(String)fields.get(i);
	        		setFieldValue(fldName,rs.getString(fldName));
	        	}
	        	setFieldValue(primaryKey,primaryKeyValue);
	        }
	        else
	        {
	        	prepStmt.close();
		        con.close();
		        throw new Exception("Failed to find the record.");
		    }
	    	prepStmt.close();
	        con.close();
	    }
	    catch(Exception e)
	    {
	       	if(con!=null && !con.isClosed())
	     		con.close();
	     	throw e;  
	    }
    }
    
    public void loadRecord(String primaryKeyValue) throws Exception
    {
    	throw new Exception("loadRecord: Function has not been implemented yet.");
    }
    
    public void findRecordBySQL(String strSQL) throws Exception
    {
     	Connection con=null;
     	try
		{
		    con=database.getConnection();
	        String selectStatement = strSQL;
	        PreparedStatement prepStmt =  con.prepareStatement(selectStatement);
	        ResultSet rs = prepStmt.executeQuery();
	   
	        if (rs.next()) 
	        {
	        	int num=fields.size();
	        	for(int i=0;i<num;i++)
	        	{
	        		String fldName=(String)fields.get(i);
	        		setFieldValue(fldName,rs.getString(fldName));
	        	}
	        	setFieldValue(primaryKey,rs.getString(primaryKey));
	        }
	        else
	        {
	        	prepStmt.close();
		        con.close();
		        throw new Exception("Failed to find the record");
		    }
	    	prepStmt.close();
	        con.close();
	    }
	    catch(Exception e)
	    {
	       	if(con!=null && !con.isClosed())
	     		con.close();
	     	throw e;  
	    }
    }
    
    public void findRecords(String strSQL) throws Exception
    {
    	Connection con=null;

     	try
		{
	    	records.clear();
		   
		    con=database.getConnection();
	        String selectStatement =strSQL;
	        PreparedStatement prepStmt = 
	                con.prepareStatement(selectStatement);
	        ResultSet rs = prepStmt.executeQuery();
	   
	        while(rs.next()) 
	        {
	        	Hashtable rcd=new Hashtable();
	        	int num=fields.size();
	        	for(int i=0;i<num;i++)
	        	{
	        		String fldName=(String)fields.get(i);
	        		rcd.put(fldName,rs.getString(fldName));
	        	}
	        	rcd.put(primaryKey,rs.getString(primaryKey));
	        	records.add(rcd);
	        }
	    	prepStmt.close();
	        con.close();
	    }
	    catch(Exception e)
	    {
	       	if(con!=null && !con.isClosed())
	     		con.close();
	     	throw e;  
	    }
    }
    public String getFieldValue(String fldName) throws Exception
    {
    	throw new Exception("getFieldValue: Function has not been implemented yet.");
    }
    public void setFieldValue(String fldName, String value) throws Exception
    {
    	throw new Exception("setFieldValue: Function has not been implemented yet.");
    }
    
    private boolean recordExisted(String primaryKeyValue) throws Exception
    {
    	boolean ret=true;
    	Connection con=null;
        try
        {
        	con=database.getConnection();
	        
	        String queryStatement ="select * from ? where ? = ?";
	            
	        PreparedStatement prepStmt = 
	            con.prepareStatement(queryStatement);
	   
	        prepStmt.setString(1, tableName);
	        prepStmt.setString(2, primaryKey);
	        prepStmt.setString(3, primaryKeyValue);
	        ResultSet rs=prepStmt.executeQuery();
	        if(rs.next())
	        	ret=true;
	        else
	        	ret=false;
	        
	        prepStmt.close();
            con.close();
            
	     }
	     catch(Exception e)
	     {
	     	if(con!=null && !con.isClosed())
	     		con.close();
	     	throw e;
	     }
	     return ret;
    }
    
	public void clearForm() throws Exception
	{
		int num=0;
		try
		{
			num=fields.size();
			int i;
			for(i=0;i<num;i++)
			{
				String fld=(String)fields.get(i);
				if(fld.indexOf("ID")>-1)
					setFieldValue(fld,BeanManager.UNKNOWN_ID);
				else
					setFieldValue(fld,BeanManager.EMPTY_VALUE);//BeanManager.EMPTY_VALUE);		
			}
			setFieldValue(primaryKey,BeanManager.UNKNOWN_ID);
			
		}
		catch(Exception e)
		{
			throw e;
		}
	}   
	public  void removeRecord(String id) throws Exception
	{
		if(records==null)throw new Exception("Empty records");
		Hashtable tb=null;
		int num=records.size();
		for(int i=0;i<num;i++)
		{
			tb=(Hashtable)records.get(i);
			if( ((String)tb.get(primaryKey)).equals(id)) records.remove(i);
		}
	}
	
	public void clearRecords()
	{
		records.clear();
	}
	
	public Hashtable getRecord(String key)
	{
		if(records==null || records.isEmpty()) return null;
		int num=records.size();
		for(int i=0;i<num;i++)
		{
			Hashtable t=(Hashtable)records.get(i);
			String tmp=(String)t.get(primaryKey);
			if(tmp.equals(key)) return t;
		}
		return null;
		
	}
}
