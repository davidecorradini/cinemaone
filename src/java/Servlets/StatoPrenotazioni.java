/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.Posto;
import Beans.PrenTmpPosto;
import Beans.Prenotazione;
import Beans.PrenotazionePosto;
import Beans.PrenotazioneTmp;
import Beans.Utente;
import Database.Cache.PrenotazioniPostoCache;
import Database.Cache.PrenotazioniTmpPostoCache;
import Database.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * La servlet risponde con una stringa json con le informazioni riguardanti lo stato dei posti della sala per uno spettacolo.
 * Stati possibili: occupato (posto sicuramente prenotato, senza timestamp), tmp (un utente è in fase di prenotazione di quel posto con timestamp), tuo (l'utente che fa la richiesta ha selezionato quel posto con timestamp)
 * Si aspetta di trovare per l'utente una sessione esistente con un id univoco che identifica l'utente sotto il nome "idUtente", l'id è di tipo stringa
 * formato json in dettaglio: {"$idPosto" : {"x" : $x, "y" : $y, "stato" : "tmp", "timestamp" : $timestamp}}
 */
public class StatoPrenotazioni extends HttpServlet {
    private DBManager manager;
    private static final String postoTuo = "tuo";
    private static final String postoTuoTmp = "tuo-tmp";
    private static final String postoOccupato = "occupato";
    private static final String postoOccupatoTmp = "occupato-tmp";
    
    @Override
    public void init() throws ServletException{
        this.manager = (DBManager)super.getServletContext().getAttribute("dbmanager");
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String idUtente = (String)(session.getAttribute("idUtente"));
        int idSpettacolo = 0;
        try{
            idSpettacolo = Integer.parseInt(request.getParameter("spettacolo"));
        }catch(NumberFormatException ex){
            response.setContentType("text/plain;charset=UTF-8\n");
            try (PrintWriter out = response.getWriter()) {
                System.err.println("stato prenotazioni: " + ex);
                out.println("fail " + ex);
            }
            return;
        }
        
        ArrayList<PrenTmpPosto> occupiedTmp;
        ArrayList<PrenotazionePosto> occupied;
        PrenotazioniTmpPostoCache ptpq = new PrenotazioniTmpPostoCache(manager);
        PrenotazioniPostoCache prenPostoCache = new PrenotazioniPostoCache(manager);
        try {
            occupiedTmp = ptpq.getPrenotazioneTmp(idSpettacolo);
            occupied = prenPostoCache.getPostiOccupati(idSpettacolo);
        } catch (SQLException ex) {
            response.setContentType("text/plain;charset=UTF-8\n");
            try (PrintWriter out = response.getWriter()) {
                System.err.println("stato prenotazioni: " + ex);
                out.println("fail " + ex);
            }
            return;
        }
        
//creazione json, serializzo i posti occupati
        JSONObject json = new JSONObject();
        try {
            for(PrenotazionePosto res : occupied){
                Posto posto = res.getPosto();
                Prenotazione pren = res.getPrenotazione();
                JSONObject jsonObject= new JSONObject();
                jsonObject.put("x", posto.getColonna());
                jsonObject.put("y", String.valueOf(posto.getRiga()));
                String stato = postoOccupato;
                Object decodedId = Utente.decodeIdUtente(idUtente); //nella tabella prenotazioni c'è l'id come intero (non encoded)
                if(decodedId instanceof Integer && res.getPrenotazione().getIdUtente() == ((Integer)decodedId))
                    stato = postoTuo;
                jsonObject.put("stato", stato);
                jsonObject.put("prezzo", -1);
                json.put(Integer.toString(posto.getIdPosto()), jsonObject);
            }
            
            for(PrenTmpPosto res : occupiedTmp){ //cicla su prenotazioniTmp.
                Posto posto = res.getPosto();
                PrenotazioneTmp pren = res.getPren();
                JSONObject jsonObject= new JSONObject();
                
                jsonObject.put("x", posto.getColonna());
                jsonObject.put("y", String.valueOf(posto.getRiga()));
                String stato = postoOccupatoTmp;
                //in prenotazioneTmp c'è l'id encoded.
                int prezzo = -1;
                if(idUtente.equals(pren.getIdUtente())){ //se ho fatto io quella prenotazionetmp
                    stato = postoTuoTmp;
                    prezzo = pren.getIdPrezzo();
                }
                jsonObject.put("stato", stato);
                jsonObject.put("prezzo", prezzo);
                jsonObject.put("timestamp", tempoRimanente(res.getPren().getTimestamp()));
                
                json.put(Integer.toString(posto.getIdPosto()), jsonObject);
            }
        } catch (JSONException ex) {
            request.setAttribute("error", "errore nella creazione del json");
            getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }        
        String jsonStr = json.toString();
        
        response.setContentType("text/plain;charset=UTF-8\n");
        try (PrintWriter out = response.getWriter()) {
            out.println(jsonStr);
        }
    }
    
    @Override
    public void destroy(){
        this.manager = null;
    }
    
    private long tempoRimanente(Timestamp prenTime){
        long tempoRestante = PrenotazioneTmp.validity*60 - (System.currentTimeMillis() - prenTime.getTime())/1000;
        return tempoRestante;
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
