/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Beans.InfoUtenti;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InfoUtentiQueries {
    private final transient Connection con;
    
    public InfoUtentiQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public InfoUtentiQueries(Connection con){
        this.con = con;
    }     
    
    
    /**
     *
     * @return info sugli utenti con più soldi spesi e con più prenotazioni effettuate
     * @throws SQLException
     */
    public InfoUtenti getInfoUtenti() throws SQLException{
        
        InfoUtenti result= new InfoUtenti();
        
         PreparedStatement stm = con.prepareStatement(
                "SELECT COUNT(*) AS TOT\n" +
                        "FROM UTENTE");
        
        try{
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    result.setUtentiRegistrati(rs.getInt("TOT"));
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        
        
        stm = con.prepareStatement(
                "SELECT TMP.ID_UTENTE,TMP.EMAIL,TMP.TOT\n" +
"FROM (\n" +
"SELECT U.ID_UTENTE, U.EMAIL, COUNT (*) AS TOT\n" +
"FROM UTENTE U JOIN PRENOTAZIONE P ON U.ID_UTENTE=P.ID_UTENTE\n" +
"GROUP BY U.ID_UTENTE, U.EMAIL\n" +
") AS TMP\n" +
"WHERE TMP.ID_UTENTE= ( SELECT TMP2.ID_UTENTE\n" +
"                       FROM (SELECT U.ID_UTENTE, COUNT (*) AS TOT\n" +
"                             FROM UTENTE U JOIN PRENOTAZIONE P ON U.ID_UTENTE=P.ID_UTENTE\n" +
"                             GROUP BY U.ID_UTENTE \n" +
"                             ) AS TMP2\n" +
"                       WHERE TMP2.TOT= ( SELECT MAX(TMP3.TOT)\n" +
"                       FROM (SELECT U.ID_UTENTE, COUNT (*) AS TOT\n" +
"                             FROM UTENTE U JOIN PRENOTAZIONE P ON U.ID_UTENTE=P.ID_UTENTE\n" +
"                             GROUP BY U.ID_UTENTE \n" +
"                             ) AS TMP3\n" +
"\n" +
")\n" +
")");
        
        try{
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    result.setEmailPostiPiuPrenotati(rs.getString("EMAIL"));
                    result.setNumeroPosti(rs.getInt("TOT"));
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        
        
        stm = con.prepareStatement(
                "SELECT TMP.ID_UTENTE,TMP.EMAIL,TMP.TOT\n" +
"FROM (\n" +
"SELECT U.ID_UTENTE, U.EMAIL, SUM (PRE.PREZZO) AS TOT\n" +
"FROM UTENTE U JOIN PRENOTAZIONE P ON U.ID_UTENTE=P.ID_UTENTE JOIN PREZZO PRE ON P.ID_PREZZO=PRE.ID_PREZZO\n" +
"GROUP BY U.ID_UTENTE, U.EMAIL\n" +
") AS TMP\n" +
"WHERE TMP.ID_UTENTE= ( SELECT TMP2.ID_UTENTE\n" +
"                       FROM (SELECT U.ID_UTENTE, SUM (PRE.PREZZO) AS TOT\n" +
"                             FROM UTENTE U JOIN PRENOTAZIONE P ON U.ID_UTENTE=P.ID_UTENTE JOIN PREZZO PRE ON P.ID_PREZZO=PRE.ID_PREZZO\n" +
"                             GROUP BY U.ID_UTENTE \n" +
"                             ) AS TMP2\n" +
"                       WHERE TMP2.TOT= ( SELECT MAX(TMP3.TOT)\n" +
"                       FROM (SELECT U.ID_UTENTE, SUM (PRE.PREZZO) AS TOT\n" +
"                             FROM UTENTE U JOIN PRENOTAZIONE P ON U.ID_UTENTE=P.ID_UTENTE JOIN PREZZO PRE ON P.ID_PREZZO=PRE.ID_PREZZO\n" +
"                             GROUP BY U.ID_UTENTE \n" +
"                             ) AS TMP3\n" +
"\n" +
")\n" +
")");
        
        try{
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    result.setEmailTotalePiuAlto(rs.getString("EMAIL"));
                    result.setTotale(rs.getInt("TOT"));
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
