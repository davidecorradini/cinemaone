<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/material-design-iconic-font.min.css" rel="stylesheet">
        <link href="css/jquery.bxslider.css" rel="stylesheet">
        <link href="css/cinema.css" rel="stylesheet">
        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
        <title>Cinema One</title>
    </head>
    <body>
        <header>
            <nav class="navbar navbar-default navbar-fixed-top">
                <div class="container">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="index.html"><i class="zmdi zmdi-movie-alt zmdi-right-8"></i>Cinema One</a>
                    </div>
                    <div id="navbar" class="navbar-collapse collapse">
                        <ul class="nav navbar-nav navbar-right">
                            <li<c:if test="${pageContext.request.servletPath == '/jsp/index.jsp'}"> class="active"</c:if>><a href="index.html"><i class="zmdi zmdi-home zmdi-right-8"></i>Home</a></li>
                            <li<c:if test="${pageContext.request.servletPath == '/jsp/film.jsp'}"> class="active"</c:if>><a href="film.html"><i class="zmdi zmdi-movie zmdi-right-8"></i>Film</a></li>
                            <li<c:if test="${pageContext.request.servletPath == '/jsp/spettacoli.jsp'}"> class="active"</c:if>><a href="spettacoli.html"><i class="zmdi zmdi-calendar-check zmdi-right-8"></i>Spettcaoli</a></li>
                            <li class="dropdown <c:if test="${requestScope.loginerror}">open</c:if>">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="zmdi zmdi-account-circle zmdi-right-8"></i><c:choose><c:when test="${sessionScope.autenticato}"><c:out value="${sessionScope.user.getEmail()}"></c:out></c:when><c:otherwise>Area Clienti</c:otherwise></c:choose> <i class="zmdi zmdi-chevron-down"></i></a>
                                <ul class="dropdown-menu">
                                    <c:choose>
                                        <c:when test="${sessionScope.autenticato}">
                                            <li><a href="profile.html"><i class="zmdi zmdi-hc-fw zmdi-settings zmdi-right-8"></i> Impostazioni</a></li>
                                            <c:if test="${requestScope.autenticato == 2}"><li><a href="admin.html"><i class="zmdi zmdi-hc-fw zmdi-flag zmdi-right-8"></i> Amministratore</a></li></c:if>
                                            <li><a href="logout.html?backto="><i class="zmdi zmdi-hc-fw zmdi-logout zmdi-right-8"></i> Logout</a></li>
                                        </c:when>
                                        <c:otherwise>
                                        <li><a href="#" data-toggle="modal" data-target="#login-modal">Accedi</a></li>
                                            <form method="POST" action="/Multisala/check-login"> 
                                                <li>username:</li>
                                                <li><input type="text" name="username" placeholder="username"></li>
                                                <li>password:</li>
                                                <li><input type="password" name="password" placeholder="password"></li>
                                                <li><input type="submit" value="login"></li>
                                                <c:if test="${requestScope.loginerror}"><li>Username e/o password sbagliato/i</li></c:if>
                                                <li><a href="#">Recupera password</a></li>
                                            </form>
                                        </c:otherwise>
                                    </c:choose>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </header>
        <div class="modal fade" id="login-modal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Accesso Clienti</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form">
                            <input type="email" class="form-control" placeholder="Indirizzo e-mail">
                            <input type="password" class="form-control" placeholder="Password">
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Save changes</button>
                    </div>
                </div>
            </div>
        </div>
