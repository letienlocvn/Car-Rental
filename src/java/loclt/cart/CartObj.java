/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loclt.cart;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import loclt.cars.CarsDTO;

/**
 *
 * @author WIN
 */
public class CartObj implements Serializable {

    private HashMap<String, CarsDTO> cart;

    public CartObj() {
        this.cart = new HashMap<>();
    }

    public HashMap<String, CarsDTO> getCart() {
        return cart;
    }

    public void setCart(HashMap<String, CarsDTO> cart) {
        this.cart = cart;
    }

    public void addCarsToCarts(CarsDTO cars) {
        String carID = cars.getCarID();
        if (this.cart.containsKey(carID)) {
            int newQuantity = this.cart.get(carID).getQuantity() + 1;
            this.cart.get(carID).setQuantity(newQuantity);
        } else {
            this.cart.put(carID, cars);
        }
    }

    public float getTotal() {
        float result = 0;
        for (CarsDTO dto : this.cart.values()) {
            result += (dto.getQuantity() * dto.getPrice());
        }
        return result;
    }

    public void removeCart(String title) {
        if (this.cart.containsKey(title)) {
            this.cart.remove(title);
        }
    }

    public void updateCart(String id, int quantity) {
        if (this.cart.containsKey(id)) {
            this.cart.get(id).setQuantity(quantity);
        }
    }

    public void dateRentalCart(String id, Date dateRental, Date dateReturn) {
        if (this.cart.containsKey(id)) {
            this.cart.get(id).setDayRental(dateRental);
            this.cart.get(id).setDayReturn(dateReturn);
        }
    }

}
