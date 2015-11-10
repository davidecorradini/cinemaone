package Servlets;

import Beans.AdminPrenotazioneUtenti;
import Beans.Film;
import Beans.Genere;
import Beans.Posto;
import Beans.Prenotazione;
import Beans.PrenotazioneTmp;
import Beans.Prezzo;
import Beans.Ruolo;
import Beans.Sala;
import Beans.Spettacolo;
import Beans.SpettacoloSalaOrario;
import Beans.Utente;
import Database.DBManager;
import Database.PostoQueries;
import Database.PrenotazioneTmpQueries;
import Database.SpettacoloSalaOrarioQueries;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author roberto
 */
public class AdminGetPrenotazioni extends HttpServlet {

    private DBManager manager;

    @Override
    public void init() throws ServletException {
        this.manager = (DBManager) super.getServletContext().getAttribute("dbmanager");
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<AdminPrenotazioneUtenti> result=null;

        String titolo = null;
        if(request.getParameter("titolo") != null && !request.getParameter("titolo").equals(""))
            titolo = request.getParameter("titolo");
        String genere = null;
        if(request.getParameter("genere") != null && !request.getParameter("genere").equals(""))
            genere = request.getParameter("genere");
        String regista = null;
        if(request.getParameter("regista") != null && !request.getParameter("regista").equals(""))
            regista = request.getParameter("regista");
        String nomeSala = null;
        if(request.getParameter("sala") != null && !request.getParameter("sala").equals(""))
            nomeSala = request.getParameter("sala");
        Integer durataMin = null ;
        if(request.getParameter("durataMin") != null &&!request.getParameter("durataMin").equals(""))
            try{
                durataMin = Integer.parseInt(request.getParameter("durataMin"));
            }catch(NumberFormatException ex){
                durataMin = null;
            }
        Integer durataMax = null ;
        if(request.getParameter("durataMax") != null &&!request.getParameter("durataMax").equals(""))
            try{
                durataMax = Integer.parseInt(request.getParameter("durataMax"));
            }catch(NumberFormatException ex){
                durataMax = null;
            }
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Timestamp programmazioneDa = null;
        if(request.getParameter("programmazioneDa") != null && !request.getParameter("programmazioneDa").equals("")){
            try {
                Date date = dateFormat.parse(request.getParameter("programmazioneDa"));
                long time = date.getTime();
                programmazioneDa = new Timestamp(time);
            } catch (ParseException ex) {
                programmazioneDa = null;
            }
        }
        
        Timestamp programmazioneA = null;
        if(request.getParameter("programmazioneA") != null && !request.getParameter("programmazioneA").equals("")){
            try {
                Date date = dateFormat.parse(request.getParameter("programmazioneA"));            
                long time = date.getTime()+86399000;
                programmazioneA = new Timestamp(time);
            } catch (ParseException ex) {
                programmazioneA = null;
            }
        }
        
        String email = null;
        if(request.getParameter("email") != null && !request.getParameter("email").equals(""))
                email = request.getParameter("email");
        String ruolo = null;
        if(request.getParameter("ruolo") != null && !request.getParameter("ruolo").equals(""))
            ruolo = request.getParameter("ruolo");
        
        Timestamp prenotazioneDa = null;
        if(request.getParameter("prenotazioneDa") != null && !request.getParameter("prenotazioneDa").equals(""))
            try {
                Date date = dateFormat.parse(request.getParameter("prenotazioneDa"));            
                long time = date.getTime();
                programmazioneA = new Timestamp(time);
            } catch (ParseException ex) {
                programmazioneA = null;
            }
        Timestamp prenotazioneA = null;
        if(request.getParameter("prenotazioneA") != null && !request.getParameter("prenotazioneA").equals(""))
            try {
                Date date = dateFormat.parse(request.getParameter("prenotazioneA"));            
                long time = date.getTime()+86399000;
                programmazioneA = new Timestamp(time);
            } catch (ParseException ex) {
                programmazioneA = null;
            }
        
        String tipoPrezzo = null;
        if(request.getParameter("tipoPrezzo") != null && !request.getParameter("tipoPrezzo").equals(""))
            tipoPrezzo = request.getParameter("tipoPrezzo"); // intero, disabile
        Character riga = null;
        if(request.getParameter("riga") != null && !request.getParameter("riga").equals(""))
            riga = request.getParameter("riga").charAt(0);
        Integer colonna = null;
        if(request.getParameter("colonna") != null && !request.getParameter("colonna").equals(""))
            try{
                colonna = Integer.parseInt(request.getParameter("colonna"));
            }catch(NumberFormatException ex){
                colonna = null;
            }
        
        
        
        SpettacoloSalaOrarioQueries spet = new SpettacoloSalaOrarioQueries(manager);
        try {
            result = spet.getPrenotazioni(titolo,genere,regista,nomeSala,durataMin,durataMax,programmazioneDa,programmazioneA,email,ruolo,prenotazioneDa,prenotazioneA,tipoPrezzo,riga,colonna);
        } catch (SQLException ex) {
            response.setContentType("text/plain;charset=UTF-8\n");
            try (PrintWriter out = response.getWriter()) {
                System.err.println("get prenotazioni: " + ex);
                out.println("fail");
            }
            return;
        }
        
        
        JSONObject json = new JSONObject();
        try{
            for(AdminPrenotazioneUtenti adminPrenotazioneUtenti : result){
                Utente utente = adminPrenotazioneUtenti.getUtente();
                Prenotazione prenotazione = adminPrenotazioneUtenti.getPrenotazione();
                Prezzo prezzo = adminPrenotazioneUtenti.getPrezzo();
                Ruolo ruolo1 = adminPrenotazioneUtenti.getRuolo();
                Posto posto = adminPrenotazioneUtenti.getPosto();
                Spettacolo spettacolo = adminPrenotazioneUtenti.getSpettacolo();
                Sala sala = adminPrenotazioneUtenti.getSala();
                Film film = adminPrenotazioneUtenti.getFilm();
                Genere genere1 = adminPrenotazioneUtenti.getGenere();
                JSONObject jsonObject= new JSONObject();
                jsonObject.put("idFilm", String.valueOf(film.getIdFilm()));
                jsonObject.put("titolo", film.getTitolo());
                jsonObject.put("regista", film.getRegista());
                jsonObject.put("genere", genere1.getDescrizione());
                jsonObject.put("anno", String.valueOf(film.getAnno()));
                jsonObject.put("durata", String.valueOf(film.getDurata()));
                jsonObject.put("dataSpettacolo", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(spettacolo.getTimeStamp()));
                jsonObject.put("dataPrenotazione", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(prenotazione.getDataOraOperazione()));
                jsonObject.put("sala", sala.getNome());
                jsonObject.put("utente", utente.getEmail());
                jsonObject.put("biglietto", prezzo.getTipo());
                String posto1 = "";                
                if(posto.getColonna()<10)
                    posto1 = String.valueOf(posto.getRiga()).toUpperCase() + "0" + posto.getColonna();
                else
                    posto1 = String.valueOf(posto.getRiga()).toUpperCase() + posto.getColonna();
                jsonObject.put("posto", posto1);
                json.put(Integer.toString(prenotazione.getIdPrenotazione()), jsonObject);
            }
        } catch (JSONException ex) {
            response.setContentType("text/plain;charset=UTF-8\n");
            try (PrintWriter out = response.getWriter()) {
                System.err.println("get prenotazioni: " + ex);
                out.println("fail");
            }
            return;
        }                    
        String jsonStr = json.toString();
        
        response.setContentType("text/plain;charset=UTF-8\n");
        try (PrintWriter out = response.getWriter()) {
            out.println(jsonStr);
        }
       
    }

    @Override
    public void destroy() {
        this.manager = null;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
