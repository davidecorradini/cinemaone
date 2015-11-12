/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Beans.Utente;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author enrico
 */
public class ConvalidaUtentiQueries {
     private final transient Connection con;
    
    public ConvalidaUtentiQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public ConvalidaUtentiQueries(Connection con){
        this.con = con;
    }
    
    private String computeHash(Utente ut, Timestamp time) throws NoSuchAlgorithmException{
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        md.update((ut.getEmail()+ut.getPassword()+ut.getCredito()+ut.getIdRuolo() + time).getBytes());
        byte[] hash = md.digest();
        return new String(hash);
    }
    
    public String aggiungiRichiestaConvalida(Utente utente) throws SQLException, NoSuchAlgorithmException{
        removeOldRequests();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String hash = computeHash(utente, timestamp);
        PreparedStatement stm;
               stm = con.prepareStatement(
                       "INSERT INTO CONVALIDAUTENTI (HASH, EMAIL, PASSWORD, CREDITO, ID_RUOLO, TIME) VALUES (?, ?, ?, ?, ?, ?)");
        try{
            stm.setString(1, hash);
            stm.setString(2, utente.getEmail());
            stm.setString(3, utente.getPassword());
            stm.setDouble(4, utente.getCredito());
            stm.setInt(5, utente.getIdRuolo());
            stm.setTimestamp(6, timestamp);
            
            stm.executeUpdate();
        } finally {
            stm.close();
        }
        return hash;
    }
    
    public Utente convalidaUtente(String id) throws SQLException, NoSuchAlgorithmException{
        if(id == null) return null;
        Utente res = null;
        Timestamp timestamp = null;
        PreparedStatement stm = con.prepareStatement(
                "SELECT * FROM CONVALIDAUTENTI WHERE HASH = ?");
        stm.setString(1, id);
        ResultSet rs = stm.executeQuery();
        if(rs.next()){
            res = new Utente();
            res.setEmail(rs.getString("EMAIL"));
            res.setPassword(rs.getString("PASSWORD"));
            res.setCredito(rs.getDouble("CREDITO"));
            res.setIdRuolo(rs.getInt("ID_RUOLO"));
            timestamp = rs.getTimestamp("TIME");
        }
        
        String computedHash = computeHash(res, timestamp);
        
        if(!computedHash.equals(id)) return null; //se l'hash non coincide.
        
        PreparedStatement stm1 = con.prepareStatement( "DELETE FROM CONVALIDAUTENTI P WHERE P.EMAIL=?");
        try{
        stm1.setString(1, res.getEmail());
        stm.executeUpdate();
        }finally{
            stm.close();
        }
        
        UtenteQueries utQuery = new UtenteQueries(con);
        utQuery.aggiungiUtente(res);
        return res;
    }
    
    public void removeOldRequests() throws SQLException{
        PreparedStatement stm = con.prepareStatement( "DELETE FROM CONVALIDAUTENTI P WHERE P.TIME< ?");
        try{
        stm.setLong(1, System.currentTimeMillis() + 24*60*60*1000); //durata massima di 1 giorno.
        stm.executeUpdate();
        }finally{
            stm.close();
        }
    }
}
