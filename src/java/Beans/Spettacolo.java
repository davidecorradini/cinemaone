/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 *
 * @author enrico
 */
public class Spettacolo implements Serializable{
    private int idSpettacolo;
    private int idFilm;
    private int idSala;
    private Timestamp timeStamp;

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
     * @return the idFilm
     */
    public int getIdFilm() {
        return idFilm;
    }

    /**
     * @param idFilm the idFilm to set
     */
    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
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
     *@return the timestamp
     */
    public Timestamp getTimeStamp(){
        return timeStamp;
    }
    
    /**
     * @return the dataOra
     */
    public Data getDataOra() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeStamp.getTime());
        int giorno = cal.get(Calendar.DAY_OF_MONTH);
        int mese = cal.get(Calendar.MONTH);
        int anno = cal.get(Calendar.YEAR);
        int ore = cal.get(Calendar.HOUR);
        int minuti = cal.get(Calendar.MINUTE);
        return new Data(giorno, mese, anno, ore, minuti);
    }

    /**
     * @param timeStamp the timeStamp to set
     */
    public void setDataOra(Timestamp timeStamp) {
       this.timeStamp = timeStamp;
    }    
}
