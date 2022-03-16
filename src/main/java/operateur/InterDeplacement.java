/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operateur;

import com.mycompany.cvrp.instance.reseau.Client;
import com.mycompany.cvrp.solution.Tournee;

/**
 *
 * @author tryla
 */
public class InterDeplacement extends OperateurInterTournees{

    public InterDeplacement() {
        super();
    }

    public InterDeplacement(Tournee tournee, Tournee autreTournee, int positionI, int positionJ) {
        super(tournee, autreTournee, positionI, positionJ);
    }   

    public Client getClientI() {
        return clientI;
    }

    public Client getClientJ() {
        return clientJ;
    }

    public int getPositionI() {
        return positionI;
    }

    public int getPositionJ() {
        return positionJ;
    }

    public Tournee getAutreTournee() {
        return autreTournee;
    }

    
    
    @Override
    protected int evalDeltaCoutTournee() {
        if(this.tournee == null) return Integer.MAX_VALUE;
        return this.tournee.deltaCoutSuppression(this.positionI);
    }

    @Override
    protected int evalDeltaCoutAutreTournee() {
        if(this.autreTournee == null) return Integer.MAX_VALUE;
        return this.autreTournee.deltaCoutInsertionInter(this.positionJ, this.clientI);
    }

    @Override
    protected boolean doMouvement() {
        return this.tournee.doDeplacement(this);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean isTabou(OperateurLocal operateur) {
        if(operateur == null)
            return false;
        if(operateur.tournee == null || operateur.clientI == null || operateur.clientJ == null) return false;
        boolean isInterDeplacement = operateur instanceof InterDeplacement;  
        boolean clientsDeplacesIdentiques = this.clientI.equals(operateur.clientI);
        return isInterDeplacement && clientsDeplacesIdentiques;
    }
    
    
}
