package Servlets;

import Beans.Ruolo;
import Database.DBManager;
import Beans.Utente;
import Beans.UtenteRuolo;
import java.io.IOException;
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
public class Login extends HttpServlet {
    private DBManager manager;
    
    @Override
    public void init() throws ServletException{
        this.manager = (DBManager)super.getServletContext().getAttribute("dbmanager");
    }

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UtenteRuolo userRuolo = null;
        String lastPageUrl = request.getHeader("Referer");
        System.out.println("login requested from: " + lastPageUrl);
                
        try{
            userRuolo = manager.authenticate(username, password);
        }catch(SQLException ex){
            request.setAttribute("error", "impossibile caricare la pagina, interrogazione al database fallita");
            getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }
        Utente user = userRuolo.getUtente();
        Ruolo ruolo = userRuolo.getRuolo();
        HttpSession session = request.getSession(true);
        if(user == null)
            session.setAttribute("autenticato", "false");
        else{
            session.setAttribute("autenticato", ruolo.getRuolo());
            session.setAttribute("user", user);
        }
        
        response.sendRedirect(lastPageUrl);
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
   
    /**
     * verifica se l'utente è autenticato, ha sbagliato password/utente oppure non ha ancor fatto richieste di login.
     * @param request
     * @return 1 se non ha fatto login, 0 se è loggato, -1 se ha sbagliato.
     */
    public static int checkAuthenticationStatus(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session == null) return 1; //if there is no session
        if(session.getAttribute("autenticato") == null) return 1; //if there is no autenticato parameter
        if(!((String)session.getAttribute("autenticato")).equals("false")) return 0;
        session.removeAttribute("autenticato");
        return -1;
    }
    
    public static void Logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        session.removeAttribute("autenticato");
    }
}
