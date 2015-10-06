/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import Beans.Film;
import Beans.InfoPrenotazione;
import Beans.Sala;
import Beans.Spettacolo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author enrico
 */
public class InfoPrenotazioneQueries{
    private final transient Connection con;
    
    public InfoPrenotazioneQueries(DBManager manager){
        this.con = manager.con;
    }
    
    public InfoPrenotazioneQueries(Connection con){
        this.con = con;
    }
    
    /**
     *
     * @param idSpettacolo
     * @return
     * @throws SQLException
     */
    public InfoPrenotazione getInfoPrenotazione(int idSpettacolo) throws SQLException{
        InfoPrenotazione res = new InfoPrenotazione();
        PreparedStatement stm = con.prepareStatement(
                "SELECT SP.ID_SPETTACOLO, SP.ID_FILM, SP.ID_SALA, SP.DATA_ORA, F.ID_GENERE, F.TITOLO, F.DURATA, F.TRAMA, F.IS_IN_SLIDER, F.URL_TRAILER, F.URI_LOCANDINA, F.REGISTA, F.ANNO, SA.NOME, SA.DESCRIZIONE\n" +
                        "FROM SPETTACOLO SP JOIN FILM F ON SP.ID_FILM = F.ID_FILM JOIN SALA SA ON SP.ID_SALA = SA.ID_SALA\n" +
                        "WHERE SP.ID_SPETTACOLO = ?");
        stm.setInt(1, idSpettacolo);
        ResultSet rs = stm.executeQuery();
        try{
            if(rs.next()){
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
                res.setFilm(tmpFilm);
                
                Spettacolo tmpSpettacolo = new Spettacolo();
                tmpSpettacolo.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
                tmpSpettacolo.setIdSala(rs.getInt("ID_SALA"));
                tmpSpettacolo.setIdFilm(rs.getInt("ID_FILM"));
                tmpSpettacolo.setDataOra(rs.getTimestamp("DATA_ORA"));
                res.setSpettacolo(tmpSpettacolo);
                Sala tmpSala = new Sala();
                tmpSala.setDescrizione(rs.getString("DESCRIZIONE"));
                tmpSala.setIdSala(rs.getInt("ID_SALA"));
                tmpSala.setNome(rs.getString("NOME"));
                res.setSala(tmpSala);
            }
            
        }finally{
            stm.close();
        }
        return res;
    }
    
}
