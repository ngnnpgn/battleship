/*
 * Classe sans mère des parties. Est utilisée par les programmeurs des couches supérieurs
 * 
 */
package bataillenavale;

/**
 *
 * @author jaylesr
 */
public abstract class AbstractPartie {
    
    /**
     * Données d'une partie
     */
    protected int idPartie;
    protected Joueur joueur1;
    protected Joueur joueur2;

    /**
     * Accesseur de l'id d'une partie
     * @return 
     */
    public int getIdPartie() {
	return this.idPartie;
    }
    
    /**
     * Accesseur du premier joueur de la partie
     * @return 
     */
    public Joueur getJoueur1() {
        return this.joueur1;
    }
    
    /**
     * Accesseur du deuxieme joueur de la partie
     * @return 
     */
    public Joueur getJoueur2() {
        return this.joueur2;
    }
    
    /**
     * Accesseur de l'adversaire du joueur pour cette partie
     * @param joueurA
     * @return 
     */
    public Joueur getAdversaire(Joueur joueurA){
        if(joueurA.equals(this.joueur1)){
            return this.joueur2;
        }else{
            return this.joueur1;
        }
    }

    /**
     * Constructeur
     * @param j1
     * @param j2
     * @param idPartie 
     */
    public AbstractPartie(Joueur j1, Joueur j2, int idPartie) {
        this.joueur1 = j1;
        this.joueur2 = j2;
        this.idPartie = idPartie;
    }
    
    @Override
    public String toString()
    {
        return joueur1.toString() +" "+joueur2.toString()+" "+idPartie;
    }
}
