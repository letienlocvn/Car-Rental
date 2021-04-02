/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loclt.orders;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author WIN
 */
public class OrdersDTO implements Serializable {

    private String orderID;
    private String username;
    private float total;
    private Date dateOfCreate;
    private boolean status;

    public OrdersDTO(String orderID, String username, float total, Date dateOfCreate, boolean status) {
        this.orderID = orderID;
        this.username = username;
        this.total = total;
        this.dateOfCreate = dateOfCreate;
        this.status = status;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Date getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(Date dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
