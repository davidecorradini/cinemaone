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
        <div class="modal fade" id="login-modal">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <form id="login-form">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">Accesso Clienti</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <input type="email" class="form-control" id="input-email" placeholder="Indirizzo e-mail">
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control" id="input-password" placeholder="Password">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
                            <button type="submit" class="btn btn-primary" id="login-button">Accedi</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="modal fade" id="signup-modal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form id="signup-form">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">Registrazione Nuovo Cliente</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="signup-email">Indirizzo e-mail</label>
                                <input type="email" class="form-control" id="signup-email" placeholder="Indirizzo e-mail">
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="signup-password1">Password</label>
                                        <input type="password" class="form-control" id="signup-password1" placeholder="Password">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="signup-password2">Ripeti Password</label>
                                        <input type="password" class="form-control" id="signup-password2" placeholder="Ripeti Password">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
                            <button type="submit" class="btn btn-primary" id="signup-button">Registrati</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="modal fade" id="recovery-modal">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <form id="recovery-form">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">Recupera Password</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="input-email">Indirizzo e-mail</label>
                                <input type="email" class="form-control" id="recovery-email" placeholder="Indirizzo e-mail">
                            </div>
                                <span class="text-danger" id="recovery-sent" style="display: none;">Un link di recupero &egrave; stato inviato all'indirizzo e-mail inserito.</span>
                                <span class="text-danger" id="recovery-no-email" style="display: none;">L'indirizzo e-mail inserito non è presente nei nostri sistemi.</span>
                                <span class="text-danger" id="recovery-error" style="display: none;">Errore non specificato, riprova pi&ugrave; tardi.</span>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" id="recovery-cancel" data-dismiss="modal">Annulla</button>
                            <button type="submit" class="btn btn-primary" id="recovery-button">Recupera</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script src="js/jquery-ui.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/cinema.js"></script>
        <c:if test="${pageContext.request.servletPath == '/jsp/prenotazione.jsp'}">
            <script>
                $(document).ready(function () {
                    updatePosti(id_spettacolo);
                });
                $(function () {
                    $('[data-toggle="tooltip"]').tooltip()
                })
            </script>
        </c:if>
    </body>
</html>
