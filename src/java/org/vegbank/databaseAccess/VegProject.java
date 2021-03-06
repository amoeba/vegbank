package org.vegbank.databaseAccess;

/**
 * '$RCSfile: VegProject.java,v $'
 *
 * Purpose: 
 *
 * '$Author: farrell $'
 * '$Date: 2003-08-21 21:16:44 $'
 * '$Revision: 1.3 $'
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Vector;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import org.vegbank.xmlresource.*;


/**
 * class to store a vegetation plot as a DOM
 * and to perform specific functions to the DOM
 * structure like add plot information and query
 * the nodes
 *
 */
public class VegProject
{

  /**
   * root node of the in-memory DOM structure
   */
  private Node root;

  /**
   * Document node of the in-memory DOM structure
   */
  private Document doc;

  /**
   * XML file name in string form
   */
  private String fileName;

  /**
   * Print writer (output)
   */
  private PrintWriter out;
	
	/**
	 * access to the parser utility
	 */
	private XMLparse parse = new XMLparse();
	
	//the number of plots in this project file
	int numberOfPlots = 0;
	
  /**
   * String passed to the creator is the XML config file name
   * 
   * @param filename name of XML file
   */
  public VegProject(String filename) throws FileNotFoundException
  {
    this.fileName = filename;

    DOMParser parser = new DOMParser();
    File plotFile = new File(filename);
    InputSource in;
    FileInputStream fs;
    fs = new FileInputStream(filename);
    in = new InputSource(fs);

    try
    {
      parser.parse(in);
      fs.close();
    } 
		catch(Exception e1) 
		{
			System.out.println("VegProject > Exception: "+e1.getMessage() );
    }
    doc = parser.getDocument();
    root = doc.getDocumentElement();
	}
	
	
	public int getNumberOfPlots()
	{
		numberOfPlots = parse.getNumberOfNodes(doc, "plot");
		//System.out.println("number of plots: "+numberOfPlots);
		return(numberOfPlots);
	}
	
	//method to return a vector containing the list of plotNames
	public Vector getPlotNames()
	{
		Vector plotNames = new Vector();
		plotNames = parse.get(doc, "authorPlotCode");
		return(plotNames);
	}
	
	//method that returns the project name of this project
	public String getProjectName()
	{
		String returnString = parse.getNodeValue(doc, "projectName");
		return(returnString);
	}
	
	//method that returns the description of the project
	public String getProjectDescription()
	{
		String returnString = parse.getNodeValue(doc, "projectDescription");
		return(returnString);
	}
	
	
	// method that takes as input a node containg a plot and writes it to the file
	// that is also specified at input basically just a wrapper for the 'saveDom'
	// method in the XMLparse class
	public void savePlot(Node plotNode, String fileName)
	{
		parse.saveDOM(plotNode, fileName);
	}
	
	
	/**
	 * method to save a plot to a file based on a plotName
	 * @param plotName -- the name of the plot {authorPlotCode}
	 * @param filename -- the filename that the plot should be saved to
	 */
	 public void savePlot(String authorPlotCode, String fileName)
	 {
		 Node plot = getPlot(authorPlotCode);
		 savePlot(plot, fileName);
	 }
	
	//method to return a single plot from a project xml file which may contain
	// several plots
	public Node getPlot(String authorPlotCode)
	{
		//index number corresponding to the plot in the vector never should be less
		// than 0
		int index = -999;  
		Node returnNode = null;
		//get all the plot names and then get the one that matches the input
		Vector plotNames = getPlotNames();
		for (int i =0; i < plotNames.size(); i++)
		{
			if ( plotNames.elementAt(i).toString().trim().equals(authorPlotCode) )
			{
				//assign the index
				index = i;
			}
		}
		//if the index is unchanged, still -999, then print an error
		if (index == -999)
		{
			System.out.println("plot with authorPlotCode: "+authorPlotCode+"not found ERROR");
		}
		else
		{
			returnNode = parse.get(doc, "plot", index);
		}
		return(returnNode);
	}
	
}
