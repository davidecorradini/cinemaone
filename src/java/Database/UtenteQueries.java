/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import Beans.Film;
import Beans.Utente;
import Beans.PrenotazioniUtente;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class UtenteQueries {
    private final transient Connection con;
    
    public UtenteQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public UtenteQueries(Connection con){
        this.con = con;
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
    
    public static String criptaPassword(String password){
        String res;
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            //
        }
        sha.update(password.getBytes());
        byte[] sha256 = sha.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : sha256) {
            sb.append(String.format("%02x", b & 0xff));
        }
        res = sb.toString();
        return res;
    }
    
    public boolean emailValida(String email) throws SQLException{
        boolean res = false;
        PreparedStatement stm = con.prepareStatement(
                "SELECT * FROM UTENTE WHERE EMAIL = ?");
        try{
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                res = true;
            }
        }finally{
            stm.close();
        }
        return res;
    }
    
    /**
     * assegna la password all'utente con e-mail email.
     * @param email
     * @param password
     * @throws SQLException
     */
    public void cambiaPassword(String email, String password) throws SQLException{
        password = criptaPassword(password);
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
    
    public double getCredito(int idUtente) throws SQLException{
        PreparedStatement stm = con.prepareStatement("SELECT CREDITO FROM UTENTE WHERE ID_UTENTE=?");
        double res = 0.0;
        try {
            stm.setInt(1, idUtente);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                res=rs.getDouble("CREDITO");
            }
        } finally {
            stm.close();
        }
        return res;
    }
    
    
    /**
     * inserisce un nuovo utente nel database.
     * @param ut utente da aggiungere
     * @throws SQLException
     */
    public void aggiungiUtente(Utente ut) throws SQLIntegrityConstraintViolationException, SQLException{
        String password = criptaPassword(ut.getPassword());
        PreparedStatement stm = con.prepareStatement("INSERT INTO UTENTE (ID_RUOLO, EMAIL, PASSWORD, CREDITO) VALUES (?,?,?,?)");
        try {
            stm.setInt(1, ut.getIdRuolo());
            stm.setString(2, ut.getEmail());
            stm.setString(3, password);
            stm.setDouble(4, ut.getCredito());
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
    
    public ArrayList<PrenotazioniUtente> getInfoPrenotazioniUtente(int idUtente) throws SQLIntegrityConstraintViolationException, SQLException{
        ArrayList<PrenotazioniUtente> infoPrenotazioniUtente = new ArrayList<>();
        PreparedStatement stm = con.prepareStatement(" SELECT F.ID_FILM, TITOLO, ID_GENERE,URL_TRAILER, DURATA, TRAMA,URI_LOCANDINA,IS_IN_SLIDER, ANNO, REGISTA,DATA_ORA_OPERAZIONE,DATA_ORA,NOME, COUNT(*) AS NUM_BIGLIETTI, SUM(PREZ.PREZZO) AS TOT\n" +
                "FROM PRENOTAZIONE PRE JOIN SPETTACOLO S ON S.ID_SPETTACOLO=PRE.ID_SPETTACOLO JOIN FILM F ON S.ID_FILM=F.ID_FILM JOIN SALA SA ON SA.ID_SALA=S.ID_SALA JOIN PREZZO PREZ ON PREZ.ID_PREZZO=PRE.ID_PREZZO\n" +
                "WHERE PRE.ID_UTENTE=?\n" +
                "GROUP BY  PRE.ID_SPETTACOLO,F.ID_FILM, TITOLO, ID_GENERE,URL_TRAILER, DURATA, TRAMA,URI_LOCANDINA,IS_IN_SLIDER, ANNO, REGISTA,DATA_ORA_OPERAZIONE,DATA_ORA,NOME ");
        
        
        stm.setInt(1, idUtente);
        
        try {
            ResultSet rs = stm.executeQuery();
            try {
                
                while(rs.next()){
                    PrenotazioniUtente tmp = new PrenotazioniUtente();
                    
                    Film tmpFilm=new Film();
                    tmpFilm.setIdFilm(rs.getInt("ID_FILM"));
                    tmpFilm.setTitolo(rs.getString("TITOLO"));
                    tmpFilm.setIdGenere(rs.getInt("ID_GENERE"));
                    tmpFilm.setUrlTrailer(rs.getString("URL_TRAILER"));
                    tmpFilm.setDurata(rs.getInt("DURATA"));
                    tmpFilm.setUriLocandina(rs.getString("URI_LOCANDINA"));
                    tmpFilm.setTrama(rs.getString("TRAMA"));
                    tmpFilm.setisInSlider(rs.getBoolean("IS_IN_SLIDER"));
                    tmpFilm.setRegista(rs.getString("REGISTA"));
                    tmpFilm.setAnno(rs.getInt("ANNO"));
                    
                    tmp.setFilm(tmpFilm);
                    tmp.setDataOraOperazione(rs.getTimestamp("DATA_ORA_OPERAZIONE"));
                    tmp.setDataOraSpettacolo(rs.getTimestamp("DATA_ORA"));
                    tmp.setNomeSala(rs.getString("NOME"));
                    tmp.setNumBiglietti(rs.getInt("NUM_BIGLIETTI"));
                    tmp.setTot(rs.getInt("TOT"));
                    
                    infoPrenotazioniUtente.add(tmp);
                }
            }finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return infoPrenotazioniUtente;
        
        
    }
    
    Utente getUtente(int idUtente) throws SQLIntegrityConstraintViolationException, SQLException{
        Utente utente=new Utente();
        
        PreparedStatement stm = con.prepareStatement("SELECT * FROM UTENTE WHERE ID_UTENTE=?");
        
        try {
            stm.setInt(1, idUtente);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                utente.setIdUtente(idUtente);
                utente.setEmail(rs.getString("EMAIL"));
                utente.setPassword(rs.getString("PASSWORD"));
                
                utente.setCredito(rs.getDouble("CREDITO"));
                utente.setIdRuolo(rs.getInt("ID_RUOLO"));
            }
        } finally {
            stm.close();
        }
        
        return utente;
        
    }
}
