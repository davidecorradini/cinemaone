<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
    
<%@ include file="header-admin.jsp" %>
<div class="container">
    <div class="page-header">
        <h1>Info spettacoli</h1>
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
                        <input type="text" class="input-sm form-control" name="start" />
                        <span class="input-group-addon">a</span>
                        <input type="text" class="input-sm form-control" name="end" />
                    </div>
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
            <th class="text-center">Data</th>
            <th class="text-center">Orario</th>
            <th class="text-center">Sala</th>
            <th class="text-center">Visualizza</th>
        </tr>
            
        <tr>
            <td><a class="no-color" href="../dettaglio-film.html?idfilm=1">I Fantastici 4</a> <small class="text-muted">Tim Story &middot; 2005 &middot; fantasy &middot; 123min</small></td>
            <td class="text-center">15 marzo 2016</td>
            <td class="text-center">19:00</td>
            <td class="text-center">SALA1</td>
            <td class="text-center"><a class="no-color" href="spettacoli.html?idspettacolo=1"><i class="zmdi zmdi-calendar-check"></i></a></td>
        </tr>        
                    
    </table>       
</div>
<%@ include file="footer-admin.jsp" %>