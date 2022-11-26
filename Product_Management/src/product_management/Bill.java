/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package product_management;

import com.mysql.cj.jdbc.DatabaseMetaData;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class Bill extends javax.swing.JFrame {

    /**
     * Creates new form Bill
     */
    public Bill(int pOrderID,int cCustID) {
        initComponents();
        this.OrderID = pOrderID;
        this.CustID = cCustID;
        Show_Products_In_Cart();
        if(CustID==0)
            customerID.setText("GuestID");
        else
            customerID.setText(""+CustID);
        add_tip.setText("0.0");
    }
    int OrderID;
    int CustID;
    private Bill() {
        initComponents();
        Show_Products_In_Cart();
    }
    public Connection getConnection()
    {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/FastFoodCenter","FastFoodDatabase","");
            return con;
        } catch (SQLException ex) {
            Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public ArrayList<Products_Management>getOrderList()
    {
        ArrayList<Products_Management> orderList = new ArrayList<Products_Management>();
            Connection con = getConnection();
            String Query = "SELECT * FROM Table"+OrderID;
            Statement st;
            ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery(Query);
            Products_Management product;
            while(rs.next())
            {
                product = new Products_Management(rs.getInt("ID"),rs.getString("Name"),Float.parseFloat(rs.getString("Price")),rs.getString("Cuisine"));
                orderList.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderList;
    }
    public void Show_Products_In_Cart()
    {
        ArrayList<Products_Management> List = getOrderList();
        DefaultTableModel model = (DefaultTableModel)Cart.getModel();
        model.setRowCount(0);
        Object[] row = new Object[4];
        for(int i=0;i<List.size();i++)
        {
            row[0]=List.get(i).getId();
            row[1]=List.get(i).getName();
            row[3]=List.get(i).getPrice();
            row[2]=List.get(i).getCuisine();
            model.addRow(row);
        }
    }
    public void showID()
    {
        if(CustID==0)
            customerID.setText("Guest Login");
        else
            customerID.setText(""+CustID+"");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        Cart = new javax.swing.JTable();
        bill = new javax.swing.JButton();
        amt = new javax.swing.JTextField();
        Cancel_btn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        taxfld = new javax.swing.JTextField();
        totalwtaxfld = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tip10 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        add_tip = new javax.swing.JTextField();
        payment = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        Date = new com.toedter.calendar.JDateChooser();
        customerID = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Cart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Cuisine", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(Cart);

        bill.setText("Calculate amt");
        bill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                billActionPerformed(evt);
            }
        });

        amt.setEditable(false);
        amt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amtActionPerformed(evt);
            }
        });

        Cancel_btn.setText("Cancel");
        Cancel_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Cancel_btnActionPerformed(evt);
            }
        });

        jLabel1.setText("Total:");

        taxfld.setEditable(false);
        taxfld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taxfldActionPerformed(evt);
            }
        });

        totalwtaxfld.setEditable(false);

        jLabel2.setText("GST 5%:");

        jLabel3.setText("Payable Amount:");

        tip10.setText("Add Tip");
        tip10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tip10ActionPerformed(evt);
            }
        });

        jLabel4.setText("Add Tip:");

        add_tip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_tipActionPerformed(evt);
            }
        });

        payment.setText("Pay Now");
        payment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentActionPerformed(evt);
            }
        });

        jLabel5.setText("Date:");

        customerID.setEditable(false);
        customerID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerIDActionPerformed(evt);
            }
        });

        jLabel6.setText("CustomerID:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Date, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(bill, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(amt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(taxfld, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(totalwtaxfld, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(405, 405, 405)
                        .addComponent(payment, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(Cancel_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tip10, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(186, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(184, 184, 184)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(add_tip, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerID, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(customerID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(92, 92, 92)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(add_tip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(amt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(tip10))
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(taxfld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)
                                .addComponent(bill))
                            .addComponent(jLabel5)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalwtaxfld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Cancel_btn)
                    .addComponent(payment))
                .addContainerGap(94, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void billActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billActionPerformed
        Connection con = getConnection();
        System.out.println("OID:"+OrderID);
        String Query = "SELECT SUM(Price) AS Total FROM Table"+OrderID;
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery(Query);
            while(rs.next())
            {
                Float total,tax,totalwtax;
                total = Float.parseFloat(rs.getString("Total"));
                tax = (Float.parseFloat("0.05")*(total));
                totalwtax = tax+total;
                taxfld.setText(tax.toString());
                totalwtaxfld.setText(totalwtax.toString());
                amt.setText(total.toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_billActionPerformed

    private void amtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amtActionPerformed

    private void Cancel_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cancel_btnActionPerformed
        try {
            Connection con = getConnection();
            PreparedStatement ps1 = con.prepareStatement("DROP TABLE Table"+OrderID);
            //PreparedStatement ps2 = con.prepareStatement("DELETE FROM transaction WHERE OrderID="+OrderID);
            ps1.executeUpdate();
            //ps2.executeUpdate();
            Customer c = new Customer();
            c.setVisible(true);
            close();
        } catch (SQLException ex) {
            Logger.getLogger(Bill.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,"Error");
        }
    }//GEN-LAST:event_Cancel_btnActionPerformed

    private void taxfldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taxfldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_taxfldActionPerformed

    private void tip10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tip10ActionPerformed
        if(Float.parseFloat(add_tip.getText())<=0.0)
        {
            JOptionPane.showMessageDialog(null,"Cannot add negative tip","Negative number inserted",ERROR);
        }
        Float newtotal = (Float.parseFloat(totalwtaxfld.getText())+(Float.parseFloat(add_tip.getText())));
        totalwtaxfld.setText(newtotal.toString());
    }//GEN-LAST:event_tip10ActionPerformed

    private void add_tipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_tipActionPerformed

    }//GEN-LAST:event_add_tipActionPerformed

    private void paymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentActionPerformed
        try {
            Connection con = getConnection();
            PreparedStatement ps1 = con.prepareStatement("INSERT INTO transactions(OrderID,total_price,orderDate,tax,CustomerID,tip)values(?,?,?,?,?,?) ");
                int row = Cart.getSelectedRow();
                ps1.setInt(1,OrderID);
                ps1.setString(2,totalwtaxfld.getText());
                Date date = new Date();
                date = Date.getDate();
                String pattern = "yyyy-MM-dd hh:mm:ss";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String finaldate = simpleDateFormat.format(date);
                ps1.setString(3,finaldate);
                ps1.setString(4,taxfld.getText());
                ps1.setInt(5,CustID);
                ps1.setFloat(6,Float.parseFloat(add_tip.getText()));
                ps1.executeUpdate();
                JOptionPane.showMessageDialog(null,"Payment Complete Thankyou!");
        } catch (SQLException ex) {
            Logger.getLogger(Bill.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,"Payment Failed!","Error",JOptionPane.ERROR_MESSAGE);
        }
        close();
    }//GEN-LAST:event_paymentActionPerformed

    private void customerIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_customerIDActionPerformed

    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Bill().setVisible(true);
            }
        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cancel_btn;
    private javax.swing.JTable Cart;
    private com.toedter.calendar.JDateChooser Date;
    private javax.swing.JTextField add_tip;
    private javax.swing.JTextField amt;
    private javax.swing.JButton bill;
    private javax.swing.JTextField customerID;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton payment;
    private javax.swing.JTextField taxfld;
    private javax.swing.JButton tip10;
    private javax.swing.JTextField totalwtaxfld;
    // End of variables declaration//GEN-END:variables

    private void close() {
        WindowEvent winClosing = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosing);
    }

}
