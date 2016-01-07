/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

public class PrenotazionePostoPrezzo extends PrenotazionePosto{
    private Prezzo prezzo;

    /**
     * @return the prezzo
     */
    public Prezzo getPrezzo() {
        return prezzo;
    }

    /**
     * @param prezzo the prezzo to set
     */
    public void setPrezzo(Prezzo prezzo) {
        this.prezzo = prezzo;
    }
}
