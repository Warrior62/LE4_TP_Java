/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cvrp.instance.reseau;

import java.util.Objects;

/**
 *
 * @author tryla
 */
public class Route {
    
    private Point depart;
    private Point arrivee;
    private int cout;

    public Route(Point pDepart, Point pArrivee) {
        this.depart = pDepart;
        this.arrivee = pArrivee;
        this.cout = this.calculCout();
    }
    
    private int calculCout(){
        int carreX, carreY;
        carreY = (int) Math.pow((float) arrivee.getOrdonee() - depart.getOrdonee(), 2);
        carreX = (int) Math.pow((float) arrivee.getAbscisse() - depart.getAbscisse(), 2);
        double distanceEuclidienne = Math.sqrt(carreY + carreX);
        return Math.round((float) distanceEuclidienne);
    }

    public Point getDepart() {
        return depart;
    }

    public Point getArrivee() {
        return arrivee;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.depart);
        hash = 23 * hash + Objects.hashCode(this.arrivee);
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
        final Route other = (Route) obj;
        if (!Objects.equals(this.depart, other.depart)) {
            return false;
        }
        if (!Objects.equals(this.arrivee, other.arrivee)) {
            return false;
        }
        return true;
    }
    
    
    public static void main(String[] args) {
        Depot d = new Depot(1, 3, 8);
        Depot a = new Depot(2, 19, 0);
        Route r = new Route(d, a);
        
        System.out.println(r.calculCout());
    }

    int getCout() {
        return cout;
    }
    
}
