/*
 * '$RCSfile: DBConnectionPool.java,v $' 
 *
 * Purpose: 
 * A class represent a DBConnection pool. Another user can use the 
 * object to initial a connection pool, get db connection or return it. 
 * Copyright: 2000 Regents of the  University of California and the 
 * National Center for Ecological Analysis and
 * Synthesis Authors: Jing Tao Release: @release@
 * 
 * '$Author: anderson $' 
 * '$Date: 2005-05-02 11:11:06 $'
 * '$Revision: 1.10 $'
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.vegbank.common.utility;

import java.sql.SQLException;
import java.util.Vector;
import org.apache.commons.logging.*;
/**
 * A class represent a DBConnection pool. Another user can use the object to
 * initial a connection pool, get db connection or return it. This a singleton
 * class, this means only one instance of this class could be in the program at
 * one time.
 */
public class DBConnectionPool implements Runnable
{

	/**
	 * Commons Logging instance.
	 */
	protected static Log log = LogFactory.getLog(DBConnectionPool.class);

	//static attributes
	private static DBConnectionPool instance;
	private static Vector connectionPool;
	private static Thread runner;

	//maximum connection number in the connection pool
	final static int MAXIMUMCONNECTIONNUMBER = Integer.parseInt(DatabaseUtility
			.getOption("maximumConnections"));

	//inintila connection number int the connection pool
	final static int INITIALCONNECTIONNUMBER = Integer.parseInt(DatabaseUtility
			.getOption("initialConnections"));

	//the number to increase connection pool size
	final static int INCREASECONNECTIONNUMBER = Integer.parseInt(DatabaseUtility
			.getOption("incrementConnections"));

	//maximum age for a connection (in milli seconds)
	final static long MAXIMUMAGE = Long.parseLong(DatabaseUtility
			.getOption("maximumConnectionAge"));

	//maximum connection time for a connection ( in milli second)
	final static long MAXIMUMCONNECTIONTIME = Long.parseLong(DatabaseUtility
			.getOption("maximumConnectionTime"));

	//maximum number for using a connection.
	final static int MAXIMUMUSAGENUMBER = Integer.parseInt(DatabaseUtility
			.getOption("maximumUsageNumber"));

	//the parameter if run dbconncestionrecyclethread or not
	final static String DBCONNECTIONRECYCLETHREAD = DatabaseUtility
			.getOption("runDBConnectionRecycleThread");

	//the cycle time of connection recycle action
	final static long CYCLETIMEOFDBCONNECTION = Long.parseLong(DatabaseUtility
			.getOption("cycleTimeOfDBConnection"));

	//Number of Attempts to find a good connection when checking out
	final static int LIMIT = 5;

	final public static int FREE = 0; //status of a connection
	final public static int BUSY = 1; //status of a connection

	/**
	 * Returns the single instance, creating one if it's the first time this
	 * method is called.
	 */
	public static synchronized DBConnectionPool getInstance() throws SQLException {
		if (instance == null) {
			instance = new DBConnectionPool();
		}
		return instance;
	} //getInstance

	/**
	 * This is a private constructor since it is singleton
	 */
	private DBConnectionPool() throws SQLException {
		connectionPool = new Vector();
		initialDBConnectionPool();
		//running the thread to recycle DBConnection
		if (DBCONNECTIONRECYCLETHREAD.equals("on")) {
			runner = new Thread(this);
			runner.start();
		}
		if ( log.isInfoEnabled() ) {
			log.info("DBConnectionPool: MaximumConnectionNumber: "+ MAXIMUMCONNECTIONNUMBER);
			log.info("DBConnectionPool: Intial connection number: " + INITIALCONNECTIONNUMBER);
			log.info("DBConnectionPool: Increated connection Number: " + INCREASECONNECTIONNUMBER);
			log.info("DBConnectionPool: Maximum connection age: " + MAXIMUMAGE);
			log.info("DBConnectionPool: Maximum connection time: " + MAXIMUMCONNECTIONTIME);
			log.info("DBConnectionPool: Maximum usage count: " + MAXIMUMUSAGENUMBER);
			log.info("DBConnectionPool: Running recycle thread or not: " + DBCONNECTIONRECYCLETHREAD);
			log.info("DBConnectionPool: Cycle time of recycle: "+ CYCLETIMEOFDBCONNECTION);
		}
	} //DBConnection

	/**
	 * Method to get the size of DBConnectionPool
	 */
	public int getSizeOfDBConnectionPool()
	{
		return connectionPool.size();
	}
	
	protected void finalize()
	{
	}

	/**
	 * Method to initial a pool of DBConnection objects
	 */
	private void initialDBConnectionPool() throws SQLException
	{
		DBConnection dbConn = null;
		for (int i = 0; i < INITIALCONNECTIONNUMBER; i++)
		{
			//create a new object of DBConnection
			//this DBConnection object has a new connection in it
			//it automatically generate the createtime and tag
			dbConn = new DBConnection();
			connectionPool.add(dbConn);
		}
	} //initialDBConnectionPool

	/**
	 * Method to get a DBConnection in connection pool <br/>
	 * <ol>
	 * <li>try to get a DBConnection from DBConnection pool</li>
	 * <li>if 1) failed, then check the size of pool. If the size reach the
	 * maximum number of connection, throw a exception: couldn't get one</li>
	 * <li>If the size is less than the maximum number of connectio, create some
	 * new connections and recursive get one</li>
	 * </ol>
	 * 
	 * @param methodName,
	 *          the name of method which will check connection out
	 */
	public synchronized DBConnection getDBConnection(String methodName)
			throws SQLException
	{
		DBConnection db = null;
		int random = 0; //random number
		int index = 0; //index
		int size =  connectionPool.size(); //size of connection pool
		
		if ( log.isInfoEnabled() ) {
			log.info("Checking out connection.  Size: " + size);
		}

		//try every DBConnection in the pool
		//every DBConnection will be tried LIMIT times
		for (int j = 0; j < LIMIT; j++)
		{
			//create a random number as the started index for connection pool
			//so that the connection of index of 0 won't get bombed
			random = (new Double(Math.random() * 100)).intValue();
            //log.debug("CHECK CONN: j=" + j + ", random="+random);

			for (int i = 0; i < size; i++)
			{
                index = (i + random) % size;

                //log.debug(":::::::::::: i=" + i+", index=" + index);
				db = (DBConnection) connectionPool.elementAt(index);

				//check if the connection is free
                int dbStatus = db.getStatus();
				if (dbStatus == FREE)
				{
                    if ( log.isDebugEnabled() ) {
                        //log.debug("connection[" + index + "]: " + ((dbStatus == FREE) ? "FREE": "BUSY"));
                    }
				
					// if this connection is good, return it
					if (validateDBConnection(db)) {

						//set this DBConnection status
						db.setStatus(BUSY);
						//increase checkout serial number
						db.increaseCheckOutSerialNumber(1);
						//increase one usageCount
						db.increaseUsageCount(1);
						//set method name to DBConnection
						db.setCheckOutMethodName(methodName);
						db.setAutoCommit(true);

						if ( log.isDebugEnabled() ) {
							//log.debug("checked out connection: "+ db.getTag());
						}

						//set check out time
						db.setCheckOutTime(System.currentTimeMillis());
						
                        //log.debug("returning VALID connection");
                        log.debug("------------ OUT [" + index + "]");
						return db;

					} else {
                        log.debug("************ INVALID CONNECTION...repairing");
					    //the DBConnection has some problem
						//close this DBConnection
						db.close();
						//remove it from connection pool
						connectionPool.remove(index);
						//insert a new DBConnection to same palace
						db = new DBConnection();
						connectionPool.insertElementAt(db, index);

                        // PMA: 29apr2005: returning db
                        log.debug("------------ (repaired) OUT [" + index + "]");
                        return db;

					} 
				} else {
                    log.debug("------------ BUSY [" + index + "]");
                }
			} //for
		} //for

		//if couldn't get a connection, we should increase DBConnection pool
		//if the connection pool size is less than maximum connection number
		if (size < MAXIMUMCONNECTIONNUMBER)
		{
			if ((size + INCREASECONNECTIONNUMBER) <= MAXIMUMCONNECTIONNUMBER)
			{
				//if we can create INCREASECONNECTIONNUMBER of new DBConnection
				//add to connection pool
				for (int i = 0; i < INCREASECONNECTIONNUMBER; i++) {
					DBConnection dbConn = new DBConnection();
					connectionPool.add(dbConn);
				} 

			} else {
				//There is not enough room to increase INCREASECONNECTIONNUMBER
				//we create new DBConnection to Maximum connection number
				for (int i = size + 1; i <= MAXIMUMCONNECTIONNUMBER; i++) {
					DBConnection dbConn = new DBConnection();
					connectionPool.add(dbConn);
				} 
			} 

            log.debug("Added connections.  Current size: " + connectionPool.size());

		} else {
			log.error("ERROR: DB pool is maxed out at " + MAXIMUMCONNECTIONNUMBER);
			return null;
		} 

		//recursive to get new connection
        log.debug("getDBConnection(): RECURSE");
		return getDBConnection(methodName);
	} //getDBConnection

	/**
	 * Method to check if a db connection works fine or not Check points include: 1.
	 * check the usageCount if it is too many 2. check the dbconne age if it is
	 * too old 3. check the connection time if it is too long 4. run simple sql
	 * query
	 * 
	 * @param dbConn,
	 *          the DBConnection object need to check
	 */
	private static boolean validateDBConnection(DBConnection dbConn)
	{
		//Check if the DBConnection usageCount if it is too many
		if (dbConn.getUsageCount() >= MAXIMUMUSAGENUMBER)
		{
			log.debug("DBConnectionPool: Connection usageCount is too high: "
					+ dbConn.getUsageCount());
			return false;
		}
		//Check if the DBConnection has too much connection time
		if (dbConn.getConnectionTime() >= MAXIMUMCONNECTIONTIME)
		{
			log.debug("DBConnectionPool: Connection surpassed max time in database: "
							+ dbConn.getConnectionTime());
			return false;
		}
		//Check if the DBConnection is too old
		if (dbConn.getAge() >= MAXIMUMAGE)
		{
			log.debug("DBConnectionPool: Connection is too old: "+ dbConn.getAge());
			return false;
		}
		
		if (dbConn.getTag() == null) {
			//log.warn("DBConnectionPool: Connection tag is null");
			return false;
		}

		//Confirm connection is open
		try
		{
			if ( dbConn.isClosed() )
			{
				log.debug("validateDBConnection(): Connection is closed");
				return false;
			}
			
			long startTime = System.currentTimeMillis();
			long stopTime = System.currentTimeMillis();
			
			// PMA - this gets increased in getDBConnection
			//dbConn.increaseUsageCount(1);
			
			//increase connection time
			dbConn.setConnectionTime(stopTime - startTime);

			//log.debug("DBConnectionPool.validateDBConnection:  conn. appears valid");
		}
		catch (Exception e)
		{
			log.error("DBConnectionPool: Error in validateDBConnection: "+ e.toString());
			return false;
		}
		return true;
	} //validateDBConnection()

	/**
	 * Method to return a connection to DBConnection pool.
	 * 
	 * @param conn,
	 *          the Connection object need to check in
	 */
	private static synchronized void returnDBConnection(DBConnection conn,
			int serialNumber)
	{
		int index = -1;
		DBConnection dbConn = null;
		index = getIndexOfPoolForConnection(conn);
		if (index == -1) {
			log.debug("Couldn't find a DBConnection in the pool"
				+ " which has same tag of the returned DBConnetion object");
			return;

		} else {
			//check the paramter - serialNumber which will be keep in calling method
			//if it is as same as the object's checkoutserial number.
			//if it is same return it. If it is not same, maybe the connection
			// already
			// was returned ealier.
			if (conn.getCheckOutSerialNumber() == serialNumber) {
                if ( log.isDebugEnabled() ) {
                    log.debug("------------ IN [" + index + "]");
                }

				dbConn = (DBConnection) connectionPool.elementAt(index);
				//set status to free
				dbConn.setStatus(FREE);
				//count connection time
				dbConn.setConnectionTime(System.currentTimeMillis()
						- dbConn.getCheckOutTime());
				//set check out time to 0
				dbConn.setCheckOutTime(0);  
				//log.debug("** CHECK-IN SUCCESS ** " + dbConn.getTag());
			} else {
				log.error("!!CHECK-IN FAILURE!!  serial number mismatch");
			} 
		} 
	} //returnConnection

	/**
	 * Given a returned DBConnection, try to find the index of DBConnection
	 * object in dbConnection pool by comparing DBConnection' tag and
	 * conn.toString. If couldn't find , -1 will be returned.
	 * 
	 * @param conn,
	 *          the connection need to be found
	 */
	private static synchronized int getIndexOfPoolForConnection(DBConnection conn)
	{
		int index = -1;
		String info = null;
		//if conn is null return -1 too
		if (conn == null) {
			return -1;
		}
		//get tag of this returned DBConnection
		info = conn.getTag();
		//if the tag is null or empty, -1 will be returned
		if (info == null || info.equals("")) {
			return index;
		}
		//compare this info to the tag of every DBConnection in the pool
		for (int i = 0; i < connectionPool.size(); i++) {
			DBConnection dbConn = (DBConnection) connectionPool.elementAt(i);
			if (info.equals(dbConn.getTag())) {
				index = i;
				break;
			} 
		} 
		return index;
	} //getIndexOfPoolForConnection

	/**
	 * Method to shut down all connections
	 */
	public static void release()
	{
		//shut down the backgroud recycle thread
		if (DBCONNECTIONRECYCLETHREAD.equals("on")) {
			runner.interrupt();
		}

		//close every dbconnection in the pool
		synchronized (connectionPool) {
			for (int i = 0; i < connectionPool.size(); i++) {
				try {
					DBConnection dbConn = (DBConnection) connectionPool.elementAt(i);
					dbConn.close();
				} catch (SQLException e) {
					log.error("DBConnectionPool: Error in releasing connection: " + e);
				} 
			} 
		} 
	} //release()

	/**
	 * periodically to recycle the connection
	 */
	public void run() {
		DBConnection dbConn = null;
		//keep the thread running
		while (true) {
		    log.info("(((((((( DBConnectionPool: Maintenance thread activating ))))))))");
			//check every dbconnection in the pool
			synchronized (connectionPool) {
				int numberCheckedOut = 0;
				for (int i = 0; i < connectionPool.size(); i++) {
					dbConn = (DBConnection) connectionPool.elementAt(i);
					//if a DBConnection conncectioning time for one check out is greater
					//than 30000 milliseconds print it out
					if (dbConn.getStatus() == BUSY) {
						numberCheckedOut++;
						if ((System.currentTimeMillis() - dbConn.getCheckOutTime()) >= 30000) {
							log.info("DBConnectionPool: This DBConnection is checked out for: "
											+ (System.currentTimeMillis() - dbConn.getCheckOutTime())
											/ 1000
											+ " secs "
											+ dbConn.getTag()
											+ " method: "
											+ dbConn.getCheckOutMethodName());
						}
					}

					// validate the free connection in the pool
					if (dbConn.getStatus() == FREE) {
						try {
							//try to print out the warning message for every connection
							if (dbConn.getWarningMessage() != null) {
								log.warn("DBConnectionPool: Warning for connection "
										+ dbConn.getTag() + " : " + dbConn.getWarningMessage());
							}

							//check if it is valiate, if not create new one and replace old
							// one
							if (!validateDBConnection(dbConn)) {
								log.debug("DBConnectionPool: Recyle it: "+ dbConn.getTag());
								//close this DBConnection
								dbConn.close();
								//remove it form connection pool
								connectionPool.remove(i);
								//insert a new DBConnection to same palace
								dbConn = new DBConnection();
								connectionPool.insertElementAt(dbConn, i);
							} 
						} catch (SQLException e) {
							log.error("DBConnectionPool: Error in DBConnectionPool.run: ", e);
						} 
					}
				}
				log.debug(numberCheckedOut + " out of " + connectionPool.size() + " checked out");
			} //synchronize
			shrinkConnectionPoolSize();
			//Thread sleep

			try {
				Thread.sleep(CYCLETIMEOFDBCONNECTION);
			} catch (InterruptedException e) {
				log.info("DBConnectionPool: DBConnectionPool.run was interupped: " + e.getMessage());
				return;
			}	
			log.debug("DBConnectionPool: Run Maintenance loop again");
		} //while
	} //run

	/**
	 * Method to get the number of free DBConnection in DBConnection pool
	 */
	public static synchronized int getFreeDBConnectionNumber()
	{
		int numberOfFreeDBConnetion = 0; //return number
		DBConnection db = null; //single DBconnection
		int poolSize = 0; //size of connection pool
		//get the size of DBConnection pool
		poolSize = connectionPool.size();
		//Check every DBConnection in the pool
		for (int i = 0; i < poolSize; i++)
		{
			db = (DBConnection) connectionPool.elementAt(i);
			//check the status of db. If it is free, count it
			if (db.getStatus() == FREE)
			{
				numberOfFreeDBConnetion++;
			} //if
		} //for
		//return the count result
		return numberOfFreeDBConnetion;
	} //getFreeDBConnectionNumber

	/**
	 * Method to print out the method name which have busy DBconnection
	 */
	public void printMethodNameHavingBusyDBConnection()
	{
		DBConnection db = null; //single DBconnection
		int poolSize = 0; //size of connection pool
		//get the size of DBConnection pool
		poolSize = connectionPool.size();
		//Check every DBConnection in the pool
		for (int i = 0; i < poolSize; i++)
		{
			db = (DBConnection) connectionPool.elementAt(i);
			//check the status of db. If it is free, count it
			if (db.getStatus() == BUSY)
			{
				log.debug("DBConnectionPool: This method has a busy DBConnection: "
								+ db.getCheckOutMethodName());
				log.debug("DBConnectionPool: The busy DBConnection tag is: "
						+ db.getTag());
			} //if
		} //for
	} //printMethodNameHavingBusyDBConnection

	/**
	 * Method to decrease dbconnection pool size when all dbconnections are idle
	 * If all connections are free and connection pool size greater than initial
	 * value, shrink connection pool size to intital value
	 */
	public static synchronized boolean shrinkConnectionPoolSize()
	{
		int connectionPoolSize = 0;
		//store the number of dbconnection pool size
		int freeConnectionSize = 0;
		//store the number of free dbconnection in pool
		int difference = 0;
		// store the difference number between connection size
		// and free connection
		boolean hasException = false; //to check if has a exception happend
		boolean result = false; //result
		DBConnection conn = null; // the dbconnection
		connectionPoolSize = connectionPool.size();
		freeConnectionSize = getFreeDBConnectionNumber();
		log.info("DBConnectionPool: Connection pool size: "+ connectionPoolSize);
		log.info("DBConnectionPool: Free Connection number: "
				+ freeConnectionSize);
		difference = connectionPoolSize - freeConnectionSize;
		//If all connections are free and connection pool size greater than
		//initial value, shrink connection pool size to intital value
		if (difference == 0 && connectionPoolSize > INITIALCONNECTIONNUMBER)
		{
			// Start killing connections until correct size reached
			for (int i = connectionPoolSize - 1; i >= INITIALCONNECTIONNUMBER; i--)
			{
				//get the dbconnection from pool
				conn = (DBConnection) connectionPool.elementAt(i);
				try
				{
					//close conn
					conn.close();
				} //try
				catch (SQLException e)
				{
					// set hadException ture
					hasException = true;
					log.error("DBConnectionPool: Couldn't close a DBConnection in "
							+ "DBConnectionPool.shrinkDBConnectionPoolSize: "
							+ e.getMessage(), e);
				} //catch
				//remove it from pool
				connectionPool.remove(i);
				// becuase enter the loop, set result true
				result = true;
			} //for
		} //if
		//if hasException is true ( there at least once exception happend)
		// the result should be false
		if (hasException)
		{
			result = false;
		} //if
		// return result
		return result;
	} //shrinkDBConnectionPoolSize


	/**
	 * @param conn
	 */
	public static void returnDBConnection(DBConnection conn)
	{ 
        if (conn != null) {
		    DBConnectionPool.returnDBConnection(conn, conn.getCheckOutSerialNumber());
        } else {
            log.error("MAJOR ERROR while returning DBConnection:\nconnection was null\n");
        }
	}
} 
