/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import Beans.Sala;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SalaQueries {
    private final transient Connection con;
    
    public SalaQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public SalaQueries(Connection con){
        this.con = con;
    }
    
    public ArrayList<Sala> getSale() throws SQLException{
        ArrayList<Sala> sale = new ArrayList();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM SALA");        
        Sala sala = new Sala();
        try {
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                sala.setIdSala(rs.getInt("ID_SALA"));
                sala.setNome(rs.getString("NOME"));                
                sala.setDescrizione(rs.getString("DESCRIZIONE"));
                sale.add(sala);
                sala = new Sala();
            }
        } finally {
            stm.close();
        }
        return sale;
    }
}
