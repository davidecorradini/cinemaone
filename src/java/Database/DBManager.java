
package Database;

/**
 *
 * @author enrico
 */

import Beans.Film;
import Beans.Posto;
import Beans.Prenotazione;
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
        ResultSet rs = stm.executeQuery();
        ArrayList<Prezzo> prezzi = new ArrayList<>();
        while(rs.next()){
            Prezzo tmp = new Prezzo();
            tmp.setIdPrezzo(rs.getInt("ID_PREZZO"));
            tmp.setPrezzo(rs.getDouble("PREZZO"));
            tmp.setTipo(rs.getString("TIPO"));
            prezzi.add(tmp);
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
        PreparedStatement stm = con.prepareStatement(
                "SELECT S.ID_SPETTACOLO, S.ID_FILM, S.ID_SALA, S.DATA_ORA FROM SPETTACOLO S WHERE S.ID_FILM = ?");
        stm.setInt(1, film.getIdFilm());
        ResultSet rs = stm.executeQuery();
        ArrayList<Spettacolo> spettacoli = new ArrayList<>();
        while(rs.next()){
            Spettacolo tmp = new Spettacolo();
            tmp.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
            tmp.setIdFilm(rs.getInt("ID_FILM"));
            tmp.setIdSala(rs.getInt("ID_SALA"));
            tmp.setDataOra(rs.getTimestamp("DATA_ORA"));
            spettacoli.add(tmp);
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
        PreparedStatement stm = con.prepareStatement(
                "SELECT P.ID_POSTO, P.ID_SALA, P.RIGA, P.COLONNA, P.STATO FROM POSTO P WHERE P.STATO = 0 AND P.ID_SALA = ? AND P.ID_POSTO NOT IN( SELECT PR.ID_POSTO FROM PRENOTAZIONE PR WHERE PR.ID_SPETTACOLO = ?");
        
        stm.setInt(1, spetta.getIdSala());
        stm.setInt(2, spetta.getIdSpettacolo());
        ResultSet rs = stm.executeQuery();
        ArrayList<Posto> posti = new ArrayList<>();
        while(rs.next()){
            Posto tmp = new Posto();
            tmp.setIdPosto(rs.getInt("ID_POSTO"));
            tmp.setIdSala(rs.getInt("ID_SALA"));
            tmp.setRiga(rs.getInt("RIGA"));
            tmp.setColonna(rs.getInt("COLONNA"));
            tmp.setStato(rs.getInt("STATO"));
            posti.add(tmp);
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
        stm.setInt(1, preno.getIdUtente());
        stm.setInt(2, preno.getIdSpettacolo());
        stm.setInt(3, preno.getIdPrezzo());
        stm.setTimestamp(4, preno.getDataOraOperazione());
        ResultSet rs = stm.executeQuery();
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
        stm.setString(1, ut.getPassword());
        stm.setInt(2, ut.getIdUtente());
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
        
        stm.setInt(1, ut.getIdUtente());
        ResultSet rs = stm.executeQuery();
        ArrayList<Object[]> res = new ArrayList<>();
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
        return res;
    }
    
    
    public Integer[] getPostiIncasso(Spettacolo sp) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement("SELECT COUNT(*) AS TOT_POSTI, SUM(PR.PREZZO) AS TOT_PREZZO FROM PREZZO AS PR JOIN PRENOTAZIONE AS P ON PR.ID_PREZZO=P.ID_PREZZO WHERE P.ID_SPETTACOLO=?");
        
        stm.setInt(1, sp.getIdSpettacolo());
        
        ResultSet rs = stm.executeQuery();
        Integer res[]=new Integer[2];
        res[0]=rs.getInt("TOT_POSTI");
        res[1]=rs.getInt("TOT_PREZZO");
        
        return res;
        
    }
    
    
    public ArrayList<Object[]> getFilmPrezzo(Utente ut) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement( " SELECT DISTINCT F.ID_FILM, SUM (PR.PREZZO) AS TOT\n" +
                "FROM FILM F JOIN SPETTACOLO S ON F.ID_FILM = S.ID_FILM JOIN PRENOTAZIONE P ON P.ID_SPETTACOLO = S.ID_SPETTACOLO JOIN PREZZO PR ON PR.ID_PREZZO = P.ID_PREZZO\n" +
                "GROUP BY F.ID_FILM " );
        
        
        ResultSet rs = stm.executeQuery();
        ArrayList<Object[]> res = new ArrayList<>();
        while(rs.next()){
            Object[] tmp = new Object[2];
            
            tmp[0] = rs.getInt("TOT");
            tmp[1] = rs.getInt("F.ID_FILM");
            
            res.add(tmp);
        }
        return res;
    }
    
    
    
    
    
    public ArrayList<Utente> getClientiTop(Utente ut) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement(" SELECT U.ID_UTENTE\n" + /// BISOGNA INSERIRE IL LIMIT!! A ME NON FUNZONA PERO
                "FROM UTENTE U JOIN PRENOTAZIONE PR ON U.ID_UTENTE=PR.ID_UTENTE\n" +
                "GROUP BY U.ID_UTENTE\n" +
                "ORDER BY COUNT(*) DESC ");
        
        
        ResultSet rs = stm.executeQuery();
        ArrayList<Utente> clienti = new ArrayList<>();
        while(rs.next()){
            Utente tmp = new Utente();
            tmp.setIdUtente(rs.getInt("ID_UTENTE"));
            clienti.add(tmp);
        }
        return clienti;
        
    }
    
    
    public void deletePrenotazione (Prenotazione pr, Utente ut) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement( "DELETE FROM PRENOTAZIONE P\n" +
                "WHERE P.ID_PRENOTAZIONE=? ");
        stm.setInt(1, pr.getIdPrenotazione());
        
        
        PreparedStatement stm2;
        stm2 = con.prepareStatement( " UPDATE UTENTE\n" +
                "SET CREDITO=CREDITO-?\n" +
                "WHERE UTENTE_ID=? ");
        int x=0; // in qualche modo x deve riuscire a tirare su il prezzo del biglietto
        
        stm.setDouble(1, x*0.8);
        stm.setInt(1, ut.getIdUtente());
        
        
        
    }
    
    
    public Integer[] getPostiPiuPrenotati(Posto ps, int n) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement(" SELECT P2.ID_POSTO\n" +
                "FROM \n" +
                "	(SELECT P.ID_POSTO, COUNT (*) TOT\n" +
                "	FROM POSTO P JOIN PRENOTAZIONE PR ON PR.ID_POSTO=P.ID_POSTO\n" +
                "	WHERE P.ID_SALA=?\n" +
                "	GROUP BY P.ID_POSTO) P2\n" +
                "ORDER BY P2.TOT\n "+
                "");// manca il limit!!
        
        stm.setInt(1, ps.getIdSala());
        
        ResultSet rs = stm.executeQuery();
        Integer res[]=new Integer[n];
        int cont=0;
        while(rs.next()){
            
            res[cont]=(rs.getInt("ID_POSTO"));
            cont++;
            
        }
        
        
        
        return res;
        
    }
    
    
    public void aggiornaCredito(Utente ut, int credito, Posto ps, Prenotazione pre) throws SQLException{
        // possiamo scegliere se farci passare tutto o solo prenotazione
        
        PreparedStatement stm;
        stm = con.prepareStatement(" UPDATE UTENTE\n" +
                "	SET CREDITO=?\n" +
                "	WHERE ID_UTENTE=?\n" +
                " ");
        stm.setInt(1, credito);
        stm.setInt(2, ut.getIdUtente());
        
        PreparedStatement stm2;
        stm2 = con.prepareStatement(" UPDATE POSTO \n" +
                "SET STATO=0 \n" +
                "WHERE ID_SALA=? AND ID_POSTO=? ");
        stm2.setInt(1, ps.getIdSala());
        stm2.setInt(2, ps.getIdPosto());
        
        PreparedStatement stm3;
        stm3 = con.prepareStatement(" INSERT INTO PRENOTAZIONE (ID_UTENTE, ID_SPETTACOLO, ID_PREZZO, ID_POSTO, DATA_ORA_OPERAZIONE)\n" +
"VALUES (?,?,?,?,?); ");
        stm3.setInt(1, pre.getIdUtente());
        stm3.setInt(2, pre.getIdSpettacolo());
        stm3.setInt(3, pre.getIdPrezzo());
        stm3.setInt(4, pre.getIdPosto());
        stm3.setTimestamp(5, pre.getDataOraOperazione());
    }
    
    public void inserisciFilm(Film fm) throws SQLException{
        // possiamo farci passare tutta una classe film
        
        PreparedStatement stm;
        stm = con.prepareStatement(" INSERT INTO FILM (TITOLO, ID_GENERE, URL_TRAILER,DURATA,TRAMA, URI_LOCANDINA)\n" +
"VALUES ('?',?,'?',?,'?', '?') ");
        stm.setString(1, fm.getTitolo());
        stm.setInt(2, fm.getIdGenere());
        stm.setString(3, fm.getUrlTrailer());
        stm.setInt(4, fm.getDurata());
        stm.setString(5, fm.getTrama());
        stm.setString(6, fm.getUriLocandina());
        
        
        
    }
    
    public void inserisciSpettacolo(Spettacolo sp) throws SQLException{
        // possiamo farci passare tutta una classe spettacolo
        
        PreparedStatement stm;
        stm = con.prepareStatement(" INSERT INTO SPETTACOLO (ID_FILM, ID_SALA, DATA_ORA, URI_LOCANDINA)\n" +
"VALUES (?,?,'?','?'); ");
        stm.setInt(1, sp.getIdFilm());
        stm.setInt(2, sp.getIdSala());
        stm.setTimestamp(3, sp.getDataOra());
        //stm.setInt(4, ()); mettiamo anche l'uri??
            
                
    }
    public void cambiaStato(Posto ps) throws SQLException{
       // prendo in input il posto che voglio modificare
        
        PreparedStatement stm;
        stm = con.prepareStatement(" UPDATE POSTO SET STATO=? ID_POSTO=? ");
        
        if(ps.getStato()==0){
            stm.setInt(1, -1);
        }
        else
        {
        stm.setInt(1,0);
        }
        
        stm.setInt(2, ps.getIdPosto());
        
            
                
    }
    
    public void aggiugiRuolo(Ruolo rl) throws SQLException{
       // prendo in input il ruolo che si vuole aggiungere
        
        PreparedStatement stm;
        stm = con.prepareStatement("  INSERT INTO RUOLO (RUOLO)\n" +
                "VALUES ('?'); ");
                
        stm.setString(1, rl.getRuolo());
        
            
                
    }
    
    public void aggiugiPosto(Posto ps) throws SQLException{
       // prendo in input il ruolo che si vuole aggiungere
        
        PreparedStatement stm;
        stm = con.prepareStatement(" INSERT INTO POSTO (RIGA,COLONNA,STATO)\n" +
"VALUES (?,?,?); ");
                
        stm.setInt(1, ps.getRiga());
        stm.setInt(2, ps.getColonna());
        stm.setInt(3, ps.getStato());
        
            
                
    }
    
    
    public void aggiugiUtente(Utente ut) throws SQLException{
       // prendo in input il ruolo che si vuole aggiungere
        
        PreparedStatement stm;
        stm = con.prepareStatement("  INSERT INTO UTENTE (ID_RUOLO, EMAIL, PASSWORD)\n" +
"VALUES (1,'gino.perna@cinemaone.it','gino'); ");
                
        stm.setInt(1, ut.getIdRuolo());
        stm.setString(2, ut.getEmail());
        stm.setString(3, ut.getPassword());
        
            
                
    }
    
    
    
    
    
    
    
    
}
