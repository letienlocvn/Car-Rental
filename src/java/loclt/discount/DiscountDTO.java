/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loclt.discount;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author WIN
 */
public class DiscountDTO implements Serializable {

    private String discountID, discountName;
    private Date dateOfCreate;
    private Date expiryDate;
    private String description;
    private float discountPercent;
    private boolean status;
    private String username;

    public DiscountDTO(String discountID, String discountName, Date dateOfCreate, Date expiryDate, String description, float discountPercent, boolean status, String username) {
        this.discountID = discountID;
        this.discountName = discountName;
        this.dateOfCreate = dateOfCreate;
        this.expiryDate = expiryDate;
        this.description = description;
        this.discountPercent = discountPercent;
        this.status = status;
        this.username = username;
    }

    public DiscountDTO(String discountID, Date expiryDate, boolean status) {
        this.discountID = discountID;
        this.expiryDate = expiryDate;
        this.status = status;
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public Date getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(Date dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(float discountPercent) {
        this.discountPercent = discountPercent;
    }

    @Override
    public String toString() {
        return "DiscountDTO{" + "discountID=" + discountID + ", discountName=" + discountName + ", dateOfCreate=" + dateOfCreate + ", expiryDate=" + expiryDate + ", description=" + description + ", discountPercent=" + discountPercent + '}';
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
