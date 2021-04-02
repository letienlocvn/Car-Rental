/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loclt.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import loclt.cars.CarsDAO;
import loclt.cars.CarsDTO;
import loclt.cart.CartObj;
import loclt.users.UsersDTO;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author WIN
 */
public class UserAddCarsShoppingServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(UserAddCarsShoppingServlet.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext sc = request.getServletContext();
        Map<String, String> file = (Map<String, String>) sc.getAttribute("FILE");
        String urlRewriting = file.get("");
        try {
            String pickDateRental = request.getParameter("dateRental");
            String pickDateReturn = request.getParameter("dateReturn");
            if (pickDateRental == null | pickDateReturn == null) {
                Date dateCurrent = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                pickDateRental = sdf.format(dateCurrent);
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, 1); //number of days to add
                pickDateReturn = (String) (sdf.format(calendar.getTime()));
            }

            //1.Customer goes to place shopping
            HttpSession session = request.getSession();
            //2.Customer take cart
            UsersDTO user = (UsersDTO) session.getAttribute("USER");
            if (user != null) {
                CartObj cart = (CartObj) session.getAttribute("CUSTOMERCART");
                //3.Check if cart != null
                if (cart == null) {
                    cart = new CartObj();
                }
                //4. Customer goes shopping
                String carID = request.getParameter("txtcarsID");
                CarsDAO carsDAO = new CarsDAO();
                CarsDTO cars = carsDAO.findPrimakey(carID);
                cars.setQuantity(1);
                cart.addCarsToCarts(cars);
                session.setAttribute("CUSTOMERCART", cart);
                urlRewriting = file.get("mainPageJSP");
            }

            session.setAttribute("DATERENTAL", pickDateRental);
            session.setAttribute("DATERETURN", pickDateReturn);
        } catch (NamingException | SQLException ex) {
            BasicConfigurator.configure();
            LOGGER.error("ERROR at UserAddCarsShoppingServlet: " + ex.getMessage());
        } finally {
            response.sendRedirect(urlRewriting);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
