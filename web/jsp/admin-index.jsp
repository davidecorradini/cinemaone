
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="spettacoliPassati" value="${requestScope.spettacoliPassati}"/>
<c:set var="spettacoliFuturi" value="${requestScope.spettacoliFuturi}"/>
<c:set var="infoUtenti" value="${requestScope.infoUtenti}"/>
<c:set var="incassoFilm" value="${requestScope.incassoFilm}"/>
<%@ include file="header-admin.jsp" %>
<div class="container">
    <div class="page-header">
        <h1>Pannello di controllo</h1>
    </div>
    <div class="row">
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading">Spettacoli passati</div>
                <div class="panel-body">
                    <ul>
                        <li><strong>Incasso totale:</strong> <fmt:formatNumber value="${spettacoliPassati[1]}" type="currency" currencySymbol="&euro;" /></li>
                        <li><strong>Prenotazioni totali:</strong> ${spettacoliPassati[0]}</li>
                        <li><strong>Numero di spettacoli:</strong> ${spettacoliPassati[2]}</li>
                    </ul>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">Spettaoli programmati</div>
                <div class="panel-body">
                    <ul>
                        <li><strong>Incasso totale:</strong> <fmt:formatNumber value="${spettacoliFuturi[1]}" type="currency" currencySymbol="&euro;" /></li>
                        <li><strong>Prenotazioni totali:</strong> ${spettacoliFuturi[0]}</li>
                        <li><strong>Numero di spettacoli:</strong> ${spettacoliFuturi[2]}</li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading">Statistiche film</div>
                <div class="panel-body">
                    <ul>
                        <li><strong>Film con incasso maggiore:</strong> ${incassoFilm.getFilm().getTitolo()}</li>
                        <li><strong>Incasso di tale film:</strong> <fmt:formatNumber value="${incassoFilm.getIncasso()}" type="currency" currencySymbol="&euro;" /></li>
                        <li><strong>Numero spettacoli di tale film:</strong> ${incassoFilm.getNumSpett()}</li>
                    </ul>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">Sale</div>
                <div class="panel-body">
                    <ul>
                        <c:forEach var="tmp" items="${requestScope.sale}">
                            <li><strong>${tmp.getNome()}:</strong> ${tmp.getDescrizione()}</li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading">Statistiche utenti</div>
                <div class="panel-body">
                    <ul>
                        <li><strong>Utenti registrati:</strong> ${infoUtenti.getUtentiRegistrati()}</li>
                        <li><strong>Utente con più posti prenotati:</strong> ${infoUtenti.getEmailPostiPiuPrenotati()}</li>
                        <li><strong>Numero posti prenotati da tale utente:</strong> ${infoUtenti.getNumeroPosti()}</li>
                        <li><strong>Utente con totale speso più alto:</strong> ${infoUtenti.getEmailTotalePiuAlto()}</li>
                        <li><strong>Totale speso da tale utente:</strong> <fmt:formatNumber value="${infoUtenti.getTotale()}" type="currency" currencySymbol="&euro;" /></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>     
</div>
<%@ include file="footer-admin.jsp" %>