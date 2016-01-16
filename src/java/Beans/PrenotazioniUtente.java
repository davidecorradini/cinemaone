/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.Serializable;
import java.sql.Timestamp;

public class PrenotazioniUtente implements Serializable {
    
    private Film film;
    private Timestamp dataOraSpettacolo;
    private Timestamp dataOraOperazione;
    private String nomeSala;
    private double tot; // totale soldi spesi per la prenotazione
    private int numBiglietti; // totale biglietti acquistati per la prenotazione

    /**
     * @return the film
     */
    public Film getFilm() {
        return film;
    }

    /**
     * @param film the film to set
     */
    public void setFilm(Film film) {
        this.film = film;
    }

    /**
     * @return the dataOraSpettacolo
     */
    public Timestamp getDataOraSpettacolo() {
        return dataOraSpettacolo;
    }

    /**
     * @param dataOraSpettacolo the dataOraSpettacolo to set
     */
    public void setDataOraSpettacolo(Timestamp dataOraSpettacolo) {
        this.dataOraSpettacolo = dataOraSpettacolo;
    }

    /**
     * @return the dataOraOperazione
     */
    public Timestamp getDataOraOperazione() {
        return dataOraOperazione;
    }

    /**
     * @param dataOraOperazione the dataOraOperazione to set
     */
    public void setDataOraOperazione(Timestamp dataOraOperazione) {
        this.dataOraOperazione = dataOraOperazione;
    }

    /**
     * @return the nomeSala
     */
    public String getNomeSala() {
        return nomeSala;
    }

    /**
     * @param nomeSala the nomeSala to set
     */
    public void setNomeSala(String nomeSala) {
        this.nomeSala = nomeSala;
    }

    /**
     * @return the tot
     */
    public double getTot() {
        return tot;
    }

    /**
     * @param tot the tot to set
     */
    public void setTot(double tot) {
        this.tot = tot;
    }

    /**
     * @return the numBiglietti
     */
    public int getNumBiglietti() {
        return numBiglietti;
    }

    /**
     * @param numBiglietti the numBiglietti to set
     */
    public void setNumBiglietti(int numBiglietti) {
        this.numBiglietti = numBiglietti;
    }
    
    
    
}
