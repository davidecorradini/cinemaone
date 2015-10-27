<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
    
<%@ include file="header-admin.jsp" %>
<div class="container">
    <h1>Gestisci Sale</h1>
    <div>        
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
            
            <c:forEach var="tmp" items="${requestScope.sale}">
                <li role="presentation" ><a href="#sala-<c:out value="${tmp.getIdSala()}"></c:out>" aria-controls="home" role="tab" data-toggle="tab"><c:out value="${tmp.getNome()}"></c:out></a></li>
                
            </c:forEach>
        </ul>
        <!-- Tab panes -->
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="home">...</div>
            <div role="tabpanel" class="tab-pane" id="profile">...</div>
            <div role="tabpanel" class="tab-pane" id="messages">...</div>
            <div role="tabpanel" class="tab-pane" id="settings">...</div>
        </div>
    </div>
</div>


<%@ include file="footer.jsp" %>