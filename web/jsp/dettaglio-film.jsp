<%-- 
    Document   : dettaglio-film
    Created on : Aug 21, 2015, 12:41:38 PM
    Author     : enrico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <link href="/jsp/css/bootstrap.min.css" rel="stylesheet">
        <link href="/jsp/css/material-design-iconic-font.min.css" rel="stylesheet">
        <link href="/jsp/css/cinema.css" rel="stylesheet">
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
                <h1>I Fantastici 4</h1>
            </div>
            <div class="row">
                <div id="1" class="col-md-3">
                    <img class="media-object thumbnail" src="img/movie/locandina_i-fantastici-4.jpg" alt="i-fantastici-4" style="height: 400px">
                </div>
                <div id="2" class="col-md-9">
                    <p><strong>Regista: </strong>Tim Story</p>
				<p><strong>Genere: </strong>fantasy</p>
                    <p><strong>Durata: </strong>135 minuti</p>				  
			<p><strong>Trama: </strong>Quattro amici (Reed Richards, i fratelli Susan e Johnny Storm e Ben Grimm) insieme al dottor Victor von Doom partono per una missione spaziale a scopo scientifico. Una volta in orbita per&ograve;, a causa di un imprevisto, una nube di raggi cosmici li investe modificando il loro DNA, un evento da cui acquisiranno dei poteri sovrumani. Reed Richards scoprirà che potrà allungare ogni parte del suo corpo a dismisura, Susan Storm può diventare invisibile e generare campi di forza, Johnny può incendiarsi e volare mentre Ben si è tramutato in una grossa "Cosa" dalla pelle di pietra e dalla forza straordinaria. Anche Von Doom è stato mutato dai raggi cosmici, che gli hanno conferito dei poteri elettroconduttori e una pelle metallica. Quest'ultimo, che è un avido imprenditore, cerca di usare i suoi poteri per i propri fini, arrivando a minacciare New York e l'intera umanità per soggiogarla ed impadronirsi della terra, diventato così il supercriminale Dottor Destino. Reed, Sue, Johnny e Ben, divenuti nel frattempo popolari supereroi con il nome di Fantastici Quattro, lo sconfiggeranno dopo una dura battaglia.</p>
			<p><strong>Programmazione: </strong><br>03 luglio 2015: <a href="prenotazione.html">17.00</a> - <a href="prenotazione.html">20.00</a> - <a href="prenotazione.html">23.00</a><br>04 luglio 2015: <a href="prenotazione.html">17.00</a> - <a href="prenotazione.html">20.00</a> - <a href="prenotazione.html">23.00</a><br>05 luglio 2015: <a href="prenotazione.html">17.00</a> - <a href="prenotazione.html">20.00</a> - <a href="prenotazione.html">23.00</a></p> 
                </div>
            </div>
 
<p><h4><strong>Trailer</strong></h4></p>
           <iframe width="560" height="315" src="https://www.youtube.com/embed/yg_d-Ubt7D8" frameborder="0" allowfullscreen></iframe>
        
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
        <script src="/jsp/js/jquery-1.11.3.min.js"></script>
        <script src="/jsp/js/bootstrap.min.js"></script>
        <script src="/jsp/js/jquery.jcarousel.min.js"></script>
    </body>
</html>