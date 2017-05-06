/**
 * Classe Abstraite de fabrique de joueurs, est utilisée par les couches supérieures
 */

package bataillenavale;

import java.sql.Date;

/**
 *
 * @author jaylesr
 */
abstract class AbstractFabriqueJoueur {

    
   
    
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
     * @return
     * @throws pseudoExistantException is jamais ce pseudo existe déjà dans la base
     */
    abstract Joueur getJoueur(String pseudo, String nom, String prenom, String email, Date date, int NumRue, String nomVille) throws pseudoExistantException;
    
    public static class pseudoExistantException extends Exception {

        public pseudoExistantException() {
        }
    }

    public static class pseudoInexistantException extends Exception {

        public pseudoInexistantException() {
        }
    }
      
}
