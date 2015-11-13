/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Servlets;

import Beans.PostiSalaPercPrenotazioni;
import Beans.Posto;
import Beans.Sala;
import Database.Cache.PrenotazioniPostoCache;
import Database.DBManager;
import Database.PostiSalaPercPrenotazioniQueries;
import Database.PostoQueries;
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

@WebServlet(name = "GestisciSale", urlPatterns = {"/admin/sale.html"})
public class GestisciSale extends HttpServlet {
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
            if(request.getParameter("id-posto") != null && request.getParameter("id-stato") != null){
                int idPosto = Integer.parseInt(request.getParameter("id-posto"));
                int stato = Integer.parseInt(request.getParameter("id-stato"));
                PostoQueries postoQueries = new PostoQueries(manager);
                Posto posto2 = new Posto();
                try {
                    posto2 = postoQueries.getPosto(idPosto);
                } catch (SQLException ex) {
                    request.setAttribute("error", "impossibile caricare la pagina, interrogazione al database fallita1");
                    getServletContext().getRequestDispatcher("/jsp/admin-error.jsp").forward(request, response);
                }
                request.setAttribute("sala", posto2.getIdSala());
                Posto posto = new Posto();
                posto.setIdPosto(idPosto);
                posto.setStato(stato);
                PrenotazioniPostoCache prenPostoQ = new PrenotazioniPostoCache(manager);
                try {
                    prenPostoQ.cambiaStato(posto);
                } catch (SQLException ex) {
                    request.setAttribute("error", "impossibile caricare la pagina, interrogazione al database fallita1");
                    getServletContext().getRequestDispatcher("/jsp/admin-error.jsp").forward(request, response);
                }
            }
            SalaQueries salaQueries = new SalaQueries(manager);
            PostiSalaPercPrenotazioniQueries postiSalaQ = new PostiSalaPercPrenotazioniQueries(manager);
            ArrayList<Sala> sale = null;
            try {
                sale = salaQueries.getSale();
            } catch (SQLException ex) {
                request.setAttribute("error", "impossibile caricare la pagina, interrogazione al database fallita1");
                getServletContext().getRequestDispatcher("/jsp/admin-error.jsp").forward(request, response);
            }
            ArrayList<ArrayList<PostiSalaPercPrenotazioni>> postiSale = new ArrayList<>();
            ArrayList<PostiSalaPercPrenotazioni> postiSala = new ArrayList<>();
            for(Sala sala : sale){
                try {
                    System.out.println(sala.getIdSala());
                    postiSala = postiSalaQ.getAllPosti(sala.getIdSala(), true);
                } catch (SQLException ex) {
                    request.setAttribute("error", "impossibile caricare la pagina, interrogazione al database fallita2"+ex);
                    getServletContext().getRequestDispatcher("/jsp/admin-error.jsp").forward(request, response);
                }
                postiSale.add(postiSala);
                postiSala = new ArrayList<>();
            }
            request.setAttribute("sale", sale);
            request.setAttribute("postiSale", postiSale);
            request.getRequestDispatcher("/jsp/gestisci-sale.jsp").forward(request, response);
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
