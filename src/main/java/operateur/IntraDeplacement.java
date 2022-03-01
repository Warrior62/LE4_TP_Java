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
public class IntraDeplacement extends OperateurIntraTournee{

    public IntraDeplacement() {
        super();
    }

    public IntraDeplacement(Tournee tournee, int positionI, int positionJ) {
        super(tournee, positionI, positionJ);
    }

    
    
    @Override
    protected int evalDeltaCout() {
        if(this.tournee == null) return Integer.MAX_VALUE;
        return this.tournee.deltaCoutDeplacement(this.positionI, this.positionJ);
    }

    @Override
    protected boolean doMouvement() {
        return false;
    }  

    @Override
    public String toString() {
        return super.toString();
    }
}
