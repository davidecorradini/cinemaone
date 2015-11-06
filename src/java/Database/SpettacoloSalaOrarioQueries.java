/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import Beans.AdminPrenotazioneUtenti;
import Beans.Film;
import Beans.Genere;
import Beans.Posto;
import Beans.Prenotazione;
import Beans.Prezzo;
import Beans.Ruolo;
import Beans.Sala;
import Beans.Spettacolo;
import Beans.SpettacoloSalaOrario;
import Beans.Utente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author enrico
 */
public class SpettacoloSalaOrarioQueries {
    private final transient Connection con;
    
    public SpettacoloSalaOrarioQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public SpettacoloSalaOrarioQueries(Connection con){
        this.con = con;
    }
    
    /**
     * per ogni spettacolo, ancora da eseguire, specifica quando e in che sala avranno luogo.
     * @return uno spettacolo che sarà fatto in una determinata sala ad uno specifico orario.
     * @throws SQLException
     */
    public ArrayList<SpettacoloSalaOrario> getSpettacoliFuturi() throws SQLException{
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
                
                Spettacolo spett = new Spettacolo();
                spett.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
                spett.setDataOra(rs.getTimestamp("DATA_ORA"));
                
                Sala tmpSala = new Sala();
                tmpSala.setDescrizione(rs.getString("DESCRIZIONE2"));
                tmpSala.setIdSala(rs.getInt("ID_SALA"));
                tmpSala.setNome(rs.getString("NOME"));
                
                tmp.setFilm(tmpFilm);
                tmp.setSala(tmpSala);
                tmp.setGenere(tmpGenere);
                tmp.setSpettacolo(spett);
                
                res.add(tmp);
            }
        } finally {
            rs.close();
        }
        return res;
    }
    
    
     public ArrayList<AdminPrenotazioneUtenti> getPrenotazioni(String titolo, String genere, String regista, String nomeSala, Integer durataMin, Integer durataMax, Timestamp programmazioneDa, Timestamp programmazioneA, String email, String ruolo, Timestamp prenotazioneDa, Timestamp prenotazioneA, String tipoPrezzo, Character riga, Integer colonna) throws SQLException{
        ArrayList<AdminPrenotazioneUtenti> res = new ArrayList<>();
        PreparedStatement stm = createQuery("prenotazioni", genere, titolo, durataMin, durataMax, regista, programmazioneDa, programmazioneA, prenotazioneDa, prenotazioneA, tipoPrezzo, nomeSala, riga, colonna, email, ruolo);
        ResultSet rs = stm.executeQuery();
        try {
            while(rs.next()){
                AdminPrenotazioneUtenti tmp = new AdminPrenotazioneUtenti();
                Film tmpFilm = new Film();
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
                
                Spettacolo spett = new Spettacolo();
                spett.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
                spett.setDataOra(rs.getTimestamp("DATA_ORA"));
                
                Sala tmpSala = new Sala();
                tmpSala.setDescrizione(rs.getString("DESCRIZIONE"));
                tmpSala.setIdSala(rs.getInt("ID_SALA"));
                tmpSala.setNome(rs.getString("NOME"));
                
                Utente tmpUtente = new Utente();
                tmpUtente.setIdUtente(rs.getInt("ID_UTENTE"));
                tmpUtente.setIdRuolo(rs.getInt("ID_RUOLO"));
                tmpUtente.setEmail(rs.getString("EMAIL"));
                tmpUtente.setCredito(rs.getDouble("CREDITO"));
                
                Prenotazione tmpPrenotazione = new Prenotazione();
                tmpPrenotazione.setIdPrenotazione(rs.getInt("ID_PRENOTAZIONE"));
                tmpPrenotazione.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
                tmpPrenotazione.setIdUtente(rs.getInt("ID_UTENTE"));
                tmpPrenotazione.setIdPrezzo(rs.getInt("ID_PREZZO"));
                tmpPrenotazione.setIdPosto(rs.getInt("ID_POSTO"));
                tmpPrenotazione.setDataOraOperazione(rs.getTimestamp("DATA_ORA_OPERAZIONE"));
                
                Prezzo tmpPrezzo = new Prezzo();
                tmpPrezzo.setIdPrezzo(rs.getInt("ID_PREZZO"));
                tmpPrezzo.setPrezzo(rs.getDouble("PREZZO"));
                tmpPrezzo.setTipo(rs.getString("TIPO"));
                
                Ruolo tmpRuolo = new Ruolo();
                tmpRuolo.setIdRuolo(rs.getInt("ID_RUOLO"));
                tmpRuolo.setRuolo(rs.getString("RUOLO"));
                
                Posto tmpPosto = new Posto();
                tmpPosto.setIdPosto(rs.getInt("ID_POSTO"));
                tmpPosto.setIdSala(rs.getInt("ID_SALA"));
                tmpPosto.setRiga(rs.getString("RIGA").charAt(0));
                tmpPosto.setColonna(rs.getInt("COLONNA"));
                tmpPosto.setStato(rs.getInt("STATO"));
                
                tmp.setFilm(tmpFilm);
                tmp.setSala(tmpSala);
                tmp.setGenere(tmpGenere);
                tmp.setSpettacolo(spett);
                tmp.setUtente(tmpUtente);
                tmp.setPrenotazione(tmpPrenotazione);
                tmp.setPrezzo(tmpPrezzo);
                tmp.setRuolo(tmpRuolo);
                tmp.setPosto(tmpPosto);
                
                res.add(tmp);
            }
        } finally {
            rs.close();
        }
        return res;
     }
    
    /**
     *
     * @param genereDescrizione
     * @param filmTitolo
     * @param filmDurataLow
     * @param filmDurataHigh
     * @param filmRegista
     * @param spettacoloDataOraLow
     * @param spettacoloDataOraHigh
     * @return
     * @throws SQLException
     */
    public ArrayList<SpettacoloSalaOrario> getSpettacoli(String genereDescrizione, String filmTitolo, Integer filmDurataLow, Integer filmDurataHigh, String filmRegista, Timestamp spettacoloDataOraLow, Timestamp spettacoloDataOraHigh, String salaNome) throws SQLException{
        ArrayList<SpettacoloSalaOrario> res = new ArrayList<>();
        PreparedStatement stm = createQuery("spettacoli", genereDescrizione, filmTitolo, filmDurataLow, filmDurataHigh, filmRegista, spettacoloDataOraLow, spettacoloDataOraHigh,null,null,null,salaNome,null,null,null,null);
        ResultSet rs = stm.executeQuery();
        try {
            while(rs.next()){
                SpettacoloSalaOrario tmp = new SpettacoloSalaOrario();
                Film tmpFilm = new Film();
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
                
                Spettacolo spett = new Spettacolo();
                spett.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
                spett.setDataOra(rs.getTimestamp("DATA_ORA"));
                
                Sala tmpSala = new Sala();
                tmpSala.setDescrizione(rs.getString("DESCRIZIONE"));
                tmpSala.setIdSala(rs.getInt("ID_SALA"));
                tmpSala.setNome(rs.getString("NOME"));
                
                tmp.setFilm(tmpFilm);
                tmp.setSala(tmpSala);
                tmp.setGenere(tmpGenere);
                tmp.setSpettacolo(spett);
                
                res.add(tmp);
            }
        } finally {
            rs.close();
        }
        return res;
    }
    
    
    private PreparedStatement createQuery(String tipo, String genereDescrizione, String filmTitolo, Integer filmDurataLow, Integer filmDurataHigh, String filmRegista, Timestamp spettacoloDataOraLow, Timestamp spettacoloDataOraHigh, Timestamp prenotazioneDataOraLow, Timestamp prenotazioneDataOraHigh, String prezzoTipo, String salaNome, Character postoRiga, Integer postoColonna, String utenteEmail, String ruoloRuolo) throws SQLException{
        String query =  "SELECT *\n" +
                "FROM FILM F JOIN SPETTACOLO SP ON F.ID_FILM = SP.ID_FILM JOIN GENERE G ON F.ID_GENERE = G.ID_GENERE JOIN SALA S ON S.ID_SALA=SP.ID_SALA";
        if(tipo.equals("prenotazioni"))
            query += " JOIN PRENOTAZIONE P ON P.ID_SPETTACOLO = SP.ID_SPETTACOLO JOIN PREZZO PREZ ON PREZ.ID_PREZZO = P.ID_PREZZO JOIN POSTO POST ON POST.ID_POSTO = P.ID_POSTO JOIN UTENTE UT ON UT.ID_UTENTE = P.ID_UTENTE JOIN RUOLO R ON R.ID_RUOLO = UT.ID_RUOLO";
        String queryWhere = null;
        if(genereDescrizione != null || filmTitolo != null || filmDurataLow != null || filmDurataHigh != null || filmRegista != null || spettacoloDataOraLow != null || spettacoloDataOraHigh != null || prenotazioneDataOraLow != null || prenotazioneDataOraHigh != null || prezzoTipo != null || salaNome != null || postoRiga != null || postoColonna != null || utenteEmail != null || ruoloRuolo != null){
            queryWhere = "WHERE ";
            
            if(genereDescrizione != null)
                queryWhere += "UPPER(G.DESCRIZIONE) LIKE UPPER(?) AND ";
            if(filmTitolo != null)
                queryWhere += "UPPER(F.TITOLO) LIKE UPPER(?) AND ";
            if(filmDurataLow != null)
                queryWhere += "F.DURATA >= ? AND ";
            if(filmDurataHigh != null)
                queryWhere += "F.DURATA <= ? AND ";
            if(filmRegista != null)
                queryWhere += "UPPER(F.REGISTA) LIKE UPPER(?) AND ";
            if(spettacoloDataOraLow != null)
                queryWhere += "SP.DATA_ORA >= ? AND ";
            if(spettacoloDataOraHigh != null)
                queryWhere += "SP.DATA_ORA <= ? AND ";
            if(prenotazioneDataOraLow != null || prenotazioneDataOraHigh != null || prezzoTipo != null || postoRiga != null || postoColonna != null || utenteEmail != null || ruoloRuolo != null){
                if(prenotazioneDataOraLow != null)
                    queryWhere += "P.DATA_ORA_OPERAZIONE >= ? AND ";
                if(prenotazioneDataOraHigh != null)
                    queryWhere += "P.DATA_ORA_OPERAZIONE <= ? AND ";
            }
            if(prezzoTipo != null){
                queryWhere += "UPPER(PREZ.TIPO) = UPPER(?) AND ";
            }
            if(salaNome != null)
                queryWhere += "UPPER(S.NOME) = UPPER(?) AND ";
            if(postoRiga != null || postoColonna != null){
                if(postoRiga != null)
                    queryWhere += "UPPER(POST.RIGA) = UPPER(?) AND ";
                if(postoColonna != null)
                    queryWhere += "POST.COLONNA = ? AND ";
            }
            
            if(utenteEmail != null || ruoloRuolo != null){
                if(utenteEmail != null)
                    queryWhere += "UT.EMAIL = ? AND ";
                if(ruoloRuolo != null){
                    queryWhere += "UPPER(RUO.RUOLO) = UPPER(?) AND ";
                }
            }
        }
        
        query += "\n";
        if(queryWhere != null)
            query += queryWhere.substring(0, queryWhere.length()-5) + "\n";
        
        
        PreparedStatement stm = con.prepareStatement(query);
        
        int index = 1;
        if(genereDescrizione != null){
            stm.setString(index, "%" + genereDescrizione + "%");
            index++;
        }
        if(filmTitolo != null){
            stm.setString(index, "%" + filmTitolo + "%");
            index++;
        }
        if(filmDurataLow != null){
            stm.setInt(index, filmDurataLow);
            index++;
        }
        if(filmDurataHigh != null){
            stm.setInt(index, filmDurataHigh);
            index++;
        }
        if(filmRegista != null){
            stm.setString(index, "%" + filmRegista + "%");
            index++;
        }
        if(spettacoloDataOraLow != null){
            stm.setTimestamp(index, spettacoloDataOraLow);
            index++;
        }
        if(spettacoloDataOraHigh != null){
            stm.setTimestamp(index, spettacoloDataOraHigh);
            index++;
        }
        if(prenotazioneDataOraLow != null){
            stm.setTimestamp(index, prenotazioneDataOraLow);
            index++;
        }
        if(prenotazioneDataOraHigh != null){
            stm.setTimestamp(index, prenotazioneDataOraHigh);
            index++;
        }
        if(prezzoTipo != null){
            stm.setString(index, prezzoTipo);
            index++;
        }
        if(salaNome != null){
            stm.setString(index, salaNome);
            index++;
        }
        if(postoRiga != null){
            stm.setString(index, String.valueOf(postoRiga));
            index++;
        }
        if(postoColonna != null){
            stm.setInt(index, postoColonna);
            index++;
        }
        if(utenteEmail != null){
            stm.setString(index, utenteEmail);
            index++;
        }
        if(ruoloRuolo != null){
            stm.setString(index, ruoloRuolo);
            index++;
        }
        return stm;
    }

   
}
