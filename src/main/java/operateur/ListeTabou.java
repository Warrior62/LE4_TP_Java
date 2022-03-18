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
    protected final int capacity = 80;
    protected LinkedBlockingQueue<OperateurLocal> liste;
    protected int deltaAspiration;
    
    public static ListeTabou getInstance(){
        if(instance == null)
            instance = new ListeTabou();
        return instance;
    }

    private ListeTabou() {
        this.liste = new LinkedBlockingQueue(this.capacity);
        this.deltaAspiration = 0;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setDeltaAspiration(int deltaAspiration) {
        this.deltaAspiration = deltaAspiration;
    }
    
    public boolean add(OperateurLocal operateur){
        if(operateur == null) return false;
        if(this.liste.size() >= this.capacity)
            this.liste.poll();
        return this.liste.offer(operateur);
    }
    
    public void vider(){
        this.liste.clear();
    }
    
    public boolean isTabou(OperateurLocal operateur){
        if(operateur == null) return false;
        if(operateur.getDeltaCout() < this.deltaAspiration) return false;
        for(OperateurLocal ol : this.liste)
            if(ol.isTabou(operateur))
                return true;
        return false;
    }

    @Override
    public String toString() {
        return "ListeTabou{" + "capacity=" + instance.capacity + '}';
    }
}
