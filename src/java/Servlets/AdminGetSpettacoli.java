package Servlets;

import Beans.PrenotazioneTmp;
import Beans.SpettacoloSalaOrario;
import Database.DBManager;
import Database.PostoQueries;
import Database.PrenotazioneTmpQueries;
import Database.SpettacoloSalaOrarioQueries;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author roberto
 */
public class AdminGetSpettacoli extends HttpServlet {

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
        ArrayList<SpettacoloSalaOrario> result=null;

        
        String titolo =request.getParameter("titolo");
        String genere =request.getParameter("genere");
        String regista =request.getParameter("regista");
        String nomeSala =request.getParameter("sala");
        int durataMin =Integer.parseInt(request.getParameter("durataMin"));
        int durataMax =Integer.parseInt(request.getParameter("durataMax"));
        //Timestamp programmazioneDa =request.getParameter("programmazioneDa");// prendo in input 14/11/2011 da convertire in Tiestamp
        //Timestamp programmazioneA =request.getParameter("programmazioneA");// prendo in input 14/11/2011 da convertire in Tiestamp
        
        
        
        
        SpettacoloSalaOrarioQueries spet = new SpettacoloSalaOrarioQueries(manager);
        //try {
            //result = spet.getSpettacoli(titolo,genere,regista,nomeSala,durataMin,durataMax,programmazioneDa,programmazioneA);
        //} catch (SQLException ex) {
            
            request.setAttribute("error", "impossibile caricare la pagina, interrogazione al database fallita");
            getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        //}
        
        
       

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
}
