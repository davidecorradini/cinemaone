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
    </script>
    <div class="container">
        <div class="page-header">
            <h1>Prenotazione</h1>
        <span class="subtitle"><strong><c:out value="${Film.getTitolo()}"></c:out></strong> &middot; <fmt:formatDate value="${Spettacolo.getTimeStamp()}" pattern="dd-MM-yyyy hh:mm"/> &middot; <c:out value="${Sala.getNome()}"></c:out></span>
        </div>
        <div class="row">
            <div class="col-md-8">
                <div class="sala">
                    <div>
                        <button id="posto-1" class="posto libero" data-toggle="tooltip" data-placement="top">A01</button>
                        <button id="posto-2" class="posto libero" data-toggle="tooltip" data-placement="top">A02</button>
                        <button id="posto-3" class="posto libero" data-toggle="tooltip" data-placement="top">A03</button>
                        <button id="posto-4" class="posto libero" data-toggle="tooltip" data-placement="top">A04</button>
                        <button id="posto-5" class="posto libero" data-toggle="tooltip" data-placement="top">A05</button>
                        <button id="posto-6" class="posto libero" data-toggle="tooltip" data-placement="top">A06</button>
                        <button id="posto-7" class="posto invisibile" data-toggle="tooltip" data-placement="top"></button>
                        <button id="posto-8" class="posto libero" data-toggle="tooltip" data-placement="top">A07</button>
                        <button id="posto-9" class="posto libero" data-toggle="tooltip" data-placement="top">A08</button>
                        <button id="posto-10" class="posto libero" data-toggle="tooltip" data-placement="top">A09</button>
                        <button id="posto-11" class="posto libero" data-toggle="tooltip" data-placement="top">A10</button>
                        <button id="posto-12" class="posto libero" data-toggle="tooltip" data-placement="top">A11</button>
                        <button id="posto-13" class="posto libero" data-toggle="tooltip" data-placement="top">A12</button>
                    </div>
                    <div>
                        <button id="posto-14" class="posto libero" data-toggle="tooltip" data-placement="top">B01</button>
                        <button id="posto-15" class="posto libero" data-toggle="tooltip" data-placement="top">B02</button>
                        <button id="posto-16" class="posto libero" data-toggle="tooltip" data-placement="top">B03</button>
                        <button id="posto-17" class="posto libero" data-toggle="tooltip" data-placement="top">B04</button>
                        <button id="posto-18" class="posto libero" data-toggle="tooltip" data-placement="top">B05</button>
                        <button id="posto-19" class="posto libero" data-toggle="tooltip" data-placement="top">B06</button>
                        <button id="posto-20" class="posto invisibile" data-toggle="tooltip" data-placement="top"></button>
                        <button id="posto-21" class="posto libero" data-toggle="tooltip" data-placement="top">B07</button>
                        <button id="posto-22" class="posto libero" data-toggle="tooltip" data-placement="top">B08</button>
                        <button id="posto-23" class="posto libero" data-toggle="tooltip" data-placement="top">B09</button>
                        <button id="posto-24" class="posto libero" data-toggle="tooltip" data-placement="top">B10</button>
                        <button id="posto-25" class="posto libero" data-toggle="tooltip" data-placement="top">B11</button>
                        <button id="posto-26" class="posto libero" data-toggle="tooltip" data-placement="top">B12</button>
                    </div>
                    <div>
                        <button id="posto-27" class="posto libero" data-toggle="tooltip" data-placement="top">C01</button>
                        <button id="posto-28" class="posto libero" data-toggle="tooltip" data-placement="top">C02</button>
                        <button id="posto-29" class="posto libero" data-toggle="tooltip" data-placement="top">C03</button>
                        <button id="posto-30" class="posto libero" data-toggle="tooltip" data-placement="top">C04</button>
                        <button id="posto-31" class="posto libero" data-toggle="tooltip" data-placement="top">C05</button>
                        <button id="posto-32" class="posto libero" data-toggle="tooltip" data-placement="top">C06</button>
                        <button id="posto-33" class="posto invisibile" data-toggle="tooltip" data-placement="top"></button>
                        <button id="posto-34" class="posto libero" data-toggle="tooltip" data-placement="top">C07</button>
                        <button id="posto-35" class="posto libero" data-toggle="tooltip" data-placement="top">C08</button>
                        <button id="posto-36" class="posto libero" data-toggle="tooltip" data-placement="top">C09</button>
                        <button id="posto-37" class="posto libero" data-toggle="tooltip" data-placement="top">C10</button>
                        <button id="posto-38" class="posto libero" data-toggle="tooltip" data-placement="top">C11</button>
                        <button id="posto-39" class="posto libero" data-toggle="tooltip" data-placement="top">C12</button>
                    </div>
                    <div>
                        <button id="posto-40" class="posto libero" data-toggle="tooltip" data-placement="top">D01</button>
                        <button id="posto-41" class="posto libero" data-toggle="tooltip" data-placement="top">D02</button>
                        <button id="posto-42" class="posto libero" data-toggle="tooltip" data-placement="top">D03</button>
                        <button id="posto-43" class="posto libero" data-toggle="tooltip" data-placement="top">D04</button>
                        <button id="posto-44" class="posto libero" data-toggle="tooltip" data-placement="top">D05</button>
                        <button id="posto-45" class="posto libero" data-toggle="tooltip" data-placement="top">D06</button>
                        <button id="posto-46" class="posto invisibile" data-toggle="tooltip" data-placement="top"></button>
                        <button id="posto-47" class="posto libero" data-toggle="tooltip" data-placement="top">D07</button>
                        <button id="posto-48" class="posto libero" data-toggle="tooltip" data-placement="top">D08</button>
                        <button id="posto-49" class="posto libero" data-toggle="tooltip" data-placement="top">D09</button>
                        <button id="posto-50" class="posto libero" data-toggle="tooltip" data-placement="top">D10</button>
                        <button id="posto-51" class="posto libero" data-toggle="tooltip" data-placement="top">D11</button>
                        <button id="posto-52" class="posto libero" data-toggle="tooltip" data-placement="top">D12</button>
                    </div>
                    <div>
                        <button id="posto-53" class="posto invisibile" data-toggle="tooltip" data-placement="top"></button>
                        <button id="posto-54" class="posto invisibile" data-toggle="tooltip" data-placement="top"></button>
                        <button id="posto-55" class="posto invisibile" data-toggle="tooltip" data-placement="top"></button>
                        <button id="posto-56" class="posto invisibile" data-toggle="tooltip" data-placement="top"></button>
                        <button id="posto-57" class="posto invisibile" data-toggle="tooltip" data-placement="top"></button>
                        <button id="posto-58" class="posto invisibile" data-toggle="tooltip" data-placement="top"></button>
                        <button id="posto-59" class="posto invisibile" data-toggle="tooltip" data-placement="top"></button>
                        <button id="posto-60" class="posto invisibile" data-toggle="tooltip" data-placement="top"></button>
                        <button id="posto-61" class="posto invisibile" data-toggle="tooltip" data-placement="top"></button>
                        <button id="posto-62" class="posto invisibile" data-toggle="tooltip" data-placement="top"></button>
                        <button id="posto-63" class="posto invisibile" data-toggle="tooltip" data-placement="top"></button>
                        <button id="posto-64" class="posto invisibile" data-toggle="tooltip" data-placement="top"></button>
                        <button id="posto-65" class="posto invisibile" data-toggle="tooltip" data-placement="top"></button>
                    </div>
                    <div>
                        <button id="posto-66" class="posto libero" data-toggle="tooltip" data-placement="top">E01</button>
                        <button id="posto-67" class="posto libero" data-toggle="tooltip" data-placement="top">E02</button>
                        <button id="posto-68" class="posto libero" data-toggle="tooltip" data-placement="top">E03</button>
                        <button id="posto-69" class="posto libero" data-toggle="tooltip" data-placement="top">E04</button>
                        <button id="posto-70" class="posto libero" data-toggle="tooltip" data-placement="top">E05</button>
                        <button id="posto-71" class="posto libero" data-toggle="tooltip" data-placement="top">E06</button>
                        <button id="posto-72" class="posto invisibile" data-toggle="tooltip" data-placement="top"></button>
                        <button id="posto-73" class="posto libero" data-toggle="tooltip" data-placement="top">E07</button>
                        <button id="posto-74" class="posto libero" data-toggle="tooltip" data-placement="top">E08</button>
                        <button id="posto-75" class="posto libero" data-toggle="tooltip" data-placement="top">E09</button>
                        <button id="posto-76" class="posto libero" data-toggle="tooltip" data-placement="top">E10</button>
                        <button id="posto-77" class="posto libero" data-toggle="tooltip" data-placement="top">E11</button>
                        <button id="posto-78" class="posto libero" data-toggle="tooltip" data-placement="top">E12</button>
                    </div>
                    <div>
                        <button id="posto-79" class="posto libero" data-toggle="tooltip" data-placement="top">F01</button>
                        <button id="posto-80" class="posto libero" data-toggle="tooltip" data-placement="top">F02</button>
                        <button id="posto-81" class="posto libero" data-toggle="tooltip" data-placement="top">F03</button>
                        <button id="posto-82" class="posto libero" data-toggle="tooltip" data-placement="top">F04</button>
                        <button id="posto-83" class="posto libero" data-toggle="tooltip" data-placement="top">F05</button>
                        <button id="posto-84" class="posto libero" data-toggle="tooltip" data-placement="top">F06</button>
                        <button id="posto-85" class="posto invisibile" data-toggle="tooltip" data-placement="top"></button>
                        <button id="posto-86" class="posto libero" data-toggle="tooltip" data-placement="top">F07</button>
                        <button id="posto-87" class="posto libero" data-toggle="tooltip" data-placement="top">F08</button>
                        <button id="posto-88" class="posto libero" data-toggle="tooltip" data-placement="top">F09</button>
                        <button id="posto-89" class="posto libero" data-toggle="tooltip" data-placement="top">F10</button>
                        <button id="posto-90" class="posto libero" data-toggle="tooltip" data-placement="top">F11</button>
                        <button id="posto-91" class="posto libero" data-toggle="tooltip" data-placement="top">F12</button>
                    </div>
                    <div>
                        <button id="posto-92" class="posto libero" data-toggle="tooltip" data-placement="top">G01</button>
                        <button id="posto-93" class="posto libero" data-toggle="tooltip" data-placement="top">G02</button>
                        <button id="posto-94" class="posto libero" data-toggle="tooltip" data-placement="top">G03</button>
                        <button id="posto-95" class="posto libero" data-toggle="tooltip" data-placement="top">G04</button>
                        <button id="posto-96" class="posto libero" data-toggle="tooltip" data-placement="top">G05</button>
                        <button id="posto-97" class="posto libero" data-toggle="tooltip" data-placement="top">G06</button>
                        <button id="posto-98" class="posto invisibile" data-toggle="tooltip" data-placement="top"></button>
                        <button id="posto-99" class="posto libero" data-toggle="tooltip" data-placement="top">G07</button>
                        <button id="posto-100" class="posto libero" data-toggle="tooltip" data-placement="top">G08</button>
                        <button id="posto-101" class="posto libero" data-toggle="tooltip" data-placement="top">G09</button>
                        <button id="posto-102" class="posto libero" data-toggle="tooltip" data-placement="top">G10</button>
                        <button id="posto-103" class="posto libero" data-toggle="tooltip" data-placement="top">G11</button>
                        <button id="posto-104" class="posto libero" data-toggle="tooltip" data-placement="top">G12</button>
                    </div>
                    <div>
                        <button id="posto-105" class="posto libero" data-toggle="tooltip" data-placement="top">H01</button>
                        <button id="posto-106" class="posto libero" data-toggle="tooltip" data-placement="top">H02</button>
                        <button id="posto-107" class="posto libero" data-toggle="tooltip" data-placement="top">H03</button>
                        <button id="posto-108" class="posto libero" data-toggle="tooltip" data-placement="top">H04</button>
                        <button id="posto-109" class="posto libero" data-toggle="tooltip" data-placement="top">H05</button>
                        <button id="posto-110" class="posto libero" data-toggle="tooltip" data-placement="top">H06</button>
                        <button id="posto-111" class="posto invisibile" data-toggle="tooltip" data-placement="top"></button>
                        <button id="posto-112" class="posto libero" data-toggle="tooltip" data-placement="top">H07</button>
                        <button id="posto-113" class="posto libero" data-toggle="tooltip" data-placement="top">H08</button>
                        <button id="posto-114" class="posto libero" data-toggle="tooltip" data-placement="top">H09</button>
                        <button id="posto-115" class="posto libero" data-toggle="tooltip" data-placement="top">H10</button>
                        <button id="posto-116" class="posto libero" data-toggle="tooltip" data-placement="top">H11</button>
                        <button id="posto-117" class="posto libero" data-toggle="tooltip" data-placement="top">H12</button>
                    </div>
                </div>
            </div>
            <div class="col-md-4" id="posti-selezionati">
                <strong>Posti selezionati</strong>
                <div id="3no-selected" class="text-muted small">Nessun posto selezionato.</div>
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
                                        <option value="1">Intero (€0.00)</option>
                                        <option value="2">Ridotto (€0.00)</option>
                                        <option value="3">Militare (€0.00)</option>
                                        <option value="4">Disabile (€0.00)</option>
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