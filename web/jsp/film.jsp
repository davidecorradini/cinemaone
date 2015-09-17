<%-- 
    Document   : film
    Created on : 17-ago-2015, 20.14.20
    Author     : roberto


<%@page contentType="text/html" pageEncoding="UTF-8"%>
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
                <h1>Film in programma</h1>
            </div>
            
            
            <c:forEach var="tmp" items="${requestScope.film}">
                <c:set var="tmpfilm" value="${tmp.getFilm()}"/>
                <c:set var="tmpgenere" value="${tmp.getGenere()}"/>
                <div class="media">
                    <div class="media-left">
                        <a href="dettaglio-film.html?idfilm=<c:out value="${tmpfilm.getIdFilm()}"></c:out>"><img class="media-object thumbnail" src="img/movie/<c:out value="${tmpfilm.getUriLocandina()}"></c:out>" style="height: 200px"></a>
                        </div>
                        <div class="media-body">
                                <h3 class="media-heading"><a href="dettaglio-film.html?idfilm=<c:out value="${tmpfilm.getIdFilm()}"></c:out>"><c:out value="${tmpfilm.getTitolo()}"></c:out></a> <small> <c:out value="${tmpfilm.getRegista()}"></c:out> &middot; <c:out value="${tmpfilm.getAnno()}"></c:out> &middot; <c:out value="${tmpgenere.getDescrizione()}"></c:out> &middot; <c:out value="${tmpfilm.getDurata()}"></c:out></small></h3>
                        <c:set var="string" value="${tmpfilm.getTrama()}"/>
                        <c:set var="index" value="${fn:indexOf(string, \".\")}"/>
                        <c:set var="string1" value="${fn:substring(string, 0, index)}" />
                        <c:set var="indexend" value="${fn:length(string)}"/>
                        <c:set var="string2" value="${fn:substring(string, index+1, indexend)}" />
                        <p><c:out value="${string1}"></c:out><div id="div<c:out value="${tmpfilm.getIdFilm()}"></c:out>" style="visibility: hidden;"><c:out value="${string2}"></c:out></div><a href="#" id="<c:out value="${tmpfilm.getIdFilm()}"></c:out>" onclick="">[mostra tutto]</a></p>
                        
                            <p>Programmazione<ul>
                            <c:forEach var="tmp1" items="${tmp.getSpettacoli()}">
                                <li><fmt:formatDate value="${tmp1.getDataOra()}" pattern="dd-MM-yyyy hh:mm"/>  <a href="prenotazione.html?idspettacolo=<c:out value="${tmp1.getIdSpettacolo()}"></c:out>"><i class="zmdi zmdi-calendar-check"></i> Prenota</a></li>
                            </c:forEach>
                        </ul></p>
                    </div>
                </div>
            </c:forEach>
            
            
            
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
        <script>
            $('a').click(function(){
                $('#div'+this.id).show();
                $('#'+this.id).hide();
            })
        </script>
    </body>
</html>


