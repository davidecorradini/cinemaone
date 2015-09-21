// Slider

$(document).ready(function(){
    $('.bxslider').bxSlider({
        auto: true,
        pause: 6000,
        easing: "ease-in-out"
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

var postiSelezionati = [];

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

$(".posto").click(function (e) {
    var posto = e.target;
    var postoString = $(posto).text();
    $(".modal-title").text("Prenotazione Posto " + postoString);
    $("");
    $("#prenota-posto-modal").modal();
    /*
    if ($(posto).hasClass("libero")) {
        $(posto).removeClass("libero");
        $(posto).addClass("selezionato");
        addSelezionato(postoString);
    } else if ($(posto).hasClass("selezionato")) {
        $(posto).addClass("libero");
        $(posto).removeClass("selezionato");
        removeSelezionato(postoString);
    }*/
});


// Mostra/Nascondi

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