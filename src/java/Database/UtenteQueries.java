/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import Beans.Utente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 *
 * @author enrico
 */
public class UtenteQueries {
    private final transient Connection con;
    
    public UtenteQueries(DBManager manager){
        this.con = manager.con;
    }
    
     public UtenteQueries(Connection con){
        this.con = con;
    }
    
    public static String encodeIdUtente(Object obj){
        
        if(obj instanceof Integer){
            return String.valueOf((char)1) + String.valueOf((int)obj);
        }
        if(obj instanceof String)
            return "t" + (String)obj;
        return null;
    }
    
    public static Object decodeIdUtente(String s){
        
        if(s.length()>0 && s.charAt(0) == (char)1){
            s=s.substring(1);
            return Integer.parseInt(s);
        }
        
        return s.substring(1);
        
    }
    
    public boolean emailValida(String email) throws SQLException{
        boolean res = false;
        PreparedStatement stm = stm = con.prepareStatement(
                "SELECT * FROM UTENTE WHERE EMAIL = ?");
        stm.setString(1, email);
        ResultSet rs = stm.executeQuery();
        if(rs.next()){
            res = true;
        }
        return res;
    }
    
    /**
     * cambia password all'utente. L'oggetto utente deve contenere l'id dell'utente e la nuova password nel campo password
     * @param ut utente di cui si vuole cambiare la password, nel campo password di tale oggetto deve esserci la nuova password
     * @throws SQLException
     */
    public void cambiaPassword(Utente ut) throws SQLException{
        cambiaPassword(ut.getEmail(), ut.getPassword());
    }
    /**
     * assegna la password all'utente con e-mail email.
     * @param email
     * @param password
     * @throws SQLException
     */
    public void cambiaPassword(String email, String password) throws SQLException{
        PreparedStatement stm = con.prepareStatement(
                "UPDATE UTENTE SET PASSWORD = ? WHERE EMAIL = ?");
        try {
            stm.setString(1, password);
            stm.setString(2, email);
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
    
    /**
     *
     * @param ut utente a cui aggiornare il credito, il credito settato nell'oggetto deve essere in nuovo credito.
     * @throws SQLException
     */
    public void aggiornaCredito(Utente ut) throws SQLException{
        PreparedStatement stm = con.prepareStatement("UPDATE UTENTE SET CREDITO=? WHERE ID_UTENTE=?");
        try {
            stm.setDouble(1, ut.getCredito());
            stm.setInt(2, ut.getIdUtente());
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
    
    
    /**
     * inserisce un nuovo utente nel database.
     * @param ut utente da aggiungere
     * @throws SQLException
     */
    public void aggiungiUtente(Utente ut) throws SQLIntegrityConstraintViolationException, SQLException{
        PreparedStatement stm = con.prepareStatement("INSERT INTO UTENTE (ID_RUOLO, EMAIL, PASSWORD, CREDITO) VALUES (?,?,?,?)");
        try {
            stm.setInt(1, ut.getIdRuolo());
            stm.setString(2, ut.getEmail());
            stm.setString(3, ut.getPassword());
            stm.setDouble(4, ut.getCredito());
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
}
