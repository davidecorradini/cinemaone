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
public class Film implements Serializable{
    private int idFilm;
    private int idGenere;
    private String titolo; //max size: 100
    private int durata; //durata in minuti
    private String UrlTrailer; //max size: 255
    private String UriLocandina; //max size: 255
    private String trama; //max size 1000

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
     * @return the titolo
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * @param titolo the titolo to set
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * @return the durata
     */
    public int getDurata() {
        return durata;
    }

    /**
     * @param durata the durata to set
     */
    public void setDurata(int durata) {
        this.durata = durata;
    }

    /**
     * @return the UrlTrailer
     */
    public String getUrlTrailer() {
        return UrlTrailer;
    }

    /**
     * @param UrlTrailer the UrlTrailer to set
     */
    public void setUrlTrailer(String UrlTrailer) {
        this.UrlTrailer = UrlTrailer;
    }

    /**
     * @return the UriLocandina
     */
    public String getUriLocandina() {
        return UriLocandina;
    }

    /**
     * @param UriLocandina the UriLocandina to set
     */
    public void setUriLocandina(String UriLocandina) {
        this.UriLocandina = UriLocandina;
    }

    /**
     * @return the trama
     */
    public String getTrama() {
        return trama;
    }

    /**
     * @param trama the trama to set
     */
    public void setTrama(String trama) {
        this.trama = trama;
    }
}
