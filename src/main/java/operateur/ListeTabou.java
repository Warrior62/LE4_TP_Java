/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operateur;

import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author tryla
 */
public class ListeTabou{
    
    private static ListeTabou instance;
    private final int capacity = 80;
    private LinkedBlockingQueue<OperateurLocal> liste;
    private int deltaAspiration;
    
    public static ListeTabou getInstance(){
        if(instance == null)
            instance = new ListeTabou();
        return instance;
    }

    private ListeTabou() {
        this.liste = new LinkedBlockingQueue<OperateurLocal>(this.capacity);
        this.deltaAspiration = 0;
    }

    public int getCapacity() {
        return instance.capacity;
    }

    public void setDeltaAspiration(int deltaAspiration) {
        this.deltaAspiration = deltaAspiration;
    }
    
    public boolean add(OperateurLocal operateur){ 
        if(instance.liste.offer(operateur)){
            instance.liste.poll();
            instance.liste.offer(operateur);
            return false;
        }
        return true;
    }
    
    public void vider(){
        instance.liste.clear();
    }
    
    public boolean isTabou(OperateurLocal operateur){
        if(operateur.getDeltaCout() < this.deltaAspiration) return false;
        for(OperateurLocal ol : instance.liste)
            if(ol.isTabou(operateur))
                return true;
        return false;
    }

    @Override
    public String toString() {
        return "ListeTabou{" + "capacity=" + instance.capacity + '}';
    }
}
