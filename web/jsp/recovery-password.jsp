    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:import url="header.jsp"></c:import>
<c:choose>
    <c:when test="${requestScope.valida}">
        <div class="container" id="containerResetPassword">
            <div class="page-header col-md-6 col-md-offset-3">
                <h1>Cambia Password</h1>
            </div>
            <div class="col-md-6 col-md-offset-3">
                <form id="resetPassword" class="form-horizontal"> 
                    <div class="form-group">
                        <label for="inputEmail3" class="col-sm-2 control-label">Nuova password:</label>
                        <div class="col-sm-10">
                            <input type="password" class="form-control" id="password1" placeholder="password">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputEmail3" class="col-sm-2 control-label">Ripeti password:</label>
                        <div class="col-sm-10">
                            <input type="password" class="form-control" id="password2" placeholder="password">
                        </div>
                    </div>
                    <input type="hidden" id="email" value="${requestScope.email}">
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <span class="text-danger" id="reset-wrong-password" style="display: none;">Le Password non corrispondono!</span>
                            <span class="text-danger" id="reset-error" style="display: none;">Errore non specificato, riprova pi&ugrave; tardi.</span>
                        </div>
                    </div>
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