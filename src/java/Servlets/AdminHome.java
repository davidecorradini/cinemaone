/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.Sala;
import Database.DBManager;
import Database.SalaQueries;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author alessandro
 */
@WebServlet(name = "AdminHome", urlPatterns = {"/admin/index.html"})
public class AdminHome extends HttpServlet {
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
        HttpSession session = request.getSession(false);
        String ruolo = (String)session.getAttribute("autenticato");
        if(ruolo!=null && ruolo.equals("ADMIN")){
            SalaQueries salaQueries = new SalaQueries(manager);
            ArrayList<Sala> sale = null;
            try {
                sale = salaQueries.getSale();
            } catch (SQLException ex) {
                request.setAttribute("error", "impossibile caricare la pagina, interrogazione al database fallita1");
                getServletContext().getRequestDispatcher("/jsp/admin-error.jsp").forward(request, response);
            }
            request.setAttribute("sale", sale);
            getServletContext().getRequestDispatcher("/jsp/admin-index.jsp").forward(request, response);
        }else{
            request.setAttribute("error", "non disponi dei permessi necessari");
            getServletContext().getRequestDispatcher("/jsp/admin-error.jsp").forward(request, response);
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
