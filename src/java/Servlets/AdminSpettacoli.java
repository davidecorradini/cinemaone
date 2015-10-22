/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.SpettacoloSalaOrario;
import Database.DBManager;
import Database.SpettacoloSalaOrarioQueries;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        String idSpettacoloStr = request.getParameter("idspettacolo");
        if(idSpettacoloStr != null){
           /*  int idSpettacolo = Integer.parseInt(idSpettacoloStr);
           InfoPrenotazione infoPrenotazione = null;
            ArrayList<PostiSala> postiSala = null;
            ArrayList<Prezzo> prezzi = null;
            try {
                InfoPrenotazioneQueries infoPrenQ = new InfoPrenotazioneQueries(manager);
                PostiSalaQueries postiSalaQ = new PostiSalaQueries(manager);
                PrezzoQueries prezziQ = new PrezzoQueries(manager);
                infoPrenotazione = infoPrenQ.getInfoPrenotazione(idSpettacolo);
                postiSala = postiSalaQ.getAllPosti(infoPrenotazione.getSala().getIdSala());
                prezzi = prezziQ.getAllPrezzi();
            } catch (SQLException ex){
                request.setAttribute("error", "impossibile caricare la pagina, interrogazione al database fallita");
                getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
            }
            stampaPostiSala(postiSala);
            postiSala = formattaInfoSala(postiSala);
            System.out.println("\n\n after:\n\n");
            stampaPostiSala(postiSala);
            request.setAttribute("infoPrenotazione", infoPrenotazione);
            request.setAttribute("postiSala", postiSala);
            request.setAttribute("prezzi", prezzi);
            request.getRequestDispatcher("/jsp/prenotazione.jsp").forward(request, response);*/
        }else{
            ArrayList<SpettacoloSalaOrario> spettacoli = null;
            SpettacoloSalaOrarioQueries sq = new SpettacoloSalaOrarioQueries(manager);
//            try {
//                //TO DO aggiungere film
//                spettacoli = sq.getSpettacoli();
//            } catch (SQLException ex) {
//                request.setAttribute("error", "impossibile caricare la pagina, interrogazione al database fallita");
//                getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
//            }
            request.setAttribute("spettacoli", spettacoli);
            getServletContext().getRequestDispatcher("/jsp/admin-spettacoli.jsp").forward(request, response);
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
