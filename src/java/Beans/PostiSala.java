/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Beans;

import java.util.ArrayList;


public class PostiSala {
    protected static final int size = 3;
    protected static final int idPostoIndex = 0;
    protected static final int columnIndex = 1;
    protected static final int statoIndex = 2;
    protected char riga;
    protected ArrayList<Number[]> colonnaStato; //in 0 l'idPosto, in 1 il numero di colonna e in 2 lo stato
    
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
    
}
