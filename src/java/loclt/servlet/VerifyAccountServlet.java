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
import loclt.authenticate.AuthenticateDTO;
import static loclt.servlet.UserTakeOverCartServlet.LOGGER;
import loclt.users.UsersDAO;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author WIN
 */
public class VerifyAccountServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(VerifyAccountServlet.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        ServletContext sc = request.getServletContext();
        Map<String, String> file = (Map<String, String>) sc.getAttribute("FILE");
        String url = file.get("verifyAccountJSP");

        try {
            HttpSession session = request.getSession();
            AuthenticateDTO userAuthen = (AuthenticateDTO) session.getAttribute("USERAUTHEN");

            String code = request.getParameter("txtCode");
            boolean result = code.equals(userAuthen.getCode());
            if (result) {
                UsersDAO user = new UsersDAO();
                boolean updateStatus = user.updateStatusWhenCreate(userAuthen.getUsername());
                if (updateStatus) {
                    url = file.get("");
                }
            } else {
                request.setAttribute("USERAUTHENFAIL", result);
            }
        } catch (SQLException | NamingException e) {
            BasicConfigurator.configure();
            LOGGER.error("ERROR at VerifyAccountServlet: " + e.getMessage());
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
