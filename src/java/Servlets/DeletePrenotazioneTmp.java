/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Database.DBManager;
import Database.PrenotazioneTmpQueries;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author enrico
 */
public class DeletePrenotazioneTmp extends HttpServlet{
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
       
        int idPosto = -1;
        int idSpettacolo = -1;
        try{
            idSpettacolo = Integer.parseInt(request.getParameter("spettacolo"));
            idPosto = Integer.parseInt(request.getParameter("posto"));
        }catch(NumberFormatException ex){
            response.setContentType("text/plain;charset=UTF-8\n");
            try (PrintWriter out = response.getWriter()) {
                System.err.println("stato prenotazioni: " + ex);
                out.println("fail " + ex);
            }
            return;
        }
        
        PrenotazioneTmpQueries prenTmpQueries = new PrenotazioneTmpQueries(manager);
        String res = "success";
         try {
             prenTmpQueries.deletePrenotazioneTmp(idSpettacolo, idPosto);
         } catch (SQLException ex){
             res = "fail " + ex;
         }
         
         try (PrintWriter out = response.getWriter()) {
             out.println(res);
         }
         
    }
    
    @Override
    public void destroy(){
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
