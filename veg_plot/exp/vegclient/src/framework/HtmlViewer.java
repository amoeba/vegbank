/**
 *
 *
 * this class is used to display query results from the vegetation
 * databases
 *
 *
 *  Authors: @author@
 *  Release: @release@
 *	
 *  '$Author: harris $'
 *  '$Date: 2001-10-11 12:37:23 $'
 * 	'$Revision: 1.2 $'
 *
 */
package vegclient.framework;
 
import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;


import vegclient.framework.*;
import vegclient.datarequestor.*;

public class HtmlViewer extends javax.swing.JFrame 
{
	
	ResourceBundle rb = ResourceBundle.getBundle("vegclient");
  private javax.swing.JMenuBar menuBar;
  private javax.swing.JMenu fileMenu;
  private javax.swing.JMenuItem openMenuItem;
  private javax.swing.JMenuItem saveMenuItem;
  private javax.swing.JMenuItem saveAsMenuItem;
  private javax.swing.JMenuItem exitMenuItem;
  private javax.swing.JMenu editMenu;
  private javax.swing.JMenuItem cutMenuItem;
  private javax.swing.JMenuItem copyMenuItem;
  private javax.swing.JMenuItem pasteMenuItem;
  private javax.swing.JMenuItem deleteMenuItem;
  private javax.swing.JMenu helpMenu;
  private javax.swing.JMenuItem contentsMenuItem;
  private javax.swing.JMenuItem aboutMenuItem;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JEditorPane jEditorPane1;
  private javax.swing.JButton jButton1;

		
    /** Creates new form HtmlViewer */
    public HtmlViewer() 
		{
        initComponents ();
        pack ();
        setSize(720, 720);
    }
		

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentsMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jButton1 = new javax.swing.JButton();
        
        fileMenu.setText("File");
          
          openMenuItem.setText("Open");
            fileMenu.add(openMenuItem);
            
          saveMenuItem.setText("Save");
            fileMenu.add(saveMenuItem);
            
          saveAsMenuItem.setText("Save As ...");
            fileMenu.add(saveAsMenuItem);
            
          exitMenuItem.setText("Exit");
            exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    exitMenuItemActionPerformed(evt);
                }
            }
            );
            fileMenu.add(exitMenuItem);
            menuBar.add(fileMenu);
          
        editMenu.setText("Edit");
          
          cutMenuItem.setText("Cut");
            editMenu.add(cutMenuItem);
            
          copyMenuItem.setText("Copy");
            editMenu.add(copyMenuItem);
            
          pasteMenuItem.setText("Paste");
            editMenu.add(pasteMenuItem);
            
          deleteMenuItem.setText("Delete");
            editMenu.add(deleteMenuItem);
            menuBar.add(editMenu);
          
        helpMenu.setText("Help");
          
          contentsMenuItem.setText("Contents");
            helpMenu.add(contentsMenuItem);
            
          aboutMenuItem.setText("About");
            helpMenu.add(aboutMenuItem);
            menuBar.add(helpMenu);
          getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), 1));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        }
        );
        
        
        jEditorPane1.setText("test");
          jScrollPane1.setViewportView(jEditorPane1);
          
          
        getContentPane().add(jScrollPane1);
        
        
        jButton1.setText("view data");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        }
        );
        
        getContentPane().add(jButton1);
        
        setJMenuBar(menuBar);
        
    }//GEN-END:initComponents

    /**
     *  Method to allow the user access to the main veg database site when the 
     *  button is pressed
     *
     */
  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// Add your handling code here:
      try 
      {
        System.out.println("connecting");
        jEditorPane1.setContentType("text/html"); 
        DataRequestClient drc = new DataRequestClient();
        jEditorPane1.setText(drc.requestURL(
        "http://dev.nceas.ucsb.edu/harris/servlet/viewData?resultType=summary&summaryViewType=vegPlot"));
      }
      catch (Exception e) 
      {
          System.out.println(e);
      }
  }//GEN-LAST:event_jButton1ActionPerformed

    private void exitMenuItemActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit (0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        //System.exit (0);
				this.dispose();
    }//GEN-LAST:event_exitForm

    /**
    * Method to set the exet in the pane from an input file
    * which can be called by other interfaces
    */
	public void showData () 
	{
  	try
  	{
    	jEditorPane1.setContentType("text/html");
      jEditorPane1.setText("reading data");    
      //the current location of the veg plot servlet
      jEditorPane1.setPage("http://dev.nceas.ucsb.edu/harris/servlet/viewData?resultType=summary&summaryViewType=vegPlot");
    }
     catch (Exception e) 
    {
     	System.err.println(e);
    }
  }
    
    /**
    * Method to set the texet in the pane from an input file
    * which can be called by other interfaces
    */
    public void showData(String inString) 
		{
      try 
      {     
       	jEditorPane1.setContentType("text/plain");
        jEditorPane1.setText(inString); 
        //jEditorPane1.setPage("http://www.cnn.com");
       }
			catch (Exception e) 
			{
				System.err.println(e);
			}
    }
    
		
	 /**
    * Method to add the input string to the text in the pane
    */
    public void addData(String inString) 
		{
      try 
      { 
				//set the content type to plain text
       	jEditorPane1.setContentType("text/plain");
				StringBuffer sb = new StringBuffer();
				//get what is currently in the pane
//				sb.append( jEditorPane1.getText() );
				if ( jEditorPane1.getText().trim() != null )
				{
					System.out.println( "'"+jEditorPane1.getText()+"'" );
				}
				//append the new stuff
				sb.append( "\n"+inString);
				//replace the selection
        jEditorPane1.replaceSelection(sb.toString() ); 
       }
			catch (Exception e) 
			{
				System.err.println(e);
			}
    }
    
		
		
		
	/**
	 * Method to set the exet in the pane from an input file
	 * which can be called by other interfaces
	 */
	public void showData(String inXML, String styleSheet, String contentType) 
	{
		try 
		{ 
            
			//this is where the transformed xml document is to be stored
			StringBuffer results = new StringBuffer();
            
			jEditorPane1.setContentType(contentType);
            
			//now transform the xml with the style sheet
			transformXML m = new transformXML();
			m.getTransformed(inXML, styleSheet);
    
    
			//the stringwriter containg all the transformed data
			StringWriter transformedData=m.outTransformedData;  

			//pass to the utility class to convert the StringWriter to an array
			utility u =new utility();
			u.convertStringWriter(transformedData);

			String transformedString[]=u.outString; 
			int transformedStringNum=u.outStringNum; 
    
			for (int i=0; i<transformedStringNum; i++) 
			{
				//System.out.println(transformedString[i]);	
				results.append(transformedString[i]+"\n");
			}
            
			jEditorPane1.setText(results.toString()); 

		} 
		catch (Exception e) 
		{
			System.err.println(e);
		}
	}
    
    
    
    /**
    * @param args the command line arguments
    */
    public static void main (String args[]) 
		{
			// allow the user to specify an xml document and a xsl stylesheet
			if (args.length == 2) 
			{
				System.out.println("Usage: java HtmlViewer [xml] [xslt]   \n"
				+" ");
				
				String xml = args[0];
				String xsl = args[1];
				
				HtmlViewer hv = new  HtmlViewer(); 
      	hv.show();
      	hv.showData(xml, xsl, "text/html");
			}
			
			else
			{
			
    		HtmlViewer hv = new  HtmlViewer(); 
      	hv.show();
      	hv.showData();
    	}
		}

   

}
