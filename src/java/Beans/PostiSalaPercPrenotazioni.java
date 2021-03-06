/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.ArrayList;


public class PostiSalaPercPrenotazioni extends PostiSala{
    public static final int percPrenIndex = PostiSala.size;
    protected static final int size = PostiSala.size+1;
    
    private Number[] setArray(int idPosto, int colonna, int stato, double percPren){
        Number[] array = new Number[size];
        array[idPostoIndex] = idPosto;
        array[columnIndex] = colonna;
        array[statoIndex] = stato;
        array[percPrenIndex] = percPren;
        return array;
    }
    
    /**
     * aggiunge un posto con percentuale di prenotazioni 0.
     * @param idPosto
     * @param colonna
     * @param stato 
     */
    @Override
     public void addNewPosto(int idPosto, int colonna, int stato){
        this.addNewPosto(idPosto, colonna, stato, 0);
    }
    
     /**
      * aggiunge un posto.
      * @param idPosto
      * @param colonna
      * @param stato
      * @param percPren 
      */
    public void addNewPosto(int idPosto, int colonna, int stato, double percPren){
        if(colonnaStato == null)
            colonnaStato = new ArrayList<>();
        colonnaStato.add(setArray(idPosto, colonna, stato, percPren));
    }
    
    /**
     * modifica un posto esistente, setta la percentuale a 0
     * @param idPosto
     * @param colonna
     * @param stato
     * @param index
     * @return 
     */
    @Override
    public boolean setPosto(int idPosto, int colonna, int stato, int index){
        return this.setPosto(idPosto, colonna, stato, index, 0);
    }
    
    /**
     * modifica un posto esistente
     * @param idPosto
     * @param colonna
     * @param stato
     * @param index
     * @param percPren
     * @return 
     */
    public boolean setPosto(int idPosto, int colonna, int stato, int index, double percPren){
        boolean res = index < getSize();
        if(res)
            colonnaStato.set(index, setArray(idPosto, colonna, stato, percPren));
        return res;
    }
    
    public double getPrecentualePrenotazioni(int index){
        return (double)colonnaStato.get(index)[percPrenIndex];
    }
    
    public static ArrayList<PostiSalaPercPrenotazioni> normalizzaPercentuali(ArrayList<PostiSalaPercPrenotazioni> sala){
        double max=0;
        double min=1;
        for(PostiSalaPercPrenotazioni fila : sala){
            for(int i = 0; i < fila.getSize(); i++){
                double perc = fila.getPrecentualePrenotazioni(i);
                if(perc > max)
                    max = perc;
                else if(perc < min)
                    min = perc;
            }
        }
        for(PostiSalaPercPrenotazioni fila : sala){
            for(int i = 0; i < fila.getSize(); i++){
                double perc = fila.getPrecentualePrenotazioni(i);
                if(max != min)
                    perc = (fila.getPrecentualePrenotazioni(i) - min)/(max-min);
                fila.setPosto(fila.getIdPosto(i), fila.getColonna(i), fila.getStato(i), i, perc);
            }
        }
        return sala;
    }
}
