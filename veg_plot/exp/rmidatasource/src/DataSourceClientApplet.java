import java.util.*;

/*
 * DataSourceClientApplet.java
 *
 * Created on January 29, 2002, 4:29 PM
 */

/**
 *
 * @author  administrator
 */
public class DataSourceClientApplet extends javax.swing.JApplet {
    private String server = "vegbank.nceas.ucsb.edu";
		private String port = "1100";
    private DataSourceClient source = new DataSourceClient(server, port);
    
    private String targetPlot = null; //the plot of interest

    /** Creates new form DataSourceClientApplet */
    public DataSourceClientApplet() {
        try
        {
            initComponents();
            this.setSize(400,700);
            System.out.println("DataSourceClientApplet > connecting to: " + server);
        }
        catch(Exception e)
        {
            System.out.println("DataSourceClientApplet > exception: " + e.getMessage() );
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
                mainPane = new javax.swing.JPanel();
                jTabbedPane1 = new javax.swing.JTabbedPane();
                loginPanel = new javax.swing.JPanel();
                jPanel2 = new javax.swing.JPanel();
                usernameLabel = new javax.swing.JLabel();
                loginNameTextArea = new javax.swing.JTextField();
                jLabel2 = new javax.swing.JLabel();
                loginPasswordTextArea = new javax.swing.JTextField();
                jPanel1 = new javax.swing.JPanel();
                loginButton = new javax.swing.JButton();
                jPanel6 = new javax.swing.JPanel();
                jPanel59 = new javax.swing.JPanel();
                jLabel51 = new javax.swing.JLabel();
                jPanel56 = new javax.swing.JPanel();
                jScrollPane3 = new javax.swing.JScrollPane();
                jList3 = new javax.swing.JList();
                jPanel3 = new javax.swing.JPanel();
                jPanel57 = new javax.swing.JPanel();
                jLabel1 = new javax.swing.JLabel();
                jPanel5 = new javax.swing.JPanel();
                jScrollPane1 = new javax.swing.JScrollPane();
                choosablePlotsJlist = new javax.swing.JList();
                jPanel14 = new javax.swing.JPanel();
                jPanel58 = new javax.swing.JPanel();
                jPanel12 = new javax.swing.JPanel();
                jButton2 = new javax.swing.JButton();
                jButton1 = new javax.swing.JButton();
                jScrollPane2 = new javax.swing.JScrollPane();
                jTextArea1 = new javax.swing.JTextArea();
                jPanel13 = new javax.swing.JPanel();
                jPanel4 = new javax.swing.JPanel();
                jPanel7 = new javax.swing.JPanel();
                jLabel5 = new javax.swing.JLabel();
                projectNameTextArea = new javax.swing.JTextField();
                jPanel8 = new javax.swing.JPanel();
                jLabel6 = new javax.swing.JLabel();
                projectDescriptionTextArea = new javax.swing.JTextField();
                jPanel9 = new javax.swing.JPanel();
                jLabel7 = new javax.swing.JLabel();
                projectStartDateTextArea = new javax.swing.JTextField();
                jPanel10 = new javax.swing.JPanel();
                jLabel8 = new javax.swing.JLabel();
                projectStopDateTextArea = new javax.swing.JTextField();
                jPanel16 = new javax.swing.JPanel();
                jPanel17 = new javax.swing.JPanel();
                jLabel14 = new javax.swing.JLabel();
                authorPlotCodeTextArea = new javax.swing.JTextField();
                jPanel18 = new javax.swing.JPanel();
                jLabel15 = new javax.swing.JLabel();
                latitudeTextArea = new javax.swing.JTextField();
                jPanel19 = new javax.swing.JPanel();
                jLabel16 = new javax.swing.JLabel();
                longitudeTextArea = new javax.swing.JTextField();
                jPanel20 = new javax.swing.JPanel();
                jLabel17 = new javax.swing.JLabel();
                geologyTextArea = new javax.swing.JTextField();
                jPanel21 = new javax.swing.JPanel();
                jLabel18 = new javax.swing.JLabel();
                countryTextArea = new javax.swing.JTextField();
                jPanel22 = new javax.swing.JPanel();
                jLabel19 = new javax.swing.JLabel();
                stateTextArea = new javax.swing.JTextField();
                jPanel23 = new javax.swing.JPanel();
                jLabel20 = new javax.swing.JLabel();
                xCoordTextArea = new javax.swing.JTextField();
                jPanel24 = new javax.swing.JPanel();
                jLabel21 = new javax.swing.JLabel();
                yCoordTextArea = new javax.swing.JTextField();
                jPanel25 = new javax.swing.JPanel();
                jLabel22 = new javax.swing.JLabel();
                utmZoneTextArea = new javax.swing.JTextField();
                jPanel26 = new javax.swing.JPanel();
                jPanel27 = new javax.swing.JPanel();
                jLabel23 = new javax.swing.JLabel();
                observationCodeTextArea = new javax.swing.JTextField();
                jPanel28 = new javax.swing.JPanel();
                jLabel24 = new javax.swing.JLabel();
                samplingMethodTextArea = new javax.swing.JTextField();
                jPanel29 = new javax.swing.JPanel();
                jLabel25 = new javax.swing.JLabel();
                observationDateTextArea = new javax.swing.JTextField();
                jPanel30 = new javax.swing.JPanel();
                jLabel26 = new javax.swing.JLabel();
                jTextField23 = new javax.swing.JTextField();
                jPanel31 = new javax.swing.JPanel();
                jLabel27 = new javax.swing.JLabel();
                hydrologicRegimeTextArea = new javax.swing.JTextField();
                jPanel32 = new javax.swing.JPanel();
                jLabel28 = new javax.swing.JLabel();
                soilDepthTextArea = new javax.swing.JTextField();
                jPanel33 = new javax.swing.JPanel();
                jLabel29 = new javax.swing.JLabel();
                percentRockTextArea = new javax.swing.JTextField();
                jPanel34 = new javax.swing.JPanel();
                jLabel30 = new javax.swing.JLabel();
                percentLitterTextArea = new javax.swing.JTextField();
                jPanel35 = new javax.swing.JPanel();
                jLabel31 = new javax.swing.JLabel();
                percentWaterTextArea = new javax.swing.JTextField();
                jPanel36 = new javax.swing.JPanel();
                jPanel37 = new javax.swing.JPanel();
                jLabel32 = new javax.swing.JLabel();
                jScrollPane4 = new javax.swing.JScrollPane();
                strataNamesJList = new javax.swing.JList();
                jPanel38 = new javax.swing.JPanel();
                jLabel33 = new javax.swing.JLabel();
                jScrollPane5 = new javax.swing.JScrollPane();
                plantNamesJList = new javax.swing.JList();
                jPanel46 = new javax.swing.JPanel();
                jPanel47 = new javax.swing.JPanel();
                jLabel41 = new javax.swing.JLabel();
                jTextField38 = new javax.swing.JTextField();
                
                mainPane.setLayout(new javax.swing.BoxLayout(mainPane, javax.swing.BoxLayout.Y_AXIS));
                
                jTabbedPane1.setPreferredSize(new java.awt.Dimension(461, 553));
                jTabbedPane1.setMinimumSize(new java.awt.Dimension(515, 544));
                jTabbedPane1.setMaximumSize(new java.awt.Dimension(32767, 544));
                loginPanel.setLayout(new javax.swing.BoxLayout(loginPanel, javax.swing.BoxLayout.Y_AXIS));
                
                loginPanel.setMaximumSize(new java.awt.Dimension(32767, 32));
                jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
                
                jPanel2.setPreferredSize(new java.awt.Dimension(295, 50));
                jPanel2.setMinimumSize(new java.awt.Dimension(236, 41));
                jPanel2.setMaximumSize(new java.awt.Dimension(32767, 50));
                usernameLabel.setText("email address:");
                jPanel2.add(usernameLabel);
                
                loginNameTextArea.setPreferredSize(new java.awt.Dimension(200, 21));
                loginNameTextArea.setMinimumSize(new java.awt.Dimension(200, 21));
                jPanel2.add(loginNameTextArea);
                
                jLabel2.setText("password:");
                jPanel2.add(jLabel2);
                
                loginPasswordTextArea.setPreferredSize(new java.awt.Dimension(100, 21));
                loginPasswordTextArea.setMaximumSize(new java.awt.Dimension(100, 2147483647));
                loginPasswordTextArea.setMinimumSize(new java.awt.Dimension(100, 21));
                jPanel2.add(loginPasswordTextArea);
                
                loginPanel.add(jPanel2);
              
              jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
                
                jPanel1.setMaximumSize(new java.awt.Dimension(32767, 32));
                loginButton.setText("login");
                loginButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        loginButtonActionPerformed(evt);
                    }
                });
                
                jPanel1.add(loginButton);
                
                loginPanel.add(jPanel1);
              
              jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.Y_AXIS));
                  
                  jPanel6.setPreferredSize(new java.awt.Dimension(310, 120));
                  jPanel6.setMinimumSize(new java.awt.Dimension(510, 120));
                  jPanel6.setMaximumSize(new java.awt.Dimension(32767, 120));
                  jPanel59.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
                  
                  jLabel51.setText("files");
                  jPanel59.add(jLabel51);
                  
                  jPanel6.add(jPanel59);
                
                jScrollPane3.setPreferredSize(new java.awt.Dimension(300, 65));
                    jScrollPane3.setMinimumSize(new java.awt.Dimension(500, 65));
                    jScrollPane3.setMaximumSize(new java.awt.Dimension(32767, 65));
                    jScrollPane3.setViewportView(jList3);
                    
                    jPanel56.add(jScrollPane3);
                  
                  jPanel6.add(jPanel56);
                
                loginPanel.add(jPanel6);
              
              jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.Y_AXIS));
                  
                  jPanel3.setPreferredSize(new java.awt.Dimension(310, 217));
                  jPanel3.setMinimumSize(new java.awt.Dimension(510, 217));
                  jPanel3.setMaximumSize(new java.awt.Dimension(32767, 217));
                  jPanel57.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
                  
                  jLabel1.setText("Plots:");
                  jPanel57.add(jLabel1);
                  
                  jPanel3.add(jPanel57);
                
                jScrollPane1.setPreferredSize(new java.awt.Dimension(300, 220));
                    jScrollPane1.setMinimumSize(new java.awt.Dimension(500, 220));
                    choosablePlotsJlist.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            choosablePlotsJlistMouseClicked(evt);
                        }
                    });
                    
                    jScrollPane1.setViewportView(choosablePlotsJlist);
                    
                    jPanel5.add(jScrollPane1);
                  
                  jPanel5.add(jPanel14);
                  
                  jPanel3.add(jPanel5);
                
                loginPanel.add(jPanel3);
              
              jPanel58.setLayout(new javax.swing.BoxLayout(jPanel58, javax.swing.BoxLayout.Y_AXIS));
                  
                  jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
                  
                  jButton2.setText("Edit");
                  jPanel12.add(jButton2);
                  
                  jButton1.setText("Load");
                  jPanel12.add(jButton1);
                  
                  jPanel58.add(jPanel12);
                
                jScrollPane2.setViewportView(jTextArea1);
                  
                  jPanel58.add(jScrollPane2);
                
                loginPanel.add(jPanel58);
              
              loginPanel.add(jPanel13);
              
              jTabbedPane1.addTab("Initiate", loginPanel);
            
            jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.Y_AXIS));
                
                jPanel4.setPreferredSize(new java.awt.Dimension(456, 179));
                jPanel4.setMinimumSize(new java.awt.Dimension(456, 179));
                jPanel4.setMaximumSize(new java.awt.Dimension(32767, 179));
                jLabel5.setText("Project Name:");
                jPanel7.add(jLabel5);
                
                projectNameTextArea.setPreferredSize(new java.awt.Dimension(400, 21));
                projectNameTextArea.setMinimumSize(new java.awt.Dimension(400, 21));
                jPanel7.add(projectNameTextArea);
                
                jPanel4.add(jPanel7);
              
              jLabel6.setText("Project Description");
                jPanel8.add(jLabel6);
                
                projectDescriptionTextArea.setPreferredSize(new java.awt.Dimension(400, 21));
                projectDescriptionTextArea.setMinimumSize(new java.awt.Dimension(400, 21));
                jPanel8.add(projectDescriptionTextArea);
                
                jPanel4.add(jPanel8);
              
              jLabel7.setText("Project Start Date:");
                jPanel9.add(jLabel7);
                
                projectStartDateTextArea.setPreferredSize(new java.awt.Dimension(400, 21));
                projectStartDateTextArea.setMinimumSize(new java.awt.Dimension(400, 21));
                jPanel9.add(projectStartDateTextArea);
                
                jPanel4.add(jPanel9);
              
              jLabel8.setText("Project Stop Date:");
                jPanel10.add(jLabel8);
                
                projectStopDateTextArea.setPreferredSize(new java.awt.Dimension(400, 21));
                projectStopDateTextArea.setMinimumSize(new java.awt.Dimension(400, 21));
                jPanel10.add(projectStopDateTextArea);
                
                jPanel4.add(jPanel10);
              
              jTabbedPane1.addTab("Project Info", jPanel4);
            
            jPanel16.setLayout(new javax.swing.BoxLayout(jPanel16, javax.swing.BoxLayout.Y_AXIS));
                
                jLabel14.setText("Author code:");
                jPanel17.add(jLabel14);
                
                authorPlotCodeTextArea.setPreferredSize(new java.awt.Dimension(400, 21));
                authorPlotCodeTextArea.setMinimumSize(new java.awt.Dimension(400, 21));
                jPanel17.add(authorPlotCodeTextArea);
                
                jPanel16.add(jPanel17);
              
              jLabel15.setText("Latitude:");
                jPanel18.add(jLabel15);
                
                latitudeTextArea.setPreferredSize(new java.awt.Dimension(400, 21));
                latitudeTextArea.setMinimumSize(new java.awt.Dimension(400, 21));
                jPanel18.add(latitudeTextArea);
                
                jPanel16.add(jPanel18);
              
              jLabel16.setText("Longitude:");
                jPanel19.add(jLabel16);
                
                longitudeTextArea.setPreferredSize(new java.awt.Dimension(400, 21));
                longitudeTextArea.setMinimumSize(new java.awt.Dimension(400, 21));
                jPanel19.add(longitudeTextArea);
                
                jPanel16.add(jPanel19);
              
              jLabel17.setText("Geology:");
                jPanel20.add(jLabel17);
                
                geologyTextArea.setPreferredSize(new java.awt.Dimension(400, 21));
                geologyTextArea.setMinimumSize(new java.awt.Dimension(400, 21));
                jPanel20.add(geologyTextArea);
                
                jPanel16.add(jPanel20);
              
              jLabel18.setText("Country:");
                jPanel21.add(jLabel18);
                
                countryTextArea.setPreferredSize(new java.awt.Dimension(400, 21));
                countryTextArea.setMinimumSize(new java.awt.Dimension(400, 21));
                jPanel21.add(countryTextArea);
                
                jPanel16.add(jPanel21);
              
              jLabel19.setText("State:");
                jLabel19.setToolTipText("state");
                jPanel22.add(jLabel19);
                
                stateTextArea.setPreferredSize(new java.awt.Dimension(400, 21));
                stateTextArea.setMinimumSize(new java.awt.Dimension(400, 21));
                jPanel22.add(stateTextArea);
                
                jPanel16.add(jPanel22);
              
              jLabel20.setText("X Coord:");
                jPanel23.add(jLabel20);
                
                xCoordTextArea.setPreferredSize(new java.awt.Dimension(400, 21));
                xCoordTextArea.setMinimumSize(new java.awt.Dimension(400, 21));
                jPanel23.add(xCoordTextArea);
                
                jPanel16.add(jPanel23);
              
              jLabel21.setText("Y Coord:");
                jPanel24.add(jLabel21);
                
                yCoordTextArea.setPreferredSize(new java.awt.Dimension(400, 21));
                yCoordTextArea.setMinimumSize(new java.awt.Dimension(400, 21));
                jPanel24.add(yCoordTextArea);
                
                jPanel16.add(jPanel24);
              
              jLabel22.setText("UTM Zone:");
                jPanel25.add(jLabel22);
                
                utmZoneTextArea.setPreferredSize(new java.awt.Dimension(400, 21));
                utmZoneTextArea.setMinimumSize(new java.awt.Dimension(400, 21));
                jPanel25.add(utmZoneTextArea);
                
                jPanel16.add(jPanel25);
              
              jTabbedPane1.addTab("Site Info", jPanel16);
            
            jPanel26.setLayout(new javax.swing.BoxLayout(jPanel26, javax.swing.BoxLayout.Y_AXIS));
                
                jLabel23.setText("Observation Code:");
                jPanel27.add(jLabel23);
                
                observationCodeTextArea.setPreferredSize(new java.awt.Dimension(400, 21));
                observationCodeTextArea.setMinimumSize(new java.awt.Dimension(400, 21));
                jPanel27.add(observationCodeTextArea);
                
                jPanel26.add(jPanel27);
              
              jLabel24.setText("Sampling Method:");
                jPanel28.add(jLabel24);
                
                samplingMethodTextArea.setPreferredSize(new java.awt.Dimension(400, 21));
                samplingMethodTextArea.setMinimumSize(new java.awt.Dimension(400, 21));
                jPanel28.add(samplingMethodTextArea);
                
                jPanel26.add(jPanel28);
              
              jLabel25.setText("Observation Date:");
                jPanel29.add(jLabel25);
                
                observationDateTextArea.setPreferredSize(new java.awt.Dimension(400, 21));
                observationDateTextArea.setMinimumSize(new java.awt.Dimension(400, 21));
                jPanel29.add(observationDateTextArea);
                
                jPanel26.add(jPanel29);
              
              jLabel26.setText("Date Accuracy:");
                jPanel30.add(jLabel26);
                
                jTextField23.setPreferredSize(new java.awt.Dimension(400, 21));
                jTextField23.setMinimumSize(new java.awt.Dimension(400, 21));
                jPanel30.add(jTextField23);
                
                jPanel26.add(jPanel30);
              
              jLabel27.setText("Hydrologic Regime:");
                jPanel31.add(jLabel27);
                
                hydrologicRegimeTextArea.setPreferredSize(new java.awt.Dimension(400, 21));
                hydrologicRegimeTextArea.setMinimumSize(new java.awt.Dimension(400, 21));
                jPanel31.add(hydrologicRegimeTextArea);
                
                jPanel26.add(jPanel31);
              
              jLabel28.setText("Soil Depth:");
                jPanel32.add(jLabel28);
                
                soilDepthTextArea.setPreferredSize(new java.awt.Dimension(400, 21));
                soilDepthTextArea.setMinimumSize(new java.awt.Dimension(400, 21));
                jPanel32.add(soilDepthTextArea);
                
                jPanel26.add(jPanel32);
              
              jLabel29.setText("Percent Rock:");
                jPanel33.add(jLabel29);
                
                percentRockTextArea.setPreferredSize(new java.awt.Dimension(400, 21));
                percentRockTextArea.setMinimumSize(new java.awt.Dimension(400, 21));
                jPanel33.add(percentRockTextArea);
                
                jPanel26.add(jPanel33);
              
              jLabel30.setText("Percent Litter:");
                jPanel34.add(jLabel30);
                
                percentLitterTextArea.setPreferredSize(new java.awt.Dimension(400, 21));
                percentLitterTextArea.setMinimumSize(new java.awt.Dimension(400, 21));
                jPanel34.add(percentLitterTextArea);
                
                jPanel26.add(jPanel34);
              
              jLabel31.setText("Percent Water:");
                jPanel35.add(jLabel31);
                
                percentWaterTextArea.setPreferredSize(new java.awt.Dimension(400, 21));
                percentWaterTextArea.setMinimumSize(new java.awt.Dimension(400, 21));
                jPanel35.add(percentWaterTextArea);
                
                jPanel26.add(jPanel35);
              
              jTabbedPane1.addTab("Observation Info", jPanel26);
            
            jPanel36.setLayout(new javax.swing.BoxLayout(jPanel36, javax.swing.BoxLayout.Y_AXIS));
                
                jPanel37.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
                
                jLabel32.setText("Strata Names:");
                jPanel37.add(jLabel32);
                
                jScrollPane4.setViewportView(strataNamesJList);
                  
                  jPanel37.add(jScrollPane4);
                
                jPanel36.add(jPanel37);
              
              jPanel38.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
                
                jLabel33.setText("Plant Names:");
                jPanel38.add(jLabel33);
                
                jScrollPane5.setViewportView(plantNamesJList);
                  
                  jPanel38.add(jScrollPane5);
                
                jPanel36.add(jPanel38);
              
              jTabbedPane1.addTab("Plant Info", jPanel36);
            
            jPanel46.setLayout(new javax.swing.BoxLayout(jPanel46, javax.swing.BoxLayout.Y_AXIS));
                
                jLabel41.setText("Validation Errors:");
                jPanel47.add(jLabel41);
                
                jTextField38.setPreferredSize(new java.awt.Dimension(400, 21));
                jTextField38.setMinimumSize(new java.awt.Dimension(400, 21));
                jPanel47.add(jTextField38);
                
                jPanel46.add(jPanel47);
              
              jTabbedPane1.addTab("Validation Info", jPanel46);
            
            mainPane.add(jTabbedPane1);
          
          getContentPane().add(mainPane, java.awt.BorderLayout.CENTER);
        
    }//GEN-END:initComponents

    private void choosablePlotsJlistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choosablePlotsJlistMouseClicked
        // Add your handling code here:
        System.out.println("DataSourceClientApplet > plot selected ");
        this.targetPlot =  choosablePlotsJlist.getSelectedValue().toString();
        System.out.println("DataSourceClientApplet > the plot: " + choosablePlotsJlist.getSelectedValue().toString() );
        System.out.println("DataSourceClientApplet > updating the varaible fields " );
        
        //update all the plot fields in the other panels
        this.setPlotFields(this.targetPlot);
        
    }//GEN-LAST:event_choosablePlotsJlistMouseClicked

    /**
     * method that updates all the text fields etc in the applet with 
     * values related to the selected plot
     */
    private void setPlotFields(String plot)
    {
        
        String projectName = source.getProjectName(plot);
        String projectDescription = source.getProjectDescription(plot);
        String projectStartDate = source.getProjectStartDate(plot);
        String projectStopDate = source.getProjectStopDate(plot);
        String authorCode = source.getAuthorPlotCode(plot);
        
        String latitude = source.getLatitude(plot);
        String longitude = source.getLongitude(plot);
        String geology = source.getSurfGeo(plot);
        String country = source.getCountry(plot);
        String state = source.getState(plot);
        String x = source.getXCoord(plot);
        String y = source.getYCoord(plot);
        String zone = source.getUTMZone(plot);
        
        String observationCode = source.getObservationCode(plot);
        String samplingMethod =  source.getSamplingMethod(plot);
        String observationDate = source.getObservationStartDate(plot);
        String dateAccuracy = source.getObservationDateAccuracy(plot);
        String hydroRegime = source.getHydrologicRegime(plot);
        String soilDepth = source.getSoilDepth(plot);
        String percentRock = source.getPercentRock(plot);
        String percentLitter = source.getPercentLitter(plot);
        String percentWater = source.getPercentWater(plot);
        
        Vector strataNames = source.getUniqueStrataNames(plot);
        Vector plantNames =  source.getPlantNames(plot);
        
        
        System.out.println("DataSourceClientApplet > strata names: " + strataNames );
        
        this.projectNameTextArea.setText(projectName);
        this.projectDescriptionTextArea.setText(projectDescription);
        this.projectStartDateTextArea.setText(projectStartDate);
        this.projectStopDateTextArea.setText(projectStopDate);
        
        this.authorPlotCodeTextArea.setText(projectStopDate);
        this.latitudeTextArea.setText(latitude);
        this.longitudeTextArea.setText(longitude);
        this.geologyTextArea.setText(geology);
        this.countryTextArea.setText(country);
        this.stateTextArea.setText(state);
        this.xCoordTextArea.setText(x);
        this.yCoordTextArea.setText(y);
        this.utmZoneTextArea.setText(zone);
        
        this.observationCodeTextArea.setText(observationCode);
        this.samplingMethodTextArea.setText(samplingMethod);
        this.observationDateTextArea.setText(observationDate);
        this.hydrologicRegimeTextArea.setText(dateAccuracy);
        this.soilDepthTextArea.setText(soilDepth);
        this.percentRockTextArea.setText(percentRock);
        this.percentLitterTextArea.setText(percentLitter);
        this.percentWaterTextArea.setText(percentWater);
         
				System.out.println("DataSourceClientApplet > plantNames: " + plantNames );
        this.strataNamesJList.setListData(strataNames);
				this.plantNamesJList.setListData(plantNames);
    }
    
    
    
    
    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        // Add your handling code here:
        System.out.println("DataSourceClientApplet > login" );
        Vector plotList = source.getPlotNames();
        choosablePlotsJlist.setListData(plotList);
        
    }//GEN-LAST:event_loginButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mainPane;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField loginNameTextArea;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField loginPasswordTextArea;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton loginButton;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList jList3;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList choosablePlotsJlist;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField projectNameTextArea;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField projectDescriptionTextArea;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField projectStartDateTextArea;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField projectStopDateTextArea;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JTextField authorPlotCodeTextArea;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JTextField latitudeTextArea;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JTextField longitudeTextArea;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JTextField geologyTextArea;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JTextField countryTextArea;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JTextField stateTextArea;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JTextField xCoordTextArea;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JTextField yCoordTextArea;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JTextField utmZoneTextArea;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JTextField observationCodeTextArea;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JTextField samplingMethodTextArea;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JTextField observationDateTextArea;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JTextField hydrologicRegimeTextArea;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JTextField soilDepthTextArea;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JTextField percentRockTextArea;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JTextField percentLitterTextArea;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JTextField percentWaterTextArea;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JList strataNamesJList;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JList plantNamesJList;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JTextField jTextField38;
    // End of variables declaration//GEN-END:variables

}
