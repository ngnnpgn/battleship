/**
 * 
 * classe abstraite de l'Etat, est utilisée par les programmeurs des couches supérieures
 * 
 */
package bataillenavale;

/**
 *
 * @author pierre
 */
public abstract class AbstractEtat {
    /**
     * detect si un bateau est touche par un tir
     * @param e
     * @param coordX
     * @param coordY
     * @return 
     */
     public abstract  boolean estTouche(int coordX, int coordY);
     
     public abstract boolean estVivant();
}
