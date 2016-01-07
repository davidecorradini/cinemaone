/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.PasswordRecovery;
import Database.DBManager;
import Database.PasswordRecoveryQueries;
import Database.UtenteQueries;
import MailMedia.MailSender;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import javax.mail.MessagingException;

public class RecuperaPassword extends HttpServlet {
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
        response.setContentType("text/plain;charset=UTF-8");
        Date date = new Date();
        String email = request.getParameter("email");
        boolean valido = false;
        UtenteQueries uq = new UtenteQueries(manager);
        try {
            valido = uq.emailValida(email);
        } catch (SQLException ex) {
            
        }
        if(valido){
            Timestamp time = new Timestamp(date.getTime());
            String timestamp = time.toString();
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException ex) {
                response.getWriter().println("fail");
            }
            md.update((email + timestamp).getBytes());
            byte[] md5 = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : md5) {
                sb.append(String.format("%02x", b & 0xff));
            }
            String md5Str = sb.toString();
            PasswordRecoveryQueries prq = new PasswordRecoveryQueries(manager);
            try {
                PasswordRecovery pr = new PasswordRecovery();
                pr.setHash(md5Str);
                pr.setEmail(email);
                pr.setTime(time);
                prq.insertRecuperaPassword(pr);
                MailSender instance = new MailSender();
                try {
                    instance.changePassword(email, "http://" + request.getServerName() + ":" + request.getServerPort() + "/Multisala/password-recovery.html?key=" + md5Str);
                    response.getWriter().println("success");
                } catch (MessagingException ex) {
                    response.getWriter().println("fail");
                }
            } catch (SQLException ex) {
                response.getWriter().println("fail");
            }
        }else
            response.getWriter().println("noemail");      
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
