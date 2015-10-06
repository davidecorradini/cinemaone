/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Beans.Ruolo;
import Beans.Utente;
import Beans.UtenteRuolo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author enrico
 */
public class UtenteRuoloQueries {
    private final transient Connection con;
    
    public UtenteRuoloQueries(DBManager manager){
        this.con = manager.con;
    }
    
    /**
     * Autentica un utente in base a un nome utente (e-mail) e a una password
     *
     * @param email l'indirizzo e-mail dell'utente
     * @param password la password
     * @return null se l'utente non è autenticato, un oggetto User se l'utente esiste ed è autenticato
     * @throws java.sql.SQLException
     */
    public UtenteRuolo authenticate(String email, String password,String idTmp) throws SQLException {
        UtenteRuolo res;
        Utente utente = null;
        Ruolo ruolo = null;
        PreparedStatement stm = con.prepareStatement(
                "SELECT U.ID_UTENTE, U.CREDITO, U.ID_RUOLO, P.RUOLO\n" +
                        "FROM UTENTE U JOIN RUOLO P ON U.ID_RUOLO = P.ID_RUOLO\n" +
                        "WHERE EMAIL = ? AND PASSWORD = ?");
        try{
            stm.setString(1, email);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs.next()){ //se c'è un elemento significa che è autenticato.
                utente = new Utente();
                utente.setIdUtente(rs.getInt("ID_UTENTE"));
                utente.setEmail(email);
                utente.setPassword(password);
                utente.setCredito(rs.getDouble("CREDITO"));
                utente.setIdRuolo(rs.getInt("ID_RUOLO"));
                ruolo = new Ruolo();
                ruolo.setIdRuolo(rs.getInt("ID_RUOLO"));
                ruolo.setRuolo(rs.getString("RUOLO"));
                PrenotazioneTmpQueries prenTmpQ = new PrenotazioneTmpQueries(con);
                prenTmpQ.aggiornaIdPrenotazioneTmp(idTmp,utente.getIdUtente());
                
            }
        }finally{
            stm.close();
        }
        res = new UtenteRuolo();
        res.setRuolo(ruolo);
        res.setUtente(utente);
        return res;
    }
}
