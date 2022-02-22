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
import java.util.LinkedList;
import operateur.InsertionClient;
import operateur.Operateur;

/**
 *
 * @author tryla
 */
public class MeilleureInsertion implements Solveur{

    @Override
    public String getNom() {
        return "MeilleureInsertion";
    }

    @Override
    public Solution solve(Instance instance) {
        LinkedList<Client> clients = instance.getClients();
        Solution s = new Solution(instance);
        InsertionClient best = null;
        while(!clients.isEmpty()){
            for(Client c : clients)
                best = s.getMeilleureInsertion(c);
            if(s.doInsertion(best)){
                clients.remove(best.getClient());
            }
            else{
                s.ajouterClientDansNouvelleTournee(clients.getFirst());
                clients.remove(clients.getFirst());
            }
        }
        return s;
    }
    
    public static void main(String[] args) {
        InstanceReader reader;
        try {
            reader = new InstanceReader("instances/A-n32-k5.vrp");
            Instance i = reader.readInstance();
            MeilleureInsertion mi = new MeilleureInsertion();
            Solution s1 = mi.solve(i);
            System.out.println("s1 check : " + s1.check());
        } catch(Exception e){
            System.out.println(e);
        }
    }
    
}
