/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import java.sql.Connection;

public class ContattiTelefoniciQueries {
    private final transient Connection con;
    
    public ContattiTelefoniciQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public ContattiTelefoniciQueries(Connection con){
        this.con = con;
    }
    
}
