/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package product_management;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author user
 */
public class Transactions_Table {

    /**
     * @return the tip
     */
    public float getTip() {
        return tip;
    }

    /**
     * @return the CstID
     */
    public int getCstID() {
        return CstID;
    }
    private int OrderID;
    private String datetime;
    private float Price;
    private float tax;
    private int CstID;
    private float tip;
    public Transactions_Table(int pID,String dt,float p,float t,int cID,float ptip)
    {
        this.OrderID=pID;
        this.datetime=dt;
        this.Price=p;
        this.tax=t;
        this.CstID=cID;
        this.tip=ptip;
    }

    /**
     * @return the OrderID
     */
    public int getOrderID() {
        return OrderID;
    }

    /**
     * @return the datetime
     */
    public String getDatetime() {
        return datetime;
    }

    /**
     * @return the Price
     */
    public float getPrice() {
        return Price;
    }

    /**
     * @return the tax
     */
    public float getTax() {
        return tax;
    }
}
