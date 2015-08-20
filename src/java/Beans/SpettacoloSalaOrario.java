/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.sql.Timestamp;

/**
 *
 * @author enrico
 */
public class SpettacoloSalaOrario {
    private Film film;
    private Sala sala;
    private Timestamp dataOra;

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
        return dataOra;
    }

    /**
     * @param dataOra the dataOra to set
     */
    public void setDataOra(Timestamp dataOra) {
        this.dataOra = dataOra;
    }
    
}
