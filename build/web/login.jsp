<%-- 
    Document   : login
    Created on : Feb 20, 2021, 3:43:39 PM
    Author     : WIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css">
        <script src="https://www.google.com/recaptcha/api.js?hl=vi" async defer></script>
        <script type="text/javascript">
            var onloadCallback = function () {
                grecaptcha.render('html_element', {
                    'sitekey': '6LfCkmgaAAAAAL9Ua1WZ6KS7FHSSopkhU5f_g1-W'
                });
            };
        </script>
        <title>Login Page</title>
    </head>
    <body>
        <h1 class="text-center">Login Page</h1>
        <div style=" width: 360px; margin-left: auto;margin-right: auto; position: relative">
            <form action="login" method="POST" >
                <div class="mb-3">
                    <label class="form-label">Username</label>
                    <input type="text" name="txtUsername" value="" class="form-control"/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Password</label>
                    <input type="password" name="txtPassword" class="form-control" placeholder="Password">
                </div>
                <c:if test="${requestScope.CHECKBOX == false}">
                    <font color="red">
                        Please check if you not a robot
                    </font>  
                </c:if>
                <div id="html_element" class="g-recaptcha" data-sitekey="6LfCkmgaAAAAAL9Ua1WZ6KS7FHSSopkhU5f_g1-W"></div>
                <input type="submit" value="Login" class="btn btn-primary"/>
                <a href="createNewAccountJSP" class="btn btn-primary">Sign Up</a>
            </form>
        </div>
    </body>
</html>
