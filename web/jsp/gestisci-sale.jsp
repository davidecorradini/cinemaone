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
            <c:set var="first" value="${requestScope.sala}"></c:set>
            <c:forEach var="tmp" items="${requestScope.sale}">
                <c:if test="${first == null}">
                    <c:set var="first" value="${tmp.idSala}"></c:set>
                </c:if>
                <li role="presentation" <c:if test="${first == tmp.idSala}">class="active"</c:if>><a href="#sala-<c:out value="${tmp.idSala}"></c:out>" aria-controls="home" role="tab" data-toggle="tab"><c:out value="${tmp.nome}"></c:out></a></li>
                </c:forEach>
        </ul>
        <!-- Tab panes -->
        <div class="tab-content">
            <br>
            <c:set var="sale" value="${requestScope.sale}"></c:set>
            <c:forEach var="i"  begin="0" end="${sale.size()-1}">
                <div role="tabpanel" class="tab-pane<c:if test="${first == requestScope.sale[i].idSala}"> active</c:if>" id="sala-<c:out value="${requestScope.sale[i].idSala}"></c:out>">
                        <div class="row">
                            <div class="col-md-8">   
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
                                <br><br><br><br>
                        </div>
                        <div class="col-md-4">             
                            <div>
                                <strong>Legenda</strong><br><br>
                                    <button class="rotto gradient-1"></button>
                                    <button class="rotto gradient-2"></button>
                                    <button class="rotto gradient-3"></button>
                                    <button class="rotto gradient-4"></button>
                                    <button class="rotto gradient-5"></button>
                                    <button class="rotto gradient-6"></button>
                                    <button class="rotto gradient-7"></button>
                                <br><br>
                                
                                <div>MAX <span class="hidden-xs">- - - - - - - - - </span>- - - - - - - - - - - - - MIN</div>
                                <div class="small text-center width-250-responsive">Numero prenotazioni</div>
                                <br><div class="legenda-container">
                                    <button class="rotto gradient-rotto"></button><span class="legenda-v-align">Posto rotto/non disponibile</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                </c:forEach>
                <br>
            </div>
        </div>
    </div>
    <div class="modal fade" id="cambia-stato-modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="sale.html" method="POST">
                    <input type="hidden" name="id-posto" id="cambia-stato-posto-id">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Cambia stato</h4>
                    </div>
                    <div class="modal-body">
                        <table class="table">
                            <tr>
                                <th>Posto</th>
                                <th>Stato</th>
                            </tr>
                            <tr>
                                <td>
                                    <input type="text" class="form-control" id="posto-id-2" disabled="disabled">
                                </td>
                                <td>
                                    <select class="form-control" name="id-stato" id="stato-id">
                                        <option value="-1">Rotto</option>
                                        <option value="0">Aggiustato</option>
                                    </select>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" id="recovery-cancel" data-dismiss="modal">Annulla</button>
                        <button type="submit" class="btn btn-primary">Conferma</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
        
    <%@ include file="footer-admin.jsp" %>