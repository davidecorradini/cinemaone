/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import Beans.PasswordRecovery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class PasswordRecoveryQueries {
    private final transient Connection con;
    
    public PasswordRecoveryQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public PasswordRecoveryQueries(Connection con){
        this.con = con;
    }
    
    public void insertRecuperaPassword(PasswordRecovery rec) throws SQLIntegrityConstraintViolationException, SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement(
                "INSERT INTO PASSWORDRECOVERY (MD5, EMAIL, TIME) VALUES (?, ?, ?)");
        try{
            stm.setString(1, rec.getHash());
            stm.setString(2, rec.getEmail());
            stm.setTimestamp(3, rec.getTime());
            
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
    
    public void removeRecuperaPassword(String email) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement(
                "DELETE FROM PASSWORDRECOVERY WHERE EMAIL = ?");
        try{
            stm.setString(1, email);            
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
    
}
