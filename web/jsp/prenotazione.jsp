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
        var mainTimer = <c:out value="${requestScope.mainTimer}"></c:out>;
        function sformat(sec) {
            var d = Math.floor(sec / 60 / 60 / 24);
            var h = Math.floor(sec / 60 / 60) % 24;
            var m = Math.floor(sec / 60) % 60;
            var s = sec % 60;
            var hh = h;
            if (hh < 10) {
                hh = "0" + hh;
            }
            var mm = m;
            if (mm < 10) {
                mm = "0" + mm;
            }
            var ss = s;
            if (ss < 10) {
                ss = "0" + ss;
            }
            return d + "g " + hh + "h " + mm + "m " + ss + "s";
        }
        
        setInterval(function () {
            $("#main-timer").text(sformat(mainTimer));            
            if (mainTimer == 0) {
                window.location.reload();
            }
            mainTimer--;
        }, 1000);
    </script>
    <div class="container">
        <div class="page-header">
            <h1>Prenotazione</h1>
            <span class="subtitle"><strong><c:out value="${Film.getTitolo()}"></c:out></strong> &middot; <fmt:formatDate value="${Spettacolo.getTimeStamp()}" pattern="dd-MM-yyyy HH:mm"/> &middot; <c:out value="${Sala.getNome()}"></c:out></span>
            <div class="pull-right"><strong>Il film inizia tra </strong><span id="main-timer"></span></div>
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
                <br><br>
                <div>
                    <span><strong>Legenda</strong></span>
                    <div class="legenda-container">
                        <button class="rotto libero"></button><span class="legenda-v-align">Posto disponibile</span>
                    </div>
                    <div class="legenda-container">
                        <button class="rotto occupato"></button><span class="legenda-v-align">Posto occupato/non disponibile</span>
                    </div>
                    <div class="legenda-container">
                        <button class="rotto occupato-tmp"></button><span class="legenda-v-align">Posto occupato temporaneamente</span>
                    </div>
                    <div class="legenda-container">
                        <button class="rotto tuo"></button><span class="legenda-v-align">Posto prenotato</span>
                    </div>
                    <div class="legenda-container">
                        <button class="rotto tuo-tmp"></button><span class="legenda-v-align">Posto prenotato in attesa di conferma</span>
                    </div>
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
                    <span class="text-danger" id="prenotazione-error" style="display: none;">Errore non specificato, riprova pi&ugrave; tardi.</span>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
                    <button type="submit" class="btn btn-primary" id="conferma-prenotazione-button">Conferma Prenotazione</button>
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
            <form id="paga-form" action="conferma-prenotazione.html" method="post">
                <input type="hidden" name="spettacolo" value="<c:out value="${Spettacolo.getIdSpettacolo()}"></c:out>">
                    <div class="modal-body">
                        <div class="alert alert-dismissible alert-danger" role="alert">Tempo rimanente per completare il pagamento: <strong id="payment-timer"></strong></div>
                        <div id="riepilogo">
                            <table class="table table-striped">
                                <tr>
                                    <td>Totale biglietti</td>
                                    <td><strong id="totale-modal"></strong></td>
                                </tr>
                                <tr>
                                    <td>Credito</td>
                                    <td><strong id="credito-modal" class="strong"></strong></td>
                                </tr>
                                <tr>
                                    <td>Totale da addebitare su carta</td>
                                    <td><strong id="addebito-modal" class="strong"></strong></td>
                                </tr>
                            </table>
                        </div>
                        <div id="form-carta" style="display:none;">
                            <div class="row">
                                <div class="col-md-6"><input type="text" class="form-control req" name="nome" placeholder="Nome"></div>
                                <div class="col-md-6"><input type="text" class="form-control req" name="cognome" placeholder="Cognome"></div>
                            </div>
                            <div class="row" style="margin-top: 8px;">
                                <div class="col-md-6"><input type="text" class="form-control req" placeholder="Numero di Carta di Credito"></div>
                                <div class="col-md-4"><input type="text" class="form-control req" name="scadenza" placeholder="Scadenza"></div>
                                <div class="col-md-2"><input type="text" class="form-control req" name="ccv" placeholder="CCV"></div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
                        <button type="button" class="btn btn-primary" id="paga-next">Paga con Carta di Credito</button>
                        <button type="submit" class="btn btn-primary" id="paga-button" style="display: none;">Conferma Pagamento</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
<c:import url="footer.jsp"></c:import>