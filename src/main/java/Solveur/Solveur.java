/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Solveur;

import com.mycompany.cvrp.instance.Instance;
import com.mycompany.cvrp.solution.Solution;

/**
 *
 * @author tryla
 */
public interface Solveur {
    public String getNom();
    public Solution solve(Instance instance);
}
