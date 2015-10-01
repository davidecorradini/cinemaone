/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.PrenotazioneTmp;
import Database.DBManager;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author roberto
 */
public class PrenotaPosto extends HttpServlet {

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
        String result = "";

        HttpSession session = request.getSession(false);
        String idUtente = (String) session.getAttribute("idUtente");//DBManager.encodeIdUtente(10);

        int x =Integer.parseInt(request.getParameter("x"));
        String y =request.getParameter("y");
        int idSpettacolo =Integer.parseInt(request.getParameter("spettacolo"));
        int idPrezzo=Integer.parseInt(request.getParameter("tipo"));
        
        int idPosto = 0;
        try {
            idPosto = manager.getIdPosto(x, y, idSpettacolo);
        } catch (SQLException ex) {
            
            request.setAttribute("error", "impossibile caricare la pagina, interrogazione al database fallita");
            getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }
        
        if(idPosto==0){
            throw new IllegalArgumentException("posto con coordinate "+x+","+y+" inesistente");
        }
        PrenotazioneTmp pre = new PrenotazioneTmp();
        pre.setIdUtente(idUtente);
        pre.setIdSpettacolo(idSpettacolo);
        pre.setIdPosto(idPosto);
        pre.setTimestamp(null);
        pre.setIdPrezzo(idPrezzo);
        
        int inserito = 0;
        try {
            inserito = manager.aggiungiPrenotazioneTmp(pre);
        } catch (SQLException ex) {
           
            request.setAttribute("error", "impossibile caricare la pagina, interrogazione al database fallita");
          }

        if (inserito > 0) {
            result = "success";
        } else {
            result = "failed";
        }

         response.getWriter().println(result);
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
