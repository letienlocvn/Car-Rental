/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loclt.cars;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import loclt.categories.CategoriesDAO;
import loclt.categories.CategoriesDTO;
import loclt.utils.DBUtils;

/**
 *
 * @author WIN
 */
public class CarsDAO implements Serializable {

    List<CarsDTO> listCars;

    public List<CarsDTO> getListCars() {
        return listCars;
    }

        public int quantityRemaningDB(String carID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int number = -1;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select Quantity from tblCars where carID = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, carID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    number = rs.getInt("Quantity");
                    return number;
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
        return number;
    }
    
    public CarsDTO findPrimakey(String carID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        CarsDTO dto = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select carID, carName, color, dateOfCreate, CategoryID, Price, Quantity, Image, Status, Description "
                        + "from tblCars where carID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, carID);
                rs = stm.executeQuery();
                CategoriesDAO cateDAO = new CategoriesDAO();
                if (rs.next()) {
                    carID = rs.getString("carID");
                    String carName = rs.getString("carName");
                    String color = rs.getString("color");
                    Date dateOfCreate = rs.getDate("DateOfCreate");
                    String cateID = rs.getString("CategoryID");
                    CategoriesDTO cateDTO = cateDAO.findCategories(cateID);
                    float price = rs.getFloat("Price");
                    int quantity = rs.getInt("Quantity");
                    String image = rs.getString("Image");
                    boolean status = rs.getBoolean("Status");
                    String description = rs.getString("Description");
                    dto = new CarsDTO(carID, carName, color, description, image, dateOfCreate, cateDTO, quantity, price, status);
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

    public int countCarsSearch(String txtSearch, String priceFrom, String priceTo) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select COUNT(carID) from tblCars where carName like ? and price >= ? and price <= ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + txtSearch + "%");
                stm.setString(2, priceFrom);
                stm.setString(3, priceTo);
                rs = stm.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count;
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
        return -1;
    }

    public List<CarsDTO> searchCars(String txtSearch, String priceFrom, String priceTo, int pageIndex, int carsInPage) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select carID, carName, color, dateOfCreate, CategoryID, Price, Quantity, Image, Status, Description from \n"
                        + "(Select ROW_NUMBER() over (order by DateOfCreate DESC) as r,\n"
                        + "* from tblCars where carName like ? and price >= ? and price <= ? and quantity > 0 and Status =1) as x where r between (?-1)*?+1 and ?*?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + txtSearch + "%");
                stm.setString(2, priceFrom);
                stm.setString(3, priceTo);
                stm.setInt(4, pageIndex);
                stm.setInt(5, carsInPage);
                stm.setInt(6, pageIndex);
                stm.setInt(7, carsInPage);
                rs = stm.executeQuery();
                CategoriesDAO cateDAO = new CategoriesDAO();
                while (rs.next()) {
                    String carID = rs.getString("carID");
                    String carName = rs.getString("carName");
                    String color = rs.getString("color");
                    Date dateOfCreate = rs.getDate("DateOfCreate");
                    String cateID = rs.getString("CategoryID");
                    CategoriesDTO cateDTO = cateDAO.findCategories(cateID);
                    float price = rs.getFloat("Price");
                    int quantity = rs.getInt("Quantity");
                    String image = rs.getString("Image");
                    boolean status = rs.getBoolean("Status");
                    String description = rs.getString("Description");
                    CarsDTO dto = new CarsDTO(carID, carName, color, description, image, dateOfCreate, cateDTO, quantity, price, status);
                    if (this.listCars == null) {
                        this.listCars = new ArrayList<>();
                    }
                    this.listCars.add(dto);
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
        return listCars;
    }

    public List<CarsDTO> getCarsPaging(int pageIndex, int carsInPage) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select carID, carName, color, dateOfCreate, CategoryID, Price, Quantity, Image, Status, Description from \n"
                        + "(Select ROW_NUMBER() over (order by DateOfCreate DESC) as r,\n"
                        + "* from tblCars where Quantity > 0 and Status =1) as x where r between (?-1)*?+1 and ?*?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, pageIndex);
                stm.setInt(2, carsInPage);
                stm.setInt(3, pageIndex);
                stm.setInt(4, carsInPage);
                rs = stm.executeQuery();
                CategoriesDAO cateDAO = new CategoriesDAO();
                while (rs.next()) {
                    String carID = rs.getString("carID");
                    String carName = rs.getString("carName");
                    String color = rs.getString("color");
                    Date dateOfCreate = rs.getDate("DateOfCreate");
                    String cateID = rs.getString("CategoryID");
                    CategoriesDTO cateDTO = cateDAO.findCategories(cateID);
                    float price = rs.getFloat("Price");
                    int quantity = rs.getInt("Quantity");
                    String image = rs.getString("Image");
                    boolean status = rs.getBoolean("Status");
                    String description = rs.getString("Description");
                    CarsDTO dto = new CarsDTO(carID, carName, color, description, image, dateOfCreate, cateDTO, quantity, price, status);
                    if (this.listCars == null) {
                        this.listCars = new ArrayList<>();
                    }
                    this.listCars.add(dto);
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
        return listCars;
    }

    public int countCars() throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select COUNT(carID) from tblCars";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count;
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
        return -1;
    }

    public List<CarsDTO> getAllListCarsByAdmin() throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select carID, carName, color, dateOfCreate, CategoryID, Price, Quantity, Image, Status, Description "
                        + "from tblCars";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                CategoriesDAO cateDAO = new CategoriesDAO();
                while (rs.next()) {
                    String carID = rs.getString("carID");
                    String carName = rs.getString("carName");
                    String color = rs.getString("color");
                    Date dateOfCreate = rs.getDate("DateOfCreate");
                    String cateID = rs.getString("CategoryID");
                    CategoriesDTO cateDTO = cateDAO.findCategories(cateID);
                    float price = rs.getFloat("Price");
                    int quantity = rs.getInt("Quantity");
                    String image = rs.getString("Image");
                    boolean status = rs.getBoolean("Status");
                    String description = rs.getString("Description");
                    CarsDTO dto = new CarsDTO(carID, carName, color, description, image, dateOfCreate, cateDTO, quantity, price, status);
                    if (this.listCars == null) {
                        this.listCars = new ArrayList<>();
                    }
                    this.listCars.add(dto);
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
        return listCars;
    }

    public List<CarsDTO> getListCarsUsePaging() throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select carID, carName, color, dateOfCreate, CategoryID, Price, Quantity, Image, Status, Description "
                        + "from tblCars where status = 1 Order By NEWID()";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                CategoriesDAO cateDAO = new CategoriesDAO();
                while (rs.next()) {
                    String carID = rs.getString("carID");
                    String carName = rs.getString("carName");
                    String color = rs.getString("color");
                    Date dateOfCreate = rs.getDate("DateOfCreate");
                    String cateID = rs.getString("CategoryID");
                    CategoriesDTO cateDTO = cateDAO.findCategories(cateID);
                    float price = rs.getFloat("Price");
                    int quantity = rs.getInt("Quantity");
                    String image = rs.getString("Image");
                    boolean status = rs.getBoolean("Status");
                    String description = rs.getString("Description");
                    CarsDTO dto = new CarsDTO(carID, carName, color, description, image, dateOfCreate, cateDTO, quantity, price, status);
                    if (this.listCars == null) {
                        this.listCars = new ArrayList<>();
                    }
                    this.listCars.add(dto);
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
        return listCars;
    }

    public List<CarsDTO> getAllListCarsByUser() throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select carID, carName, color, dateOfCreate, CategoryID, Price, Quantity, Image, Status, Description "
                        + "from tblCars where status = 1 Order By NEWID()";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                CategoriesDAO cateDAO = new CategoriesDAO();
                while (rs.next()) {
                    String carID = rs.getString("carID");
                    String carName = rs.getString("carName");
                    String color = rs.getString("color");
                    Date dateOfCreate = rs.getDate("DateOfCreate");
                    String cateID = rs.getString("CategoryID");
                    CategoriesDTO cateDTO = cateDAO.findCategories(cateID);
                    float price = rs.getFloat("Price");
                    int quantity = rs.getInt("Quantity");
                    String image = rs.getString("Image");
                    boolean status = rs.getBoolean("Status");
                    String description = rs.getString("Description");
                    CarsDTO dto = new CarsDTO(carID, carName, color, description, image, dateOfCreate, cateDTO, quantity, price, status);
                    if (this.listCars == null) {
                        this.listCars = new ArrayList<>();
                    }
                    this.listCars.add(dto);
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
        return listCars;
    }
}
