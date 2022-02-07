/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cvrp.instance.reseau;

import java.util.HashMap;
import java.util.Map;

/**
 * Un point défini par des cordonnées en dimension 2
 * @author tryla
 */
public abstract class Point {
 
    // on supposera que l'ID est unique
    private final int id;
    private final int abscisse;
    private final int ordonee;
    // la clé représente le point d'arrivée de la route
    private Map<Point, Route> listeRoutes;

    public Point(int id, int abscisse, int ordonee) {
        this.id = id;
        this.abscisse = abscisse;
        this.ordonee = ordonee;
        this.listeRoutes = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public int getAbscisse() {
        return abscisse;
    }

    public int getOrdonee() {
        return ordonee;
    }

    @Override
    public int hashCode() {
        return this.id;
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
        final Point other = (Point) obj;
        return this.id == other.id;
    }

    
    public void ajouterRoute(Point destination){
        Route r = new Route(this, destination);
        this.listeRoutes.put(destination, r);
    }
    
    public int getCoutVers(Point destination){
        Route r = this.listeRoutes.get(destination);
        if(r != null) return r.getCout();
        return Integer.MAX_VALUE;
    } 

    @Override
    public String toString() {
        return "Point{" + "id=" + id + ", abscisse=" + abscisse + ", ordonee=" + ordonee + '}';
    }
    
    
    public static void main(String[] args) {
        Client c1 = new Client(1, 1, 0, 0);
        Client c2 = new Client(2, 2, 1, 1);
        Client c3 = new Client(3, 3, 2, 2);
        c1.ajouterRoute(c2);
        c1.ajouterRoute(c3);
        
        System.out.println(c1.getCoutVers(c1)); // infini
        System.out.println(c1.getCoutVers(c2)); // 1
        System.out.println(c1.getCoutVers(c3)); // 3
    }
    
}
