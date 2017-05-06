/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bataillenavale;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author jaylesr
 */
public interface FabriqueJoueur {
 
   /**
     * Permet de tester l'existance du joueur
     * @param pseudo du joueur
     * @return un joueur existant
     * @throws pseudoInexistantException si le joueur n'existe 
     */
    abstract Joueur getJoueur(String pseudo) throws pseudoInexistantException;
    
    
    /**
     * Requete utilisée pour l'inscription d'un joueur dans la base de données
     * @param pseudo
     * @param nom
     * @param prenom
     * @param email
     * @param date
     * @param NumRue
     * @param nomVille
     * @return
     * @throws pseudoExistantException is jamais ce pseudo existe déjà dans la base
     */
    abstract Joueur getJoueur(String pseudo, String nom, String prenom, String email, Date date, int NumRue, String nomVille) throws pseudoExistantException;
    
    /**
     * Permet d'aller chercher dans la base de donnée l'ensemble des joueurs avec le nombre de parties jouées
     * @param joueur
     * @return 
     */
    abstract ArrayList<Joueur> getParticipationJoueur(Joueur joueur);
    
    public static class pseudoExistantException extends Exception {

        public pseudoExistantException() {
        }
    }

    public static class pseudoInexistantException extends Exception {

        public pseudoInexistantException() {
        }
    }
      
}
