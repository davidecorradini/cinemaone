/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

/**
 *
 * @author enrico
 */
public class IncassoFilm {
    private Film film;
    private double incasso;

    /**
     * @return the idFilm
     */
    public Film getIdFilm() {
        return film;
    }

    /**
     * @param film the idFilm to set
     */
    public void setFilm(Film film) {
        this.film = film;
    }

    /**
     * @return the incasso
     */
    public double getIncasso() {
        return incasso;
    }

    /**
     * @param incasso the incasso to set
     */
    public void setIncasso(double incasso) {
        this.incasso = incasso;
    }
}
