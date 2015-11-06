/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import Beans.Prezzo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author enrico
 */
public class PrezzoQueries {
    private final transient Connection con;
    
    public PrezzoQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public PrezzoQueries(Connection con){
        this.con = con;
    }
    
    /**
     * ritorna una lista di tutti i tipi di prezzi
     * @return lista di tutti i prezzi possibili
     * @throws SQLException
     */
    public ArrayList<Prezzo> getAllPrezzi() throws SQLException{
        PreparedStatement stm = con.prepareStatement("SELECT ID_PREZZO, TIPO, PREZZO FROM PREZZO");
        ArrayList<Prezzo> prezzi = new ArrayList<>();
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while(rs.next()){
                    Prezzo tmp = new Prezzo();
                    tmp.setIdPrezzo(rs.getInt("ID_PREZZO"));
                    tmp.setPrezzo(rs.getDouble("PREZZO"));
                    tmp.setTipo(rs.getString("TIPO"));
                    prezzi.add(tmp);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return prezzi;
    }
    
    public int getPrezzo(int idPrezzo) throws SQLException{
        PreparedStatement stm = con.prepareStatement("select PREZZO from PREZZO WHERE ID_PREZZO=?");
        int res = 0;
        try {
            System.out.println("entrato: "+idPrezzo);
            stm.setInt(1, idPrezzo);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
            res=(rs.getInt("PREZZO"));
            System.out.println("res: "+res);
            }
        } finally {
            stm.close();
        }
        return res;
    }
}
