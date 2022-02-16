/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Solveur;

import com.mycompany.cvrp.instance.Instance;
import com.mycompany.cvrp.instance.reseau.Client;
import com.mycompany.cvrp.io.InstanceReader;
import com.mycompany.cvrp.solution.Solution;
import java.util.List;

/**
 *
 * @author tryla
 */
public class InsertionSimple implements Solveur{

    @Override
    public String getNom() {
        return "Insertion simple";
    }

    @Override
    public Solution solve(Instance instance) {
        Solution s = new Solution(instance);
        for(Client c : instance.getClients()){
            boolean affecte = false;
            if(s.ajouterClientDansTourneeExistance(c))
                affecte = true;
            if(!affecte)
                s.ajouterClientDansNouvelleTournee(c);
        }        
        return s;
    }
    
    
    public static void main(String[] args) {
        InstanceReader reader;
        try {
            reader = new InstanceReader("instances/A-n32-k5.vrp");
            Instance i = reader.readInstance();
            InsertionSimple is = new InsertionSimple();
            Solution s1 = is.solve(i);
            System.out.println("s1 : " + s1.toString() + "\n\tcheck : " + s1.check());
        } catch(Exception e){
            System.out.println("ERROR InsertionSimple");
        }
    }
    
}
