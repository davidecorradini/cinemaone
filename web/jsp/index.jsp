<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="header.jsp"></c:import>
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
<c:import url="footer.jsp"></c:import>         