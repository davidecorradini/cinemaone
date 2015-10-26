package Servlets;

import Database.DBManager;
import Database.PrenotazioneTmpQueries;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ConfermaPrenotazione extends HttpServlet {

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
        String result = "";

        HttpSession session = request.getSession(false);
        String idUtente = (String)session.getAttribute("idUtente");
        PrenotazioneTmpQueries ptm = new PrenotazioneTmpQueries(manager);
        try {
            ptm.confermaPrenotazioni(idUtente);
        } catch (SQLException ex) {
            request.setAttribute("error", "impossibile caricare la pagina, interrogazione al database fallita");
            getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }catch(IllegalArgumentException ex){
            request.setAttribute("error", "l'utente deve essere loggato per poter prenotare posti");
            getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }
        
        getServletContext().getRequestDispatcher("/jsp/prenotazioneEffettuata.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        this.manager = null;
    }

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
