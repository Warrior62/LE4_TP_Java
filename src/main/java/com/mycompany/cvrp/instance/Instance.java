/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cvrp.instance;

import com.mycompany.cvrp.instance.reseau.Client;
import com.mycompany.cvrp.instance.reseau.Depot;
import com.mycompany.cvrp.io.InstanceReader;
import io.exception.ReaderException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tryla
 */
public class Instance {
    
    // le nom doit être unique
    private final String nom;
    // capacité de tous les véhicules à livrer
    private final int capacite;
    private final Depot depot;
    // la clé correspond à l'ID du client
    private final LinkedHashMap<Integer, Client> clientsALivrer;

    public Instance(String nom, int capacite, Depot depot) {
        this.nom = nom;
        this.capacite = capacite;
        this.depot = depot;
        this.clientsALivrer = new LinkedHashMap<>();
    }

    public String getNom() {
        return nom;
    }

    public int getCapacite() {
        return capacite;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.nom);
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
        final Instance other = (Instance) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        return true;
    }
    
    public int getNbCLients(){
        return this.clientsALivrer.size();
    }
    
    /**
     * Recherche en comlexité 0(1)
     * @param id
     * @return 
     */
    public Client getClientById(int id){
        return this.clientsALivrer.get(id);
    }
    
    /**
     * @return une copie de la liste de clients
     */
    public LinkedList<Client> getClients(){
        LinkedList<Client> l = new LinkedList<>(this.clientsALivrer.values());
        return l;
    }

    public Depot getDepot() {
        return depot;
    }
    
    
    
    
    /**
     * ajoute le client ainsi que les routes avec les clients déjà existants + le dépot
     */
    public boolean ajouterClient(Client clientToAdd){
        if(clientToAdd == null) return false;
        int id = clientToAdd.getId();
        if(this.clientsALivrer.containsKey(id)) return false;
        
        creerRouteNouveauClient(clientToAdd);
        
        this.clientsALivrer.put(id, clientToAdd);
        
        return true;
    }

    private void creerRouteNouveauClient(Client clientToAdd) {
        // ajout des adresses par rapport au dépôt
        this.depot.ajouterRoute(clientToAdd);
        clientToAdd.ajouterRoute(this.depot);
        
        for(Client c : this.clientsALivrer.values()){
            c.ajouterRoute(clientToAdd);
            clientToAdd.ajouterRoute(this.depot);
        }
    }

    @Override
    public String toString() {
        String s = "Instance{" + "nom=" + nom + ", capacite=" + capacite + ", depot=" + depot + ", clientsALivrer=";
        for(Client c : clientsALivrer.values()){
            s += "\n\t" + c.toString();
        }
        s += "\n}";
        return s;
    }
    
    public static void main(String[] args) {
        InstanceReader reader;
        try {
            reader = new InstanceReader("instances/A-n32-k5.vrp");
            Instance i = reader.readInstance();
            System.out.println("nom : " + i.getNom() + ", capacite : " + i.getCapacite());
            System.out.println("idDepot : " + i.getDepot().getAbscisse() + ";" + i.getDepot().getOrdonee() );
            System.out.println("nbClients : " + i.getNbCLients());
        } catch (ReaderException ex) {
            Logger.getLogger(Instance.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
