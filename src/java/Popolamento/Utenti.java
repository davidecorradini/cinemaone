/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Popolamento;

import Beans.Utente;
import Database.UtenteQueries;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Utenti {
    private final transient Connection con;
    
    public Utenti(String dburl) throws SQLException{
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver", true, getClass().getClassLoader());
        } catch(Exception e) {
            throw new RuntimeException(e.toString(), e);
        }
        Connection con = DriverManager.getConnection(dburl);
        this.con = con;
    }
    
    private void insertUsers() throws SQLException{
        UtenteQueries queries = new UtenteQueries(con);
        
        Utente admin = new Utente();
        admin.setEmail("admin@cinemaone.it");
        admin.setPassword("admin");
        admin.setIdRuolo(1);
        queries.aggiungiUtente(admin);
        
        Utente utente = new Utente();
        utente.setEmail("enrico.magnago@studenti.unitn.it");
        utente.setPassword("enrico");
        utente.setCredito(20.50);
        utente.setIdRuolo(2);
        queries.aggiungiUtente(utente);
        
        utente = new Utente();
        utente.setEmail("roberto.fellin@studenti.unitn.it");
        utente.setPassword("roberto");
        utente.setCredito(50.00);
        utente.setIdRuolo(2);
        queries.aggiungiUtente(utente);
        
        utente = new Utente();
        utente.setEmail("alessandro.valenti-1@studenti.unitn.it");
        utente.setPassword("alessandro");
        utente.setCredito(30.00);
        utente.setIdRuolo(2);
        queries.aggiungiUtente(utente);
        
        utente = new Utente();
        utente.setEmail("davide.corradini-1@studenti.unitn.it");
        utente.setPassword("davide");
        utente.setCredito(40.00);
        utente.setIdRuolo(2);
        queries.aggiungiUtente(utente);
        
        utente = new Utente();
        utente.setEmail("stefano.flor@studenti.unitn.it");
        utente.setPassword("stefano");
        utente.setCredito(150.00);
        utente.setIdRuolo(2);
        queries.aggiungiUtente(utente);
        
        utente = new Utente();
        utente.setEmail("norman.gabalin@studenti.unitn.it");
        utente.setPassword("norman");
        utente.setCredito(0.00);
        utente.setIdRuolo(2);
        queries.aggiungiUtente(utente);
        
        utente = new Utente();
        utente.setEmail("en.magnago@gmail.com");
        utente.setPassword("enrico");
        utente.setCredito(45.00);
        utente.setIdRuolo(2);
        queries.aggiungiUtente(utente);
        
        utente = new Utente();
        utente.setEmail("fellin.roberto@hotmail.it");
        utente.setPassword("fellin");
        utente.setCredito(2.00);
        utente.setIdRuolo(2);
        queries.aggiungiUtente(utente);
        
        utente = new Utente();
        utente.setEmail("valentini.alessandro94@gmail.com");
        utente.setPassword("alessandro");
        utente.setCredito(32.00);
        utente.setIdRuolo(2);
        queries.aggiungiUtente(utente);
        
        
    }
    
    
    //inserisce 10 utenti.
    public static void main(String [] args) throws SQLException, IOException
    {
        Utenti inserisciUtenti = new Utenti("jdbc:derby://localhost:1527/MultisalaDB;user=pythoni;password=pythoni");
        inserisciUtenti.insertUsers();
    }
}
