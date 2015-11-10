package Servlets;

import Beans.InfoPrenotazione;
import Beans.PrenotazioneTmp;
import Beans.Spettacolo;
import Database.DBManager;
import Database.InfoPrenotazioneQueries;
import Database.PostoQueries;
import Database.PrenotazioneTmpQueries;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author roberto
 */
public class SynchronizeTimers extends HttpServlet {

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
        String res = "success";
        response.setContentType("text/plain;charset=UTF-8\n");
        int idSpettacolo = Integer.parseInt(request.getParameter("spettacolo"));
        HttpSession session = request.getSession(false);
        String idUtente = (String) session.getAttribute("idUtente");
        PrenotazioneTmpQueries ptq = new PrenotazioneTmpQueries(manager);
        InfoPrenotazione infoPrenotazione = null;
        InfoPrenotazioneQueries infoPrenQ = new InfoPrenotazioneQueries(manager);
        try {
            infoPrenotazione = infoPrenQ.getInfoPrenotazione(idSpettacolo);
        } catch (SQLException ex) {
            res = "fail";
        }
        Spettacolo spettacolo = infoPrenotazione.getSpettacolo();        
        Timestamp time = spettacolo.getTimeStamp();
        Date date = new Date();
        Timestamp currentTime = new Timestamp(date.getTime());
        if(!((time.getTime() - currentTime.getTime())/1000 < 60)){
            try {
                ptq.setTimerPrenotazioneTMP(idUtente,null);
            } catch (SQLException ex) {
                res = "fail";
            }
        }else{
            try {
                ptq.setTimerPrenotazioneTMP(idUtente,new Timestamp(time.getTime() - PrenotazioneTmp.validity*60*1000));
            } catch (SQLException ex) {
                res = "fail";
            }
        }
        
        
        try (PrintWriter out = response.getWriter()) {
            out.println(res);
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
