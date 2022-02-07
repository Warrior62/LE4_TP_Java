/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cvrp.instance.reseau;

/**
 *
 * @author tryla
 */
public class Client extends Point{
    
    private int quantiteAMeLivrer;

    public Client(int quantiteAMeLivrer, int id, int abscisse, int ordonee) {
        super(id, abscisse, ordonee);
        this.quantiteAMeLivrer = quantiteAMeLivrer;
    }

    public int getQuantiteAMeLivrer() {
        return quantiteAMeLivrer;
    }


    @Override
    public String toString() {
        return "Client{" + "quantiteAMeLivrer=" + quantiteAMeLivrer + super.toString() + '}';
    }
    
    
    public static void main(String[] args){
        
        Client c1 = new Client(1,2,3,5);
        System.out.println(c1.toString());
                
    }
            
    
    
    
}
