/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import Solveur.InsertionSimple;
import com.mycompany.cvrp.instance.Instance;
import com.mycompany.cvrp.instance.reseau.Client;
import com.mycompany.cvrp.instance.reseau.Depot;
import com.mycompany.cvrp.solution.Tournee;
import operateur.InsertionClient;

/**
 *
 * @author tryla
 */
public class TestMeilleurInsertion {
    public static void main(String[] args) {
        int id=1;
        Depot d = new Depot(id++, 0, 0);
        Instance inst = new Instance("test", 100, d);
        Client c1 = new Client(10, id++, 5, 0);
        Client c2 = new Client(10, id++, 10, 0);
        Client c3 = new Client(10, id++, 20, 0);
        Client cIns = new Client(10, id++, 15, 0);
        inst.ajouterClient(c1);
        inst.ajouterClient(c2);
        inst.ajouterClient(c3);
        inst.ajouterClient(cIns);
        Tournee t = new Tournee(inst);
        t.ajouterClient(c1);
        t.ajouterClient(c2);
        t.ajouterClient(c3);
        
        System.out.println(t.check());
        
        for(int i=-1; i<5; i++)
            System.out.println("Cout d'insertion cIns pos°" + i + " = " + t.deltaCoutInsertion(i, cIns));
        
        
        InsertionClient ic = new InsertionClient(t, cIns, -1);
        InsertionClient ic0 = new InsertionClient(t, cIns, 0);
        InsertionClient ic1 = new InsertionClient(t, cIns, 1);
        InsertionClient ic2 = new InsertionClient(t, cIns, 2);
        InsertionClient ic3 = new InsertionClient(t, cIns, 3);
        InsertionClient ic4 = new InsertionClient(t, cIns, 4);

        System.out.println(ic);
        System.out.println(ic0);
        System.out.println(ic1);
        System.out.println(ic2);
        System.out.println(ic3);
        System.out.println(ic4);

        System.out.println("is realisable " + ic.isMouvementRealisable());
        System.out.println("is realisable " + ic0.isMouvementRealisable());
        System.out.println("is realisable " + ic1.isMouvementRealisable());
        System.out.println("is realisable " + ic2.isMouvementRealisable());
        System.out.println("is realisable " + ic3.isMouvementRealisable());
        System.out.println("is realisable " + ic4.isMouvementRealisable());


        /*System.out.println("is meilleur " + ic1.isMeilleur(ic));
        System.out.println("is meilleur " + ic2.isMeilleur(ic1));
        System.out.println("is meilleur " + ic3.isMeilleur(ic2));
        System.out.println("is meilleur " + ic4.isMeilleur(ic3));*/
        
        System.out.println("meilleure insertion : " + t.getMeilleureInsertion(cIns)); // pos = 2
        
        
        System.out.println("insertion ? " + ic.doMouvementIfRealisable()); // false
        System.out.println("insertion ? " + ic2.doMouvementIfRealisable()); // true
        System.out.println(t);
    }
}
