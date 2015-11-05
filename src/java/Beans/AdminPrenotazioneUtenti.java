/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

/**
 *
 * @author roberto
 */
public class AdminPrenotazioneUtenti {
    
    private Spettacolo spettacolo;
    private Film film;
    private Sala sala;
    private Genere genere;
    private Utente utente;
    private Prenotazione prenotazione;
    private Prezzo prezzo;
    private Ruolo ruolo;
    private Posto posto;

    /**
     * @return the spettacolo
     */
    public Spettacolo getSpettacolo() {
        return spettacolo;
    }

    /**
     * @param spettacolo the spettacolo to set
     */
    public void setSpettacolo(Spettacolo spettacolo) {
        this.spettacolo = spettacolo;
    }

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
     * @return the sala
     */
    public Sala getSala() {
        return sala;
    }

    /**
     * @param sala the sala to set
     */
    public void setSala(Sala sala) {
        this.sala = sala;
    }

    /**
     * @return the genere
     */
    public Genere getGenere() {
        return genere;
    }

    /**
     * @param genere the genere to set
     */
    public void setGenere(Genere genere) {
        this.genere = genere;
    }

    /**
     * @return the utente
     */
    public Utente getUtente() {
        return utente;
    }

    /**
     * @param utente the utente to set
     */
    public void setUtente(Utente utente) {
        this.utente = utente;
    }

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

    /**
     * @return the ruolo
     */
    public Ruolo getRuolo() {
        return ruolo;
    }

    /**
     * @param ruolo the ruolo to set
     */
    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
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
