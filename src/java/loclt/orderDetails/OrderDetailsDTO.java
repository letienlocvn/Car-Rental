/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loclt.orderDetails;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author WIN
 */
public class OrderDetailsDTO implements Serializable {

    private String orderDetailsID;
    private String orderID;
    private int quantity;
    private float price;
    private String carID;
    private Date dateRental;
    private Date dateReturn;

    public OrderDetailsDTO(String orderDetailsID, String orderID, int quantity, float price, String carID, Date dateRental, Date dateReturn) {
        this.orderDetailsID = orderDetailsID;
        this.orderID = orderID;
        this.quantity = quantity;
        this.price = price;
        this.carID = carID;
        this.dateRental = dateRental;
        this.dateReturn = dateReturn;
    }
    
    public OrderDetailsDTO(String orderDetailsID, String orderID, int quantity, float price, String carID) {
        this.orderDetailsID = orderDetailsID;
        this.orderID = orderID;
        this.quantity = quantity;
        this.price = price;
        this.carID = carID;
    }
    
    public OrderDetailsDTO(String carID, Date dateRental, Date dateReturn){
        this.carID = carID;
        this.dateRental = dateRental;
        this.dateReturn = dateReturn;
    }

    public String getOrderDetailsID() {
        return orderDetailsID;
    }

    public void setOrderDetailsID(String orderDetailsID) {
        this.orderDetailsID = orderDetailsID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public Date getDateRental() {
        return dateRental;
    }

    public void setDateRental(Date dateRental) {
        this.dateRental = dateRental;
    }

    public Date getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(Date dateReturn) {
        this.dateReturn = dateReturn;
    }

}
