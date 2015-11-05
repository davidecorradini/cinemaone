<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:forEach var="tmp" items="${postiSala}">
    <div class="row">
        <c:forEach var="i"  begin="0" end="${tmp.getSize()-1}">
            
            <button <c:choose>
                    <c:when test="${tmp.getStato(i) == 0}">
                        id="posto-<c:out value="${tmp.getIdPosto(i)}"></c:out>" class="posto libero"
                    </c:when>
                    <c:when test="${tmp.getStato(i) == -1}">
                        id="posto-<c:out value="${tmp.getIdPosto(i)}"></c:out>" class="rotto occupato"
                    </c:when>
                    <c:otherwise>
                        class="posto invisibile"
                    </c:otherwise>
                </c:choose> data-toggle="tooltip" data-placement="top" title=""><div class="hidden-xs"><c:out value="${fn:toUpperCase(tmp.getRiga())}"></c:out><c:if test="${tmp.getColonna(i) < 10}">0</c:if><c:out value="${tmp.getColonna(i)}"></c:out></div></button>
        </c:forEach>
    </div>
</c:forEach>