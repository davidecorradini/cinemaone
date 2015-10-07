/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import Beans.Prenotazione;
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
public class PrenotazioneQueries{
    private final transient Connection con;
    
    public PrenotazioneQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public PrenotazioneQueries(Connection con){
        this.con = con;
    }
    
    /**
     * inserisce una nuova prenotazione.
     * @param preno prenotazione da memorizzare
     * @throws java.sql.SQLIntegrityConstraintViolationException
     * @throws SQLException
     */
    public void storePrenotazione(Prenotazione preno) throws SQLIntegrityConstraintViolationException, SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement(
                "INSERT INTO PRENOTAZIONE (ID_UTENTE, ID_SPETTACOLO, ID_PREZZO, ID_POSTO, DATA_ORA_OPERAZIONE) VALUES (?, ?, ?, ?, ?)");
        try{
            stm.setInt(1, preno.getIdUtente());
            stm.setInt(2, preno.getIdSpettacolo());
            stm.setInt(3, preno.getIdPrezzo());
            stm.setInt(4, preno.getIdPosto());
            stm.setTimestamp(5, preno.getDataOraOperazione());
            
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
    
     /**
     * cancella una prenotazione di un utente dal database.
     * @param pr prenotazione da eliminare
     * @param ut utente a cui rimborsare il costo della prenotazione
     * @throws SQLException
     */
    public void deletePrenotazione (Prenotazione pr) throws SQLException{
        PreparedStatement stm = con.prepareStatement( "DELETE FROM PRENOTAZIONE P WHERE P.ID_PRENOTAZIONE=?");
        PreparedStatement stm2 = con.prepareStatement( "UPDATE UTENTE SET CREDITO=CREDITO+? WHERE UTENTE_ID=?");
        PreparedStatement stm3 = con.prepareStatement("SELECT P.PREZZO FROM PREZZO P WHERE P.ID_PREZZO =?");
        try {
            stm.setInt(1, pr.getIdPrenotazione());
            stm3.setInt(1, pr.getIdPrezzo());
            ResultSet prezzo = stm3.executeQuery();
            double rimborso = 0;
            if(prezzo.next())
                rimborso = prezzo.getDouble("PREZZO") * 0.8; //rimborso dell'80% del prezzo.
            stm2.setDouble(1, rimborso);
            stm2.setInt(2, pr.getIdUtente());
            stm2.executeUpdate();
        } finally {
            stm.close();
            stm2.close();
        }
    }
    
    /**
     * aggiunge una prenotazione nel database.
     * @param pre prenotazione da aggiungere
     * @throws java.sql.SQLIntegrityConstraintViolationException
     * @throws SQLException
     */
    public void aggiungiPrenotazione(Prenotazione pre) throws SQLIntegrityConstraintViolationException, SQLException{
        PreparedStatement stm = con.prepareStatement("INSERT INTO PRENOTAZIONE (ID_UTENTE, ID_SPETTACOLO, ID_PREZZO, ID_POSTO, DATA_ORA_OPERAZIONE) VALUES (?,?,?,?,?)");
        try {
            stm.setInt(1, pre.getIdUtente());
            stm.setInt(2, pre.getIdSpettacolo());
            stm.setInt(3, pre.getIdPrezzo());
            stm.setInt(4, pre.getIdPosto());
            stm.setTimestamp(5, pre.getDataOraOperazione());
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
    
    
    /**
     * aggiunge una prenotazione nel database.
     * @param pre prenotazione da aggiungere
     * @throws java.sql.SQLIntegrityConstraintViolationException
     * @throws SQLException
     */
    public Prenotazione getPrenotazione(int idPrenotazione) throws SQLIntegrityConstraintViolationException, SQLException{
       
        Prenotazione pre= new Prenotazione();
        PreparedStatement stm = con.prepareStatement(" select P.ID_PRENOTAZIONE,P.ID_UTENTE,P.ID_SPETTACOLO,P.ID_PREZZO,P.ID_POSTO,P.DATA_ORA_OPERAZIONE from PYTHONI.PRENOTAZIONE P\n" +
                "where P.ID_PRENOTAZIONE=1\n");
        try {
            stm.setInt(1, idPrenotazione);
            
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    
                    pre.setDataOraOperazione(rs.getTimestamp("DATA_ORA_OPERAZIONE"));
                    pre.setIdPosto(rs.getInt("ID_PREZZO"));
                    pre.setIdPrenotazione(rs.getInt("ID_PRENOTAZIONE"));
                    pre.setIdPrezzo(rs.getInt("ID_PREZZO"));
                    pre.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
                    pre.setIdUtente(rs.getInt("ID_UTENTE"));
                    
                   
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        
        return pre;
    }
}
