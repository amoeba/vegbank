#!/bin/sh

# This script will delete a user from the vegbank 
# user profile database 
#	 '$Author: farrell $'
#  '$Date: 2003-02-03 18:41:07 $'
#  '$Revision: 1.2 $'


if [ "$#" -ne 2 ]
then
	echo " USAGE: dropuser.sh <email> <host>  "
	echo "  email: the email address of the user"
	echo "  host: the host machine like beta.nceas.ucsb.edu"
	echo "  "
	exit 1
fi

ACTION='dropuser'
EMAIL=$1
HOST=$2


DATATRANSLATOR=../lib/datatranslator.jar
XMLRESOURCE=../lib/xmlresource.jar
PG_JDBC=../lib/jdbc7.0-1.2.jar
XALAN=../lib/xalan_1_2_2.jar
XERCES=../lib/xerces_1_4.jar
ACCESSRESOURCE=../lib/database_access.jar
UTILS=../lib/utilities.jar
PLANTTAXON=../lib/planttaxonomy.jar
CLASSPATH=$XMLRESOURCE:$DATATRANSLATOR:$PG_JDBC:$XALAN:$XERCES:$ACCESSRESOURCE:$UTILS:$PLANTTAXON

java  -cp $CLASSPATH databaseAccess.Utility $ACTION $EMAIL $HOST
 
