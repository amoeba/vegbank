package servlet.authentication;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import servlet.util.ServletUtility;
import servlet.authentication.UserDatabaseAccess;

/**
 * Servlet to perform user authentication for all the 
 * servlets within a domain.  This servlet will recognize varying 
 * degrees of authentication for users and will place cookies representing
 * the authentication level
 *
 * <p>Valid parameters are:<br><br>
 * REQUIRED PARAMETERS
 * @param userName - user name of the user tying to be authenticated <br>
 * @param password - the password of the user attempting to be authenticated <br> 
 * @param authType -- the authentication type {loginUser, uploadFile}
 *
 * @version 
 * @author 
 * 
 */

public class AuthenticationServlet extends HttpServlet 
{
	public int cookieValidFlag=0;  //1=valid
	public Cookie registeredCookie;
	public String servletPath = null;
	public ServletUtility su = new ServletUtility();
	public UserDatabaseAccess uda = new UserDatabaseAccess();
	
	ResourceBundle rb = ResourceBundle.getBundle("plotQuery");
	//public String clientLogFile = null; //the file to log the client usage


	/** Handle "POST" method requests from HTTP clients */
	public void doPost(HttpServletRequest request,
	HttpServletResponse response)
	throws IOException, ServletException
	{
		doGet(request, response);
	}
    
	/** Handle "POST" method requests from HTTP clients */
	public void doGet(HttpServletRequest request,
	HttpServletResponse response)
	throws IOException, ServletException
	{

		//clientLogFile=rb.getString("requestparams.clientLog");
		//System.out.println("the client info log file is: "+clientLogFile);

		//first block will look for the userName and password as well as valid cookie
		try
		{
			ResourceBundle rb = ResourceBundle.getBundle("plotQuery");
			servletPath=rb.getString("servlet-path");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
	
			//get the request - parameters using the 
			//servlet Utility class 
			Hashtable requestParams = su.parameterHash(request);
			System.out.println( requestParams.toString() );
			//grab the remote host information
			String remoteHost=request.getRemoteHost();
			String remoteAddress = request.getRemoteAddr();
			System.out.println("remoteHost: "+remoteHost+" Address: "+remoteAddress);
			
			
			//determine what the client wants to do
			if ( requestParams.containsKey("authType") == false)
			{
				out.println("PARAMETERS INCLUDE: " + this.getParameters() );
			}
			else
			{
				//figure out what the client wants to do -- exactly
				//LOGIN THE USER
				if ( requestParams.get("authType").toString().equals("loginUser")  )
				{
					System.out.println("user login attempt");
					if ( authenticateUser(requestParams, remoteAddress) == true)
					{
						//log the login in the clientLogger
						//set a cookie
						String user = getUserName(requestParams);
						Cookie cookie = new Cookie("null", "null");
						AuthenticationServlet m =new AuthenticationServlet();  
						m.cookieDelegator(cookie, requestParams, remoteAddress, user);
						response.addCookie(m.registeredCookie);
						
 						
						//send the user to the correct page
						Thread.currentThread().sleep(100);
						response.sendRedirect("http://vegbank.nceas.ucsb.edu/framework/servlet/usermanagement");
					}
					else 
					{
						//send the user to the correct page
						Thread.currentThread().sleep(100);
						response.sendRedirect(servletPath+"pageDirector?pageType=login");
						//response.sendRedirect("/harris/servlet/pageDirector?pageType=login");
					}
				}
				//AUTHENTICATE THE USER TO UPLOAD A FILE
				else if ( requestParams.get("authType").toString().equals("uploadfile")  )
				{
					System.out.println("authenticating for file upload");
					if ( authenticateUser(requestParams, remoteAddress) == true)
					{
						out.println("<authentication>true</authentication>");
						
						//log the login in the clientLogger
						//set a cookie
						String user = getUserName(requestParams );
						Cookie cookie = new Cookie("null", "null");
						AuthenticationServlet m =new AuthenticationServlet();  
						m.cookieDelegator(cookie, requestParams, remoteAddress, user);
						response.addCookie(m.registeredCookie);
						
 						
						//send the user to the correct page
						//Thread.currentThread().sleep(100);
						
						//response.sendRedirect(servletPath+"pageDirector?pageType=loggedin");
						//response.sendRedirect("/harris/servlet/pageDirector?pageType=loggedin");
					}
					else 
					{
						out.println("<authentication>false</authentication>");
					}
				}
				
				// CREATE A NEW USER
				else if ( requestParams.get("authType").toString().equals("createUser")  )
				{
					System.out.println("creating a new user");
					if (createNewUser(requestParams, remoteAddress) == true )
					{
						Thread.currentThread().sleep(100);
						response.sendRedirect("http://vegbank.nceas.ucsb.edu/framework/servlet/usermanagement");
        		//response.sendRedirect("/harris/servlet/pageDirector?pageType=loggedin");
					}
					else
					{
						out.println("unsuccessful user creation -- please try again");
					}
				}
				
				else
				{
					System.out.println("incorect input paramaters to authenticate");
				}
			}
			

		}	  //end try
		catch( Exception e ) 
		{
			System.out.println("failed in: "
			+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * method that returns the user name for the given user from the request 
	 * parameters
	 */
	 private String getUserName(Hashtable requestParams)
	 {
		 if (requestParams.containsKey("userName") )
		 {
			String user = (String)requestParams.get("userName");
		 	return(user);
		 }
		 else
		 {
			 return("null");
		 }
	 }
	
	/**
	 * method that returns the parameters required and accepted by this servlet
	 *
	 */
	 private String getParameters()
	 {
		 StringBuffer sb = new StringBuffer();
		 	sb.append("<br> authType {loginUser, createUser, uploadfile} <br>");
		 	sb.append("userName <br>");
		 	sb.append("password <br>");
		 return(sb.toString());
	 }
	
	/**
	 * method to authenticate a known user
	 *
	 */
	 private boolean authenticateUser(Hashtable requestParams, String remoteAddress)
	 {
		 	//grab thee user name and password
			String userName=requestParams.get("userName").toString();
			String passWord=requestParams.get("password").toString();
			System.out.println("name password/pair: "+userName+" "+passWord);
		
			//access the class in the dbAccess class to validate the 
			//login and password with that stored in the database
			return( uda.authenticateUser(userName, passWord) );
	 }
	 

	 /**
	 * method to create a new user identification 
	 * in the vegetation user database
	 *
	 */
	 private boolean createNewUser(Hashtable requestParams, String remoteAddress)
	 {
		 //get the key variables that are required 
		 String emailAddress = requestParams.get("emailAddress").toString();
		 String passWord =  requestParams.get("password").toString();
		 String retypePassWord =  requestParams.get("password2").toString();
		 System.out.println("REQUEST PARAMS: "+requestParams.toString() );
		 
		 //try to get the other variables
		 String givenName = null;
		 String surName = null;
		 if (requestParams.containsKey("givenname") )
		 {
			 givenName= requestParams.get("givenname").toString();
		 }	
		 if (requestParams.containsKey("surname") )
		 {
			surName =  requestParams.get("surname").toString();
		 } 
		 System.out.println("given name: "+givenName);
		 System.out.println("sur name: "+surName);
		 
		 
		 System.out.println("password comparison: '"+passWord+"' '"+retypePassWord+"'");
		 if ( passWord.equals(retypePassWord) &&  passWord.length() > 2 )
		 {
			 System.out.println("equals");
			 uda.createUser(emailAddress, passWord, givenName, surName, remoteAddress);
			 return(true);
		 }
		 else
		 {
			 System.out.println("not equals");
			 return(false);
		 }
	 }


/**
	* Method to register a cookie and set it in the browser if there is a match 
 * between the issued user name and password with a valid name password pair
 * stored in some data store
 *  
 * @param cookie - a cookie whose name, value and max age are set depending on
 * 	the results of validation 
 * @param userName - the user name issued
 * @param passWord - the issued password
 *
 */
	private void cookieDelegator (Cookie cookie, Hashtable requestParams, 
		String remoteAddress, String userName) 
	{

		// below is quite crude but will suffice for the time being.  
		// Soon will rewrite the method to send more meaning values 
		// and will use a database query to access 
		// user information -- the resource bundle is set here b/c
		// this method may be call from another class
		//ResourceBundle rbun = ResourceBundle.getBundle("LocalStrings");
		//String clientLogFile = rbun.getString("requestparams.clientLog");
		
		//get the cookie value form the user database class
		System.out.println(">>>>USER NAME: " + userName );
		String cookieValue = uda.getUserCookieValue(userName);
		String cookieName = "framework";
		if (cookieName != null)
		{
			String cookieAddress = remoteAddress; //same
			cookie = new Cookie(cookieName, cookieValue);
			cookie.setMaxAge(3600);  //set cookie for an hour
			registeredCookie=cookie;
		}
		else
		{
			System.out.println("ERROR null user name");
		}
		
	}





/**
 *  Method to check and see if a cookie is valid using as inputs the name of the
 * cookie, the value of the cookie and the remote host information
 *
 * @param cookieName - name of the cookie for validation
 * @param cookieValue - value of the cookie
 * @param remoteHost - remote host's address
 *
 */
	public void cookieChecker (String cookieName, String cookieValue, 
		String remoteHost) 
	{
		ResourceBundle rb = ResourceBundle.getBundle("plotQuery");
 		if (cookieName.equals("xploit") && cookieValue.equals("001")) 
		{ 
			cookieValidFlag=1;
 		}

 		else 
	 {
		cookieValidFlag=0;
 		}
	}
	
}


