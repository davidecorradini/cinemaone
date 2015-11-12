/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.Ruolo;
import Beans.Utente;
import Database.ConvalidaUtentiQueries;
import Database.DBManager;
import Database.RuoloQueries;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author enrico
 */
public class ConfermaUtente extends HttpServlet {
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
        try {
            String id = request.getParameter("key");
            if(id == null){
                getServletContext().getRequestDispatcher("/").forward(request, response);
            }
            Utente utente = null;
            ConvalidaUtentiQueries convalidaUtQuery = new ConvalidaUtentiQueries(manager);
            try {
                utente = convalidaUtQuery.convalidaUtente(id);
            } catch (SQLException | NoSuchAlgorithmException ex) {
                request.setAttribute("error", "impossibile confermare dati Utente;" + ex);
                getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
            }
            
            RuoloQueries ruoloQuery = new RuoloQueries(manager);
            Ruolo ruolo = ruoloQuery.getRuolo(utente.getIdRuolo());
            HttpSession session = request.getSession();
            session.setAttribute("autenticato", ruolo.getRuolo());
            session.setAttribute("user", utente);
            session.removeAttribute("idUtente");
            session.setAttribute("idUtente", Utente.encodeIdUtente(utente.getIdUtente()));
            response.sendRedirect(request.getContextPath());
            
        } catch (SQLException ex) {
            request.setAttribute("error", "impossibile collegarsi al database;");
             getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }    
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
