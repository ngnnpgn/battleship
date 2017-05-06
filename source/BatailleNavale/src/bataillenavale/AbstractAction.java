/**
 * 
 * Classe mère des Action. Le patron appliqué est le patron stratégie, avec la fonction enregistrer action
 * 
 * 
*/
package bataillenavale;

import bataillenavale.AbstractPartieLive.ExceptionCoupImpossible;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class AbstractAction {
    
    /**
     * Le bateau qui execute l'action
     */
    private AbstractBateau bateau;
    
    public AbstractAction(AbstractBateau bateau){
        this.bateau = bateau;
    }
    
    /**
     * Pour enregistrer une action en base de donnée
     * creer le tuple dans la table action
     * @param idPartie 
     */
    public void enregistrerAction(AbstractPartieLive partie, Joueur joueur, int numTour, int numAction) throws ExceptionCoupImpossible,ExceptionBateauTouche{
        PreparedStatement stt;
        try {
            stt = BDManager.getInstance().prepareStatement("INSERT INTO Action VALUES(sequence_action.nextval,?,?,?,?)");
            stt.setInt(1, numTour);
            stt.setInt(2, numAction);
            stt.setInt(3, partie.getIdPartie());
            stt.setInt(4,this.getBateau().getIdBateau());
            stt.executeQuery();
            stt.close();
        } catch (SQLException ex) {
            Logger.getLogger(AbstractAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the bateau
     */
    public AbstractBateau getBateau() {
        return bateau;
    }
    
    /**
     * Exception levée si lors de notre action on touche un bateau adverse
     */
    public static class ExceptionBateauTouche extends Exception {
        int coordX;
        int coordY;

        public int getCoordX() {
            return coordX;
        }

        public int getCoordY() {
            return coordY;
        }
        
        public ExceptionBateauTouche(int x,int y) {
            this.coordX=x;
            this.coordY=y;
        }
    }

    
}
