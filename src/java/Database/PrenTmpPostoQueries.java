/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import Beans.Posto;
import Beans.PrenTmpPosto;
import Beans.PrenotazioneTmp;
import Database.Cache.PrenotazioniTmpPostoCache;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author enrico
 */
public class PrenTmpPostoQueries {
    private final transient Connection con;
    
    public PrenTmpPostoQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public PrenTmpPostoQueries(Connection con){
        this.con = con;
    }
    
    /**
     *
     * @param id_spettacolo ID dello spettacolo di cui si vuole vedere la lista di prenotazioni temporanee
     * @return prenotazioni temporanee dei posti per il dato spettacolo.
     * @throws SQLException
     */
    public ArrayList<PrenTmpPosto> getPrenotazioneTmp(int id_spettacolo) throws SQLException{
        PrenotazioniTmpPostoCache prenTmpQuery = new PrenotazioniTmpPostoCache(con);
        long tempo = System.currentTimeMillis() - Beans.PrenotazioneTmp.validity*60*1000;
        Timestamp validityLimit = new Timestamp(tempo);
        prenTmpQuery.cancellaPrenotazioniTmp(validityLimit);
        
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
}
