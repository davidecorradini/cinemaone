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
    private int idSpettacolo;
    private Film film;
    private Sala sala;
    private Genere genere;
    private Timestamp dataOra;
    private String data;
    private String ora;

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
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return the ora
     */
    public String getOra() {
        return ora;
    }

    /**
     * @param ora the ora to set
     */
    public void setOra(String ora) {
        this.ora = ora;
    }

    /**
     * @return the idSpettacolo
     */
    public int getIdSpettacolo() {
        return idSpettacolo;
    }

    /**
     * @param idSpettacolo the idSpettacolo to set
     */
    public void setIdSpettacolo(int idSpettacolo) {
        this.idSpettacolo = idSpettacolo;
    }
    
}