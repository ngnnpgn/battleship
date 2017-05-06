/**
 * Acion spécifique au tir, utilisé pour la patron stratégie
 * 
 */
package bataillenavale;

import bataillenavale.AbstractAction.*;
import bataillenavale.AbstractPartieLive.ExceptionCoupImpossible;
import bataillenavale.FabriqueEtat.ExceptionPartiePerdue;
import java.awt.PageAttributes;
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
public class ActionTir extends AbstractAction{
    int coordX;

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }
    int coordY;

    public ActionTir(AbstractBateau bateau, int coordX, int coordY) {
        super(bateau);
        this.coordX=coordX;
        this.coordY=coordY;       
    }
    
    
    
    /**
     * Pour enregistrer l'action et le nouvel état en base de donnée
     * @param partie
     * @param joueur
     * @param numTour
     * @param numAction
     * @throws bataillenavale.AbstractPartieLive.ExceptionCoupImpossible
     * @throws bataillenavale.AbstractAction.ExceptionBateauTouche 
     */
    @Override
    public void enregistrerAction(AbstractPartieLive partie, Joueur joueur, int numTour, int numAction) throws ExceptionCoupImpossible,ExceptionBateauTouche{
        
        //création du tir dans la base de donnée
        try {
            super.enregistrerAction(partie, joueur, numTour, numAction);
            int idAction = 0;
            ResultSet resultat;
            PreparedStatement stt;
            String requete;
            
            try {
                requete = "SELECT sequence_action.currval FROM Action";
                stt = BDManager.getInstance().prepareStatement(requete);
                resultat = stt.executeQuery();
                resultat.next();
                idAction = resultat.getInt(1);
                resultat.close();
                stt.close();
            } catch (SQLException ex) {
                Logger.getLogger(ActionTir.class.getName()).log(Level.SEVERE, "La recuperation de idAction a echoué", ex);
            }
            
            // creation du tuple ActionTir
            try {
                requete = "INSERT INTO ActionTir VALUES ("+idAction+","+coordX+","+coordY+")";
                stt = BDManager.getInstance().prepareStatement(requete);
                stt.executeQuery(requete);
                stt.close();
            } catch (SQLException ex) {
                Logger.getLogger(ActionTir.class.getName()).log(Level.SEVERE, "La creation de ActionTir a echoué", ex);
            }
            
            // Recherche si un bateau adverse est present sur cette case
            // list bateau du joueur adverse : 
            LinkedList<Etat> listEtatBateau=null;
            
            listEtatBateau = ConcreteFabriqueEtat.getInstance().getEtatEncours(partie, partie.getAdversaire(joueur));
            
            /**
             * Si le bateau est touché, on enregistre un nouvel état dans la base de donnée, et on lève l'exception pour informer que le coup a touché
             */
            AbstractBateau bateauAdverse;
            for(Etat e: listEtatBateau){
                if(e.estVivant()){
                    if(e.estTouche(coordX, coordY)){
                        // TODO MAJ etat bateau touché
                        e.getBateau().afficheEtat();

                        bateauAdverse = e.getBateau();
                        bateauAdverse.setVie(bateauAdverse.getVie()-1);
                        e.setVie(e.getVie()-1);
                        stt = BDManager.getInstance().prepareStatement("INSERT INTO Etat VALUES(sequence_etat.nextval,?,?,?,?,?,?,?,?)");
                        stt.setInt(1, bateauAdverse.getCoordX());
                        stt.setInt(2, bateauAdverse.getCoordY());
                        stt.setString(3, bateauAdverse.getDirection().toString());
                        stt.setInt(4, bateauAdverse.getVie());
                        stt.setInt(5, idAction);
                        stt.setInt(6,numAction);
                        stt.setInt(7,bateauAdverse.getIdBateau());
                        stt.setInt(8, bateauAdverse.getTaille());
                        stt.executeQuery();
                        stt.close();
                        throw new ExceptionBateauTouche(coordX,coordY);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActionTir.class.getName()).log(Level.SEVERE, "action tir failed", ex);
        } catch (ExceptionPartiePerdue ex) {
            Logger.getLogger(ActionTir.class.getName()).log(Level.FINE, "Partie Perdue", ex);
            partie.finPartie(joueur);
        }
               
    }

}
