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
public class PrezzoQueries {
    private final transient Connection con;
    
    public PrezzoQueries(DBManager manager){
        this.con = manager.con;
    }
}
