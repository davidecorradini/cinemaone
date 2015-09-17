<%-- 
    Document   : index
    Created on : Aug 21, 2015, 12:38:33 PM
    Author     : enrico
--%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
                        <a class="navbar-brand" href="#"><i class="zmdi zmdi-movie-alt zmdi-right-8"></i>Cinema One</a>
                    </div>
                    <div id="navbar" class="navbar-collapse collapse">
                        <ul class="nav navbar-nav navbar-right">
                            <li class="active"><a href="index.html"><i class="zmdi zmdi-home zmdi-right-8"></i>Home</a></li>
                            <li><a href="film.html"><i class="zmdi zmdi-movie zmdi-right-8"></i>Film</a></li>
                            <li><a href="spettacoli.html"><i class="zmdi zmdi-calendar-check zmdi-right-8"></i>Spettcaoli</a></li>
                            
                            <li class="dropdown <c:if test="${requestScope.login-error == 't'}">open</c:if>">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="zmdi zmdi-account-circle zmdi-right-8"></i>Area Clienti <i class="zmdi zmdi-chevron-down"></i></a>
                                <ul class="dropdown-menu">
                                    <form method="POST" action="/Multisala/check-login"> 
                                        <li>username:</li>
                                        <li><input type="text" name="username" placeholder="username"></li>
                                        <li>password:</li>
                                        <li><input type="password" name="password" placeholder="password"></li>
                                        <li><input type="submit" value="login"></li>
                                    <c:if test="${requestScope.login-error == 't'}"><li>Username e/o password sbagliato/i</li></c:if>
                                        <li><a href="#">Recupera password</a></li>
                                    </form>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </header>
            
        <ul class="bxslider">
            <c:forEach var="tmp" items="${requestScope.filmsSlider}"> 
                <li>
                    <a href="dettaglio-film.html?idfilm=<c:out value="${tmp.getIdFilm()}"></c:out>">
                        <div class="cover" style="background-image:url(img/slider/<c:out value="${tmp.getUriLocandina()}"></c:out>);">
                                <div class="cover-text">
                                    <div class="container">
                                        <h1><c:out value="${tmp.getTitolo()}"></c:out> <small><c:out value="${tmp.getRegista()}"></c:out> &middot; <c:out value="${tmp.getAnno()}"></c:out> &middot; <c:out value="${tmp.getDurata()}"></c:out>min</small></h1>
                                </div>
                            </div>
                        </div>
                    </a>
                </li>
            </c:forEach>
        </ul>
        <div class="container">
            <div class="row">
                <div class="col-md-8">
                    <p><strong>Cinema One S.r.l.</strong> offre ai propri clienti</p>
                </div>
                <div class="col-md-4">
                    <div class="panel panel-default">
                        <div class="panel-heading">Listino prezzi</div>
                        <div class="panel-body">
                            <ul>
                                
                                <c:forEach var="tmp2" items="${requestScope.prezzi}">
                                    
<li><strong><c:out value="${tmp2.getTipo()}"></c:out></strong> <c:out value="${tmp2.getPrezzo()}"></c:out></li>
                                    
                                        
                                    </c:forEach>
                                
                            </ul>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading">Dove siamo?</div>
                        <div class="panel-body">
                            <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script><div style="overflow:hidden;height:200px;width:100%;"><div id="gmap_canvas" style="height:200px;width:100%;"></div><style>#gmap_canvas img{max-width:none!important;background:none!important}</style><a class="google-map-code" href="http://www.themecircle.net" id="get-map-data">themecircle.net</a></div><script type="text/javascript"> function init_map(){var myOptions = {zoom:15,center:new google.maps.LatLng(46.35699,11.02963299999999),mapTypeId: google.maps.MapTypeId.ROADMAP};map = new google.maps.Map(document.getElementById("gmap_canvas"), myOptions);marker = new google.maps.Marker({map: map,position: new google.maps.LatLng(46.35699, 11.02963299999999)});infowindow = new google.maps.InfoWindow({content:"<b>Cinema One</b><br/>Viale A. De Gasperi 95<br/>38023 Cles" });google.maps.event.addListener(marker, "click", function(){infowindow.open(map,marker);});}google.maps.event.addDomListener(window, 'load', init_map);</script>
                        </div>
                    </div>
                </div>
            </div>        
        </div>
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
        <script src="js/jquery.bxslider.min.js"></script>
        <script src="js/cinema.js"></script>
    </body>
</html>