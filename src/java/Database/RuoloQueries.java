/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import Beans.Ruolo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 *
 * @author enrico
 */
public class RuoloQueries {
    private final transient Connection con;
    
    public RuoloQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public RuoloQueries(Connection con){
        this.con = con;
    }
    
    /**
     * aggiunge un ruolo.
     * @param rl ruolo da aggiungere
     * @throws SQLException
     */
    public void aggiungiRuolo(Ruolo rl) throws SQLIntegrityConstraintViolationException, SQLException{
        
        PreparedStatement stm = con.prepareStatement("INSERT INTO RUOLO (RUOLO) VALUES (?)");
        try {
            stm.setString(1, rl.getRuolo());
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
}
