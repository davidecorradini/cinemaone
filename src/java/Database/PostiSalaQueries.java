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
        PreparedStatement stm = con.prepareStatement(
                "SELECT P.ID_POSTO, P.ID_SALA, P.RIGA, P.COLONNA, P.STATO\n" +
                        "FROM POSTO P\n" +
                        " WHERE P.ID_SALA = ?\n" +
                        " ORDER BY P.RIGA, P.COLONNA");
        try {
            stm.setInt(1, id_sala);
            ResultSet rs = stm.executeQuery();
            try {
                
                String tmpRiga="";
                
                int flag=0;
                ArrayList<Integer[]> colonnaStato=new ArrayList<>();
                
                while(rs.next()){
                    Posto tmpPosto = new Posto();
                    
                    tmpPosto.setIdPosto(rs.getInt("ID_POSTO"));
                    tmpPosto.setIdSala(rs.getInt("ID_SALA"));
                    tmpPosto.setRiga(rs.getString("RIGA").charAt(0));
                    tmpPosto.setColonna(rs.getInt("COLONNA"));
                    tmpPosto.setStato(rs.getInt("STATO"));
                    
                    if(String.valueOf(tmpPosto.getRiga()).equals(tmpRiga)){
                        
                        Integer[] l=new Integer[2];
                        l[0]=tmpPosto.getColonna();
                        l[1]=tmpPosto.getStato();
                        colonnaStato.add(l);
                        
                    }
                    else if(flag==0){
                        
                        Integer[] l=new Integer[2];
                        l[0]=tmpPosto.getColonna();
                        l[1]=tmpPosto.getStato();
                        colonnaStato.add(l);
                        flag=1;
                    }
                    else{
                        
                        PostiSala ps=new PostiSala();
                        ps.setRiga(tmpRiga.charAt(0));
                        ps.setColonna(colonnaStato);
                        colonnaStato=new ArrayList<>();
                        
                        res.add(ps);
                        
                        Integer[] l=new Integer[2];
                        l[0]=tmpPosto.getColonna();
                        l[1]=tmpPosto.getStato();
                        colonnaStato.add(l);
                        
                        
                        
                    }
                    tmpRiga=String.valueOf(tmpPosto.getRiga());
                    
                    
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
