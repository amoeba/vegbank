///////////////////////////////////////////////////////////
//
//  event_Form.java
//  Created on Wed Apr 21 14:02:14 EDT 2004
//  By Auto FormBean,Action and JSP Builder 1.0
//
///////////////////////////////////////////////////////////



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
import org.vegbank.nvcrs.util.*;

public class event_Form extends SuperForm
{
	private String EVENT_ID;
	private String PROPOSAL_ID;
	private String USR_ID;
	private String ROLE_ID;
	private String ACTION_ID;
	private String SUBJECTUSR_ID;
	private String eventDate;
	private String privateComments;
	private String publicComments;
	private String document1;
	private String document2;
	private String document3;
	private String reviewText;
	private String summary;

	
	
	public event_Form()
	{
		super();
		EVENT_ID="-1";
		PROPOSAL_ID="-1";
		USR_ID="-1";
		ROLE_ID="-1";
		ACTION_ID="-1";
		SUBJECTUSR_ID="-1";
		eventDate="";
		privateComments="";
		publicComments=" ";
		document1="";
		document2="";
		document3="";
		reviewText="";
		summary="";
		updateFields();

	}


	public event_Form(String EVENT_ID,String PROPOSAL_ID,String USR_ID,
		String ROLE_ID,String ACTION_ID,String SUBJECTUSR_ID,
		String eventDate,String privateComments,String publicComments,
		String document1, String document2, String document3,String reviewText,String summary
		)
	{
		this.EVENT_ID=EVENT_ID;
		this.PROPOSAL_ID=PROPOSAL_ID;
		this.USR_ID=USR_ID;
		this.ROLE_ID=ROLE_ID;
		this.ACTION_ID=ACTION_ID;
		this.SUBJECTUSR_ID=SUBJECTUSR_ID;
		this.eventDate=eventDate;
		this.privateComments=privateComments;
		this.publicComments=publicComments;
		this.document1=document1;
		this.document2=document2;
		this.document3=document3;
		this.reviewText=reviewText;
		this.summary=summary;
		updateFields();
	}

	

	public String getDocument1()
	{
		return this.document1;
	}
	public String getDocument2()
	{
		return this.document2;
	}

	public String getDocument3()
	{
		return this.document3;
	}


	public String getEVENT_ID()
	{
		return this.EVENT_ID;
	}


	public String getPROPOSAL_ID()
	{
		return this.PROPOSAL_ID;
	}


	public String getUSR_ID()
	{
		return this.USR_ID;
	}


	public String getROLE_ID()
	{
		return this.ROLE_ID;
	}


	public String getACTION_ID()
	{
		return this.ACTION_ID;
	}


	public String getSUBJECTUSR_ID()
	{
		return this.SUBJECTUSR_ID;
	}


	public String getEventDate()
	{
		return this.eventDate;
	}


	public String getPrivateComments()
	{
		return this.privateComments;
	}


	public String getPublicComments()
	{
		return this.publicComments;
	}


	public String getReviewText()
	{
		return this.reviewText;
	}


	public String getSummary()
	{
		return this.summary;
	}

	public void setDocument1(String doc)
	{
		this.document1=doc;
	}
	
	public void setDocument2(String doc)
	{
		this.document2=doc;
	}
	
	public void setDocument3(String doc)
	{
		this.document3=doc;
	}

	public void setEVENT_ID(String EVENT_ID)
	{
		this.EVENT_ID=EVENT_ID;
	}


	public void setPROPOSAL_ID(String PROPOSAL_ID)
	{
		this.PROPOSAL_ID=PROPOSAL_ID;
	}


	public void setUSR_ID(String USR_ID)
	{
		this.USR_ID=USR_ID;
	}


	public void setROLE_ID(String ROLE_ID)
	{
		this.ROLE_ID=ROLE_ID;
	}


	public void setACTION_ID(String ACTION_ID)
	{
		this.ACTION_ID=ACTION_ID;
	}


	public void setSUBJECTUSR_ID(String SUBJECTUSR_ID)
	{
		this.SUBJECTUSR_ID=SUBJECTUSR_ID;
	}


	public void setEventDate(String eventDate)
	{
		this.eventDate=eventDate;
	}


	public void setPrivateComments(String privateComments)
	{
		this.privateComments=privateComments;
	}


	public void setPublicComments(String publicComments)
	{
		this.publicComments=publicComments;
	}



	public void setReviewText(String reviewText)
	{
		this.reviewText=reviewText;
	}


	public void setSummary(String summary)
	{
		this.summary=summary;
	}


	public void updateFields()
	{
		if(fields.size()<1)
		{
			addField("PROPOSAL_ID");
			addField("USR_ID");
			addField("ROLE_ID");
			addField("ACTION_ID");
			addField("SUBJECTUSR_ID");
			addField("eventDate");
			addField("privateComments");
			addField("publicComments");
			addField("document1");
			addField("document2");
			addField("document3");
			addField("reviewText");
			addField("summary");
			setTableName("event");
			setPrimaryKey("EVENT_ID");
		}
	}


	public String getFieldValue(String fldName) throws Exception
	{
		if(fldName==null)
			throw new Exception("Null field name.");
		if(fldName.equals("EVENT_ID"))
			return EVENT_ID;
		if(fldName.equals("PROPOSAL_ID"))
			return PROPOSAL_ID;
		if(fldName.equals("USR_ID"))
			return USR_ID;
		if(fldName.equals("ROLE_ID"))
			return ROLE_ID;
		if(fldName.equals("ACTION_ID"))
			return ACTION_ID;
		if(fldName.equals("SUBJECTUSR_ID"))
			return SUBJECTUSR_ID;
		if(fldName.equals("eventDate"))
			return eventDate;
		if(fldName.equals("privateComments"))
			return privateComments;
		if(fldName.equals("publicComments"))
			return publicComments;
		if(fldName.equals("document1"))
			return document1;
		if(fldName.equals("document2"))
			return document2;
		if(fldName.equals("document3"))
			return document3;
		if(fldName.equals("reviewText"))
			return reviewText;
		if(fldName.equals("summary"))
			return summary;
		throw new Exception("Field not found: "+fldName);
	}


	public void setFieldValue(String fldName, String value) throws Exception
	{
		if(fldName==null)
			throw new Exception("Null field name.");
		if(fldName.equals("EVENT_ID"))
			EVENT_ID=value;
		else if(fldName.equals("PROPOSAL_ID"))
			PROPOSAL_ID=value;
		else if(fldName.equals("USR_ID"))
			USR_ID=value;
		else if(fldName.equals("ROLE_ID"))
			ROLE_ID=value;
		else if(fldName.equals("ACTION_ID"))
			ACTION_ID=value;
		else if(fldName.equals("SUBJECTUSR_ID"))
			SUBJECTUSR_ID=value;
		else if(fldName.equals("eventDate"))
			eventDate=value;
		else if(fldName.equals("privateComments"))
			privateComments=value;
		else if(fldName.equals("publicComments"))
			publicComments=value;
		else if(fldName.equals("document1"))
			document1=value;
		else if(fldName.equals("document2"))
			document2=value;
		else if(fldName.equals("document3"))
			document3=value;	

		else if(fldName.equals("reviewText"))
			reviewText=value;
		else if(fldName.equals("summary"))
			summary=value;
		else
			throw new Exception("Field not found: "+fldName);
	}


}

