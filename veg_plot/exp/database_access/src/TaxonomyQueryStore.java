package databaseAccess;

/**
 *  '$RCSfile: TaxonomyQueryStore.java,v $'
 *    Authors: @authors@
 *    Release: @release@
 *
 *   '$Author: harris $'
 *     '$Date: 2002-07-30 15:58:52 $'
 * '$Revision: 1.19 $'
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


import java.lang.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.sql.*;
import databaseAccess.*;

/**
 * this class has been implemented to contain methods which are to be
 * used to query the plant taxonomy database - a database that follows 
 * the 'concept-based taxonomy' design following: Taswell, Peet, Jones
 * and others.
 * 
 * @author John Harris
 *
 */
	public class  TaxonomyQueryStore
	{
		LocalDbConnectionBroker lb = new LocalDbConnectionBroker();
		Connection c = null;
	
	
	/**
	* method that will return a database connection for use with the database
	*
	* @return conn -- an active connection
	*/
	private Connection getConnection()
	{
		Connection c = null;
		try 
 		{
			Class.forName("org.postgresql.Driver");
			//c = DriverManager.getConnection("jdbc:postgresql://vegbank.nceas.ucsb.edu/plants_dev", "datauser", "");
			c = DriverManager.getConnection("jdbc:postgresql://vegbank.nceas.ucsb.edu/tmp2", "datauser", "");
		}
		catch ( Exception e )
		{
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return(c);
	}
	
	/**
	 * this method will return near matches for a given taxon name using the 
	 * logic the Mike Lee and J Harris came up with on June 8, 2002
	 * @param name -- the plant name
	 * @return v -- a vector with the near matches
	 */
	 public Vector getNameNearMatches(String name)
	 {
		Vector returnVector = new Vector();
		try
		{
			Connection conn = this.getConnection();
			Statement query = conn.createStatement();
			ResultSet results = null;
			//create and issue the query --
			StringBuffer sqlBuf = new StringBuffer();
			sqlBuf.append("SELECT plantname from PLANTNAME where upper(plantname) like '%" + name.toUpperCase()+ "%'  order by plantname");
			
			results = query.executeQuery( sqlBuf.toString() );
			//retrieve the results
			while (results.next()) 
			{
				String plantName = results.getString(1);
				returnVector.addElement(plantName);
			}
			//remember to close the connections etc..
	 	}
		catch (Exception e) 
		{
			System.out.println("failed " +e.getMessage());
			e.printStackTrace();
		}
		System.out.println("TaxonomyQueryStore > returning near matches: " + returnVector.size()  );
		return( returnVector );
	 }
	 
	 
	
	/**
 	 * method to query the plant taxonomy database using as input a 
	 * taxon name.  The structure of the vector that is returned is meant
	 * to parallel the structure of the 'taxa.dtd'
	 *
	 * @param taxonName -- the 
	 * @param taxonNameType -- the type of taxon name input by the client, may
	 * include: scientificName, commonName, symbolCode (the usda code)
	 *
 	 */
		public Vector getPlantTaxonSummary(String taxonName, String taxonNameType )
		{
			Vector returnVector = new Vector();
			try 
			{
				
				Connection conn = this.getConnection();
				Statement query = conn.createStatement();
				ResultSet results = null;
				
				//create and issue the query --
				StringBuffer sqlBuf = new StringBuffer();
				///if (taxonNameType.trim().equals("scientificName") )
				///{
					sqlBuf.append("SELECT plantname_id, plantconcept_id, plantName, ");
					sqlBuf.append(" plantDescription, plantNameStatus, classsystem, ");
					sqlBuf.append(" plantlevel, parentName, acceptedsynonym,  ");
					sqlBuf.append(" startDate, stopDate, plantNameAlias ");
					sqlBuf.append(" from VEG_TAXA_SUMMARY where upper(plantName) like '"+taxonName.toUpperCase()+"'");
				///}
				///else if ( taxonNameType.trim().equals("commonName")  ) 
				///{
				///	sqlBuf.append("SELECT acceptedsynonym, plantnamestatus, ");
				///	sqlBuf.append(" plantName, parentName, startDate, stopDate");
				///	sqlBuf.append(" from VEG_TAXA_SUMMARY where plantName like '%"+taxonName+"%'");
				///}
				///else 
				///{
				///	System.out.println("TaxonomyQueryStore > unrecognized taxonNameType: "+taxonNameType);
				///}
				//issue the query
				results = query.executeQuery( sqlBuf.toString() );
			
				//retrieve the results
				while (results.next()) 
				{
					int plantNameId = results.getInt(1);
					int plantConceptId = results.getInt(2);
					String plantName = results.getString(3);
					String plantDescription = results.getString(4);
					String status = results.getString(5);
					String classSystem = results.getString(6);
					String plantLevel = results.getString(7);
					String parentName = results.getString(8);
					String acceptedSynonym = results.getString(9);
					String startDate = results.getString(10);
					String stopDate = results.getString(11);
					String plantNameAlias = results.getString(12);
					
					//little trick to keep from breaking the code
					String commonName = plantName;
					String concatenatedName = plantName;
					
					//bunch all of these data attributes into the return vector
					//returnVector.addElement( consolidateTaxaSummaryInstance( 
					//	acceptedSynonym, status, concatenatedName, 	commonName, 
					//	startDate, stopDate) );
					
					Hashtable h = consolidateTaxaSummaryInstance( 
						plantNameId,
						plantConceptId,
						plantName,
						plantDescription,
						status,
						classSystem,
						plantLevel,
						parentName,
						acceptedSynonym,
						startDate,
						stopDate, 
						plantNameAlias);
						
					returnVector.addElement(h);
					//System.out.println("TaxonomyQueryStore > hash: " + h.toString()   );
				}
			
				//remember to close the connections etc..
				query.close();
				conn.close();
			}
			catch (Exception e) 
			{
				System.out.println("failed " 
				+e.getMessage());
				e.printStackTrace();
			}
			System.out.println("TaxonomyQueryStore > returning results: " + returnVector.size()  );
			return( returnVector );
		}
		
		
	/**
	 * method that is overloading the method above that in that this one
	 * allows an explicit request of a name, name type (eg, scientific, common)
	 * and level in the heirarchy (eg., genus, species) 
	 * method to query the plant taxonomy database using as input a 
	 * taxon name.  The structure of the vector that is returned is meant
	 * to parallel the structure of the 'taxa.dtd'
	 *
	 * @param taxonName -- the 
	 * @param taxonNameType -- the type of taxon name input by the client, may
	 * include: scientificName, commonName, symbolCode (the usda code)
	 * @param taxonLevel -- the level in the heirarchy (eg, species, genus)
	 * 
 	 */
		public Vector getPlantTaxonSummary(String taxonName, String taxonNameType,
		String taxonLevel)
		{
			Vector returnVector = new Vector();
			try 
			{
				//connection stuff
				Connection conn = this.getConnection();
				Statement query = conn.createStatement();
				ResultSet results = null;

				//create and issue the query --
				StringBuffer sqlBuf = new StringBuffer();

				sqlBuf.append("SELECT plantname_id, plantconcept_id, plantName, ");
				sqlBuf.append(" plantDescription, plantNameStatus, classsystem, ");
				sqlBuf.append(" plantlevel, parentName, acceptedsynonym,  ");
				sqlBuf.append(" startDate, stopDate, plantNameAlias  ");
				sqlBuf.append(" from VEG_TAXA_SUMMARY where upper(plantName) like '"+taxonName.toUpperCase()+"'");
			
				System.out.println("TaxonQueryStore > taxonLevel: '"+taxonLevel+"' ");
				//add the level in the heirachy
				if ( ! taxonLevel.trim().equals("%") )
				{
					sqlBuf.append(" and upper(plantlevel) like  '"+taxonLevel.toUpperCase()+"'");
				}
				
				System.out.println("TaxonQueryStore > taxonNameType: '"+taxonNameType+"' ");
				//add the name type
				if ( ! taxonNameType.trim().equals("%") ) 
				{
					sqlBuf.append(" and ( classsystem like '"+taxonNameType+"' ");
					sqlBuf.append(" or  upper(classsystem) like '"+taxonNameType.toUpperCase()+"' ");
					sqlBuf.append(")");
				}
				
				// add the end quote
				//sqlBuf.append(")");
			
			System.out.println("TaxonomyQueryStore > query: " + sqlBuf.toString() );	
				results = query.executeQuery( sqlBuf.toString() );
			
				//retrieve the results
				while (results.next()) 
				{
					int plantNameId = results.getInt(1);
					int plantConceptId = results.getInt(2);
					String plantName = results.getString(3);
					String plantDescription = results.getString(4);
					String status = results.getString(5);
					String classSystem = results.getString(6);
					String plantLevel = results.getString(7);
					String parentName = results.getString(8);
					String acceptedSynonym = results.getString(9);
					String startDate = results.getString(10);
					String stopDate = results.getString(11);
					String plantNameAlias = results.getString(12);
					
					//little trick to keep from breaking the code
					String commonName = plantName;
					String concatenatedName = plantName;
					
					
					Hashtable h = consolidateTaxaSummaryInstance( 
						plantNameId,
						plantConceptId,
						plantName,
						plantDescription,
						status,
						classSystem,
						plantLevel,
						parentName,
						acceptedSynonym,
						startDate,
						stopDate,
						plantNameAlias);	
						
					returnVector.addElement(h);
					//System.out.println("TaxonomyQueryStore > hash: " + h.toString()   );
				}
			
				//remember to close the connections etc..
				results.close();
				query.close();
				conn.close();
			}
			catch (Exception e) 
			{
				System.out.println("failed " 
				+e.getMessage());
				e.printStackTrace();
			}
			System.out.println("TaxonomyQueryStore > returning results: " + returnVector.size()  );
			return( returnVector );
		}
		
		
		
		/**
		 * method that consolidates the summary data from the database into 
		 * a hashtable, with appropriate key names and passes back the hashtable
		 * which is in turn appended to the results vector
		 *
		 * @param acceptedSynonym -- the accepted name if the taxon is, itself,
		 *		not accepted
		 * @param plantNameAlias -- this is the plantName with the author token too.
		 */
		private Hashtable consolidateTaxaSummaryInstance( int plantNameId,
		int plantConceptId, String plantName, String plantDescription,
		String status, String	classSystem, String	plantLevel, String parentName,
		String acceptedSynonym, String startDate, String stopDate, String plantNameAlias)
		{
			Hashtable returnHash = new Hashtable();
			 try
			 {
				 //replace the null values that were retrieved from the database
					if (plantName == null )
					{
						plantName ="";
					}
					if (plantDescription == null )
					{
						 plantDescription="";
					}
					if (status == null )
					{
						 status="";
					}
					if (classSystem == null )
					{
						 classSystem="";
					}
					if (plantLevel == null )
					{
						 plantLevel="";
					}
					if (parentName == null )
					{
						 parentName="";
					}
					if (acceptedSynonym == null )
					{
						 acceptedSynonym="";
					}
					if (startDate == null )
					{
						 startDate="";
					}
					if (stopDate == null )
					{
						 stopDate="";
					}
					if (plantNameAlias == null )
					{
						 plantNameAlias="";
					}
					
					returnHash.put("plantNameId", ""+plantNameId);
					returnHash.put("plantConceptId", ""+plantConceptId);
			 		returnHash.put("plantName", plantName);
					returnHash.put("status", status);
					returnHash.put("classSystem", classSystem);
			 		returnHash.put("plantLevel", plantLevel);
					returnHash.put("parentName", parentName);
					returnHash.put("acceptedSynonym", acceptedSynonym);
					returnHash.put("startDate", startDate);
					returnHash.put("stopDate", stopDate);
					returnHash.put("plantDescription", plantDescription);
					returnHash.put("plantNameAlias", plantNameAlias);
					
			 }
			 catch(Exception e)
			 {
				 System.out.println("Exception : " + e.getMessage());
				 e.printStackTrace();
			 }
			 return( returnHash );
		 }
		
		
		/**
		 * method that consolidates the summary data from the database into 
		 * a hashtable, with appropriate key names and passes back the hashtable
		 * which is in turn appended to the results vector
		 *
		 * @param acceptedSynonym -- the accepted name if the taxon is, itself,
		 *		not accepted
		 */
		 
		 private Hashtable consolidateTaxaSummaryInstance ( String acceptedSynonym,
		 	String status, String concatenatedName, String commonName, 
			String startDate, String stopDate)
		 {
			 Hashtable returnHash = new Hashtable();
			 try
			 {
				 //replace the null values that were retrieved from the database
					if (acceptedSynonym == null )
					{
						acceptedSynonym ="";
					}
					if (status == null )
					{
						status ="";
					}
					if (concatenatedName == null )
					{
						concatenatedName ="";
					}
					if (commonName == null )
					{
						commonName ="";
					}
					if (startDate == null )
					{
						startDate ="";
					}
					if (stopDate == null )
					{
						stopDate ="";
					}
					
					if (concatenatedName != null)
			 		{
			 			returnHash.put("acceptedSynonym", acceptedSynonym);
						returnHash.put("status", status);
						returnHash.put("concatenatedName", concatenatedName);
						returnHash.put("commonName", commonName);
						returnHash.put("startDate", startDate);
						returnHash.put("stopDate", stopDate);
			 		}
			 }
			 catch(Exception e)
			 {
				 System.out.println("Exception : " + e.getMessage());
				 e.printStackTrace();
			 }
			 return( returnHash );
		 }
		 
		 /** 
		  * method that retuns the name reference for a plant name
			*/
			public Hashtable getPlantNameReference(String name)
			{
				Hashtable h = new Hashtable();
				h.put("plantName", ""+name);
				StringBuffer sqlBuf = new StringBuffer();
				try 
				{
					// connection stuff
					Connection conn = this.getConnection();
					Statement query = conn.createStatement();
					ResultSet results = null;

					// create and issue the query --
					sqlBuf.append("select  PLANTREFERENCE_ID, AUTHORS, OTHERCITATIONDETAILS, ");
					sqlBuf.append("TITLE, PUBDATE, EDITION, SERIESNAME, ISSUEIDENTIFICATION, ");
					sqlBuf.append(" PAGE, TABLECITED, ISBN, ISSN, PLANTDESCRIPTION ");
					sqlBuf.append(" from PLANTREFERENCE where PLANTREFERENCE_ID = ( ");
					sqlBuf.append(" select PLANTREFERENCE_ID from PLANTNAME where PLANTNAME like ");
					sqlBuf.append( "'" +name+"' )");
					results = query.executeQuery( sqlBuf.toString() );
					
					//retrieve the results
					while (results.next()) 
					{
						int plantReferenceId = results.getInt(1);
						String authors = results.getString(2);
						String otherCitationDetails = results.getString(3);
						String title = results.getString(4);
						String pubDate = results.getString(5);
						String edition = results.getString(6);
						String seriesName = results.getString(7);
						String issueIdentification = results.getString(8);
						String page = results.getString(9);
						String tableCited = results.getString(10);
						String isbn = results.getString(11);
						String issn = results.getString(12);
						String plantDescription= results.getString(13);
						
						h.put("plantReferenceId", ""+plantReferenceId);
						h.put("authors", ""+authors);
						h.put("otherCitationDetails", ""+otherCitationDetails);
						h.put("title", ""+title);
						h.put("pubDate", ""+pubDate);
						h.put("edition", ""+edition);
						h.put("seriesName", ""+seriesName);
						h.put("issueIdentification", ""+issueIdentification );
						h.put("page", ""+page);
						h.put("tableCited", ""+tableCited );
						h.put("isbn", ""+isbn);
						h.put("issn", ""+issn);
						
					}
					//remember to close the connections etc..
				}
				catch (Exception e) 
				{
					System.out.println("failed " + e.getMessage() );
					System.out.println("sql: " + sqlBuf.toString() );
					e.printStackTrace();
				}
				return( h );
			}
		 
		 
		 
		/**
		 * Main method for testing
		 */  
		 public static void main(String[] args)
		 {
			 //test the get plant name reference method
			 TaxonomyQueryStore qs = new TaxonomyQueryStore();
			 Hashtable h = qs.getPlantNameReference("Big Bush");
			 System.out.println("TaxonomyQueryStore > plantname reference" + h.toString()  );
		 }
		
	}
