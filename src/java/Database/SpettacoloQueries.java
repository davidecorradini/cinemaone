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
    
    public Number[] getInfoSpettacoliPassati() throws SQLException{
        
        Number [] result= new Number[3];
        
        PreparedStatement stm = con.prepareStatement(
                "SELECT COUNT(*) AS TOT, SUM (P.PREZZO) AS INCASSO\n" +
                        "FROM PYTHONI.SPETTACOLO AS S JOIN PYTHONI.PRENOTAZIONE AS PREN ON S.ID_SPETTACOLO=PREN.ID_SPETTACOLO JOIN PYTHONI.PREZZO AS P ON PREN.ID_PREZZO=P.ID_PREZZO \n" +
                        "WHERE S.DATA_ORA<CURRENT_TIMESTAMP");
        
        try{
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    result[0]= rs.getInt("TOT");
                    result[1]= rs.getDouble("INCASSO");
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        
        
        stm = con.prepareStatement(
                "SELECT COUNT(*) AS TOT\n" +
                        "FROM PYTHONI.SPETTACOLO AS S\n" +
                        "WHERE S.DATA_ORA<CURRENT_TIMESTAMP");
        
        try{
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    result[2]= rs.getInt("TOT");
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        
        
        return result;
    }
    
    public Number[] getInfoSpettacoliFuturi() throws SQLException{
        
        Number [] result= new Number[3];
        
        PreparedStatement stm = con.prepareStatement(
                "SELECT COUNT(*) AS TOT, SUM (P.PREZZO) AS INCASSO\n" +
                        "FROM PYTHONI.SPETTACOLO AS S JOIN PYTHONI.PRENOTAZIONE AS PREN ON S.ID_SPETTACOLO=PREN.ID_SPETTACOLO JOIN PYTHONI.PREZZO AS P ON PREN.ID_PREZZO=P.ID_PREZZO \n" +
                        "WHERE S.DATA_ORA>CURRENT_TIMESTAMP");
        
        try{
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    result[0]= rs.getInt("TOT");
                    result[1]= rs.getDouble("INCASSO");
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        
        
        stm = con.prepareStatement(
                "SELECT COUNT(*) AS TOT\n" +
                        "FROM PYTHONI.SPETTACOLO AS S\n" +
                        "WHERE S.DATA_ORA>CURRENT_TIMESTAMP");
        
        try{
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    result[2]= rs.getInt("TOT");
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        
        
        return result;
        
    }
    
    public IncassoFilm getInfoTopFilm() throws SQLException{
        
        IncassoFilm result= new IncassoFilm();
        
        PreparedStatement stm = con.prepareStatement(
                "SELECT F.ID_FILM,F.ID_GENERE,F.TITOLO,F.DURATA,F.TRAMA,F.URL_TRAILER,F.URI_LOCANDINA,F.IS_IN_SLIDER,F.ANNO,F.REGISTA, SUM(P.PREZZO) AS TOT\n" +
"FROM PYTHONI.SPETTACOLO AS S JOIN PYTHONI.PRENOTAZIONE AS PREN ON S.ID_SPETTACOLO=PREN.ID_SPETTACOLO JOIN PYTHONI.PREZZO AS P ON PREN.ID_PREZZO=P.ID_PREZZO JOIN PYTHONI.FILM AS F ON F.ID_FILM=S.ID_FILM  \n" +
"GROUP BY F.ID_FILM,F.ID_GENERE,F.TITOLO,F.DURATA,F.TRAMA,F.URL_TRAILER,F.URI_LOCANDINA,F.IS_IN_SLIDER,F.ANNO,F.REGISTA\n" +
"HAVING F.ID_FILM = (SELECT TMP.ID_FILM\n" +
"FROM (\n" +
"  SELECT F.ID_FILM, SUM(P.PREZZO) AS TOT\n" +
"FROM PYTHONI.SPETTACOLO AS S JOIN PYTHONI.PRENOTAZIONE AS PREN ON S.ID_SPETTACOLO=PREN.ID_SPETTACOLO JOIN PYTHONI.PREZZO AS P ON PREN.ID_PREZZO=P.ID_PREZZO JOIN PYTHONI.FILM AS F ON F.ID_FILM=S.ID_FILM  \n" +
"GROUP BY F.ID_FILM\n" +
") AS TMP\n" +
"WHERE TMP.TOT= (SELECT MAX(TMP2.TOT) \n" +
"                FROM (\n" +
"                      SELECT F.ID_FILM, SUM(P.PREZZO) AS TOT\n" +
"                      FROM PYTHONI.SPETTACOLO AS S JOIN PYTHONI.PRENOTAZIONE AS PREN ON S.ID_SPETTACOLO=PREN.ID_SPETTACOLO JOIN PYTHONI.PREZZO AS P ON PREN.ID_PREZZO=P.ID_PREZZO JOIN PYTHONI.FILM AS F ON F.ID_FILM=S.ID_FILM  \n" +
"                      GROUP BY F.ID_FILM\n" +
"                      ) AS TMP2\n" +
"                )\n" +
"\n" +
")");
        Film film = null;
        try{
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    film = new Film();
                    film.setIdFilm(rs.getInt("ID_FILM"));
                    film.setTitolo(rs.getString("TITOLO"));
                    film.setIdGenere(rs.getInt("ID_GENERE"));
                    film.setUrlTrailer(rs.getString("URL_TRAILER"));
                    film.setDurata(rs.getInt("DURATA"));
                    film.setUriLocandina(rs.getString("URI_LOCANDINA"));
                    film.setTrama(rs.getString("TRAMA"));
                    film.setisInSlider(rs.getBoolean("IS_IN_SLIDER"));
                    film.setRegista(rs.getString("REGISTA"));
                    film.setAnno(rs.getInt("ANNO"));
                    
                    result.setFilm(film);
                    result.setIncasso(rs.getDouble("TOT"));
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        
        
        stm = con.prepareStatement(
                "SELECT COUNT(S.ID_FILM) AS TOT\n" +
"FROM PYTHONI.SPETTACOLO S\n" +
"GROUP BY S.ID_FILM\n" +
"HAVING S.ID_FILM=?");
        
        try{
            stm.setInt(1, film.getIdFilm());
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    result.setNumSpett(rs.getInt("TOT"));
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        
        
        return result;
        
    }
    
    public InfoUtenti getInfoUtenti() throws SQLException{
        
        InfoUtenti result= new InfoUtenti();
        
         PreparedStatement stm = con.prepareStatement(
                "SELECT COUNT(*) AS TOT\n" +
                        "FROM UTENTE");
        
        try{
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    result.setUtentiRegistrati(rs.getInt("TOT"));
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        
        
        stm = con.prepareStatement(
                "SELECT TMP.ID_UTENTE,TMP.EMAIL,TMP.TOT\n" +
"FROM (\n" +
"SELECT U.ID_UTENTE, U.EMAIL, COUNT (*) AS TOT\n" +
"FROM UTENTE U JOIN PRENOTAZIONE P ON U.ID_UTENTE=P.ID_UTENTE\n" +
"GROUP BY U.ID_UTENTE, U.EMAIL\n" +
") AS TMP\n" +
"WHERE TMP.ID_UTENTE= ( SELECT TMP2.ID_UTENTE\n" +
"                       FROM (SELECT U.ID_UTENTE, COUNT (*) AS TOT\n" +
"                             FROM UTENTE U JOIN PRENOTAZIONE P ON U.ID_UTENTE=P.ID_UTENTE\n" +
"                             GROUP BY U.ID_UTENTE \n" +
"                             ) AS TMP2\n" +
"                       WHERE TMP2.TOT= ( SELECT MAX(TMP3.TOT)\n" +
"                       FROM (SELECT U.ID_UTENTE, COUNT (*) AS TOT\n" +
"                             FROM UTENTE U JOIN PRENOTAZIONE P ON U.ID_UTENTE=P.ID_UTENTE\n" +
"                             GROUP BY U.ID_UTENTE \n" +
"                             ) AS TMP3\n" +
"\n" +
")\n" +
")");
        
        try{
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    result.setEmailPostiPiuPrenotati(rs.getString("EMAIL"));
                    result.setNumeroPosti(rs.getInt("TOT"));
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        
        
        stm = con.prepareStatement(
                "SELECT TMP.ID_UTENTE,TMP.EMAIL,TMP.TOT\n" +
"FROM (\n" +
"SELECT U.ID_UTENTE, U.EMAIL, SUM (PRE.PREZZO) AS TOT\n" +
"FROM UTENTE U JOIN PRENOTAZIONE P ON U.ID_UTENTE=P.ID_UTENTE JOIN PREZZO PRE ON P.ID_PREZZO=PRE.ID_PREZZO\n" +
"GROUP BY U.ID_UTENTE, U.EMAIL\n" +
") AS TMP\n" +
"WHERE TMP.ID_UTENTE= ( SELECT TMP2.ID_UTENTE\n" +
"                       FROM (SELECT U.ID_UTENTE, SUM (PRE.PREZZO) AS TOT\n" +
"                             FROM UTENTE U JOIN PRENOTAZIONE P ON U.ID_UTENTE=P.ID_UTENTE JOIN PREZZO PRE ON P.ID_PREZZO=PRE.ID_PREZZO\n" +
"                             GROUP BY U.ID_UTENTE \n" +
"                             ) AS TMP2\n" +
"                       WHERE TMP2.TOT= ( SELECT MAX(TMP3.TOT)\n" +
"                       FROM (SELECT U.ID_UTENTE, SUM (PRE.PREZZO) AS TOT\n" +
"                             FROM UTENTE U JOIN PRENOTAZIONE P ON U.ID_UTENTE=P.ID_UTENTE JOIN PREZZO PRE ON P.ID_PREZZO=PRE.ID_PREZZO\n" +
"                             GROUP BY U.ID_UTENTE \n" +
"                             ) AS TMP3\n" +
"\n" +
")\n" +
")");
        
        try{
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    result.setEmailTotalePiuAlto(rs.getString("EMAIL"));
                    result.setTotale(rs.getInt("TOT"));
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        
        return result;
        
    }
}
