/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Database;

import Beans.Film;
import Beans.Genere;
import Beans.Sala;
import Beans.Spettacolo;
import Beans.SpettacoloSalaOrario;
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
       
    public ArrayList<SpettacoloSalaOrario> getSpettacoli() throws SQLException{
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
}
