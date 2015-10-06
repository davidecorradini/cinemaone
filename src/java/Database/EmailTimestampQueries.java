/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import Beans.EmailTimestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author enrico
 */
public class EmailTimestampQueries{
    private final transient Connection con;
    
    public EmailTimestampQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public EmailTimestampQueries(Connection con){
        this.con = con;
    }
    
    public EmailTimestamp getInfoRecovery(String md5) throws SQLException{
        EmailTimestamp res = null;
        PreparedStatement stm = stm = con.prepareStatement(
                "SELECT * FROM PASSWORDRECOVERY WHERE MD5 = ?");
        stm.setString(1, md5);
        ResultSet rs = stm.executeQuery();
        if(rs.next()){
            res = new EmailTimestamp();
            res.setEmail(rs.getString("EMAIL"));
            res.setTimestamp(rs.getTimestamp("TIME"));
        }
        return res;
    }
    
}
