/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loclt.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import loclt.cars.CarsDAO;
import loclt.cars.CarsDTO;
import loclt.cart.CartObj;
import loclt.discount.DiscountDAO;
import loclt.discount.DiscountDTO;
import loclt.orderDetails.OrderDetailsDAO;
import loclt.orderDetails.OrderDetailsDTO;
import loclt.orders.OrdersDAO;
import loclt.users.UsersDTO;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author WIN
 */
public class UserTakeOverCartServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(UserTakeOverCartServlet.class.getName());

    /*
    Trong đây là các công việc được thực hiện của user như sau:
        1. Remove
        2. Update
        3. Check Out
        4. Discount
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext sc = request.getServletContext();
        Map<String, String> file = (Map<String, String>) sc.getAttribute("FILE");
        String url = file.get("viewCartJSP");
        try {
            String button = request.getParameter("btAction");
            HttpSession session = request.getSession();
            UsersDTO user = (UsersDTO) session.getAttribute("USER");
            CartObj cart = (CartObj) session.getAttribute("CUSTOMERCART");
            CarsDAO cars = new CarsDAO();

            //SetDAY
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
            request.setAttribute("DATERENTAL", pickDateRental);
            request.setAttribute("DATERETURN", pickDateReturn);

            if (user != null) {
                String removeButton = "Remove Cars";
                String updateButton = "Update Cars";
                String checkoutButton = "Check Out";
                String discountButton = "Use Discount";
                String[] listCarID = request.getParameterValues("txtCarID");
                if (removeButton.equals(button)) {
                    //Remove
                    String[] listID = request.getParameterValues("chkRemove");
                    for (String id : listID) {
                        cart.removeCart(id);
                    }
                } else if (updateButton.equals(button)) {
                    //Comfirm
                    String[] listQuantity = request.getParameterValues("txtQuantity");
                    String[] listDateRental = request.getParameterValues("dateRental");
                    String[] listDateReturn = request.getParameterValues("dateReturn");

                    for (int i = 0; i < listCarID.length; i++) {
                        cart.updateCart(listCarID[i], Integer.parseInt(listQuantity[i]));
                        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateRental = sdf.parse(listDateRental[i]);
                        Date dateReturn = sdf.parse(listDateReturn[i]);
                        cart.dateRentalCart(listCarID[i], dateRental, dateReturn);
//                        List<DiscountDTO> listDiscount = (List<DiscountDTO>) session.getAttribute("DISCOUNT");
//                        request.setAttribute("DISCOUNT", listDiscount);
                    }
                } else if (checkoutButton.equals(button)) {
                    //Check Out
                    String uniqueID = UUID.randomUUID().toString();
                    OrdersDAO orders = new OrdersDAO();
                    String username = user.getUsername();
                    String orderID;
                    String lastOrderID = orders.getLastOderByUser(username);
                    if (lastOrderID == null) {
                        orderID = "OD-" + username + "-" + uniqueID;
                    } else {
                        orderID = "OD-" + username + "-" + uniqueID;
                    }
                    String discountID = request.getParameter("txtDiscountID");
                    if (discountID == null) {
                        discountID = "";
                    }

                    float total = cart.getTotal();
                    float finalTotal = total;
                    //Take Discount
                    boolean emptyDiscount = discountID.trim().isEmpty();
                    if (!emptyDiscount) {
                        DiscountDAO discountDAO = new DiscountDAO();
                        boolean isExprityDay = discountDAO.checkExpiryDate(discountID);
                        if (isExprityDay) {
                            List<DiscountDTO> discount = discountDAO.getListDiscountByUsername(username, discountID);
                            for (DiscountDTO discountDTO : discount) {
                                float percent = discountDTO.getDiscountPercent();
                                finalTotal = total - (total * (percent / 100));
                            }
                            discountDAO.updateDiscount(discountID);
                        } else {
                            request.setAttribute("CANNOTUSEDISCOUNT", isExprityDay);
                        }
                    }

                    OrdersDAO ordersDAO = new OrdersDAO();
                    OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
                    DiscountDAO discountDAO = new DiscountDAO();
                    boolean createOrder = ordersDAO.createOrder(orderID, username, finalTotal, new Date());
                    if (createOrder) {
                        int count = 0;
                        for (CarsDTO dto : cart.getCart().values()) {
                            String carID = dto.getCarID();
                            float carPrice = dto.getPrice();
                            int quantityInCart = dto.getQuantity();
                            String orderDetailsID = orderID + "-" + count++;
                            int quantityRemaningInDataBase = cars.quantityRemaningDB(carID);
                            //Thanh toan Fail
                            if (quantityInCart > quantityRemaningInDataBase) {
                                boolean updateOrder = ordersDAO.updateOrderStatus(orderID, false);
                                if (updateOrder) {
                                    url = file.get("outStock");
                                }
                                return;
                            } else {
                                //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                OrderDetailsDTO dtoDetails = new OrderDetailsDTO(orderDetailsID, orderID, quantityInCart, carPrice, dto.getCarID());
                                boolean createOrderDetails = orderDetailsDAO.createOrder(dtoDetails, dto.getDayRental(), dto.getDayReturn());
                                if (createOrderDetails) {
                                    boolean updateDiscount = discountDAO.updateDiscount(discountID);
                                    if (updateDiscount) {

                                    } else {
                                    }
                                    ordersDAO.updateQuantity(dto.getCarID(), quantityRemaningInDataBase, quantityInCart);
                                    session.removeAttribute("CUSTOMERCART");
                                    url = file.get("userGoShopping");
                                }
                            }
                        }
                    }
                } else if (discountButton.equals(button)) {
                    //Discount
                    String discountID = request.getParameter("txtDiscount");
                    boolean isEmptyDiscount = discountID.trim().isEmpty();
                    if (!isEmptyDiscount) {
                        DiscountDAO discount = new DiscountDAO();
                        boolean checkExpirity = discount.checkExpiryDate(discountID);
                        if (checkExpirity) {
                            String username = user.getUsername();
                            List<DiscountDTO> listDiscount = discount.getListDiscountByUsername(username, discountID);
                            session.setAttribute("DISCOUNT", listDiscount);
                            for (DiscountDTO discountDTO : listDiscount) {
                                String id = discountDTO.getDiscountID();
                                boolean result = id.equals(discountID);
                                if (result) {
                                    request.setAttribute("USEDISCOUNT", result);
                                }
                            }
                        } else {
                            session.removeAttribute("DISCOUNT");
                            request.setAttribute("USEDISCOUNT", checkExpirity);
                        }
                    } else {
                        session.removeAttribute("DISCOUNT");
                    }
                }
            }
        } catch (NamingException | SQLException | ParseException ex) {
            BasicConfigurator.configure();
            LOGGER.error("ERROR at UserTakeOverCartServlet: " + ex.getMessage());
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
