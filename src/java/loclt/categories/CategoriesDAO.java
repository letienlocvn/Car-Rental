/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loclt.categories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import loclt.utils.DBUtils;

/**
 *
 * @author WIN
 */
public class CategoriesDAO {

    List<CategoriesDTO> listCategories;

    public List<CategoriesDTO> getListCategories() {
        return listCategories;
    }

    public List<CategoriesDTO> getAllCategories() throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select CategoryID, CategoryName,Description "
                        + "from tblCategories";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String categoriesID = rs.getString("CategoryID");
                    String categoriesName = rs.getString("CategoryName");
                    String Description = rs.getString("Description");
                    CategoriesDTO dto = new CategoriesDTO(categoriesID, categoriesName, Description);
                    if (this.listCategories == null) {
                        this.listCategories = new ArrayList<>();
                    }
                    this.listCategories.add(dto);
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
        return listCategories;
    }

    public CategoriesDTO findCategories(String categoriesID) throws NamingException, SQLException {
        int i = findCategoriesID(categoriesID);
        return i < 0 ? null : this.listCategories.get(i);
    }

    public int findCategoriesID(String cateID) throws NamingException, SQLException {
        getAllCategories();
        for (int i = 0; i < this.listCategories.size(); i++) {
            CategoriesDTO get = this.listCategories.get(i);
            if (cateID.equals(get.getCategoryID())) {
                return i;
            }
        }
        return -1;
    }
}
