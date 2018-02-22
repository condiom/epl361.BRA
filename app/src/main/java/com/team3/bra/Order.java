package com.team3.bra;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

/**
 * Created by GamerMakrides on 04/02/2018.
 */

public class Order implements Serializable {
    protected static ArrayList<Order> orders=new ArrayList<Order>();
    protected static ArrayList<Order> notificationOrders=new ArrayList<Order>();
    protected static ArrayList<Order> cookOrders=new ArrayList<Order>();

    private int id;
    private String dateTime;
    private int state;
    private int table;
    private int userID;
    private ArrayList<ItemOrder> items;

    public Order(int tableID) {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.dateTime = formatter.format(now);
        this.id=-1;
        this.state = 0;
        this.table =tableID;
        this.userID =User.currentUser.getId();
        this.items=new ArrayList<ItemOrder>();
    }

    public Order(Vector<Object> vec) {
        this.id = (int) vec.get(0);
        this.dateTime = (String) vec.get(1).toString();
        this.state = (int) vec.get(2);
        this.table =(int) vec.get(3);
        this.userID =(int) vec.get(4);
        this.items=new ArrayList<ItemOrder>();
    }

    public int getId() {
        return id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getState() {
        return state;
    }

    public int getTable() {
        return table;
    }

    public int getUserID() {
        return userID;
    }

    public ArrayList<ItemOrder> getItems() {
        return items;
    }

    public static void findActiveOrders(){
            orders = new ArrayList<Order>();
            String a[] = {};
            Vector<Vector<Object>> vec = JDBC.callProcedure("activeOrders", a);
            for (int i = 0; i < vec.size(); i++) {
                Order o = new Order(vec.get(i));
                orders.add(o);
            }
    }
    public static void findNotificationOrders(){
        notificationOrders=new ArrayList<Order>();
        String a[] = {};
        Vector<Vector<Object>> vec = JDBC.callProcedure("notifications", a);
        for (int i = 0; i < vec.size(); i++) {
            Order o = new Order(vec.get(i));
            notificationOrders.add(o);
        }
    }
    public static void findCookOrders(){
        cookOrders=new ArrayList<Order>();
        String a[] = {};
        Vector<Vector<Object>> vec = JDBC.callProcedure("cookView", a);
        for (int i = 0; i < vec.size(); i++) {
            Order o = new Order(vec.get(i));
            cookOrders.add(o);
        }
    }

    public void fillOrder(){
            this.items=new ArrayList<ItemOrder>();
            String a[] = {this.getId() + ""};
            Vector<Vector<Object>> vec = JDBC.callProcedure("FindItemOrder", a);
            for (int i = 0; i < vec.size(); i++) {
                this.items.add(new ItemOrder(vec.get(i)));
            }
    }
    public void setState(int state){
       this.state=state;
        String a[] = {this.id+"",this.state+""};
        Vector<Vector<Object>> vec= JDBC.callProcedure("EditOrderState", a);

    }

    public void setId(int id){
        if(this.id==-1)
            this.id=id;
    }
    @Override
    public String toString(){
        if(this.table!=-1)
            return "Table: "+this.table;
        else
            return "<New Order>";
    }
}
