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
        <h1>Lista clienti top</h1>
    </div>
    <table class="table table-bordered table-striped">
        <tr class="active">
            <th>Utente</th>
            <th class="text-center">Numero Prenotazioni</th>
            <th class="text-center">Totale spesa</th>
        </tr>
        <c:forEach var="tmp" items="${requestScope.utenti}">
            <c:set var="tmpUt" value="${tmp.getUt()}"/>
            <tr>
                <td><c:out value="${tmpUt.getEmail()}"></c:out></td>
                <td class="text-center"><c:out value="${tmp.getNumPrenotazioni()}"></c:out></td>
                <td class="text-center"><c:out value="${tmp.getSpesaTot()}"></c:out></td>
            </tr>
        </c:forEach>
    </table>
</div>
<%@ include file="footer.jsp" %>