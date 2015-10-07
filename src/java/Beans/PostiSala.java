/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Beans;

import java.util.ArrayList;

/**
 *
 * @author enrico
 */
public class PostiSala {
    private static final int idPostoIndex = 0;
    private static final int columnIndex = 1;
    private static final int statoIndex = 2;
    private char riga;
    private ArrayList<Integer[]> colonnaStato; //in 0 l'idPosto, in 1 il numero di colonna e in 2 lo stato
    
    private Integer[] setArray(int idPosto, int colonna, int stato){
        Integer[] array = new Integer[3];
        array[idPostoIndex] = idPosto;
        array[columnIndex] = colonna;
        array[statoIndex] = statoIndex;
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
    
    public void setColonna(ArrayList<Integer[]> colonnaStato){
        this.colonnaStato = colonnaStato;
    }
    
    public ArrayList<Integer[]> getColonnaStato(){
        return this.colonnaStato;
    }
    
    public void addNewPosto(int idPosto, int colonna, int stato){
        if(colonnaStato == null)
            colonnaStato = new ArrayList<>();
        colonnaStato.add(setArray(idPosto, colonna, stato));
    }
    
    public int getSize(){
        return colonnaStato.size();
    }
    public boolean setPosto(int idPosto, int colonna, int stato, int index){
        if(index >= getSize())
            return false;
        colonnaStato.set(index, setArray(idPosto, colonna, stato));
        return true;
    }
    /**
     * ritorna l'id del posto ad un dato indice.
     * @param index
     * @return
     */
    public int getIdPosto(int index){
        return colonnaStato.get(index)[idPostoIndex];
    }
    
    /**
     * ritorna la colonna in cui si trova il posto ad un dato indice.
     * @param index
     * @return
     */
    public int getColonna(int index){
        return colonnaStato.get(index)[columnIndex];
    }
    
    /**
     * ritorna lo stato del posto ad un dato indice.
     * @param index
     * @return
     */
    public int getStato(int index){
        return colonnaStato.get(index)[statoIndex];
    }
    
}
