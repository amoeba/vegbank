/**
 * '$RCSfile: DataSourceClientViewerApplet.java,v $'
 *
 * Purpose: 
 *
 * '$Author: farrell $'
 * '$Date: 2003-08-21 21:16:43 $'
 * '$Revision: 1.2 $'
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

public class DataSourceClientViewerApplet extends javax.swing.JApplet {

    /** Creates new form DataSourceClientViewerApplet */
    public DataSourceClientViewerApplet() 
    {
        initComponents();
        this.setSize(400,300);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
          jPanel1 = new javax.swing.JPanel();
          projectDescriptionTextArea = new javax.swing.JTextField();
          
          private javax.swing.JTextField projectDescriptionTextArea1;
          projectDescriptionTextArea.setToolTipText("help");
          projectDescriptionTextArea.setText("hdfgjsdf");
          projectDescriptionTextArea.setPreferredSize(new java.awt.Dimension(400, 21));
          projectDescriptionTextArea.setMinimumSize(new java.awt.Dimension(400, 21));
          jPanel1.add(projectDescriptionTextArea);
          
          getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);
        
    }//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField projectDescriptionTextArea;
    // End of variables declaration//GEN-END:variables

}
