/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loclt.orders;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import loclt.utils.DBUtils;

/**
 *
 * @author WIN
 */
public class OrdersDAO implements Serializable {

    public boolean createOrder(String orderID, String username, float total, Date dateOfCreate) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUtils.makeConnection();

            if (con != null) {
                String sql = "Insert into tblOrders(orderID, username, total, dateOfCreate) "
                        + "values(?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, orderID);
                stm.setString(2, username);
                stm.setFloat(3, total);
                stm.setTimestamp(4, new Timestamp(dateOfCreate.getTime()));
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

    public String getLastOderByUser(String username) throws NamingException, SQLException {
        String orderID = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select OrderID from tblOrders "
                        + "Where DateOfCreate =(Select MAX(DateOfCreate) "
                        + "From tblOrders where username = ?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                rs = stm.executeQuery();
                if (rs.next()) {
                    orderID = rs.getString("OrderID");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return orderID;
    }

    public void updateQuantity(String carID, int DBquantity, int orderQuantity) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Update tblCars set quantity = ? where carID = ?";
                stm = con.prepareStatement(sql);
                int quantityThenUpdate = DBquantity - orderQuantity;
                stm.setInt(1, quantityThenUpdate);
                stm.setString(2, carID);
                stm.executeUpdate();
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public boolean updateOrderStatus(String orderID, boolean status) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Update tblOrders set OrderID = ?, Status = ? where OrderID = ?";

                stm = con.prepareStatement(sql);
                stm.setString(1, orderID);
                stm.setBoolean(2, status);
                stm.setString(3, orderID);
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

    public List<OrdersDTO> getListOrder(String username) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        List<OrdersDTO> listOrder = null;
        ResultSet rs = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select OrderID, Username, Total, DateOfCreate, Status from tblOrders "
                        + " where username = ? "
                        + " ORDER BY DateOfCreate DESC";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String orderID = rs.getString("OrderID");
                    String usernameOder = rs.getString("Username");
                    float total = rs.getFloat("Total");
                    Date dateOfCreate = rs.getDate("DateOfCreate");
                    boolean status = rs.getBoolean("Status");
                    if (listOrder == null) {
                        listOrder = new ArrayList<>();
                    }
                    OrdersDTO oder = new OrdersDTO(orderID, usernameOder, total, dateOfCreate, status);
                    listOrder.add(oder);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return listOrder;
    }
}
