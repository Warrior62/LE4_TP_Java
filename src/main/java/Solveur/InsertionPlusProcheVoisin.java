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

/**
 *
 * @author tryla
 */
public class InsertionPlusProcheVoisin implements Solveur {

    @Override
    public String getNom() {
        return "Insertion plus proche voisin";
    }

    @Override
    public Solution solve(Instance instance) {
        LinkedList<Client> clients = instance.getClients();
        Solution s = new Solution(instance);
        Client premierClient = clients.getFirst();
        Client closestClient = null;
        while(!clients.isEmpty()){
            boolean affecte = false;
            if(s.ajouterClientDansDerniereTournee(premierClient))
                affecte = true;
            if(!affecte)
                s.ajouterClientDansNouvelleTournee(premierClient);
            clients.remove(premierClient);
            if(!clients.isEmpty()){
                closestClient = clientLePlusProche(clients, premierClient);
                System.out.println("cc: " + closestClient.toString());
                premierClient = closestClient;                
            }
        }
        return s;
    }
    
    public Client clientLePlusProche(LinkedList<Client> clients, Client c){
        int min = Integer.MAX_VALUE, index = 0;
        Client lePlusProche = null;
        for(int i=0; i<clients.size(); i++){
            if(c.getCoutVers(clients.get(i)) < min){
                min = c.getCoutVers(clients.get(i));
                index = i;
            }
        }
        return clients.get(index);
    }
    
    public static void main(String[] args) {
        InstanceReader reader;
        try {
            reader = new InstanceReader("instances/A-n32-k5.vrp");
            Instance i = reader.readInstance();
            InsertionPlusProcheVoisin is = new InsertionPlusProcheVoisin();
            Solution s1 = is.solve(i);
            System.out.println("s1 check : " + s1.check());
        } catch(Exception e){
            System.out.println(e);
        }
    }
    
}
