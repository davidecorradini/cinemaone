<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
    
<%@ include file="header-admin.jsp" %>
<div class="container">
    <div class="page-header">
        <h1>Gestisci Sale</h1>
    </div>
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
            <br>
            <c:set var="sale" value="${requestScope.sale}"></c:set>
            <c:forEach var="i"  begin="0" end="${sale.size()-1}">
                <div role="tabpanel" class="tab-pane<c:if test="${first == 0}"> active</c:if>" id="sala-<c:out value="${requestScope.sale[i].idSala}"></c:out>">
                    <div class="sala">
                    <c:set var="postiSala" value="${requestScope.postiSale[i]}"/>
                    <c:forEach var="tmp" items="${requestScope.postiSale[i]}">
                        <div class="row">
                            <c:forEach var="i"  begin="0" end="${tmp.getSize()-1}">
                                
                                <button <c:choose>
                                        <c:when test="${tmp.getStato(i) == 0}">
                                            id="posto-<c:out value="${tmp.getIdPosto(i)}"></c:out>" class="posto-admin 
                                            <c:set var="perc" value="${tmp.getPrecentualePrenotazioni(i)}"/>
                                            <c:choose>
                                                <c:when test="${perc < 0.15}">
                                                    gradient-7
                                                </c:when>
                                                <c:when test="${perc >= 0.15 && perc < 0.30}">
                                                    gradient-6
                                                </c:when>
                                                <c:when test="${perc >= 0.30 && perc <0.45}">
                                                    gradient-5
                                                </c:when>
                                                <c:when test="${perc >= 0.45 && perc <0.60}">
                                                    gradient-4
                                                </c:when>
                                                <c:when test="${perc >= 0.60 && perc <0.75}">
                                                    gradient-3
                                                </c:when>
                                                <c:when test="${perc >= 0.75 && perc <0.90}">
                                                    gradient-2
                                                </c:when>
                                                <c:otherwise>
                                                    gradient-1
                                                </c:otherwise>
                                            </c:choose>"
                                        </c:when>
                                        <c:when test="${tmp.getStato(i) == -1}">
                                            id="posto-<c:out value="${tmp.getIdPosto(i)}"></c:out>" class="posto-admin gradient-rotto
                                            <c:set var="perc" value="${tmp.getPrecentualePrenotazioni(i)}"/>
                                            <c:choose>
                                                <c:when test="${perc < 0.15}">
                                                    gradient-7
                                                </c:when>
                                                <c:when test="${perc >= 0.15 && perc < 0.30}">
                                                    gradient-6
                                                </c:when>
                                                <c:when test="${perc >= 0.30 && perc <0.45}">
                                                    gradient-5
                                                </c:when>
                                                <c:when test="${perc >= 0.45 && perc <0.60}">
                                                    gradient-4
                                                </c:when>
                                                <c:when test="${perc >= 0.60 && perc <0.75}">
                                                    gradient-3
                                                </c:when>
                                                <c:when test="${perc >= 0.75 && perc <0.90}">
                                                    gradient-2
                                                </c:when>
                                                <c:otherwise>
                                                    gradient-1
                                                </c:otherwise>
                                            </c:choose>"
                                        </c:when>
                                        <c:otherwise>
                                            class="posto invisibile"
                                        </c:otherwise>
                                    </c:choose> data-toggle="tooltip" data-placement="top"><c:out value="${fn:toUpperCase(tmp.getRiga())}"></c:out><c:if test="${tmp.getColonna(i) < 10}">0</c:if><c:out value="${tmp.getColonna(i)}"></c:out></button>
                            </c:forEach>
                        </div>
                    </c:forEach>
                    </div>
                </div>
                <c:set var="first" value="1"></c:set>
            </c:forEach>
            <br>
        </div>
    </div>
</div>


<%@ include file="footer-admin.jsp" %>