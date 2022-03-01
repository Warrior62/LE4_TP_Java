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
public abstract class OperateurLocal extends Operateur {
    protected Client clientI, clientJ;
    protected final int positionI, positionJ;

    public OperateurLocal() {
        super();
        this.positionI = -1;
        this.positionJ = -1;
    }

    public OperateurLocal(Tournee tournee, int positionI, int positionJ) {
        super(tournee);
        this.positionI = positionI;
        this.positionJ = positionJ;       
        this.clientJ = tournee.getClient(positionJ);
        this.clientI = tournee.getClient(positionI);      
    }

    public Client getClientI() {
        return clientI;
    }

    public Client getClientJ() {
        return clientJ;
    }
    
    public static OperateurLocal getOperateur(TypeOperateurLocal type){
        switch(type){
            case INTRA_DEPLACEMENT:
                return new IntraDeplacement();
            /*case INTRA_ECHANGE:
                return new IntraEchange();
            case INTER_DEPLACEMENT:
                return new InterDeplacement(tournee, positionI, positionJ);
            case INTER_ECHANGE:
                return new InterEchange(tournee, positionI, positionJ);*/
            default:
                return null;
        }
    }
    
    public static OperateurIntraTournee getOperateurIntra(TypeOperateurLocal type, Tournee tournee, int positionI, int positionJ){
        switch(type){
            case INTRA_DEPLACEMENT:
                return new IntraDeplacement(tournee, positionI, positionJ);
            /*case INTRA_ECHANGE:
                return new IntraEchange(tournee, positionI, positionJ);*/
            default:
                return null;
        }
    }
    
    public static OperateurInterTournees getOperateurInter(TypeOperateurLocal type, Tournee tournee, int positionI, int positionJ){
        switch(type){
            /*case INTER_DEPLACEMENT:
                return new InterDeplacement(tournee, positionI, positionJ);
            case INTER_ECHANGE:
                return new InterEchange(tournee, positionI, positionJ);*/
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return "OperateurLocal{" + "clientI=" + clientI + ", positionI=" + positionI + ", clientJ=" + clientJ + ", positionJ=" + positionJ + '}';
    }    
}
