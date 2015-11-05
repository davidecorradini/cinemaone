/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Servlets;

import Beans.InfoPrenotazione;
import Beans.PostiSala;
import Beans.Posto;
import Beans.Prezzo;
import Beans.Spettacolo;
import Database.DBManager;
import Database.InfoPrenotazioneQueries;
import Database.PostiSalaQueries;
import Database.PrezzoQueries;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class Prenotazioni extends HttpServlet {
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer idSpettacolo = null;
        try{
            idSpettacolo = Integer.parseInt(request.getParameter("idspettacolo"));
        }catch(NumberFormatException ex){
            request.setAttribute("error", "impossibile caricare la pagina, dati richiesta corrotti");
            getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }
        InfoPrenotazione infoPrenotazione = null;
        ArrayList<PostiSala> postiSala = null;
        ArrayList<Prezzo> prezzi = null;
        try {
            InfoPrenotazioneQueries infoPrenQ = new InfoPrenotazioneQueries(manager);
            PostiSalaQueries postiSalaQ = new PostiSalaQueries(manager);
            PrezzoQueries prezziQ = new PrezzoQueries(manager);
            infoPrenotazione = infoPrenQ.getInfoPrenotazione(idSpettacolo);
            if(infoPrenotazione == null){
                request.setAttribute("error", "impossibile caricare la pagina, spettacolo non disponibile");
                getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
            }
            postiSala = postiSalaQ.getAllPosti(infoPrenotazione.getSala().getIdSala(), true);
            prezzi = prezziQ.getAllPrezzi();
        } catch (SQLException ex){
            request.setAttribute("error", "impossibile caricare la pagina, interrogazione al database fallita");
            getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        } 
        Spettacolo spettacolo = infoPrenotazione.getSpettacolo();        
        Timestamp time = spettacolo.getTimeStamp();
        Date date = new Date();
        Timestamp currentTime = new Timestamp(date.getTime());
        if(time.getTime() > currentTime.getTime()){
            long timer = (time.getTime()-currentTime.getTime())/1000;
            request.setAttribute("mainTimer", timer);
            request.setAttribute("infoPrenotazione", infoPrenotazione);
            request.setAttribute("postiSala", postiSala);
            request.setAttribute("prezzi", prezzi);
            request.getRequestDispatcher("/jsp/prenotazione.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "impossibile caricare la pagina, spettacolo non disponibile");
            getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
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
