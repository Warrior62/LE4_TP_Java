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
public abstract class OperateurIntraTournee extends OperateurLocal{
   
    public OperateurIntraTournee() {
        super();
    }
    
    public OperateurIntraTournee(Tournee tournee, int positionI, int positionJ) {
        super(tournee, positionI, positionJ);
        this.deltaCout = this.evalDeltaCout();
    }    
}
