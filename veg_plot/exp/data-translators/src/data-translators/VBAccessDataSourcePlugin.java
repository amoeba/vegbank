import java.lang.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.sql.*;


/**
 * plugin to allow access to plot data stored in the VegBank - plots 
 * database 
 *
 *  Release: 
 *	
 *  '$Author: harris $'
 *  '$Date: 2002-05-14 13:20:04 $'
 * 	'$Revision: 1.2 $'
 */
 
//public class VBAccessDataSourcePlugin
public class VBAccessDataSourcePlugin extends VegBankDataSourcePlugin implements PlotDataSourceInterface 
{
	private String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
	private String dbUrl = "jdbc:odbc:vegbank_access";
	private String dbUser = "datauser";
	private Connection con = null;
	
	
	public VBAccessDataSourcePlugin()
	{
		super("msaccess");
		try 
		{
			con = super.con;
		}
		catch (java.lang.Exception ex) 
		{
			System.out.println("Exception: " + ex + "<BR>");
			ex.printStackTrace();
		}
	}


 	/** 
	 * method that returns the accession number associated with a plot id
	 * the input plot id is the unique identifier of the plot as used by 
	 * the RDBMS
	 * @param plotId -- the RDBMS unique plot ID
	 */
	public String getAccessionValue(String plotId)
	{
		String s = "";
		return(s);
	}
	
	
  /**
	 * method to return the taxa code from a data source using as input
	 * the scientific plant name -- or the plant name that comes from
	 * the 'getPlantTaxaNames' method
	 *
	 * @param plantName -- the scientific plantName
	 */
	public String getPlantTaxonCode(String plantName)
	{
	 	return("code");
	}
																																																																																																												
	
	
	/**
	 * method that returns a vector of PLOT ID'S (Plot table Primary keys) 
	 * from the target VegBank Database
	 */
	public Vector getPlotIDs()
	{
		Vector v = new Vector();
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select distinct PLOT_ID from PLOT");
							
			while (rs.next()) 
			{
				String s = rs.getString(1);
				v.addElement(s);
			}
		}
		catch (java.lang.Exception ex) 
		{   
			System.out.println("Exception: " + ex );
			ex.printStackTrace();
		}
		return(v);
	}
	
	
	/**
	 * method that returns a vector of plot codes which are the 
	 * VegBank Accession Numbers, for now it will be the authorPlotCode
	 * 
	 */
	public Vector getPlotCodes()
	{
		return super.getPlotCodes();
	}
 	

	/**
 	 * returns the soil drainage of a plot
 	 * @param plotName -- the plot
 	 */
 	public String getSoilDrainage(String plotName)
 	{
 		return("");
 	}
 	
 	/**
 	 * returns the author's observation code
 	 * @param plotName -- the plot
 	 */
 	public String getAuthorObsCode(String plotName)
 	{
 		return("");
 	}
 	
 	/**
 	 * returns the start date for the plot observation
 	 * @param plotName -- the plot
 	 */
 	public String getObsStartDate(String plotName)
 	{
		return super.getObsStartDate(plotName);
 	}
 	
 	/**
 	 * returns the stop date for the plot observation
 	 * @param plotName -- the plot
 	 */
 	public String getObsStopDate(String plotName)
 	{
	 return super.getObsStopDate(plotName);	
 	}
 	
 	/**
 	 * returns the soil depth of a plot
 	 * @param plotName -- the plot
 	 */
 	public String getSoilDepth(String plotName) 
 	{
 		return("");
 	}


	/**
 	 * returns true if the plot is a permanent plot or false if not
 	 * @param plotName -- the plot
 	 */
 	 public boolean isPlotPermanent(String plotName)
 	 {
 	 	return super.isPlotPermanent(plotName);
 	 }
  
  /**
 	* returns the soil taxon for the plot -- this is the USDA class
 	* heirarchy (eg., Order, Group, Family, Series etc..)
 	* @param plotName -- the plot
 	*/
 	public String getSoilTaxon(String plotName)
 	{
 		return("");
 	}
 	
 	/**
 	 * returns how the soil taxon was determined (eg., field observation
 	 * mapping, other ...)
 	 * @param plotName -- the plot
 	 */
 	public String getSoilTaxonSource(String plotName)
 	{
 		return("");
 	}



	//returns all the plots stored in the access file
	public Vector getPlotNames()
	{
		Vector v = new Vector();
		v.addElement("test-plot");
		return(v);
	}
	
	//returns the project name 
	public String getProjectName(String plotName)
	{ 
		return super.getProjectName(plotName); 
	}
	
	//returns the project description
	public Vector getProjectContributors(String plotName)
	{
		return super.getProjectContributors(plotName);
	}
	
	//retuns the person's salutation based on their full name which is the
	//concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorSalutation(String contributorWholeName)
	{
		return super.getProjectContributorSalutation(contributorWholeName);
	}
	//retuns the person's given based on their full name which is the
	//concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorGivenName(String contributorWholeName)
	{
		return super.getProjectContributorGivenName(contributorWholeName);
	}
	//retuns the person's middle name based on their full name which is the
	//concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorMiddleName(String contributorWholeName)
	{
		return super.getProjectContributorMiddleName(contributorWholeName);
	}
	//retuns the person's surName based on their full name which is the
	//concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorSurName(String contributorWholeName)
	{
		return super.getProjectContributorSurName(contributorWholeName);
	}
	//retuns the name of an org. that a person is associated with based on 
	//their full name which is the concatenated givename and surname of the 
	//user like 'bob peet'
	public String getProjectContributorOrganizationName(String contributorWholeName)
	{
		return super.getProjectContributorOrganizationName(contributorWholeName);
	}
	
	//retuns the person's contactinstructions based on their full name which is the
	//concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorContactInstructions(String contributorWholeName)
	{
		return super.getProjectContributorContactInstructions(contributorWholeName);
	}
	//retuns the person's phone number based on their full name which is the
	//concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorPhoneNumber(String contributorWholeName)
	{
		return super.getProjectContributorPhoneNumber(contributorWholeName);
	}
	//retuns the person's cellPhone based on their full name which is the
	//concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorCellPhoneNumber(String contributorWholeName)
	{
		return super.getProjectContributorCellPhoneNumber(contributorWholeName);
	}
	//retuns the person's fax phone number based on their full name which is the
	//concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorFaxPhoneNumber(String contributorWholeName)
	{
		return super.getProjectContributorFaxPhoneNumber(contributorWholeName);
	}
	//retuns the party's position within an organization based on their full 
	// name which is the concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorOrgPosition(String contributorWholeName)
	{
		return super.getProjectContributorOrgPosition(contributorWholeName);
	}
	//retuns the person's email based on their full name which is the
	//concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorEmailAddress(String contributorWholeName)
	{
		return super.getProjectContributorEmailAddress(contributorWholeName);
	}
	//retuns the person's address line based on their full name which is the
	//concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorDeliveryPoint(String contributorWholeName)
	{
		return super.getProjectContributorDeliveryPoint(contributorWholeName);
	}
	//retuns the person's city based on their full name which is the
	//concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorCity(String contributorWholeName)
	{
		return super.getProjectContributorCity(contributorWholeName);
	}
	
	//returns the administrative area, or state that a party is from
	public String getProjectContributorAdministrativeArea(String contributorWholeName)
	{
		return super.getProjectContributorAdministrativeArea(contributorWholeName);
	}
	//retuns the zip code for a party
	public String getProjectContributorPostalCode(String contributorWholeName)
	{
		return super.getProjectContributorPostalCode(contributorWholeName);
	}
	//retuns the person's country based on their full name which is the
	//concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorCountry(String contributorWholeName)
	{
			return super.getProjectContributorCountry(contributorWholeName);
	}
	//retuns a boolean 'true' if it is a party's current address based on their 
	//full name which is the concatenated givename and surname of the user like 'bob peet'
	public String getProjectContributorCurrentFlag(String contributorWholeName)
	{
		return super.getProjectContributorCurrentFlag(contributorWholeName);
	}
	//retuns the date that the address became current for a party based on 
	//their full name which is the concatenated givename and surname of the 
	//user like 'bob peet'
	public String getProjectContributorAddressStartDate(String contributorWholeName)
	{
		return super.getProjectContributorAddressStartDate(contributorWholeName);
	}
	
	//returns the start date for the project
	public String getProjectStartDate(String plotName )
	{
		return super.getProjectStartDate(plotName);
	}
	
	//returns the stop date for the project
	public String getProjectStopDate(String plotName )
	{
			return super.getProjectStopDate(plotName);
	}
	
	//returns the placeNames each name can be used to retrieve
	//other information about a place
	public Vector getPlaceNames(String plotName)
	{
		return super.getPlaceNames(plotName);
	}
	
	//retuns a description of a place based on a placeName
	public String getPlaceDescription(String placeName)
	{
		if ( placeName.startsWith("Marina") )
			return("marina park by the sunken ship imitation");
		else if (placeName.startsWith("Ventura"))
			return("the ventura harbor on the north side");
		else return("");
	}
	//returns a placeCode based on a placeName
	public String getPlaceCode(String placeName)
	{
		if ( placeName.startsWith("Marina") )
			return("MarinePrk");
		else if (placeName.startsWith("Ventura"))
			return("VenHbr");
		else return("");
	}
	//returns a place system based on a placeName
	public String getPlaceSystem(String placeName)
	{
		return("unknown system");
	}
	//returns the owner of a place based on the name of a place
	public String getPlaceOwner(String placeName)
	{
		if ( placeName.startsWith("Marina") )
			return("Ventura county");
		else if (placeName.startsWith("Ventura"))
			return("State of California");
		else return("");
	}
	
	
	//returns the project description
	public String getProjectDescription(String plotName )
	{
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select  projectdescription  "
			+" from PROJECT where PROJECT_ID = 0 "  );
							
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
		// All other types of exceptions
			System.out.println("Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	
	//returns the plot code for the current plot
	public String getPlotCode(String plotName)
	{ 
		return super.getPlotCode(plotName);
	}
	
	//returns the easting 
	public String getXCoord(String plotName)
	{ 
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select  authore "
			+" from PLOT where PLOT_ID = " + plotName );
							
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
		// All other types of exceptions
			System.out.println("Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	//returns the northing
	public String getYCoord(String plotName)
	{ 
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select  authorn "
			+" from PLOT where PLOT_ID = " + plotName );
							
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
		// All other types of exceptions
			System.out.println("Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	/**
	 * method that retuns the latitude for an input plotid
	 * @param plotName -- the VegBank PLOTID
	 * @return latitude -- the validated latitude of that plot
	 */
	public String getLatitude(String plotName)
	{
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select  LATITUDE "
			+" from PLOT where PLOT_ID = " + plotName );
							
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
		// All other types of exceptions
			System.out.println("Exception: " + ex );
			ex.printStackTrace();
		}
	return(s);
	}
	
	/**
	 * method that retuns the latitude for an input plotid
	 * @param plotName -- the VegBank PLOTID
	 * @return longitude -- the validated latitude of that plot
	 */
	public String getLongitude(String plotName)
	{
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select  LONGITUDE "
			+" from PLOT where PLOT_ID = " + plotName );
							
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
		// All other types of exceptions
			System.out.println("Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	
	//returns the geographic zone
	public String getUTMZone(String plotName)
	{ 
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select  authorzone "
			+" from PLOT where PLOT_ID = " + plotName );
							
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
		// All other types of exceptions
			System.out.println("Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	//returns the plot shape
	public String getPlotShape(String plotName)
	{ 
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select  shape "
			+" from PLOT where PLOT_ID = " + plotName );
							
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
		// All other types of exceptions
			System.out.println("Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	//returns the plot area -n m^2
	public String getPlotArea(String plotName)
	{ 
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select  area "
			+" from PLOT where PLOT_ID = " + plotName );
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
		// All other types of exceptions
			System.out.println("Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	//returns the state for the current plot
	public String getState(String plotName)
	{ 
		String s = "NC";
		return(s);
	}
	
	//returns the state for the current plot
	public String getCommunityName(String plotName)
	{ return("ice-plant community"); }
	
	
	/**
	 * returns the community code for the named plot
	 */
	public String getCommunityCode(String plotName)
	{
		String s = "";
		return(s);
	}
	
	/**
	 * returns the community level of the framework for the named plot
	 */
	public String getCommunityLevel(String plotName)
	{
		String s = "";
		return(s);
	}
	
	/**
	 * returns the community framework for the named plot
	 */
	public String getCommunityFramework(String plotName)
	{
		String s = "";
		return(s);
	}
	
	
	//retuns the topo position
	public String getTopoPosition(String plotName)
	{ 
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select  topoposition "
			+" from PLOT where PLOT_ID = " + plotName );
							
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
		// All other types of exceptions
			System.out.println("Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	//returns the hydrologic regime
	public String getHydrologicRegime(String plotName)
	{ 
		String s = null;
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select  hydrologicregime "
			+" from observation where  PLOT_ID = " + plotName );
							
			while (rs.next()) 
			{
				 s = rs.getString(1);
			}
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
		// All other types of exceptions
			System.out.println("Exception: " + ex );
			ex.printStackTrace();
		}
		return(s);
	}
	
	/**
	 * method that retuns the latitude for an input plotid
	 * @param plotName -- the VegBank PLOTID
	 * @return geology -- the validated surface geology of that plot
	 */
	public String getSurfGeo(String plotName)
	{
		return(super.getSurfGeo(plotName) );
	}
	
	//returns the country
	public String getCountry(String plotName)
	{ 
		return super.getCountry(plotName); 
	}
	
	//returns the slope aspect
	public String getSlopeAspect(String plotName)
	{
		return super.getSlopeAspect(plotName);
	}
	
	//returns the slope gradient
	public String getSlopeGradient(String plotName)
	{
		return super.getSlopeGradient(plotName);
	}
	
	//returns the size of the stand -- extensive etc..
	public String getStandSize(String plotName)
	{
		return("extensive");
	}
	//returns the location as described by the author
	public String getAuthorLocation(String plotName)
	{
		return super.getAuthorLocation(plotName);
	}
	//returns the landForm
	public String getLandForm(String plotName)
	{
		return("aluvial fan");
	}
	//retuns the elevation
	public String getElevation(String plotName)
	{
		return super.getElevation(plotName);
	}
	//returns the elevation accuracy
	public String getElevationAccuracy(String plotName)
	{
		return super.getElevationAccuracy(plotName);
	}
	
	//return the confidentiality reason -- not null
	public String	getConfidentialityReason(String plotName)
	{
		return super.getConfidentialityReason(plotName);
	}
	
	//return the confidentiality status -- not null 
	public String getConfidentialityStatus(String plotName)
	{
		return super.getConfidentialityStatus(plotName);
	}
	
	/**
	 * method that retuns the unique names for a plotID
	 * @param plotName -- the VegBank PLOTID
	 * @return v -- a vector containg the unique strata for that plot
	 *
	 */
	public Vector getUniqueStrataNames(String plotName)
	{
		Vector v = new Vector();
		Statement stmt = null;
		try 
		{
			Vector observationIDs = this.getObservationIDs( plotName );
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select distinct STRATUMNAME "
			+" from STRATUMTYPE " );
			while (rs.next()) 
			{
				String s = rs.getString(1);
				v.addElement(s);
			}
		}
		catch (java.lang.Exception ex) 
		{   
			// All other types of exceptions
			System.out.println("Exception: " + ex );
			ex.printStackTrace();
		}
		return(v);
	}
	
	
	/**
	 * method that returns the observation ids that are children
	 * of a plot id
	 * @param plotName -- the VegBank plotId
	 * @return v -- a vector containg all the observtaions for that specific
	 *		plot id provided as input
	 */
	 private  Vector getObservationIDs(String plotName)
	{
		Vector v = new Vector();
		Statement stmt = null;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select OBSERVATION_ID from OBSERVATION "
			+"where PLOT_ID = " + plotName );
							
			while (rs.next()) 
			{
				String s = rs.getString(1);
				v.addElement(s);
			}
			
		}
		catch (SQLException ex) 
		{
			this.handleSQLException( ex );
		}
		catch (java.lang.Exception ex) 
		{   
			// All other types of exceptions
			System.out.println("Exception: " + ex );
			ex.printStackTrace();
		}
		return(v);
	}
	
	
	
	/**
	 * method that takes a plot code and populates the public varaibles with
	 * data related to that specific plot
	 *
	 * @param plotName -- the name of a plot for which to retrieve data for
	 */
	 public void getPlotData(String plotName)
	 {
		 //there is only one plot here  so this method should be blank
	 }
	 
	
		
		/**
		 * method that returns a vector with the unique plant 
		 * taxa names for a given plot -- this method is defined
		 * in the plugin interface
		 */
		 public Vector getPlantTaxaNames(String plotName)
		 {
				Vector v = new Vector();
				Statement stmt = null;
				try 
				{
					Vector observationIDs = this.getObservationIDs(plotName);
					stmt = con.createStatement();
					StringBuffer sb = new StringBuffer();
					sb.append("select plantname from plantname where plantname_id in ( ");
					sb.append("select distinct plantname_id from TAXONOBSERVATION where OBSERVATION_ID = ");
					sb.append((String)observationIDs.elementAt(0)+" )" );
					System.out.println("VBAccessDataSourcePlugin > query: " + sb.toString() );
					ResultSet rs = stmt.executeQuery( sb.toString() );	
					while (rs.next()) 
					{
						String s = rs.getString(1);
						v.addElement(s);
					}
				}
				catch (java.lang.Exception ex) 
				{   
					System.out.println("Exception: " + ex );
					ex.printStackTrace();
				}
				return(v);
		 }
		 


	/**
	 * method that returns the strata in which the input plant
	 * exists within the input plot
	 */
	 public Vector getTaxaStrataExistence(String plantName, String plotName)
	 {
		 Vector v = new Vector();
		 	String s = null;
			Statement stmt = null;
			try 
			{
				stmt = con.createStatement();
				StringBuffer sb = new StringBuffer();
				sb.append("select STRATUMNAME from STRATUMTYPE WHERE STRATUMTYPE_ID in ( ");
				sb.append("select STRATUMTYPE_ID FROM STRATUM WHERE OBSERVATION_ID in ( ");
				sb.append("select observation_id from OBSERVATION where PLOT_ID = " + plotName+" ) and STRATUM_ID in ");
				sb.append(" ( select STRATUM_ID from stratumcomposition where TAXONOBSERVATION_ID in ( ");
				sb.append(" select TAXONOBSERVATION_ID from TAXONOBSERVATION where PLANTNAME_ID = ( ");
				sb.append(" select PLANTNAME_ID from PLANTNAME where PLANTNAME LIKE '"+plantName+"' ) ) ) ) ");
				ResultSet rs = stmt.executeQuery( sb.toString() );
				while (rs.next()) 
				{
					s = rs.getString(1);
					v.addElement(s);
				}
				rs.close();
				stmt.close();
			}
			catch (SQLException ex) 
			{
				this.handleSQLException( ex );
			}
			catch (java.lang.Exception ex) 
			{   
				System.out.println("Exception: " + ex );
				ex.printStackTrace();
			}
			return(v);
	 }
	 
	 
	 /**
	 * method to return the cover for a given strata for a given 
	 * plot
	 */
	 public String getStrataCover(String plotName, String strataName)
	 {
		 return("100");
	 }
	 
	 /** 
	  * method the returns the base height of a strata based on the 
		* name of that starta and the plot for which that strata is included
		*/
		public String getStrataBase(String plotName, String strataName)
		{
			String s = null;
			Statement stmt = null;
			try 
			{
				stmt = con.createStatement();
				StringBuffer sb = new StringBuffer();
				sb.append("select STRATUMBASE from STRATUM where STRATUMTYPE_ID in (");
				sb.append(" select DISTINCT STRATUMTYPE_ID from STRATUMTYPE where STRATUMNAME LIKE '"+strataName+"') and OBSERVATION_ID = (");
				sb.append(" select observation_id from OBSERVATION where PLOT_ID = " + plotName+" )");
				//System.out.println("VBAccessDataSourcePlugin > query: " + sb.toString() );
				ResultSet rs = stmt.executeQuery( sb.toString() );			
				while (rs.next()) 
				{
					s = rs.getString(1);
				}
			}
			catch (java.lang.Exception ex) 
			{   
				System.out.println("Exception: " + ex );
				ex.printStackTrace();
			}
			return(s);
		}
		
		/**
		 * method that returns the upper height of a starata based on 
		 * the name of the strata and the plot inwhich the strata exists
		 */
		public String getStrataHeight(String plotName, String strataName)
		{
			String s = null;
			Statement stmt = null;
			try 
			{
				stmt = con.createStatement();
				StringBuffer sb = new StringBuffer();
				sb.append("select STRATUMHEIGHT from STRATUM where STRATUMTYPE_ID in (");
				sb.append(" select DISTINCT STRATUMTYPE_ID from STRATUMTYPE where STRATUMNAME LIKE '"+strataName+"') and OBSERVATION_ID = (");
				sb.append(" select observation_id from OBSERVATION where PLOT_ID = " + plotName+" )");
				//System.out.println("VBAccessDataSourcePlugin > query: " + sb.toString() );
				ResultSet rs = stmt.executeQuery( sb.toString() );			
				while (rs.next()) 
				{
					s = rs.getString(1);
				}
			}
			catch (java.lang.Exception ex) 
			{   
				System.out.println("Exception: " + ex );
				ex.printStackTrace();
			}
			return(s);
		}
		
	 
	 /**
	 * method that returns the strata cover for a given plant, strata, and 
	 * plot
	 */
	 public String getTaxaStrataCover(String plantName, String plotName, String stratum)
	 {
		 	String s = null;
			Statement stmt = null;
			try 
			{
				stmt = con.createStatement();
				StringBuffer sb = new StringBuffer();
				System.out.println("VBAccessDataSourcePlugin > querying cover for: " + plantName+" "+plotName+" "+stratum );
				
				sb.append("select TAXONSTRATUMCOVER from STRATUMCOMPOSITION where TAXONOBSERVATION_ID in ( ");
				sb.append(" select TAXONOBSERVATION_ID from TAXONOBSERVATION where PLANTNAME_ID = ( ");
				sb.append(" select PLANTNAME_ID from PLANTNAME where PLANTNAME LIKE '"+plantName+"' ) )");
				sb.append(" and ");
				sb.append(" STRATUM_ID in ( select STRATUM_ID from STRATUM where STRATUMTYPE_ID = ( ");
				sb.append(" select STRATUMTYPE_ID from STRATUMTYPE WHERE STRATUMNAME like '"+stratum+"') and OBSERVATION_ID = ");
				sb.append(" (select observation_id from OBSERVATION where PLOT_ID = " + plotName+" ) )");
				System.out.println("VBAccessDataSourcePlugin > query " + sb.toString() );
				ResultSet rs = stmt.executeQuery( sb.toString() );
				while (rs.next()) 
				{
					s = rs.getString(1);
				}
				rs.close();
				stmt.close();
			}
			catch (SQLException ex) 
			{
				this.handleSQLException( ex );
			}
			catch (java.lang.Exception ex) 
			{   
				System.out.println("Exception: " + ex );
				ex.printStackTrace();
			}
			return(s);
	 }
	 
	/**
	 * method that retuns the cummulative cover accoss all strata for a given 
	 * plant taxa in a given plot
	 */
	public String getCummulativeStrataCover( String plantName, String plotName )
	{
		return("7");
	}

	
	
	/**
	 * method for handling the sql exceptions thrown 
	 */
	private void handleSQLException(SQLException ex)
	{
		try
		{
			while (ex != null)
			{
				System.out.println ("VBAccessDataSourcePlugin > ErrorCode: " + ex.getErrorCode () );
				System.out.println ("VBAccessDataSourcePlugin > SQLState:  " + ex.getSQLState () );
				System.out.println ("VBAccessDataSourcePlugin > Message:   " + ex.getMessage () );
				ex.printStackTrace();
				ex = ex.getNextException();
			}
		}
		catch (java.lang.Exception x) 
		{   
			// All other types of exceptions
			System.out.println("Exception: " + x + "<BR>");
			x.printStackTrace();
		}
	}
	
	
	
	
	
/**
 * main method for testing --
 */
	public static void main(String[] args)
	{
		if (args.length == 1) 
		{
			String plotName = args[0];
		//	VBAccessDataSourcePlugin db = new VBAccessDataSourcePlugin("msaccess");
		//	System.out.println(" VBAccessDataSourcePlugin > getting info for: " + plotName );
		}
		else
		{
		//	VBAccessDataSourcePlugin db = new VBAccessDataSourcePlugin("msaccess");
		//	System.out.println( db.getPlotCodes().toString() );
		//	System.out.println( db.getPlotIDs().toString() );
		}
	}
	
	
}
