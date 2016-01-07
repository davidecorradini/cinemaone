/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

public class PrenotazionePosto {
    private Prenotazione prenotazione;
    private Posto posto;

    /**
     * @return the prenotazione
     */
    public Prenotazione getPrenotazione() {
        return prenotazione;
    }

    /**
     * @param prenotazione the prenotazione to set
     */
    public void setPrenotazione(Prenotazione prenotazione) {
        this.prenotazione = prenotazione;
    }

    /**
     * @return the posto
     */
    public Posto getPosto() {
        return posto;
    }

    /**
     * @param posto the posto to set
     */
    public void setPosto(Posto posto) {
        this.posto = posto;
    }
}
