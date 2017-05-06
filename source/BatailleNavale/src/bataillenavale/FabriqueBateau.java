/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bataillenavale;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Set;

/**
 *
 * @author thomas
 */
public interface FabriqueBateau {
   
    /**
     * recupere les bateaux d'un joueur lors d'une partie
     * @param idPartie
     * @param joueur
     * @return
     * @throws SQLException 
     */
    public LinkedList<AbstractBateau> getBateau(int idPartie, Joueur joueur ) throws SQLException ;
    
    /**
     * Creer une class bateau destroyer
     * @return 
     */
    public BateauDestroyer getDestroyer(Joueur joueur);
    
    /**
     * Creer une class bateau Escorteur
     * @return 
     */
    public BateauEscorteur getEscorteur(Joueur joueur);
    
    /**
     * Permet de créer en base de donnée un bateau et de renvoyer l'action correspondante a son palcement
     * @param joueur
     * @param x
     * @param y
     * @return 
     */
    public ActionPlacement placerDestroyer(Joueur joueur, int x, int y, Direction direction);
    
    /**
     * Permet de créer en base de donnée un bateau et de renvoyer l'action correspondante a son palcement
     * @param joueur
     * @param x
     * @param y
     * @return 
     */
    public ActionPlacement placerEscorteur(Joueur joueur, int x, int y, Direction direction);
    
    
    public static class bateauInexistantException extends Exception {

        public bateauInexistantException() {
        }
    }
    
    
}
