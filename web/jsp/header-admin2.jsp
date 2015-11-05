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
        <link href="../css/bootstrap.min.css" rel="stylesheet">
        <link href="../css/material-design-iconic-font.min.css" rel="stylesheet">
        <link href="../css/jquery.bxslider.css" rel="stylesheet">
        <link href="../css/cinema.css" rel="stylesheet">
        <link href="../img/favicon.png" rel="shortcut icon"/>
        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
        <script src="../js/jquery-1.11.3.min.js"></script>
        <script src="../js/jquery.bxslider.min.js"></script>
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
                        <a class="navbar-brand" href="../index.html"><i class="zmdi zmdi-movie-alt zmdi-right-8"></i>Cinema One</a>
                    </div>
                    <div id="navbar" class="navbar-collapse collapse">
                        <ul class="nav navbar-nav navbar-right">
                            <li<c:if test="${pageContext.request.servletPath == '/jsp/index.jsp'}"> class="active"</c:if>><a href="../index.html"><i class="zmdi zmdi-home zmdi-right-8"></i>Home</a></li>
                            <li<c:if test="${pageContext.request.servletPath == '/jsp/film.jsp' || pageContext.request.servletPath == '/jsp/dettaglio-film.jsp'}"> class="active"</c:if>><a href="../film.html"><i class="zmdi zmdi-movie zmdi-right-8"></i>Film</a></li>
                            <li<c:if test="${pageContext.request.servletPath == '/jsp/spettacoli.jsp' || pageContext.request.servletPath == '/jsp/prenotazione.jsp'}"> class="active"</c:if>><a href="../spettacoli.html"><i class="zmdi zmdi-calendar-check zmdi-right-8"></i>Spettacoli</a></li>
                            <li class="dropdown" id="menu">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="zmdi zmdi-account-circle zmdi-right-8"></i><c:choose><c:when test="${sessionScope.autenticato != null}"><c:out value="${sessionScope.user.getEmail()}"></c:out></c:when><c:otherwise>Area Clienti</c:otherwise></c:choose> <i class="zmdi zmdi-chevron-down"></i></a>
                                <ul class="dropdown-menu">
                                    <c:choose>
                                        <c:when test="${sessionScope.autenticato != null}">
                                            <li><a href="../profile.html"><i class="zmdi zmdi-hc-fw zmdi-settings zmdi-right-8"></i>Impostazioni</a></li>
                                            <li><a href="../logout.html" id="logout-link"><i class="zmdi zmdi-hc-fw zmdi-close-circle-o zmdi-right-8"></i>Logout</a></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="dropdown-header">Cliente Registrato</li>
                                            <li><a href="#" data-toggle="modal" data-target="#login-modal"><i class="zmdi zmdi-hc-fw zmdi-sign-in zmdi-logout zmdi-right-8"></i>Accedi</a></li>
                                            <li><a href="#" data-toggle="modal" data-target="#recovery-modal"><i class="zmdi zmdi-hc-fw zmdi-key zmdi-logout zmdi-right-8"></i>Recupera Password</a></li>
                                            <li class="divider" role="separator"></li>
                                            <li class="dropdown-header">Nuovo Cliente</li>
                                            <li><a href="#" data-toggle="modal" data-target="#signup-modal"><i class="zmdi zmdi-hc-fw zmdi-account-add zmdi-logout zmdi-right-8"></i>Registrati</a></li>
                                        </c:otherwise>
                                    </c:choose>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </header>