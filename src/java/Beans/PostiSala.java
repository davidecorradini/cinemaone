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
public class PostiSala {
    private char riga;
    private ArrayList<Integer[]> colonnaStato; //in 0 il numero di colonna, in 1 lo stato

    /**
     * @return the riga
     */
    public char getRiga() {
        return riga;
    }

    /**
     * @param riga the riga to set
     */
    public void setRiga(char riga) {
        this.riga = riga;
    }

    /**
     * @return the colonnaStato
     */
    public ArrayList<Integer[]> getColonnaStato() {
        return colonnaStato;
    }

    /**
     * @param colonna the colonnaStato to set
     */
    public void setColonna(ArrayList<Integer[]> colonna) {
        this.colonnaStato = colonna;
    }
}
