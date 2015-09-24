<%-- 
    Document   : film
    Created on : 17-ago-2015, 20.14.20
    Author     : roberto


<%@page contentType="text/html" pageEncoding="UTF-8"%>
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<c:import url="header.jsp"></c:import>
    <div class="container">
        <div class="page-header">
            <h1>Film in programma</h1>
        </div>
        
        
    <c:forEach var="tmp" items="${requestScope.film}">
        <c:set var="tmpfilm" value="${tmp.getFilm()}"/>
        <c:set var="tmpgenere" value="${tmp.getGenere()}"/>
        <div class="media">
            <div class="media-left">
                <a href="dettaglio-film.html?idfilm=<c:out value="${tmpfilm.getIdFilm()}"></c:out>"><img class="media-object thumbnail" src="img/movie/<c:out value="${tmpfilm.getUriLocandina()}"></c:out>" style="height: 200px"></a>
                </div>
                <div class="media-body">
                        <h3 class="media-heading"><a class="no-color" href="dettaglio-film.html?idfilm=<c:out value="${tmpfilm.getIdFilm()}"></c:out>"><c:out value="${tmpfilm.getTitolo()}"></c:out></a> <small> <c:out value="${tmpfilm.getRegista()}"></c:out> &middot; <c:out value="${tmpfilm.getAnno()}"></c:out> &middot; <c:out value="${tmpgenere.getDescrizione()}"></c:out> &middot; <c:out value="${tmpfilm.getDurata()}"></c:out></small></h3>
                <c:set var="string" value="${tmpfilm.getTrama()}"/>
                <c:set var="indexend" value="${fn:length(string)}"/>
                <c:choose>
                    <c:when test="${indexend < '250'}">
                        <p><c:out value="${string}"></c:out></p>
                    </c:when>
                    <c:otherwise>
                        <c:set var="string1" value="${fn:substring(string, 0, 150)}" />
                        <p>
                        <div id="dm<c:out value="${tmpfilm.getIdFilm()}"></c:out>">
                            <c:out value="${string1}"></c:out>
                            <a class="mostranascondi" id="m<c:out value="${tmpfilm.getIdFilm()}"></c:out>">[altro...]</a>
                            </div>
                            <div id="div<c:out value="${tmpfilm.getIdFilm()}"></c:out>" style="display: none;"><c:out value="${string}"></c:out>
                            <a class="mostranascondi" id="n<c:out value="${tmpfilm.getIdFilm()}"></c:out>">[nascondi]</a></div></p>
                            
                    </c:otherwise>
                </c:choose>      
                <p>Programmazione<ul>
                    <c:forEach var="tmp1" items="${tmp.getSpettacoli()}">
                        <li><fmt:formatDate value="${tmp1.getDataOra()}" type="both" dateStyle="long" timeStyle="short"/>  <a href="prenotazione.html?idspettacolo=<c:out value="${tmp1.getIdSpettacolo()}"></c:out>"><i class="zmdi zmdi-calendar-check"></i> Prenota</a></li>
                        </c:forEach>
                </ul></p>
            </div>
        </div>
    </c:forEach>    
</div>
<c:import url="footer.jsp"></c:import>
