/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import Beans.Film;
import Beans.IncassoFilm;
import Beans.InfoUtenti;
import Beans.Spettacolo;
import static java.lang.Math.random;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author enrico
 */
public class SpettacoloQueries {
    private final transient Connection con;
    
    public SpettacoloQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public SpettacoloQueries(Connection con){
        this.con = con;
    }
    
     /**
     * inserisce un nuovo spettacolo nel database.
     * @param sp spettacolo da inserire nel database
     * @throws java.sql.SQLIntegrityConstraintViolationException
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
     *
     * @param sp spettacolo di cui vogliamo visionare il numero di posti prenotati e l'incasso
     * @return array di Integer il cui primo elemento è il numero di posti prenotati e il secondo è l'incasso
     * @throws SQLException
     */
    public Number[] getPostiIncasso(int idSpettacolo) throws SQLException{
        Number res[]=new Number[2];
        PreparedStatement stm;
        stm = con.prepareStatement(
                "SELECT COUNT(*) AS TOT_POSTI, SUM(PR.PREZZO) AS TOT_PREZZO\n" +
                        "FROM PREZZO AS PR JOIN PRENOTAZIONE AS P ON PR.ID_PREZZO=P.ID_PREZZO\n" +
                        "WHERE P.ID_SPETTACOLO=?");
        try{
            stm.setInt(1, idSpettacolo);
            
            ResultSet rs = stm.executeQuery();
            try {
                if(rs.next()){
                    res[0]=rs.getInt("TOT_POSTI");
                    res[1]=rs.getDouble("TOT_PREZZO");
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
     * setta la programmazione dei film in modo da metterli in serie
     * @param min numero di minuti tra uno spettacolo e l'altro
     * @param n numero di spettacoli
     * @throws SQLException
     */
    public void setProgrammazione(int min, int n) throws SQLException{
        
        //int n=5; //numero di film per spettacolo
        
        Calendar calendar = Calendar.getInstance();
        
        PreparedStatement stmFilm;
        PreparedStatement stmSala;
        PreparedStatement stmUpdate;
        stmFilm = con.prepareStatement("SELECT ID_FILM FROM FILM ORDER BY RANDOM()");
        stmSala = con.prepareStatement("SELECT ID_SALA FROM SALA ORDER BY RANDOM()");
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
                if(!film.isEmpty()){
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
            }
        } finally {
            stmFilm.close();
            stmSala.close();
            stmUpdate.close();
            rsFilm.close();
            rsSala.close();
        }
    }

    public boolean checkInizio(int idSpettacolo) throws SQLException{
    
        boolean res=true;
        
        PreparedStatement stm = con.prepareStatement(
                "SELECT S.DATA_ORA\n" +
                        "FROM SPETTACOLO S\n" +
                        "WHERE S.ID_SPETTACOLO=?");
        Timestamp data_ora=null;
        try{
            stm.setInt(1, idSpettacolo);
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    data_ora= rs.getTimestamp("DATA_ORA");
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        
        java.util.Date date= new java.util.Date();
	Timestamp current=new Timestamp(date.getTime());
        
        if(data_ora.after(current)){
            res=true;
        }
        else{
            res=false;
        }
        
        return res;
        
    }

    public Number[] getInfoSpettacoliPassati() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Number[] getInfoSpettacoliFuturi() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public IncassoFilm getInfoTopFilm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public InfoUtenti getInfoUtenti() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
