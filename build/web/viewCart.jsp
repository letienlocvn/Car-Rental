<%-- 
    Document   : viewCart
    Created on : Mar 7, 2021, 2:54:02 PM
    Author     : WIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
        <title>View Cart Page</title>
    </head>
    <body>
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
        <!------ Include the above in your HEAD tag ---------->
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-12 col-md-10 col-md-offset-1">
                    <form action="userTakeOverCart">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>Product</th>
                                    <th>Quantity</th>
                                    <th class="text-center">Price</th>
                                    <th class="text-center">Total</th>
                                    <th>Date Rental</th>
                                    <th>Date Return</th>
                                    <th>Action</th>
                                </tr>
                            </thead>

                            <tbody>
                                <c:forEach var="shopping" items="${sessionScope.CUSTOMERCART.cart.values()}">
                                    <tr>
                                        <td class="col-sm-8 col-md-6">
                                            <div class="media">
                                                <a class="thumbnail pull-left" href="#"> 
                                                    <img class="media-object" src="asset/images/${shopping.images}" style="width: 72px; height: 72px;"> </a>
                                                <div class="media-body">
                                                    <h4 class="media-heading">${shopping.carName}</h4>
                                                    <h5 class="media-heading"> From <a class="btn-link">${shopping.categoriesDTO.categoryName}</a></h5>
                                                    <span>Status: </span>
                                                    <c:if test="${shopping.status == true}">
                                                        <span class="text-success"><strong>In Stock</strong></span>
                                                    </c:if>
                                                    <c:if test="${shopping.status == false}">
                                                        <span class="text-fail"><strong>Out Of Stock</strong></span>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </td>
                                        <td class="col-sm-1 col-md-1" style="text-align: center">
                                            <input type="number" name="txtQuantity" min="0" class="form-control" value="${shopping.quantity}">
                                            <input type="hidden" name="txtCarID" value="${shopping.carID}" />
                                        </td>
                                        <td class="col-sm-1 col-md-1 text-center"><strong>$${shopping.price}</strong></td>
                                        <td class="col-sm-1 col-md-1 text-center"><strong>${shopping.price*shopping.quantity}</strong></td>
                                        <td>

                                            <input id="currentDAY" name="dateRental" class="form-control" type="date" value="${requestScope.DATERENTAL}">
                                        </td>
                                        <td>

                                            <input id="currentDAY" name="dateReturn" class="form-control" type="date" value="${requestScope.DATERETURN}" >

                                        </td>
                                        <td style="text-align: center">
                                            <input type="checkbox" name="chkRemove" value="${shopping.carID}" />
                                        </td>
                                    </tr>
                                </c:forEach>

                                <tr>
                                    <td>  </td>
                                    <td>  </td>
                                    <td>  </td>
                                    <td> <h3>Input Discount</h3> </td>
                                    <td style="padding-top: 30px;">
                                        <input type="text" name="txtDiscount" value="${param.txtDiscount}" minlength="1" maxlength="20" />
                                        <c:if test="${requestScope.USEDISCOUNT == false}">
                                            <h4>
                                                DISCOUNT NOT EXITS. MAY BE Het Hang
                                            </h4>
                                        </c:if>
                                        <c:if test="${requestScope.USEDISCOUNT == true}">
                                            <h4>
                                                SUCCESS
                                            </h4>
                                        </c:if>
                                    </td>
                                    <td style="padding-top: 25px;" >
                                        <button type="submit" class="btn btn-success" value="Use Discount" name="btAction">
                                            <span class="glyphicon glyphicon-ok"></span> Use Discount
                                        </button>
                                    </td>
                                    <td></td>
                                </tr>

                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td><h3>Discount</h3></td>
                                    <td class="text-right">
                                        <h3>
                                            <c:forEach var="discount" items="${sessionScope.DISCOUNT}" begin="">
                                                <c:if test="${not empty discount}">
                                                    ${discount.discountName}                                                    
                                                </c:if>
                                            </c:forEach>
                                        </h3>
                                    </td>
                                    <td></td>
                                    <td></td>
                                </tr>

                                <!-- Total -->

                                <tr>
                                    <td>  </td>
                                    <td> 

                                    </td>
                                    <td>   </td>
                                    <td><h3>Total</h3></td>             
                                    <td class="text-right">
                                        <h3>
                                            <c:set var="totalCars" value="${sessionScope.CUSTOMERCART.total}"/>
                                            <strong><c:out value="$${totalCars}"/></strong><br/>
                                            <strong> - </strong>
                                            <c:forEach var="total" items="${sessionScope.DISCOUNT}">
                                                <input type="hidden" name="txtDiscountID" value="${total.discountID}" />
                                                <c:set var="percent" value="${total.discountPercent / 100}"/>  
                                                <c:if test="${not empty total}">                                                                      
                                                    <strong>${(totalCars * percent)}</strong>
                                                </c:if>
                                                <br/>
                                                <strong>----------</strong><br/>
                                                <c:set var="finalTotal" value="${totalCars - (totalCars * percent)}"/>
                                                <strong>${finalTotal}</strong>
                                            </c:forEach>

                                        </h3>
                                    </td>
                                    <td></td>
                                    <td></td>
                                    <td></td>

                                </tr>


                                <tr>
                                    <td>   </td>
                                    <td>   </td>
                                    <td>   </td>
                                    <td>
                                        <button type="button" class="btn btn-default" >
                                            <span class="glyphicon glyphicon-shopping-cart">
                                                <a href="userGoShopping">Continue Shopping</a>
                                            </span> 
                                        </button>
                                    </td>
                                    <td>
                                        <button id="confirm" onclick="enableCheckOutButton()" type="submit" class="btn btn-success" value="Update Cars" name="btAction">
                                            <span class="glyphicon glyphicon-ok"></span> Confirm
                                        </button>
                                    </td>
                                    <td>
                                        <button Onclick="return ConfirmDelete();" type="submit" class="btn btn-danger" value="Remove Cars" name="btAction">
                                            <span class="glyphicon glyphicon-remove"></span> Remove
                                        </button>
                                    </td>
                                </tr>

                            </tbody>
                        </table>
                        <button id="checkout" type="submit" class="btn btn-info" value="Check Out" name="btAction">
                            Checkout <span class="glyphicon glyphicon-play"></span>
                        </button>
                    </form>

                </div>
            </div>
        </div>

    </body>
    <script>

        function ConfirmDelete() {
            var deleteItem = confirm("Are you sure you want to delete?");
            if (deleteItem)
                return true;
            else
                return false;
        }


        var today = new Date();
        var date = today.getFullYear() + '/' + (today.getMonth() + 1) + '/' + today.getDate();
        document.getElementById("currentDAY").innerHTML = date;

    </script>
</html>
