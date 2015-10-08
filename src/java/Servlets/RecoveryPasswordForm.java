/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.EmailTimestamp;
import Database.DBManager;
import Database.EmailTimestampQueries;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alessandro
 */
public class RecoveryPasswordForm extends HttpServlet {
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
        String md5 = request.getParameter("key");
        EmailTimestamp info = null;
        EmailTimestampQueries etsq = new EmailTimestampQueries(manager);
        try {
            info = etsq.getInfoRecovery(md5);            
        } catch (SQLException ex) {
            request.setAttribute("valida", false);
        }
        if(info != null){
            String email = info.getEmail();
            request.setAttribute("email", email);
            Timestamp time = info.getTimestamp();
            Date date = new Date();
            Timestamp currentTime = new Timestamp(date.getTime());
            long diff = currentTime.getTime() - time.getTime();
            if(diff <= 300000)
                request.setAttribute("valida", true);
            else
                request.setAttribute("valida", false);
        }else
            request.setAttribute("valida", false);
        getServletContext().getRequestDispatcher("/jsp/recovery-password.jsp").forward(request, response);
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
