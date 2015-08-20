/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.ArrayList;

/**
 *
 * @author enrico
 */
public class FilmSpettacoli {
    private Film film;
    private ArrayList<Spettacolo> spettacoli;

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
     * @return the spettacoli
     */
    public ArrayList<Spettacolo> getSpettacoli() {
        return spettacoli;
    }

    /**
     * @param spettacoli the spettacoli to set
     */
    public void setSpettacoli(ArrayList<Spettacolo> spettacoli) {
        this.spettacoli = spettacoli;
    }
}
