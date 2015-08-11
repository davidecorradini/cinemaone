/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.Serializable;

/**
 *
 * @author enrico
 */
public class Posto implements Serializable{
    private int idPosto;
    private int idSala;
    private int riga;
    private int colonna;
    private int stato; //semantica stato: 0 ok, -1 rotto.

    /**
     * @return the idPosto
     */
    public int getIdPosto() {
        return idPosto;
    }

    /**
     * @param idPosto the idPosto to set
     */
    public void setIdPosto(int idPosto) {
        this.idPosto = idPosto;
    }

    /**
     * @return the idSala
     */
    public int getIdSala() {
        return idSala;
    }

    /**
     * @param idSala the idSala to set
     */
    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    /**
     * @return the riga
     */
    public int getRiga() {
        return riga;
    }

    /**
     * @param riga the riga to set
     */
    public void setRiga(int riga) {
        this.riga = riga;
    }

    /**
     * @return the colonna
     */
    public int getColonna() {
        return colonna;
    }

    /**
     * @param colonna the colonna to set
     */
    public void setColonna(int colonna) {
        this.colonna = colonna;
    }

    /**
     * @return the stato
     */
    public int getStato() {
        return stato;
    }

    /**
     * @param stato the stato to set
     */
    public void setStato(int stato) {
        this.stato = stato;
    }
}
