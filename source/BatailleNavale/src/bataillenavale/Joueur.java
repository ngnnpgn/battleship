package bataillenavale;

/**
 * Produit Joueur, renvoyé par les fabriques de joueurs
 * class contenant les fonctions sur le joueur
 * @author jaylesr
 */
public class Joueur {
    /**
     * Le pseudo du joueur
     */
    private String pseudo;
    
    /**
     * Le nombre de parties jouées par le joueur (utile que dans certain cas, sinon initialisé à -1)
     */
    private int nombreParties;
    
    public Joueur(String pseudo){
        this.pseudo = pseudo;
        this.nombreParties = -1;
    }
    
    public Joueur(String pseudo, int nombreParties){
        this.pseudo = pseudo;
        this.nombreParties = nombreParties;
    }
    
    public String getPseudo(){
        return this.pseudo;
    }
    
    public void setPseudo(String pseudo){
        this.pseudo = pseudo;
    }
    
    public int getNombreParties(){
        return this.nombreParties;
    }
    
    @Override  
    public boolean equals(Object obj) {  
        if (obj == null) { return false; }  
        if (getClass() != obj.getClass()) { return false; }  
        else {
            if(this.pseudo.equals(((Joueur) obj).pseudo)){
                return true;
            }
            return false;
        }
     }

    @Override
    public int hashCode() {
        return pseudo.hashCode();
    }
    
    @Override 
    public String toString(){
        return pseudo + (nombreParties != -1 ? " " + Integer.toString(
                nombreParties) : "");
        
    }
    
}
