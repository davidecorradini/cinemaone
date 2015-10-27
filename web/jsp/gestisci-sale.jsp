<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
    
<%@ include file="header-admin.jsp" %>
<div class="container">
    <h1>Gestisci Sale</h1>
    <div>        
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
            <c:set var="first" value="1"></c:set>
            <c:forEach var="tmp" items="${requestScope.sale}">
                <li role="presentation" <c:if test="${first == 1}">class="active"</c:if>><a href="#sala-<c:out value="${tmp.idSala}"></c:out>" aria-controls="home" role="tab" data-toggle="tab"><c:out value="${tmp.nome}"></c:out></a></li>
                <c:set var="first" value="0"></c:set>
            </c:forEach>
        </ul>
        <!-- Tab panes -->
        <div class="tab-content">
            <c:forEach var="i"  begin="0" end="${requestScope.sale.getSize()-1}">
                <div role="tabpanel" class="tab-pane<c:if test="${first == 0}"> active</c:if>" id="sala-<c:out value="${requestScope.sale[i].idSala}"></c:out>">
                    <c:out value="${requestScope.sale[i].nome}"></c:out>
                    <c:set var="postiSala" value="${requestScope.postiSale[i]}"/>
                    <c:import url="sala.jsp"></c:import>
                </div>
                <c:set var="first" value="1"></c:set>
            </c:forEach>
        </div>
    </div>
</div>


<%@ include file="footer.jsp" %>