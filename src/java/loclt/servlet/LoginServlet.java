/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loclt.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import loclt.users.UsersDAO;
import loclt.users.UsersDTO;
import loclt.utils.VerifyRecaptcha;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author WIN
 */
public class LoginServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext sc = request.getServletContext();
        Map<String, String> file = (Map<String, String>) sc.getAttribute("FILE");
        String url = file.get("invalidHTML");
        try {
            String username = request.getParameter("txtUsername");
            String password = request.getParameter("txtPassword");

            //Recaptcha
            String gRecaptchaResponse = request
                    .getParameter("g-recaptcha-response");
            boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
            UsersDAO user = new UsersDAO();
            boolean result = user.checkLogin(username, password);
            HttpSession session = request.getSession();
            if (verify) {
                if (result) {
                    UsersDTO userInfo = user.userInfo(username, password);
                    session.setAttribute("USER", userInfo);
                    int role = userInfo.getRole();
                    int isAdmin = 1;
                    int isCustomer = 2;
                    if (role == isAdmin) {
                        url = file.get("adminManagementCar");
                    } else if (role == isCustomer) {
                        url = file.get("userGoShopping");
                    }
                }
            } else {
                url = file.get("");
                request.setAttribute("CHECKBOX", verify);
            }

        } catch (SQLException | NamingException e) {
            BasicConfigurator.configure();
            LOGGER.error("ERROR at LoginServlet: " + e.getMessage());
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
