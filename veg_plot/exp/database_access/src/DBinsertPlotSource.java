/** 
 *  Authors: @author@
 *  Release: @release@
 *	
 *  '$Author: harris $'
 *  '$Date: 2002-01-22 21:58:53 $'
 * 	'$Revision: 1.1 $'
 */
package databaseAccess;

import java.lang.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.sql.*;

import org.w3c.dom.Node;

import databaseAccess.*;
import xmlresource.datatype.Plot;
import xmlresource.datatype.VegProject;

import PlotDataSource;

/**
 * this is a class that is used to load the database by parsing an xml document
 * that is consistent with the  veg plot project
 */
public class DBinsertPlotSource implements ServletPluginInterface
{

	// constructor -- define as static the LocalDbConnectionBroker
	// so that methods called by this class can access the 'local' 
	// pool of database connections
	static LocalDbConnectionBroker connectionBroker = new LocalDbConnectionBroker(); 

	public Node plotNode;
	public Connection conn;
	public Statement query = null;
	private PrintWriter out;
	private String logFile = "loadlog.txt";
	
	//refers to the project for which the data is to be used in the db class
	public VegProject project;
	//refers to the given plot, a sub-set of data of the above project, for use in
	//the class
	public Plot plot;

	// variable that are quite general to the class and are going to be used by a
	// number of the methods
	String projectName = null;
	String projectDescription = null;
	String plotName = null;
	Vector plotNameList = new Vector();
	
	//these variables are returned from the database and are used throughout the
	// class
	int plotId;
	int namedPlaceId;
	int plotObservationId;
	int strataId;
	int taxonObservationId;
	int coverMethodId;
	int stratumMethodId;


	// the name of the plots project xml file containing the plot information
	// to be stored in the database
	public String filename = new String();
	
	//debug string buffer
	StringBuffer debug = new StringBuffer();

	//the data source that will be used for loading the db
	public PlotDataSource source;

	//filename is the xml file that contains the project data and the plots
	public DBinsertPlotSource(String plugin, String plot) throws FileNotFoundException
  {
		try
    {
			//System.out.println("start");
			//initialize the data sourcee
			source = null;
			source = new PlotDataSource(plugin);
			//System.out.println("getting the plot data");
			source.getPlot(plot);
			
			//System.out.println("init db");
			//initialize the database connection manager
			connectionBroker.manageLocalDbConnectionBroker("initiate");
			conn = connectionBroker.manageLocalDbConnectionBroker("getConn");
			
			//System.out.println("opening log file");
    	out = new PrintWriter(new FileWriter(logFile));
		}
		catch (Exception e)
		{
			System.out.println("Caught Exception: "+e.getMessage() ); 
			e.printStackTrace();
			System.out.println("Exiting at DBinsertPlotSource constructor");
			System.exit(0);
		}
	}
	
	
	
	/**
	 * this constructor, unlike the other is for inserting a potentially large
	 * numebe of plots stored on a given source
	 */
	public DBinsertPlotSource(String plugin) throws FileNotFoundException
  {
		try
    {
			
			//initialize the data source 
			source = new PlotDataSource(plugin);
			
			//initialize the database connection manager
			connectionBroker.manageLocalDbConnectionBroker("initiate");
			conn = connectionBroker.manageLocalDbConnectionBroker("getConn");
		
    	out = new PrintWriter(new FileWriter(logFile));
		}
		catch (Exception e)
		{
			System.out.println("Caught Exception: "+e.getMessage() ); 
			e.printStackTrace();
			System.out.println("Exiting at DBinsertPlotSource constructor");
			System.exit(0);
		}
	}
	
	
	/**
	 * this constructor -- is empty
	 */
	public DBinsertPlotSource() 
  {
		
	}
	
	
	/**
	 * this is the method that the servlet plugin interface uses
	 */
	public StringBuffer servletRequestHandler(String action, Hashtable params)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("accessed the DBinsertPlotSource class");
		return(sb);
	}
	
	
	/**
   * the main routine used to test the DB class which interacts with the 
	 * vegclass database.
   * 
   * Usage: DBinsertPlotSource pluginName plot1 plot2 ... plotn
   *
   */
  static public void main(String[] args) 
	{
  	try 
		{
			if (args.length >= 2)
			{
				//get the plugin named
				String plugin = args[0];
				//if there is only one plot then handle that differently than multiple
				if (args.length == 2)
				{
					String plot = args[1];
					System.out.println("loading single plot: "+plot+"\n");
					DBinsertPlotSource db = new DBinsertPlotSource(plugin, plot);
					db.insertPlot(plot);
				}
				//multiple plots on the command line
				else
				{
					DBinsertPlotSource db = new DBinsertPlotSource();
					for (int i=1; i < args.length; i++)
					{
						String plot = args[i];
						plot = plot.trim();
						System.out.println("loading plot: " + plot +" \n");
						db = new DBinsertPlotSource(plugin, plot);
						db.insertPlot(plot);
						db = null;
					}
				}
			}
			else
			{
				System.out.println("Usage: DBinsertPlotSource pluginName plot1 plot2 ... plotn");
			}
		}
		catch (Exception e)
		{
			System.out.println("Exception: "+ e.getMessage() );
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * method that loads an entire plot package
	 *
	 * @param plotNames -- a vector that holds all the plotNames
	 */
	 public void insertPlotPackage(String pluginClass)
	{
		try 
		{
				
			//use the test plugin
	//		DBinsertPlotSource db = new DBinsertPlotSource(pluginClass);
			
			//initialize the data source 
			source = new PlotDataSource(pluginClass);
			Vector plotNames = source.getPlotNames();
			
			System.out.println("plots to be inserted: " + plotNames.toString() );
			//Thread.sleep(3000);
			
			
			for (int i =0; i < plotNames.size(); i++)
			{
				
				String pName = plotNames.elementAt(i).toString();
				source = new PlotDataSource(pluginClass);
				DBinsertPlotSource db = new DBinsertPlotSource(pluginClass, pName);
				db.insertPlot(pName);
			}
		}
		catch (Exception e)
		{
			System.out.println("Caught Exception: "+e.getMessage() ); 
			e.printStackTrace();
		}
	}
	
	
	
	
	//method to initiate the insertion of a plot, there are a series of private
	//methods that will be called to actually get the data into the database
	public void insertPlot(String plotName)
	{
		try 
		{
			//System.out.println(source.plotCode);
			//this boolean determines if the plot should be commited or rolled-back
			boolean commit = true;
			
			
			
			//set the auto commit option on the connection to false after getting a
			// new connection from the pool
			conn = connectionBroker.manageLocalDbConnectionBroker("getConn");
			conn.setAutoCommit(false);
			
/** comment out for the time being			
			//see if the project in which this plot is a member exists in the database
			if (projectExists(projectName) == true )
			{
				//get the project id value
				int projectId = getProjectId(projectName);
				insertNamedPlace();
				if (	insertStaticPlotData() == false ) 
				{	
					System.out.println("static data: "+commit);
					commit = false;
				}
				else
				{
					if ( insertPlotObservation() ==false )
					{
						System.out.println("obs data: "+commit);
						commit = false;
					}
					else
					{
						if ( insertStrata() == false )
						{	
							System.out.println("strata data: "+commit);
							commit = false;
						}
						else
						{
							if ( insertTaxonObservations() == false )
							{
								System.out.println("taxonObse data: "+commit);
								commit = false;
							}
						}
					}
				}
				
			}
*/			
			
			
			//else insert a new project and then the plot information
			
			{
				//insert the basis for the project, the project id is auto updated in 
				//the insert project method 			
				insertProject(source.projectName, source.projectDescription);
				int projectId = getProjectId(projectName);
				
//				insertNamedPlace();
				if (insertStaticPlotData() == false ) 
				{	
					System.out.println("static data: "+commit);
					commit = false;
				}

				
	
				else
				{
					if ( insertCoverMethod() == false )
					//if( insertPlotObservation() == false )
					{
						System.out.println("covermethod>: "+commit);
						commit = false;
					}
					
					if ( insertStratumMethod() == false )
					//if( insertPlotObservation() == false )
					{
						System.out.println("stratummethod>: "+commit);
						commit = false;
					}
					
					
					if( insertPlotObservation() == false )
					{
						System.out.println("observation>: "+commit);
						commit = false;
					}
					
					if( insertStrata() == false )
					{
						System.out.println("observation>: "+commit);
						commit = false;
					}
					
					//both the taxon observation tables
					//and the strata composition are uypdated here
					if( insertTaxonObservations() == false )
					{
						System.out.println("observation>: "+commit);
						commit = false;
					}
				}
			}
		
		
		
				/*
					else
					{
						if ( insertStrata() == false )
						{
							System.out.println("strata data: "+commit);
							commit = false;
						}
						else
						{
							if ( insertTaxonObservations() == false )
							{
								System.out.println("taxon obs data: "+commit);
								commit = false;
							}
						}
					}
					
					*/
					
			System.out.println("success?: "+ commit);
			
			//close the connections
			//conn.close();
			if ( commit == true) 
			{
				conn.commit();
				//conn.close();
				debug.append( "INSERTION SUCCESS: \n" );
			}
			else
			{
				conn.rollback();
				debug.append( "INSERTION FAILURE: \n" );
				//conn.close();
			}
			
			
			
			connectionBroker.manageLocalDbConnectionBroker("destroy");
		}
		catch (Exception e)
		{
			System.out.println("Caught Exception: "+e.getMessage() ); 
			e.printStackTrace();
		}
	}
	
	
	//method to update the strata composition tables --should only be called
	// from the insert taxonObservation method
	private boolean insertStrataComposition(String plantName, int taxonObservationId)
	{
		try 
		{
			Vector strataVec = source.getTaxaStrataExistence(plantName, plotName);
			for (int ii =0; ii < strataVec.size(); ii++)
			{
				String curStrata = strataVec.elementAt(ii).toString();
					
				StringBuffer sb = new StringBuffer();
					
				//get the strataCompId number
				int stratumCompositionId  = getNextId("stratumComposition");
				String cover = source.getTaxaStrataCover(plantName, plotName, curStrata);
				
				//get the strata ID
				int stratumId = this.getStrataId(plotObservationId, curStrata);
				
			
				//insert the strata composition values
				sb.append("INSERT into STRATUMCOMPOSITION (stratumComposition_id, "
				+" cheatPlantName, cheatStratumName, taxonStratumCover, stratum_id, " 
				+" taxonobservation_id) ");
				sb.append("values(?,?,?,?,?,?)");
				
				PreparedStatement pstmt = conn.prepareStatement( sb.toString() );
				
				// Bind the values to the query and execute it
  	  	pstmt.setInt(1, stratumCompositionId);
  	  	pstmt.setString(2, plantName);
				pstmt.setString(3, curStrata);
				pstmt.setString(4, cover);
				pstmt.setInt(5, stratumId);
				pstmt.setInt(6, taxonObservationId);
				
				//execute the p statement
  			pstmt.execute();
  			// pstmt.close();
			}
		}
		
		catch (SQLException sqle)
		{
			System.out.println("Caught SQL Exception: "+sqle.getMessage() ); 
			sqle.printStackTrace();
			return(false);
		}
		
		catch (Exception e)
		{
			System.out.println("Caught Exception: "+e.getMessage() ); 
			e.printStackTrace();
			return(false);
		}
		return(true);
	}
	
	
	/**
	 * method to insert the cover method data
	 */
	 private boolean insertCoverMethod()
	 {
		 		
			try
			{
				StringBuffer sb = new StringBuffer();
				//get the strataId number
				coverMethodId = getNextId("covermethod");
				String coverType = "replace this";
				//insert the strata values
				sb.append("INSERT into COVERMETHOD (covermethod_id, coverType) ");
				sb.append("values(?,?)");
				
				PreparedStatement pstmt = conn.prepareStatement( sb.toString() );
				
				// Bind the values to the query and execute it
  	  	pstmt.setInt(1, coverMethodId);
  	  	pstmt.setString(2, coverType);
				
				//execute the p statement
  		  pstmt.execute();
  		 // pstmt.close();
			}
			catch (Exception e)
			{
				System.out.println("Caught Exception: "+e.getMessage() ); 
				e.printStackTrace();
				//return false so that the calling method knows to roll-back
				return(false);
				//System.exit(0);
			}
		return(true);
	 }
	 
	 
	 
	/**
	 * method to insert the cover method data
	 */
	 private boolean insertStratumMethod()
	 {
		 		
			try
			{
				StringBuffer sb = new StringBuffer();
				//get the strataId number
				stratumMethodId = getNextId("stratummethod");
				String stratumMethodName = "replace this";
				//insert the strata values
				sb.append("INSERT into STRATUMMETHOD (stratummethod_id, stratummethodname) ");
				sb.append("values(?,?)");
				
				PreparedStatement pstmt = conn.prepareStatement( sb.toString() );
				
				// Bind the values to the query and execute it
  	  	pstmt.setInt(1, stratumMethodId);
  	  	pstmt.setString(2, stratumMethodName);
				
				//execute the p statement
  		  pstmt.execute();
  		 // pstmt.close();
			}
			catch (Exception e)
			{
				System.out.println("Caught Exception: "+e.getMessage() ); 
				e.printStackTrace();
				//return false so that the calling method knows to roll-back
				return(false);
				//System.exit(0);
			}
		return(true);
	 }
	 
	 
	 
	 
	
	
/**
 *  Method that returns a strataId value that corresponds to a given stratumType
 * and an observation id value 
 *
 * @param obseravtionId - the taxonObservation value for a given recognition of 
 *	a taxon 
 * @param strataType - the type of strata in which the taxon is found
 *
 */
	private int getStrataId (int plotObservationId, 
		String stratumType ) 
	{
		int strataId = -999;
		try 
		{		
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("SELECT STRATUM_ID from STRATUM where OBSERVATION_ID = "
			+plotObservationId+" and stratumName like '%"+stratumType+"%'");
			
			Statement query = conn.createStatement();
			ResultSet rs = query.executeQuery( sb.toString() );
			
			int cnt = 0;
			while ( rs.next() ) 
			{
				strataId = rs.getInt(1);
				cnt++;
			}
			//send warnings
			if ( cnt == 0)
			{
				System.out.println("warning: There were no matching strata");
			}
			
		}
		catch (Exception e) 
		{
			System.out.println("Caught Exception  " + e.getMessage());
		}
		return(strataId);
	}


	
	//method to add the taxonObservation data to the database
	// if the method fails it will return false and the plot will be rolled 
	//backe
	private boolean insertTaxonObservations()
	{
		boolean successfulCommit = true;
		try 
		{
			//get the number of taxonObservations
			Vector uniqueTaxa = source.plantTaxaNames;
			
			for (int i =0; i < uniqueTaxa.size(); i++)
			{
				StringBuffer sb = new StringBuffer();
				//get the taxonObservation number
				taxonObservationId = getNextId("taxonObservation");
				
				String authorNameId = uniqueTaxa.elementAt(i).toString();
				String percentCover = "25";
				
			
				//insert the values
				sb.append("INSERT into TAXONOBSERVATION (taxonObservation_id, observation_id, "
					+" cheatplantName ) "
					+" values(?,?,?) ");
				
				PreparedStatement pstmt = conn.prepareStatement( sb.toString() );
				
				// Bind the values to the query and execute it
  	  	pstmt.setInt(1, taxonObservationId);
  	  	pstmt.setInt(2, plotObservationId);
				pstmt.setString(3, authorNameId);
				
				//execute the p statement
  		  pstmt.execute();
  		 // pstmt.close();
			 
			 //now update the strataComposition table
			 try
			 {
				 
				 	boolean result = this.insertStrataComposition(authorNameId, taxonObservationId);
			 		if (result = false)
					{
						return(false);
					}
			 }
			 catch (Exception e)
			 {
				 	System.out.println("Caught Exception: "+e.getMessage() ); 
					e.printStackTrace();	
					return(false);
			 }
			
			
			}
		}
		catch (Exception e)
		{
			System.out.println("Caught Exception: "+e.getMessage() ); 
			e.printStackTrace();
			//System.exit(0);
			return(false);
		}
		return( true );
	}
	
	
	
	
	/**
	 * method that returns false if the strataElements cannot be loaded to the
	 * database
	 *
	 */
	private boolean insertStrata()
	{
		try 
		{
			
			//get the names of the recognized strata
			Vector strataTypes = source.uniqueStrataNames;
			for (int i =0; i < strataTypes.size(); i++)
			{
				StringBuffer sb = new StringBuffer();
				//get the strataId number
				strataId = getNextId("stratum");
				
				String sName = strataTypes.elementAt(i).toString();
			
				
				String cover = source.getStrataCover(plotName, sName);
				String base =  source.getStrataBase(plotName, sName);
				String height = source.getStrataHeight(plotName, sName);

				
				//insert the strata values
				sb.append("INSERT into STRATUM (stratum_id, observation_id, stratumName, " 
				+" stratumCover, stratumBase ,stratumHeight) "
				+" values(?,?,?,?,?,?)");
				
			
				PreparedStatement pstmt = conn.prepareStatement( sb.toString() );
  		  // Bind the values to the query and execute it
  		  pstmt.setInt(1, strataId);
  		  pstmt.setInt(2, plotObservationId);
				pstmt.setString(3, sName);
  		  pstmt.setString(4, cover);
				pstmt.setString(5, base);
				pstmt.setString(6, height);
			
			
				//execute the p statement
  		  pstmt.execute();
  		 // pstmt.close();
			
			}			
		
		}
		catch (Exception e)
		{
			System.out.println("Caught Exception: "+e.getMessage() ); 
			e.printStackTrace();
			//System.exit(0);
			return(false);
		}
		return(true);
	}
	
	
	
		/**
		 * method that returns the prepared statement for inserting data
		 * into the plotObservation table
		 *
		 * @param conn -- the current connection
		 * @return pstmt -- the prepared statement
		 *
		 */
		private boolean insertPlotObservation()
		{
			StringBuffer sb = new StringBuffer();
			try 
			{
				//get the plotid number
				plotObservationId = getNextId("observation");
				//the variables from the plot file
				String observationCode = "obscode";
				String startDate = "01-jan-1998";
				String stopDate = "02-jan-1998";
				String taxonObservationArea = "999.99";
				String autoTaxonCover = "replace this";
				String coverDispersion = "replace this";
			
				//set up the debugging
//				StringBuffer debug = new StringBuffer();
				debug.append("plotId: "+ plotId+"\n");
				debug.append("observationCode: "+ observationCode+"\n");
				debug.append("startDate: "+ startDate+"\n");
				debug.append("stopDate: "+ stopDate +"\n");
			
				//System.out.println( debug.toString() );
			

				sb.append("INSERT into OBSERVATION (observation_id, covermethod_id,  "
				+" plot_Id, authorobscode, "
				+" obsStartDate, obsEndDate, stratummethod_id, taxonObservationArea, "
				+" autoTaxonCover, coverDispersion) "
				+" values(?,?,?,?,?,?,?,?,?,?)" );
	
			
				PreparedStatement pstmt = conn.prepareStatement( sb.toString() );
  		  // Bind the values to the query and execute it
  		  pstmt.setInt(1, plotObservationId);
  		  pstmt.setInt(2, coverMethodId);
				pstmt.setInt(3, plotId);
  		  pstmt.setString(4, observationCode);
				pstmt.setString(5, startDate);
				pstmt.setString(6, stopDate);
				pstmt.setInt(7, stratumMethodId);
				
				pstmt.setString(8, taxonObservationArea);
				pstmt.setString(9, autoTaxonCover);
				pstmt.setString(10, coverDispersion);
				
			
				//execute the p statement
  		  pstmt.execute();
  		 // pstmt.close();
			}
			catch (Exception e)
			{
				System.out.println("Caught Exception: "+e.getMessage() ); 
				e.printStackTrace();
				//return false so that the calling method knows to roll-back
				return(false);
				//System.exit(0);
			}
		return(true);
	}
	
	
	
	//method that inserts the named place data for a plot and then returns the
	//named place id
	private int insertNamedPlace()
	{
		StringBuffer sb = new StringBuffer();
		try 
		{
			//get the plotid number
			namedPlaceId = getNextId("namedPlace");
			//the variables from the plot file
			String placeName = plot.getPlaceName();
		
			sb.append("INSERT into NAMEDPLACE (namedplace_id, placeName) "
				+"values("+namedPlaceId+", '"+placeName+"')" );
			Statement insertStatement = conn.createStatement();
			insertStatement.executeUpdate(sb.toString());
			System.out.println("inserted named place ");
		}
		catch (Exception e)
		{
			System.out.println("Caught Exception: "+e.getMessage() ); 
			e.printStackTrace();
		}
		return(namedPlaceId);
	}
	
	
	
	
	
	/**
	 *	method to insert the static plot data like names and locations
	 * and if it catches an exception then false is returned to the calling 
	 * class so that a roll-back can be issued
	 *
	 */
	private boolean insertStaticPlotData()
	{
	//	int plotId = -999;
		StringBuffer sb = new StringBuffer();
		try 
		{
			//get the plotid number
			plotId = getNextId("plot");
			int projectId = getProjectId(projectName);
			
			//the variables from the plot file
			plotName = source.plotCode;
			String authorPlotCode = plotName;
			String surfGeo = source.surfGeo;
			String parentPlot = "9";
			String latitude = source.latitude; //not null
			String longitude = source.longitude; //not null
			String plotArea = source.plotArea;
			String altValue = source.elevation;
			String slopeAspect = source.slopeAspect;
			String slopeGradient = source.slopeGradient;
			String topoPosition = source.topoPosition;
			String hydrologicRegime = source.hydrologicRegime;
			String plotShape = source.plotShape;
			String confidentialityStatus = source.confidentialityStatus; //not null
			String confidentialityReason = source.confidentialityReason; //not null
			
			String xCoord = source.xCoord;
			String yCoord = source.yCoord;
			String state = source.state;
			String country = source.country;
			String authorLocation = source.authorLocation;
			
			//print the variables to the screen for debugging
			//StringBuffer debug = new StringBuffer();
			debug.append("authorPlotCode: "+ plotName+"\n");
			

			sb.append("INSERT into PLOT (project_id, authorPlotCode, plot_id, "
				+"geology, "
				+"latitude, longitude, area, elevation,"
				+"slopeAspect, slopeGradient, topoPosition, "
				+"shape, confidentialityStatus, confidentialityReason, "
				+"authore, authorn, state, country, authorLocation) "
				+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				
			PreparedStatement pstmt = conn.prepareStatement( sb.toString() );
			
  	  // Bind the values to the query and execute it
  	  pstmt.setInt(1, projectId);
  	  pstmt.setString(2, plotName);
			pstmt.setInt(3, plotId);
			pstmt.setString(4, surfGeo);
			pstmt.setString(5, latitude);
			pstmt.setString(6, longitude);
			pstmt.setString(7, plotArea);
			pstmt.setString(8, altValue);
			pstmt.setString(9, slopeAspect);
			pstmt.setString(10, slopeGradient);
			pstmt.setString(11, topoPosition);
			pstmt.setString(12, plotShape);
			pstmt.setString(13, confidentialityStatus);
			pstmt.setString(14, confidentialityReason);
			
			pstmt.setString(15, xCoord);
			pstmt.setString(16, yCoord);
			pstmt.setString(17, state);
			pstmt.setString(18, country);
			pstmt.setString(19, authorLocation);

			
			
			
			pstmt.getWarnings();
  	  pstmt.execute();
  	  pstmt.close();
		}
		catch (SQLException sqle)
		{
			System.out.println("Caught SQL Exception: "+sqle.getMessage() ); 
			sqle.printStackTrace();
			return(false);
			//System.exit(0);
		}
		catch (Exception e)
		{
			System.out.println("Caught Exception: "+e.getMessage() ); 
			e.printStackTrace();
			return(false);
			//System.exit(0);
		}
		return(true);
	}
	
	
	
	//method that returns true if the project with this name exists in the 
	//database
	private boolean projectExists(String projectName)
	{
		int rows = 0;
		try 
		{
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT count(*) from PROJECT where projectName like '"+projectName+"'" );
			Statement query = conn.createStatement();
			ResultSet rs = query.executeQuery( sb.toString() );
			while ( rs.next() ) 
			{
				rows = rs.getInt(1);
			}
		}
		catch (Exception e)
		{
			System.out.println("Caught Exception: "+e.getMessage() ); 
			e.printStackTrace();
		}
		if (rows == 0)
		{
			return(false);
		}
		else
		{
			return(true);
		}
	}
	
	
	
	//method to return the projectId given as input the 
	//name of a project -- before this method is to be 
	//called make sure that the project does actually exist
	//using the method 'projectExists'
	private int getProjectId(String projectName)
	{
		int projectId = 0;
		try 
		{
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT project_id from PROJECT where projectName like '"+projectName+"'" );
			Statement query = conn.createStatement();
			ResultSet rs = query.executeQuery( sb.toString() );
			while ( rs.next() ) 
			{
				projectId = rs.getInt(1);
			}
		}
		catch (Exception e)
		{
			System.out.println("Caught Exception: "+e.getMessage() ); 
			e.printStackTrace();
		}
		return(projectId);
	}
	
	
	
	
	/**
	 * method that returns the number of row in a given table
	 */
	private int getNextId(String tableName)
	{
		int rows = -1;
		try 
		{
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT count(*) from "+tableName );
			Statement query = conn.createStatement();
			ResultSet rs = query.executeQuery( sb.toString() );
			while ( rs.next() ) 
			{
				rows = rs.getInt(1);
				//System.out.println("grabbed "+rows );
				//query = conn.createStatement();
			}
		}
		catch (Exception e)
		{
			System.out.println("Caught Exception: "+e.getMessage() ); 
			e.printStackTrace();
			//System.exit(0);
		}
		return(rows);
	}
	
	//method that inserts a new project into the database
	public void insertProject(String projectName, String projectDescription)
	{
		try 
		{
			System.out.println("Project Primary Key: "+getNextId("project"));
			StringBuffer sb = new StringBuffer();
			int projectId = getNextId("project");
			sb.append("INSERT into PROJECT (project_id, projectName, projectdescription) "
			 +"values("+projectId+", '"+projectName+"', '"+projectDescription+"')" );
			Statement insertStatement = conn.createStatement();
			insertStatement.executeUpdate(sb.toString());
			System.out.println("inserted PROJECT");
			
		}
		catch (Exception e)
		{
			System.out.println("Caught Exception: "+e.getMessage() ); 
			e.printStackTrace();
		}
		
	}


}
