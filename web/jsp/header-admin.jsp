<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

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
        <link href="../css/bootstrap-datepicker.min.css" rel="stylesheet">
        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
        
        <script src="../js/jquery-1.11.3.min.js"></script>
        <script src="../js/bootstrap-table-filter.js"></script>
        
       
        <title>Amministratore &middot; Cinema One</title>
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
                            <li<c:if test="${pageContext.request.servletPath == '/jsp/admin-index.jsp'}"> class="active"</c:if>><a href="index.html"><i class="zmdi zmdi-home zmdi-right-8"></i>Home</a></li>
                            <li<c:if test="${pageContext.request.servletPath == '/jsp/incasso-film.jsp'}"> class="active"</c:if>><a href="incassi-film.html"><i class="zmdi zmdi-movie zmdi-right-8"></i>Incassi Film</a></li>
                            <li<c:if test="${pageContext.request.servletPath == '/jsp/clienti-top.jsp'}"> class="active"</c:if>><a href="clienti-top.html"><i class="zmdi zmdi-account-circle zmdi-right-8"></i>Clienti Top</a></li>
                            <li<c:if test="${pageContext.request.servletPath == '/jsp/admin-spettacoli.jsp' || pageContext.request.servletPath == '/jsp/dettaglio-spettacolo.jsp'}"> class="active"</c:if>><a href="spettacoli.html"><i class="zmdi zmdi-videocam zmdi-right-8"></i>Spettacoli</a></li>
                            <li<c:if test="${pageContext.request.servletPath == '/jsp/gestione-prenotazioni.jsp'}"> class="active"</c:if>><a href="prenotazioni.html"><i class="zmdi zmdi-assignment-check zmdi-right-8"></i>Prenotazioni</a></li>
                            <li<c:if test="${pageContext.request.servletPath == '/jsp/gestisci-sale.jsp'}"> class="active"</c:if>><a href="sale.html"><i class="zmdi zmdi-view-comfy zmdi-right-8"></i>Sale</a></li>
                            <li><a href="../index.html"><i class="zmdi zmdi-close zmdi-right-8"></i>Esci</a></li>
                        </ul>
                    </div>
                </div>
            </nav>
        </header>