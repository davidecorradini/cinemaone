package Servlets;

import Beans.Film;
import Beans.Genere;
import Beans.PrenotazioneTmp;
import Beans.Sala;
import Beans.Spettacolo;
import Beans.SpettacoloSalaOrario;
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
public class AdminGetSpettacoli extends HttpServlet {

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
        ArrayList<SpettacoloSalaOrario> result=null;

        
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
        
        SpettacoloSalaOrarioQueries spet = new SpettacoloSalaOrarioQueries(manager);
        try {
            result = spet.getSpettacoli(genere,titolo,durataMin,durataMax,regista,programmazioneDa,programmazioneA,nomeSala);
        } catch (SQLException ex) {
            response.setContentType("text/plain;charset=UTF-8\n");
            try (PrintWriter out = response.getWriter()) {
                System.err.println("get spettacoli: " + ex);
                out.println("fail");
            }
            return;
        }
        JSONObject json = new JSONObject();
        try{
            for(SpettacoloSalaOrario spettacoloSalaOrario : result){
                Spettacolo spettacolo = spettacoloSalaOrario.getSpettacolo();
                Sala sala = spettacoloSalaOrario.getSala();
                Film film = spettacoloSalaOrario.getFilm();
                Genere genere1 = spettacoloSalaOrario.getGenere();
                JSONObject jsonObject= new JSONObject();
                jsonObject.put("idFilm", String.valueOf(film.getIdFilm()));
                jsonObject.put("titolo", film.getTitolo());
                jsonObject.put("regista", film.getRegista());
                jsonObject.put("genere", genere1.getDescrizione());
                jsonObject.put("anno", String.valueOf(film.getAnno()));
                jsonObject.put("durata", String.valueOf(film.getDurata()));
                jsonObject.put("data", spettacolo.getData());
                jsonObject.put("ora", spettacolo.getOra());
                jsonObject.put("sala", sala.getNome());
                json.put(Integer.toString(spettacolo.getIdSpettacolo()), jsonObject);
            }
        } catch (JSONException ex) {
            response.setContentType("text/plain;charset=UTF-8\n");
            try (PrintWriter out = response.getWriter()) {
                System.err.println("get spettacoli: " + ex);
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
