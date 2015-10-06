/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import Beans.Film;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author enrico
 */
public class FilmQueries{
    private final transient Connection con;
    
    public FilmQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public FilmQueries(Connection con){
        this.con = con;
    }
    
    /**
     * inserisce un nuovo film nel database
     * @param fm film da inserire nel database
     * @throws java.sql.SQLIntegrityConstraintViolationException
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
        stm.setTimestamp(1, currentTimestamp);
        
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
    
}
