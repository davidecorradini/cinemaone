
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="header.jsp"></c:import>
    <div class="container">
        <div class="page-header">
            <h1>Lista prenotazioni</h1>
        </div>
        <div class="table-responsive">
            <table class="table table-bordered table-striped">
                <tr class="active">
                    <th>Film</th>
                    <th class="text-center">Sala</th>
                    <th class="text-center">Data spettacolo</th>
                    <th class="text-center">Data operazione</th>
                    <th class="text-center">Biglietti comprati</th>
                    <th class="text-center">Totale speso</th>
                </tr>
            <c:forEach var="tmp" items="${requestScope.infoPrenotazioniUtente}">
                <c:set var="tmpFilm" value="${tmp.film}"/>
                <tr>
                    <td><a class="no-color" href="dettaglio-film.html?idfilm=<c:out value="${tmpFilm.idFilm}"></c:out>"><c:out value="${tmpFilm.titolo}"></c:out></a> <small class="text-muted hidden-xs"><c:out value="${tmpFilm.regista}"></c:out> &middot; <c:out value="${tmpFilm.anno}"></c:out> &middot; <c:out value="${tmpFilm.durata}"></c:out>min</small></td>
                    <td class="text-center"><c:out value="${tmp.nomeSala}"></c:out></td>
                    <td class="text-center"><fmt:formatDate value="${tmp.dataOraSpettacolo}" type="both" dateStyle="long" timeStyle="short"/></td>
                    <td class="text-center"><fmt:formatDate value="${tmp.dataOraOperazione}" type="both" dateStyle="long" timeStyle="short"/></td>
                    <td class="text-center"><c:out value="${tmp.numBiglietti}"></c:out></td>
                    <td class="text-center"><fmt:formatNumber value="${tmp.tot}" type="currency" currencySymbol="&euro;" /></td>
                </tr>
            </c:forEach>
            </table>
        </div>
    </div>
<c:import url="footer.jsp"></c:import> 