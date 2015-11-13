/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.Cache;

import Beans.Posto;
import Beans.Prenotazione;
import Beans.PrenotazionePosto;
import Database.DBManager;
import Database.PostoQueries;
import Database.PrenotazionePostoQueries;
import Database.PrenotazioneQueries;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * classe che wrappa metodi di accesso al database aggiungendo un sistema di cache.
 * Wrappa anche i metodi di modifica (eliminazione/update) dei dati del dabase, in questo modo è possibile mantenere la cache sincronizzata.
 * necessita di controllare le modifiche alla tabella Posto e Prenotazioni.
 * 
 */
public class PrenotazioniPostoCache {
     private final transient Connection manager;
    //coppie idSpettacolo, oggetto.
    private static final ObjectCache<Integer, ArrayList<PrenotazionePosto>> cache = new ObjectCache<>();

    public PrenotazioniPostoCache(Connection manager){
        this.manager = manager;
    }
    
    public PrenotazioniPostoCache(DBManager manager){
        this.manager = manager.getConnection();
    }
    
    //wrapper del metodo PrenotazionePostoQueries.getPostiOccupati, i risultati sono salvati in cache.
    /**
     * interroga la cache per ottenere il risultato, se non presente in cache si occupa di eseguire la query.
     * @param id_spettacolo
     * @return
     * @throws SQLException 
     */
    public ArrayList<PrenotazionePosto> getPostiOccupati(int id_spettacolo) throws SQLException{
        ArrayList<PrenotazionePosto> res = cache.get(id_spettacolo);
        System.out.print("getPostiOccupati; idSpettacolo: " + id_spettacolo + " -> cache ");
        if(res == null){ //if the object is not cached, compute it.
            System.out.println("miss");
            PrenotazionePostoQueries prenPostoQ = new PrenotazionePostoQueries(manager);
            res = prenPostoQ.getPostiOccupati(id_spettacolo);
            cache.add(id_spettacolo, res); //store it in cache
        }else
            System.out.println("hit");
        return res;
    }
    
//prenotazioneQueries
    /**
     * cancella una Prenotazione dalla tabella prenotazioni mantenendo la cache sincronizzata.
     * @param pr
     * @throws SQLException 
     */
     public void deletePrenotazione (Prenotazione pr) throws SQLException{
         //prendo l'oggetto dalla cache e lo modifico eliminando le entry legate a tale prenotazione.
         ArrayList<PrenotazionePosto> value = cache.get(pr.getIdSpettacolo());
         ArrayList<PrenotazionePosto> updatedValue = null;
         if(value != null){
             updatedValue = new ArrayList<>();
             for(PrenotazionePosto obj : value){
                 //inserisco solo gli elementi che hanno idPrenotazione differente.
                 if(obj.getPrenotazione().getIdPrenotazione() != pr.getIdPrenotazione())
                     updatedValue.add(obj);
             }
         }
         (new PrenotazioneQueries(manager)).deletePrenotazione(pr);
         //una volta eseguita la query aggiorno la cache.
         if(value != null)
             cache.add(pr.getIdSpettacolo(), updatedValue);
     }
     
     /**
      * aggiunge una prenotazione alla tabella Prenotazioni mantenendo la cache sincronizzata.
      * @param pre
      * @throws SQLException 
      */
     public void aggiungiPrenotazione(Prenotazione pre) throws SQLException{
         PrenotazioneQueries prenQuery = new PrenotazioneQueries(manager);
         prenQuery.aggiungiPrenotazione(pre);
         
         //aggiungo la prenotazione per il posto alla cache una volta che la query è stata eseguita.
         Posto posto = (new PostoQueries(manager)).getPosto(pre.getIdPosto()); //ottengo il posto.
         ArrayList<PrenotazionePosto> value = cache.get(pre.getIdSpettacolo());
         if(value != null){
             PrenotazionePosto newPreno = new PrenotazionePosto();
             newPreno.setPosto(posto);
             newPreno.setPrenotazione(pre);
             value.add(newPreno);
             //cache.add(pre.getIdSpettacolo(), value); //logica dei puntatori, non necessario.
         }
     }
     
     //PostoQueries
     /**
      * aggiorna la cache in modo che non dia falsi dati. Chiama la catena di cache per propagare la modifica.
      * @param ps
      * @throws SQLException 
      */
     public void cambiaStato(Posto ps) throws SQLException{
         //considero potenzialmente troppo costoso andare a cercare tutte le entry che riguardano quel posto.
         //cancellando tutto i dati nella cache verranno reinseriti uno alla volta (per aggiurnarla dovrei ciclare su tutti i dati presenti, potenzialmente su tutte le prenotazioni per ogni posto dell'intero database)
         cache.clean();
         (new PrenotazioniTmpPostoCache(manager)).cambiaStato(ps);
        
     }
}
