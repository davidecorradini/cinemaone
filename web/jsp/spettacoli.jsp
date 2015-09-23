<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:import url="header.jsp"></c:import>
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
                <td><a class="no-color" href="dettaglio-film.html?idfilm=<c:out value="${tmpFilm.getIdFilm()}"></c:out>"><c:out value="${tmpFilm.getTitolo()}"></c:out></a> <small class="text-muted"><c:out value="${tmpFilm.getRegista()}"></c:out> &middot; <c:out value="${tmpFilm.getAnno()}"></c:out> &middot; <c:out value="${tmpGenere.getDescrizione()}"></c:out> &middot; <c:out value="${tmpFilm.getDurata()}"></c:out></small></td>
                <td class="text-center"><c:out value="${tmp.getData()}"></c:out></td>
                <td class="text-center"><c:out value="${tmp.getOra()}"></c:out></td>
                <td class="text-center"><c:out value="${tmpSala.getNome()}"></c:out></td>
                <td class="text-center"><a class="no-color" href="prenotazione.html?idspettacolo=<c:out value="${tmp.getIdSpettacolo()}"></c:out>"><i class="zmdi zmdi-calendar-check"></i></a></td>
            </tr>
        </c:forEach>
                    
    </table>        
</div >
<c:import url="footer.jsp"></c:import> 