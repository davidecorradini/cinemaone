/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Servlets;

import Beans.InfoPrenotazione;
import Beans.PostiSala;
import Beans.SpettacoloSalaOrario;
import Database.DBManager;
import Database.InfoPrenotazioneQueries;
import Database.PostiSalaQueries;
import Database.SpettacoloQueries;
import Database.SpettacoloSalaOrarioQueries;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author roberto
 */
public class AdminSpettacoli extends HttpServlet {
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
        HttpSession session = request.getSession(false);
        String ruolo = (String)session.getAttribute("autenticato");
        if(ruolo!=null && ruolo.equals("ADMIN")){
            String idSpettacoloStr = request.getParameter("idspettacolo");
            if(idSpettacoloStr != null){ //mostra sala di questo spettacolo.
                Integer idSpettacolo = null;
                try{
                    idSpettacolo = Integer.parseInt(idSpettacoloStr);
                }catch(NumberFormatException ex){
                    request.setAttribute("error", "impossibile caricare la pagina, dati richiesta corrotti");
                    getServletContext().getRequestDispatcher("/jsp/admin-error.jsp").forward(request, response);
                }
                InfoPrenotazione infoPrenotazione = null;
                ArrayList<PostiSala> postiSala = null;
                Integer[] infoIncassi = null;
                try {
                    InfoPrenotazioneQueries infoPrenQ = new InfoPrenotazioneQueries(manager);
                    PostiSalaQueries postiSalaQ = new PostiSalaQueries(manager);
                    SpettacoloQueries spettacoloQ = new SpettacoloQueries(manager);
                    infoPrenotazione = infoPrenQ.getInfoPrenotazione(idSpettacolo);
                    if(infoPrenotazione == null){
                        request.setAttribute("error", "impossibile caricare la pagina, spettacolo non disponibile");
                        getServletContext().getRequestDispatcher("/jsp/admin-error.jsp").forward(request, response);
                    }
                    postiSala = postiSalaQ.getAllPosti(infoPrenotazione.getSala().getIdSala(), true);
                    infoIncassi = spettacoloQ.getPostiIncasso(idSpettacolo);
                } catch (SQLException ex){
                    request.setAttribute("error", "impossibile caricare la pagina, interrogazione al database fallita");
                    getServletContext().getRequestDispatcher("/jsp/admin-error.jsp").forward(request, response);
                }
                request.setAttribute("infoPrenotazione", infoPrenotazione);
                request.setAttribute("postiSala", postiSala);
                request.setAttribute("infoIncassi", infoIncassi);
                request.getRequestDispatcher("/jsp/dettaglio-spettacolo.jsp").forward(request, response);
            }else{ //mega query
                
                getServletContext().getRequestDispatcher("/jsp/admin-spettacoli.jsp").forward(request, response);
            }
        }else{
            request.setAttribute("error", "non disponi dei permessi necessari");
            getServletContext().getRequestDispatcher("/jsp/admin-error.jsp").forward(request, response);
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
