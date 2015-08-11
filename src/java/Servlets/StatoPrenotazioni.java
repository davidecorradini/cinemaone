/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.Posto;
import Beans.PrenotazioneTmp;
import Database.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


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
    
    public String serialize(List<Object[]> lista){
        String res = "{";//{
        for(Object[] pren : lista){
            res = res.concat("\"" + Integer.toString(((PrenotazioneTmp)pren[0]).getIdPosto()) + "\":"); // { "$idPosto":
            res = res.concat("{\"x\":\"" + Integer.toString(((Posto)pren[1]).getColonna()) +
                    "\", \"y\":" + ((Posto)pren[1]).getRiga() + "\"}");
                    }
         res = res.concat("}");
        return res;
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
        
        HttpSession session = request.getSession(true); //da mettere poi a false
        //String idUtente = (String)session.getAttribute("idUtente");
        String idUtente = "sdascascasca";
        int idSpettacolo = 0;
        try{
            idSpettacolo = Integer.parseInt(request.getParameter("spettacolo"));
        }catch(NumberFormatException ex){
            //TO DO forward to error.jsp
        }
        
        //ArrayList<Object[]> result = manager.getPrenotazioniTmp(idSpettacolo);
        
        //creazione json, serializzo il result
        response.setContentType("text/plain;charset=UTF-8\nAccess-Control-Allow-Origin: *");
        try (PrintWriter out = response.getWriter()) {
            out.println("{\"1\":{\"x\" : \"1\", \"y\" : \"A\", \"stato\" : \"occupato\"}}");
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
