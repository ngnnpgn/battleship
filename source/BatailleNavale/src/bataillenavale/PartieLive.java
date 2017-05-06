/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bataillenavale;

import Controleur.Controleur;
import InterfaceGraphique.Interface;
import bataillenavale.FabriqueEtat.ExceptionPartiePerdue;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author jaylesr
 */
public class PartieLive extends AbstractPartieLive{
    
    public PartieLive(Joueur j1, Joueur j2, int idPartie){
        super(j1, j2, idPartie);    
    }
    
    /**
     * retourne vrai si c'est au tour du joueur de jouer
     * @param j
     * @return 
     */
    @Override
    public boolean monTour(Joueur j) {
        try {
            System.out.println(j.getPseudo());
            PreparedStatement stt = BDManager.getInstance().prepareStatement("SELECT pseudo FROM Tour WHERE idPartie = ? ORDER BY numTour");
            stt.setInt(1, this.idPartie);
            ResultSet resultat = stt.executeQuery();
            boolean res;
            if(!resultat.last()){
                res = true;
            }else{
                res = !resultat.getString(1).equals(j.getPseudo());
            }
            stt.close();
            resultat.close();
            return res;
            
        } catch (SQLException ex) {
            Logger.getLogger(PartieLive.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * Execute les coups de la liste dans la BD
     * Des exections sont levées si une action est impossible ou si la partie est finie
     * @param listeAction
     * @param joueur
     * @param numeroTour
     * @return
     * @throws bataillenavale.AbstractPartieLive.ExceptionCoupImpossible
     * @throws bataillenavale.FabriqueEtat.ExceptionPartiePerdue 
     */
    @Override
    public LinkedList<int[]> JouerCoup(LinkedList<AbstractAction> listeAction, Joueur joueur, int numeroTour) throws ExceptionCoupImpossible, ExceptionPartiePerdue {
        LinkedList<int[]> listCoupTouché = new LinkedList<int[]>();
        
        try {
            //Ici la création du tour en base de donnée
            BDManager.getInstance().beginTransaction();
            PreparedStatement stt = BDManager.getInstance().prepareStatement("INSERT INTO Tour VALUES(?,?,?)");
            stt.setInt(1, numeroTour);
            stt.setInt(2,this.idPartie);
            stt.setString(3, joueur.getPseudo());
            stt.executeQuery();
            int numeroAction = 0;
            try {
                for (AbstractAction a : listeAction) {
                    try {
                        a.enregistrerAction(this, joueur, numeroTour, numeroAction);
                    }catch (AbstractAction.ExceptionBateauTouche ex) {
                        // Action tir avec un bateau touché
                        // on stock les coorddonnées de tir reussis
                        int[] array= new int[2];
                        array[0]=ex.getCoordX();
                        array[1]=ex.getCoordY();      
                        listCoupTouché.add(array);
                    }
                    numeroAction++;
                }
                BDManager.getInstance().commit();
                stt.close();
            } catch (ExceptionCoupImpossible ex) {
                BDManager.getInstance().rollback();
                stt.close();
                throw new ExceptionCoupImpossible();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PartieLive.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Verification de l'état des bateaux de l'adversaire
        ConcreteFabriqueEtat.getInstance().getEtatEncours(this, this.getAdversaire(joueur));
      
        return listCoupTouché;
    }
    
    /**
     * retourne le numero de tour suivant du joueur
     * @param j
     * @return 
     */
    @Override
    public int getNumeroTourSuivant(Joueur j) {
        try {
            PreparedStatement stt = BDManager.getInstance().prepareStatement("SELECT Numtour FROM Tour WHERE idPartie=? ORDER BY NumTour DESC");
            stt.setInt(1, this.idPartie);
            ResultSet resultat = stt.executeQuery();
            resultat.next();
            int res = resultat.getInt(1)+1;
            resultat.close();
            stt.close();
            return res;
        } catch (SQLException ex) {
            return 0;
        }
    } 
    
    /**
     * enregistre en BD le vainqueur d'une partie
     * @param joueur 
     */
    @Override
    public void finPartie(Joueur joueur) {
        try {
            // ajout du tuple a gagner;
            String requete = "INSERT INTO Agagne values (?,?)";
            PreparedStatement stt = BDManager.getInstance().prepareStatement(requete);
            stt.setString(1,joueur.getPseudo());
            stt.setInt(2, this.idPartie);
            stt.executeQuery();
            stt.close();
        } catch (SQLException ex) {
            Logger.getLogger(PartieLive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String toString()
    {
        return super.toString();
    }

    

   
}
