/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database.Cache;

import Beans.Posto;
import Beans.PrenTmpPosto;
import Beans.PrenotazioneTmp;
import Database.DBManager;
import Database.PostoQueries;
import Database.PrenTmpPostoQueries;
import Database.PrenotazioneTmpQueries;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class PrenotazioniTmpPostoCache {
    private final transient Connection manager;
    //coppie idSpettacolo, oggetto.
    private static final ObjectCache<Integer, ArrayList<PrenTmpPosto>> cache = new ObjectCache<>();
    
    public PrenotazioniTmpPostoCache(Connection manager){
        this.manager = manager;
    }
    
    public PrenotazioniTmpPostoCache(DBManager manager){
        this.manager = manager.getConnection();
    }
    
    //wrapper del metodo PrenTmpPostoQueries.getPrenotazioneTmp, i risultati sono salvati in cache.
    /**
     * interroga la cache per ottenere il risultato, se non presente in cache si occupa di eseguire la query.
     * @param id_spettacolo
     * @return
     * @throws SQLException
     */
    public ArrayList<PrenTmpPosto> getPrenotazioneTmp(int id_spettacolo) throws SQLException{
        ArrayList<PrenTmpPosto> res = cache.get(id_spettacolo);
        if(res == null){ //if cache miss
            res = (new PrenTmpPostoQueries(manager)).getPrenotazioneTmp(id_spettacolo);
            cache.add(id_spettacolo, res);
        }
        return res;
    }
    
//PrenTmpQueries
    /**
     * aggiunge una prenotazioneTmp mantenendo la cache sincronizzata.
     * @param pre
     * @return
     * @throws SQLIntegrityConstraintViolationException
     * @throws SQLException
     */
    public int aggiungiPrenotazioneTmp(PrenotazioneTmp pre) throws SQLIntegrityConstraintViolationException, SQLException{
        int res = (new PrenotazioneTmpQueries(manager)).aggiungiPrenotazioneTmp(pre);
        
        //aggiungo la prenotazione per il posto nella cache una volta che la query è stata completata.
        Posto posto = (new PostoQueries(manager)).getPosto(pre.getIdPosto()); //ottengo il posto.
        ArrayList<PrenTmpPosto> value = cache.get(pre.getIdSpettacolo());
        if(value == null)
            value = new ArrayList<>();
        PrenTmpPosto newPreno = new PrenTmpPosto();
        newPreno.setPosto(posto);
        newPreno.setPren(pre);
        value.add(newPreno);
        //cache.add(pre.getIdSpettacolo(), value); //logica dei puntatori, non necessario.
        return res;
    }
    
    /**
     * elimina le prenotazioni temporanee precenti a tm mantenendo la cache sincronizzata.
     * @param tm
     * @throws SQLException
     */
    public void cancellaPrenotazioniTmp(Timestamp tm) throws SQLException{
        //ciclare su tutte le prenotazioniTmp potrebbe essere molto costoso.
        cache.clean();
        (new PrenotazioneTmpQueries(manager)).cancellaPrenotazioniTmp(tm);
    }
    
    /**
     * cancella le prenotazioniTmp per un dato spettacolo dell'utente specificato mantenendo la cache sincronizzata.
     * @param idUtente
     * @param idSpettacolo
     * @throws SQLException
     */
    public void cancellaPrenotazioniTmp(String idUtente, int idSpettacolo) throws SQLException{
        ArrayList<PrenTmpPosto> value = cache.get(idSpettacolo);
        ArrayList<PrenTmpPosto> updatedValue = null;
        if(value != null){
        updatedValue = new ArrayList<>();
        for(PrenTmpPosto obj : value){
            if(!obj.getPren().getIdUtente().equals(idUtente))
                updatedValue.add(obj);
        }
        //rimuovo l'elemento dalla cache, così anche se la query fallisce non avrò falsi dati.
        cache.remove(idSpettacolo);
        }
        (new PrenotazioneTmpQueries(manager)).cancellaPrenotazioniTmp(idUtente, idSpettacolo);
        if(value != null)
            cache.add(idSpettacolo, updatedValue); //update the cache.
    }
    
    /**
     * cancella le prenotazioniTmp di un dato utente mantenendo la cache sincronizzata.
     * @param id
     * @throws SQLException
     */
    public void cancellaPrenotazioniTmp(String id) throws SQLException{
        //ciclare su tutta la cache per cercare le prenotazioni fatte dall'utente potrebbe essere troppo costoso.
        cache.clean();
        (new PrenotazioneTmpQueries(manager)).cancellaPrenotazioniTmp(id);
    }
    
    
    /**
     * cancella la prenotazioneTmp per quel posto in quello spettacolo mantenendo la cache sincronizzata.
     * @param idSpettacolo
     * @param idPosto
     * @throws SQLException
     */
    public void deletePrenotazioneTmp(int idSpettacolo, int idPosto) throws SQLException{
        ArrayList<PrenTmpPosto> value = cache.get(idSpettacolo);
        ArrayList<PrenTmpPosto> updatedValue = null;
        if(value != null){
            updatedValue = new ArrayList<>();
            for(PrenTmpPosto obj : value){
                if(obj.getPren().getIdPosto() != idPosto)
                    updatedValue.add(obj);
            }
            //rimuovo l'elemento dalla cache, così anche se la query fallisce non avrò falsi dati.
            cache.remove(idSpettacolo);
        }
        (new PrenotazioneTmpQueries(manager)).deletePrenotazioneTmp(idSpettacolo, idPosto);
        if(value != null)
            cache.add(idSpettacolo, updatedValue);        
    }
    
    /**
     * aggiorna l'idUtente delle prenotazioni tmp, dall'id temporaneo all'id "loggato" mantenendo la cache sincronizzata.
     * @param idTmp
     * @param id
     * @throws SQLException
     */
    public void aggiornaIdPrenotazioneTmp(String idTmp, int id) throws SQLException{
        //ciclare su tutta la cache per cercare le prenotazioni fatte dall'utente potrebbe essere troppo costoso.
        cache.clean();
        (new PrenotazioneTmpQueries(manager)).aggiornaIdPrenotazioneTmp(idTmp, id);
    }
    
    /**
     * aggiorna il timeout delle prenotazioni temporanee dell'utente specificato.
     * mantenendo la cache sincronizzata
     * @param idUtente
     * @param time se null viene settato al timestamp attuale.
     * @throws SQLException
     * @throws IllegalArgumentException 
     */
    public void setTimerPrenotazioneTMP(String idUtente, Timestamp time) throws SQLException, IllegalArgumentException{
        //ciclare su tutta la cache per cercare le prenotazioni fatte dall'utente potrebbe essere troppo costoso.
        cache.clean();
        (new PrenotazioneTmpQueries(manager)).setTimerPrenotazioneTMP(idUtente, time);
    }
    
    
//PostoQueries
    /**
     *
     * @param ps
     * @throws SQLException
     */
    public void cambiaStato(Posto ps) throws SQLException{
        //considero potenzialmente troppo costoso andare a cercare tutte le entry che riguardano quel posto.
        //cancellando tutto i dati nella cache verranno reinseriti uno alla volta (per aggiurnarla dovrei ciclare su tutti i dati presenti, potenzialmente su tutte le prenotazioni per ogni posto dell'intero database)
        cache.clean();
        (new PostoQueries(manager)).cambiaStato(ps);
    }
    
}
