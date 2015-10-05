<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<footer>
            <div class="container">
                <div class="row">
                    <div class="col-md-6">
                        <span class="footer-brand">Cinema One s.r.l.</span><br>
                        <i class="zmdi zmdi-pin zmdi-hc-fw zmdi-right-4"></i>Viale A. Degasperi 95, 38023 Cles TN<br>
                        <i class="zmdi zmdi-email zmdi-hc-fw zmdi-right-4"></i>info@cinemaone.it<br>
                        <i class="zmdi zmdi-phone zmdi-hc-fw zmdi-right-4"></i>+39 347 244 3532
                    </div>
                    <div class="col-md-6">
                        <span class="text-right">Copyright &copy; 2015 Cinema One. Tutti i diritti riservati.</span>
                    </div>
                </div>
            </div>
        </footer>
        <script src="js/jquery-1.11.3.min.js"></script>
        <script src="js/jquery-ui.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.jcarousel.min.js"></script>
        <script src="js/jquery.bxslider.min.js"></script>
        <script src="js/cinema.js"></script>
        <c:if test="${pageContext.request.servletPath == '/jsp/prenotazione.jsp'}">
            <script>
                $(document).ready(function () {
                    updatePosti(id_spettacolo);
                });
            </script>
        </c:if>
    </body>
</html>
