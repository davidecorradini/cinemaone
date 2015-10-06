<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach var="tmp" items="${requestScope.postiSala}">
    <div class="row">
        <c:forEach var="tmpColonna" items="${tmp.getColonnaStato()}">
            <button <c:choose><c:when test="${tmpColonna[1] == 0}">id="posto-<c:out value="${tmpColonna[0]}"></c:out>" class="posto libero"</c:when><c:when test="${tmpColonna[1] == -1}">id="posto-<c:out value="${tmpColonna[0]}"></c:out>" class="posto occupato"</c:when><c:otherwise>class="posto invisibile"</c:otherwise></c:choose> data-toggle="tooltip" data-placement="top"><c:out value="${tmp.getRiga()}"></c:out><c:out value="${tmpColonna[0]}"></c:out></button>
        </c:forEach>
    </div>
</c:forEach>