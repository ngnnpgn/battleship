/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bataillenavale;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thomas
 */
public class ConcreteFabriqueBateau implements FabriqueBateau{
    
    private static ConcreteFabriqueBateau instance = null;
    
    public static ConcreteFabriqueBateau getInstance(){
        if(instance == null){
            instance = new ConcreteFabriqueBateau();
        }
        return instance;
    }

    /**
     * Permet d'obtenir la liste des bateaux d'un joueur pour une partie donnée
     * @param idPartie
     * @param joueur
     * @return 
     */
    @Override
    public LinkedList<AbstractBateau> getBateau(int idPartie, Joueur joueur){
        int id=1;
        LinkedList<AbstractBateau> listeBateau = new LinkedList<AbstractBateau>();
        try {
            // reccuperation du numero de tour relatif au placement des bateaux au début de la partie
            String requete = "SELECT MIN(numTour) FROM Tour t WHERE t.pseudo = ? AND t.idPartie= ?";
            ResultSet reponse = null;
            PreparedStatement stt1;
            ResultSet reponse2 = null;
            PreparedStatement stt2 = null;
            
            stt1 = BDManager.getInstance().prepareStatement(requete);
            stt1.setString(1, joueur.getPseudo());
            stt1.setInt(2, idPartie);
            
            reponse = stt1.executeQuery();
            if (reponse.next()) {
                // reccuperation des idBateaux
                try {
                    requete = "SELECT DISTINCT idBateau FROM Action WHERE idPartie =? "
                            + " AND numTour = ?"; 
                    stt2 = BDManager.getInstance().prepareStatement(requete);
                    stt2.setInt(1, idPartie);
                    stt2.setInt(2, reponse.getInt(1));
                    reponse2 = stt2.executeQuery();
                } catch (Exception ex) {
                    Logger.getLogger(ConcreteFabriqueBateau.class.getName()).log(Level.SEVERE, "reccuperation des idBateau impossible", ex);
                }
                // creation des bateaux
                try {
                    while (reponse2.next()) {
                        id = reponse2.getInt("idBateau");
                        listeBateau.add(NewBateau(id));
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ConcreteFabriqueBateau.class.getName()).log(Level.SEVERE, null, ex);
                }
                reponse.close();
                reponse2.close();
                stt1.close();
                stt2.close();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ConcreteFabriqueBateau.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listeBateau;
    }
    
    /**
     * Pour récuperer un bateau en base de donnée
     * @param idBateau
     * @return 
     */
    public AbstractBateau NewBateau(int idBateau) throws SQLException{
        String requete = "SELECT Type FROM Bateau b where b.idBateau = ? ";
        PreparedStatement stt = BDManager.getInstance().prepareStatement(requete);
        stt.setInt(1, idBateau);       
        ResultSet reponse = stt.executeQuery();
        reponse.next();
        if(reponse.getString(1).equals("destroyer")){
            reponse.close();
            stt.close();
            return new BateauDestroyer(idBateau);
        }else{
            reponse.close();
            stt.close();
            return new BateauEscorteur(idBateau);
        }        
    }
    
    /**
     * Pour créer un bateau en base de donnée
     * @param joueur
     * @return 
     */
    @Override
    public BateauDestroyer getDestroyer(Joueur joueur) {
        int idBateau = 0;
        String requete = "INSERT INTO Bateau values (sequence_bateau.nextval,'destroyer',?)";
        
        try {
            PreparedStatement stt = BDManager.getInstance().prepareStatement(requete);
            stt.setString(1, joueur.getPseudo());
            stt.executeQuery();
            stt.close();
            
            requete = "SELECT sequence_bateau.currval from Bateau";
            stt = BDManager.getInstance().prepareStatement(requete);
            ResultSet resultat = stt.executeQuery();
            resultat.next();
            idBateau=resultat.getInt(1);
            resultat.close();
            stt.close();
         } catch (SQLException ex) {
            Logger.getLogger(ConcreteFabriqueBateau.class.getName()).log(Level.SEVERE, "Erreur lors de la creation d'un destroyer", ex);
        }
        return new BateauDestroyer(idBateau);
    }

    @Override
    public BateauEscorteur getEscorteur(Joueur joueur) {
        int idBateau = 0;
        String requete = "INSERT INTO Bateau values (sequence_bateau.nextval,'escorteur',?)";    
        try {
            PreparedStatement stt = BDManager.getInstance().prepareStatement(requete);
            stt.setString(1,joueur.getPseudo() );
            stt.executeQuery();
            stt.close();

            requete = "SELECT sequence_bateau.currval from Bateau";
            stt = BDManager.getInstance().prepareStatement(requete);
            ResultSet resultat = stt.executeQuery();
            resultat.next();
            idBateau=resultat.getInt(1);
            resultat.close();
            stt.close();
         } catch (SQLException ex) {
            Logger.getLogger(ConcreteFabriqueBateau.class.getName()).log(Level.SEVERE, "Erreur lors de la creation d'un destroyer", ex);
        }
        return new BateauEscorteur(idBateau);

    }

    /**
     * Permet de créer le bateau mais aussi l'acion qui correspond au placement de ce batea
     * @param joueur
     * @param x
     * @param y
     * @param direction
     * @return l'action à effectuer en base de donnée pour placer le bateau
     */
    @Override
    public ActionPlacement placerDestroyer(Joueur joueur, int x, int y, Direction direction) {
        BateauDestroyer bateau = getDestroyer(joueur);
        bateau.setCoordX(x);
        bateau.setCoordY(y);
        bateau.setTaille(3);
        bateau.setDirection(direction);
        return new ActionPlacement(x,y, bateau);
    }

    @Override
    public ActionPlacement placerEscorteur(Joueur joueur, int x, int y, Direction direction) {
        BateauEscorteur bateau = getEscorteur(joueur);
        bateau.setCoordX(x);
        bateau.setCoordY(y);
        bateau.setDirection(direction);
        bateau.setTaille(2);
        return new ActionPlacement(x,y, bateau);
    }
}