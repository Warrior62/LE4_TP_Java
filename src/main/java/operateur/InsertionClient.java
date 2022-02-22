/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operateur;

import com.mycompany.cvrp.instance.reseau.Client;
import com.mycompany.cvrp.solution.Tournee;

/**
 *
 * @author tryla
 */
public class InsertionClient extends Operateur{
    
    private Client client;
    private int position;

    public InsertionClient(){
        super();
        this.position = 0;
    }
    
    // maj du coût de l'opérateur
    public InsertionClient(Tournee tournee, Client client, int position) {
        super(tournee);
        this.client = client;
        this.position = position;
        this.deltaCout = this.evalDeltaCout();
    }

    @Override
    protected int evalDeltaCout() {
        if(tournee == null) return Integer.MAX_VALUE;
        return this.tournee.deltaCoutInsertion(position, client);
    }
    
    @Override
    protected boolean doMouvement() {
        return this.tournee.doInsertion(this);
    }

    public Client getClient() {
        return client;
    }

    public int getPosition() {
        return position;
    }
    
    @Override
    public String toString() {
        return "InsertionClient{" + "client=" + client + ", position=" + position + ", tournee=" + super.tournee.toString() + ", deltaCout" + super.deltaCout + '}';
    }    
}
