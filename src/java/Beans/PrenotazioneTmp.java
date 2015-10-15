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
public class PrenotazioneTmp {
    public static final int validity = 5; //minutes
    private String idUtente;
    private int idSpettacolo;
    private int idPosto;
    private Timestamp timestamp; 
    private int idPrezzo;

    /**
     * @return the idUtente
     */
    public String getIdUtente() {
        return idUtente;
    }

    /**
     * @param idUtente the idUtente to set
     */
    public void setIdUtente(String idUtente) {
        this.idUtente = idUtente;
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
     * @return the timestamp
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the idPrezzo
     */
    public int getIdPrezzo() {
        return idPrezzo;
    }

    /**
     * @param idPrezzo the tipo to set
     */
    public void setIdPrezzo(int idPrezzo) {
        this.idPrezzo = idPrezzo;
    }

    
}
