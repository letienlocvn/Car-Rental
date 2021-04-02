/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loclt.cars;

import java.io.Serializable;
import java.util.Date;
import loclt.categories.CategoriesDTO;
import loclt.discount.DiscountDTO;

/**
 *
 * @author WIN
 */
public class CarsDTO implements Serializable {

    private String carID, carName, color, description, images;
    private Date dateOfCreate;
    private Date dayRental;
    private Date dayReturn;
    private CategoriesDTO categoriesDTO;
    private int quantity;
    private float price;
    private boolean status;

    public CarsDTO() {
    }

    public CarsDTO(String carID, String carName, String color, String description, String images, Date dateOfCreate, CategoriesDTO categoriesDTO, int quantity, float price, boolean status) {
        this.carID = carID;
        this.carName = carName;
        this.color = color;
        this.description = description;
        this.images = images;
        this.dateOfCreate = dateOfCreate;
        this.categoriesDTO = categoriesDTO;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Date getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(Date dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }

    public CategoriesDTO getCategoriesDTO() {
        return categoriesDTO;
    }

    public void setCategoriesDTO(CategoriesDTO categoriesDTO) {
        this.categoriesDTO = categoriesDTO;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getDayRental() {
        return dayRental;
    }

    public void setDayRental(Date dayRental) {
        this.dayRental = dayRental;
    }

    public Date getDayReturn() {
        return dayReturn;
    }

    public void setDayReturn(Date dayReturn) {
        this.dayReturn = dayReturn;
    }

}
