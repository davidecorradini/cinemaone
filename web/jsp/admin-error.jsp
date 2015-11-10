<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:choose>
    <c:when test="${error == 'non disponi dei permessi necessari'}">
        <c:import url="header-admin2.jsp"></c:import>  
    </c:when>
    <c:otherwise>
        <c:import url="header-admin.jsp"></c:import>  
    </c:otherwise>
</c:choose>
<div class="container">
    <div class="page-header">
        <h1>Error</h1>
    </div>
    <div style="color: #FF0000">${error}</div>
    <br><br><br><br><br>
</div>        

<c:choose>
    <c:when test="${error == 'non disponi dei permessi necessari'}">
          <c:import url="footer-admin2.jsp"></c:import>
    </c:when>
    <c:otherwise>
          <c:import url="footer-admin.jsp"></c:import>
    </c:otherwise>
</c:choose>