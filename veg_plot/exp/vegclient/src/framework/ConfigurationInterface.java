/** 
 *  Authors: @author@
 *  Release: @release@
 *	
 *  '$Author: harris $'
 *  '$Date: 2001-10-10 18:12:41 $'
 * 	'$Revision: 1.1 $'
 */
package vegclient.framework;

import javax.swing.*;
import vegclient.framework.*;

public class ConfigurationInterface extends javax.swing.JFrame {
    
    //utility classes
    ClientFramework framework = new ClientFramework();
    ConfigurationFile config = new ConfigurationFile();
    
    //database plugins
    String[] databasePlugins = {"PostgresLinuxPlugin", "PostgresNTPlugin"};

    
    /** Creates new form ConfigurationInterface */
    public ConfigurationInterface() 
    {
        initComponents();
        setTitle("ConfigurationInterface - build: @release@ ");
        setSize(400, 500);
        
        
        workingDirectoryTextField.setText( config.workingDirectory);
        servletLocationTextField.setText( config.servletLocation );
            
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        mainPane = new javax.swing.JPanel();
        generalSettingsPane = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        workingDirectoryTextField = new javax.swing.JTextField();
        workingDirectoryChooserButton = new javax.swing.JButton();
        networkConnectionPane = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        servletLocationTextField = new javax.swing.JTextField();
        localDatabasePane = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        databasePluginComboPane = new JComboBox( databasePlugins );
        updatePane = new javax.swing.JPanel();
        updateConfigurationButton = new javax.swing.JButton();
        
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });
        
        mainPane.setLayout(new javax.swing.BoxLayout(mainPane, javax.swing.BoxLayout.Y_AXIS));
        
        generalSettingsPane.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        
        jLabel2.setText("Working Directory: ");
        generalSettingsPane.add(jLabel2);
        
        workingDirectoryTextField.setBackground(java.awt.Color.pink);
        workingDirectoryTextField.setPreferredSize(new java.awt.Dimension(140, 17));
        workingDirectoryTextField.setMaximumSize(new java.awt.Dimension(140, 2147483647));
        workingDirectoryTextField.setMinimumSize(new java.awt.Dimension(140, 17));
        jScrollPane1.setViewportView(workingDirectoryTextField);
        
        generalSettingsPane.add(jScrollPane1);
        
        workingDirectoryChooserButton.setText("Choose Directory");
        workingDirectoryChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                workingDirectoryChooserButtonActionPerformed(evt);
            }
        });
        
        generalSettingsPane.add(workingDirectoryChooserButton);
        
        mainPane.add(generalSettingsPane);
        
        networkConnectionPane.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        
        jLabel3.setText("Servlet Location:");
        networkConnectionPane.add(jLabel3);
        
        servletLocationTextField.setBackground(java.awt.Color.pink);
        servletLocationTextField.setPreferredSize(new java.awt.Dimension(140, 17));
        servletLocationTextField.setMaximumSize(new java.awt.Dimension(140, 2147483647));
        servletLocationTextField.setMinimumSize(new java.awt.Dimension(140, 17));
        networkConnectionPane.add(servletLocationTextField);
        
        mainPane.add(networkConnectionPane);
        
        localDatabasePane.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        
        jLabel1.setText("Database Plugin: ");
        localDatabasePane.add(jLabel1);
        
        databasePluginComboPane.setBackground(java.awt.Color.pink);
        localDatabasePane.add(databasePluginComboPane);
        
        mainPane.add(localDatabasePane);
        
        updatePane.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 6));
        
        updateConfigurationButton.setText("Update Configuration");
        updateConfigurationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateConfigurationButtonActionPerformed(evt);
            }
        });
        
        updatePane.add(updateConfigurationButton);
        
        mainPane.add(updatePane);
        
        getContentPane().add(mainPane);
        
        pack();
    }//GEN-END:initComponents

    private void workingDirectoryChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_workingDirectoryChooserButtonActionPerformed
        // Add your handling code here:
        System.out.println("Choosing a new working Directory");
        String newWorkingDir = framework.directoryChooser();
        if ( newWorkingDir == null )
        {
            framework.debug(0, "not a valid working directory");
        }
        else
        {
          workingDirectoryTextField.setText(newWorkingDir);   
        }
    }//GEN-LAST:event_workingDirectoryChooserButtonActionPerformed

    private void updateConfigurationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateConfigurationButtonActionPerformed
        // Add your handling code here:
        System.out.println("Updating the configuration settings");
        String plugin = (String)databasePluginComboPane.getSelectedItem();
        
        //this replaces the name of the plugin
        config.replacePlugin(plugin);
        
        //replaces the working directory
        config.replaceWorkingDirectory( workingDirectoryTextField.getText() );
        
        
        
    }//GEN-LAST:event_updateConfigurationButtonActionPerformed
   /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        //System.exit(0);
        this.dispose();
        
    }//GEN-LAST:event_exitForm

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        new ConfigurationInterface().show();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mainPane;
    private javax.swing.JPanel generalSettingsPane;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField workingDirectoryTextField;
    private javax.swing.JButton workingDirectoryChooserButton;
    private javax.swing.JPanel networkConnectionPane;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField servletLocationTextField;
    private javax.swing.JPanel localDatabasePane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JComboBox databasePluginComboPane;
    private javax.swing.JPanel updatePane;
    private javax.swing.JButton updateConfigurationButton;
    // End of variables declaration//GEN-END:variables

}
