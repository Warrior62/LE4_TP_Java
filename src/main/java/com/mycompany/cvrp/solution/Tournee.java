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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import operateur.InsertionClient;
import operateur.Operateur;

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
    private final LinkedList<Client> clients;
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
        this.clients = new LinkedList<>();
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
    
    public int getNbClients() {
        return this.clients.size();
    }

    public LinkedList<Client> getClients() {
        return new LinkedList<>(clients);
    }

    public int getDemandeTotale() {
        return demandeTotale;
    }

    public int getCoutTotal() {
        return coutTotal;
    }

    public void setCoutTotal(int coutTotal) {
        this.coutTotal = coutTotal;
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
    
    private int deltaCoutInsertionFin(Client clientToAdd){
      return this.deltaCoutInsertion(this.getNbClients(), clientToAdd);
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
        if(clientToAdd == null) return false;
        
        if(!isClientAjoutable(clientToAdd))
            return false;
        
        this.coutTotal += this.deltaCoutInsertionFin(clientToAdd);

        this.clients.add(clientToAdd);
        this.demandeTotale += clientToAdd.getQuantiteAMeLivrer();
        return true;
    }
    
    private boolean isClientAjoutable(Client clientToAdd){
        int condition = this.demandeTotale + clientToAdd.getQuantiteAMeLivrer();       
        if(condition > this.capacite)
            return false;
        return true;
    }
    
    private boolean checkCoutTotal() {
        int testCoutTotal = 0;
        Client lastClient = null;
        
        for(Client c : this.getClients()){
            if(lastClient == null){
                testCoutTotal += this.depot.getCoutVers(c);
            }
            else {
                testCoutTotal += lastClient.getCoutVers(c);
            }
            lastClient = c;
        }
        if(lastClient != null){
            testCoutTotal += lastClient.getCoutVers(this.depot);
        }
        System.out.println("\tTournee check cout total : " + (testCoutTotal + " " + this.getCoutTotal()));
        return testCoutTotal == this.getCoutTotal();
    }
    
    private boolean checkDemandeTotale() {
        int testDemande = 0;
        for(Client c : this.clients) testDemande += c.getQuantiteAMeLivrer();
        if(testDemande != this.demandeTotale) return false;
        System.out.println("\tTournee check demande totale : true");
        return true;
    }
    
    private boolean checkCapacite() {
        System.out.println("\tTournee check capacité : " + (this.demandeTotale <= this.capacite));
        return this.demandeTotale <= this.capacite;
    }
    
    /**
     * réalisabe si coût total et demande totale sont correctement calculées
     * + demande totale est inférieure ou égale à la capacité
     * indique si la solution est réalisable ou non
     * @return si c'est réalisable ou non
     */
    public boolean check(){
        boolean res = checkCoutTotal() && checkDemandeTotale() && checkCapacite();
        if(res) System.out.println("\tTournee réalisable");
        else System.out.println("\tTournee non-réalisable");
        return res;
    }
    
    // renvoie le point de la tournée qui précéde la position position
    // si position vaut 0 alors c'est le dépot
    // position doit être comprise entre 0 et n-1
    private Point getPrec(int position){
        if(position == 0) return this.getDepot();
        return this.getClients().get(position-1);
    }
    
    // renvoie le point de la tournée qui est actuelemment  la position position
    // si position est égal au nombre de clients dans la tournée, alors il s’agit du dépôt
    private Point getCurrent(int position) {
        if(position == this.getNbClients()) return this.getDepot();
        return this.getClients().get(position);
    }
    
    // indique s'il est possible d'insérer un client en position position
    private boolean isPositionInsertionValide(int position) {
        if(position < 0 || position > this.getNbClients())
            return false;
        return true;   
    }
    
    public int deltaCoutInsertion(int position, Client clientToAdd) {
        if(!this.isPositionInsertionValide(position) || clientToAdd == null)
            return Integer.MAX_VALUE;
        
        int deltaCout = 0;
        
        if(this.clients.isEmpty()){
            deltaCout += this.depot.getCoutVers(clientToAdd) + clientToAdd.getCoutVers(this.depot);
        }
        else {
            Point currentClient = this.getCurrent(position);
            Point lastClient = this.getPrec(position);
            
            deltaCout -= lastClient.getCoutVers(currentClient);
            deltaCout += lastClient.getCoutVers(clientToAdd);
            deltaCout += clientToAdd.getCoutVers(currentClient);
        }
        
        return deltaCout;
    }
    
    public InsertionClient getMeilleureInsertion(Client clientToInsert){
        InsertionClient insMeilleur = new InsertionClient();
        
        if(!isClientAjoutable(clientToInsert)) return insMeilleur;
        
        InsertionClient ins;
        
        for(int pos=0; pos<this.getNbClients(); pos++){
           ins = new InsertionClient(this, clientToInsert, pos);
           if(ins.isMeilleur(insMeilleur))
               insMeilleur = ins;
        }       
        return insMeilleur;
    }
    
    public boolean doInsertion(InsertionClient infos){
        if(infos==null) return false;
        if(!infos.isMouvementRealisable()) return false;
        
        Client client = infos.getClient();
        
        this.coutTotal += infos.getDeltaCout();
        this.clients.add(infos.getPosition(), client);    
        this.demandeTotale += client.getQuantiteAMeLivrer();
        
        if(!this.check()){
            System.out.println("ERROR doInsertion");
            System.out.println(infos);
            System.exit(-1);
        }
        return true;
    }
    

    @Override
    public String toString() {
        String s = "Tournee{" + "capacite=" + capacite + ", depot=" + depot + ", clients=[";
        for(Client c : this.clients){
            s += c.getId() + ", ";
        }
        s += "]\ndemandeTotale=" + demandeTotale + ", coutTotal=" + coutTotal + '}';
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
            t.check();
            System.out.println(t.toString());
        } catch (ReaderException ex) {
            Logger.getLogger(Instance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
