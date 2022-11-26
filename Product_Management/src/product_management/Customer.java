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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class Customer extends javax.swing.JFrame {

    /**
     * Creates new form Customer
     */
    public Customer() {
        initComponents();
        Show_Products_In_JTable();
    }

    Customer(int CustoID) {
        initComponents();
        Show_Products_In_JTable();
        this.CustID = CustoID;
        if(CustID==0)
        {
            username.setText("Guest");
            custID.setText("Guest ID");
        }
        else
        {
            username.setText(FindCustomer(CustID));
            custID.setText(""+CustID);
        }
    }
    public int getOrderID()
    {
        UpdateOrderID();
        return OrderID;
    }
    int SearchOn=0;
    int CustID;
    int pos = 0;
    int OrderID = getOrderID();
    public boolean checkInputs()
    {
       if(ID_input.getText() == null||Name_input.getText() == null||Price_input.getText() == null ||Cuisine_input.getText() == null)
       {
           return false;
       }
       else
       {
           try
           {
               Float.parseFloat(Price_input.getText());
               return true;
           }catch(Exception e)
           {
               return false;
           }
       }
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
    public String FindCustomer(int CID)
    {
        ArrayList<CustomerTable> CT = new ArrayList<CustomerTable>();
        CT = getCustList();
        for(CustomerTable cust : CT)
        {
            if(cust.getCustomerID()==CID)
            {
                return cust.getUsername();
            }
        }
        JOptionPane.showMessageDialog(null,"Error in customer ID","Error ID",JOptionPane.ERROR_MESSAGE);
        return null;
    }
    public ArrayList<Products_Management>getProductList()
    {
        ArrayList<Products_Management> productList = new ArrayList<Products_Management>();
            Connection con = getConnection();
            String Query = "SELECT * FROM Food_Prd ORDER BY ID";
            Statement st;
            ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery(Query);
            Products_Management product;
            while(rs.next())
            {
                product = new Products_Management(rs.getInt("ID"),rs.getString("Name"),Float.parseFloat(rs.getString("Price")),rs.getString("Cuisine"),rs.getBytes("Image"));
                productList.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productList;
    }
    public ArrayList<Products_Management>getOrderList()
    {
        ArrayList<Products_Management> orderList = new ArrayList<Products_Management>();
            Connection con = getConnection();
            String Query = "SELECT * FROM Table"+OrderID+" ORDER By ID";
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
    public void Show_Products_In_JTable()
    {
        ArrayList<Products_Management> List = getProductList();
        DefaultTableModel model = (DefaultTableModel)Menu.getModel();
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
    public void ShowItem(int index)
    {
        ID_input.setText(Integer.toString(getProductList().get(index).getId()));
        Name_input.setText(getProductList().get(index).getName());
        Price_input.setText(Float.toString(getProductList().get(index).getPrice()));
        Cuisine_input.setText(getProductList().get(index).getCuisine());
        lbl_image.setIcon(ResizeImage(null,getProductList().get(index).getPhoto()));
    }
    public void ShowName(int index)
    {
        ID_input.setText(Integer.toString(getSearchbyNameList().get(index).getId()));
        Name_input.setText(getSearchbyNameList().get(index).getName());
        Price_input.setText(Float.toString(getSearchbyNameList().get(index).getPrice()));
        Cuisine_input.setText(getSearchbyNameList().get(index).getCuisine());
        lbl_image.setIcon(ResizeImage(null,getSearchbyNameList().get(index).getPhoto()));
    }
    public void ShowCuisine(int index)
    {
        ID_input.setText(Integer.toString(getSearchbyCuisineList().get(index).getId()));
        Name_input.setText(getSearchbyCuisineList().get(index).getName());
        Price_input.setText(Float.toString(getSearchbyCuisineList().get(index).getPrice()));
        Cuisine_input.setText(getSearchbyCuisineList().get(index).getCuisine());
        lbl_image.setIcon(ResizeImage(null,getSearchbyCuisineList().get(index).getPhoto()));
    }
    public void UpdateOrderID()
    {
        Connection con = getConnection();
                if(tableexist(con)){
                try {
                    String Query = "SELECT COUNT(*) FROM transactions";
                    Statement st;
                    ResultSet rs;
                    st = con.createStatement();
                    rs = st.executeQuery(Query);
                    while(rs.next())
                    {
                        OrderID = rs.getInt("COUNT(*)")+1;
                    }
                    if(!tableexist(con))
                    {
                        System.out.println("Creating new table");                        
                    }
                }catch(SQLException ex){
                    Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);                    
                }
                }
    }
    String ImgPath=null;
    public ImageIcon ResizeImage(String imagePath,byte[] pic)
    {
        ImageIcon myImage = null;
        if(imagePath != null)
        {
            myImage = new ImageIcon(imagePath);
        }
        else
        {
            myImage = new ImageIcon(pic);
        }
        Image img = myImage.getImage();
        Image img2 = img.getScaledInstance(lbl_image.getWidth(), lbl_image.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        return image;
    }
    public boolean tableexist(Connection con)
    {
        try {
            String table = "Table"+OrderID;
            DatabaseMetaData dbm = (DatabaseMetaData) con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, table, null);
            if(tables.next())
            {
                return true;
            }
            else
            {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    private void close() {
        WindowEvent winClosing = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosing);
    }
    public void Show_Products_In_Cuisine()
    {
        ArrayList<Products_Management> List = getSearchbyCuisineList();
        if(List.isEmpty())
        {
            JOptionPane.showMessageDialog(null,"No results");
        }
        DefaultTableModel model = (DefaultTableModel)Menu.getModel();
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
    public void Show_Products_In_Name()
    {
        ArrayList<Products_Management> List = getSearchbyNameList();
        if(List.isEmpty())
        {
            JOptionPane.showMessageDialog(null,"No results");
        }
        DefaultTableModel model = (DefaultTableModel)Menu.getModel();
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
    public ArrayList<Products_Management>getSearchbyCuisineList()
    {
        ArrayList<Products_Management> productList = new ArrayList<Products_Management>();
            Connection con = getConnection();
            String Query;
            if(srchbyname.getText()!="")
            {
                Query = "SELECT * FROM Food_Prd WHERE Name LIKE '%"+srchbyname.getText()+"%' AND Cuisine LIKE '%"+srchbycuisine.getText()+"%'";
            }
            else
            {
                Query = "SELECT * FROM Food_Prd WHERE Name LIKE '%"+srchbycuisine.getText()+"%'";
            }
            Statement st;
            ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery(Query);
            Products_Management product;
            while(rs.next())
            {
                product = new Products_Management(rs.getInt("ID"),rs.getString("Name"),Float.parseFloat(rs.getString("Price")),rs.getString("Cuisine"),rs.getBytes("Image"));
                productList.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productList;
    }
    public ArrayList<Products_Management>getSearchbyNameList()
    {
        ArrayList<Products_Management> productList = new ArrayList<Products_Management>();
            Connection con = getConnection();
            String Query;
            if(srchbycuisine.getText()!="")
            {
                Query = "SELECT * FROM Food_Prd WHERE Name LIKE '%"+srchbyname.getText()+"%' AND Cuisine LIKE '%"+srchbycuisine.getText()+"%'";
            }
            else
            {
                Query = "SELECT * FROM Food_Prd WHERE Name LIKE '%"+srchbyname.getText()+"%'";
            }
            Statement st;
            ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery(Query);
            Products_Management product;
            while(rs.next())
            {
                product = new Products_Management(rs.getInt("ID"),rs.getString("Name"),Float.parseFloat(rs.getString("Price")),rs.getString("Cuisine"),rs.getBytes("Image"));
                productList.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productList;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        Menu = new javax.swing.JTable();
        add = new javax.swing.JButton();
        lbl_image = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Cart = new javax.swing.JTable();
        bill = new javax.swing.JButton();
        First = new javax.swing.JButton();
        prev = new javax.swing.JButton();
        next = new javax.swing.JButton();
        Last = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        ID_input = new javax.swing.JTextField();
        Name_input = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Price_input = new javax.swing.JTextField();
        Cuisine_input = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        login = new javax.swing.JButton();
        Delete_button = new javax.swing.JButton();
        Close_btn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        custID = new javax.swing.JTextField();
        search = new javax.swing.JButton();
        show_orders = new javax.swing.JButton();
        srchbycuisine = new javax.swing.JTextField();
        srchbyname = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        username = new javax.swing.JTextField();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Menu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Cuisine", "Price"
            }
        ));
        Menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuMouseClicked(evt);
            }
        });
        Menu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenuKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(Menu);

        add.setText("ADD TO CART");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        lbl_image.setBackground(new java.awt.Color(255, 255, 153));

        jLabel6.setFont(new java.awt.Font("FZYaoTi", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 51));
        jLabel6.setText("Image:");

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
        ));
        jScrollPane3.setViewportView(Cart);

        bill.setText("GEN BILL");
        bill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                billActionPerformed(evt);
            }
        });

        First.setFont(new java.awt.Font("FZYaoTi", 1, 12)); // NOI18N
        First.setIcon(new javax.swing.ImageIcon(getClass().getResource("/product_management/previous.png"))); // NOI18N
        First.setText("FIRST");
        First.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FirstActionPerformed(evt);
            }
        });

        prev.setFont(new java.awt.Font("FZYaoTi", 1, 12)); // NOI18N
        prev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/product_management/left-arrow.png"))); // NOI18N
        prev.setText("PREVIOUS");
        prev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevActionPerformed(evt);
            }
        });

        next.setFont(new java.awt.Font("FZYaoTi", 1, 12)); // NOI18N
        next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/product_management/next.png"))); // NOI18N
        next.setText("NEXT");
        next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextActionPerformed(evt);
            }
        });

        Last.setFont(new java.awt.Font("FZYaoTi", 1, 12)); // NOI18N
        Last.setIcon(new javax.swing.ImageIcon(getClass().getResource("/product_management/fast-forward-button.png"))); // NOI18N
        Last.setText("LAST");
        Last.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LastActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("FZYaoTi", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 51));
        jLabel1.setText("ID:");

        ID_input.setEditable(false);
        ID_input.setFont(new java.awt.Font("FZYaoTi", 0, 18)); // NOI18N
        ID_input.setPreferredSize(new java.awt.Dimension(59, 40));
        ID_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ID_inputActionPerformed(evt);
            }
        });

        Name_input.setEditable(false);
        Name_input.setFont(new java.awt.Font("FZYaoTi", 0, 18)); // NOI18N
        Name_input.setPreferredSize(new java.awt.Dimension(59, 40));

        jLabel2.setFont(new java.awt.Font("FZYaoTi", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 0));
        jLabel2.setText("Name:");

        jLabel3.setFont(new java.awt.Font("FZYaoTi", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 102, 0));
        jLabel3.setText("Price:");

        Price_input.setEditable(false);
        Price_input.setFont(new java.awt.Font("FZYaoTi", 0, 18)); // NOI18N
        Price_input.setPreferredSize(new java.awt.Dimension(59, 40));
        Price_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Price_inputActionPerformed(evt);
            }
        });

        Cuisine_input.setEditable(false);
        Cuisine_input.setFont(new java.awt.Font("FZYaoTi", 0, 18)); // NOI18N
        Cuisine_input.setPreferredSize(new java.awt.Dimension(59, 40));

        jLabel4.setFont(new java.awt.Font("FZYaoTi", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 102));
        jLabel4.setText("Cuisine:");

        login.setFont(new java.awt.Font("FZYaoTi", 0, 11)); // NOI18N
        login.setText("Back to Login");
        login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginActionPerformed(evt);
            }
        });

        Delete_button.setFont(new java.awt.Font("FZYaoTi", 0, 12)); // NOI18N
        Delete_button.setText("DELETE FROM CART");
        Delete_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Delete_buttonActionPerformed(evt);
            }
        });

        Close_btn.setFont(new java.awt.Font("FZYaoTi", 0, 11)); // NOI18N
        Close_btn.setText("CLOSE");
        Close_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Close_btnActionPerformed(evt);
            }
        });

        jLabel5.setText("Customer ID:");

        custID.setEditable(false);

        search.setFont(new java.awt.Font("FZYaoTi", 1, 11)); // NOI18N
        search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/magnifier.png"))); // NOI18N
        search.setText("SEARCH ID");
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        show_orders.setText("Show Previous Orders");
        show_orders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                show_ordersActionPerformed(evt);
            }
        });

        srchbycuisine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                srchbycuisineActionPerformed(evt);
            }
        });

        srchbyname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                srchbynameActionPerformed(evt);
            }
        });

        jLabel7.setText("Cuisine:");

        jLabel8.setText("Name:");

        jLabel9.setText("Username:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_image, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(79, 79, 79))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(ID_input, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(143, 143, 143))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(Price_input, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(Name_input, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(12, 12, 12)))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 663, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(bill, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(First, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(27, 27, 27)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(login)
                                            .addComponent(prev, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(58, 58, 58)
                                        .addComponent(show_orders, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(14, 14, 14))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(next, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(32, 32, 32)
                                            .addComponent(Last, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(Close_btn)
                                            .addGap(47, 47, 47)
                                            .addComponent(jLabel5)
                                            .addGap(18, 18, 18)
                                            .addComponent(custID, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(13, 13, 13))))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(srchbyname, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(srchbycuisine, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(46, 46, 46)
                                        .addComponent(search)
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addGap(655, 655, 655))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Cuisine_input, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(add, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Delete_button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
                        .addGap(844, 844, 844))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(246, 246, 246)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(First, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(prev, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(next)
                            .addComponent(Last)
                            .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bill)
                            .addComponent(login)
                            .addComponent(Close_btn)
                            .addComponent(jLabel5)
                            .addComponent(custID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(srchbycuisine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(show_orders)
                            .addComponent(srchbyname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lbl_image, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel1)
                                            .addComponent(ID_input, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(6, 6, 6))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(Name_input, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                                .addGap(229, 229, 229)))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Price_input, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(Cuisine_input, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(add)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Delete_button)
                        .addGap(36, 36, 36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        Connection con = getConnection();    
        if(checkInputs())
            {
               try {
                if(!tableexist(con))
                {
                    System.out.println("Creating new table");
                    PreparedStatement ps1 = con.prepareStatement("CREATE TABLE Table"+OrderID+" (ID INT NOT NULL,Name VARCHAR(30),Price FLOAT,Cuisine VARCHAR(30),CustomerID INT,FOREIGN KEY (ID) REFERENCES Food_Prd(ID),FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)) ");
                    ps1.executeUpdate();
                }
                PreparedStatement ps = con.prepareStatement("INSERT INTO Table"+OrderID+" (ID,Name,Price,Cuisine,CustomerID) values(?,?,?,?,?) ");
                int row = Cart.getSelectedRow();
                ps.setString(1,ID_input.getText());
                ps.setString(2,Name_input.getText());
                ps.setString(3,Price_input.getText());
                ps.setString(4,Cuisine_input.getText());
                ps.setInt(5,CustID);
                ps.executeUpdate();
                Show_Products_In_Cart();
                SearchOn=0;
                        } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,ex.getMessage());
            }
            }
        else{
            JOptionPane.showMessageDialog(null,"Data not inserted");
            }
            System.out.println("ID : "+ID_input.getText());
            System.out.println("Name : "+Name_input.getText());
            System.out.println("Price : "+Price_input.getText());
            System.out.println("Cuisine : "+Cuisine_input.getText());
            System.out.println("OrderID: "+OrderID);
            System.out.println("CustomerID: "+CustID);
            Show_Products_In_JTable();
    }//GEN-LAST:event_addActionPerformed

    private void MenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuMouseClicked
       int index = Menu.getSelectedRow();
       if(SearchOn==0)
            ShowItem(index);
       else if(SearchOn == 1)
           ShowCuisine(index);
       else if(SearchOn == 2)
           ShowName(index);
    }//GEN-LAST:event_MenuMouseClicked

    private void MenuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenuKeyPressed

    }//GEN-LAST:event_MenuKeyPressed

    private void FirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FirstActionPerformed
        pos = 0;
        if(SearchOn==0)
            ShowItem(pos);
       else if(SearchOn == 1)
           ShowCuisine(pos);
       else if(SearchOn == 2)
           ShowName(pos);
    }//GEN-LAST:event_FirstActionPerformed

    private void prevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevActionPerformed
        pos--;
        if(SearchOn==0)
        {
            if(pos<=0)
            {
                pos=0;
            }
            ShowItem(pos);
        }
       else if(SearchOn == 1)
       {
           if(pos<=0)
        {
            pos=0;
        }
        ShowCuisine(pos);
       }
       else if(SearchOn == 2)
       {
           if(pos<=0)
            {
                pos=0;
            }
            ShowName(pos);
       }
        if(pos<=0)
        {
            pos=getProductList().size()-1;
        }
        ShowItem(pos);
    }//GEN-LAST:event_prevActionPerformed

    private void nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextActionPerformed
        pos++;
        if(SearchOn==0)
        {
            if(pos>=getProductList().size())
            {
                pos=0;
            }
            ShowItem(pos);
        }
       else if(SearchOn == 1)
       {
           if(pos>=getSearchbyCuisineList().size())
        {
            pos=0;
        }
        ShowCuisine(pos);
       }
       else if(SearchOn == 2)
       {
           if(pos>=getSearchbyNameList().size())
            {
                pos=0;
            }
            ShowName(pos);
       }
        
    }//GEN-LAST:event_nextActionPerformed

    private void LastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LastActionPerformed
        pos=getProductList().size()-1;
        if(SearchOn==0)
            ShowItem(pos);
       else if(SearchOn == 1)
           ShowCuisine(pos);
       else if(SearchOn == 2)
           ShowName(pos);
    }//GEN-LAST:event_LastActionPerformed

    private void billActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billActionPerformed
        Bill b = new Bill(OrderID,CustID);
        b.setVisible(true);
        close();
        OrderID++;
    }//GEN-LAST:event_billActionPerformed

    private void loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginActionPerformed
        try {
            Connection con = getConnection();
            PreparedStatement ps1 = con.prepareStatement("DROP TABLE Table"+OrderID);
            ps1.executeUpdate();
            Customer c = new Customer();
            c.setVisible(true);
            close();
        } catch (SQLException ex) {
            Logger.getLogger(Bill.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,"Error");
        }
        close();
        Login l = new Login();
        l.setVisible(true);

    }//GEN-LAST:event_loginActionPerformed

    private void Delete_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Delete_buttonActionPerformed
        if(!ID_input.getText().equals(""))
        {
            try {
                Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement("DELETE FROM Table"+OrderID+" WHERE ID = ?");
                int id = Integer.parseInt(ID_input.getText());
                ps.setInt(1,id);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Product Deleted");
            } catch (SQLException ex) {
                Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Product not Deleted!");
            }

        }
        else
        {
            JOptionPane.showMessageDialog(null,"Product not deleted -> No ID to delete");
        }
        Show_Products_In_Cart();
    }//GEN-LAST:event_Delete_buttonActionPerformed

    private void Close_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Close_btnActionPerformed
        try {
            Connection con = getConnection();
            PreparedStatement ps1 = con.prepareStatement("DROP TABLE Table"+OrderID);
            ps1.executeUpdate();
            Customer c = new Customer();
            c.setVisible(true);
            close();
        } catch (SQLException ex) {
            Logger.getLogger(Bill.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,"Error");
        }
        close();
    }//GEN-LAST:event_Close_btnActionPerformed

    private void Price_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Price_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Price_inputActionPerformed

    private void ID_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ID_inputActionPerformed

    }//GEN-LAST:event_ID_inputActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
       if(srchbyname.getText()=="")
        {
            Show_Products_In_Cuisine();
            SearchOn = 1;
        }
        else if(srchbycuisine.getText()=="")
        {
            Show_Products_In_Name();
            SearchOn = 2;
        }
        else
        {
            Show_Products_In_Cuisine();
            SearchOn = 1;
        }
    }//GEN-LAST:event_searchActionPerformed

    private void show_ordersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_show_ordersActionPerformed
        Transactions t = new Transactions(CustID);
        t.setVisible(true);
    }//GEN-LAST:event_show_ordersActionPerformed

    private void srchbycuisineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_srchbycuisineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_srchbycuisineActionPerformed

    private void srchbynameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_srchbynameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_srchbynameActionPerformed

    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Customer().setVisible(true);
            }
        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Cart;
    private javax.swing.JButton Close_btn;
    private javax.swing.JTextField Cuisine_input;
    private javax.swing.JButton Delete_button;
    private javax.swing.JButton First;
    private javax.swing.JTextField ID_input;
    private javax.swing.JButton Last;
    private javax.swing.JTable Menu;
    private javax.swing.JTextField Name_input;
    private javax.swing.JTextField Price_input;
    private javax.swing.JButton add;
    private javax.swing.JButton bill;
    private javax.swing.JTextField custID;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbl_image;
    private javax.swing.JButton login;
    private javax.swing.JButton next;
    private javax.swing.JButton prev;
    private javax.swing.JButton search;
    private javax.swing.JButton show_orders;
    private javax.swing.JTextField srchbycuisine;
    private javax.swing.JTextField srchbyname;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
