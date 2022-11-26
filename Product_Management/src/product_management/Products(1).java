package product_management;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author user
 */

public class Products extends javax.swing.JFrame {

    /**
     * Creates new form Products
     */
    
    public Products() {
        initComponents();
        Show_Products_In_JTable();
        //scaleImage();
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
    
/*
public void scaleImage () {

ImageIcon icon= new ImageIcon ("D:\\Netbeans\\Product_Management\\src\\product_management\\finalBack.jpg");
Image img = icon.getImage ();
Image imgScale = img.getScaledInstance(jLabel8.getWidth(),jLabel8.getHeight(),Image.SCALE_SMOOTH);
ImageIcon scaledIcon = new ImageIcon(imgScale);
jLabel8.setIcon (scaledIcon);
}*/
    int pos=0;
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
    public boolean FindProduct(String name , ArrayList<Products_Management> FD)
    {
        for(Products_Management food : FD)
        {
            if(food.getName().equalsIgnoreCase(name))
            {
                return true;
            }
        }
        return false;
    }
    public boolean checkInputs()
    {
        ArrayList<Products_Management> existing =new ArrayList<Products_Management>();
        existing = getProductList();
        if(!FindProduct(Name_input.getText(),existing))
        {
            if(ID_input.getText() == null||Name_input.getText() == null||Price_input.getText() == null||Cuisine_input.getSelectedItem().toString() == null)
            {
                return false;
            }
           else
           {
               if(Integer.parseInt(ID_input.getText())<=0||Float.parseFloat(Price_input.getText())<=0.0 )
               {
                   JOptionPane.showMessageDialog(null,"Price or ID cannot be negative","Invalid",JOptionPane.ERROR_MESSAGE);
                   return false;
               }
            String regex = "^.*[0-9]+.*$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(Name_input.getText());
            if(!matcher.matches())
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
            else
            {
                JOptionPane.showMessageDialog(null,"Name cannot have numbers","wrong input",JOptionPane.WARNING_MESSAGE);
            }
                return false;
            }
        }
        else
        {
             JOptionPane.showMessageDialog(null,"Name already exists!","Duplicate entry",JOptionPane.WARNING_MESSAGE);               
            return false;
        }
    }
    public ArrayList<Products_Management>getProductList()
    {
        ArrayList<Products_Management> productList = new ArrayList<Products_Management>();
            Connection con = getConnection();
            String Query = "SELECT * FROM Food_Prd ORDER BY Cuisine,ID";
            Statement st;
            ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery(Query);
            Products_Management product;
            while(rs.next())
            {
                product = new Products_Management(rs.getInt("ID"),rs.getString("Name"),Float.parseFloat(rs.getString("Price")),rs.getString("Cuisine"),rs.getBytes("Image")/*,rs.getString("ImgPath")*/);
                productList.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productList;
    }
    public void Show_Products_In_JTable()
    {
        ArrayList<Products_Management> List = getProductList();
        DefaultTableModel model = (DefaultTableModel)Table.getModel();
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
        Cuisine_input.getEditor().setItem(getProductList().get(index).getCuisine());
        lbl_image.setIcon(ResizeImage(null,getProductList().get(index).getPhoto()));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuBar1 = new java.awt.MenuBar();
        menu1 = new java.awt.Menu();
        menu2 = new java.awt.Menu();
        menuBar2 = new java.awt.MenuBar();
        menu3 = new java.awt.Menu();
        menu4 = new java.awt.Menu();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ID_input = new javax.swing.JTextField();
        Price_input = new javax.swing.JTextField();
        Name_input = new javax.swing.JTextField();
        lbl_image = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        Btn_Choose_Image = new javax.swing.JButton();
        Update_button = new javax.swing.JButton();
        Add_button = new javax.swing.JButton();
        Delete_button = new javax.swing.JButton();
        next = new javax.swing.JButton();
        prev = new javax.swing.JButton();
        Last = new javax.swing.JButton();
        First = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        Close_btn = new javax.swing.JButton();
        Cuisine_input = new javax.swing.JComboBox<>();
        trans = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        menu1.setLabel("File");
        menuBar1.add(menu1);

        menu2.setLabel("Edit");
        menuBar1.add(menu2);

        menu3.setLabel("File");
        menuBar2.add(menu3);

        menu4.setLabel("Edit");
        menuBar2.add(menu4);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 153, 102));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.setFocusCycleRoot(true);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("FZYaoTi", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 26, 34, -1));

        jLabel2.setFont(new java.awt.Font("FZYaoTi", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Name:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 70, 60, -1));

        jLabel3.setFont(new java.awt.Font("FZYaoTi", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(240, 240, 240));
        jLabel3.setText("Price:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 114, 60, -1));

        jLabel4.setFont(new java.awt.Font("FZYaoTi", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(240, 240, 240));
        jLabel4.setText("Cuisine:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 170, 70, -1));

        jLabel5.setFont(new java.awt.Font("FZYaoTi", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(240, 240, 240));
        jLabel5.setText("Image:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 218, 70, -1));

        ID_input.setFont(new java.awt.Font("FZYaoTi", 0, 18)); // NOI18N
        ID_input.setPreferredSize(new java.awt.Dimension(59, 40));
        jPanel1.add(ID_input, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 18, 112, 38));

        Price_input.setFont(new java.awt.Font("FZYaoTi", 0, 18)); // NOI18N
        Price_input.setPreferredSize(new java.awt.Dimension(59, 40));
        Price_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Price_inputActionPerformed(evt);
            }
        });
        jPanel1.add(Price_input, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 106, 112, 38));

        Name_input.setFont(new java.awt.Font("FZYaoTi", 0, 18)); // NOI18N
        Name_input.setPreferredSize(new java.awt.Dimension(59, 40));
        jPanel1.add(Name_input, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 62, 112, 38));

        lbl_image.setBackground(new java.awt.Color(255, 255, 153));
        jPanel1.add(lbl_image, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, 191, 166));

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Cuisine", "Price"
            }
        ));
        Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(341, 13, 565, 264));

        Btn_Choose_Image.setFont(new java.awt.Font("FZYaoTi", 1, 12)); // NOI18N
        Btn_Choose_Image.setText("Choose Image");
        Btn_Choose_Image.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Choose_ImageActionPerformed(evt);
            }
        });
        jPanel1.add(Btn_Choose_Image, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, 191, -1));

        Update_button.setFont(new java.awt.Font("FZYaoTi", 1, 22)); // NOI18N
        Update_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/product_management/updated.png"))); // NOI18N
        Update_button.setText("UPDATE");
        Update_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Update_buttonActionPerformed(evt);
            }
        });
        jPanel1.add(Update_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 340, 150, -1));

        Add_button.setFont(new java.awt.Font("FZYaoTi", 1, 22)); // NOI18N
        Add_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/product_management/insert.png"))); // NOI18N
        Add_button.setText(" INSERT");
        Add_button.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Add_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Add_buttonActionPerformed(evt);
            }
        });
        jPanel1.add(Add_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 340, 150, -1));

        Delete_button.setFont(new java.awt.Font("FZYaoTi", 1, 22)); // NOI18N
        Delete_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/product_management/bin.png"))); // NOI18N
        Delete_button.setText("DELETE");
        Delete_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Delete_buttonActionPerformed(evt);
            }
        });
        jPanel1.add(Delete_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 340, 140, -1));

        next.setFont(new java.awt.Font("FZYaoTi", 1, 12)); // NOI18N
        next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/product_management/next.png"))); // NOI18N
        next.setText("NEXT");
        next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextActionPerformed(evt);
            }
        });
        jPanel1.add(next, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 300, 85, -1));

        prev.setFont(new java.awt.Font("FZYaoTi", 1, 12)); // NOI18N
        prev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/product_management/left-arrow.png"))); // NOI18N
        prev.setText("PREVIOUS");
        prev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevActionPerformed(evt);
            }
        });
        jPanel1.add(prev, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 300, 109, -1));

        Last.setFont(new java.awt.Font("FZYaoTi", 1, 12)); // NOI18N
        Last.setIcon(new javax.swing.ImageIcon(getClass().getResource("/product_management/fast-forward-button.png"))); // NOI18N
        Last.setText("LAST");
        Last.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LastActionPerformed(evt);
            }
        });
        jPanel1.add(Last, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 300, 90, -1));

        First.setFont(new java.awt.Font("FZYaoTi", 1, 12)); // NOI18N
        First.setIcon(new javax.swing.ImageIcon(getClass().getResource("/product_management/previous.png"))); // NOI18N
        First.setText("FIRST");
        First.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FirstActionPerformed(evt);
            }
        });
        jPanel1.add(First, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 300, 110, -1));

        jLabel7.setFont(new java.awt.Font("Freestyle Script", 1, 50)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Kulshrestha Fast Food Center");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 390, 470, 60));

        jButton1.setFont(new java.awt.Font("FZYaoTi", 0, 11)); // NOI18N
        jButton1.setText("Back to Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 450, -1, -1));

        Close_btn.setFont(new java.awt.Font("FZYaoTi", 0, 11)); // NOI18N
        Close_btn.setText("CLOSE");
        Close_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Close_btnActionPerformed(evt);
            }
        });
        jPanel1.add(Close_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 450, -1, -1));

        Cuisine_input.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gravy", "Chinese", "Biryani Ka Dum", "Indian Desi/Combo Meal", "Continental", "Italian", "Sandwich and Rolls", "Appetizer", "Beverages", "Breads and Parantha", "Dessert", "Chaat Bazaar", " ", " ", " " }));
        Cuisine_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Cuisine_inputActionPerformed(evt);
            }
        });
        jPanel1.add(Cuisine_input, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 210, 40));

        trans.setText("Show Transaction");
        trans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transActionPerformed(evt);
            }
        });
        jPanel1.add(trans, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 450, -1, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/product_management/finalBack.jpg"))); // NOI18N
        jLabel8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jLabel8FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jLabel8FocusLost(evt);
            }
        });
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 490));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/product_management/Kulshrestha.jpg"))); // NOI18N
        jLabel10.setText("jLabel10");
        jLabel10.setOpaque(true);
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 0, 470, -1));

        jLabel6.setText("jLabel6");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 114, -1, 0));

        jLabel9.setText("jLabel9");
        jLabel9.setOpaque(true);
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 130, -1, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 11, -1, 487));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Update_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Update_buttonActionPerformed
        if(checkInputs() && ID_input.getText() != null )
        {
            String UpdateQuery = null;
            PreparedStatement ps = null;
            Connection con = getConnection();
            if(ImgPath == null)
            {
                try {
                    UpdateQuery = "UPDATE Food_Prd SET Name = ?, Price = ?,Cuisine = ? WHERE ID = ?";
                    ps = con.prepareStatement(UpdateQuery);
                    ps.setString(1,Name_input.getText());
                    ps.setString(2,Price_input.getText());
                    ps.setString(3,Cuisine_input.getSelectedItem().toString());
                    ps.setInt(4,Integer.parseInt(ID_input.getText()));
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                try {
                    InputStream img = new FileInputStream(new File(ImgPath));
                    UpdateQuery = "UPDATE Food_Prd SET Name = ?, Price = ?,Cuisine = ?,Image = ? WHERE ID = ?";
                    ps = con.prepareStatement(UpdateQuery);
                    ps.setString(1,Name_input.getText());
                    ps.setString(2,Price_input.getText());
                    ps.setString(3,Cuisine_input.getSelectedItem().toString());
                    ps.setBlob(4, img);
                    ps.setInt(5,Integer.parseInt(ID_input.getText()));
                    ps.executeUpdate();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Warning! One or more fields were empty or wrong Action Failed");
        }
        Show_Products_In_JTable();
    }//GEN-LAST:event_Update_buttonActionPerformed

    private void Add_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Add_buttonActionPerformed
        if(checkInputs()&& ImgPath != null)
        {
            try {
                Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement("INSERT INTO Food_Prd(ID,Name,Price,Cuisine,Image)"+"values(?,?,?,?,?) ");
                ps.setString(1,ID_input.getText());
                ps.setString(2,Name_input.getText());
                ps.setString(3,Price_input.getText());
                ps.setString(4,Cuisine_input.getSelectedItem().toString());
                InputStream img = new FileInputStream(new File(ImgPath));
                ps.setBlob(5, img);
                ps.executeUpdate();
                Show_Products_In_JTable();
                JOptionPane.showMessageDialog(null,"Data Inserted");
                        } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,ex.getMessage());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Warning ! One or more Fields are empty!");
        }
        System.out.println("ID : "+ID_input.getText());
        System.out.println("Name : "+Name_input.getText());
        System.out.println("Price : "+Price_input.getText());
        System.out.println("Cuisine : "+Cuisine_input.getSelectedItem().toString());
        System.out.println("Image : "+ImgPath);
    }//GEN-LAST:event_Add_buttonActionPerformed

    private void Delete_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Delete_buttonActionPerformed
        if(!ID_input.getText().equals(""))
        {
            try {
                Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement("DELETE FROM Food_Prd WHERE ID = ?");
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
        Show_Products_In_JTable();
    }//GEN-LAST:event_Delete_buttonActionPerformed

    private void nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextActionPerformed
        pos++;
        if(pos>=getProductList().size())
        {
            pos=0;
        }
        ShowItem(pos);
    }//GEN-LAST:event_nextActionPerformed

    private void prevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevActionPerformed
        pos--;
        if(pos<=0)
        {
            pos=getProductList().size()-1;
        }
        ShowItem(pos);
    }//GEN-LAST:event_prevActionPerformed

    private void LastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LastActionPerformed
        pos=getProductList().size()-1;
        ShowItem(pos);
    }//GEN-LAST:event_LastActionPerformed

    private void FirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FirstActionPerformed
        pos = 0;
        ShowItem(pos);
    }//GEN-LAST:event_FirstActionPerformed

    private void Btn_Choose_ImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Choose_ImageActionPerformed
        JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.image","jpg","png");
        file.addChoosableFileFilter(filter);
        int result = file.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = file.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
            lbl_image.setIcon(ResizeImage(path,null));
            ImgPath = path;
        }
        else
        {
            System.out.println("No File Selected");
        }
    }//GEN-LAST:event_Btn_Choose_ImageActionPerformed

    private void Price_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Price_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Price_inputActionPerformed

    private void jLabel8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jLabel8FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel8FocusGained

    private void jLabel8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jLabel8FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel8FocusLost

    private void TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMouseClicked
       int index = Table.getSelectedRow();
       ShowItem(index);
    }//GEN-LAST:event_TableMouseClicked

    private void Cuisine_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cuisine_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Cuisine_inputActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        Login l = new Login();
        l.setVisible(true);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void Close_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Close_btnActionPerformed
        close();
    }//GEN-LAST:event_Close_btnActionPerformed

    private void transActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transActionPerformed
          Transactions t = new Transactions();
          t.setVisible(true);
    }//GEN-LAST:event_transActionPerformed

    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Products().setVisible(true);
            }
        });
    }*/
    private void close() {
        WindowEvent winClosing = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosing);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add_button;
    private javax.swing.JButton Btn_Choose_Image;
    private javax.swing.JButton Close_btn;
    private javax.swing.JComboBox<String> Cuisine_input;
    private javax.swing.JButton Delete_button;
    private javax.swing.JButton First;
    private javax.swing.JTextField ID_input;
    private javax.swing.JButton Last;
    private javax.swing.JTextField Name_input;
    private javax.swing.JTextField Price_input;
    private javax.swing.JTable Table;
    private javax.swing.JButton Update_button;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_image;
    private java.awt.Menu menu1;
    private java.awt.Menu menu2;
    private java.awt.Menu menu3;
    private java.awt.Menu menu4;
    private java.awt.MenuBar menuBar1;
    private java.awt.MenuBar menuBar2;
    private javax.swing.JButton next;
    private javax.swing.JButton prev;
    private javax.swing.JButton trans;
    // End of variables declaration//GEN-END:variables
}
