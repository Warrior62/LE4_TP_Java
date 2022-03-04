/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.mycompany.cvrp.instance.Instance;
import com.mycompany.cvrp.instance.reseau.Client;
import com.mycompany.cvrp.instance.reseau.Depot;
import com.mycompany.cvrp.solution.Tournee;
import operateur.IntraDeplacement;
import operateur.OperateurIntraTournee;
import operateur.OperateurLocal;
import operateur.TypeOperateurLocal;

/**
 *
 * @author tryla
 */
public class TestIntraDeplacement {
    public static void main(String[] args) {
        int id=1;
        Depot d = new Depot(id++, 0, 0);
        Instance inst = new Instance("test", 100, d);
        Client c1 = new Client(10, id++, 5, 0);
        Client c2 = new Client(10, id++, 10, 0);
        Client c3 = new Client(10, id++, 40, 0);
        Client c4 = new Client(10, id++, 5, 0);
        Client c5 = new Client(10, id++, 20, 0);
        inst.ajouterClient(c1);
        inst.ajouterClient(c2);
        inst.ajouterClient(c3);
        inst.ajouterClient(c4);
        inst.ajouterClient(c5);
        Tournee t = new Tournee(inst);
        t.ajouterClient(c1);
        t.ajouterClient(c2);
        t.ajouterClient(c3);
        t.ajouterClient(c4);
        t.ajouterClient(c5);
        System.out.println("\tcout deplacement entre 2 et 4 : " + t.deltaCoutDeplacement(2, 4)); // -20
        System.out.println("\tcout deplacement entre 2 et 3: " + t.deltaCoutDeplacement(2, 3)); // infini
        
        IntraDeplacement ind = (IntraDeplacement) OperateurLocal.getOperateurIntra(TypeOperateurLocal.INTRA_DEPLACEMENT, t, 2, 4);       
        System.out.println("ind entre 2 et 4 : " + ind.getDeltaCout()); // -20
        
        IntraDeplacement ind1 = (IntraDeplacement) t.getMeilleurOperateurIntra(TypeOperateurLocal.INTRA_DEPLACEMENT);       
        System.out.println("meilleur opérateur de déplacement : " + ind1.toString());
        
        if(ind1.isMeilleur(ind))
            System.out.println("ind1 meilleur que ind");
        
        IntraDeplacement ind_impossible = (IntraDeplacement) OperateurLocal.getOperateurIntra(TypeOperateurLocal.INTRA_DEPLACEMENT, t, 2, 3);
        System.out.println("ind_impossible mouvement : " + ind_impossible.doMouvementIfRealisable());
        System.out.println("ind_possible mouvement : " + ind.doMouvementIfRealisable());
    }
}
