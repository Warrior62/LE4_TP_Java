/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Solveur;

import com.mycompany.cvrp.instance.Instance;
import com.mycompany.cvrp.solution.Solution;
import java.util.List;
import operateur.IntraDeplacement;
import operateur.ListeTabou;

/**
 *
 * @author tryla
 */
public class RechercheTabou implements Solveur{

    private Solveur solveur;
    
    @Override
    public String getNom() {
        return this.solveur.getNom() + "RechercheTabou";
    }

    public RechercheTabou(Solveur solveur) {
        this.solveur = solveur;
    }

    @Override
    public Solution solve(Instance instance) {
        
        Solution s = this.solveur.solve(instance);
        Solution sBest = new Solution(s);
        operateur.TypeOperateurLocal[] operateursListe = {operateur.TypeOperateurLocal.INTER_DEPLACEMENT, operateur.TypeOperateurLocal.INTRA_DEPLACEMENT};
        int nbIterMax = 10000, nbIterSansAmelioration = 0;
        ListeTabou.getInstance().vider();
        while(nbIterSansAmelioration < nbIterMax){
            operateur.OperateurLocal best = new IntraDeplacement();
            ListeTabou.getInstance().setDeltaAspiration(sBest.getCoutTotal() - s.getCoutTotal());
            for(operateur.TypeOperateurLocal o : operateursListe){
                operateur.OperateurLocal op = s.getMeilleurOperateurLocal(o);
                if(op.isMeilleur(best)) best = op;
            }            
            if(s.doMouvementRechercheLocale(best))
                ListeTabou.getInstance().add(best);
            if(s.getCoutTotal() < sBest.getCoutTotal()){
                sBest = new Solution(s);
                nbIterSansAmelioration = 0;
            }
            else {
                nbIterSansAmelioration += 1;
            }
        }
        return sBest;
    }
    
}
