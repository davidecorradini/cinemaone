
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:import url="header.jsp"></c:import>
<c:choose>
    <c:when test="${requestScope.valida}">
        
        <form method="POST" action="/Multisala/resetPassword"> 
            <p>
                Nuova password:
                <input type="text" name="pw1" placeholder="password">
            </p>
            <p>
                Ripeti password:
                <input type="password" name="pw2" placeholder="password">
            </p>
            <p>
                <input type="hidden" name="email" value="${requestScope.email}">
            </p>
            <p>
                <input type="submit">
            </p>
                
        </form>
            
            
    </c:when>
    <c:otherwise>
        <div class="container">
            <div class="page-header">
                <h1>Pagina Scaduta</h1>
            </div>
            Siamo spiacenti, ma la pagina che stai cercando di raggiungere &egrave; scaduta.
            <br><br><br><br><br>
        </div>
    </c:otherwise>
</c:choose>
<c:import url="footer.jsp"></c:import>