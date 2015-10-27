/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Beans;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;


public class PostiSala {
    protected static final int size = 3;
    protected static final int idPostoIndex = 0;
    protected static final int columnIndex = 1;
    protected static final int statoIndex = 2;
    protected char riga;
    protected ArrayList<Number[]> colonnaStato; //in 0 l'idPosto, in 1 il numero di colonna e in 2 lo stato
    
    static class ParameterClass<T extends PostiSala>{
        public T getInstance(){
            ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
            Class<T> type = (Class<T>) superClass.getActualTypeArguments()[0];
            T instance;
            try {
                instance = type.newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                throw new RuntimeException("the class: " + type.toString() + " must implement a default constructor");
            }
            
            return instance;
        }
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
    
    public int getSize(){
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
    
    public static<T extends PostiSala> ArrayList<T> formattaInfoSala(ArrayList<T> incompleteList){
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
        
        ParameterClass<T> tInstances = new ParameterClass<>();
        for(char c=startC; c<=endC; c++){
            int stato = Posto.INESISTENTE_STATUS;
            T posto = tInstances.getInstance();
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
}
