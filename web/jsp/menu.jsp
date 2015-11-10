<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="zmdi zmdi-account-circle zmdi-right-8"></i><c:choose><c:when test="${sessionScope.autenticato != null}"><c:out value="${sessionScope.user.getEmail()}"></c:out></c:when><c:otherwise>Area Clienti</c:otherwise></c:choose> <i class="zmdi zmdi-chevron-down"></i></a>
    <ul class="dropdown-menu">
        <c:choose>
            <c:when test="${sessionScope.autenticato != null}">
                <li><a href="profile.html"><i class="zmdi zmdi-hc-fw zmdi-settings zmdi-right-8"></i>Info utente</a></li>
                <c:if test="${sessionScope.autenticato == 'ADMIN'}"><li><a href="admin/index.html"><i class="zmdi zmdi-hc-fw zmdi-flag zmdi-right-8"></i> Amministratore</a></li></c:if>
                <li><a href="logout.html" id="logout-link"><i class="zmdi zmdi-hc-fw zmdi-close-circle-o zmdi-right-8"></i>Logout</a></li>
            </c:when>
            <c:otherwise>
                <li class="dropdown-header">Cliente Registrato</li>
                <li><a href="#" data-toggle="modal" data-target="#login-modal"><i class="zmdi zmdi-hc-fw zmdi-sign-in zmdi-logout zmdi-right-8"></i>Accedi</a></li>
                <li><a href="#" data-toggle="modal" data-target="#recovery-modal"><i class="zmdi zmdi-hc-fw zmdi-key zmdi-logout zmdi-right-8"></i>Recupera Password</a></li>
                <li class="divider" role="separator"></li>
                <li class="dropdown-header">Nuovo Cliente</li>
                <li><a href="#" data-toggle="modal" data-target="#signup-modal"><i class="zmdi zmdi-hc-fw zmdi-account-add zmdi-logout zmdi-right-8"></i>Registrati</a></li>
            </c:otherwise>
        </c:choose>
    </ul>