/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operateur;

import com.mycompany.cvrp.solution.Tournee;

/**
 *
 * @author tryla
 */
public abstract class Operateur {
    protected Tournee tournee;
    protected int deltaCout;

    public Operateur() {
        this.tournee = null;
        this.deltaCout = Integer.MAX_VALUE;
    }

    public Operateur(Tournee tournee) {
        this();
        this.tournee = tournee;
    }
    
    public int getDeltaCout(){
        return this.deltaCout;
    }

    public Tournee getTournee() {
        return tournee;
    }
    
    
    
    public boolean isMouvementRealisable(){
        return this.getDeltaCout() != Integer.MAX_VALUE;
    }
    
    public boolean isMeilleur(Operateur op){
        if(op == null) return true;
        if(this.getDeltaCout() >= op.getDeltaCout())
            return false;
        return true;
    }
    
    public boolean doMouvementIfRealisable(){
        if(!this.isMouvementRealisable())
            return false;
        this.doMouvement();
        return true;
    }
    
    public boolean isMouvementAmeliorant(){
        if(this.getDeltaCout() < 0) return true;
        return false;
    }
    
    protected abstract int evalDeltaCout();
    protected abstract boolean doMouvement();

    @Override
    public String toString() {
        return "Operateur{" + "tournee=" + tournee + ", \ndeltaCout=" + deltaCout + '}';
    }    
}
