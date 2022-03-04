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
import operateur.InsertionClient;
import operateur.Operateur;
import operateur.OperateurIntraTournee;
import operateur.OperateurLocal;
import operateur.TypeOperateurLocal;

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
    
    public boolean ajouterClientDansDerniereTournee(Client clientToAdd){
        int coutInit = 0, lastIndex = this.listeTournees.size() - 1;
        
        if(this.listeTournees.isEmpty())
            return false;
        
        Tournee lastTournee = this.listeTournees.get(lastIndex);
        coutInit = lastTournee.getCoutTotal();
        System.out.println("cout total: " + this.coutTotal + ", coutInit: " + coutInit);
        if(lastTournee.ajouterClient(clientToAdd)){
            this.coutTotal -= coutInit;
            this.coutTotal += lastTournee.getCoutTotal();
            System.out.println("\t\tcout total: " + this.coutTotal + ", coutInit: " + coutInit);
            return true;
        }
        else{
            System.out.println("pb cli : " + clientToAdd.getId());
        }
        return false;
    }
    
    private boolean checkCoutTotal() {
        int testCoutTotal = 0;
        
        for(Tournee t : this.listeTournees){
            testCoutTotal += t.getCoutTotal();
        }
        boolean res = (testCoutTotal == this.getCoutTotal()); 
        System.out.println("Solution Check Cout total : " + res);
        return res;
    }

    private boolean checkToutesTourneesRealisables() {
        for(Tournee t : this.listeTournees){
            if(t.check() == false){
                System.out.println("Solution Check Tournees réalisables : false");
               return false; 
            } 
        }      
        System.out.println("Solution Check tournées réalisables OK");
        return true;
    }

    private boolean checkUniciteClientTournee() {
        List<Client> clients = this.instance.getClients();
        for(Tournee t : this.listeTournees){
            System.out.println("Nb cli dans t : " + t.getClients().size());
            for(Client c : t.getClients()){
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
        System.out.println("Solution Check unicité client tournée OK");
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
    
    public InsertionClient getMeilleureInsertion(Client clientToInsert) {
        InsertionClient insMeilleur = new InsertionClient();        
        if(clientToInsert == null) return insMeilleur;
        InsertionClient ins;
        
        for(Tournee t : this.getListeTournees()){
            ins = t.getMeilleureInsertion(clientToInsert);
            if(ins.isMeilleur(insMeilleur))
                insMeilleur = ins; 
        }             
        return insMeilleur;
    }
    
    public boolean doInsertion(InsertionClient infos){
        if(infos == null) return false;  
        if(!this.listeTournees.contains(infos.getTournee())) return false;
        if(!infos.doMouvementIfRealisable()) return false;
        
        this.coutTotal += infos.getDeltaCout();
        return true;
    }
    
    public boolean doMouvementRechercheLocale(OperateurLocal infos){
        if(infos == null) return false;  
        if(!this.listeTournees.contains(infos.getTournee())) return false;
        if(!infos.doMouvementIfRealisable()) return false;
        
        this.coutTotal += infos.getDeltaCout();
        if(!this.check()){
            System.out.println("ERROR doMouvementRechercheLocale");
            System.out.println(infos);
            System.exit(-1);
        }
        return true;
    }
    
    private OperateurLocal getMeilleurOperateurIntra(TypeOperateurLocal type){
        OperateurLocal best = OperateurLocal.getOperateur(type);
        for(Tournee t : this.listeTournees){
            for(int i=0; i<t.getClients().size(); i++) {
                for(int j=0; j<t.getClients().size()+1; j++) {
                    if(j < t.getClients().size()){
                        OperateurIntraTournee op = OperateurLocal.getOperateurIntra(type, t, i, j);
                        if(op.isMeilleur(best)) {
                            best = op;
                        }                  
                    }
                }
            }    
        }
        return best;
    }
    
    public OperateurLocal getMeilleurOperateurLocal(TypeOperateurLocal type){
        if(OperateurLocal.getOperateur(type) instanceof OperateurIntraTournee)
            return this.getMeilleurOperateurIntra(type);
        //this.getMeilleurOperateurInter(type);
        return null;
    }
    
    @Override
    public String toString() {
        String s = "Solution{" + "nomInstance=" + instance.getNom() + ", listeTournees=";
        for(Tournee t : listeTournees){
            s += "\t\n" + t.toString();
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
            Client c1 = new Client(5, 5, 5, 5);
            Client c2 = new Client(6, 6, 2, 2);
            Client c3 = new Client(1, 7, 12, 9);
            Client c4 = new Client(1, 8, 1, 1);

            s.ajouterClientDansNouvelleTournee(c1);
            s.ajouterClientDansNouvelleTournee(c2);
            s.ajouterClientDansTourneeExistance(c3);
            s.ajouterClientDansDerniereTournee(c4);
            System.out.println("Size tournees dans solution : " + s.listeTournees.size());
            System.out.println("Check solution : " + s.check());
            
            System.out.println(s.toString());
        } catch (Exception ex) {
            Logger.getLogger(Instance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
