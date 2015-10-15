/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author enrico
 */
public class SpettacoloSalaOrario {
    private Spettacolo spettacolo;
    private Film film;
    private Sala sala;
    private Genere genere;

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
     * @return the dataOra
     */
    public Timestamp getDataOra() {
        return spettacolo.getTimeStamp();
    }

    /**
     * @param dataOra the dataOra to set
     */
    public void setDataOra(Timestamp dataOra) {
        if(spettacolo == null){
            spettacolo = new Spettacolo();
        }
        spettacolo.setDataOra(dataOra);
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
     * @return the idSpettacolo
     */
    public Spettacolo getSpettacolo() {
        return spettacolo;
    }
    
    public void setSpettacolo(Spettacolo spettacolo){
        this.spettacolo = spettacolo;
    }
}