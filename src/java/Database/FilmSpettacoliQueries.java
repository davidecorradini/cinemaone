/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import Beans.Film;
import Beans.FilmSpettacoli;
import Beans.Genere;
import Beans.Spettacolo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author enrico
 */
public class FilmSpettacoliQueries{
    private final transient Connection con;
    
    public FilmSpettacoliQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public FilmSpettacoliQueries(Connection con){
        this.con = con;
    }
    
    /**
     * TO DO  selezionare solo i primi 3 spettacoli.
     * @return lista di Film e relativi spettacoli.
     * @throws SQLException
     */
    public ArrayList<FilmSpettacoli> getFilmSpettacoli() throws SQLException{
        ArrayList<FilmSpettacoli> res = null;
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
                res = new ArrayList<>();
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
        FilmSpettacoli res = null;
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
            res = new FilmSpettacoli();
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
            res.setFilm(tmpFilm);
            res.setGenere(tmpGenere);
            res.setSpettacoli(tmpSpettacoli);
        }
        while(rs.next()){
            Spettacolo tmpSpettacolo = new Spettacolo();
            tmpSpettacolo.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
            tmpSpettacolo.setIdSala(rs.getInt("ID_SALA"));
            tmpSpettacolo.setIdFilm(rs.getInt("ID_FILM"));
            tmpSpettacolo.setDataOra(rs.getTimestamp("DATA_ORA"));
            tmpSpettacoli.add(tmpSpettacolo);
        }
        return res;
    }
}
