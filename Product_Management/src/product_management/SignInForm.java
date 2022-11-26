/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package product_management;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class SignInForm extends javax.swing.JFrame {

    /**
     * Creates new form SignInForm
     */
    public SignInForm() {
        initComponents();
    }
    int CustID=1;
    
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
    public ArrayList<CustomerTable>getCustList()
    {
        ArrayList<CustomerTable> custList = new ArrayList<CustomerTable>();
            Connection con = getConnection();
            String Query = "SELECT * FROM Customers ORDER By CustomerID";
            Statement st;
            ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery(Query);
            CustomerTable cust;
            while(rs.next())
            {
                cust = new CustomerTable(rs.getInt("CustomerID"),rs.getString("Username"),rs.getString("Password"));
                custList.add(cust);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return custList;
    }
    public boolean FindCustomer(String name , ArrayList<CustomerTable> CT)
    {
        for(CustomerTable cust : CT)
        {
            if(cust.getUsername().equalsIgnoreCase(name))
            {
                return true;
            }
        }
        return false;
    }
    public boolean checkInputs()
    {
       if(usernamefld.getText() == null||passfld.getText() == null||cnfpassfld.getText() == null)
       {
                return false;
       }
       else
       {
            ArrayList<CustomerTable> existing = new ArrayList<CustomerTable>();
            if(!FindCustomer(usernamefld.getText(),existing))
            {
                if(passfld.getText().equals(cnfpassfld.getText()))
                {
                    if(passfld.getText().length()>=8)
                {
                    String regex = "^.*[0-9]+.*$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(usernamefld.getText());
                    if(matcher.matches())
                    {
                        return true;
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Username should contain atleast 1 numeric character","Invalid Input",JOptionPane.WARNING_MESSAGE);
                        usernamefld.setText("");
                        return false;
                    }
                }
                else
                {
                        JOptionPane.showMessageDialog(null,"Password should be atleast 8 digits long","invalid password length",JOptionPane.WARNING_MESSAGE);                    
                        passfld.setText("");
                        cnfpassfld.setText("");
                        return false;
                }
                }
            else
            {
               passfld.setText("");
               cnfpassfld.setText("");
               JOptionPane.showMessageDialog(null,"Password and Confirm Passwords dont match","Invalid Input",JOptionPane.ERROR_MESSAGE);
               return false;
            }}
            else
            {
                JOptionPane.showMessageDialog(null,"Username already exists!","Duplicate entry",JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        username = new javax.swing.JLabel();
        pass = new javax.swing.JLabel();
        cust_Id = new javax.swing.JLabel();
        cnfpass = new javax.swing.JLabel();
        signin = new javax.swing.JButton();
        passfld = new javax.swing.JPasswordField();
        cnfpassfld = new javax.swing.JPasswordField();
        usernamefld = new javax.swing.JTextField();
        cid = new javax.swing.JTextField();
        cont = new javax.swing.JButton();
        passwarning = new javax.swing.JLabel();
        userwarning = new javax.swing.JLabel();
        back = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        username.setText("username:");

        pass.setText("password:");

        cust_Id.setText("Unique customer ID");

        cnfpass.setText("confirm password:");

        signin.setText("Sign Up");
        signin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signinActionPerformed(evt);
            }
        });

        cid.setEditable(false);
        cid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cidActionPerformed(evt);
            }
        });

        cont.setText("Continue");
        cont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contActionPerformed(evt);
            }
        });

        passwarning.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        passwarning.setForeground(new java.awt.Color(255, 51, 51));
        passwarning.setText("*Password must be atleast 8 characters long");

        userwarning.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        userwarning.setForeground(new java.awt.Color(255, 51, 51));
        userwarning.setText("*Username should contain atleast 1 number");

        back.setText("Back");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(usernamefld, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(userwarning, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addComponent(cust_Id))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(passfld, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(cnfpass, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cnfpassfld, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(cid, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(passwarning, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(signin, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                            .addComponent(cont, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(username)
                    .addComponent(usernamefld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userwarning))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passfld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwarning, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pass))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cnfpass)
                    .addComponent(cnfpassfld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(signin)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(back)
                        .addGap(1, 1, 1)))
                .addComponent(cont)
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cust_Id)
                    .addComponent(cid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void signinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signinActionPerformed

       Connection con = getConnection();
            if(checkInputs())
            {
               try {
                    String Query = "SELECT COUNT(*) FROM Customers";
                    Statement st;
                    ResultSet rs;
                    st = con.createStatement();
                    rs = st.executeQuery(Query);
                    if(rs.next())
                    {
                        CustID = rs.getInt("COUNT(*)");
                        CustID++;
                    }
                    else
                        System.out.println("Error");
                }catch (SQLException ex) {
                    Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
                }
                cid.setText(""+CustID+"");
                PreparedStatement ps;
                try {
                    ps = con.prepareStatement("INSERT INTO Customers (CustomerID,Username,Password) values(?,?,?) ");
                    ps.setString(1,""+CustID+"");
                    ps.setString(2,usernamefld.getText());
                    ps.setString(3,passfld.getText());
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Welcome "+usernamefld.getText()+" \n Congratulation!");
                } catch (SQLException ex) {
                    Logger.getLogger(SignInForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("ID : "+CustID);
            System.out.println("UserName : "+usernamefld.getText());
            System.out.println("Password : "+passfld.getText());
            System.out.println("Confirm pass : "+cnfpassfld.getText());
            
    }//GEN-LAST:event_signinActionPerformed

    private void contActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contActionPerformed
            Customer c = new Customer(CustID);
            c.setVisible(true);
            close();
    }//GEN-LAST:event_contActionPerformed

    private void cidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cidActionPerformed

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        close();
    }//GEN-LAST:event_backActionPerformed

        private void close() {
        WindowEvent winClosing = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosing);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton back;
    private javax.swing.JTextField cid;
    private javax.swing.JLabel cnfpass;
    private javax.swing.JPasswordField cnfpassfld;
    private javax.swing.JButton cont;
    private javax.swing.JLabel cust_Id;
    private javax.swing.JLabel pass;
    private javax.swing.JPasswordField passfld;
    private javax.swing.JLabel passwarning;
    private javax.swing.JButton signin;
    private javax.swing.JLabel username;
    private javax.swing.JTextField usernamefld;
    private javax.swing.JLabel userwarning;
    // End of variables declaration//GEN-END:variables
}
