/**
 *  '$RCSfile: SplashFrame.java,v $'
 *  Copyright: 2000 Regents of the University of California and the
 *              National Center for Ecological Analysis and Synthesis.
 *							'An Information Infrastructure for Vegetation Science'
 *    Authors: @author@
 *    Release: @release@
 *
 *   '$Author: harris $'
 *     '$Date: 2001-10-10 18:12:42 $'
 * '$Revision: 1.1 $'
 *
 */

package vegclient.framework;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


import vegclient.framework.*;

/**
 * GUI window that provides information about Morpho, its contributors,
 * and acknowlegements.
 */
public class SplashFrame extends javax.swing.JFrame
{
  // Used by addNotify
  private boolean frameSizeAdjusted = false;

  // the version number
  //private static String version = "Version 0.0.0 Alpha 3";
  private static String[] coders = 
	{ 
		"John Harris",
    "Robert Peet",
    "Michael Jennings",
    "Marilyn Walker",
    "Dennis Grossman" 
	};
  private static String[] orgs = 
	{ 
    "National Center for Ecological Analysis and Synthesis",
    "University of North Carolina",
    "United States Geological Survey",
    "University of Alaska",
		"Association for Biodiversity"
	};

  private static String credit = 
                  "This material is based upon work supported\n" +
                  "by the National Science Foundation under Grant\n" +
                  " No. DBI9905838. Also\n" +
                  "supported by the National Center for Ecological\n" +
                  "Analysis and Synthesis, a Center funded by\n" +
                  "NSF (Grant No. DEB-94-21535), the University of\n" +
                  "California - Santa Barbara, and the State of\n" +
                  "California. Any opinions, findings and conclusions\n" +
                  "or recommendations expressed in this material are\n" +
                  "those of the author(s) and do not necessarily\n" +
                  "reflect the views of the National Science Foundation\n" +
                  "(NSF).\n\nThis software is intended for use in the \n"
									+"management of vegetation data in the support of the \n"
									+"National Vegetation Classification";
 
  javax.swing.JLabel loadingLabel = new javax.swing.JLabel();
  javax.swing.JLabel titleLabel = new javax.swing.JLabel();
  javax.swing.JLabel subTitleLabel = new javax.swing.JLabel();
  javax.swing.JLabel versionLabel = new javax.swing.JLabel();
  javax.swing.JButton closeButton = new javax.swing.JButton();
  javax.swing.JButton fundingButton = new javax.swing.JButton();
  //}}

  javax.swing.ImageIcon BFlyIcon = new javax.swing.ImageIcon();
  //{{DECLARE_MENUS
  //}}


	
	
  /**
   * Construct the window
   */
  public SplashFrame()
  {
    //{{INIT_CONTROLS
    setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
    //getContentPane().setLayout(new BorderLayout(0, 0));
    getContentPane().setLayout(
                     new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    getContentPane().setBackground(java.awt.Color.white);
    setSize(570, 330);
		
		setTitle("Vegetation Database Workbench - build: @release@ ");
		
		
    // Center the Frame
    Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
    Rectangle frameDim = getBounds();
    setLocation((screenDim.width - frameDim.width) / 2,
                (screenDim.height - frameDim.height) / 2);
    setVisible(false);

    getContentPane().add(Box.createVerticalStrut(8));

    loadingLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    loadingLabel.setText("VegClient is loading...");
    loadingLabel.setForeground(java.awt.Color.red);
    loadingLabel.setFont(new Font("Dialog", Font.BOLD, 14));
    loadingLabel.setVisible(false);
    getContentPane().add(loadingLabel);

    Box titleBox = Box.createHorizontalBox();
    titleBox.setBackground(Color.white);
    titleBox.add(Box.createHorizontalStrut(8));
    titleBox.add(Box.createHorizontalGlue());
   	
		//BFlyIcon = new ImageIcon(("images/owlogos_250_85_1.jpg"));
	  BFlyIcon = new ImageIcon(getClass().getResource("images/owlogos_250_85_1.jpg"));
		
	  JLabel imageLabel = new JLabel();
    imageLabel.setIcon(BFlyIcon);
    titleBox.add(imageLabel);
    titleBox.add(Box.createHorizontalStrut(8));

    Box subTitleBox = Box.createVerticalBox();
    subTitleBox.setBackground(Color.white);
    subTitleBox.add(Box.createVerticalStrut(8));
    subTitleBox.add(Box.createVerticalGlue());
    titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    titleLabel.setText("VegClient");
    titleLabel.setForeground(java.awt.Color.blue);
    titleLabel.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 36));
    subTitleBox.add(titleLabel);
    subTitleBox.add(Box.createVerticalStrut(4));

    subTitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    subTitleLabel.setText("Vegetation Data Management Workbench");
    subTitleLabel.setForeground(java.awt.Color.black);
    subTitleLabel.setFont(new Font("Dialog", Font.BOLD, 14));
    subTitleBox.add(subTitleLabel);
    subTitleBox.add(Box.createVerticalGlue());
    subTitleBox.add(Box.createVerticalStrut(8));

    titleBox.add(subTitleBox);
    titleBox.add(Box.createHorizontalGlue());
    titleBox.add(Box.createHorizontalStrut(8));
    getContentPane().add(titleBox);

    getContentPane().add(Box.createVerticalStrut(8));

    JPanel contribPanel = new JPanel();
    contribPanel.setLayout(new BoxLayout(contribPanel, BoxLayout.Y_AXIS));
    contribPanel.setAlignmentY(Component.TOP_ALIGNMENT);
    contribPanel.setBackground(java.awt.Color.white);
    JLabel contributorsLabel = new JLabel("Contributors:");
    contributorsLabel.setForeground(java.awt.Color.black);
    contributorsLabel.setFont(new Font("Dialog", Font.BOLD, 12));
    contribPanel.add(contributorsLabel);
    for (int i = 0; i < coders.length; i++) {
      JLabel coderLabel = new JLabel();
      coderLabel.setBackground(java.awt.Color.white);
      coderLabel.setForeground(java.awt.Color.black);
      coderLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
      coderLabel.setText("    " + coders[i]);
      contribPanel.add(coderLabel);
    }

    JPanel orgPanel = new JPanel();
    orgPanel.setLayout(new BoxLayout(orgPanel, BoxLayout.Y_AXIS));
    orgPanel.setAlignmentY(Component.TOP_ALIGNMENT);
    orgPanel.setBackground(java.awt.Color.white);
    JLabel orgLabel = new JLabel("Sponsoring Organizations:");
    orgLabel.setForeground(java.awt.Color.black);
    orgLabel.setFont(new Font("Dialog", Font.BOLD, 12));
    orgPanel.add(orgLabel);
    for (int i = 0; i < orgs.length; i++) {
      JLabel instLabel = new JLabel();
      instLabel.setBackground(java.awt.Color.white);
      instLabel.setForeground(java.awt.Color.black);
      instLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
      instLabel.setText("    " + orgs[i]);
      orgPanel.add(instLabel);
    }

    Box creditsBox = Box.createHorizontalBox();
    creditsBox.setBackground(Color.white);
    creditsBox.add(Box.createHorizontalStrut(8));
    creditsBox.add(contribPanel);
    creditsBox.add(Box.createHorizontalStrut(8));
    creditsBox.add(Box.createHorizontalGlue());
    creditsBox.add(orgPanel);
    creditsBox.add(Box.createHorizontalStrut(8));
    getContentPane().add(creditsBox);

    getContentPane().add(Box.createVerticalStrut(8));
    getContentPane().add(Box.createVerticalGlue());

    versionLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
//    versionLabel.setText(ClientFramework.VERSION);
    versionLabel.setBackground(java.awt.Color.white);
    versionLabel.setForeground(java.awt.Color.black);
    versionLabel.setFont(new Font("Dialog", Font.BOLD, 12));

    fundingButton.setText("Credits...");
    fundingButton.setActionCommand("Credits...");

    closeButton.setText("Close");
    closeButton.setActionCommand("Close");

    Box footerBox = Box.createHorizontalBox();
    footerBox.setBackground(Color.white);
    footerBox.add(Box.createHorizontalStrut(8));
    footerBox.add(versionLabel);
    footerBox.add(Box.createHorizontalGlue());
    footerBox.add(fundingButton);
    footerBox.add(Box.createHorizontalStrut(8));
    footerBox.add(closeButton);
    footerBox.add(Box.createHorizontalStrut(8));
    getContentPane().add(footerBox);
    getContentPane().add(Box.createVerticalStrut(8));
    //}}

    //{{INIT_MENUS
    //}}

    //{{REGISTER_LISTENERS
    SymAction lSymAction = new SymAction();
    closeButton.addActionListener(lSymAction);
    fundingButton.addActionListener(lSymAction);
    ClickListener mouseClicks = new ClickListener();
    addMouseListener((MouseListener)mouseClicks);
    //}}
  }

  /**
   * Construct a frame with a title
   *
   * @param sTitle the title for the frame
   */
  public SplashFrame(String sTitle)
  {
    this();
    //setTitle(sTitle);
		setTitle("Vegetation Database Workbench - build: @release@ ");
  }

  
	
	/**
   * Construct a frame and display a note that the application is loading.
   * This is used during application startup only.
   *
   * @param showLoading use boolean true if the loading message should appear
   */
  public SplashFrame(boolean showLoading)
  {
    this();
    if (showLoading)
    {
      loadingLabel.setVisible(showLoading);
      closeButton.setEnabled(false);
    }
  }

  /**
   * Construct a frame and display a note that the application is loading.
   * This is used during application startup only.
   *
   * @param sTitle the title for the frame
   * @param showLoading use boolean true if the loading message should appear
   */
  public SplashFrame(String sTitle, boolean showLoading)
  {
    this();
    setTitle(sTitle);
    if (showLoading)
    {
      loadingLabel.setVisible(showLoading);
      closeButton.setEnabled(false);
    }
  }

  /**
   * Show or hide the window.  This method can probably be eliminated
   * unless we want to move the window every time it is shown.
   *
   * @param b true if window whould be visible
   */
  public void setVisible(boolean b)
  {
    super.setVisible(b);
  }

  /** Test the frame */
  static public void main(String args[])
  {
		System.out.println("starting the SplashFrame application");
     SplashFrame sf = new SplashFrame();
 			sf.setVisible(true);
		(new SplashFrame()).setVisible(true);
  }

  /**
   * Override addNotify -- vis cafe does this, but why?
   */
  public void addNotify()
  {
    // Record the size of the window prior to calling parents addNotify.
    Dimension size = getSize();

    super.addNotify();

    if (frameSizeAdjusted)
        return;
    frameSizeAdjusted = true;

    // Adjust size of frame according to the insets and menu bar
    Insets insets = getInsets();
    javax.swing.JMenuBar menuBar = getRootPane().getJMenuBar();
    int menuBarHeight = 0;
    if (menuBar != null)
      menuBarHeight = menuBar.getPreferredSize().height;
    
    setSize(insets.left + insets.right + size.width,
              insets.top + insets.bottom + size.height + menuBarHeight);
  }

  /**
   * Action listener that waits for the close button to be pressed
   */
  class SymAction implements java.awt.event.ActionListener
  {
    public void actionPerformed(java.awt.event.ActionEvent event)
    {
      Object object = event.getSource();
      if (object == closeButton) {
        closeButton_actionPerformed(event);
      } else if (object == fundingButton) {
        fundingButton_actionPerformed(event);
      }
    }
  }

  /**
   * Implements the close action to close the window.
   */
  void closeButton_actionPerformed(java.awt.event.ActionEvent event)
  {
    this.dispose();
  }

  /**
   * Opens a new dialog with funding information
   */
  void fundingButton_actionPerformed(java.awt.event.ActionEvent event)
  {
    JOptionPane.showMessageDialog(null, credit);
  }

  // Listen for mouse clicks in the window and close the window and
  // close the window upon a click.
  private class ClickListener extends MouseAdapter
  {
    /**
     * Upon mouse clicks close the window
     */
    public void mouseClicked(MouseEvent e)
    {
      closeButton_actionPerformed(null);
    }
  }
}
