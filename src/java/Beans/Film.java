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
    private String urlTrailer; //max size: 255
    private String uriLocandina; //max size: 255
    private String trama; //max size 1000
    private boolean isInSlider;
    private int anno;
    private String regista;

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
        return urlTrailer;
    }

    /**
     * @param urlTrailer the UrlTrailer to set
     */
    public void setUrlTrailer(String urlTrailer) {
        //watch?v= ->  embed/
        this.urlTrailer = urlTrailer.replaceFirst("watch?v=", "embed/");
    }

    /**
     * @return the UriLocandina
     */
    public String geturiLocandina() {
        return uriLocandina;
    }

    /**
     * @param uriLocandina the UriLocandina to set
     */
    public void setUriLocandina(String uriLocandina) {
        this.uriLocandina = uriLocandina;
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

    /**
     * @return the IsInSlider
     */
    public boolean isInSlider() {
        return isInSlider;
    }

    /**
     * @param isInSlider the isInSlider to set
     */
    public void setisInSlider(boolean isInSlider) {
        this.isInSlider = isInSlider;
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
     * @return the regista
     */
    public String getRegista() {
        return regista;
    }

    /**
     * @param regista the regista to set
     */
    public void setRegista(String regista) {
        this.regista = regista;
    }
}
