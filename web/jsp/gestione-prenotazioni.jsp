<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
    
<%@ include file="header-admin.jsp" %>
<div class="container">
    <div class="page-header">
        <h1>Gestione prenotazioni</h1>
    </div>
    <br>
    <form>
        <div class="row">
            <div class="col-md-3">
                <div class="form-group">
                    <label>Titolo film</label>
                    <input type="text" class="form-control" name="titolo" placeholder="titolo">
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label>Genere film</label>
                    <input type="text" class="form-control" name="genere" placeholder="genere">
                </div> 
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label>Regista film</label>
                    <input type="text" class="form-control" name="regista" placeholder="regista">
                </div> 
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label>Sala spettacolo</label>
                    <input type="text" class="form-control" name="sala" placeholder="sala">
                </div> 
            </div>
        </div>
        <div class="row">
            <div class="col-md-3">
                <div class="form-group">
                    <label>Durata minima</label>
                    <input type="text" class="form-control" name="durataMin" placeholder="durata minima">
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label>Durata massima</label>
                    <input type="text" class="form-control" name="durataMax" placeholder="durata massima">
                </div> 
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label>In programmazione</label>
                    <div class="input-daterange input-group" id="datepicker">
                        <span class="input-group-addon">da</span>
                        <input type="text" class="input-sm form-control" name="programmazioneDa" />
                        <span class="input-group-addon">a</span>
                        <input type="text" class="input-sm form-control" name="programmazioneA" />
                    </div>
                </div> 
            </div>
        </div>
        <div class="row">
            <div class="col-md-3">
                <div class="form-group">
                    <label>Utente</label>
                    <input type="email" class="form-control" name="email" placeholder="email utente">
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label>Ruolo utente</label>
                    <input type="text" class="form-control" name="ruolo" placeholder="ruolo">
                </div> 
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label>Prenotazione effettuata</label>
                    <div class="input-daterange input-group" id="datepicker">
                        <span class="input-group-addon">da</span>
                        <input type="text" class="input-sm form-control" name="prenotazioneDa" />
                        <span class="input-group-addon">a</span>
                        <input type="text" class="input-sm form-control" name="prenotazioneA" />
                    </div>
                </div> 
            </div>
        </div>
        <div class="row">
            <div class="col-md-3">
                <div class="form-group">
                    <label>Tipo biglietto</label>
                    <input type="text" class="form-control" name="tipoPrezzo" placeholder="tipo biglietto">
                </div>
            </div>
            <div class="col-md-2">
                <div class="form-group">
                    <label>Riga</label>
                    <input type="text" class="form-control" name="riga" placeholder="riga">
                </div> 
            </div>
            <div class="col-md-2">
                <div class="form-group">
                    <label>Colonna</label>
                    <input type="text" class="form-control" name="colonna" placeholder="colonna">
                </div> 
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Cerca</button>
    </form>
    <br>
    <div class="panel-heading">
            <h3 class="panel-title">Spettacoli</h3>
        </div>
    <table class="table table-bordered table-striped">
        
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
            
        <tr>
            <td><a class="no-color" href="../dettaglio-film.html?idfilm=1">I Fantastici 4</a> <small class="text-muted">Tim Story &middot; fantasy &middot; 123min</small></td>
            <td class="text-center">15 marzo 2016</td>            
            <td class="text-center">SALA1</td>           
            <td class="text-center">alessandro@cinemaone.it</td> 
            <td class="text-center">10 marzo 2016</td>
            <td class="text-center">Intero</td>
            <td class="text-center">C10</td>
            <td class="text-center"><a class="no-color" href="#"><i class="zmdi zmdi-delete"></i></a></td>
        </tr>        
                    
    </table>       
</div>

<%@ include file="footer-admin.jsp" %>
