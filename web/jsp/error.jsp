<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:import url="header.jsp"></c:import>    
    <div class="container">
        <div class="page-header">
            <h1>Error</h1>
        </div>
        <div style="color: #FF0000">${error}</div>
        <br><br><br><br><br>
    </div>        
<c:import url="footer.jsp"></c:import>
