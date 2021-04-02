/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loclt.users;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.naming.NamingException;
import loclt.utils.DBUtils;

/**
 *
 * @author WIN
 */
public class UsersDAO implements Serializable {

    public boolean checkLogin(String username, String password) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select status from tblUsers where username = ? and password = ? and status = 1";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                rs = stm.executeQuery();
                if (rs.next()) {
                    boolean isStatus = rs.getBoolean("Status");
                    return isStatus;
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

    public UsersDTO userInfo(String username, String password) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        UsersDTO user = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select username,password,fullName,role,status from tblUsers where username = ? and password = ? and status = 1";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                rs = stm.executeQuery();
                if (rs.next()) {
                    username = rs.getString("username");
                    password = rs.getString("password");
                    String fullName = rs.getString("fullName");
                    int role = rs.getInt("role");
                    boolean status = rs.getBoolean("status");
                    user = new UsersDTO(username, password, fullName, role, status);
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
        return user;
    }

    public boolean creatNewAccount(UsersDTO user) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            //Make Connection
            con = DBUtils.makeConnection();
            //User
            String username = user.getUsername();
            String password = user.getPassword();
            String fullName = user.getFullName();
            boolean status = user.isStatus();
            int role = user.getRole();
            Date dateOfCreate = user.getDateOfCreate();
            String address = user.getAddress();
            int phone = user.getPhone();
            if (con != null) {
                String sql = "Insert into tblUsers(username, password, fullName, status, role, dateOfCreate, address, phone) "
                        + "values(?,?,?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                stm.setString(3, fullName);
                stm.setBoolean(4, status);
                stm.setInt(5, role);
                stm.setTimestamp(6, new Timestamp(dateOfCreate.getTime()));
                stm.setString(7, address);
                stm.setInt(8, phone);
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {

            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public boolean updateStatusWhenCreate(String username) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Update tblUsers set status = 1 where username = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
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
