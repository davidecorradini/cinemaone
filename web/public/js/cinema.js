// Slider

$(function() {
    $('.banner').unslider({
        speed: 500,
        delay: 3000,
        keys: true,
        dots: true,
        fluid: false
    });
});


// Prenotazione

function updatePosti (spettacolo) {
    $.getJSON("http://192.168.1.5:8084/Multisala/statoPrenotazioni", "spettacolo=1", function (result) {
        //$.each();
        alert(result);
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
    if ($(posto).hasClass("libero")) {
        $(posto).removeClass("libero");
        $(posto).addClass("selezionato");
        addSelezionato(postoString);
    } else if ($(posto).hasClass("selezionato")) {
        $(posto).addClass("libero");
        $(posto).removeClass("selezionato");
        removeSelezionato(postoString);
    }
});