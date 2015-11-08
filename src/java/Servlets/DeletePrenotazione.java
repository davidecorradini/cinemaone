package Servlets;

import Beans.Prenotazione;
import Database.Cache.PrenotazioniPostoCache;
import Database.DBManager;
import Database.PrenotazioneQueries;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DeletePrenotazione extends HttpServlet {
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
        response.setContentType("text/plain");
        int idPrenotazione = Integer.parseInt(request.getParameter("idPrenotazione"));
        
        PrenotazioneQueries pQ= new PrenotazioneQueries(manager);
        Prenotazione pren = null;
        try {
            pren = pQ.getPrenotazione(idPrenotazione);
        } catch (SQLException ex) {
           response.getWriter().println("fail");
        
        }
        
        PrenotazioniPostoCache prenQ= new PrenotazioniPostoCache(manager);
        try {
            
            prenQ.deletePrenotazione(pren);
            response.getWriter().println("success");
        } catch (SQLException ex) {
            response.getWriter().println("fail");
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
