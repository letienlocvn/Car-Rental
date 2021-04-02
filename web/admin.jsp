<%-- 
    Document   : admin
    Created on : Feb 25, 2021, 3:58:08 PM
    Author     : WIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Car Management Page</title>
        <link rel="stylesheet" href="asset/css/main.css">
        <link rel="stylesheet" href="asset/images">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css">
    </head>
    <body>
        <form action="teacherSearch">
            <nav class="navbar navbar-dark sticky-top bg-primary flex-md-nowrap p-0">
                <a class="navbar-brand col-sm-3 col-md-2 mr-0">${sessionScope.USER.fullName}</a>

                <input class="form-control form-control-dark w-50" type="text" placeholder="Search" aria-label="Search" name="txtSearch" value="${param.txtSearch}">
                <ul class="navbar-nav px-3">
                    <li class="nav-item text-nowrap">
                        <input class="btn btn-secondary" type="submit" value="Search" />
                    </li>
                </ul>
            </nav>
        </form>

        <div class="container-fluid">
            <div class="row">
                <nav class="col-md-2 d-none d-md-block bg-light sidebar">
                    <div class="sidebar-sticky">
                        <ul class="nav flex-column">
                            <li class="nav-item">
                                <a class="nav-link active" href="logout">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-home"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path><polyline points="9 22 9 12 15 12 15 22"></polyline></svg>
                                    Logout <span class="sr-only">(current)</span>
                                </a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link" href="teacherCreateQuestionJSP">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file"><path d="M13 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V9z"></path><polyline points="13 2 13 9 20 9"></polyline></svg>
                                    Create Question
                                </a>
                            </li>                     
                        </ul>
                    </div>
                </nav>
                <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
                    <h2>Dashboard</h2>
                    <hr/>
                    <div class="table-responsive">
                        <table class="table table-striped table-sm table-bordered table-hover">
                            <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Image</th>
                                    <th>Car Name</th>
                                    <th>Category Name</th>
                                    <th>Price</th>
                                    <th>Quantity</th>
                                    <th>Status</th>
                                    <th>Date Of Create</th>
                                    <th>Description</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="cars" items="${sessionScope.LISTCARS}" varStatus="counter">

                                <form action="cars">
                                    <tr>
                                        <td>${counter.count}</td>
                                        <td>
                                            <img class="img img-thumbnail" src="asset/images/${cars.images}"alt="Cars"
                                                 width="450" height="450">
                                        </td>
                                        <td>${cars.carName}</td>
                                        <td>${cars.categoriesDTO.categoryName}</td>
                                        <td>${cars.price}</td>
                                        <td>${cars.quantity}</td>
                                        <td>${cars.status}</td>
                                        <td>${cars.dateOfCreate}</td>
                                        <td>${cars.description}</td>
                                    </tr>
                                </form>
                            </c:forEach>
                            </tbody>
                        </table>
                        <ul id="pagination"></ul>
                        <!-- Paging-->
                        <%--
                        <div class="row" >
                            <div class="col-sm-1" style="float: right">
                                <ul class="pagination">
                                    <c:forEach var="sizeADMIN" begin="1" end="${requestScope.SIZEPAGEADMIN}">
                                        <li class="page-item">
                                            <c:url var="ManagementQuestion" 
                                                   value="teacher?pageIndex=${sizeADMIN}"/>
                                            <a class="page-link" 
                                               href="${ManagementQuestion}">${sizeADMIN}</a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                        --%>
                </main>
            </div>
        </div>
    </body>
    <script type="text/javascript">
    </script>
</html>
