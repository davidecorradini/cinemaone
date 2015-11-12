/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

/**
 *
 * @author alessandro
 */
public class InfoUtenti {
    private int utentiRegistrati;
    private String emailPostiPiuPrenotati;
    private int numeroPosti;
    private String emailTotalePiuAlto;
    private double totale;

    /**
     * @return the utentiRegistrati
     */
    public int getUtentiRegistrati() {
        return utentiRegistrati;
    }

    /**
     * @param utentiRegistrati the utentiRegistrati to set
     */
    public void setUtentiRegistrati(int utentiRegistrati) {
        this.utentiRegistrati = utentiRegistrati;
    }

    /**
     * @return the emailPostiPiuPrenotati
     */
    public String getEmailPostiPiuPrenotati() {
        return emailPostiPiuPrenotati;
    }

    /**
     * @param emailPostiPiuPrenotati the emailPostiPiuPrenotati to set
     */
    public void setEmailPostiPiuPrenotati(String emailPostiPiuPrenotati) {
        this.emailPostiPiuPrenotati = emailPostiPiuPrenotati;
    }

    /**
     * @return the numeroPosti
     */
    public int getNumeroPosti() {
        return numeroPosti;
    }

    /**
     * @param numeroPosti the numeroPosti to set
     */
    public void setNumeroPosti(int numeroPosti) {
        this.numeroPosti = numeroPosti;
    }

    /**
     * @return the emailTotalePiuAlto
     */
    public String getEmailTotalePiuAlto() {
        return emailTotalePiuAlto;
    }

    /**
     * @param emailTotalePiuAlto the emailTotalePiuAlto to set
     */
    public void setEmailTotalePiuAlto(String emailTotalePiuAlto) {
        this.emailTotalePiuAlto = emailTotalePiuAlto;
    }

    /**
     * @return the totale
     */
    public double getTotale() {
        return totale;
    }

    /**
     * @param totale the totale to set
     */
    public void setTotale(double totale) {
        this.totale = totale;
    }
    
}
