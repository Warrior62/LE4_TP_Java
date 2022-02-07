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
            
            System.out.println(s.toString());
        } catch (ReaderException ex) {
            Logger.getLogger(Instance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
