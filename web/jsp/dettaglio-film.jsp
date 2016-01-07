  
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="header.jsp"></c:import>
<c:set var="tmp" value="${requestScope.dettaglioFilm}"></c:set>
<c:set var="tmpFilm" value="${tmp.getFilm()}"></c:set>
<c:set var="tmpGenere" value="${tmp.getGenere()}"></c:set>
    <div class="container">
        <div class="page-header">
            <h1><c:out value="${tmpFilm.getTitolo()}"></c:out></h1>
        </div>
        <div class="row">
            <div id="1" class="col-md-3">
                <img class="media-object thumbnail" src="img/movie/<c:out value="${tmpFilm.getUriLocandina()}"></c:out>" style="height: 400px">
            </div>
            <div id="2" class="col-md-9">
                <p><strong>Regista: </strong><c:out value="${tmpFilm.getRegista()}"></c:out></p>
            <p><strong>Genere: </strong><c:out value="${tmpGenere.getDescrizione()}"></c:out></p>
            <p><strong>Durata: </strong><c:out value="${tmpFilm.getDurata()}"></c:out> minuti</p>				  
            <p><strong>Trama: </strong><c:out value="${tmpFilm.getTrama()}"></c:out></p>
                <p><strong>Programmazione: </strong><br>
                <c:forEach var="tmpSpettacolo" items="${tmp.getSpettacoli()}">
                    <fmt:formatDate value="${tmpSpettacolo.getDataOra()}" type="both" dateStyle="long" timeStyle="short"/>  <a href="prenotazione.html?idspettacolo=<c:out value="${tmpSpettacolo.getIdSpettacolo()}"></c:out>"><i class="zmdi zmdi-calendar-check"></i> Prenota</a><br>
                </c:forEach>
            </p>
        </div>
    </div>
    <div class="page-header">
        <h3><a id="link" class="no-color" href="#">Guarda il Trailer</a></h3>
    </div>
</div >
<c:import url="footer.jsp"></c:import>  
    
    
    <div id="myModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                
                <div class="modal-header">
                    <label>Trailer</label>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                </div>
                    
                <div class="modal-body">
                    <iframe width="100%" height="315" frameborder="0" allowfullscreen=""></iframe>
                </div>
            </div>
        </div>
    </div>
        
    <script>
        $('#link').click(function () {
            var src = '<c:out value="${tmpFilm.getUrlTrailer()}"></c:out>';
            $('#myModal').modal('show');
            $('#myModal iframe').attr('src', src);
        });
        
        $('#myModal button').click(function () {
            $('#myModal iframe').removeAttr('src');
        });
</script>