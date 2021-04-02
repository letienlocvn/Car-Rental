/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loclt.authenticate;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import loclt.utils.DBUtils;

/**
 *
 * @author WIN
 */
public class AuthenticateDAO implements Serializable {

    public boolean insertAuthen(AuthenticateDTO authenticate) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Insert into tblAuthenticate (id, email, code, username) "
                        + "values (?,?,?,?)";
                stm = con.prepareStatement(sql);
                String id = authenticate.getId();
                String email = authenticate.getEmail();
                String username = authenticate.getUsername();
                String code = authenticate.getCode();
                stm.setString(1, id);
                stm.setString(2, email);
                stm.setString(3, code);
                stm.setString(4, username);
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

    public AuthenticateDTO auDTO(String uniqueID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        AuthenticateDTO dto = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select id, username, email, code from tblAuthenticate where id = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, uniqueID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String id = rs.getString("id");
                    String email = rs.getString("email");
                    String username = rs.getString("username");
                    String code = rs.getString("code");
                    dto = new AuthenticateDTO(id, email, code, username);
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
        return dto;
    }
}
