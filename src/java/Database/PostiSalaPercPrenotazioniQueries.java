/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Beans.PostiSala;
import Beans.PostiSalaPercPrenotazioni;
import Beans.Posto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author alessandro
 */
public class PostiSalaPercPrenotazioniQueries {
    private final transient Connection con;
    
    public PostiSalaPercPrenotazioniQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public PostiSalaPercPrenotazioniQueries(Connection con){
        this.con = con;
    }
    
    /**
     *
     * @param id_sala ID dello spettacolo di cui si vuole vedere la lista dei posti occupati
     * @param aggiungiInvisibili aggiunge posti invisibili per rendere la sala rettangolare.
     * @return ritorna una lista di postioccupati.
     * @throws SQLException
     */
    public ArrayList<PostiSalaPercPrenotazioni> getAllPosti(int id_sala, boolean aggiungiInvisibili) throws SQLException{
        ArrayList<PostiSalaPercPrenotazioni> res = new ArrayList<>();
        
        
        PreparedStatement stm = con.prepareStatement("SELECT P.ID_POSTO, P.ID_SALA, P.RIGA, P.COLONNA, P.STATO, COUNT (PR.ID_PRENOTAZIONE) AS TOT\n" +
                "FROM POSTO P JOIN PRENOTAZIONE PR ON PR.ID_POSTO=P.ID_POSTO\n" +
                "WHERE P.ID_SALA=?\n" +
                "GROUP BY P.ID_POSTO, P.ID_SALA, P.RIGA, P.COLONNA, P.STATO\n" +
                "ORDER BY P.RIGA, P.COLONNA");
        try {
            stm.setInt(1, id_sala);
            ResultSet rs = stm.executeQuery();
            try {
                
                String tmpRiga="";
                
                int flag=0;
                PostiSalaPercPrenotazioni ps=new PostiSalaPercPrenotazioni();
                PreparedStatement stm2 = con.prepareStatement("SELECT COUNT(*) AS TOT\n" +
                        "FROM PRENOTAZIONE PR JOIN SPETTACOLO S ON PR.ID_SPETTACOLO=S.ID_SPETTACOLO\n" +
                        "WHERE S.ID_SALA=?");
                stm2.setInt(1, id_sala);
                ResultSet rs2 = stm2.executeQuery();
                int totale = 1;
                if(rs2.next())
                    totale = rs2.getInt("TOT");
                
                while(rs.next()){
                    Posto tmpPosto = new Posto();
                    
                    tmpPosto.setIdPosto(rs.getInt("ID_POSTO"));
                    tmpPosto.setIdSala(rs.getInt("ID_SALA"));
                    tmpPosto.setRiga(rs.getString("RIGA").charAt(0));
                    tmpPosto.setColonna(rs.getInt("COLONNA"));
                    tmpPosto.setStato(rs.getInt("STATO"));
                    
                    if(String.valueOf(tmpPosto.getRiga()).equals(tmpRiga) || flag == 0){
                        flag = 1;
                    }else{
                        ps.setRiga(tmpRiga.charAt(0));
                        res.add(ps);
                        ps=new PostiSalaPercPrenotazioni();
                    }
                    
                    double perc = ((double)rs.getInt("TOT"))/totale;
                    ps.addNewPosto(tmpPosto.getIdPosto(), tmpPosto.getColonna(), tmpPosto.getStato(), perc);
                    tmpRiga=String.valueOf(tmpPosto.getRiga());
                }
            } finally {
                rs.close();
            }
                        
        } finally {
            stm.close();
        }
        //if(aggiungiInvisibili)
        //    res = PostiSalaPercPrenotazioni.formattaInfoSala(res);
        return res;
    }
}
