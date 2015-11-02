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
        stm = con.prepareStatement("DELETE FROM POSTO" );
        stm.executeUpdate();
        
        stm = con.prepareStatement("ALTER TABLE POSTO ALTER COLUMN \"ID_POSTO\" RESTART WITH 1" );
        stm.executeUpdate();
        
        for (char i='a'; i<='a'+9; i++){
            for(int j=1;j<=13;j++){                               
                if (    (((i>='a') && (i<='b')) && ((j>=3) && (j<=11))) || 
                        (((i>='c') && (i<='d')) && ((j>=2) && (j<=12))) ||
                        (((i>='f') && (i<='g')) && (((j>=2)&&(j<=6))||((j>=8) && (j<=12)))) ||
                        (((i>='h') && (i<='j')) && (j!=7))  )
                        {
                stm = con.prepareStatement("INSERT INTO POSTO (ID_SALA, RIGA, COLONNA, STATO) VALUES(1,?,?,0)");
                stm.setString(1, String.valueOf(i));
                stm.setInt(2, j); 
                stm.executeUpdate();
                }              
            }
        }
        
       for (char i='a'; i<='a'+9; i++){
            for(int j=1;j<=13;j++){                               
                if (    (((i>='a') && (i<='b')) && ((j>=4) && (j<=10))) || 
                        (((i>='c') && (i<='d')) && ((j>=3) && (j<=11))) ||
                        (((i>='f') && (i<='g')) && (((j>=2)&&(j<=6))||((j>=8) && (j<=12)))) ||
                        (((i>='h') && (i<='j')) && (j!=7))  )
                        {
                stm = con.prepareStatement("INSERT INTO POSTO (ID_SALA, RIGA, COLONNA, STATO) VALUES(2,?,?,0)");
                stm.setString(1, String.valueOf(i));
                stm.setInt(2, j); 
                stm.executeUpdate();
                }              
            }
        }
        
        
        for (char i='a'; i<='a'+7; i++){
            for(int j=1;j<=11;j++){                               
                if (    (((i>='a') && (i<='b')) && ((j>=2) && (j<=10) && (j!=6))) || 
                        (((i>='c') && (i<='d')) && ((j!=6))) ||
                        ((i=='f') && ((j>=2)&&(j<=10))) ||
                        ((i=='g') && ((j>=3)&&(j<=9))) ||
                        ((i=='h') && ((j>=4)&&(j<=8))) )
                        {
                stm = con.prepareStatement("INSERT INTO POSTO (ID_SALA, RIGA, COLONNA, STATO) VALUES(3,?,?,0)");
                stm.setString(1, String.valueOf(i));
                stm.setInt(2, j); 
                stm.executeUpdate();
                }              
            }
        }
        
        for (char i='a'; i<='a'+7; i++){
            for(int j=1;j<=13;j++){                               
                if (    (((i>='a') && (i<='b')) && ((j>=2) && (j<=12) && (j!=7))) || 
                        (((i>='c') && (i<='d')) && ((j!=7))) ||
                        ((i=='f') && ((j>=2)&&(j<=12))) ||
                        ((i=='g') && ((j>=3)&&(j<=11))) ||
                        ((i=='h') && ((j>=4)&&(j<=10))) )
                        {
                stm = con.prepareStatement("INSERT INTO POSTO (ID_SALA, RIGA, COLONNA, STATO) VALUES(4,?,?,0)");
                stm.setString(1, String.valueOf(i));
                stm.setInt(2, j); 
                stm.executeUpdate();
                }              
            }
        }
        
        for (char i='a'; i<='a'+7; i++){
            for(int j=1;j<=13;j++){                               
                if (    (((i>='a') && (i<='b')) && ((j>=3) && (j<=11))) || 
                        (((i>='d') && (i<='e')) && ((j>=2) && (j<=12))) ||
                        (((i>='g') && (i<='h')) && (j!=7))  )  
                        {
                stm = con.prepareStatement("INSERT INTO POSTO (ID_SALA, RIGA, COLONNA, STATO) VALUES(5,?,?,0)");
                stm.setString(1, String.valueOf(i));
                stm.setInt(2, j); 
                stm.executeUpdate();
                }              
            }
        }
        
    }
    
}


