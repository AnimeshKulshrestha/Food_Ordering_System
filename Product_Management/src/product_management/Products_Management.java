package product_management;

import javax.swing.ImageIcon;

public class Products_Management 
{

    /**
     * @return the imgpath
     */
    
    public String getImgpath() {
        return imgpath;
    }

    public Products_Management(int pid,String pname,float pprice,String pCuisine,byte[] pimg)
    {
        this.id = pid;
        this.name = pname;
        this.price = pprice;
        this.cuisine = pCuisine;
        this.photo = pimg;
    }
    public Products_Management(int pid,String pname,float pprice,String pCuisine)
    {
        this.id = pid;
        this.name = pname;
        this.price = pprice;
        this.cuisine = pCuisine;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public String getCuisine() {
        return cuisine;
    }
    public ImageIcon getImage()
    {
        return new ImageIcon(photo);
    }
    private int id;
    private String name;
    private float price;
    private byte[] photo;
    private String cuisine;
    private String imgpath;
    
}
