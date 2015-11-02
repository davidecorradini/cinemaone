$(document).ready(function () {
    $('[data-toggle="tooltip"]').tooltip();
});

// AJAX Login

$("#login-form").submit(function(event) {
    event.preventDefault();
    $("#login-button").attr("disabled","disabled");
    $("#login-button").html("<i class=\"zmdi zmdi-rotate-left zmdi-hc-spin-reverse\"></i> Accesso");
    $.ajax({
        type: "POST",
        url: "check-login",
        data: "username=" + $("#input-email").val() + "&password=" + $("#input-password").val(),
        success: function(answer) {
            answer = answer.trim();
            if(answer == "success") {
                $("#login-modal").modal("hide");
                $("#menu").html("<i class=\"zmdi zmdi-rotate-left zmdi-hc-spin-reverse\"></i> Caricamento...");
                $.get("jsp/menu.jsp", function(data) {
                    $("#menu").html(data);
                });
            } else {
                $("#login-button").html("Accedi");
                $("#login-button").removeAttr("disabled");
                $("#login-modal").effect("shake");
            }
        }
    });
});


// AJAX SignUp

$("#signup-form").submit(function(event) {
    event.preventDefault();
    $("#signup-existing").slideUp("fast");
    $("#signup-wrong-password").slideUp("fast");
    $("#signup-success").slideUp("fast");
    $("#signup-button").attr("disabled", "disabled");
    $("#signup-button").html("<i class=\"zmdi zmdi-rotate-left zmdi-hc-spin-reverse\"></i> Attendere");
    $.ajax({
        type: "POST",
        url: "signUp",
        data: "email=" + $("#signup-email").val() + "&password1=" + $("#signup-password1").val() + "&password2=" + $("#signup-password2").val(),
        success: function(answer) {
            answer = answer.trim();
            if(answer == "success") {
                $("#signup-button").hide();
                $("#signup-success").slideDown("slow");
            } else if (answer == "existing") {
                $("#signup-existing").slideDown("slow");
                $("#signup-button").html("Registrati");
                $("#signup-button").removeAttr("disabled");
            } else if (answer == "wrong-password") {
                $("#signup-wrong-password").slideDown("slow");
                $("#signup-button").html("Registrati");
                $("#signup-button").removeAttr("disabled");
            }
        }
    });
});


// Password Recovery

$("#recovery-form").submit(function(event) {
    event.preventDefault();
    $("#recovery-sent").slideUp("fast");
    $("#recovery-no-email").slideUp("fast");
    $("#recovery-error").slideUp("fast");
    $("#recovery-button").attr("disabled", "disabled");
    $("#recovery-button").html("<i class=\"zmdi zmdi-rotate-left zmdi-hc-spin-reverse\"></i> Attendere");
    $.ajax({
        type: "POST",
        url: "recuperaPassword",
        data: "email=" + $("#recovery-email").val(),
        success: function(answer) {
            answer = answer.trim();
            if (answer == "success") {
                $("#recovery-sent").slideDown("slow");
                $("#recovery-button").hide();
                $("#recovery-cancel").text("Chiudi");
            } else if (answer == "noemail") {
                $("#recovery-no-email").slideDown("slow");
                $("#recovery-button").removeAttr("disabled");
                $("#recovery-button").html("Recupera");
            } else {
                $("#recovery-error").slideDown("slow");
                $("#recovery-button").removeAttr("disabled");
                $("#recovery-button").html("Recupera");
            }
        }
    });
});


// Prenotazione

function updatePosti (spettacolo) {
    var interval = 1000;
    setInterval(function () {
        $.getJSON("statoPrenotazioni", "spettacolo=" + spettacolo, function (result) {
            $(".posto").each(function (i, element) {
                $(element).removeClass("occupato");
                $(element).removeClass("occupato-tmp");
                $(element).removeClass("selezionato");
                $(element).addClass("libero");
                $(element).prop('title', '');
            });
            $.each(result, function (key, val) {
                var x, y, stato, timestamp, postoName;
                $.each(val, function (key2, val2) {
                    if (key2 == "x") {
                        if (parseInt(val2) >= 10) {
                            x = val2;
                        } else {
                            x = "0" + val2;
                        }
                    } else if (key2 == "y") {
                        y = val2.toString().toUpperCase();
                    } else if (key2 == "stato") {
                        stato = val2;
                    } else if (key2 == "timestamp") {
                        var remaining = parseInt(val2);
                        var m = remaining % 60;
                        var s = remaining - m * 60;
                        var mm = "0" + m;
                        var ss;
                        if (s < 10) {
                            ss = "0" + s;
                        } else {
                            ss = s;
                        }
                        timestamp = mm + ":" + ss;
                    }
                    postoName = y + x;
                });
                $(".posto").each(function (i, element) {
                    if ($(element).text() == postoName) {
                        if (stato == "occupato-tmp") {
                            $(element).removeClass("libero");
                            $(element).addClass("occupato-tmp");
                            $(element).prop('title', timestamp);
                        } else if (stato == "occupato") {
                            $(element).removeClass("libero");
                            $(element).addClass("occupato");
                            $(element).prop('title', '');
                        } else if (stato == "tuo") {
                            $(element).removeClass("libero");
                            $(element).addClass("selezionato");
                            $(element).prop('title', '');
                        } else if (stato == "tuo-tmp") {
                            $(element).removeClass("libero");
                            $(element).addClass("selezionato");
                            $(element).prop('title', timestamp);
                        }
                    }
                });
            });
            interval = 1000;
        }).fail( function(d, textStatus, error) {
            interval = 5000;
        });
    }, interval);
}

// db-fail: errore nel database, probabile posto doppio

function addSelezionato (postoString) {
    if (postiSelezionati.length == 0) {
        $("#no-selected").slideUp("fast");
    }
    postiSelezionati.push(postoString);
    $("#posti-selezionati").append("<div class=\"selezionato-container\" id=\"" + postoString + "\"><div class=\"posto-side selezionato\">" + postoString + "</div>Intero €9.00</div>");
    $("#" + postoString).slideDown("fast");
}

function removeSelezionato (postoString) {
    for(var i = postiSelezionati.length - 1; i >= 0; i--) {
        if(postiSelezionati[i] == postoString) {
           postiSelezionati.splice(i, 1);
        }
    }
    $("#" + postoString).slideUp("fast");
}

$(".posto").click(function (event) {
    var posto = event.target;
    var postoString = $.trim($(posto).text());
    var postoId = $(posto).attr("id").substring(6);
    if ($(posto).hasClass("libero")) {
        $("#prenotazione-posto-id").val(postoId);
        $("#posto-id").text(postoString);
        $("#posto-id-2").val(postoString);
        $("#prenotazione-modal").modal();
    }
});

$("#prenota-form").submit(function (event) {
    event.preventDefault();
    var tipo = $("#prenotazione-tipo").val();
    var postoId = $("#prenotazione-posto-id").val();
    $.ajax({
        type: "GET",
        url: "aggiungiPrenotazioneTmp",
        data: "spettacolo=" + id_spettacolo + "&idPosto=" + postoId + "&prezzo=" + tipo,
        success: function(answer) {
            answer = $.trim(answer);
            if (answer == "success") {
                alert("OK");
            } else {
                alert("Errore");
            }
        }
    });
});


// Link che mostra descrizione completa

$('a.mostranascondi').click(function(){
    var id = this.id.substring(1);
    var prefix = this.id.substring(0,1);
    if(prefix === 'm'){
        $('#'+this.id).hide();
        $('#dm'+id).hide();
        $('#div'+id).show();
    }else{
        $('#div'+id).hide();
        $('#m' + id).show();
        $('#dm'+id).show();
    }                
});


// Admin

$(".posto-admin").click(function (event) {
    var posto = event.target;
    var postoString = $.trim($(posto).text());
    var postoId = $(posto).attr("id").substring(6);
    $("#cambia-stato-posto-id").val(postoId);
    $("#posto-id").text(postoString);
    $("#posto-id-2").val(postoString);
    $("#cambia-stato-modal").modal();
});