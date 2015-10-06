/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import java.sql.Connection;

/**
 *
 * @author enrico
 */
public class SalaQueries {
    private final transient Connection con;
    
    public SalaQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public SalaQueries(Connection con){
        this.con = con;
    }
}
