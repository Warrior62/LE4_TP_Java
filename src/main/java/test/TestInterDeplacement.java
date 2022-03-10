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
import operateur.InterDeplacement;
import operateur.OperateurLocal;
import operateur.TypeOperateurLocal;

/**
 *
 * @author tryla
 */
public class TestInterDeplacement {
    public static void main(String[] args) {
        int id = 1;
        Depot d = new Depot(id++, 0, 0);
        Instance inst = new Instance("test", 100, d);
        Client c1 = new Client(10, id++, 5, 0);
        Client c2 = new Client(10, id++, 10, 0);
        Client c3 = new Client(10, id++, 10, 0);
        Client c4 = new Client(10, id++, 15, 0);
        Client c5 = new Client(60, id++, 0, 10);
        Client c6 = new Client(10, id++, 10, 10);
         Client c7 = new Client(10, id++, 15, 10);
        inst.ajouterClient(c1);
        inst.ajouterClient(c2);
        inst.ajouterClient(c3);
        inst.ajouterClient(c4);
        inst.ajouterClient(c5);
        inst.ajouterClient(c6);
        inst.ajouterClient(c7);
        Tournee t = new Tournee(inst);
        t.ajouterClient(c1);
        t.ajouterClient(c2);
        t.ajouterClient(c6);
        t.ajouterClient(c3);
        t.ajouterClient(c4);
        Tournee u = new Tournee(inst);
        u.ajouterClient(c5);
        u.ajouterClient(c7);



        OperateurLocal opDefault = OperateurLocal.getOperateur(TypeOperateurLocal.INTER_DEPLACEMENT);

        OperateurLocal opInter1 = OperateurLocal.getOperateurInter(
                TypeOperateurLocal.INTER_DEPLACEMENT, t, u, 0, 2); //true

        OperateurLocal opInter2 = OperateurLocal.getOperateurInter(
                TypeOperateurLocal.INTER_DEPLACEMENT, u, t, 0, 2);  //false excès capacité

        OperateurLocal opInter3 = OperateurLocal.getOperateurInter(
                TypeOperateurLocal.INTER_DEPLACEMENT, t, u, 2, 1);  //true

        OperateurLocal opInter4 = OperateurLocal.getOperateurInter(
                TypeOperateurLocal.INTER_DEPLACEMENT, t, u, 4, 2);  //true


        System.out.println(opDefault);//Infini
        System.out.println(opInter1); //>0
        System.out.println(opInter2); //Infini
        System.out.println(opInter3); //-20
        System.out.println(opInter4); //-3

        System.out.println(opInter1.isMouvementRealisable()); //true
        System.out.println(opInter2.isMouvementRealisable()); //false
        System.out.println(opInter3.isMouvementRealisable()); //true
        System.out.println(opInter4.isMouvementRealisable()); //true

        System.out.println(opInter1.isMouvementAmeliorant()); //false
        System.out.println(opInter3.isMouvementAmeliorant()); //true
        System.out.println(opInter4.isMouvementAmeliorant()); //true

        System.out.println(opInter1.isMeilleur(opInter3)); //false
        System.out.println(opInter3.isMeilleur(opInter1)); //true
        System.out.println(opInter3.isMeilleur(opInter4)); //true
        System.out.println(opInter4.isMeilleur(opInter3)); //false
        
        System.out.println(t.getMeilleurOperateurInter(TypeOperateurLocal.INTER_DEPLACEMENT, u));
        System.out.println(u.getMeilleurOperateurInter(TypeOperateurLocal.INTER_DEPLACEMENT, t));
        
        System.out.println(opInter2.doMouvementIfRealisable()); // false
        System.out.println(opInter3.doMouvementIfRealisable()); // true
    }
}
