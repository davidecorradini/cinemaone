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
public class Data implements Serializable{
    private int giorno;
    private int mese;
    private int anno;
    private int ore;
    private int minuti;

    public Data(int giorno, int mese, int anno, int ore, int minuti){
        this.giorno = giorno;
        this.mese = mese;
        this.anno = anno;
        this.ore = ore;
        this.minuti = minuti;
    }

    /**
     * @return the giorno
     */
    public int getGiorno() {
        return giorno;
    }

    /**
     * @param giorno the giorno to set
     */
    public void setGiorno(int giorno) {
        this.giorno = giorno;
    }

    /**
     * @return the mese
     */
    public int getMese() {
        return mese;
    }

    /**
     * @param mese the mese to set
     */
    public void setMese(int mese) {
        this.mese = mese;
    }

    /**
     * @return the anno
     */
    public int getAnno() {
        return anno;
    }

    /**
     * @param anno the anno to set
     */
    public void setAnno(int anno) {
        this.anno = anno;
    }

    /**
     * @return the ore
     */
    public int getOre() {
        return ore;
    }

    /**
     * @param ore the ore to set
     */
    public void setOre(int ore) {
        this.ore = ore;
    }

    /**
     * @return the minuti
     */
    public int getMinuti() {
        return minuti;
    }

    /**
     * @param minuti the minuti to set
     */
    public void setMinuti(int minuti) {
        this.minuti = minuti;
    }
    
}
