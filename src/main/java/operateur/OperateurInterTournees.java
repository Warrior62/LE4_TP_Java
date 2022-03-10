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
public abstract class OperateurInterTournees extends OperateurLocal{
    protected Tournee autreTournee;
    protected int deltaCoutTournee, deltaCoutAutreTournee;

    public OperateurInterTournees() {
        super();
        this.autreTournee = null;
    }

    public OperateurInterTournees(Tournee tournee, Tournee autreTournee, int positionI, int positionJ) {
        super(tournee, positionI, positionJ);
        this.autreTournee = autreTournee;
        this.clientJ = autreTournee.getClient(positionJ);
        this.deltaCout = this.evalDeltaCout();
    }
    
    protected abstract int evalDeltaCoutTournee();
    protected abstract int evalDeltaCoutAutreTournee();
    
    // Surcoût engendré par l'application de l'opérateur sur la solution
    @Override
    protected int evalDeltaCout() {
        this.deltaCoutTournee = this.evalDeltaCoutTournee();
        this.deltaCoutAutreTournee = this.evalDeltaCoutAutreTournee();
        if(this.deltaCoutTournee == Integer.MAX_VALUE || this.deltaCoutAutreTournee == Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        return this.deltaCoutTournee + this.deltaCoutAutreTournee;
    }

    public int getDeltaCoutTournee() {
        return deltaCoutTournee;
    }

    public int getDeltaCoutAutreTournee() {
        return deltaCoutAutreTournee;
    }
    
    

    @Override
    public String toString() {
        return "InterTournees" 
                + super.toString()
                + "\n\tautreTournee=" + autreTournee 
                + "\n\tdeltaCoutTournee=" + deltaCoutTournee 
                + "\n\tdeltaCoutAutreTournee=" + deltaCoutAutreTournee;
    }
}
