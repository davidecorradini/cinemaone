<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="header-admin.jsp" %>
<div class="container">
    <div class="page-header">
        <h1>Gestione prenotazioni</h1>
    </div>
    <br>
    <form id="cerca-prenotazioni">
        <div class="row">
            <div class="col-md-3">
                <div class="form-group">
                    <label>Titolo film</label>
                    <input type="text" class="form-control" id="titolo" placeholder="titolo">
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label>Genere film</label>
                    <input type="text" class="form-control" id="genere" placeholder="genere">
                </div> 
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label>Regista film</label>
                    <input type="text" class="form-control" id="regista" placeholder="regista">
                </div> 
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label>Sala spettacolo</label>
                    <input type="text" class="form-control" id="sala" placeholder="sala">
                </div> 
            </div>
        </div>
        <div class="row">
            <div class="col-md-3">
                <div class="form-group">
                    <label>Durata minima</label>
                    <input type="text" class="form-control" id="durataMin" placeholder="durata minima">
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label>Durata massima</label>
                    <input type="text" class="form-control" id="durataMax" placeholder="durata massima">
                </div> 
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label>In programmazione</label>
                    <div class="input-daterange input-group" id="datepicker">
                        <span class="input-group-addon">da</span>
                        <input type="text" class="input-sm form-control" id="programmazioneDa" />
                        <span class="input-group-addon">a</span>
                        <input type="text" class="input-sm form-control" id="programmazioneA" />
                    </div>
                </div> 
            </div>
        </div>
        <div class="row">
            <div class="col-md-3">
                <div class="form-group">
                    <label>Utente</label>
                    <input type="email" class="form-control" id="email" placeholder="email utente">
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label>Ruolo utente</label>
                    <input type="text" class="form-control" id="ruolo" placeholder="ruolo">
                </div> 
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label>Prenotazione effettuata</label>
                    <div class="input-daterange input-group" id="datepicker">
                        <span class="input-group-addon">da</span>
                        <input type="text" class="input-sm form-control" id="prenotazioneDa" />
                        <span class="input-group-addon">a</span>
                        <input type="text" class="input-sm form-control" id="prenotazioneA" />
                    </div>
                </div> 
            </div>
        </div>
        <div class="row">
            <div class="col-md-3">
                <div class="form-group">
                    <label>Tipo biglietto</label>
                    <input type="text" class="form-control" id="tipoPrezzo" placeholder="tipo biglietto">
                </div>
            </div>
            <div class="col-md-2">
                <div class="form-group">
                    <label>Riga</label>
                    <input type="text" class="form-control" id="riga" placeholder="riga">
                </div> 
            </div>
            <div class="col-md-2">
                <div class="form-group">
                    <label>Colonna</label>
                    <input type="text" class="form-control" id="colonna" placeholder="colonna">
                </div> 
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Cerca</button>
    </form>
    <br>
    <div class="panel-heading">
        <h3 class="panel-title">Prenotazioni</h3>
    </div>
    <div class="table-responsive">
        <table class="table table-bordered table-striped">
            <thead>
                <tr class="active">
                    <th>Film</th>
                    <th class="text-center">Data programmazione</th>
                    <th class="text-center">Sala</th>
                    <th class="text-center">Utente</th>
                    <th class="text-center">Data prenotazione</th>
                    <th class="text-center">Tipo biglietto</th>
                    <th class="text-center">Posto</th>
                    <th class="text-center">Elimina</th>
                </tr>
            </thead>
            <tbody id="prenotazioni">
                       
            </tbody>       
        </table>    
    </div>
</div>
<div class="modal fade" id="elimina-prenotazione">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <form id="delete-form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Elimina prenotazione</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        Vuoi eliminare definitivamente la prenotazione?
                        <input type="hidden" id="id-prenotazione">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
                    <button type="submit" class="btn btn-primary" id="delete-button">Elimina</button>
                </div>
            </form>
        </div>
    </div>
</div>
<%@ include file="footer-admin.jsp" %>
