/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import Beans.Posto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostoQueries{
    private final transient Connection con;
    
    public PostoQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public PostoQueries(Connection con){
        this.con = con;
    }
    
    /**
     * modifica lo stato del posto.
     * @param ps posto a cui va modificato lo stato
     * @throws SQLException
     */
    public void cambiaStato(Posto ps) throws SQLException{
        PreparedStatement stm = con.prepareStatement(" UPDATE POSTO SET STATO=? WHERE ID_POSTO=? ");
        try {
            stm.setInt(1, ps.getStato());
            stm.setInt(2, ps.getIdPosto());
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
        
    /**
     *
     * @param idPosto
     * @return bean Posto dato l'id
     * @throws SQLException
     */
    public Posto getPosto(int idPosto) throws SQLException{
        Posto tmp = new Posto();
        PreparedStatement stm = con.prepareStatement(
                "SELECT *\n" +
                        "FROM POSTO P\n" +
                        "WHERE P.ID_POSTO = ?");
        try {
            stm.setInt(1, idPosto);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                tmp.setIdPosto(rs.getInt("ID_POSTO"));
                tmp.setColonna(rs.getInt("COLONNA"));
                tmp.setIdSala(rs.getInt("ID_SALA"));
                tmp.setRiga(rs.getString("RIGA").charAt(0));
                tmp.setStato(rs.getInt("STATO"));
            }
        } finally {
            stm.close();
        }
        return tmp;
    }
    
}
