<%-- 
    Document   : incasso-film
    Created on : 1-ott-2015, 11.50.53
    Author     : alessandro
--%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
    
<%@ include file="header-admin.jsp" %>
<div class="container">
    <div class="page-header">
        <h1>Incassi per film</h1>
    </div>
    <table class="table table-bordered table-striped">
        <tr class="active">
            <th>Film</th>
            <th class="text-center">IncassoTotale</th>
            <th class="text-center">Numero Spettacoli</th>
            <th class="text-center">Incasso medio</th>
        </tr>
        <c:forEach var="tmp" items="${requestScope.incassoFilm}">
            <c:set var="tmpFilm" value="${tmp.getFilm()}"/>
            <tr>
                <td><a class="no-color" href="dettaglio-film.html?idfilm=<c:out value="${tmpFilm.getIdFilm()}"></c:out>"><c:out value="${tmpFilm.getTitolo()}"></c:out></a> <small class="text-muted"><c:out value="${tmpFilm.getRegista()}"></c:out> &middot; <c:out value="${tmpFilm.getAnno()}"></c:out> &middot; <c:out value="${tmpFilm.getDurata()}"></c:out>min</small></td>
                <td class="text-center"><c:out value="${tmp.getIncasso()}"></c:out></td>
                <td class="text-center"><c:out value="${tmp.getNumSpett()}"></c:out></td>
                <td class="text-center"><c:out value="${tmp.getIncassoMedio()}"></c:out></td>
            </tr>
        </c:forEach>
    </table>
</div>
<%@ include file="footer.jsp" %>