package org.vegbank.servlet.request;

/*
 *  '$RCSfile: ViewFile.java,v $'
 *
 *	'$Author: farrell $'
 *  '$Date: 2003-04-16 00:12:48 $'
 *  '$Revision: 1.2 $'
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ViewFile extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res) 
  	throws ServletException, IOException {
    // Use a ServletOutputStream since we may pass binary information
    ServletOutputStream out = res.getOutputStream();

    // Get the file to view
    String file = req.getPathTranslated();
	out.println(file);
    // No file, nothing to view
    if (file == null) {
      out.println("No file to view");
      out.println(file);
      return;
    }

    // Get and set the type of the file
    String contentType = getServletContext().getMimeType(file);
    res.setContentType(contentType);
    out.println("contentType: "+contentType );

    // Return the file
    try {
      ViewFile.returnFile(file, out);
    }
    catch (FileNotFoundException e) { 
      out.println("File not found");
    }
    catch (IOException e) { 
      out.println("Problem sending file: " + e.getMessage());
    }
  }
  
  //send the contents of a file to the outputStream
  public static void returnFile(String fileName, OutputStream out)
  throws FileNotFoundException, IOException {
	  //A FileInputStream is for Bytes
	  FileInputStream fis=null;
	  
	  
	  try {
	  
	  fis = new FileInputStream(fileName);
	  byte[] buf = new byte[4 * 1024]; //4K buffer
	  int bytesRead;
	  while ((bytesRead=fis.read(buf)) != -1) {
		  out.write(buf, 0, bytesRead);
	  }
	  }
	  finally {
		  if (fis !=null) fis.close();
	  }
  }
	  
}
