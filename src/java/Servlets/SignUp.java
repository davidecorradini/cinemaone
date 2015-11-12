/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.Utente;
import Database.ConvalidaUtentiQueries;
import Database.DBManager;
import Database.UtenteQueries;
import MailMedia.MailSender;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Davide
 */
public class SignUp extends HttpServlet {
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        String email = request.getParameter("email");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        
        UtenteQueries uq = new UtenteQueries(manager);
        try {
            if(!uq.emailValida(email)){ //se la mail non è già presente nel database
                if (password1.equals(password2)){
                    ConvalidaUtentiQueries convalidaUtenteQ = new ConvalidaUtentiQueries(manager);
                    Utente ut = new Utente();
                    ut.setEmail(email);
                    ut.setPassword(password2);
                    ut.setIdRuolo(2);
                    ut.setCredito(0);
                    String requestId = null;
                    try {
                        requestId = convalidaUtenteQ.aggiungiRichiestaConvalida(ut);
                    } catch (NoSuchAlgorithmException ex) {
                        response.getWriter().print("fail");
                    }
                    String url = "http://" + request.getServerName() + ":" + request.getServerPort() + "/Multisala/conferma-utente.html?key=" + requestId;
                    MailSender instance = new MailSender();
                    try {
                        instance.convalidaAccount(ut.getEmail(), url);
                    } catch (MessagingException ex) {
                        response.getWriter().print("fail");   
                    }
                } else {
                    response.getWriter().print("wrong-password");
                }
                response.getWriter().print("success");
            }
            else{
                response.getWriter().print("existing");
            }
        } catch (SQLException ex) {
            response.getWriter().print("fail");
            System.out.println(ex);            
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
