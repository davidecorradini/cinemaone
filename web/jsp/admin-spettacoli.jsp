<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
    
<%@ include file="header-admin.jsp" %>
<div class="container">
    <div class="page-header">
        <h1>Gestione spettacoli</h1>
    </div>
    
        <form class="form-horizontal">
            <div class="form-group">
                <label for="inputEmail3" class="col-sm-2 control-label">Email</label>
                <div class="col-sm-10">
                    <input type="email" class="form-control" id="inputEmail3" placeholder="Email">
                </div>
            </div>
            <div class="form-group">
                <label for="inputPassword3" class="col-sm-2 control-label">Password</label>
                <div class="col-sm-10">
                    <input type="password" class="form-control" id="inputPassword3" placeholder="Password">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox"> Remember me
                        </label>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default">Sign in</button>
                </div>
            </div>
        </form>
    
    <div class="panel-heading">
            <h3 class="panel-title">Spettacoli</h3>
            <div class="pull-right">
                <button class="btn btn-default btn-xs btn-filter"><span class="glyphicon glyphicon-filter"></span> Filter</button>
            </div>
        </div>
    <table class="table table-bordered table-striped">
        
        <tr class="active">
            <th>Film</th>
            <th class="text-center">Data</th>
            <th class="text-center">Orario</th>
            <th class="text-center">Sala</th>
            <th class="text-center">Visualizza</th>
        </tr>
            
            
        <c:forEach var="tmp" items="${requestScope.spettacoli}">
            <c:set var="tmpFilm" value="${tmp.getFilm()}"/>
            <c:set var="tmpSala" value="${tmp.getSala()}"/>
            <c:set var="tmpGenere" value="${tmp.getGenere()}"/>
            <tr>
                <td><a class="no-color" href="dettaglio-film.html?idfilm=<c:out value="${tmpFilm.getIdFilm()}"></c:out>"><c:out value="${tmpFilm.getTitolo()}"></c:out></a> <small class="text-muted"><c:out value="${tmpFilm.getRegista()}"></c:out> &middot; <c:out value="${tmpFilm.getAnno()}"></c:out> &middot; <c:out value="${tmpGenere.getDescrizione()}"></c:out> &middot; <c:out value="${tmpFilm.getDurata()}"></c:out>min</small></td>
                <td class="text-center"><c:out value="${tmp.getData()}"></c:out></td>
                <td class="text-center"><c:out value="${tmp.getOra()}"></c:out></td>
                <td class="text-center"><c:out value="${tmpSala.getNome()}"></c:out></td>
                <td class="text-center"><a class="no-color" href="spettacoli.html?idspettacolo=<c:out value="${tmp.getIdSpettacolo()}"></c:out>"><i class="zmdi zmdi-calendar-check"></i></a></td>
            </tr>
        </c:forEach>
                    
    </table>       
</div>
<%@ include file="footer.jsp" %>