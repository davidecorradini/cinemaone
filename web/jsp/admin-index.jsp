<%-- 
    Document   : incasso-film
    Created on : 1-ott-2015, 11.50.53
    Author     : alessandro
--%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
    
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
                        <li><strong>Incasso totale:</strong> €1054,00</li>
                        <li><strong>Prenotazioni totali:</strong> 512</li>
                        <li><strong>Numero di spettacoli:</strong> 31</li>
                    </ul>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">Spettaoli programmati</div>
                <div class="panel-body">
                    <ul>
                        <li><strong>Incasso totale:</strong> €1054,00</li>
                        <li><strong>Prenotazioni totali:</strong> 512</li>
                        <li><strong>Numero di spettacoli:</strong> 31</li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading">Statistiche film</div>
                <div class="panel-body">
                    <ul>
                        <li><strong>Film con incasso maggiore:</strong> I fantastici 4</li>
                        <li><strong>Incasso di tale film:</strong> €500,00</li>
                        <li><strong>Numero spettacoli di tale film:</strong> 15</li>
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
                        <li><strong>Utenti registrati:</strong> 26</li>
                        <li><strong>Utente con più posti prenotati:</strong> valentini.alessandro@hotmail.it</li>
                        <li><strong>Numero posti prenotati da tale utente:</strong> 31</li>
                        <li><strong>Utente con totale speso più alto:</strong> valentini.alessandro@hotmail.it</li>
                        <li><strong>Totale speso da tale utente:</strong> €150,00</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>     
</div>
<%@ include file="footer-admin.jsp" %>