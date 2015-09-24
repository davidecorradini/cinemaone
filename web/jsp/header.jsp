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
                            <li<c:if test="${pageContext.request.servletPath == '/jsp/film.jsp' || pageContext.request.servletPath == '/jsp/dettaglio-film.jsp'}"> class="active"</c:if>><a href="film.html"><i class="zmdi zmdi-movie zmdi-right-8"></i>Film</a></li>
                            <li<c:if test="${pageContext.request.servletPath == '/jsp/spettacoli.jsp' || pageContext.request.servletPath == '/jsp/prenotazione.jsp'}"> class="active"</c:if>><a href="spettacoli.html"><i class="zmdi zmdi-calendar-check zmdi-right-8"></i>Spettcaoli</a></li>
                            <li class="dropdown" id="menu">
                                <%@ include file="menu.jsp" %>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </header>
        <div class="modal fade" id="login-modal">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <form id="login-form">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">Accesso Clienti</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <input type="email" class="form-control" id="input-email" placeholder="Indirizzo e-mail">
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control" id="input-password" placeholder="Password">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
                            <button type="submit" class="btn btn-primary" id="login-button">Accedi</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="modal fade" id="recovery-modal">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <form id="login-form">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">Recupera Password</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="input-email">Indirizzo e-mail</label>
                                <input type="email" class="form-control" id="input-email" placeholder="Indirizzo e-mail">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
                            <button type="submit" class="btn btn-primary" id="login-button">Recupera</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
