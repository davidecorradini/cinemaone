<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<c:set var="InfoPrenotzione" value="${requestScope.infoPrenotazione}"/>
<c:set var="Film" value="${infoPrenotazione.getFilm()}"/>
<c:set var="Spettacolo" value="${infoPrenotazione.getSpettacolo()}"/>
<c:set var="Sala" value="${infoPrenotazione.getSala()}"/>
<c:import url="header.jsp"></c:import>
    <script>
        var id_spettacolo = <c:out value="${Spettacolo.getIdSpettacolo()}"></c:out>;
        var prezzi = new Array();
        <c:forEach var="tmp20" items="${requestScope.prezzi}">
        prezzi[<c:out value="${tmp20.getIdPrezzo()}"></c:out>] = new Array("<c:out value="${tmp20.getTipo()}"></c:out>", "<fmt:formatNumber value="${tmp20.getPrezzo()}" type="currency" currencySymbol="&euro;" />");
        </c:forEach>
    </script>
    <div class="container">
        <div class="page-header">
            <h1>Prenotazione</h1>
        <span class="subtitle"><strong><c:out value="${Film.getTitolo()}"></c:out></strong> &middot; <fmt:formatDate value="${Spettacolo.getTimeStamp()}" pattern="dd-MM-yyyy hh:mm"/> &middot; <c:out value="${Sala.getNome()}"></c:out></span>
        </div>
        <div class="row">
            <div class="col-md-8">
                <div class="sala">
                    <c:set var="postiSala" value="${requestScope.postiSala}"/>
                    <c:import url="sala.jsp"></c:import>
                </div>
                <br><br><br>
            </div>
            <div class="col-md-4" id="posti-selezionati">
                <strong>Posti selezionati</strong>
                <div id="no-selected" class="text-muted small">Nessun posto selezionato.</div>
                <div id="posti-selezionati-list"></div>
                <!--<div id="posti-selezionati-list">
                    <div>
                        <div class="progress-bar-light">
                            <div class="progress-bar-dark" style="width:50%;"></div>                                
                        </div>
                        <div class="selezionato-container">
                            <div class="posto-side selezionato">X99</div>
                            <strong>&euro; 8,00</strong> Intero
                            <div class="delete-posto">02:50 <a href="#" id="delete-posto"><i class="zmdi zmdi-close"></i></a></div>
                        </div>
                    </div>
                </div>-->
                <div id="totale-bottone" style="display: none;">
                    TOTALE: <strong id="totale"></strong>
                    <button class="btn btn-primary btn-sm pull-right" id="procedi-button">Procedi</button>
                </div>
            </div>
        </div>
        <br><br><br>
    </div>
    <div class="modal fade" id="prenotazione-modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Prenota Posto <span id="posto-id">(nessun posto selezionato)</span></h4>
                </div>
                <form id="prenota-form">
                    <input type="hidden" id="prenotazione-posto-id">
                    <div class="modal-body">
                        <div class="alert alert-dismissible alert-info" role="alert">Il posto ti verr&agrave; riservato per 5 minuti dalla conferma di prenotazione, entro i quali dovrai effettuare il pagamento!</div>
                        <table class="table">
                            <tr>
                                <th>Posto</th>
                                <th>Tipo</th>
                            </tr>
                            <tr>
                                <td>
                                    <input type="text" class="form-control" id="posto-id-2" disabled="disabled">
                                </td>
                                <td>
                                    <select class="form-control" id="prenotazione-tipo">
                                        <c:forEach var="tmp2" items="${requestScope.prezzi}">
                                            <option value="<c:out value="${tmp2.getIdPrezzo()}"></c:out>"><c:out value="${tmp2.getTipo()}"></c:out> (<fmt:formatNumber value="${tmp2.getPrezzo()}" type="currency" currencySymbol="&euro;" />)</option>
                                        </c:forEach>  
                                    </select>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
                        <button type="submit" class="btn btn-primary">Conferma Prenotazione</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="modal fade" id="pagamento-modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Conferma del pagamento</h4>
                </div>
                <form id="prenota-form">
                    <div class="modal-body">
                        <div class="alert alert-dismissible alert-danger" role="alert">Tempo rimanente per completare il pagamento:</div>
                        <table class="table">
                            <tr>
                                <th>Posto</th>
                                <th>Tipo</th>
                            </tr>
                            <tr>
                                <td>
                                    <input type="text" class="form-control" id="posto-id-2" disabled="disabled">
                                </td>
                                <td>
                                    <select class="form-control" id="prenotazione-tipo">
                                        <c:forEach var="tmp2" items="${requestScope.prezzi}">
                                            <option value="<c:out value="${tmp2.getIdPrezzo()}"></c:out>"><c:out value="${tmp2.getTipo()}"></c:out> (<fmt:formatNumber value="${tmp2.getPrezzo()}" type="currency" currencySymbol="&euro;" />)</option>
                                        </c:forEach>  
                                    </select>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
                        <button type="submit" class="btn btn-primary">Conferma Prenotazione</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
<c:import url="footer.jsp"></c:import>