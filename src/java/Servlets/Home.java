/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.Film;
import Beans.Prezzo;
import Database.DBManager;
import Database.FilmQueries;
import Database.PrezzoQueries;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author enrico
 */
public class Home extends HttpServlet {
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
        ArrayList<Film> films = null;
        FilmQueries fq = new FilmQueries(manager);
        try {
            films = fq.getFilmsSlider();
        } catch (SQLException ex) {
            request.setAttribute("error", "impossibile caricare la pagina home, interrogazione al database fallita: " + ex);
            getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }
        request.setAttribute("filmsSlider", films);
        ArrayList<Prezzo> prezzi = null;
        
        PrezzoQueries pq = new PrezzoQueries(manager);
        try {
            prezzi = pq.getAllPrezzi();
        } catch (SQLException ex) {
             request.setAttribute("error", "impossibile caricare la pagina, interrogazione al database fallita");
             getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }
        request.setAttribute("prezzi", prezzi);
        getServletContext().getRequestDispatcher("/jsp/index.jsp").forward(request, response);
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
