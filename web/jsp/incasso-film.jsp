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
        <h1>Spettacoli</h1>
    </div>
    <table class="table table-bordered table-striped">
        <tr class="active">
            <th>Film</th>
            <th class="text-center">Film</th>
            <th class="text-center">IncassoTotale</th>
            <th class="text-center">Numero Spettacoli</th>
            <th class="text-center">Incasso medio</th>
        </tr>
    </table>
</div>
<%@ include file="footer.jsp" %>