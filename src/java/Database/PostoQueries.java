/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import Beans.Posto;
import Beans.Spettacolo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author enrico
 */
public class PostoQueries{
    private final transient Connection con;
    
    public PostoQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public PostoQueries(Connection con){
        this.con = con;
    }
    
    /**
     * modifica lo stato del posto.
     * @param ps posto a cui va modificato lo stato
     * @throws SQLException
     */
    public void cambiaStato(Posto ps) throws SQLException{
        
        PreparedStatement stm = con.prepareStatement(" UPDATE POSTO SET STATO=? WHERE ID_POSTO=? ");
        try {
            stm.setInt(1, ps.getStato());
            stm.setInt(2, ps.getIdPosto());
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
    
    /**
     *
     * @param x coordinata x del posto
     * @param y coordinata x del posto
     * @param idSpettacolo
     * @return l'id del posto con coordiante x e y nello spettacolo con idSpettacolo
     * @throws SQLException
     */
    public int getIdPosto(int x, String y, int idSpettacolo) throws SQLException{
        Integer idPosto = 0;
        PreparedStatement stm;
        stm = con.prepareStatement("SELECT P.ID_POSTO AS IDP FROM POSTO P, SALA S, SPETTACOLO SP WHERE P.RIGA=? AND P.COLONNA=? AND SP.ID_SPETTACOLO=? AND S.ID_SALA=SP.ID_SALA AND S.ID_SALA=P.ID_SALA");
        
        try{
            stm.setInt(3,3);
            stm.setString(1,String.valueOf(y.charAt(0)));
            stm.setInt(2, x);
            
            ResultSet rs = stm.executeQuery();
            
            if(rs.next()){
                try {
                    idPosto=rs.getInt("IDP");
                    
                }finally {
                    rs.close();
                }
            }
        } finally {
            stm.close();
        }
        return idPosto;
    }
    
    /**
     * selezione gli n posti più prenotati.
     * @param idSala sala di cui si vogliono i posti più prenotati.
     * @param n numero di posti ritornati
     * @return lista con gli (al massimo) n posti più pronatati della sala.
     * @throws SQLException
     */
    public ArrayList<Posto> getPostiPiuPrenotati(int idSala, int n) throws SQLException{
        PreparedStatement stm;
        ArrayList<Posto> res = new ArrayList<>();
        stm = con.prepareStatement(" SELECT P.ID_POSTO, P.ID_SALA, P.RIGA, P.COLONNA, COUNT (PR.ID_PRENOTAZIONE) AS TOT\n" +
                "                        FROM POSTO P JOIN PRENOTAZIONE PR ON PR.ID_POSTO=P.ID_POSTO\n" +
                "                        WHERE P.ID_SALA=? \n" +
                "                        GROUP BY P.ID_POSTO, P.ID_SALA, P.RIGA, P.COLONNA\n" +
                "                        ORDER BY TOT ");
        try {
            stm.setInt(1, idSala);
            
            ResultSet rs = stm.executeQuery();
            int cont=0;
            while(rs.next() && cont<n){
                Posto tmp = new Posto();
                tmp.setIdPosto(rs.getInt("ID_POSTO"));
                tmp.setColonna(rs.getInt("COLONNA"));
                tmp.setIdSala(rs.getInt("ID_SALA"));
                tmp.setRiga(rs.getString("RIGA").charAt(0));
                tmp.setStato(rs.getInt("STATO"));
                res.add(tmp);
                cont++;
            }
        } finally {
            stm.close();
        }
        return res;
    }
    
    /**
     * restituisce tutti i posti liberi per un determinato spettacolo.
     * @param spetta spettacolo di cui si vogliono ottenere i posti disponibili
     * @return lista di posti disponibili (toglie i rotti)
     * @throws SQLException
     */
    public ArrayList<Posto> getPostiLiberi(Spettacolo spetta) throws SQLException{
        ArrayList<Posto> posti = new ArrayList<>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT P.ID_POSTO, P.ID_SALA, P.RIGA, P.COLONNA, P.STATO\n" +
                        "FROM POSTO P\n" +
                        "WHERE P.STATO = 0 AND P.ID_SALA = ? AND P.ID_POSTO NOT IN(\n" +
                        "SELECT PR.ID_POSTO FROM PRENOTAZIONE PR WHERE PR.ID_SPETTACOLO = ?)");
        try {
            stm.setInt(1, spetta.getIdSala());
            stm.setInt(2, spetta.getIdSpettacolo());
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                Posto tmp = new Posto();
                tmp.setIdPosto(rs.getInt("ID_POSTO"));
                tmp.setIdSala(rs.getInt("ID_SALA"));
                tmp.setRiga(rs.getString("RIGA").charAt(0));
                tmp.setColonna(rs.getInt("COLONNA"));
                tmp.setStato(rs.getInt("STATO"));
                posti.add(tmp);
            }
        } finally {
            stm.close();
        }
        return posti;
    }
    
    public Posto getPosto(int idPosto) throws SQLException{
        Posto tmp = new Posto();
        PreparedStatement stm = con.prepareStatement(
                "SELECT *\n" +
                        "FROM POSTO P\n" +
                        "WHERE P.ID_POSTO = ?");
        try {
            stm.setInt(1, idPosto);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                tmp.setIdPosto(rs.getInt("ID_POSTO"));
                tmp.setColonna(rs.getInt("COLONNA"));
                tmp.setIdSala(rs.getInt("ID_SALA"));
                tmp.setRiga(rs.getString("RIGA").charAt(0));
                tmp.setStato(rs.getInt("STATO"));
            }
        } finally {
            stm.close();
        }
        return tmp;
    }
    
}
