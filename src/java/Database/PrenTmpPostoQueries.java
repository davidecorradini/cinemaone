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
public class PrenTmpPostoQueries {
    private final transient Connection con;
    
    public PrenTmpPostoQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public PrenTmpPostoQueries(Connection con){
        this.con = con;
    }
}
