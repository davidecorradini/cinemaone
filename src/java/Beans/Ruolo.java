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
public class Ruolo implements Serializable{
    private int idRuolo;
    private String ruolo;

    /**
     * @return the idRuolo
     */
    public int getIdRuolo() {
        return idRuolo;
    }

    /**
     * @param idRuolo the idRuolo to set
     */
    public void setIdRuolo(int idRuolo) {
        this.idRuolo = idRuolo;
    }

    /**
     * @return the ruolo
     */
    public String getRuolo() {
        return ruolo;
    }

    /**
     * @param ruolo the ruolo to set
     */
    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }
}
