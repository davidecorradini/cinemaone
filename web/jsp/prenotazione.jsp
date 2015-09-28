<%-- 
    Document   : prenotazione
    Created on : Aug 21, 2015, 12:40:44 PM
    Author     : enrico
--%>
    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:import url="header.jsp"></c:import>
    <div class="container">
        <div class="page-header">
            <h1>Prenotazione</h1>
        <c:set var="InfoPrenotzione" value="${requestScope.infoPrenotazione}"/>
        <c:set var="Film" value="${infoPrenotazione.getFilm()}"/>
        <c:set var="Spettacolo" value="${infoPrenotazione.getSpettacolo()}"/>
        <c:set var="Sala" value="${infoPrenotazione.getSala()}"/>
        <span class="subtitle"><strong><c:out value="${Film.getTitolo()}"></c:out></strong> &middot; <fmt:formatDate value="${Spettacolo.getTimeStamp()}" pattern="dd-MM-yyyy hh:mm"/> &middot; <c:out value="${Sala.getNome()}"></c:out></span>
        </div>
        <div class="row">
            <div class="col-md-8">
                <div class="sala">
                    <div>
                        <button class="posto libero">A01</button>
                        <button class="posto libero">A02</button>
                        <button class="posto libero">A03</button>
                        <button class="posto libero">A04</button>
                        <button class="posto libero">A05</button>
                        <button class="posto libero">A06</button>
                        <button class="posto invisibile"></button>
                        <button class="posto libero">A07</button>
                        <button class="posto libero">A08</button>
                        <button class="posto libero">A09</button>
                        <button class="posto libero">A10</button>
                        <button class="posto libero">A11</button>
                        <button class="posto libero">A12</button>
                    </div>
                    <div>
                        <button class="posto libero">B01</button>
                        <button class="posto libero">B02</button>
                        <button class="posto libero">B03</button>
                        <button class="posto libero">B04</button>
                        <button class="posto libero">B05</button>
                        <button class="posto libero">B06</button>
                        <button class="posto invisibile"></button>
                        <button class="posto libero">B07</button>
                        <button class="posto libero">B08</button>
                        <button class="posto libero">B09</button>
                        <button class="posto libero">B10</button>
                        <button class="posto libero">B11</button>
                        <button class="posto libero">B12</button>
                    </div>
                    <div>
                        <button class="posto libero">C01</button>
                        <button class="posto libero">C02</button>
                        <button class="posto libero">C03</button>
                        <button class="posto libero">C04</button>
                        <button class="posto libero">C05</button>
                        <button class="posto libero">C06</button>
                        <button class="posto invisibile"></button>
                        <button class="posto libero">C07</button>
                        <button class="posto libero">C08</button>
                        <button class="posto libero">C09</button>
                        <button class="posto libero">C10</button>
                        <button class="posto libero">C11</button>
                        <button class="posto libero">C12</button>
                    </div>
                    <div>
                        <button class="posto libero">D01</button>
                        <button class="posto libero">D02</button>
                        <button class="posto libero">D03</button>
                        <button class="posto libero">D04</button>
                        <button class="posto libero">D05</button>
                        <button class="posto libero">D06</button>
                        <button class="posto invisibile"></button>
                        <button class="posto libero">D07</button>
                        <button class="posto libero">D08</button>
                        <button class="posto libero">D09</button>
                        <button class="posto libero">D10</button>
                        <button class="posto libero">D11</button>
                        <button class="posto libero">D12</button>
                    </div>
                    <div>
                        <button class="posto invisibile"></button>
                        <button class="posto invisibile"></button>
                        <button class="posto invisibile"></button>
                        <button class="posto invisibile"></button>
                        <button class="posto invisibile"></button>
                        <button class="posto invisibile"></button>
                        <button class="posto invisibile"></button>
                        <button class="posto invisibile"></button>
                        <button class="posto invisibile"></button>
                        <button class="posto invisibile"></button>
                        <button class="posto invisibile"></button>
                        <button class="posto invisibile"></button>
                        <button class="posto invisibile"></button>
                    </div>
                    <div>
                        <button class="posto libero">E01</button>
                        <button class="posto libero">E02</button>
                        <button class="posto libero">E03</button>
                        <button class="posto libero">E04</button>
                        <button class="posto libero">E05</button>
                        <button class="posto libero">E06</button>
                        <button class="posto invisibile"></button>
                        <button class="posto libero">E07</button>
                        <button class="posto libero">E08</button>
                        <button class="posto libero">E09</button>
                        <button class="posto libero">E10</button>
                        <button class="posto libero">E11</button>
                        <button class="posto libero">E12</button>
                    </div>
                    <div>
                        <button class="posto libero">F01</button>
                        <button class="posto libero">F02</button>
                        <button class="posto libero">F03</button>
                        <button class="posto libero">F04</button>
                        <button class="posto libero">F05</button>
                        <button class="posto libero">F06</button>
                        <button class="posto invisibile"></button>
                        <button class="posto libero">F07</button>
                        <button class="posto libero">F08</button>
                        <button class="posto libero">F09</button>
                        <button class="posto libero">F10</button>
                        <button class="posto libero">F11</button>
                        <button class="posto libero">F12</button>
                    </div>
                    <div>
                        <button class="posto libero">G01</button>
                        <button class="posto libero">G02</button>
                        <button class="posto libero">G03</button>
                        <button class="posto libero">G04</button>
                        <button class="posto libero">G05</button>
                        <button class="posto libero">G06</button>
                        <button class="posto invisibile"></button>
                        <button class="posto libero">G07</button>
                        <button class="posto libero">G08</button>
                        <button class="posto libero">G09</button>
                        <button class="posto libero">G10</button>
                        <button class="posto libero">G11</button>
                        <button class="posto libero">G12</button>
                    </div>
                    <div>
                        <button class="posto libero">H01</button>
                        <button class="posto libero">H02</button>
                        <button class="posto libero">H03</button>
                        <button class="posto libero">H04</button>
                        <button class="posto libero">H05</button>
                        <button class="posto libero">H06</button>
                        <button class="posto invisibile"></button>
                        <button class="posto libero">H07</button>
                        <button class="posto libero">H08</button>
                        <button class="posto libero">H09</button>
                        <button class="posto libero">H10</button>
                        <button class="posto libero">H11</button>
                        <button class="posto libero">H12</button>
                    </div>
                </div>
            </div>
            <div class="col-md-4" id="posti-selezionati">
                <strong>Posti selezionati</strong>
                <div id="no-selected" class="text-muted small">Nessun posto selezionato.</div>
            </div>
        </div>
        <br><br><br>
    </div>
    <div class="modal fade" id="prenotazione-modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Prenota posto <span id="posto-id">(nessun posto selezionato)</span></h4>
                </div>
                <form>
                    <div class="modal-body">
                        <table class="table">
                            <tr>
                                <th>Posto</th>
                                <th>Tipo</th>
                            </tr>
                            <tr>
                                <td>
                                    <input type="text" id="posto-id-2">
                                </td>
                                <td>
                                    <select class="form-control">
                                        <option value="1">Intero (€0.00)</option>
                                        <option value="2">Ridotto (€0.00)</option>
                                        <option value="3">Militare (€0.00)</option>
                                        <option value="4">Disabile (€0.00)</option>
                                    </select>
                                </td>
                            </tr>
                        </table>
                        <div class="alert alert-info" role="alert">Il posto ti verr&agrave; riservato per 5 minuti dalla conferma di prenotazione, entro i quali dovrai eseguire il pagamento!</div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
                        <button type="button" class="btn btn-primary">Conferma Prenotazione</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
<c:import url="footer.jsp"></c:import>    