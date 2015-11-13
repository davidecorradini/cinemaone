/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import Beans.Posto;
import Beans.Prenotazione;
import Beans.PrenotazionePosto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrenotazionePostoQueries {
    private final transient Connection con;
     
    public PrenotazionePostoQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public PrenotazionePostoQueries(Connection con){
        this.con = con;
    }
    
    /**
     *
     * @param id_spettacolo ID dello spettacolo di cui si vuole vedere la lista dei posti occupati
     * @return ritorna una lista di postioccupati.
     * @throws SQLException
     */
    public ArrayList<PrenotazionePosto> getPostiOccupati(int id_spettacolo) throws SQLException{
        ArrayList<PrenotazionePosto> res = new ArrayList<>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT P.ID_POSTO, P.ID_SALA, P.RIGA, P.COLONNA, P.STATO,PR2.ID_PRENOTAZIONE,PR2.ID_UTENTE,PR2.ID_SPETTACOLO,PR2.ID_POSTO,PR2.ID_PREZZO,PR2.DATA_ORA_OPERAZIONE\n" +
                        "FROM POSTO P JOIN PRENOTAZIONE PR2 ON PR2.ID_POSTO=P.ID_POSTO\n" +
                        "WHERE P.ID_POSTO IN(\n"+
                        "SELECT PR.ID_POSTO FROM PRENOTAZIONE PR WHERE PR.ID_SPETTACOLO = ?)");
        try {
            stm.setInt(1, id_spettacolo);
            ResultSet rs = stm.executeQuery();
            try {
                while(rs.next()){
                    PrenotazionePosto tmp = new PrenotazionePosto();
                    
                    Prenotazione tmpPre = new Prenotazione();
                    
                    tmpPre.setIdPrenotazione(rs.getInt("ID_PRENOTAZIONE"));
                    tmpPre.setIdUtente(rs.getInt("ID_UTENTE"));
                    tmpPre.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
                    tmpPre.setIdPrezzo(rs.getInt("ID_PREZZO"));
                    tmpPre.setIdPosto(rs.getInt("ID_POSTO"));
                    tmpPre.setDataOraOperazione(rs.getTimestamp("DATA_ORA_OPERAZIONE"));
                    
                    Posto tmpPosto = new Posto();
                    tmpPosto.setIdPosto(rs.getInt("ID_POSTO"));
                    tmpPosto.setIdSala(rs.getInt("ID_SALA"));
                    tmpPosto.setRiga(rs.getString("RIGA").charAt(0));
                    tmpPosto.setColonna(rs.getInt("COLONNA"));
                    tmpPosto.setStato(rs.getInt("STATO"));
                    
                    tmp.setPrenotazione(tmpPre);
                    tmp.setPosto(tmpPosto);
                    res.add(tmp);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return res;
    }
}
