/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Solveur;

import com.mycompany.cvrp.instance.Instance;
import com.mycompany.cvrp.solution.Solution;
import operateur.TypeOperateurLocal;

/**
 *
 * @author tryla
 */
public class RechercheLocale implements Solveur{
    
    private Solveur solveur;

    public RechercheLocale() {
        this.solveur = new InsertionSimple();
    }
    
    public RechercheLocale(Solveur solveur) {
        this.solveur = solveur;
    }

    @Override
    public String getNom() {
        return "RechercheLocale";
    }

    @Override
    public Solution solve(Instance instance) {
        Solution s = this.solveur.solve(instance);
        boolean improve = true;
        while(improve){
            improve = false;
            operateur.OperateurLocal best = s.getMeilleurOperateurLocal(TypeOperateurLocal.INTRA_DEPLACEMENT);
            if(best.isMouvementAmeliorant()){
                s.doMouvementRechercheLocale(best);
                improve = true;
            }
        }
        return s;
    }
    
}
