/**
 * ActionPlacement, utilisé dans le patron stratégie
 * 
 */
package bataillenavale;

import bataillenavale.AbstractPartieLive.ExceptionCoupImpossible;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jaylesr
 */
public class ActionPlacement extends AbstractAction{
    
    private int positionX;
    private int positionY;
    
    /**
     * Constructeur
     * @param positionX
     * @param positionY
     * @param bateau 
     */
    public ActionPlacement(int positionX, int positionY, AbstractBateau bateau){
        super(bateau);
        this.positionX = positionX;
        this.positionY = positionY;
    }
    
    /**
     * Enregistrement de l'action spécifique au placement
     * @param partie
     * @param joueur
     * @param numTour
     * @param numAction
     * @throws bataillenavale.AbstractPartieLive.ExceptionCoupImpossible 
     */
    @Override
    public void enregistrerAction(AbstractPartieLive partie, Joueur joueur, int numTour, int numAction) throws ExceptionCoupImpossible{
        try {
            super.enregistrerAction(partie, joueur, numTour, numAction);
        } catch (ExceptionBateauTouche ex) {
            Logger.getLogger(ActionPlacement.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int idAction = 0;
        try {
            
            //on récupère l'idAction en cours
            PreparedStatement stt = BDManager.getInstance().prepareStatement("SELECT sequence_action.currval FROM Action");
            ResultSet resultat = stt.executeQuery();
            resultat.next();
            idAction = resultat.getInt(1);
            stt.close();
            resultat.close();
            
            //on insère le tuple dans la base de donnée 
            stt = BDManager.getInstance().prepareStatement("INSERT INTO Etat VALUES(sequence_etat.nextval,?,?,?,?,?,?,?,?)");
            stt.setInt(1, this.getPositionX());
            stt.setInt(2, this.getPositionY());
            stt.setString(3, this.getBateau().getDirection().toString());
            stt.setInt(4, this.getBateau().getVie());
            stt.setInt(5, idAction);
            stt.setInt(6, numAction);
            stt.setInt(7, this.getBateau().getIdBateau());
            stt.setInt(8, this.getBateau().taille);
            stt.executeQuery();
            stt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ActionPlacement.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    /**
     * Accesseur positionX
     * @return 
     */
    public int getPositionX() {
        return this.positionX;
    }

    /**
     * Accesseur positionY
     * @return 
     */
    public int getPositionY() {
        return this.positionY;
    }
}
