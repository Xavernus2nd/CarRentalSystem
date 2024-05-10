/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package RentalSystem;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author USER
 */
public class adminManageCar extends javax.swing.JFrame {

    /**
     * Creates new form adminAddCar
     */
    public adminManageCar() {
        initComponents();
        initSearch();
        bAll.setSelected(true);
        populateTable();
        // table row selection for fields
        tManageCar.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e){
                int selectedRow = tManageCar.getSelectedRow();
                if(selectedRow != -1){
                    tfManageCarID.setText(tManageCar.getValueAt(selectedRow, 0).toString());
                    tfManageCarName.setText(tManageCar.getValueAt(selectedRow, 1).toString());
                    //changing car type combo box to selected value
                    switch (tManageCar.getValueAt(selectedRow, 2).toString()){
                        case "Sedan":
                            cbCarType.setSelectedIndex(0);
                            break;
                        case "Hatchback":
                            cbCarType.setSelectedIndex(1);
                            break;
                        case "SUV":
                            cbCarType.setSelectedIndex(2);
                            break;
                        case "MPV":
                            cbCarType.setSelectedIndex(3);
                            break;
                    }
                    tfManageCarRate.setText(tManageCar.getValueAt(selectedRow, 3).toString());
                    String deletionCheck = tManageCar.getValueAt(selectedRow, 4).toString();
                    //setting deletion combo box
                    if(deletionCheck.equals("Deleted")){
                    cbDeletion.setSelectedIndex(0);
                    }
                    else if(deletionCheck.equals("Not Deleted")){
                    cbDeletion.setSelectedIndex(1);
                    }
                    bSave.setEnabled(true);
                }
            }
        });
        //makes only single selection possible for the table
        tManageCar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void populateTable(){
        DefaultTableModel model = (DefaultTableModel) tManageCar.getModel();
        model.setRowCount(0);
        model.setColumnCount(0); //to clear previous table model
        String[] columnHeaders = {"CarID", "Car Name", "Car Type", "Car Rate (per Day)", "Deletion Status", "Availability"};
        model.setColumnIdentifiers(columnHeaders);
        try {
        BufferedReader carBr = new BufferedReader(new FileReader("car.txt"));
        String line;
        while((line = carBr.readLine()) != null){
            String record[] = line.split(",");
            String rCarID = record[0];
            String rCarName = record[1];
            String rCarType = record[2];
            String rCarRate = record[3];
            String rDeletion = record[4];
            Car car = new Car();
            car.setCarID(Integer.parseInt(record[0]));
            car.setDeletionStatus(rDeletion);
            String carAvail = car.checkAvail();
            if (bAll.isSelected()) {
                //adds all cars to table
                String[] row = {rCarID, rCarName, rCarType, rCarRate, rDeletion, carAvail};
                model.addRow(row);
            } else if (bAvailable.isSelected() && carAvail.equals("Available")) {
                //adds only available cars
                String[] row = {rCarID, rCarName, rCarType, rCarRate, rDeletion, carAvail};
                model.addRow(row);
            } else if (bNotAvailable.isSelected() && carAvail.equals("Not Available")) {
                //adds only unavailable cars
                String[] row = {rCarID, rCarName, rCarType, rCarRate, rDeletion, carAvail};
                model.addRow(row);
            } else if (bDeleted.isSelected() && carAvail.equals("Deleted")){
                //adds only deleted cars
                String[] row = {rCarID, rCarName, rCarType, rCarRate, rDeletion, carAvail};
                model.addRow(row);
            }
        }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        bBack = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tManageCar = new javax.swing.JTable();
        bAddCar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tfManageCarID = new javax.swing.JTextField();
        tfManageCarName = new javax.swing.JTextField();
        bSave = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        cbDeletion = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        tfManageCarRate = new javax.swing.JTextField();
        cbCarType = new javax.swing.JComboBox<>();
        bAvailable = new javax.swing.JRadioButton();
        bAll = new javax.swing.JRadioButton();
        jLabel19 = new javax.swing.JLabel();
        tfSearch = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cUniqueID = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        bNotAvailable = new javax.swing.JRadioButton();
        bDeleted = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Manage Car");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Selected Car Details:");

        bBack.setBackground(new java.awt.Color(255, 0, 102));
        bBack.setForeground(new java.awt.Color(255, 255, 255));
        bBack.setText("Back");
        bBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBackActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(tManageCar);

        bAddCar.setText("Add Car");
        bAddCar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddCarActionPerformed(evt);
            }
        });

        jLabel7.setText("CarID:");

        jLabel8.setText("Car Name:");

        jLabel9.setText("Car Type:");

        tfManageCarID.setEnabled(false);
        tfManageCarID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfManageCarIDActionPerformed(evt);
            }
        });

        bSave.setText("Save");
        bSave.setEnabled(false);
        bSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSaveActionPerformed(evt);
            }
        });

        jLabel10.setText("Deletion Status:");

        cbDeletion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Deleted", "Not Deleted" }));

        jLabel11.setText("Car Rate:");

        cbCarType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sedan", "Hatchback", "SUV", "MPV" }));

        bAvailable.setText("Available");
        bAvailable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAvailableActionPerformed(evt);
            }
        });

        bAll.setText("All");
        bAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAllActionPerformed(evt);
            }
        });

        jLabel19.setText("View:");

        tfSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfSearchActionPerformed(evt);
            }
        });

        jLabel12.setText("Search:");

        cUniqueID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Car ID", "Car Name", "Car Type" }));
        cUniqueID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cUniqueIDActionPerformed(evt);
            }
        });

        jLabel13.setText("by");

        bNotAvailable.setText("Not Available");
        bNotAvailable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNotAvailableActionPerformed(evt);
            }
        });

        bDeleted.setText("Deleted");
        bDeleted.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeletedActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(bAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bAvailable)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bNotAvailable)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bDeleted)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cUniqueID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 753, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(bBack)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bSave)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bAddCar))
                                    .addComponent(cbDeletion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addGap(42, 42, 42)
                                    .addComponent(tfManageCarID))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addGap(18, 18, 18)
                                    .addComponent(tfManageCarName, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfManageCarRate)
                                    .addComponent(cbCarType, 0, 112, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(44, 44, 44))
            .addGroup(layout.createSequentialGroup()
                .addGap(288, 288, 288)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bAvailable)
                    .addComponent(jLabel19)
                    .addComponent(bAll)
                    .addComponent(jLabel12)
                    .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(cUniqueID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bNotAvailable)
                    .addComponent(bDeleted))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tfManageCarID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(tfManageCarName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbCarType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(tfManageCarRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cbDeletion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bBack)
                    .addComponent(bAddCar)
                    .addComponent(bSave))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        getContentPane().add(tfSearch);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bAddCarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddCarActionPerformed
        dispose();
        adminAddCar frame = new adminAddCar();
        frame.setVisible(true);
    }//GEN-LAST:event_bAddCarActionPerformed

    private void bBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBackActionPerformed
        dispose();
        adminMain frame = new adminMain();
        frame.setVisible(true);
    }//GEN-LAST:event_bBackActionPerformed

    private void tfManageCarIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfManageCarIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfManageCarIDActionPerformed

    private void bSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSaveActionPerformed
        try{
            //getting values from fields
            String eCarID = tfManageCarID.getText();
            String eCarName = tfManageCarName.getText();
            String eCarType = cbCarType.getSelectedItem().toString();
            double eCarRate = Double.parseDouble(tfManageCarRate.getText());
            String eCarDeletion = cbDeletion.getSelectedItem().toString();
            //creating car object and calling edit method
            Car car = new Car(Integer.parseInt(eCarID), eCarName, eCarType, eCarRate, eCarDeletion);
            car.editCar();
            //Setting values of changed row
            tManageCar.setValueAt(tfManageCarName.getText(), Integer.parseInt(eCarID)-1, 1);
            tManageCar.setValueAt(cbCarType.getSelectedItem().toString(), Integer.parseInt(eCarID)-1, 2);
            tManageCar.setValueAt(tfManageCarRate.getText(), Integer.parseInt(eCarID)-1, 3);
            tManageCar.setValueAt(cbDeletion.getSelectedItem().toString(), Integer.parseInt(eCarID)-1, 4);
            tManageCar.setValueAt(car.checkAvail(), Integer.parseInt(eCarID)-1, 5);
        }
        catch (NumberFormatException e){
            JOptionPane.showMessageDialog(rootPane, "Please enter valid car information.");
        }
        catch (IllegalArgumentException ex){ //for enum
            JOptionPane.showMessageDialog(rootPane, "Please enter valid car type.");
        }
        
    }//GEN-LAST:event_bSaveActionPerformed

    private void bAvailableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAvailableActionPerformed
        bNotAvailable.setSelected(false);
        bAll.setSelected(false);
        bDeleted.setSelected(false);
        populateTable();
    }//GEN-LAST:event_bAvailableActionPerformed

    private void bAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAllActionPerformed
        bAvailable.setSelected(false);
        bNotAvailable.setSelected(false);
        populateTable();
    }//GEN-LAST:event_bAllActionPerformed

    private TableRowSorter<TableModel> sorter;
    
    private void resetSearch() {
        tfSearch.setText("");
        sorter.setRowFilter(null);
    }
    
    private void initSearch() {
        sorter = new TableRowSorter<>(tManageCar.getModel());
        tManageCar.setRowSorter(sorter);
    }
    
    private void tfSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfSearchActionPerformed
        String searchQuery = tfSearch.getText();
        //search function
        if (!searchQuery.isEmpty()) {
            RowFilter<TableModel, Object> rf = null;
            if (cUniqueID.getSelectedItem().equals("Car ID")) {
                rf = RowFilter.regexFilter("(?i)" + searchQuery, 0); // Column index 0 corresponds to Car ID
            } else if (cUniqueID.getSelectedItem().equals("Car Name")) {
                rf = RowFilter.regexFilter("(?i)" + searchQuery, 1); // Column index 1 corresponds to Car Name
            } else if (cUniqueID.getSelectedItem().equals("Car Type")) {
                rf = RowFilter.regexFilter("(?i)" + searchQuery, 2); // Column index 1 corresponds to Car Type
            }
            if (rf != null) {
                sorter.setRowFilter(rf);
            }
        } else {
            resetSearch();
        }
    }//GEN-LAST:event_tfSearchActionPerformed

    private void cUniqueIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cUniqueIDActionPerformed

    }//GEN-LAST:event_cUniqueIDActionPerformed

    private void bNotAvailableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNotAvailableActionPerformed
        bAvailable.setSelected(false);
        bAll.setSelected(false);
        bDeleted.setSelected(false);
        populateTable();
    }//GEN-LAST:event_bNotAvailableActionPerformed

    private void bDeletedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeletedActionPerformed
        bAvailable.setSelected(false);
        bAll.setSelected(false);
        bNotAvailable.setSelected(false);
        populateTable();
    }//GEN-LAST:event_bDeletedActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(adminManageCar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(adminManageCar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(adminManageCar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(adminManageCar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new adminManageCar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAddCar;
    private javax.swing.JRadioButton bAll;
    private javax.swing.JRadioButton bAvailable;
    private javax.swing.JButton bBack;
    private javax.swing.JRadioButton bDeleted;
    private javax.swing.JRadioButton bNotAvailable;
    private javax.swing.JButton bSave;
    private javax.swing.JComboBox<String> cUniqueID;
    private javax.swing.JComboBox<String> cbCarType;
    private javax.swing.JComboBox<String> cbDeletion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tManageCar;
    private javax.swing.JTextField tfManageCarID;
    private javax.swing.JTextField tfManageCarName;
    private javax.swing.JTextField tfManageCarRate;
    private javax.swing.JTextField tfSearch;
    // End of variables declaration//GEN-END:variables
}