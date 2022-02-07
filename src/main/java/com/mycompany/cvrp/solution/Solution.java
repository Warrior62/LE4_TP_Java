/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cvrp.solution;

import com.mycompany.cvrp.instance.Instance;
import com.mycompany.cvrp.instance.reseau.Client;
import com.mycompany.cvrp.io.InstanceReader;
import io.exception.ReaderException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tryla
 */
public class Solution {
    
    private Instance instance;
    private List<Tournee> listeTournees;
    // la somme des coûts des tournées
    private int coutTotal;

    public Solution(Instance instance) {
        this.instance = instance;
        this.listeTournees = new ArrayList<>();
    }

    public List<Tournee> getListeTournees() {
        return listeTournees;
    }

    public int getCoutTotal() {
        return coutTotal;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.instance);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Solution other = (Solution) obj;
        if (!Objects.equals(this.instance, other.instance)) {
            return false;
        }
        return true;
    }
    
    /**
     * ajoute un client à la solution dans une nouvelle tournée
     */
    public boolean ajouterClientDansNouvelleTournee(Client clientToAdd){
        Tournee t = new Tournee(this.instance);
        boolean added = t.ajouterClient(clientToAdd);
        if(added){
            this.coutTotal += t.getCoutTotal();
            this.listeTournees.add(t);
        }
        return added;
    }
    
    /**
     * ajoute un client à la solution dans une tournée existante
     * @note on peut parcourir les tournées et faire l’ajout dans la première tournée où c’est possible
     * @return indique si le client a pu être ajouté dans une tournée ou non
     */
    public boolean ajouterClientDansTourneeExistance(Client clientToAdd){
        int deltaCout = 0;
        for(Tournee t : this.getListeTournees()){
            deltaCout = t.getCoutTotal();
            if(t.ajouterClient(clientToAdd)){
                this.coutTotal += t.getCoutTotal() - deltaCout;
                return true;
            }
        }
        return false;
    }
    
    private boolean checkCoutTotal() {
        int testCoutTotal = 0;
        
        for(Tournee t : this.listeTournees){
            testCoutTotal += t.getCoutTotal();
        }
        System.out.println("Check Cout total : " + (testCoutTotal == this.getCoutTotal()));
        return testCoutTotal == this.getCoutTotal();
    }

    private boolean checkToutesTourneesRealisables() {
        for(Tournee t : this.listeTournees){
            if(t.check() == false){
               return false; 
            } 
        }      
        System.out.println("Check tournées réalisables OK");
        return true;
    }

    private boolean checkUniciteClientTournee() {
        List<Client> clients = this.instance.getClients();
        for(Tournee t : this.listeTournees){
            System.out.println("Nb cli dans t : " + t.getClients().size());
            for(Client c : t.getClients().values()){
                if(!clients.remove(c)){
                    System.out.println("le client " + c.getId() + " pose problème");
                    return false;
                }
            }
        }
        if(!clients.isEmpty()){
            System.out.println("ERREUR UNICITE ISEMPTY");
            return false;
        } 
        System.out.println("Check unicité client tournée OK");
        return true;
    }
   
    /**
     * réalisable si coût total de la solution est correctement calculé
     * + toutes ses tournées sont réalisables
     * + tous les clients sont présents dans excatement 1 seule des tournées de la solution
     * indique si la solution est réalisable ou non
     * @return si c'est réalisable ou non
     */
    public boolean check(){
        return checkCoutTotal() && checkToutesTourneesRealisables() && checkUniciteClientTournee();
    }
    
    @Override
    public String toString() {
        String s = "Solution{" + "nomInstance=" + instance.getNom() + ", listeTournees=";
        for(Tournee t : listeTournees){
            s += "\t\n" + t.getInstance().getNom();
        }
        s += "\n\tcoutTotal=" + coutTotal + '}';
        return s;
    }
    
    
    public static void main(String[] args) {
        
        
        InstanceReader reader;
        try {
            reader = new InstanceReader("instances/A-n32-k5.vrp");
            Instance i = reader.readInstance();
            Tournee t = new Tournee(i);
            Solution s = new Solution(i);
            Client c1 = new Client(4, 5, 5, 5);
            //s.listeTournees.add(t);
            //t.ajouterClient(c1);
            s.ajouterClientDansNouvelleTournee(c1);
            System.out.println("Size tournees dans solution : " + s.listeTournees.size());
            //System.out.println("Check tournee : " + t.check());
            System.out.println("Check solution : " + s.check());
            
            System.out.println(s.toString());
        } catch (Exception ex) {
            Logger.getLogger(Instance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
