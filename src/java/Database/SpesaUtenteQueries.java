/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import Beans.SpesaUtente;
import Beans.Utente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SpesaUtenteQueries {
    private final transient Connection con;
    
    public SpesaUtenteQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public SpesaUtenteQueries(Connection con){
        this.con = con;
    }
    
    /**
     *
     * @param num numero dei clienti da ritornare
     * @return ArrayList contenente i num clienti migliori (quelli che hanno/hanno fatto più prenotazioni).
     * @throws SQLException
     */
    public ArrayList<SpesaUtente> getClientiTop(int num) throws SQLException{
        ArrayList<SpesaUtente> clienti = new ArrayList<>();
        int i=0;
        PreparedStatement stm = con.prepareStatement(
                "SELECT U.ID_UTENTE, U.EMAIL, U.CREDITO, U.ID_RUOLO, SUM (P.PREZZO) AS TOT, COUNT (*) AS NUM\n" +
                        "FROM UTENTE U JOIN PRENOTAZIONE PR ON U.ID_UTENTE=PR.ID_UTENTE JOIN PREZZO P ON PR.ID_PREZZO=P.ID_PREZZO\n" +
                        "GROUP BY U.ID_UTENTE, U.EMAIL, U.PASSWORD, U.CREDITO, U.ID_RUOLO ORDER BY COUNT(*) DESC, SUM (P.PREZZO) DESC");
        try {
            ResultSet rs = stm.executeQuery();
            try {
                SpesaUtente utenti;
                Utente tmp;
                while(rs.next() && i<num){
                    utenti = new SpesaUtente();
                    tmp = new Utente();
                    tmp.setIdUtente(rs.getInt("ID_UTENTE"));
                    tmp.setEmail(rs.getString("EMAIL"));
                    tmp.setCredito(rs.getDouble("CREDITO"));
                    tmp.setIdRuolo(rs.getInt("ID_RUOLO"));
                    utenti.setUt(tmp);
                    utenti.setNumPrenotazioni(rs.getInt("NUM"));
                    utenti.setSpesaTot(rs.getDouble("TOT"));
                    clienti.add(utenti);
                    i++;
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return clienti;
    }
}
