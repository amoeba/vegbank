/**
 * ObjectLookupInterface.java
 *
 * Created on October 19, 2001, 2:29 PM
 *
 *
 *
 *  Authors: @author@
 *  Release: @release@
 *	
 *  '$Author: harris $'
 *  '$Date: 2001-10-22 23:45:00 $'
 * 	'$Revision: 1.1 $' 
 */
package vegclient.ternarysearch;

import vegclient.ternarysearch.*;
import java.util.*;
/**
 *
 * @author  harris
 */
public class ObjectLookupInterface extends javax.swing.JFrame {

    //private TernarySearchTree tst = TSTLoader.getXMLToTST("test.xml", "concatenatedLongName", "concatenatedLongName");
    private TernarySearchTree tst = new TSTLoader().getEmptyTST();;
    /** Creates new form ObjectLookupInterface */
    public ObjectLookupInterface() {
        initComponents();
        setSize(400, 400);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
            buttonGroup1 = new javax.swing.ButtonGroup();
            buttonGroup2 = new javax.swing.ButtonGroup();
            mainPane = new javax.swing.JPanel();
            jPanel2 = new javax.swing.JPanel();
            targetKeyTextField = new javax.swing.JTextField();
            dataSelectionPane = new javax.swing.JPanel();
            plantNameRadioButton = new javax.swing.JRadioButton();
            communityNameRadioButton = new javax.swing.JRadioButton();
            jPanel6 = new javax.swing.JPanel();
            jLabel2 = new javax.swing.JLabel();
            jScrollPane1 = new javax.swing.JScrollPane();
            KeyTextArea = new javax.swing.JTextArea();
            jPanel7 = new javax.swing.JPanel();
            jLabel3 = new javax.swing.JLabel();
            jScrollPane3 = new javax.swing.JScrollPane();
            ObjectTextArea = new javax.swing.JTextArea();
            
            
            addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent evt) {
                    exitForm(evt);
                }
            });
            
            mainPane.setLayout(new javax.swing.BoxLayout(mainPane, javax.swing.BoxLayout.Y_AXIS));
            
            targetKeyTextField.setBackground(java.awt.Color.pink);
            targetKeyTextField.setPreferredSize(new java.awt.Dimension(400, 17));
            targetKeyTextField.setMinimumSize(new java.awt.Dimension(400, 17));
            targetKeyTextField.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    targetKeyTextFieldActionPerformed(evt);
                }
            });
            
            targetKeyTextField.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    targetKeyTextFieldKeyTyped(evt);
                }
            });
            
            jPanel2.add(targetKeyTextField);
            
            mainPane.add(jPanel2);
          
          dataSelectionPane.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
            
            plantNameRadioButton.setText("Plant Names");
            plantNameRadioButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    plantNameRadioButtonActionPerformed(evt);
                }
            });
            
            dataSelectionPane.add(plantNameRadioButton);
            
            communityNameRadioButton.setText("Community Names");
            communityNameRadioButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    communityNameRadioButtonActionPerformed(evt);
                }
            });
            
            dataSelectionPane.add(communityNameRadioButton);
            
            mainPane.add(dataSelectionPane);
          
          jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.Y_AXIS));
            
            jLabel2.setText("Target Key:");
            jPanel6.add(jLabel2);
            
            jScrollPane1.setPreferredSize(new java.awt.Dimension(400, 200));
              KeyTextArea.addMouseListener(new java.awt.event.MouseAdapter() {
                  public void mouseClicked(java.awt.event.MouseEvent evt) {
                      KeyTextAreaMouseClicked(evt);
                  }
              });
              
              jScrollPane1.setViewportView(KeyTextArea);
              
              jPanel6.add(jScrollPane1);
            
            mainPane.add(jPanel6);
          
          jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.Y_AXIS));
            
            jLabel3.setText("Target Object:");
            jPanel7.add(jLabel3);
            
            jScrollPane3.setPreferredSize(new java.awt.Dimension(400, 200));
              jScrollPane3.setViewportView(ObjectTextArea);
              
              jPanel7.add(jScrollPane3);
            
            mainPane.add(jPanel7);
          
          getContentPane().add(mainPane, java.awt.BorderLayout.CENTER);
        
        pack();
    }//GEN-END:initComponents

    private void communityNameRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_communityNameRadioButtonActionPerformed
        // Add your handling code here:
        System.out.println("loading community names");
        tst = TSTLoader.getTableToTST("./data/resources/ETC.txt", "|", 6, 0);
        
    }//GEN-LAST:event_communityNameRadioButtonActionPerformed

    private void plantNameRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plantNameRadioButtonActionPerformed
        // Add your handling code here:
        System.out.println("loading the plant names");
        tst = TSTLoader.getXMLToTST("./data/resources/plant-list-2000.xml", "concatenatedLongName", "concatenatedLongName");
        
        
    }//GEN-LAST:event_plantNameRadioButtonActionPerformed

    private void KeyTextAreaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KeyTextAreaMouseClicked
        // Add your handling code here:
        System.out.println( "mouse clicked: "  );
        String s =  KeyTextArea.getText();
        StringTokenizer t = new StringTokenizer(s, "\n" );
       StringBuffer  sb  = new StringBuffer();
        while ( t.hasMoreTokens() )
        {
            String buf = t.nextToken();
            //System.out.println("tok" + buf );
            String matchingObject = tst.get(buf.trim() ).toString();
            //ObjectTextArea.setText( matchingObject);
            sb.append( buf + " -- " + matchingObject + "\n" );
           
        }
        ObjectTextArea.setText( sb.toString() );
    }//GEN-LAST:event_KeyTextAreaMouseClicked

    private void targetKeyTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_targetKeyTextFieldKeyTyped
        // Add your handling code here:
        System.out.println("typing");
        //find the matching key
        String matchingKeys = tst.matchPrefixString( targetKeyTextField.getText() );
        KeyTextArea.setText( matchingKeys);
        //String matchingObject = tst.get( ).toString();
        //ObjectTextArea.setText( matchingObject);
    }//GEN-LAST:event_targetKeyTextFieldKeyTyped

    private void targetKeyTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_targetKeyTextFieldActionPerformed
        // Add your handling code here:
        System.out.println("hitting return key");
        
       
    }//GEN-LAST:event_targetKeyTextFieldActionPerformed

    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        new ObjectLookupInterface().show();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JPanel mainPane;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField targetKeyTextField;
    private javax.swing.JPanel dataSelectionPane;
    private javax.swing.JRadioButton plantNameRadioButton;
    private javax.swing.JRadioButton communityNameRadioButton;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea KeyTextArea;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea ObjectTextArea;
    // End of variables declaration//GEN-END:variables

}
