/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import Beans.Posto;
import Beans.PrenTmpPosto;
import Beans.Prenotazione;
import Beans.PrenotazioneTmp;
import static Database.DBManager.decodeIdUtente;
import static Database.DBManager.encodeIdUtente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author enrico
 */
public class PrenotazioneTmpQueries {
    private final transient Connection con;
    
    public PrenotazioneTmpQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public PrenotazioneTmpQueries(Connection con){
        this.con = con;
    }
    
    /**
     * aggiunge una prenotazione temporanea nel database.
     * @param pre prenotazionetmp da aggiungere
     * @return
     * @throws java.sql.SQLIntegrityConstraintViolationException
     * @throws SQLException
     */
    public int aggiungiPrenotazioneTmp(PrenotazioneTmp pre) throws SQLIntegrityConstraintViolationException, SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement("INSERT INTO PRENOTAZIONETMP (ID_SPETTACOLO, ID_UTENTE, ID_POSTO, DATA_ORA_OPERAZIONETMP, ID_PREZZO) VALUES (?,?,?,CURRENT_TIMESTAMP,?)");
        int result;
        
        try {
            stm.setInt(1, pre.getIdSpettacolo());
            stm.setString(2, pre.getIdUtente());
            stm.setInt(3, pre.getIdPosto());
            stm.setInt(4, pre.getIdPrezzo());
            
            result=stm.executeUpdate();
            
        } finally {
            stm.close();
        }
        return result;
    }
    
    /**
     * elimina le prenotazioni temporanee precedenti a tm.
     * @param tm limite minimo di validità delle prenotazionitmp (quelle piu vecchie vengono eliminate)
     * @throws SQLException
     */
    public void eliminaPrenotazioneTmp(Timestamp tm) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement("DELETE * FROM PRENOTAZIONETMP WHERE DATA_ORA_OPERAZIONETMP < ?");
        try {
            stm.setTimestamp(1, tm);
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
    
    /**
     *
     * @param id_spettacolo ID dello spettacolo di cui si vuole vedere la lista di prenotazioni temporanee
     * @return prenotazioni temporanee dei posti per il dato spettacolo.
     * @throws SQLException
     */
    public ArrayList<PrenTmpPosto> getPrenotazioneTmp(int id_spettacolo) throws SQLException{
        PreparedStatement stm;
        ArrayList<PrenTmpPosto> prenotazioniTmp = new ArrayList<>();
        stm = con.prepareStatement(
                "SELECT *\n" +
                        "FROM PRENOTAZIONETMP PREN JOIN POSTO P ON P.ID_POSTO = PREN.ID_POSTO " +
                        "WHERE PREN.ID_SPETTACOLO = ?");
        try {
            stm.setInt(1,id_spettacolo);
            ResultSet rs = stm.executeQuery();
            try {
                
                while(rs.next()){
                    PrenotazioneTmp tmp = new PrenotazioneTmp();
                    Posto posto = new Posto();
                    
                    tmp.setIdUtente(rs.getString("ID_UTENTE"));
                    tmp.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
                    tmp.setIdPosto(rs.getInt("ID_POSTO"));
                    tmp.setIdPrezzo(rs.getInt("ID_PREZZO"));
                    tmp.setTimestamp(rs.getTimestamp("DATA_ORA_OPERAZIONETMP"));
                    posto.setColonna(rs.getInt("COLONNA"));
                    posto.setRiga(rs.getString("RIGA").charAt(0));
                    posto.setIdPosto(rs.getInt("ID_POSTO"));
                    posto.setIdSala(rs.getInt("ID_SALA"));
                    posto.setStato(rs.getInt("STATO"));
                    PrenTmpPosto res = new PrenTmpPosto();
                    res.setPren(tmp);
                    res.setPosto(posto);
                    prenotazioniTmp.add(res);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return prenotazioniTmp;
    }
    
    public ArrayList<PrenotazioneTmp> getAndDeletePrenotazioniTmp(String idUtente) throws SQLException{
        PreparedStatement stm;
        ArrayList<PrenotazioneTmp> prenotazioneTmp = new ArrayList<>();
        stm = con.prepareStatement(
                "SELECT *\n" +
                        "FROM PRENOTAZIONETMP PREN " +
                        "WHERE ID_UTENTE = ?");
        try {
            stm.setString(1,idUtente);
            ResultSet rs = stm.executeQuery();
            try {
                
                while(rs.next()){
                    PrenotazioneTmp tmp = new PrenotazioneTmp();
                    
                    tmp.setIdUtente(rs.getString("ID_UTENTE"));
                    tmp.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
                    tmp.setIdPosto(rs.getInt("ID_POSTO"));
                    tmp.setTimestamp(rs.getTimestamp("DATA_ORA_OPERAZIONETMP"));
                    tmp.setIdPrezzo(rs.getInt("ID_PREZZO"));
                    prenotazioneTmp.add(tmp);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        
        cancellaPrenotazioniTmp(idUtente);
        
        return prenotazioneTmp;
    }
    
    public void cancellaPrenotazioniTmp(String id) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement("DELETE FROM PRENOTAZIONETMP WHERE ID_UTENTE = ?");
        try {
            stm.setString(1, id);
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
    
    public void aggiornaIdPrenotazioneTmp(String idTmp,int id) throws SQLException{
        
        PreparedStatement stm = con.prepareStatement("UPDATE PRENOTAZIONETMP PT SET PT.ID_UTENTE=? WHERE PT.ID_UTENTE=?");
        try {
            stm.setString(1, encodeIdUtente(id));
            stm.setString(2, idTmp);
            stm.executeUpdate();
        } finally {
            stm.close();
        }
        
    }
    
    /**
     *
     * @param idUtente
     * @throws SQLException
     */
    public void confermaPrenotazioni(String idUtente) throws SQLException{
        
        // controllo se utente loggato
        Object obj = decodeIdUtente(idUtente);
        if (!(obj instanceof Integer))
            throw new IllegalArgumentException("Conferma prenotazioni: invalid Id");
        
        int id = (int)obj;
        
        // get prenotazioniTmp di idUtente e le cancello
        ArrayList<PrenotazioneTmp> prenTmp=getAndDeletePrenotazioniTmp(idUtente);
        
        // le prendo e le importo in Prenotazioni
        
        for(PrenotazioneTmp tmp: prenTmp){
            Prenotazione pren = new Prenotazione();
            pren.setIdUtente(id);
            pren.setDataOraOperazione(tmp.getTimestamp());
            pren.setIdPosto(tmp.getIdPosto());
            pren.setIdSpettacolo(tmp.getIdSpettacolo());
            pren.setIdPrezzo(tmp.getIdPrezzo());
            PrenotazioneQueries prenQ = new PrenotazioneQueries(con);
            prenQ.aggiungiPrenotazione(pren);
        }
    }
}
