<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="InfoPrenotzione" value="${requestScope.infoPrenotazione}"/>
<c:set var="Film" value="${infoPrenotazione.getFilm()}"/>
<c:set var="Spettacolo" value="${infoPrenotazione.getSpettacolo()}"/>
<c:set var="Sala" value="${infoPrenotazione.getSala()}"/>
<c:import url="header-admin.jsp"></c:import>
<script>
    var id_spettacolo = <c:out value="${Spettacolo.getIdSpettacolo()}"></c:out>;
</script>
    <div class="container">
        <div class="page-header">
            <h1><c:out value="${Film.getTitolo()}"></c:out></h1>
            <span class="subtitle"><strong><fmt:formatDate value="${Spettacolo.getTimeStamp()}" pattern="dd MMMM yyyy HH:mm"/></strong> &middot; <c:out value="${Sala.getNome()}"></c:out></span>
        </div>
        <div class="row">
            <div class="col-md-8">
        <div class="sala">
            <c:set var="postiSala" value="${requestScope.postiSala}"/>
            <c:import url="sala-admin.jsp"></c:import>
        </div>
                <br><br><br>
                </div>
            <div class="col-md-4">
                <strong>Totale biglietti venduti:</strong> <c:out value="${requestScope.infoIncassi[0]}"></c:out>
            <br><br>
                <strong>Totale incasso:</strong> <fmt:formatNumber value="${requestScope.infoIncassi[1]}" type="currency" currencySymbol="&euro;" />
            </div>
        </div>
        <br><br><br>
    </div>
<c:import url="footer-admin.jsp"></c:import> 