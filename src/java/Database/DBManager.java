
package Database;

/**
 *
 * @author enrico
 */

import Beans.Film;
import Beans.Posto;
import Beans.Prenotazione;
import Beans.PrenotazioneTmp;
import Beans.Prezzo;
import Beans.Ruolo;
import Beans.Spettacolo;
import Beans.Utente;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Logger;

public class DBManager implements Serializable {
    // transient = non viene serializzato
    
    private transient Connection con;
    
    public DBManager(String dburl) throws SQLException {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver", true, getClass().getClassLoader());
        } catch(Exception e) {
            throw new RuntimeException(e.toString(), e);
        }
        Connection con = DriverManager.getConnection(dburl);
        this.con = con;
    }
    
    public static void shutdown() {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).info(ex.getMessage());
        }
    }
    
    /**
     * Autentica un utente in base a un nome utente (e-mail) e a una password
     *
     * @param email l'indirizzo e-mail dell'utente
     * @param password la password
     * @return null se l'utente non è autenticato, un oggetto User se l'utente esiste ed è autenticato
     * @throws java.sql.SQLException
     */
    public Utente authenticate(String email, String password) throws SQLException {
        // usare SEMPRE i PreparedStatement, anche per query banali.
        PreparedStatement stm = con.prepareStatement("SELECT ID_UTENTE, CREDITO, ID_RUOLO FROM UTENTE WHERE EMAIL = ? AND PASSWORD = ?");
        try {
            stm.setString(1, email);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()){ //se c'è un elemento significa che è autenticato.
                    Utente user = new Utente();
                    user.setIdUtente(rs.getInt("ID_UTENTE"));
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setCredito(rs.getDouble("CREDITO"));
                    user.setIdRuolo(rs.getInt("ID_RUOLO"));
                    return user;
                } else {
                    return null;
                }
            } finally {
// ricordarsi SEMPRE di chiudere i ResultSet in un blocco finally
                rs.close();
            }
        } finally { // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally
            stm.close();
        }
    }
    /*
    /**
    * ritorna una lista di tutti i tipi di prezzi
    * @return lista di tutti i prezzi possibili
    * @throws SQLException
    */
    
    
    public ArrayList<Prezzo> getAllPrezzi() throws SQLException{
        PreparedStatement stm = con.prepareStatement("SELECT ID_PREZZO, TIPO, PREZZO FROM PREZZO");
        ArrayList<Prezzo> prezzi = new ArrayList<>();
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while(rs.next()){
                    Prezzo tmp = new Prezzo();
                    tmp.setIdPrezzo(rs.getInt("ID_PREZZO"));
                    tmp.setPrezzo(rs.getDouble("PREZZO"));
                    tmp.setTipo(rs.getString("TIPO"));
                    prezzi.add(tmp);
                }
            } finally {
            // ricordarsi SEMPRE di chiudere i ResultSet in un blocco finally
            rs.close();
            }
        } finally { // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally
            stm.close();
        }
        return prezzi;
    }
    
    /**
     *
     * @param film film di cui si vogliono avere tutti gli spettacoli
     * @return lista di spettacoli del film
     * @throws SQLException
     */
    
    
    public ArrayList<Spettacolo> getSpettacoli(Film film) throws SQLException{
        ArrayList<Spettacolo> spettacoli = new ArrayList<>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT S.ID_SPETTACOLO, S.ID_FILM, S.ID_SALA, S.DATA_ORA FROM SPETTACOLO S WHERE S.ID_FILM = ?");
        try{
            stm.setInt(1, film.getIdFilm());
            ResultSet rs = stm.executeQuery();
            try{
            while(rs.next()){
                Spettacolo tmp = new Spettacolo();
                tmp.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
                tmp.setIdFilm(rs.getInt("ID_FILM"));
                tmp.setIdSala(rs.getInt("ID_SALA"));
                tmp.setDataOra(rs.getTimestamp("DATA_ORA"));
                spettacoli.add(tmp);
            }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return spettacoli;
    }
    
    /**
     *
     * @param spetta spettacolo di cui si vogliono ottenere i posti disponibili
     * @return lista di posti disponibili (toglie i rotti)
     * @throws SQLException
     */
    
    
    public ArrayList<Posto> getPostiLiberi(Spettacolo spetta) throws SQLException{
        ArrayList<Posto> posti = new ArrayList<>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT P.ID_POSTO, P.ID_SALA, P.RIGA, P.COLONNA, P.STATO FROM POSTO P WHERE P.STATO = 0 AND P.ID_SALA = ? AND P.ID_POSTO NOT IN( SELECT PR.ID_POSTO FROM PRENOTAZIONE PR WHERE PR.ID_SPETTACOLO = ?");
        try {
            stm.setInt(1, spetta.getIdSala());
            stm.setInt(2, spetta.getIdSpettacolo());
            ResultSet rs = stm.executeQuery();
            try {
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
                rs.close();
            }
        } finally {
            stm.close();
        }
        return posti;
    }
    
    /**
     *
     * @param preno prenotazione da memorizzare
     * @throws SQLException
     */
    
    
    public void storePrenotazione(Prenotazione preno) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement(
                "INSERT INTO PRENOTAZIONE (ID_UTENTE, ID_SPETTACOLO, ID_PREZZO, ID_POSTO, DATA_ORA_OPERAZIONE) VALUES (?, ?, ?, ?)");
        try{
            stm.setInt(1, preno.getIdUtente());
            stm.setInt(2, preno.getIdSpettacolo());
            stm.setInt(3, preno.getIdPrezzo());
            stm.setTimestamp(4, preno.getDataOraOperazione());
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
    
    /**
     *
     * @param ut utente di cui si vuole cambiare la password, nel campo password di tale oggetto deve esserci la nuova password
     * @throws SQLException
     */
    
    
    public void cambiaPassword(Utente ut) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement(
                "UPDATE UTENTE SET PASSWORD = ? WHERE ID_UTENTE = ?");
        try {
            stm.setString(1, ut.getPassword());
            stm.setInt(2, ut.getIdUtente());
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
    
    /**
     *
     * @param ut utente di cui si vuole ottenere lo storico
     * @return un array di coppie di oggetti, di cui il primo è un instanza di Film e il secondo di Prezzo
     * @throws SQLException
     */
    
    
    public ArrayList<Object[]> getStorico(Utente ut) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement(
                "SELECT DISTINCT F.ID_FILM, F.ID_GENERE, F.TITOLO, F.DURATA, F.TRAMA, F.URL_TRAILER, F.URI_LOCANDINA, PR.ID_PREZZO, PR.TIPO, PR.PREZZO FROM FILM F JOIN SPETTACOLO S ON F.ID_FILM = S.ID_FILM JOIN PRENOTAZIONE P ON P.ID_SPETTACOLO = S.ID_SPETTACOLO JOIN PREZZO PR ON PR.ID_PREZZO = P.ID_PREZZO WHERE P.ID_UTENTE = ?");
        ArrayList<Object[]> res = new ArrayList<>();
        try {
            stm.setInt(1, ut.getIdUtente());
            ResultSet rs = stm.executeQuery();
            try {

                while(rs.next()){
                    Object[] tmp = new Object[2];

                    Film tmpFilm = new Film();
                    tmpFilm.setIdFilm(rs.getInt("ID_FILM"));
                    tmpFilm.setIdGenere(rs.getInt("ID_GENERE"));
                    tmpFilm.setTitolo(rs.getString("TITOLO"));
                    tmpFilm.setDurata(rs.getInt("DURATA"));
                    tmpFilm.setTrama(rs.getString("TRAMA"));
                    tmpFilm.setUrlTrailer(rs.getString("URL_TRAILER"));
                    tmpFilm.setUriLocandina(rs.getString("URI_LOCANDINA"));

                    Prezzo tmpPrezzo = new Prezzo();
                    tmpPrezzo.setIdPrezzo(rs.getInt("ID_PREZZO"));
                    tmpPrezzo.setPrezzo(rs.getDouble("PREZZO"));
                    tmpPrezzo.setTipo(rs.getString("TIPO"));

                    tmp[0] = tmpFilm;
                    tmp[1] = tmpPrezzo;
                    res.add(tmp);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return res;
    }
    
    
    public Integer[] getPostiIncasso(Spettacolo sp) throws SQLException{
        Integer res[]=new Integer[2];
        PreparedStatement stm;
        stm = con.prepareStatement("SELECT COUNT(*) AS TOT_POSTI, SUM(PR.PREZZO) AS TOT_PREZZO FROM PREZZO AS PR JOIN PRENOTAZIONE AS P ON PR.ID_PREZZO=P.ID_PREZZO WHERE P.ID_SPETTACOLO=?");
        try{
            stm.setInt(1, sp.getIdSpettacolo());

            ResultSet rs = stm.executeQuery();
                try {
                res[0]=rs.getInt("TOT_POSTI");
                res[1]=rs.getInt("TOT_PREZZO");
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        
        return res;
        
    }
    
    
    public ArrayList<Object[]> getFilmPrezzo(Utente ut) throws SQLException{
        ArrayList<Object[]> res = new ArrayList<>();
        PreparedStatement stm;
        stm = con.prepareStatement( " SELECT DISTINCT F.ID_FILM, SUM (PR.PREZZO) AS TOT FROM FILM F JOIN SPETTACOLO S ON F.ID_FILM = S.ID_FILM JOIN PRENOTAZIONE P ON P.ID_SPETTACOLO = S.ID_SPETTACOLO JOIN PREZZO PR ON PR.ID_PREZZO = P.ID_PREZZO GROUP BY F.ID_FILM " );
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while(rs.next()){
                    Object[] tmp = new Object[2];

                    tmp[0] = rs.getInt("TOT");
                    tmp[1] = rs.getInt("F.ID_FILM");

                    res.add(tmp);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return res;
    }
    
    
    
    
    
    public ArrayList<Utente> getClientiTop(int num) throws SQLException{
        ArrayList<Utente> clienti = new ArrayList<>();
        int i=0;
        PreparedStatement stm;
        stm = con.prepareStatement(" SELECT U.ID_UTENTE FROM UTENTE U JOIN PRENOTAZIONE PR ON U.ID_UTENTE=PR.ID_UTENTE GROUP BY U.ID_UTENTE ORDER BY COUNT(*) DESC ");
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while(rs.next() && i<num){
                    Utente tmp = new Utente();
                    tmp.setIdUtente(rs.getInt("ID_UTENTE"));
                    clienti.add(tmp);
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
    
    
    public void deletePrenotazione (Prenotazione pr, Utente ut) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement( "DELETE FROM PRENOTAZIONE P WHERE P.ID_PRENOTAZIONE=? ");
        try {
            stm.setInt(1, pr.getIdPrenotazione());
            PreparedStatement stm2;
            stm2 = con.prepareStatement( " UPDATE UTENTE SET CREDITO=CREDITO-? WHERE UTENTE_ID=? ");
            int x=0; // in qualche modo x deve riuscire a tirare su il prezzo del biglietto

            stm.setDouble(1, x*0.8);
            stm.setInt(1, ut.getIdUtente());
            stm.executeUpdate();
        } finally {
            stm.close();
        }
        
        
    }
    
    
    public Integer[] getPostiPiuPrenotati(Posto ps, int n) throws SQLException{
        PreparedStatement stm;
        Integer res[]=new Integer[n];
        stm = con.prepareStatement(" SELECT P2.ID_POSTO FROM (SELECT P.ID_POSTO, COUNT (*) TOT FROM POSTO P JOIN PRENOTAZIONE PR ON PR.ID_POSTO=P.ID_POSTO WHERE P.ID_SALA=? GROUP BY P.ID_POSTO) P2 ORDER BY P2.TOT");
        try {
            stm.setInt(1, ps.getIdSala());

            ResultSet rs = stm.executeQuery();
            try{
                int cont=0;
                while(rs.next() && cont<n){

                    res[cont]=(rs.getInt("ID_POSTO"));
                    cont++;

                }
            } finally {
            rs.close();
            }
        } finally {
            stm.close();
        }
        
        return res;
        
    }
    
    
    public void aggiornaCredito(Utente ut, int credito, Posto ps, Prenotazione pre) throws SQLException{
        // possiamo scegliere se farci passare tutto o solo prenotazione
        
        PreparedStatement stm;
        stm = con.prepareStatement(" UPDATE UTENTE SET CREDITO=? WHERE ID_UTENTE=?");
        
        PreparedStatement stm2;
        stm2 = con.prepareStatement(" UPDATE POSTO SET STATO=0 WHERE ID_SALA=? AND ID_POSTO=? ");
           
        PreparedStatement stm3;
        stm3 = con.prepareStatement(" INSERT INTO PRENOTAZIONE (ID_UTENTE, ID_SPETTACOLO, ID_PREZZO, ID_POSTO, DATA_ORA_OPERAZIONE) VALUES (?,?,?,?,?); ");
            try {
                stm.setInt(1, credito);
                stm.setInt(2, ut.getIdUtente());
                stm2.setInt(1, ps.getIdSala());
                stm2.setInt(2, ps.getIdPosto());
                stm3.setInt(1, pre.getIdUtente());
                stm3.setInt(2, pre.getIdSpettacolo());
                stm3.setInt(3, pre.getIdPrezzo());
                stm3.setInt(4, pre.getIdPosto());
                stm3.setTimestamp(5, pre.getDataOraOperazione());
                stm.executeUpdate();
                stm2.executeUpdate();
                stm3.executeUpdate();
        } finally {
            stm.close();
        }
    }
    
    public void inserisciFilm(Film fm) throws SQLException{
        // possiamo farci passare tutta una classe film
        
        PreparedStatement stm;
        stm = con.prepareStatement(" INSERT INTO FILM (TITOLO, ID_GENERE, URL_TRAILER,DURATA,TRAMA, URI_LOCANDINA) VALUES ('?',?,'?',?,'?', '?') ");
        try {
            stm.setString(1, fm.getTitolo());
            stm.setInt(2, fm.getIdGenere());
            stm.setString(3, fm.getUrlTrailer());
            stm.setInt(4, fm.getDurata());
            stm.setString(5, fm.getTrama());
            stm.setString(6, fm.getUriLocandina());
            stm.executeUpdate();
        } finally {
            stm.close();
        }
        
        
    }
    
    public void inserisciSpettacolo(Spettacolo sp) throws SQLException{
        // possiamo farci passare tutta una classe spettacolo
        
        PreparedStatement stm;
        stm = con.prepareStatement(" INSERT INTO SPETTACOLO (ID_FILM, ID_SALA, DATA_ORA, URI_LOCANDINA) VALUES (?,?,'?','?') ");
        try {
            stm.setInt(1, sp.getIdFilm());
            stm.setInt(2, sp.getIdSala());
            stm.setTimestamp(3, sp.getDataOra());
            //stm.setInt(4, ()); mettiamo anche l'uri??
            stm.executeUpdate();
        } finally {
            stm.close();
        }
            
                
    }
    public void cambiaStato(Posto ps) throws SQLException{
       // prendo in input il posto che voglio modificare
        
        PreparedStatement stm;
        stm = con.prepareStatement(" UPDATE POSTO SET STATO=? ID_POSTO=? ");
        
        try {
            if(ps.getStato()==0){
                stm.setInt(1, -1);
            }
            else
            {
            stm.setInt(1,0);
            }

            stm.setInt(2, ps.getIdPosto());
            stm.executeUpdate();
        } finally {
            stm.close();
        }
            
                
    }
    
    public void aggiugiRuolo(Ruolo rl) throws SQLException{
       // prendo in input il ruolo che si vuole aggiungere
        
        PreparedStatement stm;
        stm = con.prepareStatement("  INSERT INTO RUOLO (RUOLO) VALUES ('?'); ");
        try {        
            stm.setString(1, rl.getRuolo());
            stm.executeUpdate();
        } finally {
            stm.close();
        }
            
                
    }
    
    public void aggiugiPosto(Posto ps) throws SQLException{
       // prendo in input il ruolo che si vuole aggiungere
        
        PreparedStatement stm;
        stm = con.prepareStatement(" INSERT INTO POSTO (RIGA,COLONNA,STATO) VALUES (?,?,?); ");
        try {
            stm.setInt(1, ps.getRiga());
            stm.setInt(2, ps.getColonna());
            stm.setInt(3, ps.getStato());
            stm.executeUpdate();
        } finally {
            stm.close();
        }    
                
    }
    
    
    public void aggiugiUtente(Utente ut) throws SQLException{
       // prendo in input il ruolo che si vuole aggiungere
        
        PreparedStatement stm;
        stm = con.prepareStatement("  INSERT INTO UTENTE (ID_RUOLO, EMAIL, PASSWORD) VALUES (?,?,?); ");
        try {        
            stm.setInt(1, ut.getIdRuolo());
            stm.setString(2, ut.getEmail());
            stm.setString(3, ut.getPassword());
            stm.executeUpdate();
        } finally {
        stm.close();
        }
            
                
    }
    
    public ArrayList<Object[]> getPrenotazioneTmp(int id_spettacolo) throws SQLException{
        PreparedStatement stm;
        ArrayList<Object[]> PrenotazioniTmp = new ArrayList<>();
        stm = con.prepareStatement("SELECT * FROM PRENOTAZIONETmp JOIN POSTO WHERE ID_SPETTACOLO = ?;");
        try {
            stm.setInt(1,id_spettacolo);
            ResultSet rs = stm.executeQuery();
            try {
               
                while(rs.next()){
                    PrenotazioneTmp tmp = new PrenotazioneTmp();
                    Posto posto = new Posto();
                    tmp.setIdUtente(rs.getString("ID_UTENTE"));
                    tmp.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
                    tmp.setIdPosto(rs.getInt("ID_POSTO"));
                    tmp.setTimestamp(rs.getTimestamp("DATA_ORA_OPERAZIONETmp"));
                    posto.setColonna(rs.getInt("COLONNA"));
                    posto.setRiga(rs.getString("RIGA").charAt(0));
                    posto.setIdPosto(rs.getInt("ID_POSTO"));
                    posto.setIdSala(rs.getInt("ID_SALA"));
                    posto.setStato(rs.getInt("STATO"));
                    Object res[] = new Object[2];
                    res[0] = tmp;
                    res[1] = posto;
                    PrenotazioniTmp.add(res);
                }
            } finally { 
                rs.close();
            }
        } finally {
            stm.close();
        }
        return PrenotazioniTmp;
    }
    
    
    public ArrayList<Object[]> getPrenotazione(int id_spettacolo) throws SQLException{
        PreparedStatement stm;
        ArrayList<Object[]> Prenotazioni = new ArrayList<>();
        stm = con.prepareStatement("SELECT * FROM PRENOTAZIONE WHERE ID_SPETTACOLO=?");
        try {
            stm.setInt(1,id_spettacolo);
            ResultSet rs = stm.executeQuery();
            try {
                while(rs.next()){
                    Prenotazione tmp = new Prenotazione();
                    Posto posto = new Posto();
                    tmp.setIdPrenotazione(rs.getInt("ID_PRENOTAZIONE"));
                    tmp.setIdUtente(rs.getInt("ID_UTENTE"));
                    tmp.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
                    tmp.setIdPrezzo(rs.getInt("ID_PREZZO"));
                    tmp.setIdPosto(rs.getInt("ID_POSTO"));
                    tmp.setDataOraOperazione(rs.getTimestamp("DATA_ORA_OPERAZIONE"));
                    posto.setColonna(rs.getInt("COLONNA"));
                    posto.setRiga(rs.getString("RIGA").charAt(0));
                    posto.setIdPosto(rs.getInt("ID_POSTO"));
                    posto.setIdSala(rs.getInt("ID_SALA"));
                    posto.setStato(rs.getInt("STATO")); //se è prenotato dovremmo essere già sicuri che è in buono stato? abbiamo già fatto questo controllo da qualche parte?
                    Object res[] = new Object[2];
                    res[0] = tmp;
                    res[1] = posto;
                    Prenotazioni.add(res);
                }
            } finally { 
                rs.close();
            }
        } finally {
            stm.close();
        }
        return Prenotazioni;
    }
    
    
    public void aggiungiPrenotazione(Prenotazione pre) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement("INSERT INTO PRENOTAZIONE (ID_PRENOTAZIONE, ID_UTENTE, ID_SPETTACOLO, ID_PREZZO, ID_POSTO, DATA_ORA_OPERAZIONE) VALUES (?,?,?,?,?,?); ");
        try {
            stm.setInt(1, pre.getIdPrenotazione());
            stm.setInt(2, pre.getIdUtente());
            stm.setInt(3, pre.getIdSpettacolo());
            stm.setInt(4, pre.getIdPrezzo());
            stm.setInt(5, pre.getIdPosto());
            stm.setTimestamp(6, pre.getDataOraOperazione());
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
        
        public void aggiungiPrenotazioneTmp(PrenotazioneTmp pre) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement("INSERT INTO PRENOTAZIONETMP (ID_SPETTACOLO, ID_POSTO, DATA_ORA_OPERAZIONETMP) VALUES (?,?,?) ");
        try {
            stm.setInt(1, pre.getIdSpettacolo());
            stm.setInt(2, pre.getIdPosto());
            stm.setTimestamp(3, pre.getTimestamp());
            stm.executeUpdate();
        } finally {
            stm.close();
        }
        
    }
    
    
    
    public void eliminaPrenotazioneTmp(Timestamp tm) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement("DELETE * FROM PRENOTAZIONETMP WHERE DATA_ORA_OPERAZIONETMP < '?'; ");
        try {
            stm.setTimestamp(1, tm);
            stm.executeUpdate();
        } finally {
            stm.close();
        }
        
    }
    
    
}
