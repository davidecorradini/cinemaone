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
public class Genere implements Serializable{
    private int idGenere;
    private String descrizione; //max size: 50

    /**
     * @return the idGenere
     */
    public int getIdGenere() {
        return idGenere;
    }

    /**
     * @param idGenere the idGenere to set
     */
    public void setIdGenere(int idGenere) {
        this.idGenere = idGenere;
    }

    /**
     * @return the descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * @param descrizione the descrizione to set
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
