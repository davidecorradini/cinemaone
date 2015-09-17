<%-- 
    Document   : spettacoli
    Created on : Aug 21, 2015, 12:39:50 PM
    Author     : enrico
--%>

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
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/material-design-iconic-font.min.css" rel="stylesheet">
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
                    <a class="navbar-brand" href="#"><i class="zmdi zmdi-movie-alt zmdi-right-8"></i>Cinema One</a>
                    </div>
                    <div id="navbar" class="navbar-collapse collapse">
                        <ul class="nav navbar-nav navbar-right">
                            <li class="active"><a href="index.html"><i class="zmdi zmdi-home zmdi-right-8"></i>Home</a></li>
                            <li><a href="film.html"><i class="zmdi zmdi-movie zmdi-right-8"></i>Film</a></li>
                            <li><a href="spettacoli.html"><i class="zmdi zmdi-calendar-check zmdi-right-8"></i>Spettcaoli</a></li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="zmdi zmdi-account-circle zmdi-right-8"></i>Area Clienti <i class="zmdi zmdi-chevron-down"></i></a>
                                <ul class="dropdown-menu">
                                    <li><a href="#">Action</a></li>
                                    <li><a href="#">Another action</a></li>
                                    <li><a href="#">Something else here</a></li>
                                    <li role="separator" class="divider"></li>
                                    <li class="dropdown-header">Nav header</li>
                                    <li><a href="#">Separated link</a></li>
                                    <li><a href="#">One more separated link</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </header>
		<div class="container">
            <div class="page-header">
                <h1>Spettacoli</h1>
            </div>
            <table class="table table-bordered table-striped">
                <tr class="active">
                    <th>Film</th>
                    <th class="text-center">Data</th>
                    <th class="text-center">Orario</th>
                    <th class="text-center">Sala</th>
                    <th class="text-center">Prenota</th>
                </tr>
                <c:forEach var="tmp" items="${requestScope.spettacoli}">
                    <c:set var="tmpFilm" value="${tmp.getFilm()}"/>
                    <c:set var="tmpSala" value="${tmp.getSala()}"/>
                    <c:set var="tmpGenere" value="${tmp.getGenere()}"/>
                    <tr>
                        <td><c:out value="${tmpFilm.getTitolo()}"></c:out> <small class="text-muted"><c:out value="${tmpFilm.getRegista()}"></c:out> &middot; <c:out value="${tmpFilm.getAnno()}"></c:out> &middot; <c:out value="${tmpGenere.getDescrizione()}"></c:out> &middot; <c:out value="${tmpFilm.getDurata()}"></c:out></small></td>
                        <td class="text-center"><c:out value="${tmp.getData()}"></c:out></td>
                        <td class="text-center"><c:out value="${tmp.getOra()}"></c:out></td>
                        <td class="text-center"><c:out value="${tmpSala.getNome()}"></c:out></td>
                        <td class="text-center"><a href="prenotazione.html?idspettacolo=<c:out value="${tmp.getIdSpettacolo()}"></c:out>"><i class="zmdi zmdi-calendar-check"></i></a></td>
                    </tr>
                </c:forEach>
                
            </table>
            <!--
            <table class="table table-striped">
                <tbody>
                    <tr>
                        <td>
                            <h5><strong>LunedÃ¬ 03 agosto 2015</strong></h5>
                            <div class="row">
                                <div class="col-md-3">
                                    <h6><strong>Sala1</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                                <div class="col-md-3">
                                    <h6><strong>Sala2</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                                <div class="col-md-3">
                                    <h6><strong>Sala3</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                                <div class="col-md-3">
                                    <h6><strong>Sala4</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <h5><strong>MartedÃ¬ 04 agosto 2015</strong></h5>
                            <div class="row">
                                <div class="col-md-3">
                                    <h6><strong>Sala1</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                                <div class="col-md-3">
                                    <h6><strong>Sala2</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                                <div class="col-md-3">
                                    <h6><strong>Sala3</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                                <div class="col-md-3">
                                    <h6><strong>Sala4</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>  
                        <td>
                            <h5><strong>MercoledÃ¬ 05 agosto 2015</strong></h5>
                            <div class="row">
                                <div class="col-md-3">
                                    <h6><strong>Sala1</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                                <div class="col-md-3">
                                    <h6><strong>Sala2</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                                <div class="col-md-3">
                                    <h6><strong>Sala3</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                                <div class="col-md-3">
                                    <h6><strong>Sala4</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>     
                        <td>
                            <h5><strong>GiovedÃ¬ 06 agosto 2015</strong></h5>
                            <div class="row">
                                <div class="col-md-3">
                                    <h6><strong>Sala1</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                                <div class="col-md-3">
                                    <h6><strong>Sala2</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                                <div class="col-md-3">
                                    <h6><strong>Sala3</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                                <div class="col-md-3">
                                    <h6><strong>Sala4</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>       
                        <td>
                            <h5><strong>VenerdÃ¬ 07 agosto 2015</strong></h5>
                            <div class="row">
                                <div class="col-md-3">
                                    <h6><strong>Sala1</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                                <div class="col-md-3">
                                    <h6><strong>Sala2</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                                <div class="col-md-3">
                                    <h6><strong>Sala3</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                                <div class="col-md-3">
                                    <h6><strong>Sala4</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>     
                        <td>
                            <h5><strong>Sabato 08 agosto 2015</strong></h5>
                            <div class="row">
                                <div class="col-md-3">
                                    <h6><strong>Sala1</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                                <div class="col-md-3">
                                    <h6><strong>Sala2</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                                <div class="col-md-3">
                                    <h6><strong>Sala3</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                                <div class="col-md-3">
                                    <h6><strong>Sala4</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>     
                        <td>
                            <h5><strong>Domenica 09 agosto 2015</strong></h5>
                            <div class="row">
                                <div class="col-md-3">
                                    <h6><strong>Sala1</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                                <div class="col-md-3">
                                    <h6><strong>Sala2</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                                <div class="col-md-3">
                                    <h6><strong>Sala3</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                                <div class="col-md-3">
                                    <h6><strong>Sala4</strong></h6>
                                    <p>ore 17.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 20.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                    <p>ore 23.00: I FANTASTICI 4 -<a href="prenotazione.html"> Prenota</a></p>
                                </div>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>-->
        
		</div >
		<footer>
            <div class="container">
                <div class="row">
                    <div class="col-md-6">
                        <span class="footer-brand">Cinema One s.r.l.</span><br>
                        <i class="zmdi zmdi-pin zmdi-hc-fw zmdi-right-4"></i>Viale A. Degasperi 95, 38023 Cles TN<br>
                        <i class="zmdi zmdi-email zmdi-hc-fw zmdi-right-4"></i>info@cinemaone.it<br>
                        <i class="zmdi zmdi-phone zmdi-hc-fw zmdi-right-4"></i>+39 347 244 3532
                    </div>
                    <div class="col-md-6">
                        <span class="text-right">Copyright &copy; 2015 Cinema One. Tutti i diritti riservati.</span>
                    </div>
                </div>
            </div>
        </footer>
        <script src="js/jquery-1.11.3.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.jcarousel.min.js"></script>
    </body>
</html>