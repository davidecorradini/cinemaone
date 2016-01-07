/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import Beans.Ruolo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RuoloQueries {
    private final transient Connection con;
    
    public RuoloQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public RuoloQueries(Connection con){
        this.con = con;
    }
    
    public Ruolo getRuolo(int id) throws SQLException{
        Ruolo res = new Ruolo();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM RUOLO WHERE RUOLO.ID_RUOLO = ?");
        try {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                res.setIdRuolo(id);
                res.setRuolo(rs.getString("RUOLO"));
            }
        } finally {
            stm.close();
        }
        return res;
    }
}
