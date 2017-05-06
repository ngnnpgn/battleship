/**
 * 
 * Action déplacement, avec un enregistrerActionspécifique (Patron stratégie)
 * 
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
 * @author thomas
 */
public class ActionDeplacement extends AbstractAction{
    
    /**
     * Attributs
     */
    Deplacement deplacement;

    public Deplacement getDeplacement() {
        return deplacement;
    }

    /**
     * Constructeur
     * @param bateau
     * @param deplacement 
     */
    public ActionDeplacement(AbstractBateau bateau,Deplacement deplacement)  {
        super(bateau);
        this.deplacement=deplacement;
    }

    /**
     * Cree le tuple dans la table ActionDeplacement et execute l'action
     * @param partie
     * @param joueur
     * @param numTour
     * @param numAction
     * @throws bataillenavale.AbstractPartieLive.ExceptionCoupImpossible
     * @throws bataillenavale.FabriqueEtat.ExceptionPartiePerdue 
     */
    @Override
    public void enregistrerAction(AbstractPartieLive partie, Joueur joueur, int numTour, int numAction) throws ExceptionCoupImpossible{
        try {
            super.enregistrerAction(partie, joueur, numTour, numAction);
        } catch (ExceptionBateauTouche ex) {
            Logger.getLogger(ActionDeplacement.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            // recuperation de idAction
            int idAction = 0;
            PreparedStatement stt = BDManager.getInstance().prepareStatement("SELECT sequence_action.currval FROM Action");
            ResultSet resultat = stt.executeQuery();
            resultat.next();
            idAction = resultat.getInt(1);
            
            // creation du tuple ActionDeplacement
            stt = BDManager.getInstance().prepareStatement("INSERT INTO ActionDeplacement VALUES (?,?)");
            stt.setInt(1, idAction);
            stt.setString(2, deplacement.toString());
            stt.executeQuery();
            stt.close();  
            this.getBateau().deplacer(deplacement);

            // creation du tuple etat
            stt = BDManager.getInstance().prepareStatement("INSERT INTO Etat VALUES(sequence_etat.nextval,?,?,?,?,?,?,?,?)");
            stt.setInt(1, this.getBateau().getCoordX());
            stt.setInt(2,this.getBateau().getCoordY());
            stt.setString(3, this.getBateau().getDirection().toString());
            stt.setInt(4,this.getBateau().getVie());
            stt.setInt(5, idAction);
            stt.setInt(6,numAction);
            stt.setInt(7,this.getBateau().getIdBateau());
            stt.setInt(8, this.getBateau().taille);
            stt.executeQuery();
            stt.close();
            
        } catch (SQLException ex){
            throw new ExceptionCoupImpossible();
        }
        
    }
}
