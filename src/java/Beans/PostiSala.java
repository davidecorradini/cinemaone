/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Beans;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;


public class PostiSala {
    protected static final int size = 3;
    protected static final int idPostoIndex = 0;
    protected static final int columnIndex = 1;
    protected static final int statoIndex = 2;
    protected char riga;
    protected ArrayList<Number[]> colonnaStato; //in 0 l'idPosto, in 1 il numero di colonna e in 2 lo stato
      
    public Number[] getColonnaStato(int index){
        return colonnaStato.get(index);
    }
    
    private Integer[] setArray(int idPosto, int colonna, int stato){
        Integer[] array = new Integer[size];
        array[idPostoIndex] = idPosto;
        array[columnIndex] = colonna;
        array[statoIndex] = stato;
        return array;
    }
    
    /**
     * @return the riga
     */
    public char getRiga() {
        return riga;
    }
    
    /**
     * @param riga the riga to set
     */
    public void setRiga(char riga) {
        this.riga = riga;
    }
    
    public void addNewPosto(int idPosto, int colonna, int stato){
        if(colonnaStato == null)
            colonnaStato = new ArrayList<>();
        colonnaStato.add(setArray(idPosto, colonna, stato));
    }
    
     public void addNewPosto(Number[] newStato){
        if(colonnaStato == null)
            colonnaStato = new ArrayList<>();
        colonnaStato.add(newStato);
    }
    
    public int getSize(){
        if(colonnaStato == null) return 0;
        return colonnaStato.size();
    }
    /**
     * modifica un posto esistente
     * @param idPosto
     * @param colonna
     * @param stato
     * @param index
     * @return
     */
    public boolean setPosto(int idPosto, int colonna, int stato, int index){
        boolean res = index < getSize();
        if(res)
            colonnaStato.set(index, setArray(idPosto, colonna, stato));
        return res;
    }
    /**
     * ritorna l'id del posto ad un dato indice.
     * @param index
     * @return
     */
    public int getIdPosto(int index){
        return (int)colonnaStato.get(index)[idPostoIndex];
    }
    
    /**
     * ritorna la colonna in cui si trova il posto ad un dato indice.
     * @param index
     * @return
     */
    public int getColonna(int index){
        return (int)colonnaStato.get(index)[columnIndex];
    }
    
    /**
     * ritorna lo stato del posto ad un dato indice.
     * @param index
     * @return
     */
    public int getStato(int index){
        return (int)colonnaStato.get(index)[statoIndex];
    }
    
     public static<T extends PostiSala> ArrayList<T> formattaInfoSala(ArrayList<T> incompleteList, Class<T> paramClass){
        if(incompleteList.isEmpty()) return null;
        int startN = Integer.MAX_VALUE, endN = Integer.MIN_VALUE;
        for(PostiSala postiSala : incompleteList){
            for(int i=0; i<postiSala.getSize(); i++){
                int col = postiSala.getColonna(i);
                if(col < startN) startN = col;
                if(col > endN) endN = col;
            }
        }
         System.out.println("primo getSize passato");
        if(endN-startN == 0) return null;
        ArrayList<T> res = new ArrayList<>();
        int indexN = 0;
        //startC è la prossima riga che deve essere inserita
        char startC = incompleteList.get(0).getRiga(); 
        for (T incompleteList1 : incompleteList) {
            System.out.println("inizio ciclo su incompleteList");
            char endC = incompleteList1.getRiga(); //prossima riga già presente.
            //inserisci righe mancanti all'incomplete list
            for(char nextRiga = startC; nextRiga < endC; nextRiga++){ //aggiungi le righe che mancano interamente.
                T riga;
                try {
                    try {
                        riga = paramClass.getConstructor().newInstance();
                    } catch (NoSuchMethodException | SecurityException ex) {
                        throw new RuntimeException("class: " + paramClass + " must implement default constructor");
                    }
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    throw new RuntimeException("can NOT create a new instance of: " + paramClass);
                }
                riga.setRiga(nextRiga);
                for(int nextColonna = startN; nextColonna <= endN; nextColonna++) //inserisce tutte le colonna
                    riga.addNewPosto(-1, nextColonna, Posto.INESISTENTE_STATUS);
                res.add(riga); //aggiungo la colonna.
                System.out.println("aggiunta riga: " + riga.getRiga());
            }
            System.out.println("fine creazione righe non esistenti: nextRiga: " + endC);
            //completa la riga endC dell'incompleteList
            T incompleteC = incompleteList1;
            T colonna;
            try {
                try {
                    colonna = paramClass.getConstructor().newInstance();
                } catch (NoSuchMethodException | SecurityException ex) {
                    throw new RuntimeException("class: " + paramClass + " must implement default constructor");
                }
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                throw new RuntimeException("can NOT create a new instance of: " + paramClass);
            }
            colonna.setRiga(endC);
            //copia i posti da incompleteC a colonna.
            System.out.println("\n\nvado col secondo: inCompleteC.size() = " + incompleteC.getSize());
            for(int indiceColonna = 0; indiceColonna < incompleteC.getSize(); indiceColonna++){ 
                System.out.println("indiceColonna = " + indiceColonna);
                //aggiungo i posti nella colonna non presenti in incompleteC
                for(int nextColonna = startN; nextColonna < incompleteC.getColonna(indiceColonna); nextColonna++){
                    colonna.addNewPosto(-1, nextColonna, Posto.INESISTENTE_STATUS);
                }
                //copio quelli il posto con colonna incompleteC.getColonna(indiceColonna);
               
                Number[] stato = incompleteC.getColonnaStato(indiceColonna);
                colonna.addNewPosto(stato); //copio il contenuto.
                startN = incompleteC.getColonna(indiceColonna)+1;
            }
            res.add(colonna);
            startC = endC++;
        }
        
        for(T obj : res){
            System.out.println("riga: " + obj.getRiga());
            for(int i=0; i<obj.getSize(); i++){
                System.out.println("\tcolonna: " + obj.getColonna(i));
            }
        }
        return res;
     }
     
    /*
    public static<T extends PostiSala> ArrayList<T> formattaInfoSala(ArrayList<T> incompleteList, Class<T> paramClass){
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
        ArrayList<T> res = new ArrayList<>();
        //riempimento a vuoto, inserisco tutti i posti come inesistenti con un id fantoccio -1;
        
        for(char c=startC; c<=endC; c++){
            int stato = Posto.INESISTENTE_STATUS;
            T posto;
            
            try {
                try {
                    posto = paramClass.getConstructor().newInstance();
                } catch (NoSuchMethodException | SecurityException ex) {
                    throw new RuntimeException("class: " + paramClass + " must implement default constructor");
                }
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                throw new RuntimeException("can NOT create a new instance of: " + paramClass);
            }
            
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
                    throw new RuntimeException("errore nell'inserimento dei posti invisibili");
            }
        }
        return res;
    }
    */
}
