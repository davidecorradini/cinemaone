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
    var seats = new Array();
    var currentSeats = new Array();
    var oldSeats = new Array();
    var interval = 1000;
    setInterval(function () {
        $.getJSON("statoPrenotazioni", "spettacolo=" + spettacolo, function (result) {
            // Parsing dell'oggeto JSON
            $.each(result, function (key1, val1) {
                var idPosto, timestamp, stato, prezzo, x, y;
                idPosto = key1;
                $.each(val1, function (key2, val2) {
                    if (key2 == "timestamp") {
                        timestamp = parseInt(val2);
                    } else if (key2 == "stato") {
                        stato = val2;
                    } else if (key2 == "prezzo") {
                        prezzo = parseInt(val2);
                    } else if (key2 == "y") {
                        y = val2.toUpperCase();
                    } else if (key2 == "x") {
                        x = val2;
                        if (parseInt(x) < 10) {
                            x = "0" + x;
                        }
                    }
                });
                var currentObject = {"idPosto": idPosto, "timestamp": timestamp, "stato": stato, "prezzo": prezzo, "y": y, "x": x};
                seats.push(currentObject);
                currentSeats.push(currentObject.idPosto);
            });
            // Reset
            $(".posto").each(function (index, element) {
                $(element).removeClass("tuo");
                $(element).removeClass("tuo-tmp");
                $(element).removeClass("occupato");
                $(element).removeClass("occupato-tmp");
            });
            $("#posti-selezionati-list").html("");
            // Fill
            $.each(seats, function (index, object) {
                console.log("idPosto: " + object.idPosto + ", timestamp: " + object.timestamp + ", stato: " + object.stato + ", prezzo: " + object.prezzo + ", x/y: " + object.y + object.x);
                $("#posto-" + object.idPosto).addClass(object.stato);
                if (object.stato == "tuo-tmp") {
                    var percentuale = (object.timestamp / 300) * 100;
                    var m = Math.floor(object.timestamp / 60);
                    var s = object.timestamp % 60;
                    var mm = "0" + m;
                    var ss;
                    if (s < 10) {
                        ss = "0" + s;
                    } else {
                        ss = s;
                    }
                    var remaining = mm + ":" + ss;
                    if (object.timestamp == 1) {
                        $("#posti-selezionati-list").append("<div class=\"prenotazione-container\" id=\"prenotazione-" + object.idPosto + "\"><div class=\"progress-bar-light\"><div class=\"progress-bar-dark\" style=\"width:" + percentuale + "%;\"></div></div><div class=\"selezionato-container\"><div class=\"posto-side tuo-tmp\">" + object.y + object.x + "</div><strong>" + prezzi[object.prezzo][1] + "</strong> " + prezzi[object.prezzo][0] + "<div class=\"delete-posto\"><i class=\"zmdi zmdi-timer\"></i> " + remaining + " <a href=\"#\" id=\"delete-posto\"><i class=\"zmdi zmdi-close\"></i></a></div></div></div>");
                        setTimeout(function () { $("#prenotazione-" + object.idPosto).slideUp(250); }, 700);
                    } else {
                        if ($.inArray(object.idPosto, currentSeats) > -1 && $.inArray(object.idPosto, oldSeats) == -1) {
                            $("#posti-selezionati-list").append("<div class=\"prenotazione-container\" id=\"prenotazione-" + object.idPosto + "\" style=\"display: none;\"><div class=\"progress-bar-light\"><div class=\"progress-bar-dark\" style=\"width:" + percentuale + "%;\"></div></div><div class=\"selezionato-container\"><div class=\"posto-side tuo-tmp\">" + object.y + object.x + "</div><strong>" + prezzi[object.prezzo][1] + "</strong> " + prezzi[object.prezzo][0] + "<div class=\"delete-posto\"><i class=\"zmdi zmdi-timer\"></i> " + remaining + " <a href=\"#\" id=\"delete-posto\"><i class=\"zmdi zmdi-close\"></i></a></div></div></div>");
                            $("#prenotazione-" + object.idPosto).slideDown(500);
                        } else {
                            $("#posti-selezionati-list").append("<div class=\"prenotazione-container\" id=\"prenotazione-" + object.idPosto + "\"><div class=\"progress-bar-light\"><div class=\"progress-bar-dark\" style=\"width:" + percentuale + "%;\"></div></div><div class=\"selezionato-container\"><div class=\"posto-side tuo-tmp\">" + object.y + object.x + "</div><strong>" + prezzi[object.prezzo][1] + "</strong> " + prezzi[object.prezzo][0] + "<div class=\"delete-posto\"><i class=\"zmdi zmdi-timer\"></i> " + remaining + " <a href=\"#\" id=\"delete-posto\"><i class=\"zmdi zmdi-close\"></i></a></div></div></div>");
                        }
                    }
                }
            });
            
            
            
            /*$("#no-selected").show();
            $("#totale-bottone").hide();
            $(".posto").each(function (i, element) {
                $(element).removeClass("occupato");
                $(element).removeClass("occupato-tmp");
                $(element).removeClass("selezionato");
                $(element).addClass("libero");
                $(element).prop('title', '');
            });
            $("#posti-selezionati-list").html("");
            $.each(result, function (key, val) {
                var json = jQuery.parseJSON(val);
                console.log(key + " - " + val);
            });
                var x, y, stato, timestamp, postoName, prezzo;
                $.each(val, function (key2, val2) {
                    if (key2 == "x") {
                        if (parseInt(val2) >= 10) {
                            x = val2;-
                        } else {
                            x = "0" + val2;
                        }
                    } else if (key2 == "y") {
                        y = val2.toString().toUpperCase();
                    } else if (key2 == "stato") {
                        stato = val2;
                    } else if (key2 == "timestamp") {
                        remaining = parseInt(val2);
                        var m = Math.floor(remaining / 60);
                        var s = remaining % 60;
                        var mm = "0" + m;
                        var ss;
                        if (s < 10) {
                            ss = "0" + s;
                        } else {
                            ss = s;
                        }
                        timestamp = mm + ":" + ss;
                    } else if (key2 == "prezzo") {
                        prezzo = parseInt(val2);
                    }
                    postoName = y + x;
                    if (stato == "tuo-tmp")
                        currentSeats.push(postoName);
                });
                $("#no-selected").show();
                $("#totale-bottone").hide();
                $(".posto").each(function (i, element) {
                    if ($(element).text() == postoName) {
                        if (stato == "occupato-tmp") {
                            $(element).removeClass("libero");
                            $(element).addClass("occupato-tmp");
                            // TODO: mostra timer
                        } else if (stato == "occupato") {
                            $(element).removeClass("libero");
                            $(element).addClass("occupato");
                            $(element).prop('title', '');
                            $(element).prop('data-original-title', '');
                        } else if (stato == "tuo") {
                            $(element).removeClass("libero");
                            $(element).addClass("selezionato");
                            $(element).prop('title', '');
                            $(element).prop('data-original-title', '');
                        } else if (stato == "tuo-tmp") {
                            if (remaining == 1)
                                setTimeout(function () {
                                    $("#prenotazione-" + postoName).slideUp(200);
                                }, 700);
                            $(element).removeClass("libero");
                            $(element).addClass("selezionato");
                            //$(element).attr('title', timestamp).tooltip('fixTitle').data('bs.tooltip').$tip.find('.tooltip-inner').text(timestamp);
                            var price = parseFloat(prezzi[prezzo][1].substr(7).replace(",", "."));
                            totale = totale + price;
                            percentuale = (remaining / 300.) * 100.;
                            if ($.inArray(postoName, currentSeats) > -1 && $.inArray(postoName, oldSeats) < 0) {
                                $("#posti-selezionati-list").append("<div class=\"prenotazione-container\" id=\"new-prenotazione\" style=\"display: none;\"><div class=\"progress-bar-light\"><div class=\"progress-bar-dark\" style=\"width:" + percentuale + "%;\"></div></div><div class=\"selezionato-container\"><div class=\"posto-side selezionato\">" + postoName + "</div><strong>" + prezzi[prezzo][1] + "</strong> " + prezzi[prezzo][0] + "<div class=\"delete-posto\"><i class=\"zmdi zmdi-timer\"></i> " + timestamp + " <a href=\"#\" id=\"delete-posto\"><i class=\"zmdi zmdi-close\"></i></a></div></div></div>");
                                $("#new-prenotazione").each(function (i, element) {
                                    setTimeout($(element.currentTarget).slideDown(700), 100);
                                });
                            } else {
                                
                                if (remaining == 1)
                                    lastSecond = " last-second";
                                else
                                    lastSecond = "";
                                $("#posti-selezionati-list").append("<div class=\"prenotazione-container" + lastSecond + "\" id=\"prenotazione-" + postoName + "\"><div class=\"progress-bar-light\"><div class=\"progress-bar-dark\" style=\"width:" + percentuale + "%;\"></div></div><div class=\"selezionato-container\"><div class=\"posto-side selezionato\">" + postoName + "</div><strong>" + prezzi[prezzo][1] + "</strong> " + prezzi[prezzo][0] + "<div class=\"delete-posto\"><i class=\"zmdi zmdi-timer\"></i> " + timestamp + " <a href=\"#\" id=\"delete-posto\"><i class=\"zmdi zmdi-close\"></i></a></div></div></div>");
                            }
                        }
                    }
                });
                if ($.trim($("#posti-selezionati-list").html()) == "") {
                    //$("#no-selected").slideDown(500);
                    //$("#totale-bottone").slideUp(200);
                } else {
                    
                    $("#no-selected").hide();
                    $("#totale-bottone").show();
                    
                }
                $("#totale").html("&euro; " + totale.toFixed(2));
                
            });
            interval = 1000;*/
        }).fail( function(d, textStatus, error) {
            interval = 5000;
        });
        oldSeats = currentSeats.slice();
        currentSeats.splice(0,currentSeats.length);
        seats.splice(0,seats.length);
    }, interval);
}

// db-fail: errore nel database, probabile posto doppio

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
                $("#prenotazione-modal").modal("hide");
            } else {
                alert("Errore");
            }
        }
    });
});

$("#procedi-button").click(function () {
    if (true) {
        $("#pagamento-modal").modal();
    } else {
        $("#login-modal").modal();
    }
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


$("#cerca-spettacoli").submit(function (event) {
    event.preventDefault();
    $('#spettacoli').html("");
    var titolo = $("#titolo").val();
    var genere = $("#genere").val();
    var regista = $("#regista").val();
    var sala = $("#sala").val();
    var durataMin = $("#durataMin").val();
    var durataMax = $("#durataMax").val();
    var programmazioneDa = $("#programmazioneDa").val();
    var programmazioneA = $("#programmazioneA").val();
    $.getJSON("../AdminGetSpettacoli", "titolo=" + titolo + "&genere=" + genere + "&regista=" + regista + "&sala=" + sala + "&durataMin=" + durataMin + "&durataMax=" + durataMax + "&programmazioneDa=" + programmazioneDa + "&programmazioneA=" + programmazioneA, function (result) {
        $.each(result, function (key, val) {
            var idFilm, titolo, regista, genere, anno, durata, data, ora, sala, idSpettacolo;
            idSpettacolo = key;
            $.each(val, function (key2, val2) {
                if(key2 == "idFilm")
                    idFilm = val2;
                else if(key2 == "titolo")
                    titolo = val2;
                else if(key2 == "regista")
                    regista = val2;
                else if(key2 == "genere")
                    genere = val2;
                else if(key2 == "anno")
                    anno = val2;
                else if(key2 == "durata")
                    durata = val2;
                else if(key2 == "data")
                    data = val2;
                else if(key2 == "ora")
                    ora = val2;
                else if(key2 == "sala")
                    sala = val2;
                $('#spettacoli').append("\n\
        <tr>\n\
            <td><a class=\"no-color\" href=\"../dettaglio-film.html?idfilm=" + idFilm + ">" + titolo + "</a> <small class=\"text-muted\">" + regista + " &middot; " + anno + " &middot; " + genere + " &middot; " + durata + "min</small></td>\n\
            <td class=\"text-center\">" + data + "</td>\n\
            <td class=\"text-center\">" + ora + "</td>\n\
            <td class=\"text-center\">" + sala + "</td>\n\
            <td class=\"text-center\"><a class=\"no-color\" href=\"spettacoli.html?idspettacolo=" + idSpettacolo + "\"><i class=\"zmdi zmdi-calendar-check\"></i></a></td>\n\
        </tr>\n\
                ");
            });
        });
    });
});

$("#cerca-prenotazioni").submit(function (event) {
    event.preventDefault();
    $('#prenotazioni').html("");
    var titolo = $("#titolo").val();
    var genere = $("#genere").val();
    var regista = $("#regista").val();
    var sala = $("#sala").val();
    var durataMin = $("#durataMin").val();
    var durataMax = $("#durataMax").val();
    var programmazioneDa = $("#programmazioneDa").val();
    var programmazioneA = $("#programmazioneA").val();
    var email = $("#email").val();
    var ruolo = $("#ruolo").val();
    var prenotazioneDa = $("#prenotazioneDa").val();
    var prenotazioneA = $("#prenotazioneA").val();
    var tipoPrezzo = $("#tipoPrezzo").val();
    var riga = $("#riga").val();
    var colonna = $("#colonna").val();    
    $.getJSON("../AdminGetPrenotazioni", "titolo=" + titolo + "&genere=" + genere + "&regista=" + regista + "&sala=" + sala + "&durataMin=" + durataMin + "&durataMax=" + durataMax + "&programmazioneDa=" + programmazioneDa + "&programmazioneA=" + programmazioneA + "&email=" + email + "&ruolo=" + ruolo + "&prenotazioneDa=" + prenotazioneDa + "&prenotazioneA=" + prenotazioneA + "&tipoPrezzo=" + tipoPrezzo + "&riga=" + riga + "&colonna=" + colonna, function (result) {
        $.each(result, function (key, val) {
            var idFilm, titolo, regista, genere, anno, durata, dataSpettacolo, dataPrenotazione, sala, utente, biglietto, posto, idPrenotazione;
            idPrenotazione = key;
            $.each(val, function (key2, val2) {
                if(key2 == "idFilm")
                    idFilm = val2;
                else if(key2 == "titolo")
                    titolo = val2;
                else if(key2 == "regista")
                    regista = val2;
                else if(key2 == "genere")
                    genere = val2;
                else if(key2 == "anno")
                    anno = val2;
                else if(key2 == "durata")
                    durata = val2;
                else if(key2 == "dataSpettacolo")
                    dataSpettacolo = val2;
                else if(key2 == "dataPrenotazione")
                    dataPrenotazione = val2;
                else if(key2 == "sala")
                    sala = val2;
                else if(key2 == "utente")
                    utente = val2;
                else if(key2 == "biglietto")
                    biglietto = val2;
                else if(key2 == "posto")
                    posto = val2;
                $('#spettacoli').append("\n\
        <tr id=\"id-" + idPrenotazione + "\">\n\
            <td><a class=\"no-color\" href=\"../dettaglio-film.html?idfilm=" + idFilm + ">" + titolo + "</a> <small class=\"text-muted\">" + regista + " &middot; " + anno + " &middot; " + genere + " &middot; " + durata + "min</small></td>\n\
            <td class=\"text-center\">" + dataSpettacolo + "</td>\n\
            <td class=\"text-center\">" + sala + "</td>\n\
            <td class=\"text-center\">" + utente + "</td>\n\
            <td class=\"text-center\">" + dataPrenotazione + "</td>\n\
            <td class=\"text-center\">" + biglietto + "</td>\n\
            <td class=\"text-center\">" + posto + "</td>\n\
            <td class=\"text-center\"><a class=\"no-color rimuovi\" href=\"id-" + idPrenotazione + "\" id=\"delete-" + idPrenotazione + "\"><i class=\"zmdi zmdi-delete\"></i></a></td>\n\
        </tr>\n\
                ");
            });
        });
    });
});

$("a.rimuovi").click(function (event) {
    var posto = event.target;
    var postoId = $(posto).attr("id").substring(7);
    $("#id-prenotazione").val(postoId);
    $("#elimina-prenotazione").modal();
});

$("#delete-form").submit(function (event) {
    event.preventDefault();
    var idPrenotazione = $("#id-prenotazione").val();
    $.ajax({
        type: "GET",
        url: "../DeletePrenotazione",
        data: "idPrenotazione=" + idPrenotazione,
        success: function(answer) {
            answer = $.trim(answer);
            if (answer == "success") {
                $('#id-' + idPrenotazione).slideUp("fast");
                $("#elimina-prenotazione").modal("hide");
            } else {
                alert("Errore");
            }
        }
    });    
});

$("#resetPassword").submit(function (event) {
    event.preventDefault();
    $("#reset-wrong-password").slideUp("fast");
    $("#reset-error").slideUp("fast");
    var email = $("#email").val();
    var password1 = $("#password1").val();
    var password2 = $("#password2").val();
    $.ajax({
        type: "GET",
        url: "resetPassword",
        data: "email=" + email + "&password1=" + password1 + "&password2=" + password2,
        success: function(answer) {
            answer = $.trim(answer);
            if (answer == "success") {
                $("#containerResetPassword").html("<div class=\"page-header col-md-6 col-md-offset-3\"><h1>Password modificata</h1></div>");
            } else if(answer == "wrong-password"){
                $("#reset-wrong-password").slideDown("slow");
            } else {
                $("#reset-error").slideDown("slow");
            }
        }
    });
});

function updatePostiAdmin (spettacolo) {
    var interval = 1000;
    setInterval(function () {
        $.getJSON("../statoPrenotazioni", "spettacolo=" + spettacolo, function (result) {
            $(".posto").each(function (i, element) {
                $(element).removeClass("occupato");
                $(element).removeClass("occupato-tmp");
                $(element).removeClass("selezionato");
                $(element).addClass("libero");
                $(element).prop('title', '');
            });
            $.each(result, function (key, val) {
                var x, y, stato, timestamp, postoName, prezzo;
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
                        var m = Math.floor(remaining / 60);
                        var s = remaining % 60;
                        var mm = "0" + m;
                        var ss;
                        if (s < 10) {
                            ss = "0" + s;
                        } else {
                            ss = s;
                        }
                        timestamp = mm + ":" + ss;
                    } else if (key2 == "prezzo") {
                        prezzo = parseInt(val2);
                    }
                    postoName = y + x;
                });
                $(".posto").each(function (i, element) {
                    if ($(element).text() == postoName) {
                        if (stato == "occupato-tmp") {
                            $(element).removeClass("libero");
                            $(element).addClass("occupato-tmp");
                            // TODO: mostra timer
                        } else if (stato == "occupato") {
                            $(element).removeClass("libero");
                            $(element).addClass("occupato");
                            $(element).prop('title', '');
                            $(element).prop('data-original-title', '');
                        } else if (stato == "tuo") {
                            $(element).removeClass("libero");
                            $(element).addClass("selezionato");
                            $(element).prop('title', '');
                            $(element).prop('data-original-title', '');
                        } else if (stato == "tuo-tmp") {
                            $(element).removeClass("libero");
                            $(element).addClass("selezionato");
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

// AJAX Login

$("#login-form-admin").submit(function(event) {
    event.preventDefault();
    $("#login-button").attr("disabled","disabled");
    $("#login-button").html("<i class=\"zmdi zmdi-rotate-left zmdi-hc-spin-reverse\"></i> Accesso");
    $.ajax({
        type: "POST",
        url: "../check-login",
        data: "username=" + $("#input-email").val() + "&password=" + $("#input-password").val(),
        success: function(answer) {
            answer = answer.trim();
            if(answer == "success") {
                $("#login-modal").modal("hide");
                window.location.reload();
            } else {
                $("#login-button").html("Accedi");
                $("#login-button").removeAttr("disabled");
                $("#login-modal").effect("shake");
            }
        }
    });
});


// AJAX SignUp

$("#signup-form-admin").submit(function(event) {
    event.preventDefault();
    $("#signup-existing").slideUp("fast");
    $("#signup-wrong-password").slideUp("fast");
    $("#signup-success").slideUp("fast");
    $("#signup-button").attr("disabled", "disabled");
    $("#signup-button").html("<i class=\"zmdi zmdi-rotate-left zmdi-hc-spin-reverse\"></i> Attendere");
    $.ajax({
        type: "POST",
        url: "../signUp",
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

$("#recovery-form-admin").submit(function(event) {
    event.preventDefault();
    $("#recovery-sent").slideUp("fast");
    $("#recovery-no-email").slideUp("fast");
    $("#recovery-error").slideUp("fast");
    $("#recovery-button").attr("disabled", "disabled");
    $("#recovery-button").html("<i class=\"zmdi zmdi-rotate-left zmdi-hc-spin-reverse\"></i> Attendere");
    $.ajax({
        type: "POST",
        url: "../recuperaPassword",
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