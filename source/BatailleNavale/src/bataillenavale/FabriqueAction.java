/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bataillenavale;

import java.util.LinkedList;

/**
 *
 * @author thomas
 */
public interface FabriqueAction {
    /**
     * retourne une liste d'action de deplacement correpondant au tour d'une partie
     * @param idPartie
     * @param numTour
     * @return 
     */
    public LinkedList <AbstractAction> getActionDeplacement(int idPartie, int numTour);
    
    /**
     * retourne une liste d'action de tir correpondant au tour d'une partie
     * @param idPartie
     * @param numTour
     * @return 
     */
    public LinkedList <AbstractAction> getActionTir(int idPartie, int numTour);
    
}
