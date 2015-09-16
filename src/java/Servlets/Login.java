package Servlets;

import Database.DBManager;
import Beans.Utente;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
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
        Utente user = null;
        String lastPageUrl = request.getHeader("Referer");
        System.out.println("last visited page: " + lastPageUrl);
        
        try{
            user = manager.authenticate(username, password);
        }catch(SQLException ex){
            request.setAttribute("error", "impossibile caricare la pagina, interrogazione al database fallita");
            getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }
        if(user == null){
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/login.jsp");
            request.setAttribute("message", "Username/password non esistente");
            rd.forward(request, response);
        }else{
            HttpSession session = request.getSession(true);
            
            session.setAttribute("autenticato", true);
            session.setAttribute("user", user);
            System.out.println("redirigo a: " + (String)session.getAttribute("destination"));
            if(session.getAttribute("destination") != null){
                response.sendRedirect((String)session.getAttribute("destination"));
            }else{
                response.sendRedirect(request.getContextPath());
                System.out.println("redirigo a: " + request.getContextPath());
            }
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
