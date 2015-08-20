
package Database;

/**
 *
 * @author enrico
 */

import Beans.Film;
import Beans.FilmPrezzo;
import Beans.FilmSpettacoli;
import Beans.IncassoFilm;
import Beans.Posto;
import Beans.PrenTmpPosto;
import Beans.Prenotazione;
import Beans.PrenotazionePosto;
import Beans.PrenotazioneTmp;
import Beans.Prezzo;
import Beans.Ruolo;
import Beans.Sala;
import Beans.Spettacolo;
import Beans.SpettacoloSalaOrario;
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
     * inserisce una nuova prenotazione.
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
     * cambia password all'utente. L'oggetto utente deve contenere l'id dell'utente e la nuova password nel campo password
     * @param ut utente di cui si vuole cambiare la password, nel campo password di tale oggetto deve esserci la nuova password
     * @throws SQLException
     */
    public void cambiaPassword(Utente ut) throws SQLException{
        cambiaPassword(ut.getIdUtente(), ut.getPassword());
    }
    /**
     * assegna la password all'utente con id idUtente.
     * @param idUtente
     * @param password
     * @throws SQLException 
     */
    public void cambiaPassword(int idUtente, String password) throws SQLException{
       PreparedStatement stm = con.prepareStatement(
                "UPDATE UTENTE SET PASSWORD = ? WHERE ID_UTENTE = ?");
        try {
            stm.setString(1, password);
            stm.setInt(2, idUtente);
            stm.executeUpdate();
        } finally {
            stm.close();
        }  
    }
    
    /**
     * cancella una prenotazione di un utente dal database.
     * @param pr prenotazione da eliminare
     * @param ut utente a cui rimborsare il costo della prenotazione
     * @throws SQLException
     */
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
    
    /**
     *
     * @param ut utente a cui aggiornare il credito
     * @param credito credito da inserire in utente
     * @param ps posto da assegnare
     * @param pre prenotazione da inserire
     * @throws SQLException
     */
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
    
    /**
     * inserisce un nuovo film nel database
     * @param fm film da inserire nel database
     * @throws SQLException
     */
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
    
    /**
     * inserisce un nuovo spettacolo nel database.
     * @param sp spettacolo da inserire nel database
     * @throws SQLException
     */
    public void inserisciSpettacolo(Spettacolo sp) throws SQLException{
        // possiamo farci passare tutta una classe spettacolo
        
        PreparedStatement stm;
        stm = con.prepareStatement(" INSERT INTO SPETTACOLO (ID_FILM, ID_SALA, DATA_ORA) VALUES (?,?,'?') ");
        try {
            stm.setInt(1, sp.getIdFilm());
            stm.setInt(2, sp.getIdSala());
            stm.setTimestamp(3, sp.getDataOra());
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
    
    /**
     * modifica lo stato del posto.
     * @param ps posto a cui va modificato lo stato
     * @throws SQLException
     */
    public void cambiaStato(Posto ps) throws SQLException{
        // prendo in input il posto che voglio modificare
        
        PreparedStatement stm;
        stm = con.prepareStatement(" UPDATE POSTO SET STATO=? ID_POSTO=? ");
        try {
            stm.setInt(1, ps.getStato());            
            stm.setInt(2, ps.getIdPosto());
            stm.executeUpdate();
        } finally {
            stm.close();
        } 
    }
    
    /**
     * aggiunge un ruolo.
     * @param rl ruolo da aggiungere
     * @throws SQLException
     */
    public void aggiugiRuolo(Ruolo rl) throws SQLException{
        // prendo in input il ruolo che si vuole aggiungere
        
        PreparedStatement stm;
        stm = con.prepareStatement("INSERT INTO RUOLO (RUOLO) VALUES ('?')");
        try {
            stm.setString(1, rl.getRuolo());
            stm.executeUpdate();
        } finally {
            stm.close();
        }
        
        
    }
    
    /**
     *
     * @param ps posto da aggiungere
     * @throws SQLException
     */
    public void aggiugiPosto(Posto ps) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement("INSERT INTO POSTO (RIGA,COLONNA,STATO) VALUES (?,?,?)");
        try {
            stm.setInt(1, ps.getRiga());
            stm.setInt(2, ps.getColonna());
            stm.setInt(3, ps.getStato());
            stm.executeUpdate();
        } finally {
            stm.close();
        }
        
    }
    
    /**
     * inserisce un nuovo utente nel database.
     * @param ut utente da aggiungere
     * @throws SQLException
     */
    public void aggiugiUtente(Utente ut) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement("INSERT INTO UTENTE (ID_RUOLO, EMAIL, PASSWORD) VALUES (?,?,?); ");
        try {
            stm.setInt(1, ut.getIdRuolo());
            stm.setString(2, ut.getEmail());
            stm.setString(3, ut.getPassword());
            stm.executeUpdate();
        } finally {
            stm.close();
        }
        
        
    }
    
    /**
     * aggiunge una prenotazione nel database.
     * @param pre prenotazione da aggiungere
     * @throws SQLException
     */
    public void aggiungiPrenotazione(Prenotazione pre) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement("INSERT INTO PRENOTAZIONE (ID_PRENOTAZIONE, ID_UTENTE, ID_SPETTACOLO, ID_PREZZO, ID_POSTO, DATA_ORA_OPERAZIONE) VALUES (?,?,?,?,?,?)");
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
    
    /**
     * aggiunge una prenotazione temporanea nel database.
     * @param pre prenotazionetmp da aggiungere
     * @throws SQLException
     */
    public void aggiungiPrenotazioneTmp(PrenotazioneTmp pre) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement("INSERT INTO PRENOTAZIONETMP (ID_SPETTACOLO, ID_POSTO, DATA_ORA_OPERAZIONETMP) VALUES (?,?,?)");
        try {
            stm.setInt(1, pre.getIdSpettacolo());
            stm.setInt(2, pre.getIdPosto());
            stm.setTimestamp(3, pre.getTimestamp());
            stm.executeUpdate();
        } finally {
            stm.close();
        }
        
    }
    
    
    /**
     * elimina una prenotazione temporanea.
     * @param tm limite minimo di validità delle prenotazionitmp (quelle piu vecchie vengono eliminate)
     * @throws SQLException
     */
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
    
    
    /**
     * selezione gli n posti più prenotati.
     * @param ps posto da cui ricavare IdSala
     * @param n numero di posti ritornati
     * @return array di Integer contenente gli ID dei posti più prenotati
     * @throws SQLException
     */
    public Integer[] getPostiPiuPrenotati(Posto ps, int n) throws SQLException{
        PreparedStatement stm;
        Integer res[]=new Integer[n];
        stm = con.prepareStatement(
                "SELECT P2.ID_POSTO FROM (SELECT P.ID_POSTO, COUNT (*) AS TOT\n" +
                        "FROM POSTO P JOIN PRENOTAZIONE PR ON PR.ID_POSTO=P.ID_POSTO\n"+
                        "WHERE P.ID_SALA=? GROUP BY P.ID_POSTO) P2\n" +
                        "ORDER BY P2.TOT");
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
    
    /**
     *
     * @param sp spettacolo di cui vogliamo visionare il numero di posti prenotati e l'incasso
     * @return array di Integer il cui primo elemento è il numero di posti prenotati e il secondo è l'incasso
     * @throws SQLException
     */
    public Integer[] getPostiIncasso(Spettacolo sp) throws SQLException{
        Integer res[]=new Integer[2];
        PreparedStatement stm;
        stm = con.prepareStatement(
                "SELECT COUNT(*) AS TOT_POSTI, SUM(PR.PREZZO) AS TOT_PREZZO\n" +
                        "FROM PREZZO AS PR JOIN PRENOTAZIONE AS P ON PR.ID_PREZZO=P.ID_PREZZO\n" +
                        "WHERE P.ID_SPETTACOLO=?");
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
    
    
    /**
     * Autentica un utente in base a un nome utente (e-mail) e a una password
     *
     * @param email l'indirizzo e-mail dell'utente
     * @param password la password
     * @return null se l'utente non è autenticato, un oggetto User se l'utente esiste ed è autenticato
     * @throws java.sql.SQLException
     */
    public Utente authenticate(String email, String password) throws SQLException {
        
        PreparedStatement stm = con.prepareStatement(
                "SELECT ID_UTENTE, CREDITO, ID_RUOLO\n" + 
                        "FROM UTENTE\n" + 
                        "WHERE EMAIL = ? AND PASSWORD = ?");
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
                rs.close();
            }
        } finally {
            stm.close();
        }
    }
    
    
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
     * dato un film restituisce tutti gli spettacoli  nei quali verrà o è stato proiettato.
     * @param film film di cui si vogliono avere tutti gli spettacoli
     * @return lista di spettacoli del film
     * @throws SQLException
     */
    public ArrayList<Spettacolo> getSpettacoli(Film film) throws SQLException{
        ArrayList<Spettacolo> spettacoli = new ArrayList<>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT S.ID_SPETTACOLO, S.ID_FILM, S.ID_SALA, S.DATA_ORA\n" + 
                        "FROM SPETTACOLO S\n" + 
                        "WHERE S.ID_FILM = ?");
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
     * per ogni spettacolo, ancora da eseguire, specifica quando e in che sala avranno luogo.
     * @return uno spettacolo che sarà fatto in una determinata sala ad uno specifico orario.
     * @throws SQLException
     */    
    
    public ArrayList<SpettacoloSalaOrario> getSpettacoli() throws SQLException{
        ArrayList<SpettacoloSalaOrario> res = new ArrayList<>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT F.ID_FILM, F.ID_GENERE, F.TITOLO, F.DURATA, F.TRAMA, F.URL_TRAILER,F.IS_IN_SLIDER, F.URI_LOCANDINA,S.ID_SALA,S.NOME,S.DESCRIZIONE, SP.DATA_ORA\n" + 
                "FROM FILM F JOIN SPETTACOLO SP ON F.ID_FILM = SP.ID_FILM JOIN SALA S ON S.ID_SALA=SP.ID_SALA\n" + 
                "WHERE SP.DATA_ORA >= CURRENT_TIMESTAMP;");
        ResultSet rs = stm.executeQuery();
        try {
            while(rs.next()){
                SpettacoloSalaOrario tmp = new SpettacoloSalaOrario();
                Film tmpFilm = new Film();
                Sala tmpSala = new Sala();
                Timestamp dataOra;
                tmpFilm.setDurata(rs.getInt("DURATA"));
                tmpFilm.setIdFilm(rs.getInt("ID_FILM"));
                tmpFilm.setIdGenere(rs.getInt("ID_GENERE"));
                tmpFilm.setIsInSlider(rs.getBoolean("IS_IN_SLIDER"));
                tmpFilm.setTitolo(rs.getString("TITOLO"));
                tmpFilm.setTrama(rs.getString("TRAMA"));
                tmpFilm.setUriLocandina(rs.getString("URI_LOCANDINA"));
                tmpFilm.setUrlTrailer(rs.getString("URL_TRAILER"));
                tmpSala.setDescrizione(rs.getString("DESCRIZIONE"));
                tmpSala.setIdSala(rs.getInt("ID_SALA"));
                tmpSala.setNome(rs.getString("NOME"));
                dataOra = rs.getTimestamp("DATA_ORA");
                tmp.setFilm(tmpFilm);
                tmp.setSala(tmpSala);
                tmp.setDataOra(dataOra);
                res.add(tmp);
            }
        } finally {
            rs.close();
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
     * permette di ottenere uno storico dell'utente: che film ha guardato e quando ha pagato.
     * @param ut utente di cui si vuole ottenere lo storico
     * @return un array di coppie di oggetti, di cui il primo è un instanza di Film e il secondo di Prezzo
     * @throws SQLException
     */
    public ArrayList<FilmPrezzo> getStorico(Utente ut) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement(
                "SELECT DISTINCT F.ID_FILM, F.ID_GENERE, F.TITOLO, F.DURATA, F.TRAMA, F.URL_TRAILER, F.URI_LOCANDINA, PR.ID_PREZZO, PR.TIPO, PR.PREZZO\n" + 
                        "FROM FILM F JOIN SPETTACOLO S ON F.ID_FILM = S.ID_FILM JOIN PRENOTAZIONE P ON P.ID_SPETTACOLO = S.ID_SPETTACOLO JOIN PREZZO PR ON PR.ID_PREZZO = P.ID_PREZZO\n" + 
                        "WHERE P.ID_UTENTE = ?");
        ArrayList<FilmPrezzo> res = new ArrayList<>();
        try {
            stm.setInt(1, ut.getIdUtente());
            ResultSet rs = stm.executeQuery();
            try {
                
                while(rs.next()){
                    FilmPrezzo tmp = new FilmPrezzo();
                    
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
                    
                    tmp.setFilm(tmpFilm);
                    tmp.setPrezzo(tmpPrezzo);
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
    
    
    /**
     *
     * @return un oggetto che permette di accedere al film e al suo incasso.
     * @throws SQLException
     */
    public ArrayList<IncassoFilm> getFilmIncasso() throws SQLException{
        ArrayList<IncassoFilm> res = new ArrayList<>();
        PreparedStatement stm;
        stm = con.prepareStatement(
                "SELECT DISTINCT F.ID_FILM, SUM (PR.PREZZO) AS TOT\n" + 
                "FROM FILM F JOIN SPETTACOLO S ON F.ID_FILM = S.ID_FILM JOIN PRENOTAZIONE P ON P.ID_SPETTACOLO = S.ID_SPETTACOLO JOIN PREZZO PR ON PR.ID_PREZZO = P.ID_PREZZO\n" + 
                "GROUP BY F.ID_FILM" );
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while(rs.next()){
                    IncassoFilm tmp = new IncassoFilm();
                    
                    tmp.setIdFilm(rs.getInt("F.ID_FILM"));
                    tmp.setIncasso(rs.getDouble("TOT"));                   
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
    
    
    
    
    /**
     *
     * @param num numero dei clienti da ritornare
     * @return ArrayList contenente i num clienti migliori (quelli che hanno/hanno fatto più prenotazioni).
     * @throws SQLException
     */
    public ArrayList<Utente> getClientiTop(int num) throws SQLException{
        ArrayList<Utente> clienti = new ArrayList<>();
        int i=0;
        PreparedStatement stm;
        stm = con.prepareStatement(
                "SELECT U.ID_UTENTE\n" + 
                        "FROM UTENTE U JOIN PRENOTAZIONE PR ON U.ID_UTENTE=PR.ID_UTENTE\n" + 
                        "GROUP BY U.ID_UTENTE ORDER BY COUNT(*) DESC");
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
    
    
    
    /**
     *
     * @param id_spettacolo ID dello spettacolo di cui si vuole vedere la lista di prenotazioni temporanee
     * @return prenotazioni temporanee dei posti per il dato spettacolo.
     * @throws SQLException
     */
    public ArrayList<PrenTmpPosto> getPrenotazioneTmp(int id_spettacolo) throws SQLException{
        PreparedStatement stm;
        ArrayList<PrenTmpPosto> PrenotazioniTmp = new ArrayList<>();
        stm = con.prepareStatement(
                "SELECT *\n" +
                "FROM PRENOTAZIONETmp JOIN POSTO WHERE ID_SPETTACOLO = ?");
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
                    PrenTmpPosto res = new PrenTmpPosto();
                    res.setPren(tmp);
                    res.setPosto(posto);
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
    
    
    /**
     *
     * @param id_spettacolo ID dello spettacolo di cui si vuole vedere la lista di prenotazioni
     * @return prenotazioni dei posti relative al dato spettacolo.
     * @throws SQLException
     */
    public ArrayList<PrenotazionePosto> getPrenotazioni(int id_spettacolo) throws SQLException{
        PreparedStatement stm;
        ArrayList<PrenotazionePosto> prenotazioni = new ArrayList<>();
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
                    PrenotazionePosto res = new PrenotazionePosto();
                    res.setPrenotazione(tmp);
                    res.setPosto(posto);
                    prenotazioni.add(res);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return prenotazioni;
    }
    
    
    
    /**
     *
     * @return ArrayList dei film presenti nello slider.
     * @throws SQLException
     */
    public ArrayList<Film> getFilmsSlider() throws SQLException{
        ArrayList<Film> film = new ArrayList<>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT ID_FILM, TITOLO, ID_GENERE,URL_TRAILER, DURATA, TRAMA,URI_LOCANDINA,IS_IN_SLIDER FROM FILM\n" +
                        "WHERE IS_IN_SLIDER=TRUE");
        try {
            ResultSet rs = stm.executeQuery();
            try {
                
                while(rs.next()){
                    Film tmp = new Film();
                    tmp.setIdFilm(rs.getInt("ID_FILM"));
                    tmp.setTitolo(rs.getString("TITOLO"));
                    tmp.setIdGenere(rs.getInt("ID_GENERE"));
                    tmp.setUrlTrailer(rs.getString("URL_TRAILER"));
                    tmp.setDurata(rs.getInt("DURATA"));
                    tmp.setUriLocandina(rs.getString("TRAMA"));
                    tmp.setTrama(rs.getString("URI_LOCANDINA"));
                    tmp.setIsInSlider(rs.getBoolean("IS_IN_SLIDER"));
                    
                    film.add(tmp);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return film;
    }
    
    
    
    
    
    
    /**
     *
     * @param id_spettacolo ID dello spettacolo di cui si vuole vedere la lista dei posti occupati
     * @return ritorna una lista di postioccupati.
     * @throws SQLException
     */
    public ArrayList<PrenotazionePosto> getPostiOccupati(Integer id_spettacolo) throws SQLException{
        ArrayList<PrenotazionePosto> res = new ArrayList<>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT P.ID_POSTO, P.ID_SALA, P.RIGA, P.COLONNA, P.STATO,PR2.ID_PRENOTAZIONE,PR2.ID_UTENTE,PR2.ID_SPETTACOLO,PR2.ID_POSTO,PR2.ID_PREZZO,PR2.DATA_ORA_OPERAZIONE\n" + 
                        "FROM POSTO P JOIN PRENOTAZIONE PR2 ON PR2.ID_POSTO=P.ID_POSTO\n" + 
                        "WHERE P.ID_POSTO IN(\n"+ 
                        "SELECT PR.ID_POSTO FROM PRENOTAZIONE PR WHERE PR.ID_SPETTACOLO = ?)");
        try {
            stm.setInt(1, id_spettacolo);
            ResultSet rs = stm.executeQuery();
            try {
                while(rs.next()){
                    
                    PrenotazionePosto tmp = new PrenotazionePosto();
                    
                    Prenotazione tmpPre = new Prenotazione();
                    
                    tmpPre.setIdPrenotazione(rs.getInt("ID_PRENOTAZIONE"));
                    tmpPre.setIdUtente(rs.getInt("ID_UTENTE"));
                    tmpPre.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
                    tmpPre.setIdPrezzo(rs.getInt("ID_PREZZO"));
                    tmpPre.setIdPosto(rs.getInt("ID_POSTO"));
                    tmpPre.setDataOraOperazione(rs.getTimestamp("DATA_ORA_OPERAZIONE"));
                    
                    
                    
                    Posto tmpPosto = new Posto();
                    tmpPosto.setIdPosto(rs.getInt("ID_POSTO"));
                    tmpPosto.setIdSala(rs.getInt("ID_SALA"));
                    tmpPosto.setRiga(rs.getString("RIGA").charAt(0));
                    tmpPosto.setColonna(rs.getInt("COLONNA"));
                    tmpPosto.setStato(rs.getInt("STATO"));
                    
                    tmp.setPrenotazione(tmpPre);
                    tmp.setPosto(tmpPosto);
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
    
    /**
     * 
     * @return lista di Film e relativi spettacoli.
     * @throws SQLException 
     */
    public ArrayList<FilmSpettacoli> getFilmESpettacoli() throws SQLException{
        ArrayList<FilmSpettacoli> res = new ArrayList<>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT F.ID_FILM, F.ID_GENERE, F.TITOLO, F.DURATA, F.TRAMA, F.URL_TRAILER,F.IS_IN_SLIDER, F.URI_LOCANDINA, SP.ID_SPETTACOLO,SP.ID_FILM,SP.ID_SALA,SP.DATA_ORA\n" +
                "FROM FILM F JOIN SPETTACOLO SP ON F.ID_FILM = SP.ID_FILM\n" +
                "WHERE SP.DATA_ORA >= CURRENT_TIMESTAMP\n" +
                "ORDER BY F.ID_FILM, SP.DATA_ORA;");
        ResultSet rs = stm.executeQuery();
        try {
            int oldId = 0;
            ArrayList<Spettacolo> tmpSpettacoli = new ArrayList<>();            
            FilmSpettacoli tmp = new FilmSpettacoli();
            if(rs.next()){
                Spettacolo tmpSpettacolo = new Spettacolo();
                tmpSpettacolo.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
                tmpSpettacolo.setIdSala(rs.getInt("ID_SALA"));
                tmpSpettacolo.setIdFilm(rs.getInt("ID_FILM"));
                tmpSpettacolo.setDataOra(rs.getTimestamp("DATA_ORA"));
                
                oldId = rs.getInt("ID_FILM");
                Film tmpFilm = new Film();
                tmpFilm.setIdFilm(rs.getInt("ID_FILM"));
                tmpFilm.setIdGenere(rs.getInt("ID_GENERE"));
                tmpFilm.setDurata(rs.getInt("DURATA"));
                tmpFilm.setTitolo(rs.getString("TITOLO"));
                tmpFilm.setTrama(rs.getString("TRAMA"));
                tmpFilm.setIsInSlider(rs.getBoolean("IS_IN_SLIDER"));
                tmpFilm.setUriLocandina(rs.getString("URI_LOCANDINA"));
                tmpFilm.setUrlTrailer(rs.getString("URL_TRAILER"));
                
                tmp.setFilm(tmpFilm);
                tmp.setSpettacoli(tmpSpettacoli);            
                
                tmpSpettacoli.add(tmpSpettacolo);
            }
            
            while(rs.next()){
                Spettacolo tmpSpettacolo = new Spettacolo();
                tmpSpettacolo.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
                tmpSpettacolo.setIdSala(rs.getInt("ID_SALA"));
                tmpSpettacolo.setIdFilm(rs.getInt("ID_FILM"));
                tmpSpettacolo.setDataOra(rs.getTimestamp("DATA_ORA"));
                
                int idFilm = rs.getInt("ID_FILM");
                if(idFilm != oldId){
                    res.add(tmp);                    
                    tmp = new FilmSpettacoli();
                    Film tmpFilm = new Film();
                    tmpFilm.setIdFilm(rs.getInt("ID_FILM"));
                    tmpFilm.setIdGenere(rs.getInt("ID_GENERE"));
                    tmpFilm.setDurata(rs.getInt("DURATA"));
                    tmpFilm.setTitolo(rs.getString("TITOLO"));
                    tmpFilm.setTrama(rs.getString("TRAMA"));
                    tmpFilm.setIsInSlider(rs.getBoolean("IS_IN_SLIDER"));
                    tmpFilm.setUriLocandina(rs.getString("URI_LOCANDINA"));
                    tmpFilm.setUrlTrailer(rs.getString("URL_TRAILER"));
                    tmpSpettacoli = new ArrayList<>();
                    
                    tmp.setFilm(tmpFilm);
                    tmp.setSpettacoli(tmpSpettacoli);
                    oldId = idFilm;
                }
                tmpSpettacoli.add(tmpSpettacolo);
            }
        } finally {
            rs.close();
        }
        return res;
    }
    
    /**
     * lista di spettacoli del film.
     * @param filmId
     * @return
     * @throws SQLException 
     */
    public FilmSpettacoli getFilmESpettacoli(int filmId) throws SQLException{
        FilmSpettacoli res = new FilmSpettacoli();
        PreparedStatement stm = con.prepareStatement(
                "SELECT F.ID_FILM, F.ID_GENERE, F.TITOLO, F.DURATA, F.TRAMA, F.URL_TRAILER,F.IS_IN_SLIDER, F.URI_LOCANDINA, SP.ID_SPETTACOLO,SP.ID_FILM,SP.ID_SALA,SP.DATA_ORA\n" +
                "FROM FILM F JOIN SPETTACOLO SP ON F.ID_FILM = SP.ID_FILM\n" +
                "WHERE SP.DATA_ORA >= CURRENT_TIMESTAMP AND F.ID_FILM = ?\n" +
                "ORDER BY SP.DATA_ORA;");
        stm.setInt(1, filmId);
        ResultSet rs = stm.executeQuery();
        ArrayList<Spettacolo> tmpSpettacoli = null;
        Film tmpFilm = null;
        if(rs.next()){
            tmpSpettacoli = new ArrayList<>();
            tmpFilm = new Film();
            tmpFilm.setIdFilm(rs.getInt("ID_FILM"));
            tmpFilm.setIdGenere(rs.getInt("ID_GENERE"));
            tmpFilm.setDurata(rs.getInt("DURATA"));
            tmpFilm.setTitolo(rs.getString("TITOLO"));
            tmpFilm.setTrama(rs.getString("TRAMA"));
            tmpFilm.setIsInSlider(rs.getBoolean("IS_IN_SLIDER"));
            tmpFilm.setUriLocandina(rs.getString("URI_LOCANDINA"));
            tmpFilm.setUrlTrailer(rs.getString("URL_TRAILER"));
                        
            Spettacolo tmpSpettacolo = new Spettacolo();
            tmpSpettacolo.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
            tmpSpettacolo.setIdSala(rs.getInt("ID_SALA"));
            tmpSpettacolo.setIdFilm(rs.getInt("ID_FILM"));
            tmpSpettacolo.setDataOra(rs.getTimestamp("DATA_ORA"));
            tmpSpettacoli.add(tmpSpettacolo);
        }
        while(rs.next()){
            Spettacolo tmpSpettacolo = new Spettacolo();
            tmpSpettacolo.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
            tmpSpettacolo.setIdSala(rs.getInt("ID_SALA"));
            tmpSpettacolo.setIdFilm(rs.getInt("ID_FILM"));
            tmpSpettacolo.setDataOra(rs.getTimestamp("DATA_ORA"));
            tmpSpettacoli.add(tmpSpettacolo);
        }        
        res.setFilm(tmpFilm);
        res.setSpettacoli(tmpSpettacoli);
        return res;
    }
}
