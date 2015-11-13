/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

public class SpesaUtente {
    private Utente ut;
    private int numPrenotazioni;
    private double spesaTot;

    /**
     * @return the ut
     */
    public Utente getUt() {
        return ut;
    }

    /**
     * @param ut the ut to set
     */
    public void setUt(Utente ut) {
        this.ut = ut;
    }

    /**
     * @return the numPrenotazioni
     */
    public int getNumPrenotazioni() {
        return numPrenotazioni;
    }

    /**
     * @param numPrenotazioni the numPrenotazioni to set
     */
    public void setNumPrenotazioni(int numPrenotazioni) {
        this.numPrenotazioni = numPrenotazioni;
    }

    /**
     * @return the spesaTot
     */
    public double getSpesaTot() {
        return spesaTot;
    }

    /**
     * @param spesaTot the spesaTot to set
     */
    public void setSpesaTot(double spesaTot) {
        this.spesaTot = spesaTot;
    }
}
