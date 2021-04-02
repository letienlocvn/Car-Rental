<%-- 
    Document   : verifyAccount
    Created on : Mar 2, 2021, 1:09:38 PM
    Author     : WIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Verify Account</title>
    </head>
    <body>
        <form action="verifyAccount">
            <input type="text" name="txtCode" value="${param.txtCode}"/>
            <input type="submit" value="Submit" name="btAction" />            
        </form>
            <c:if test="${requestScope.USERAUTHENFAIL == false}">
            <font color="red">
            <p>The code verify not match. Please try again</p>
            </font>
        </c:if>
    </body>
</html>
