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
import Database.DBManager;
import Database.PrenTmpPostoQueries;
import Database.PrenotazionePostoQueries;
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
        
        HttpSession session = request.getSession(false); //da mettere poi a false
        String idUtente = Utente.encodeIdUtente(session.getAttribute("idUtente"));
        int idSpettacolo = 0;
        try{
            idSpettacolo = Integer.parseInt(request.getParameter("spettacolo"));
        }catch(NumberFormatException ex){
            response.setContentType("text/plain;charset=UTF-8\n");
            try (PrintWriter out = response.getWriter()) {
                out.println("fail " + ex);
            }
            return;
        }
        
        ArrayList<PrenTmpPosto> result;
        ArrayList<PrenotazionePosto> occupied;
        PrenTmpPostoQueries ptpq = new PrenTmpPostoQueries(manager);
        PrenotazionePostoQueries ppq = new PrenotazionePostoQueries(manager);
        try {
            result = ptpq.getPrenotazioneTmp(idSpettacolo);
            occupied = ppq.getPostiOccupati(idSpettacolo);
        } catch (SQLException ex) {
            response.setContentType("text/plain;charset=UTF-8\n");
            try (PrintWriter out = response.getWriter()) {
                out.println("fail " + ex);
            }
            return;
        }
        
         
        ArrayList<Posto> postiSettati = new ArrayList<>();
        
//creazione json, serializzo il result...trova una libreria decente.
        JSONObject json = new JSONObject();
        try {
            for(PrenotazionePosto res : occupied){
                Posto posto = res.getPosto();
                postiSettati.add(posto);
                Prenotazione pren = res.getPrenotazione();
                JSONObject jsonObject= new JSONObject();
                jsonObject.put("x", posto.getColonna());
                jsonObject.put("y", String.valueOf(posto.getRiga()));
                String stato = "occupato";
                /*if(res.getPrenotazione().getIdUtente().equals(idUtente))
                    stato = "tuo-pagato";*/
                jsonObject.put("stato", stato);
                java.util.Date date= new java.util.Date();
                jsonObject.put("timestamp", new Timestamp(date.getTime()));
                
                json.put(Integer.toString(posto.getIdPosto()), jsonObject);
            }
            
            for(PrenTmpPosto res : result){ //cicla su prenotazioniTmp.
                Posto posto = res.getPosto();
               
                if(!postiSettati.contains(posto)){ //non dovrebbe mai essere vera.                   
                    
                    PrenotazioneTmp pren = res.getPren();
                    JSONObject jsonObject= new JSONObject();
                    
                    jsonObject.put("x", posto.getColonna());
                    jsonObject.put("y", String.valueOf(posto.getRiga()));
                    String stato = "tmp";
                    if(idUtente.equals(pren.getIdUtente())) //se ho fatto io quella prenotazionetmp
                        stato = "tuo";
                                       
                    jsonObject.put("stato", stato);
                    java.util.Date date= new java.util.Date();
                    jsonObject.put("timestamp", new Timestamp(date.getTime()));
                    
                    json.put(Integer.toString(posto.getIdPosto()), jsonObject);
                }else{
                    //errore
                    }  
            }
        } catch (JSONException ex) {
            request.setAttribute("error", "errore nella creazione del json");
            getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }        
        
        response.setContentType("text/plain;charset=UTF-8\n");
        try (PrintWriter out = response.getWriter()) {
            out.println(json.toString());
        }
    }
    
    @Override
    public void destroy(){
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
