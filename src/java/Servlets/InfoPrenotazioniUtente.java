package Servlets;

import Beans.PrenotazioniUtente;
import Beans.Utente;
import Database.DBManager;
import Database.UtenteQueries;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class InfoPrenotazioniUtente extends HttpServlet {
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
        Utente user = (Utente)session.getAttribute("user");
        if(user!=null){
            int idUtente=user.getIdUtente(); //encoded ID.
            double credito = 0.0;
            UtenteQueries utenteQ = new UtenteQueries(manager);
            try {
                credito = utenteQ.getCredito(user.getIdUtente());
            } catch (SQLException ex) {
                credito = 0.0;
            }
            ArrayList<PrenotazioniUtente> infoPrenotazioniUtente = null;
            UtenteQueries sq = new UtenteQueries(manager);
            try {
                infoPrenotazioniUtente = sq.getInfoPrenotazioniUtente(idUtente);
            } catch (SQLException ex) {
                request.setAttribute("error", "impossibile caricare la pagina, interrogazione al database fallita");
                getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
            }
            request.setAttribute("credito", credito);
            request.setAttribute("infoPrenotazioniUtente", infoPrenotazioniUtente);
            getServletContext().getRequestDispatcher("/jsp/infoPrenotazioniUtente.jsp").forward(request, response);
        }else{
            request.setAttribute("error", "impossibile caricare la pagina, nessun utente loggato");
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
