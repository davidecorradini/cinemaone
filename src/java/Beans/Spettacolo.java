/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public Date getDataOra() {
        return new Date(timeStamp.getTime());
    }

    /**
     * @param timeStamp the timeStamp to set
     */
    public void setDataOra(Timestamp timeStamp) {
       this.timeStamp = timeStamp;
    }    
    
    /**
     * @return the data
     */
    public String getData(){
        Date date = new Date(this.getTimeStamp().getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        return df.format(date);    
    }

    /**
     * @return the ora
     */
    public String getOra(){
        Date date = new Date(this.getTimeStamp().getTime());
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(date);
    }
}
