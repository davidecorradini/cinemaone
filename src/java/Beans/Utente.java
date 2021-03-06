/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author enrico
 */
public class Utente implements Serializable{
    private int idUtente;
    private String email;
    private String password;
    private double credito;
    private int idRuolo;
    
    /**
     * fa l'encode dell'id-utente.
     * @param obj id utente: Integer or String.
     * @return encoded id utente.
     */
    public static String encodeIdUtente(Object obj){
        final int dbFieldSize = 128;
        final String dateFormat = "MMddHHmmssSSS";
        String res;
        if(obj instanceof Integer){
            res = String.valueOf((char)1) + String.valueOf((int)obj);
        }else
            res = "t" + (String)obj;
        
        //check that the idUtente can be stored in the database.
        if(res.length()>dbFieldSize){
            MessageDigest messageDigest = null;
            try {
                messageDigest = MessageDigest.getInstance("SHA-512", "BC");
            } catch (NoSuchAlgorithmException | NoSuchProviderException ex) {
                try {
                    messageDigest = MessageDigest.getInstance("SHA-256");
                } catch (NoSuchAlgorithmException ex1) {
                    res = res.substring(0, dbFieldSize - dateFormat.length());
                }
            }
            if(messageDigest != null){
                messageDigest.update(res.getBytes());
                res = "t" + new String(messageDigest.digest());
                if(res.length()>dbFieldSize){
                    res = res.substring(0, dbFieldSize - dateFormat.length());
                }
            }
            if(res.length() + dateFormat.length() <= dbFieldSize){
                SimpleDateFormat df = new SimpleDateFormat(dateFormat);
                Date date = new Date(System.currentTimeMillis());
                System.out.println("date: " + df.format(date));
                res += df.format(date);
            }
        }
        return res;
        
    }
    
    /**
     * dall' encoded id all'id. 
     * @param s encoded id
     * @return oggetto di tipo string se id temporaneo, integer altrimenti.
     */
    public static Object decodeIdUtente(String s){
        
        if(s.length()>0 && s.charAt(0) == (char)1){
            s=s.substring(1);
            return Integer.parseInt(s);
        }
        
        return s.substring(1);
    }

    /**
     * @return the idUtente
     */
    public int getIdUtente() {
        return idUtente;
    }

    /**
     * @param idUtente the idUtente to set
     */
    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the credito
     */
    public double getCredito() {
        return credito;
    }

    /**
     * @param credito the credito to set
     */
    public void setCredito(double credito) {
        this.credito = credito;
    }

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
}
