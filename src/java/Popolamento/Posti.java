/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Popolamento;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author roberto
 */
public class Posti {
    
    private final transient Connection con;
        
    public Posti(String dburl) throws SQLException{
    try {
            Class.forName("org.apache.derby.jdbc.ClientDriver", true, getClass().getClassLoader());
        } catch(Exception e) {
            throw new RuntimeException(e.toString(), e);
        }
        Connection con = DriverManager.getConnection(dburl);
        this.con = con;    
    }
    
    
    
    public static void main(String [] args) throws SQLException, IOException
	{
            Posti posti = new Posti("jdbc:derby://localhost:1527/MultisalaDB;user=pythoni;password=pythoni");
            
            posti.inserisciPosti();
           
        }
    
    
    public void inserisciPosti() throws SQLException{
        PreparedStatement stm;
        for (char i='a'; i<'a'+5; i++){
            for(int j=1;j<=10;j++){
                stm = con.prepareStatement("INSERT INTO POSTO (ID_SALA, RIGA, COLONNA, STATO) VALUES(1,?,?,0)");
                stm.setString(1, String.valueOf(i));
                stm.setInt(2, j);
		stm.executeUpdate();
            }
        }
        
        for (char i='a';i<='a'+9;i++){
            for(int j=1;j<=10;j++){
                
                stm = con.prepareStatement("INSERT INTO POSTO (ID_SALA, RIGA, COLONNA, STATO) VALUES(2,?,?,0)");
                stm.setString(1, String.valueOf(i));
                stm.setInt(2, j);
		stm.executeUpdate();
            }
        }
        
        for (char i='a';i<='a'+9;i++){
            for(int j=1;j<=10;j++){
                stm = con.prepareStatement("INSERT INTO POSTO (ID_SALA, RIGA, COLONNA, STATO) VALUES(3,?,?,0)");
                stm.setString(1, String.valueOf(i));
                stm.setInt(2, j);
		stm.executeUpdate();
            }
        }
        
        for (char i='a';i<='a'+9;i++){
            for(int j=1;j<=20;j++){
                stm = con.prepareStatement("INSERT INTO POSTO (ID_SALA, RIGA, COLONNA, STATO) VALUES(4,?,?,0)");
                stm.setString(1, String.valueOf(i));
                stm.setInt(2, j);
		stm.executeUpdate();
            }
        }
        
        for (char i='a';i<='a'+9;i++){
            for(int j=1;j<=20;j++){
                stm = con.prepareStatement("INSERT INTO POSTO (ID_SALA, RIGA, COLONNA, STATO) VALUES(5,?,?,0)");
                stm.setString(1, String.valueOf(i));
                stm.setInt(2, j);
		stm.executeUpdate();
            }
        }
        
    }
    
}


