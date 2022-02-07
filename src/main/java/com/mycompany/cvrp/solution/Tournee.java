/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cvrp.solution;

import com.mycompany.cvrp.instance.Instance;
import com.mycompany.cvrp.instance.reseau.Client;
import com.mycompany.cvrp.instance.reseau.Depot;
import com.mycompany.cvrp.instance.reseau.Point;
import com.mycompany.cvrp.instance.reseau.Route;
import com.mycompany.cvrp.io.InstanceReader;
import io.exception.ReaderException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Représente le trajet effectué par un véhicule pour livrer des clients
 * @author tryla
 */
public class Tournee {
    
    // dupliquée de l'attribut du même nom dans la classe Instance
    private final int capacite;
    // le même que celui de l'instance
    private final Depot depot;
    // collection triée dans l'ordre de visite
    // la clé correspond à l'ID du client
    private final LinkedHashMap<Integer, Client> clients;
    // somme des demandes des clients livrés dans la tournée
    private int demandeTotale = 0;
    // somme des coûts des routes empruntées pour livrer
    private int coutTotal = 0;
    private Instance instance;
    private Map<Point, Route> listeRoutes;

    public Tournee(Instance instance) {
        this.instance = instance;
        this.capacite = instance.getCapacite();
        this.depot = instance.getDepot();
        this.clients = new LinkedHashMap<>();
        this.listeRoutes = new HashMap<>();
    }

    public int getCapacite() {
        return capacite;
    }

    public Depot getDepot() {
        return depot;
    }  

    public Instance getInstance() {
        return instance;
    }
    
    

    public LinkedHashMap<Integer, Client> getClients() {
        return clients;
    }

    public int getDemandeTotale() {
        return demandeTotale;
    }

    public int getCoutTotal() {
        return coutTotal;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.instance);
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
        final Tournee other = (Tournee) obj;
        if (!Objects.equals(this.instance, other.instance)) {
            return false;
        }
        return true;
    }
    
    /**
     * ajoute le client en dernière position de la tournée
     * @note ajout d'un client possible que si la demande totale de la tourne 
     * plus la demande du client à ajouter est inférieure 
     * ou égale à la capacité de la tournée
     * 
     * mettre à jour les valeurs de la demande totale et du 
     * coût total de la tournée, si possible en temps constant O(1)
     * 
     * @param clientToAdd
     * @return si l'ajout a pu être effectué ou non
     */
    public boolean ajouterClient(Client clientToAdd){
        int condition = this.demandeTotale + clientToAdd.getQuantiteAMeLivrer();
        if(condition <= this.capacite){  
            // si on a ajouté un nouveau client dans une tournée sans clients
            if(this.clients.isEmpty()){
                //this.listeRoutes.put(clientToAdd, new Route(this.depot, clientToAdd));
                this.depot.ajouterRoute(clientToAdd);
                this.coutTotal += this.depot.getCoutVers(clientToAdd);
            }
            else {
                int lastElementIndex = this.clients.size()+1;
                Client lastClient = this.clients.get(lastElementIndex);
                //this.listeRoutes.put(clientToAdd, new Route(this.clients.get(lastElementIndex), clientToAdd));
                lastClient.ajouterRoute(clientToAdd);
                int ajout = lastClient.getCoutVers(clientToAdd) + clientToAdd.getCoutVers(this.depot) - lastClient.getCoutVers(this.depot);
                this.coutTotal += ajout;
            }
            
            this.clients.put(clientToAdd.getId(), clientToAdd);
            this.demandeTotale += clientToAdd.getQuantiteAMeLivrer();
            
            return true;
        }
        return false;
    }
    

    @Override
    public String toString() {
        String s = "Tournee{" + "capacite=" + capacite + ", depot=" + depot + ", clients=";
        for(Client c : this.clients.values()){
            s += "\n\tid: " + c.getId();
        }
        s += "\ndemandeTotale=" + demandeTotale + ", coutTotal=" + coutTotal + '}';
        return s;
    }
    
    
    
    
    public static void main(String[] args) {
        Depot depot = new Depot(0, 10, 100);
        Client c = new Client(1, 1, 3, 8);
        
        
        InstanceReader reader;
        try {
            reader = new InstanceReader("instances/A-n32-k5.vrp");
            Instance i = reader.readInstance();
            Tournee t = new Tournee(i);
           
            for(Client cli : i.getClients()){
                t.ajouterClient(cli);
            }
            System.out.println(t.toString());
        } catch (ReaderException ex) {
            Logger.getLogger(Instance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
