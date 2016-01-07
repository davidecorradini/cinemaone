/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

public class IncassoFilm {
    private Film film;
    private double incasso;
    private int numSpett;
    private double incassoMedio;

    /**
     * @return the idFilm
     */
    public Film getFilm() {
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

    /**
     * @return the numSpett
     */
    public int getNumSpett() {
        return numSpett;
    }

    /**
     * @param numSpett the numSpett to set
     */
    public void setNumSpett(int numSpett) {
        this.numSpett = numSpett;
    }

    /**
     * @return the incassoMedio
     */
    public double getIncassoMedio() {
        return incassoMedio;
    }

    /**
     * @param incassoMedio the incassoMedio to set
     */
    public void setIncassoMedio(double incassoMedio) {
        this.incassoMedio = incassoMedio;
    }
}
