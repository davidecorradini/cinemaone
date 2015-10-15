/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Servlets;

import Beans.InfoPrenotazione;
import Beans.PostiSala;
import Beans.Posto;
import Beans.Prezzo;
import Database.DBManager;
import Database.InfoPrenotazioneQueries;
import Database.PostiSalaQueries;
import Database.PrezzoQueries;
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
        int idSpettacolo = Integer.parseInt(request.getParameter("idspettacolo"));
        InfoPrenotazione infoPrenotazione = null;
        ArrayList<PostiSala> postiSala = null;
        ArrayList<Prezzo> prezzi = null;
        try {
            InfoPrenotazioneQueries infoPrenQ = new InfoPrenotazioneQueries(manager);
            PostiSalaQueries postiSalaQ = new PostiSalaQueries(manager);
            PrezzoQueries prezziQ = new PrezzoQueries(manager);
            infoPrenotazione = infoPrenQ.getInfoPrenotazione(idSpettacolo);
            postiSala = postiSalaQ.getAllPosti(infoPrenotazione.getSala().getIdSala());
            prezzi = prezziQ.getAllPrezzi();
        } catch (SQLException ex){
            request.setAttribute("error", "impossibile caricare la pagina, interrogazione al database fallita");
            getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        } 
        postiSala = formattaInfoSala(postiSala);
        request.setAttribute("infoPrenotazione", infoPrenotazione);
        request.setAttribute("postiSala", postiSala);
        request.setAttribute("prezzi", prezzi);
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
        for(PostiSala postiSala : incompleteList){
            for(int i=0; i<postiSala.getSize(); i++){
                int col = postiSala.getColonna(i);
                if(col < startN) startN = col;
                if(col > endN) endN = col;
            }
        }
        if(endN-startN == 0) return null;
        ArrayList<PostiSala> res = new ArrayList<>();
        //riempimento a vuoto, inserisco tutti i posti come inesistenti con un id fantoccio -1;
        for(char c=startC; c<=endC; c++){
            int stato = Posto.INESISTENTE_STATUS;
            PostiSala posto = new PostiSala();
            posto.setRiga(c);
            ArrayList<Integer[]> colonnaStato = new ArrayList<>();
            for(int col=startN; col<=endN; col++){
                posto.addNewPosto(-1, col, stato);
            }
            res.add(posto);
        }
        //vado a settare i posti esistenti come tali.
        for(PostiSala posto : incompleteList){
            char c = posto.getRiga();
            int indiceRiga = c - startC;
            PostiSala resPostoSala = res.get(indiceRiga);
            for(int i=0; i<posto.getSize(); i++){
               int indiceColonna = posto.getColonna(i) - startN;
               if(!resPostoSala.setPosto(posto.getIdPosto(i), posto.getColonna(i), posto.getStato(i), indiceColonna))
                   throw new RuntimeException("programming error nella Prenotazioni.java riga 102");
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
