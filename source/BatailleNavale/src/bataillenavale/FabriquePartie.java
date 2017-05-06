/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bataillenavale;

import java.util.*;

/**
 *
 * @author jaylesr
 */
public interface FabriquePartie {

    /**
     * Permet d'obtenir une nouvelle partie, s'occupe de trouver le joueur
     * adverse et d'instancier la partie dans la base de donnée
     * @param joueur
     * @return 
     */
    public PartieLive nouvellePartie(Joueur joueur) throws ExceptionAdversaireInexistant;
    
    /**
     * 
     * @param joueur1
     * @param joueur2
     * @return
     * @throws bataillenavale.FabriquePartie.ExceptionPartieInexistante 
     */
    public PartieLive partieEnCours(Joueur joueur1, Joueur joueur2) throws ExceptionPartieInexistante;
    
    /**
     * Renvoie la liste des parties en cours ou terminées où le joueur n'apparait pas
     * @param joueur
     * @return
     * @throws bataillenavale.FabriquePartie.ExceptionPartieInexistante 
     */
    public LinkedList<PartieEnReplay> getListePartieAObserver(Joueur joueur) throws ExceptionPartieInexistante;
    
    /**
     * 
     * @param joueur
     * @return 
     */
    public Set<PartieLive> getPartieEnCours(Joueur joueur);

    public static class ExceptionAdversaireInexistant extends Exception {

        public ExceptionAdversaireInexistant() {
        }
    }
    
    public static class ExceptionPartieInexistante extends Exception {

        public ExceptionPartieInexistante() {
        }
    }
}
