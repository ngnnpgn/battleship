/*
 * Classe abstraite des parties en replay, est utilisée par les programmeurs de la couche supérieure
 */
package bataillenavale;

import java.util.LinkedList;

/**
 *
 * @author jaylesr
 */
public abstract class AbstractPartieEnReplay extends AbstractPartie{
    
    public AbstractPartieEnReplay(Joueur j1, Joueur j2, int idPartie){
        super(j1,j2,idPartie);
    }
    
    public abstract int getTourEnCours();
    
    public abstract int getTourMax();
    
    public abstract void etatSuivant();
    
    public abstract void etatPrecedent();
    
    public abstract LinkedList<Etat> getEtatJoueur1();
    
    public abstract LinkedList<Etat> getEtatJoueur2();
}
