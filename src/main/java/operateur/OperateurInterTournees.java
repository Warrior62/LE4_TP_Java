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
    private Tournee autreTournee;
    private int deltaCoutTournee, deltaCoutAutreTournee;

    public OperateurInterTournees() {
        super();
    }

    public OperateurInterTournees(Tournee autreTournee, Tournee tournee, int positionI, int positionJ) {
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
        if(this.getDeltaCoutAutreTournee() == Integer.MAX_VALUE || this.getDeltaCoutTournee() == Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        return this.getDeltaCoutTournee() + this.getDeltaCoutAutreTournee();
    }

    public int getDeltaCoutTournee() {
        return deltaCoutTournee;
    }

    public int getDeltaCoutAutreTournee() {
        return deltaCoutAutreTournee;
    }
    
    

    @Override
    public String toString() {
        return "OperateurInterTournees{" + "autreTournee=" + autreTournee + ", deltaCoutTournee=" + deltaCoutTournee + ", deltaCoutAutreTournee=" + deltaCoutAutreTournee + '}';
    }
}
