/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loclt.orderDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.naming.NamingException;
import loclt.utils.DBUtils;

/**
 *
 * @author WIN
 */
public class OrderDetailsDAO {

    public boolean createOrder(OrderDetailsDTO dto, Date dateRental, Date dateReturn) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUtils.makeConnection();

            if (con != null) {
                String sql = "Insert into tblOrderDetails(orderDetailsID, OrderID, carID, Quantity,Price,dateRental,dateReturn) "
                        + "values(?,?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                String orderDetails = dto.getOrderDetailsID();
                String orderID = dto.getOrderID();
                String carID = dto.getCarID();
                int quantity = dto.getQuantity();
                float price = dto.getPrice();

                stm.setString(1, orderDetails);
                stm.setString(2, orderID);
                stm.setString(3, carID);
                stm.setInt(4, quantity);
                stm.setFloat(5, price);
                //Chua setup qua ngay
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String rentalDate = sdf.format(dateRental);
                String returnDate = sdf.format(dateReturn);
                stm.setString(6, rentalDate);
                stm.setString(7, returnDate);
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

}
