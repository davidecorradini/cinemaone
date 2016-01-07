/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import static java.lang.Math.random;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class SpettacoloQueries {
    private final transient Connection con;
    
    public SpettacoloQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public SpettacoloQueries(Connection con){
        this.con = con;
    }
        
    /**
     *
     * @param sp spettacolo di cui vogliamo visionare il numero di posti prenotati e l'incasso
     * @return array di Integer il cui primo elemento è il numero di posti prenotati e il secondo è l'incasso
     * @throws SQLException
     */
    public Number[] getPostiIncasso(int idSpettacolo) throws SQLException{
        Number res[]=new Number[2];
        PreparedStatement stm;
        stm = con.prepareStatement(
                "SELECT COUNT(*) AS TOT_POSTI, SUM(PR.PREZZO) AS TOT_PREZZO\n" +
                        "FROM PREZZO AS PR JOIN PRENOTAZIONE AS P ON PR.ID_PREZZO=P.ID_PREZZO\n" +
                        "WHERE P.ID_SPETTACOLO=?");
        try{
            stm.setInt(1, idSpettacolo);
            
            ResultSet rs = stm.executeQuery();
            try {
                if(rs.next()){
                    res[0]=rs.getInt("TOT_POSTI");
                    res[1]=rs.getDouble("TOT_PREZZO");
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return res;
    }
    
    /**
     * setta la programmazione dei film in modo da metterli in serie
     * @param min numero di minuti tra uno spettacolo e l'altro
     * @param n numero di spettacoli
     * @throws SQLException
     */
    public void setProgrammazione(int min, int n) throws SQLException{
        
        //int n=5; //numero di film per spettacolo
        
        Calendar calendar = Calendar.getInstance();
        
        PreparedStatement stmFilm;
        PreparedStatement stmSala;
        PreparedStatement stmUpdate;
        stmFilm = con.prepareStatement("SELECT ID_FILM FROM FILM ORDER BY RANDOM()");
        stmSala = con.prepareStatement("SELECT ID_SALA FROM SALA ORDER BY RANDOM()");
        stmUpdate = con.prepareStatement("INSERT INTO SPETTACOLO (ID_SALA, ID_FILM, DATA_ORA) VALUES (?,?,?)");
        ResultSet rsFilm = stmFilm.executeQuery();
        ResultSet rsSala = stmSala.executeQuery();
        
        ArrayList<Integer> film = new ArrayList<>();
        
        //fare ciclo che tira su tutti gli id_film
        while(rsFilm.next()){
            int id= rsFilm.getInt("ID_FILM");
            film.add(id);
        }
        
        try {
            int nfilm=0;
            while (rsSala.next()){
                
                Calendar calendar2 = (Calendar) calendar.clone();
                calendar2.add(Calendar.MINUTE, (int) (random() * min));
                int id_sala= rsSala.getInt("ID_SALA");
                if(!film.isEmpty()){
                    int id_film= film.remove(nfilm);
                    
                    
                    for (int i=0; i<n; i++){
                        
                        Timestamp time = new Timestamp(calendar2.getTimeInMillis());
                        stmUpdate.setInt(1, id_sala);
                        stmUpdate.setInt(2, id_film);
                        stmUpdate.setTimestamp(3, time);
                        stmUpdate.executeUpdate();
                        //schema di spettacolo: ID_SPETTACOLO, ID_FILM, ID_SALA, DATA_ORA
                        //aumento il tempo
                        calendar2.add(Calendar.MINUTE, min);
                    }
                }
            }
        } finally {
            stmFilm.close();
            stmSala.close();
            stmUpdate.close();
            rsFilm.close();
            rsSala.close();
        }
    }
    
    /**
     *
     * @return incasso totale, prenotazioni totali e numero di spettacoli di quelli passati
     * @throws SQLException
     */
    public Number[] getInfoSpettacoliPassati() throws SQLException{
        
        Number [] result= new Number[3];
        
        PreparedStatement stm = con.prepareStatement(
                "SELECT COUNT(*) AS TOT, SUM (P.PREZZO) AS INCASSO\n" +
                        "FROM PYTHONI.SPETTACOLO AS S JOIN PYTHONI.PRENOTAZIONE AS PREN ON S.ID_SPETTACOLO=PREN.ID_SPETTACOLO JOIN PYTHONI.PREZZO AS P ON PREN.ID_PREZZO=P.ID_PREZZO \n" +
                        "WHERE S.DATA_ORA<CURRENT_TIMESTAMP");
        
        try{
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    result[0]= rs.getInt("TOT");
                    result[1]= rs.getDouble("INCASSO");
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        
        
        stm = con.prepareStatement(
                "SELECT COUNT(*) AS TOT\n" +
                        "FROM PYTHONI.SPETTACOLO AS S\n" +
                        "WHERE S.DATA_ORA<CURRENT_TIMESTAMP");
        
        try{
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    result[2]= rs.getInt("TOT");
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        
        
        return result;
    }
    
    /**
     *
     * @return incasso totale, prenotazioni totali e numero di spettacoli di quelli futuri
     * @throws SQLException
     */
    public Number[] getInfoSpettacoliFuturi() throws SQLException{
        
        Number [] result= new Number[3];
        
        PreparedStatement stm = con.prepareStatement(
                "SELECT COUNT(*) AS TOT, SUM (P.PREZZO) AS INCASSO\n" +
                        "FROM PYTHONI.SPETTACOLO AS S JOIN PYTHONI.PRENOTAZIONE AS PREN ON S.ID_SPETTACOLO=PREN.ID_SPETTACOLO JOIN PYTHONI.PREZZO AS P ON PREN.ID_PREZZO=P.ID_PREZZO \n" +
                        "WHERE S.DATA_ORA>CURRENT_TIMESTAMP");
        
        try{
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    result[0]= rs.getInt("TOT");
                    result[1]= rs.getDouble("INCASSO");
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        
        
        stm = con.prepareStatement(
                "SELECT COUNT(*) AS TOT\n" +
                        "FROM PYTHONI.SPETTACOLO AS S\n" +
                        "WHERE S.DATA_ORA>CURRENT_TIMESTAMP");
        
        try{
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    result[2]= rs.getInt("TOT");
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        
        
        return result;
        
    }
    
}
