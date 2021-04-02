/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loclt.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import loclt.authenticate.AuthenticateDAO;
import loclt.authenticate.AuthenticateDTO;
import loclt.users.UsersCreateError;
import loclt.users.UsersDAO;
import loclt.users.UsersDTO;
import loclt.utils.SendEmail;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author WIN
 */
public class CreateNewAccountServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(CreateNewAccountServlet.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext sc = request.getServletContext();
        Map<String, String> file = (Map<String, String>) sc.getAttribute("FILE");
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String comfirm = request.getParameter("txtComfirm");
        String phone = request.getParameter("txtPhone");
        int phoneNumber = Integer.parseInt(phone);
        String fullName = request.getParameter("txtFullName");
        String email = request.getParameter("txtEmail");
        String address = request.getParameter("txtAddress");
        String url = file.get("createNewAccountJSP");

        UsersCreateError errors = new UsersCreateError();
        try {
            boolean flag = false;
            if (username.trim().length() < 6 || username.trim().length() > 30) {
                flag = true;
                errors.setUsernameLengthErr("Username requires typing 6-30 characters");
            }
            if (password.trim().length() < 6 || password.trim().length() > 30) {
                flag = true;
                errors.setPasswordLengthErr("Password requires typing 6-30 characters");
            } else if (!password.trim().equals(comfirm.trim())) {
                flag = true;
                errors.setConfirmNotMatched("Comfirm must match password");
            }
            if (fullName.trim().length() < 2 || fullName.trim().length() > 30) {
                flag = true;
                errors.setFullnameLengthErr("FullName requires typing 6-30 characters");
            }
            if (address.trim().length() < 2 || address.trim().length() > 200) {
                flag = true;
                errors.setAddressLengthErr("Address requires typing 2-200 characters");
            }
            if (phone.trim().length() < 9 || phone.trim().length() > 11) {
                flag = true;
                errors.setPhoneLengthErr("Phone requires typing 9-11 numbers");
            }
            if (flag) {
                request.setAttribute("CREATERACCOUNT", errors);
            } else {
                //Verify Code
                // Call the send email method
                String stmpServer = "smtp.gmail.com";
                String subject = "Verify user";
                String body = "Code verify: ";
                SendEmail mail = new SendEmail();
                String codeVerify = mail.getRandom();
                String uniqueID = UUID.randomUUID().toString();
                AuthenticateDTO userAuthen = new AuthenticateDTO(uniqueID, email, codeVerify, username);
                boolean test = mail.sendEmail(stmpServer, userAuthen, subject, body + codeVerify);

                //Create New User
                boolean isFalse = false;
                UsersDAO dao = new UsersDAO();
                UsersDTO user = new UsersDTO(username, password, fullName, 2, isFalse, new Date(), address, phoneNumber);
                boolean result = dao.creatNewAccount(user);

                if (result) {
                    if (test) {
                        HttpSession session = request.getSession();
                        session.setAttribute("USERAUTHEN", userAuthen);
                        AuthenticateDAO auDAO = new AuthenticateDAO();
                        boolean createAuthen = auDAO.insertAuthen(userAuthen);
                        if (createAuthen) {
                            url = file.get("verifyAccountJSP");
                        }
                    } else {
                        request.setAttribute("USERAUTHENFAIL", test);
                        url = file.get("verifyAccountJSP");
                    } //end if verify code
                } else {

                }// end if createAccount

            }
        } catch (SQLException | NamingException e) {
            BasicConfigurator.configure();
            LOGGER.error("ERROR at CreateNewAccountServlet: " + e.getMessage());
            errors.setUsernameIsExisted(username + " is exits in system");
            request.setAttribute("CREATER", errors);
        } catch (Exception ex) {
            BasicConfigurator.configure();
            LOGGER.error("ERROR at CreateNewAccountServlet: " + ex.getMessage());
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
