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
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import loclt.cars.CarsDAO;
import loclt.cars.CarsDTO;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author WIN
 */
public class UserGoShoppingServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(UserGoShoppingServlet.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext sc = request.getServletContext();
        Map<String, String> file = (Map<String, String>) sc.getAttribute("FILE");
        String url = file.get("mainPageJSP");
        try {
            CarsDAO cars = new CarsDAO();
            String pageIndex = request.getParameter("pageIndex");
            if (pageIndex == null) {
                pageIndex = "1";
            }
            int index = Integer.parseInt(pageIndex);
            int carsInPage = 9;
            int totalCars = cars.countCars();
            int sizeOfPage = totalCars / carsInPage;
            if (totalCars % carsInPage != 0) {
                sizeOfPage++;
            }
            String dateRental = request.getParameter("dateRental");
            String dateReturn = request.getParameter("dateReturn");
            if (dateRental == null | dateReturn == null) {
                Date dateCurrent = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                dateRental = sdf.format(dateCurrent);
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, 1); //number of days to add
                dateReturn = (String) (sdf.format(calendar.getTime()));
            }
            List<CarsDTO> listCars = cars.getCarsPaging(index, carsInPage);
            HttpSession session = request.getSession();
            session.setAttribute("LISTCARSUSER", listCars);
            session.setAttribute("SIZEOFPAGE", sizeOfPage);
            request.setAttribute("DATERENTAL", dateRental);
            request.setAttribute("DATERETURN", dateReturn);
        } catch (NamingException | SQLException ex) {
            BasicConfigurator.configure();
            LOGGER.error("ERROR at UserGoShoppingServlet: " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
