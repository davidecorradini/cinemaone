/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.InfoPrenotazione;
import Beans.PrenotazioneTmp;
import Beans.Spettacolo;
import Database.DBManager;
import Database.InfoPrenotazioneQueries;
import Database.PrenotazioneTmpQueries;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class aggiungiPrenTmp extends HttpServlet {
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
        int idPosto = Integer.parseInt(request.getParameter("idPosto"));
        int idSpettacolo = Integer.parseInt(request.getParameter("spettacolo"));
        int idPrezzo = Integer.parseInt(request.getParameter("prezzo"));
        PrenotazioneTmp prenTmp = new PrenotazioneTmp();
        prenTmp.setIdPosto(idPosto);
        prenTmp.setIdSpettacolo(idSpettacolo);
        prenTmp.setIdUtente((String)request.getSession(false).getAttribute("idUtente")); //encoded ID.
        
        prenTmp.setIdPrezzo(idPrezzo);
        InfoPrenotazione infoPrenotazione = null;
        InfoPrenotazioneQueries infoPrenQ = new InfoPrenotazioneQueries(manager);
        try {
            infoPrenotazione = infoPrenQ.getInfoPrenotazione(idSpettacolo);
        } catch (SQLException ex) {
            
        }
        Spettacolo spettacolo = infoPrenotazione.getSpettacolo();        
        Timestamp time = spettacolo.getTimeStamp();
        Date date = new Date();
        Timestamp currentTime = new Timestamp(date.getTime());
        if(time.getTime() > currentTime.getTime()){
            if((time.getTime() - currentTime.getTime())/1000 < PrenotazioneTmp.validity*60){
                prenTmp.setTimestamp(new Timestamp(time.getTime() - PrenotazioneTmp.validity*60*1000));
            }else{
                prenTmp.setTimestamp(new Timestamp(currentTime.getTime()));
                System.out.println("qui");
            }
            PrenotazioneTmpQueries ptq = new PrenotazioneTmpQueries(manager);
            try {
                ptq.aggiungiPrenotazioneTmp(prenTmp);
                response.getWriter().println("success");
            } catch (SQLException ex) {
                if(ex instanceof SQLIntegrityConstraintViolationException){
                    response.getWriter().println("db-fail");
                    System.err.println("constraint violation: " + ex);
                }else{
                    response.getWriter().println("fail");
                    System.err.println("sqlException1: " + ex);
                }
            }
        }else{
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
