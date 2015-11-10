<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
    
<%@ include file="header-admin.jsp" %>
<div class="container">
    <div class="page-header">
        <h1>Info spettacoli</h1>
    </div>
    <br>
    <form id="cerca-spettacoli">
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
        <button type="submit" class="btn btn-primary">Cerca</button>
    </form>
    <br>
    <div class="panel-heading">
        <h3 class="panel-title">Spettacoli</h3>
    </div>
    <div class="table-responsive">
        <table class="table table-bordered table-striped">
            <thead>
                <tr class="active">
                    <th>Film</th>
                    <th class="text-center">Data</th>
                    <th class="text-center">Orario</th>
                    <th class="text-center">Sala</th>
                    <th class="text-center">Visualizza</th>
                </tr>
            </thead>
            <tbody id="spettacoli">
                    
            </tbody>     
        </table>       
    </div>
</div>
<%@ include file="footer-admin.jsp" %>