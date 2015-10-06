/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import Beans.Film;
import Beans.FilmPrezzo;
import Beans.Prezzo;
import Beans.Utente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author enrico
 */
public class FilmPrezzoQueries{
    private final transient Connection con;
    
    public FilmPrezzoQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public FilmPrezzoQueries(Connection con){
        this.con = con;
    }
    
    /**
     * permette di ottenere uno storico dell'utente: che film ha guardato e quando ha pagato.
     * @param ut utente di cui si vuole ottenere lo storico
     * @return un array di coppie di oggetti, di cui il primo è un instanza di Film e il secondo di Prezzo
     * @throws SQLException
     */
    public ArrayList<FilmPrezzo> getStorico(Utente ut) throws SQLException{
        PreparedStatement stm;
        stm = con.prepareStatement(
                "SELECT DISTINCT F.ID_FILM, F.ID_GENERE, F.TITOLO, F.DURATA, F.TRAMA,F.IS_IN_SLIDER, F.URL_TRAILER, F.URI_LOCANDINA, F.ANNO, F.REGISTA" +
                        " PR.ID_PREZZO, PR.TIPO, PR.PREZZO\n" +
                        "FROM FILM F JOIN SPETTACOLO S ON F.ID_FILM = S.ID_FILM JOIN PRENOTAZIONE P ON P.ID_SPETTACOLO = S.ID_SPETTACOLO JOIN PREZZO PR ON PR.ID_PREZZO = P.ID_PREZZO\n" +
                        "WHERE P.ID_UTENTE = ?");
        ArrayList<FilmPrezzo> res = new ArrayList<>();
        try {
            stm.setInt(1, ut.getIdUtente());
            ResultSet rs = stm.executeQuery();
            try {
                
                while(rs.next()){
                    FilmPrezzo tmp = new FilmPrezzo();
                    
                    Film tmpFilm = new Film();
                    tmpFilm.setIdFilm(rs.getInt("ID_FILM"));
                    tmpFilm.setIdGenere(rs.getInt("ID_GENERE"));
                    tmpFilm.setTitolo(rs.getString("TITOLO"));
                    tmpFilm.setDurata(rs.getInt("DURATA"));
                    tmpFilm.setTrama(rs.getString("TRAMA"));
                    tmpFilm.setUrlTrailer(rs.getString("URL_TRAILER"));
                    tmpFilm.setUriLocandina(rs.getString("URI_LOCANDINA"));
                    tmpFilm.setAnno(rs.getInt("ANNO"));
                    tmpFilm.setisInSlider(rs.getBoolean("IS_IN_SLIDER"));
                    tmpFilm.setRegista(rs.getString("REGISTA"));
                    
                    Prezzo tmpPrezzo = new Prezzo();
                    tmpPrezzo.setIdPrezzo(rs.getInt("ID_PREZZO"));
                    tmpPrezzo.setPrezzo(rs.getDouble("PREZZO"));
                    tmpPrezzo.setTipo(rs.getString("TIPO"));
                    
                    tmp.setFilm(tmpFilm);
                    tmp.setPrezzo(tmpPrezzo);
                    res.add(tmp);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return res;
    }
    
}
