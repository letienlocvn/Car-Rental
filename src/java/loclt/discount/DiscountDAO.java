/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loclt.discount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import loclt.utils.DBUtils;

/**
 *
 * @author WIN
 */
public class DiscountDAO {

    List<DiscountDTO> listDiscount;

    public List<DiscountDTO> getListDiscount() {
        return listDiscount;
    }

    public boolean checkExpiryDate(String discountID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select status from tblDiscount where ExpiryDate - GETDATE() >= 0 and discountID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, discountID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    boolean status = rs.getBoolean("Status");
                    return status;
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
        return false;
    }

    public boolean updateDiscount(String discountID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Update tblDiscount set status = 0 where discountID = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, discountID);
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

    public List<DiscountDTO> getListDiscountByUsername(String username, String discountID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select discountID, discountName, DateOfCreate, ExpiryDate, description, discountPercent,status, username from tblDiscount where username = ? and discountID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, discountID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    discountID = rs.getString("discountID");
                    String discountName = rs.getString("discountName");
                    Date dateOfCreate = rs.getDate("DateOfCreate");
                    Date expiryDate = rs.getDate("ExpiryDate");
                    String description = rs.getString("Description");
                    boolean status = rs.getBoolean("Status");
                    float discountPercent = rs.getFloat("discountPercent");
                    username = rs.getString("username");
                    DiscountDTO dto = new DiscountDTO(discountID, discountName, dateOfCreate, expiryDate, description, discountPercent, status, username);
                    if (listDiscount == null) {
                        listDiscount = new ArrayList<>();
                    }
                    listDiscount.add(dto);
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
        return listDiscount;
    }

}
