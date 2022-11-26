/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package product_management;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class Transactions extends javax.swing.JFrame {

    /**
     * Creates new form Transactions
     */
    public Transactions() {
        initComponents();
        getConnection();
        Show_Products_In_Transactions();
    }
    public Transactions(int CID) {
        initComponents();
        this.CustID=CID;
        getConnection();
        Show_Products_In_CustomerTransactions();
    }
    int CustID;
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
    public ArrayList<Transactions_Table>getTransactionList()
    {
        ArrayList<Transactions_Table> orderList = new ArrayList<Transactions_Table>();
            Connection con = getConnection();
            String Query = "SELECT * FROM Transactions ORDER BY orderDate";
            Statement st;
            ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery(Query);
            Transactions_Table transaction;
            while(rs.next())
            {
                transaction = new Transactions_Table(rs.getInt("OrderID"),rs.getString("orderDate"),Float.parseFloat(rs.getString("total_price")),Float.parseFloat(rs.getString("tax")),rs.getInt("CustomerID"),Float.parseFloat(rs.getString("Tip")));
                orderList.add(transaction);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderList;
    }
    public ArrayList<Transactions_Table>getCustomersTransactionList()
    {
        ArrayList<Transactions_Table> orderList = new ArrayList<Transactions_Table>();
            Connection con = getConnection();
            System.out.println(CustID);
            String Query = "SELECT * FROM Transactions WHERE CustomerID = "+CustID+" ORDER BY orderDate";
            Statement st;
            ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery(Query);
            Transactions_Table transaction;
            while(rs.next())
            {
                transaction = new Transactions_Table(rs.getInt("OrderID"),rs.getString("orderDate"),Float.parseFloat(rs.getString("total_price")),Float.parseFloat(rs.getString("tax")),rs.getInt("CustomerID"),Float.parseFloat(rs.getString("Tip")));
                orderList.add(transaction);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderList;
    }
    public void Show_Products_In_Transactions()
    {
        ArrayList<Transactions_Table> List = getTransactionList();
        DefaultTableModel model = (DefaultTableModel)Transactions.getModel();
        model.setRowCount(0);
        Object[] row = new Object[6];
        for(int i=0;i<List.size();i++)
        {
            row[0]=List.get(i).getOrderID();
            row[2]=List.get(i).getPrice();
            row[3]=List.get(i).getTax();
            row[1]=List.get(i).getDatetime();
            if(List.get(i).getCstID()==0)
                row[4]="GuestID";
            else    
                row[4]=List.get(i).getCstID();
            row[5]=List.get(i).getTip();
            model.addRow(row);
        }
    }
    public void Show_Products_In_CustomerTransactions()
    {
        ArrayList<Transactions_Table> List = getCustomersTransactionList();
        DefaultTableModel model = (DefaultTableModel)Transactions.getModel();
        model.setRowCount(0);
        Object[] row = new Object[6];
        for(int i=0;i<List.size();i++)
        {
            row[0]=List.get(i).getOrderID();
            row[2]=List.get(i).getPrice();
            row[3]=List.get(i).getTax();
            row[1]=List.get(i).getDatetime();
            if(List.get(i).getCstID()==0)
                row[4]="GuestID";
            else    
                row[4]=List.get(i).getCstID();
            row[5]=List.get(i).getTip();
            model.addRow(row);
        }
    }
    
    public ArrayList<Products_Management>getOrderList()
    {
        ArrayList<Products_Management> orderList = new ArrayList<Products_Management>();
        int row = Transactions.getSelectedRow();
        System.out.print("ROW:"+row);
        String OrderID = Transactions.getModel().getValueAt(row,0).toString();
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
                product = new Products_Management(rs.getInt("ID"),rs.getString("Name"),Float.parseFloat(rs.getString("Price")),rs.getString("Cuisine")/*,rs.getBytes("Image"),rs.getString("ImgPath")*/);
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
        int selrow = Transactions.getSelectedRow();
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        Transactions = new javax.swing.JTable();
        Close = new javax.swing.JButton();
        orders = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Cart = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Transactions.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Order ID", "Date Time", "Price", "Tax", "CustomerID", "Tip"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(Transactions);

        Close.setText("Back");
        Close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseActionPerformed(evt);
            }
        });

        orders.setText("Show Order");
        orders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ordersActionPerformed(evt);
            }
        });

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
        jScrollPane2.setViewportView(Cart);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 653, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Close, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(orders, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(orders)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Close))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseActionPerformed
        close();
    }//GEN-LAST:event_CloseActionPerformed

    private void ordersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ordersActionPerformed
        Show_Products_In_Cart();
    }//GEN-LAST:event_ordersActionPerformed

    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Transactions().setVisible(true);
            }
        });
    }*/
     private void close() {
        WindowEvent winClosing = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosing);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Cart;
    private javax.swing.JButton Close;
    private javax.swing.JTable Transactions;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton orders;
    // End of variables declaration//GEN-END:variables
}
