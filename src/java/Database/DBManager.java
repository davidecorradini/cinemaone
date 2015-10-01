
package Database;



import Beans.EmailTimestamp;
import Beans.Film;
import Beans.FilmPrezzo;
import Beans.FilmSpettacoli;
import Beans.Genere;
import Beans.IncassoFilm;
import Beans.InfoPrenotazione;
import Beans.PostiSala;
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
import Beans.UtenteRuolo;
import java.io.Serializable;
import static java.lang.Math.random;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

public class DBManager implements Serializable {
    // transient = non viene serializzato
    
    private final transient Connection con;
    
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
    public void storePrenotazione(Prenotazione preno) throws SQLIntegrityConstraintViolationException, SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement(
                "INSERT INTO PRENOTAZIONE (ID_UTENTE, ID_SPETTACOLO, ID_PREZZO, ID_POSTO, DATA_ORA_OPERAZIONE) VALUES (?, ?, ?, ?, ?)");
        try{
            stm.setInt(1, preno.getIdUtente());
            stm.setInt(2, preno.getIdSpettacolo());
            stm.setInt(3, preno.getIdPrezzo());
            stm.setInt(4, preno.getIdPosto());
            stm.setTimestamp(5, preno.getDataOraOperazione());
            
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
        cambiaPassword(ut.getEmail(), ut.getPassword());
    }
    /**
     * assegna la password all'utente con e-mail email.
     * @param email
     * @param password
     * @throws SQLException
     */
    public void cambiaPassword(String email, String password) throws SQLException{
        PreparedStatement stm = con.prepareStatement(
                "UPDATE UTENTE SET PASSWORD = ? WHERE EMAIL = ?");
        try {
            stm.setString(1, password);
            stm.setString(2, email);
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
        PreparedStatement stm = con.prepareStatement( "DELETE FROM PRENOTAZIONE P WHERE P.ID_PRENOTAZIONE=?");
        PreparedStatement stm2 = con.prepareStatement( "UPDATE UTENTE SET CREDITO=CREDITO+? WHERE UTENTE_ID=?");
        PreparedStatement stm3 = con.prepareStatement("SELECT P.PREZZO FROM PREZZO P WHERE P.ID_PREZZO =?");
        try {
            stm.setInt(1, pr.getIdPrenotazione());
            stm3.setInt(1, pr.getIdPrezzo());
            ResultSet prezzo = stm3.executeQuery();
            double rimborso = 0;
            if(prezzo.next())
                rimborso = prezzo.getDouble("PREZZO") * 0.8; //rimborso dell'80% del prezzo.
            stm2.setDouble(1, rimborso);
            stm2.setInt(2, ut.getIdUtente());
            stm2.executeUpdate();
        } finally {
            stm.close();
            stm2.close();
        }
    }
    
    /**
     *
     * @param ut utente a cui aggiornare il credito, il credito settato nell'oggetto deve essere in nuovo credito.
     * @throws SQLException
     */
    public void aggiornaCredito(Utente ut) throws SQLException{
        PreparedStatement stm = con.prepareStatement("UPDATE UTENTE SET CREDITO=? WHERE ID_UTENTE=?");
        try {
            stm.setDouble(1, ut.getCredito());
            stm.setInt(2, ut.getIdUtente());
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
    
    /**
     * inserisce un nuovo film nel database
     * @param fm film da inserire nel database
     * @throws SQLException
     */
    public void inserisciFilm(Film fm) throws SQLIntegrityConstraintViolationException, SQLException{
        PreparedStatement stm = con.prepareStatement("INSERT INTO FILM (TITOLO, ID_GENERE, URL_TRAILER, DURATA, TRAMA, URI_LOCANDINA, IS_IN_SLIDER, ANNO, REGISTA) VALUES (?,?,?,?,?,?,?,?,?)");
        try {
            stm.setString(1, fm.getTitolo());
            stm.setInt(2, fm.getIdGenere());
            stm.setString(3, fm.getUrlTrailer());
            stm.setInt(4, fm.getDurata());
            stm.setString(5, fm.getTrama());
            stm.setString(6, fm.getUriLocandina());
            stm.setBoolean(7, fm.isInSlider());
            stm.setInt(8, fm.getAnno());
            stm.setString(9, fm.getRegista());
            
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
    public void inserisciSpettacolo(Spettacolo sp) throws SQLIntegrityConstraintViolationException, SQLException{
        // possiamo farci passare tutta una classe spettacolo
        
        PreparedStatement stm = con.prepareStatement("INSERT INTO SPETTACOLO (ID_FILM, ID_SALA, DATA_ORA) VALUES (?,?,?) ");
        try {
            stm.setInt(1, sp.getIdFilm());
            stm.setInt(2, sp.getIdSala());
            stm.setTimestamp(3, sp.getTimeStamp());
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
        
        PreparedStatement stm = con.prepareStatement(" UPDATE POSTO SET STATO=? ID_POSTO=? ");
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
    public void aggiungiRuolo(Ruolo rl) throws SQLIntegrityConstraintViolationException, SQLException{
        
        PreparedStatement stm = con.prepareStatement("INSERT INTO RUOLO (RUOLO) VALUES (?)");
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
    public void aggiungiPosto(Posto ps) throws SQLIntegrityConstraintViolationException, SQLException{
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
    public void aggiungiUtente(Utente ut) throws SQLIntegrityConstraintViolationException, SQLException{
        PreparedStatement stm = con.prepareStatement("INSERT INTO UTENTE (ID_RUOLO, EMAIL, PASSWORD, CREDITO) VALUES (?,?,?,?)");
        try {
            stm.setInt(1, ut.getIdRuolo());
            stm.setString(2, ut.getEmail());
            stm.setString(3, ut.getPassword());
            stm.setDouble(4, ut.getCredito());
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
    
    /**
     * aggiunge una prenotazione nel database.
     * @param pre prenotazione da aggiungere
     * @throws java.sql.SQLIntegrityConstraintViolationException
     * @throws SQLException
     */
    public void aggiungiPrenotazione(Prenotazione pre) throws SQLIntegrityConstraintViolationException, SQLException{
        PreparedStatement stm = con.prepareStatement("INSERT INTO PRENOTAZIONE (ID_UTENTE, ID_SPETTACOLO, ID_PREZZO, ID_POSTO, DATA_ORA_OPERAZIONE) VALUES (?,?,?,?,?)");
        try {
            stm.setInt(1, pre.getIdUtente());
            stm.setInt(2, pre.getIdSpettacolo());
            stm.setInt(3, pre.getIdPrezzo());
            stm.setInt(4, pre.getIdPosto());
            stm.setTimestamp(5, pre.getDataOraOperazione());
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
    
    
     /**
     * aggiunge una prenotazione temporanea nel database.
     * @param pre prenotazionetmp da aggiungere
     * @return
     * @throws java.sql.SQLIntegrityConstraintViolationException
     * @throws SQLException
     */
    public int aggiungiPrenotazioneTmp(PrenotazioneTmp pre) throws SQLIntegrityConstraintViolationException, SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement("INSERT INTO PRENOTAZIONETMP (ID_SPETTACOLO, ID_UTENTE, ID_POSTO, DATA_ORA_OPERAZIONETMP, ID_PREZZO) VALUES (?,?,?,CURRENT_TIMESTAMP,?)");
        int result;
        
        try {
            stm.setInt(1, pre.getIdSpettacolo());
            stm.setString(2, pre.getIdUtente());
            stm.setInt(3, pre.getIdPosto());
            stm.setInt(4, pre.getIdPrezzo());
            
            result=stm.executeUpdate();
            
        } finally {
            stm.close();
        }        
        return result;
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
     * elimina le prenotazioni temporanee precedenti a tm.
     * @param tm limite minimo di validità delle prenotazionitmp (quelle piu vecchie vengono eliminate)
     * @throws SQLException
     */
    public void eliminaPrenotazioneTmp(Timestamp tm) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement("DELETE * FROM PRENOTAZIONETMP WHERE DATA_ORA_OPERAZIONETMP < ?");
        try {
            stm.setTimestamp(1, tm);
            stm.executeUpdate();
        } finally {
            stm.close();
        }        
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
                if(res[1] == 0)
                    res[1] = 0;
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
                
                aggiornaIdPrenotazioneTmp(idTmp,utente.getIdUtente());
                
            }
        }finally{
            stm.close();
        }
        res = new UtenteRuolo();
        res.setRuolo(ruolo);
        res.setUtente(utente);
        return res;
    }
    
    
    /**
     * ritorna una lista di tutti i tipi di prezzi
     * @return lista di tutti i prezzi possibili
     * @throws SQLException
     */
    public ArrayList<Prezzo> getAllPrezzi() throws SQLException{
        PreparedStatement stm = con.prepareStatement("SELECT ID_PREZZO, ID_PREZZO, PREZZO FROM PREZZO");
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
    //aggiungere film.
    
    public ArrayList<SpettacoloSalaOrario> getSpettacoli() throws SQLException{
        ArrayList<SpettacoloSalaOrario> res = new ArrayList<>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT SP.ID_SPETTACOLO, F.ID_FILM, F.ID_GENERE, F.TITOLO, F.DURATA, F.TRAMA, F.URL_TRAILER, F.URI_LOCANDINA, F.IS_IN_SLIDER, F.ANNO, F.REGISTA," +
                        " S.ID_SALA,S.NOME,S.DESCRIZIONE AS DESCRIZIONE2, SP.DATA_ORA, G.DESCRIZIONE\n" +
                        "FROM FILM F JOIN SPETTACOLO SP ON F.ID_FILM = SP.ID_FILM JOIN SALA S ON S.ID_SALA=SP.ID_SALA JOIN GENERE G ON F.ID_GENERE = G.ID_GENERE\n" +
                        "WHERE SP.DATA_ORA >= CURRENT_TIMESTAMP\n"+
                        "ORDER BY SP.DATA_ORA");
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
                tmpFilm.setisInSlider(rs.getBoolean("IS_IN_SLIDER"));
                tmpFilm.setTitolo(rs.getString("TITOLO"));
                tmpFilm.setTrama(rs.getString("TRAMA"));
                tmpFilm.setUriLocandina(rs.getString("URI_LOCANDINA"));
                tmpFilm.setUrlTrailer(rs.getString("URL_TRAILER"));
                tmpFilm.setRegista(rs.getString("REGISTA"));
                tmpFilm.setAnno(rs.getInt("ANNO"));
                
                Genere tmpGenere = new Genere();
                tmpGenere.setIdGenere(rs.getInt("ID_GENERE"));
                tmpGenere.setDescrizione(rs.getString("DESCRIZIONE"));
                
                tmpSala.setDescrizione(rs.getString("DESCRIZIONE2"));
                tmpSala.setIdSala(rs.getInt("ID_SALA"));
                tmpSala.setNome(rs.getString("NOME"));
                dataOra = rs.getTimestamp("DATA_ORA");
                tmp.setFilm(tmpFilm);
                tmp.setSala(tmpSala);
                tmp.setGenere(tmpGenere);
                tmp.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
                tmp.setDataOra(dataOra);
                
                String data = dataOra.toString().substring(0, 10);
                String[] data1 = data.split("-");
                tmp.setData(data1[2] + "-" + data1[1] + "-" + data1[0]);
                tmp.setOra(dataOra.toString().substring(11, 16));
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
    
    
    /**
     * permette di ottenere uno storico dell'utente: che film ha guardato e quando ha pagato.
     * @param ut utente di cui si vuole ottenere lo storico
     * @return un array di coppie di oggetti, di cui il primo è un instanza di Film e il secondo di Prezzo
     * @throws SQLException
     */
    public ArrayList<FilmPrezzo> getStorico(Utente ut) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement(
                "SELECT DISTINCT F.ID_FILM, F.ID_GENERE, F.TITOLO, F.DURATA, F.TRAMA,F.IS_IN_SLIDER, F.URL_TRAILER, F.URI_LOCANDINA, F.ANNO, F.REGISTA" +
                        " PR.ID_PREZZO, PR.TIPO, PR.PREZZO\n" +
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
                    tmpFilm.setAnno(rs.getInt("ANNO"));
                    tmpFilm.setisInSlider(rs.getBoolean("IS_IN_SLIDER"));
                    tmpFilm.setRegista(rs.getString("REGISTA"));
                    
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
        stm = con.prepareStatement(" SELECT DISTINCT F.ID_FILM, F.ID_GENERE, F.TITOLO, F.DURATA, F.TRAMA,F.IS_IN_SLIDER, F.URL_TRAILER, F.URI_LOCANDINA, F.ANNO, F.REGISTA, SUM (PR.PREZZO) AS TOT\n" +
"FROM FILM F JOIN SPETTACOLO S ON F.ID_FILM = S.ID_FILM JOIN PRENOTAZIONE P ON P.ID_SPETTACOLO = S.ID_SPETTACOLO JOIN PREZZO PR ON PR.ID_PREZZO = P.ID_PREZZO\n" +
"GROUP BY F.ID_FILM, F.ID_GENERE, F.TITOLO, F.DURATA, F.TRAMA,F.IS_IN_SLIDER, F.URL_TRAILER, F.URI_LOCANDINA, F.ANNO, F.REGISTA ");
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while(rs.next()){
                    IncassoFilm tmp = new IncassoFilm();
                    Film film = new Film();
                    
                    film.setIdFilm(rs.getInt("ID_FILM"));
                    film.setIdGenere(rs.getInt("ID_GENERE"));
                    film.setTitolo(rs.getString("TITOLO"));
                    film.setDurata(rs.getInt("DURATA"));
                    film.setTrama(rs.getString("TRAMA"));
                    film.setUrlTrailer(rs.getString("URL_TRAILER"));
                    film.setisInSlider(rs.getBoolean("IS_IN_SLIDER"));
                    film.setUriLocandina(rs.getString("URI_LOCANDINA"));
                    film.setAnno(rs.getInt("ANNO"));
                    film.setRegista(rs.getString("REGISTA"));
                    
                    
                    tmp.setFilm(film);
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
        PreparedStatement stm = con.prepareStatement(
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
        ArrayList<PrenTmpPosto> prenotazioniTmp = new ArrayList<>();
        stm = con.prepareStatement(
                "SELECT *\n" +
                        "FROM PRENOTAZIONETMP PREN JOIN POSTO P ON P.ID_POSTO = PREN.ID_POSTO" +
                        "WHERE ID_SPETTACOLO = ?");
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
                    tmp.setIdPrezzo(rs.getInt("ID_PREZZO"));
                    tmp.setTimestamp(rs.getTimestamp("DATA_ORA_OPERAZIONETMP"));
                    posto.setColonna(rs.getInt("COLONNA"));
                    posto.setRiga(rs.getString("RIGA").charAt(0));
                    posto.setIdPosto(rs.getInt("ID_POSTO"));
                    posto.setIdSala(rs.getInt("ID_SALA"));
                    posto.setStato(rs.getInt("STATO"));
                    PrenTmpPosto res = new PrenTmpPosto();
                    res.setPren(tmp);
                    res.setPosto(posto);
                    prenotazioniTmp.add(res);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return prenotazioniTmp;
    }
    
    
    public ArrayList<PrenotazioneTmp> getAndDeletePrenotazioniTmp(String idUtente) throws SQLException{
        PreparedStatement stm;
        ArrayList<PrenotazioneTmp> prenotazioneTmp = new ArrayList<>();
        stm = con.prepareStatement(
                "SELECT *\n" +
                        "FROM PRENOTAZIONETMP PREN " +
                        "WHERE ID_UTENTE = ?");
        try {
            stm.setString(1,idUtente);
            ResultSet rs = stm.executeQuery();
            try {
                
                while(rs.next()){
                    PrenotazioneTmp tmp = new PrenotazioneTmp();
                                        
                    tmp.setIdUtente(rs.getString("ID_UTENTE"));
                    tmp.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
                    tmp.setIdPosto(rs.getInt("ID_POSTO"));
                    tmp.setTimestamp(rs.getTimestamp("DATA_ORA_OPERAZIONETMP"));
                    tmp.setIdPrezzo(rs.getInt("ID_PREZZO"));
                    prenotazioneTmp.add(tmp);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        
        cancellaPrenotazioniTmp(idUtente);
                
        return prenotazioneTmp;
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
        stm = con.prepareStatement("SELECT * FROM PRENOTAZIONE PR JOIN POSTO PO ON PR.ID_POSTO = PO.ID_POSTO "+
                "WHERE PR.ID_SPETTACOLO=?");
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
                "SELECT DISTINCT F.ID_FILM, TITOLO, ID_GENERE,URL_TRAILER, DURATA, TRAMA,URI_LOCANDINA,IS_IN_SLIDER, ANNO, REGISTA "
                        + "FROM FILM F JOIN SPETTACOLO S ON S.ID_FILM=F.ID_FILM WHERE F.IS_IN_SLIDER=TRUE AND S.DATA_ORA>?");
                

        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        //System.out.println(currentTimestamp);
        //System.out.println("1");
        stm.setTimestamp(1, currentTimestamp);
        //System.out.println("2");
       //System.out.println(currentTimestamp);
      
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
                    tmp.setUriLocandina(rs.getString("URI_LOCANDINA"));
                    tmp.setTrama(rs.getString("TRAMA"));
                    tmp.setisInSlider(rs.getBoolean("IS_IN_SLIDER"));
                    tmp.setRegista(rs.getString("REGISTA"));
                    tmp.setAnno(rs.getInt("ANNO"));
                    
                    film.add(tmp);
                }
            }finally {
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
    public ArrayList<PrenotazionePosto> getPostiOccupati(int id_spettacolo) throws SQLException{
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
     * @param id_spettacolo ID dello spettacolo di cui si vuole vedere la lista dei posti occupati
     * @return ritorna una lista di postioccupati.
     * @throws SQLException
     */
    public ArrayList<PostiSala> getAllPosti(int id_sala) throws SQLException{
        ArrayList<PostiSala> res = new ArrayList<>();
       /* PreparedStatement stm = con.prepareStatement(
                "SELECT P.ID_POSTO, P.ID_SALA, P.RIGA, P.COLONNA, P.STATO\n" +
                        "FROM POSTO P\n" +
                        " WHERE P.ID_SALA = ?");
        try {
            stm.setInt(1, id_sala);
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
        }*/
        return res;
    }
    
    
    /**
     * TO DO  selezionare solo i primi 3 spettacoli.
     * @return lista di Film e relativi spettacoli.
     * @throws SQLException
     */
    public ArrayList<FilmSpettacoli> getFilmSpettacoli() throws SQLException{
        ArrayList<FilmSpettacoli> res = new ArrayList<>();
        PreparedStatement stm = con.prepareStatement("SELECT F.ID_FILM, F.ID_GENERE, F.TITOLO, F.DURATA, F.TRAMA, F.URL_TRAILER,F.IS_IN_SLIDER, F.URI_LOCANDINA, F.ANNO, F.REGISTA,\n" +
                "SP.ID_SPETTACOLO,SP.ID_FILM,SP.ID_SALA,SP.DATA_ORA, G.DESCRIZIONE\n" +
                "FROM FILM F JOIN SPETTACOLO SP ON F.ID_FILM = SP.ID_FILM JOIN GENERE G ON F.ID_GENERE = G.ID_GENERE\n" +
                "WHERE SP.DATA_ORA >= CURRENT_TIMESTAMP\n" +
                "ORDER BY F.ID_FILM, SP.DATA_ORA");
        ResultSet rs = stm.executeQuery();
        try {
            int oldId = 0;
            FilmSpettacoli tmpFilmESpettacoli = new FilmSpettacoli();
            ArrayList<Spettacolo> tmpSpettacoli = new ArrayList<>();
            if(rs.next()){
                Spettacolo tmpSpettacolo = new Spettacolo();
                tmpSpettacolo.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
                tmpSpettacolo.setIdSala(rs.getInt("ID_SALA"));
                tmpSpettacolo.setIdFilm(rs.getInt("ID_FILM"));
                tmpSpettacolo.setDataOra(rs.getTimestamp("DATA_ORA"));
                
                Genere tmpGenere = new Genere();
                tmpGenere.setIdGenere(rs.getInt("ID_GENERE"));
                tmpGenere.setDescrizione(rs.getString("DESCRIZIONE"));
                
                oldId = rs.getInt("ID_FILM");
                Film tmpFilm = new Film();
                tmpFilm.setIdFilm(rs.getInt("ID_FILM"));
                tmpFilm.setIdGenere(rs.getInt("ID_GENERE"));
                tmpFilm.setDurata(rs.getInt("DURATA"));
                tmpFilm.setTitolo(rs.getString("TITOLO"));
                tmpFilm.setTrama(rs.getString("TRAMA"));
                tmpFilm.setisInSlider(rs.getBoolean("IS_IN_SLIDER"));
                tmpFilm.setUriLocandina(rs.getString("URI_LOCANDINA"));
                tmpFilm.setUrlTrailer(rs.getString("URL_TRAILER"));
                tmpFilm.setAnno(rs.getInt("ANNO"));
                tmpFilm.setRegista(rs.getString("REGISTA"));
                
                tmpFilmESpettacoli.setFilm(tmpFilm);
                tmpFilmESpettacoli.setSpettacoli(tmpSpettacoli);
                tmpFilmESpettacoli.setGenere(tmpGenere);
                res.add(tmpFilmESpettacoli);
                tmpSpettacoli.add(tmpSpettacolo);
            }
            
            while(rs.next()){
                
                int idFilm = rs.getInt("ID_FILM");
                if(idFilm != oldId){
                    tmpFilmESpettacoli = new FilmSpettacoli();
                    tmpSpettacoli = new ArrayList<>();
                    Film tmpFilm = new Film();
                    tmpFilm.setIdFilm(rs.getInt("ID_FILM"));
                    tmpFilm.setIdGenere(rs.getInt("ID_GENERE"));
                    tmpFilm.setDurata(rs.getInt("DURATA"));
                    tmpFilm.setTitolo(rs.getString("TITOLO"));
                    tmpFilm.setTrama(rs.getString("TRAMA"));
                    tmpFilm.setisInSlider(rs.getBoolean("IS_IN_SLIDER"));
                    tmpFilm.setUriLocandina(rs.getString("URI_LOCANDINA"));
                    tmpFilm.setUrlTrailer(rs.getString("URL_TRAILER"));
                    tmpFilm.setAnno(rs.getInt("ANNO"));
                    tmpFilm.setRegista(rs.getString("REGISTA"));
                    
                    
                    Genere tmpGenere = new Genere();
                    tmpGenere.setIdGenere(rs.getInt("ID_GENERE"));
                    tmpGenere.setDescrizione(rs.getString("DESCRIZIONE"));
                    
                    tmpFilmESpettacoli.setFilm(tmpFilm);
                    tmpFilmESpettacoli.setGenere(tmpGenere);
                    tmpFilmESpettacoli.setSpettacoli(tmpSpettacoli);
                    res.add(tmpFilmESpettacoli);
                    
                    oldId = idFilm;
                }
                Spettacolo tmpSpettacolo = new Spettacolo();
                tmpSpettacolo.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
                tmpSpettacolo.setIdSala(rs.getInt("ID_SALA"));
                tmpSpettacolo.setIdFilm(rs.getInt("ID_FILM"));
                tmpSpettacolo.setDataOra(rs.getTimestamp("DATA_ORA"));
                
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
    public FilmSpettacoli getFilmSpettacoli(int filmId) throws SQLException{
        FilmSpettacoli res = new FilmSpettacoli();
        PreparedStatement stm = con.prepareStatement(
                "SELECT F.ID_FILM, F.ID_GENERE, F.TITOLO, F.DURATA, F.TRAMA, F.URL_TRAILER, F.IS_IN_SLIDER, F.URI_LOCANDINA, F.ANNO, F.REGISTA, SP.ID_SPETTACOLO, SP.ID_FILM, SP.ID_SALA, SP.DATA_ORA, G.DESCRIZIONE\n" +
                        "FROM FILM F JOIN SPETTACOLO SP ON F.ID_FILM = SP.ID_FILM JOIN GENERE G ON F.ID_GENERE = G.ID_GENERE\n" +
                        "WHERE SP.DATA_ORA >= CURRENT_TIMESTAMP AND F.ID_FILM = ?\n" +
                        "ORDER BY SP.DATA_ORA");
        stm.setInt(1, filmId);
        ResultSet rs = stm.executeQuery();
        ArrayList<Spettacolo> tmpSpettacoli = null;
        Film tmpFilm = null;
        Genere tmpGenere = null;
        if(rs.next()){
            tmpSpettacoli = new ArrayList<>();
            tmpFilm = new Film();
            tmpFilm.setIdFilm(rs.getInt("ID_FILM"));
            tmpFilm.setIdGenere(rs.getInt("ID_GENERE"));
            tmpFilm.setDurata(rs.getInt("DURATA"));
            tmpFilm.setTitolo(rs.getString("TITOLO"));
            tmpFilm.setTrama(rs.getString("TRAMA"));
            tmpFilm.setisInSlider(rs.getBoolean("IS_IN_SLIDER"));
            tmpFilm.setUriLocandina(rs.getString("URI_LOCANDINA"));
            tmpFilm.setUrlTrailer(rs.getString("URL_TRAILER"));
            tmpFilm.setAnno(rs.getInt("ANNO"));
            tmpFilm.setRegista(rs.getString("REGISTA"));
            
            tmpGenere = new Genere();
            tmpGenere.setIdGenere(rs.getInt("ID_GENERE"));
            tmpGenere.setDescrizione(rs.getString("DESCRIZIONE"));
            
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
        res.setGenere(tmpGenere);
        res.setSpettacoli(tmpSpettacoli);
        return res;
    }
    
    /**
     *
     * @param idSpettacolo
     * @return
     * @throws SQLException
     */
    public InfoPrenotazione getInfoPrenotazione(int idSpettacolo) throws SQLException{
        InfoPrenotazione res = new InfoPrenotazione();
        PreparedStatement stm = stm = con.prepareStatement(
                "SELECT SP.ID_SPETTACOLO, SP.ID_FILM, SP.ID_SALA, SP.DATA_ORA, F.ID_GENERE, F.TITOLO, F.DURATA, F.TRAMA, F.IS_IN_SLIDER, F.URL_TRAILER, F.URI_LOCANDINA, F.REGISTA, F.ANNO, SA.NOME, SA.DESCRIZIONE\n" +
                        "FROM SPETTACOLO SP JOIN FILM F ON SP.ID_FILM = F.ID_FILM JOIN SALA SA ON SP.ID_SALA = SA.ID_SALA\n" +
                        "WHERE SP.ID_SPETTACOLO = ?");
        stm.setInt(1, idSpettacolo);
        ResultSet rs = stm.executeQuery();
        try{
            if(rs.next()){
                Film tmpFilm = new Film();
                tmpFilm.setIdFilm(rs.getInt("ID_FILM"));
                tmpFilm.setIdGenere(rs.getInt("ID_GENERE"));
                tmpFilm.setDurata(rs.getInt("DURATA"));
                tmpFilm.setTitolo(rs.getString("TITOLO"));
                tmpFilm.setTrama(rs.getString("TRAMA"));
                tmpFilm.setisInSlider(rs.getBoolean("IS_IN_SLIDER"));
                tmpFilm.setUriLocandina(rs.getString("URI_LOCANDINA"));
                tmpFilm.setUrlTrailer(rs.getString("URL_TRAILER"));
                tmpFilm.setAnno(rs.getInt("ANNO"));
                tmpFilm.setRegista(rs.getString("REGISTA"));
                res.setFilm(tmpFilm);
                
                Spettacolo tmpSpettacolo = new Spettacolo();
                tmpSpettacolo.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
                tmpSpettacolo.setIdSala(rs.getInt("ID_SALA"));
                tmpSpettacolo.setIdFilm(rs.getInt("ID_FILM"));
                tmpSpettacolo.setDataOra(rs.getTimestamp("DATA_ORA"));
                res.setSpettacolo(tmpSpettacolo);
                Sala tmpSala = new Sala();
                tmpSala.setDescrizione(rs.getString("DESCRIZIONE"));
                tmpSala.setIdSala(rs.getInt("ID_SALA"));
                tmpSala.setNome(rs.getString("NOME"));
                res.setSala(tmpSala);
            }
            
        }finally{
            stm.close();
        }
        return res;
    }
    
    /**
     * setta la programmazione dei film in modo da metterli in serie
     * @param min numero di minuti tra uno spettacolo e l'altro
     * @throws SQLException
     */
    public void setProgrammazione(int min) throws SQLException{
        
        int n=5; //numero di film per spettacolo
        
        Calendar calendar = Calendar.getInstance();
        
        PreparedStatement stmFilm;
        PreparedStatement stmSala;
        PreparedStatement stmUpdate;
        stmFilm = con.prepareStatement("SELECT ID_FILM FROM FILM");
        stmSala = con.prepareStatement("SELECT ID_SALA FROM SALA");
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
        } finally {
            stmFilm.close();
            stmSala.close();
            stmUpdate.close();
            rsFilm.close();
            rsSala.close();
        }
    }
    
    public void cancellaPrenotazioniTmp(String id) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement("DELETE FROM PRENOTAZIONETMP WHERE ID_UTENTE = ?");
        try {
            stm.setString(1, id);
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
    

    public EmailTimestamp getInfoRecovery(String md5) throws SQLException{
        EmailTimestamp res = null;
        PreparedStatement stm = stm = con.prepareStatement(
                "SELECT * FROM PASSWORDRECOVERY WHERE MD5 = ?");
        stm.setString(1, md5);
        ResultSet rs = stm.executeQuery();
        if(rs.next()){
            res = new EmailTimestamp();
            res.setEmail(rs.getString("EMAIL"));
            res.setTimestamp(rs.getTimestamp("TIME"));
        }
        return res;
    }
    
    public void insertRecuperaPassword(String md5, String email, Timestamp time) throws SQLIntegrityConstraintViolationException, SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement(
                "INSERT INTO PASSWORDRECOVERY (MD5, EMAIL, TIME) VALUES (?, ?, ?)");
        try{
            stm.setString(1, md5);
            stm.setString(2, email);
            stm.setTimestamp(3, time);
            
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
    

    
    public static String encodeIdUtente(Object obj){
        
        if(obj instanceof Integer){
            return String.valueOf((char)1) + String.valueOf((int)obj);
        }
        if(obj instanceof String)
            return "t" + (String)obj;
        
        return null;
        
    }
    
    public static Object decodeIdUtente(String s){
        
        if(s.length()>0 && s.charAt(0) == (char)1){
            s=s.substring(1);
            return Integer.parseInt(s);
        }
        
        return s.substring(1);
        
    }
    
    public boolean emailValida(String email) throws SQLException{
        boolean res = false;
        PreparedStatement stm = stm = con.prepareStatement(
                "SELECT * FROM UTENTE WHERE EMAIL = ?");
        stm.setString(1, email);
        ResultSet rs = stm.executeQuery();
        if(rs.next()){
            res = true;
        }
        return res;
    } 
    
    public void aggiornaIdPrenotazioneTmp(String idTmp,int id) throws SQLException{
        
        PreparedStatement stm = con.prepareStatement("UPDATE PRENOTAZIONETMP PT SET PT.ID_UTENTE=? WHERE PT.ID_UTENTE=?");
        try {
            stm.setString(1, encodeIdUtente(id));
            stm.setString(2, idTmp);
            stm.executeUpdate();
        } finally {
            stm.close();
        }
        
    } 
    
    /**
     * 
     * @param idUtente
     * @throws SQLException 
     */
    public void confermaPrenotazioni(String idUtente) throws SQLException{
        
        // controllo se utente loggato
        Object obj = decodeIdUtente(idUtente);
        if (!(obj instanceof Integer))
            throw new IllegalArgumentException("Conferma prenotazioni: invalid Id");
                
        int id = (int)obj;
        
        // get prenotazioniTmp di idUtente e le cancello
        ArrayList<PrenotazioneTmp> prenTmp=getAndDeletePrenotazioniTmp(idUtente);
         
        // le prendo e le importo in Prenotazioni
        
        for(PrenotazioneTmp tmp: prenTmp){
            Prenotazione pren = new Prenotazione();
            pren.setIdUtente(id);
            pren.setDataOraOperazione(tmp.getTimestamp());
            pren.setIdPosto(tmp.getIdPosto());
            pren.setIdSpettacolo(tmp.getIdSpettacolo());
            pren.setIdPrezzo(tmp.getIdPrezzo());
                   
            this.aggiungiPrenotazione(pren);
        }
    }
    
}


