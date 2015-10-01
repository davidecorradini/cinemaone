// Slider

$(document).ready(function(){
    $('.bxslider').bxSlider({
        auto: true,
        pause: 6000
    });
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


// AJAX Logout
/*
$("#logout-link").click(function(event) {
    event.preventDefault();
    $("#menu").html("<i class=\"zmdi zmdi-rotate-left zmdi-hc-spin-reverse\"></i> Caricamento...");
    $.ajax({
        type: "GET",
        url: "logout",
        success: function(answer) {
            $.get("jsp/menu.jsp", function(data) {
                $("#menu").html(data);
            });
        }
    });
    return false;
});
*/


// AJAX SignUp

$("#recovery-form").submit(function(event) {
    
});


// Password Recovery

$("#recovery-form").submit(function(event) {
    event.preventDefault();
    $("#recovery-button").attr("disabled","disabled");
    $("#recovery-button").html("<i class=\"zmdi zmdi-rotate-left zmdi-hc-spin-reverse\"></i> Attendere");
    $.ajax({
        type: "POST",
        url: "recuperaPassword",
        data: "email=" + $("#recovery-email").val(),
        success: function(answer) {
            answer = answer.trim();
            if (answer == "success") {
                $("#recovery-sent").slideDown("slow");
            } else if (answer == "noemail") {
                $("#recovery-no-email").slideDown("slow");
            } else {
                $("#recovery-error").slideDown("slow");
            }
        }
    });
});


// Prenotazione

function updatePosti (spettacolo) {
    $.getJSON("http://web-dev.esy.es/cinemaone/statoPrenotazioni.php", "spettacolo=" + spettacolo, function (result) {
        $(".posto").each(function (i, element) {
            $(element).removeClass("occupato");
            $(element).removeClass("occupato-tmp");
            $(element).removeClass("selezionato");
            $(element).addClass("libero");
        });
        $.each(result, function (key, val) {
            var x, y, stato, timestamp, postoId;
            $.each(val, function (key2, val2) {
                if (key2 == "x") {
                    if (parseInt(val2) >= 10) {
                        x = val2;
                    } else {
                        x = "0" + val2;
                    }
                } else if (key2 == "y") {
                    y = val2;
                } else if (key2 == "stato") {
                    stato = val2;
                } else if (key2 == "timestamp") {
                    timestamp = val2;
                }
                postoId = y + x;
            });
            $(".posto").each(function (i, element) {
                if ($(element).text() == postoId) {
                    if (stato == "tmp") {
                        $(element).addClass("occupato-tmp");
                    } else if (stato == "occupato") {
                        $(element).addClass("occupato");
                    } else if (stato == "tuo") {
                        $(element).addClass("selezionato");
                    }
                }
            });
        });
        setTimeout(updatePosti, 1000);
    }).fail( function(d, textStatus, error) {
        setTimeout(updatePosti, 5000);
    });
}

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
    if ($(posto).hasClass("libero")) {
        $("#x").val(postoString.substr(0, 1));
        $("#y").val(parseInt(postoString.substr(1, 2)));
        $("#posto-id").text(postoString);
        $("#posto-id-2").val(postoString);
        $("#prenotazione-modal").modal();
    }
});

$("#prenota-form").submit(function (event) {
    event.preventDefault();
    var tipo = $("#prenotazione-tipo").val();
    var posto_x = $("#x").val();
    var posto_y = $("#y").val();
    $.ajax({
        type: "GET",
        url: "aggiungiPrenotazioneTmp",
        data: "spettacolo=" + id_spettacolo + "&x=" + posto_x + "&y=" + posto_y + "&tipo=" + tipo,
        success: function(answer) {
            answer = $.trim(answer);
            if (answer == "success") {
                // Prenotato!!!!!
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