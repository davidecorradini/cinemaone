
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:import url="header.jsp"></c:import>
<c:choose>
    <c:when test="${requestScope.valida}">
        
    </c:when>
    <c:otherwise>
        
    </c:otherwise>
</c:choose>
<c:import url="footer.jsp"></c:import>