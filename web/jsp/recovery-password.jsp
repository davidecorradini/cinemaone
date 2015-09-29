
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:import url="header.jsp"></c:import>
<c:choose>
    <c:when test="${requestScope.valida}">
        <div class="container">
            <div class="page-header col-md-6 col-md-offset-3">
                <h1>Cambia Password</h1>
            </div>
        <div class="col-md-6 col-md-offset-3">
            <form method="POST" action="/Multisala/resetPassword" class="form-horizontal"> 
                <div class="form-group">
                    <label for="inputEmail3" class="col-sm-2 control-label">Nuova password:</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" name="password1" placeholder="password">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputEmail3" class="col-sm-2 control-label">Ripeti password:</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" name="password2" placeholder="password">
                    </div>
                </div>
                <input type="hidden" name="email" value="${requestScope.email}">
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">Modifica</button>
                    </div>
                </div>                
            </form>
        </div>
        </div>
           <br><br><br><br><br> 
    </c:when>
    <c:otherwise>
        <div class="container">
            <div class="page-header">
                <h1>Pagina Scaduta</h1>
            </div>
            Siamo spiacenti, ma la pagina che stai cercando di raggiungere &egrave; scaduta.
            <br><br><br><br><br>
        </div>
    </c:otherwise>
</c:choose>
<c:import url="footer.jsp"></c:import>