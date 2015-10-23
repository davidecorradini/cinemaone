


/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import Beans.PostiSala;
import Beans.Posto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author enrico
 */
public class PostiSalaQueries{
    private final transient Connection con;
    
    public PostiSalaQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public PostiSalaQueries(Connection con){
        this.con = con;
    }
    
    /**
     *
     * @param id_sala ID dello spettacolo di cui si vuole vedere la lista dei posti occupati
     * @return ritorna una lista di postioccupati.
     * @throws SQLException
     */
    public ArrayList<PostiSala> getAllPosti(int id_sala) throws SQLException{
        ArrayList<PostiSala> res = new ArrayList<>();

        
        PreparedStatement stm = con.prepareStatement(" SELECT P.ID_POSTO, P.ID_SALA, P.RIGA, P.COLONNA, COUNT (PR.ID_PRENOTAZIONE) AS TOT\n" +
                "                        FROM POSTO P JOIN PRENOTAZIONE PR ON PR.ID_POSTO=P.ID_POSTO\n" +
                "                        WHERE P.ID_SALA=? \n" +
                "                        GROUP BY P.ID_POSTO, P.ID_SALA, P.RIGA, P.COLONNA\n" +
                "                        ORDER BY P.RIGA, P.COLONNA ");
        try {
            stm.setInt(1, id_sala);             
            String tmpRiga="";                
            int flag=0;
            PostiSala ps=new PostiSala();
            ResultSet rs = stm.executeQuery();
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
                        ps=new PostiSala();
                    }
            PreparedStatement stm2 = con.prepareStatement(" COUNT (PR.ID_PRENOTAZIONE) AS TOT\n" +
                "                        FROM  PRENOTAZIONE PR\n" +
                "                        WHERE PR.ID_SALA=? \n" +
                "                        GROUP BY PR.ID_SALA\n");
            stm2.setInt(1, id_sala);
            ResultSet rs2 = stm2.executeQuery();
            int totale = rs2.getInt("TOT");
            int perc=rs.getInt("TOT")/totale;
            ps.addNewPosto(tmpPosto.getIdPosto(), tmpPosto.getColonna(), tmpPosto.getStato(), perc);
            tmpRiga=String.valueOf(tmpPosto.getRiga());
            res.add(ps);
            
        } finally {
            stm.close();
        }
        
       
        return res;  
        }
    
        
