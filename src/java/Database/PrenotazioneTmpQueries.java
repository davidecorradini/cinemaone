
package Database;

import Beans.InfoPrenotazione;
import Beans.Posto;
import Beans.PrenTmpPosto;
import Beans.Prenotazione;
import Beans.PrenotazioneTmp;
import Beans.Sala;
import Beans.Spettacolo;
import Beans.Utente;
import MailMedia.MailSender;
import MailMedia.TicketCreator;

import Servlets.ConfermaPrenotazione;
import com.lowagie.text.DocumentException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;


public class PrenotazioneTmpQueries {
    private final transient Connection con;
    
    public PrenotazioneTmpQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public PrenotazioneTmpQueries(Connection con){
        this.con = con;
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
        stm = con.prepareStatement("INSERT INTO PRENOTAZIONETMP (ID_SPETTACOLO, ID_UTENTE, ID_POSTO, DATA_ORA_OPERAZIONETMP, ID_PREZZO) VALUES (?,?,?,?,?)");
        int result;
        
        try {
            stm.setInt(1, pre.getIdSpettacolo());
            stm.setString(2, pre.getIdUtente());
            stm.setInt(3, pre.getIdPosto());
            stm.setTimestamp(4, pre.getTimestamp());
            stm.setInt(5, pre.getIdPrezzo());
            result=stm.executeUpdate();
            
        } finally {
            stm.close();
        }
        return result;
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
                        "FROM PRENOTAZIONETMP PREN JOIN POSTO P ON P.ID_POSTO = PREN.ID_POSTO " +
                        "WHERE PREN.ID_SPETTACOLO = ?");
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
    
    /**
     * ritorna e cancella le prenotazioniTmp di un utente.
     * @param idUtente l'utente di cui si vogliono confermare le prenotazioni temporanee
     * @param idSpettacolo
     * @return
     * @throws SQLException
     */
    public ArrayList<PrenotazioneTmp> getAndDeletePrenotazioniTmp(String idUtente,int idSpettacolo) throws SQLException{
        PreparedStatement stm;
        ArrayList<PrenotazioneTmp> prenotazioneTmp = new ArrayList<>();
        stm = con.prepareStatement(
                "SELECT *\n" +
                        "FROM PRENOTAZIONETMP PREN " +
                        "WHERE ID_UTENTE = ? AND ID_SPETTACOLO = ?");
        try {
            stm.setString(1,idUtente);
            stm.setInt(2, idSpettacolo);
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
        
        cancellaPrenotazioniTmp(idUtente,idSpettacolo);
        
        return prenotazioneTmp;
    }
    /**
     * cancella le prenotazioniTmp di un dato utente.
     * @param id
     * @param idSpettacolo
     * @throws SQLException
     */
    public void cancellaPrenotazioniTmp(String id,int idSpettacolo) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement("DELETE FROM PRENOTAZIONETMP WHERE ID_UTENTE = ? AND ID_SPETTACOLO = ?");
        try {
            stm.setString(1, id);
            stm.setInt(2, idSpettacolo);
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
    
    /**
     * cancella la prenotazioneTmp per quel posto in quello spettacolo.
     * @param idSpettacolo
     * @param idPosto
     * @throws SQLException
     */
    public void deletePrenotazioneTmp(int idSpettacolo, int idPosto) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement("DELETE FROM PRENOTAZIONETMP P WHERE P.ID_SPETTACOLO = ? AND P.ID_POSTO = ?");
        try{
            stm.setInt(1, idSpettacolo);
            stm.setInt(2, idPosto);
            stm.executeUpdate();
        }finally{
            stm.close();
        }
    }
    
    /**
     * cancella le prenotazioniTmp di un dato utente.
     * @param time
     * @throws SQLException
     */
    public void cancellaPrenotazioniTmp(Timestamp time) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement("DELETE FROM PRENOTAZIONETMP PTMP WHERE PTMP.DATA_ORA_OPERAZIONETMP < ?");
        try {
            stm.setTimestamp(1, time);
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
    
    /**
     * aggiorna l'idUtente delle prenotazioni tmp, dall'id temporaneo all'id "loggato"
     * @param idTmp
     * @param id
     * @throws SQLException
     */
    public void aggiornaIdPrenotazioneTmp(String idTmp,int id) throws SQLException{
        
        PreparedStatement stm = con.prepareStatement("UPDATE PRENOTAZIONETMP PT SET PT.ID_UTENTE=? WHERE PT.ID_UTENTE=?");
        try {
            stm.setString(1, Utente.encodeIdUtente(id));
            stm.setString(2, idTmp);
            stm.executeUpdate();
        } finally {
            stm.close();
        }
        
    }
    
    /**
     * trasferisce le prenotazioni di un utente da temporanee a definitive.
     * @param idUtente
     * @param idSpettacolo
     * @param manager
     * @throws SQLException
     */
    public void confermaPrenotazioni(String idUtente,int idSpettacolo,DBManager manager) throws SQLException, IllegalArgumentException{
        
        // controllo se utente loggato
        Object obj = Utente.decodeIdUtente(idUtente);
        if (!(obj instanceof Integer))
            throw new IllegalArgumentException("Conferma prenotazioni: invalid Id");
        
        int id = (int)obj;
        
        double totDaPagare=0;
        // get prenotazioniTmp di idUtente e le cancello
        ArrayList<PrenotazioneTmp> prenTmp=getAndDeletePrenotazioniTmp(idUtente,idSpettacolo);
        for(PrenotazioneTmp tmp: prenTmp){
            
            PrezzoQueries pq=new PrezzoQueries(manager);
            try {
                totDaPagare+=pq.getPrezzo(tmp.getIdPrezzo());
            } catch (SQLException ex) {
                System.err.println("ex: "+ex);
            }
            
        }
        // le prendo e le importo in Prenotazioni
        
        
        UtenteQueries uQ=new UtenteQueries(manager);
        Utente utente=uQ.getUtente(id);
        
        
        double credito=utente.getCredito();
        
        
        if(credito>=totDaPagare){
            utente.setCredito(credito-totDaPagare);
            uQ.aggiornaCredito(utente);
        }
        else{
            //controllo pagamento carta di credito
            utente.setCredito(0);
            uQ.aggiornaCredito(utente);
        }
        
        ArrayList<String> allegato=new ArrayList<>();
        
        for(PrenotazioneTmp tmp: prenTmp){
            
            Prenotazione pren = new Prenotazione();
            pren.setIdUtente(id);
            pren.setDataOraOperazione(tmp.getTimestamp());
            pren.setIdPosto(tmp.getIdPosto());
            pren.setIdSpettacolo(tmp.getIdSpettacolo());
            pren.setIdPrezzo(tmp.getIdPrezzo());
            PrenotazioneQueries prenQ = new PrenotazioneQueries(manager);
            prenQ.aggiungiPrenotazione(pren);
            
            InfoPrenotazioneQueries iPQ=new InfoPrenotazioneQueries(manager);
            InfoPrenotazione filmSalaSpettacolo=iPQ.getInfoPrenotazione(pren.getIdSpettacolo());
            
            
            PostoQueries pQ=new PostoQueries(manager);
            Posto posto=pQ.getPosto(pren.getIdPosto()); 
            
            
            TicketCreator ticketCreator=new TicketCreator("biglietto");
            
            String ticket="";
            System.out.println(pren.getIdPrenotazione());
            try {
                ticket=ticketCreator.generaTicket(utente, pren, filmSalaSpettacolo.getSpettacolo(), filmSalaSpettacolo.getFilm(), filmSalaSpettacolo.getSala(), posto);
            } catch (DocumentException | IOException ex) {
                System.err.println("errore: "+ ex.getLocalizedMessage());
            }
            allegato.add(ticket);
        }
        
        
        String to=utente.getEmail();
        String subject="Ticket CinemaOne s.r.l.";
        String text="In allegato i biglietti acquistati.\nGrazie per aver scelto CinemaOne s.r.l.";
        
        MailSender mailSender= new MailSender ();
        try {
            mailSender.sendMail(to,subject,text,allegato);
        } catch (MessagingException ex) {
            Logger.getLogger(ConfermaPrenotazione.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setTimerPrenotazioneTMP(String idUtente, Timestamp time) throws SQLException, IllegalArgumentException{
        
        // controllo se utente loggato
        Object obj = Utente.decodeIdUtente(idUtente);
        if (!(obj instanceof Integer))
            throw new IllegalArgumentException("Conferma prenotazioni: invalid Id");
        
        int id = (int)obj;
        
        PreparedStatement stm;
        
        if(time != null){
            stm = con.prepareStatement("UPDATE PRENOTAZIONETMP PT SET PT.DATA_ORA_OPERAZIONETMP= ? WHERE PT.ID_UTENTE=?");
            try {
                stm.setTimestamp(1, time);
                stm.setString(2, idUtente);
                stm.executeUpdate();
            } finally {
                stm.close();
            }
        }else{
            stm = con.prepareStatement("UPDATE PRENOTAZIONETMP PT SET PT.DATA_ORA_OPERAZIONETMP= {fn TIMESTAMPADD(SQL_TSI_MINUTE, -4, CURRENT_TIMESTAMP)} WHERE PT.ID_UTENTE=?");
            try {
                stm.setString(1, idUtente);
                stm.executeUpdate();
            } finally {
                stm.close();
            }
        }
        
    }
}
