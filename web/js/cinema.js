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
                currentSeats.push(currentObject);
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
            $.each(currentSeats, function (index, object) {
                console.log("idPosto: " + object.idPosto + ", timestamp: " + object.timestamp + ", stato: " + object.stato + ", prezzo: " + object.prezzo + ", x/y: " + object.y + object.x);
                $("#posto-" + object.idPosto).addClass(object.stato);
                var slideIn = "";
                if (oldSeats.indexOf(object) == -1) {
                    slideIn = " slide-in";
                    console.log("new object");
                }
                console.log("indexOf old: " + oldSeats.indexOf(object));
                console.log("indexOf current:" + currentSeats.indexOf(object));
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
                $("#posti-selezionati-list").append("<div class=\"prenotazione-container" + slideIn + "\"><div class=\"progress-bar-light\"><div class=\"progress-bar-dark\" style=\"width:" + percentuale + "%;\"></div></div><div class=\"selezionato-container\"><div class=\"posto-side tuo-tmp\">" + object.y + object.x + "</div><strong>" + prezzi[object.prezzo][1] + "</strong> " + prezzi[object.prezzo][0] + "<div class=\"delete-posto\"><i class=\"zmdi zmdi-timer\"></i> " + remaining + " <a href=\"#\" id=\"delete-posto\"><i class=\"zmdi zmdi-close\"></i></a></div></div></div>");
                $(".slide-in").each(function (index, element) {
                    $(element).slideDown(500);
                });
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
        console.log(currentSeats + " <> " + oldSeats);
        oldSeats = JSON.parse(JSON.stringify(currentSeats));
        console.log(currentSeats + " <> " + oldSeats);
        currentSeats.splice(0, currentSeats.length);
        console.log(currentSeats + " <> " + oldSeats);
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