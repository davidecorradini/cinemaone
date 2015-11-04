
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<c:import url="header.jsp"></c:import>
<c:choose>
    <c:when test="${requestScope.generazione}">        
        <div class="container">
            <div class="page-header">
                <h1>Generazione spettacoli effettuata</h1>
            </div>
            <br><br><br><br><br>
        </div>
    </c:when>
    <c:otherwise>
        <div class="container">
            <div class="page-header col-md-6 col-md-offset-3">
                <h1>Genera spettacoli</h1>
            </div>
            <div class="col-md-6 col-md-offset-3">
                <form action="generaSpettacoli" method="POST" class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-4 control-label">Durata spettacoli:</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="durata" placeholder="durata">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">Numero spettacoli:</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="spettacoli" placeholder="numero spettacoli">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-4 col-sm-8">
                            <button type="submit" class="btn btn-default">Esegui</button>
                        </div>
                    </div>        
                </form>
            </div>
        </div>
    </c:otherwise>
</c:choose>
<c:import url="footer.jsp"></c:import>
