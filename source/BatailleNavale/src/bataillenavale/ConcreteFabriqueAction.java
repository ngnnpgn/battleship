/**
 * Fabrique utilisée pour aller chercher les actions en base de donnée (les actions déjà réalisées)
 * 
 */
package bataillenavale;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thomas
 */
public class ConcreteFabriqueAction implements FabriqueAction{
    private static ConcreteFabriqueAction instance = null;
    
    public ConcreteFabriqueAction(){
        super();
    }
    
    /**
     * Pour la patron singleton
     * @return 
     */
    public static ConcreteFabriqueAction getInstance(){
        if(ConcreteFabriqueAction.instance == null){
            ConcreteFabriqueAction.instance = new ConcreteFabriqueAction();
        }
        return ConcreteFabriqueAction.instance;
    }
    
    
    // TODO useless
    /**
     * Renvoie un liste contenant les idActions d'un tour d'une partie
     * @param idPartie
     * @param numTour
     * @return 
     */
    //@Override
    public LinkedList <Integer> getListIdAction(int idPartie, int numTour) {
        LinkedList <Integer> listIdAction = new LinkedList<Integer>();
        String requete = "select idAction from Action where numTour=? and idPartie=?";
    
        try {
            PreparedStatement stt = BDManager.getInstance().prepareStatement(requete);
            stt.setInt(1, numTour);
            stt.setInt(2, idPartie);
            ResultSet resultat = stt.executeQuery();
            
            while (resultat.next()) {
                listIdAction.add(resultat.getInt("idAction"));
            }
         resultat.close();
         stt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConcreteFabriqueAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listIdAction;
    }
    
    
    
    /**
     * renvoie un liste contenant les actionTirs du tour
     * @param idPartie
     * @param numTour
     * @return 
     */
    public LinkedList <AbstractAction> getActionTir(int idPartie, int numTour){
        
        LinkedList <AbstractAction> listAction = new LinkedList<AbstractAction>();
        AbstractBateau bateau;
        AbstractAction action;
        
        try {
            String requete =  "SELECT * FROM action NATURAL JOIN ActionTir  NATURAL "
                + "JOIN  Action WHERE numTour=? and idPartie=?";
            PreparedStatement stt = BDManager.getInstance().prepareStatement(requete);
            stt.setInt(1, numTour);
            stt.setInt(2, idPartie);
            ResultSet resultat =  stt.executeQuery();
            while(resultat.next()){
                action = new ActionTir(null, resultat.getInt("X"), resultat.getInt("Y")); 
                listAction.add(action);
            }
            stt.close();
            resultat.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConcreteFabriqueAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listAction;
    }
    
    /**
     * renvoie un liste contenant les actionDeplacement du tour
     * @param idPartie
     * @param numTour
     * @return 
     */
    public LinkedList <AbstractAction> getActionDeplacement(int idPartie, int numTour){
        
        LinkedList <AbstractAction> listAction = new LinkedList<AbstractAction>();
        AbstractBateau bateau;
        AbstractAction action;
        
        try {
            String requete =  "SELECT * FROM action NATURAL JOIN ActionDeplacement  NATURAL"
                    + " JOIN  Action WHERE numTour=? and idPartie=?";
            PreparedStatement stt= BDManager.getInstance().prepareStatement(requete);
            stt.setInt(1, numTour);
            stt.setInt(2, idPartie);
            ResultSet resultat = stt.executeQuery();
            while(resultat.next()){
                action = new ActionDeplacement(null, Deplacement.valueOf(resultat.getString("SENS")));
                listAction.add(action);
            }
            stt.close();
            resultat.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConcreteFabriqueAction.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return listAction;
    }
    
    
    
    
}
