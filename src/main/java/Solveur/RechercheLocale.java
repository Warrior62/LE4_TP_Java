/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Solveur;

import com.mycompany.cvrp.instance.Instance;
import com.mycompany.cvrp.io.InstanceReader;
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
        return this.solveur.getNom() + " : RechercheLocale";
    }

    @Override
    public Solution solve(Instance instance) {
        Solution s = this.solveur.solve(instance);
        boolean improve = true;
        while(improve){
            improve = false;
            operateur.OperateurLocal best = s.getMeilleurOperateurLocal(TypeOperateurLocal.INTER_DEPLACEMENT);
            //if(best != null){
                if(best.isMouvementAmeliorant()){
                    s.doMouvementRechercheLocale(best);
                    improve = true;
                }
            //}
        }
        return s;
    }
    
    public static void main(String[] args) {
        InstanceReader reader;
        try {
            reader = new InstanceReader("instances/A-n32-k5.vrp");
            Instance i = reader.readInstance();
            RechercheLocale rl = new RechercheLocale();
            Solution s1 = rl.solve(i);
            System.out.println("s1 check : " + s1.check());
        } catch(Exception e){
            System.out.println(e);
        }
    }
    
}
