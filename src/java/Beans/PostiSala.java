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
    private ArrayList<Integer> colonna;

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
     * @return the colonna
     */
    public ArrayList<Integer> getColonna() {
        return colonna;
    }

    /**
     * @param colonna the colonna to set
     */
    public void setColonna(ArrayList<Integer> colonna) {
        this.colonna = colonna;
    }
}
