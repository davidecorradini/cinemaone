/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Servlets;

import Beans.InfoPrenotazione;
import Beans.PostiSala;
import Beans.Posto;
import Database.DBManager;
import Database.InfoPrenotazioneQueries;
import Database.PostiSalaQueries;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class Prenotazioni extends HttpServlet {
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
        System.out.println("PRENOTAZIONI");
        int idSpettacolo = Integer.parseInt(request.getParameter("idspettacolo"));
        InfoPrenotazione infoPrenotazione = null;
        ArrayList<PostiSala> postiSala = null;
        try {
            InfoPrenotazioneQueries infoPrenQ = new InfoPrenotazioneQueries(manager);
            PostiSalaQueries postiSalaQ = new PostiSalaQueries(manager);
            infoPrenotazione = infoPrenQ.getInfoPrenotazione(idSpettacolo);
            postiSala = postiSalaQ.getAllPosti(infoPrenotazione.getSala().getIdSala());
        } catch (SQLException ex){
            request.setAttribute("error", "impossibile caricare la pagina, interrogazione al database fallita");
            getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }
        
        postiSala = formattaInfoSala(postiSala);
        
        request.setAttribute("infoPrenotazione", infoPrenotazione);
        request.setAttribute("postiSala", postiSala);
        request.getRequestDispatcher("/jsp/prenotazione.jsp").forward(request, response);
    }
    
    @Override
    public void destroy(){
        this.manager = null;
    }
    
    ArrayList<PostiSala> formattaInfoSala(ArrayList<PostiSala> incompleteList){
        if(incompleteList.isEmpty()) return null;
        char startC = incompleteList.get(0).getRiga();
        char endC = incompleteList.get(incompleteList.size()-1).getRiga();
        if(endC - startC == 0) return null;
        int startN = Integer.MAX_VALUE, endN = Integer.MIN_VALUE;
        for(PostiSala it : incompleteList){
            for(Integer[] col : it.getColonnaStato()){
                if(col[0]<startN) startN = col[0];
                if(col[0]>endN) endN = col[0];
            }
        }
        if(endN-startN == 0) return null;
        ArrayList<PostiSala> res = new ArrayList<>();
        for(char c=startC; c<=endC; c++){
            int stato = Posto.INESISTENTE_STATUS;
            PostiSala posto = new PostiSala();
            posto.setRiga(c);
            ArrayList<Integer[]> colonnaStato = new ArrayList<>();
            for(int col=startN; col<=endN; col++){
                Integer[] postoStato = new Integer[2];
                postoStato[0] = col;
                postoStato[1] = stato;
                colonnaStato.add(postoStato);
            }
            posto.setColonna(colonnaStato);
            res.add(posto);
        }
        
        for(PostiSala posto : incompleteList){
            char c = posto.getRiga();
            int indiceRiga = c - startC;
            System.out.println("indiceRiga: " + indiceRiga + " ; size: " + res.size());
            ArrayList<Integer[]> colonna = res.get(indiceRiga).getColonnaStato();
            for(Integer[] postoStato : posto.getColonnaStato()){
                int indiceColonna = postoStato[0] - startN;
                Integer[] postoStatoN = colonna.get(indiceColonna);
                //se tutto è ok postoStatoN[0] == postoStato[0] deve essere true;
                postoStatoN[1] = postoStato[1];
                
            }
        }
        return res;
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
