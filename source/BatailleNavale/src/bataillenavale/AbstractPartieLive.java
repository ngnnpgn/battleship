/**
 * Classe abstraite, utilisée par les programmeurs des niveaux supérieurs
 */
package bataillenavale;

import bataillenavale.FabriqueEtat.ExceptionPartiePerdue;
import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author jaylesr
 */
public abstract class AbstractPartieLive extends AbstractPartie{

    /**
     * Constructeur
     * @param j1
     * @param j2
     * @param idPartie 
     */
    public AbstractPartieLive(Joueur j1, Joueur j2, int idPartie) {
        super(j1, j2, idPartie);
    }
    
    /**
     * Permet de savoir si c'est mon tour pour cette partie
     * @param j
     * @return 
     */
    public abstract boolean monTour(Joueur j);
    
    /**
     * Permet d'avoir le numéro du tour a jouer
     * @param j
     * @return 
     */
    public abstract int getNumeroTourSuivant(Joueur j);
    
    /**
     * Permet d'enregistrer le coup en base
     * @param listeAction
     * @param joueur
     * @param numeroTour
     * @return
     * @throws bataillenavale.AbstractPartieLive.ExceptionCoupImpossible
     * @throws bataillenavale.FabriqueEtat.ExceptionPartiePerdue 
     */
    public abstract LinkedList<int[]> JouerCoup(LinkedList<AbstractAction> listeAction, Joueur joueur, int numeroTour) throws ExceptionCoupImpossible,ExceptionPartiePerdue;

    /**
     * 
     * @param joueur 
     */
    public abstract void finPartie(Joueur joueur);
    
    public static class ExceptionCoupImpossible extends Exception {

        public ExceptionCoupImpossible() {
        }
    }
}
