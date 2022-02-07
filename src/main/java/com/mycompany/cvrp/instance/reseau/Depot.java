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
public class Depot extends Point{

    public Depot(int id, int abscisse, int ordonee) {
        super(id, abscisse, ordonee);
    }

    @Override
    public String toString() {
        return "Depot{" + super.toString() + '}';
    }
 
    public static void main(String[] args) {
        Depot d = new Depot(1, 4, 2);
        System.out.println(d.toString());
    }
    
}
