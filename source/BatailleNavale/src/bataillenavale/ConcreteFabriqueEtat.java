/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bataillenavale;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pierre
 */
public class ConcreteFabriqueEtat implements FabriqueEtat {

	private static ConcreteFabriqueEtat instance = null;

        /**
         * Constructeur
         */
	private ConcreteFabriqueEtat() {
		super();
	}

        /**
         * getInstance
         * @return 
         */
	public static ConcreteFabriqueEtat getInstance() {
		if (ConcreteFabriqueEtat.instance == null) {
			ConcreteFabriqueEtat.instance = new ConcreteFabriqueEtat();
		}
		return ConcreteFabriqueEtat.instance;
	}

        /**
         * 
         * @param partie
         * @param joueur
         * @return
         * @throws bataillenavale.FabriqueEtat.ExceptionPartiePerdue 
         */
        public LinkedList<Etat> getEtatEncours(AbstractPartie partie, Joueur joueur) throws ExceptionPartiePerdue{
            //TODO
            Etat etatBateau;
            int vieTotale = 0;
            LinkedList<Etat> listeEtat = new LinkedList<Etat>();
            LinkedList<AbstractBateau> listeBateau = ConcreteFabriqueBateau.getInstance().getBateau(partie.getIdPartie(), joueur);
            for(AbstractBateau b : listeBateau){
                etatBateau = ConcreteFabriqueEtat.getInstance().getEtatBateau(partie,b);
                listeEtat.add(etatBateau);
                vieTotale = vieTotale + etatBateau.getVie();
            }
            if(vieTotale == 0){
                throw new ExceptionPartiePerdue();
            }
            
            return listeEtat;
        }
        
        /**
         * 
         * @param partie
         * @param bateau
         * @return 
         */
        public Etat getEtatBateau(AbstractPartie partie, AbstractBateau bateau) {
            try {
                int idLastAction = 0;
                PreparedStatement stt = BDManager.getInstance().prepareStatement("SELECT e.x, e.y, e.vie, e.direction, e.taille FROM Etat e CROSS JOIN Action a WHERE e.idAction = a.idAction AND e.numAction = a.numAction AND e.idBateau = ? ORDER BY a.numTour DESC, a.numAction DESC");
                stt.setInt(1, bateau.getIdBateau());
                ResultSet resultat = stt.executeQuery();
                
                if(resultat.first()){
                    int x = resultat.getInt(1);
                    int y = resultat.getInt(2);
                    int vie =resultat.getInt(3);
                    String direction = resultat.getString(4);
                    int taille = resultat.getInt(5);
                    System.out.println("Etat pour bateau : " + bateau.getIdBateau() + "x : " + x + " y : " + y + " Direction : " + Direction.valueOf(direction)+" vie= "+ vie);
                    bateau.setDirection(Direction.valueOf(direction));
                    bateau.setCoordX(x);
                    bateau.setCoordY(y);
                    bateau.setVie(vie);
                    bateau.setTaille(taille);
                    stt.close();
                    resultat.close();
                    return new Etat(bateau,x,y,vie,Direction.valueOf(direction),taille);
                }
                stt.close();
                resultat.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConcreteFabriqueEtat.class.getName()).log(Level.SEVERE, "getEtatBateau failed", ex);
            }
            
            return null;
        }
        
        /**
         * Permet de remplir les etats de la PartieEnReplay avec les états initiaux de la BD
         * @param partie 
         */
        public void getEtatInitiaux(PartieEnReplay partie){
            try {
                Joueur j1 = partie.getJoueur1();
                Joueur j2 = partie.getJoueur2();
                PreparedStatement stt = BDManager.getInstance().prepareStatement("SELECT numTour, pseudo FROM Tour WHERE idPartie=? ORDER BY NumTour");
                stt.setInt(1, partie.getIdPartie());
                ResultSet resultat = stt.executeQuery();
                resultat.next();
                j1.setPseudo(resultat.getString(2));
                resultat.next();
                j2.setPseudo(resultat.getString(2));
                /*<AbstractBateau> listeBateau1 = ConcreteFabriqueBateau.getInstance().getBateau(partie.getIdPartie(), j1);
                LinkedList<AbstractBateau> listeBateau2 = ConcreteFabriqueBateau.getInstance().getBateau(partie.getIdPartie(), j2);*/
                partie.setEtatJoueur1(getEtatInitial(0, partie.getIdPartie()));
                partie.setEtatJoueur2(getEtatInitial(1, partie.getIdPartie()));
                stt.close();
                resultat.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConcreteFabriqueEtat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        /**
         * Permet d'obtenir les état initiaux des bateaux pour le joueur qui a  joué le tour (le numéro de tour doit être 0 ou 1)
         */
        private LinkedList<Etat> getEtatInitial(int tour, int idPartie){
            LinkedList<Etat> listeEtat = new LinkedList<Etat>();
        
            try {
                
                PreparedStatement stt = BDManager.getInstance().prepareStatement("SELECT e.idBateau, x,y,taille,direction,vie FROM Action a CROSS JOIN Etat e WHERE idPartie=? AND numTour=? AND a.idAction = e.idAction ORDER BY e.numAction");
                stt.setInt(1, idPartie);
                stt.setInt(2, tour);
                ResultSet resultat = stt.executeQuery();
                //on va épuiser les résultats
                AbstractBateau bateau;
                Etat etat;
                int idBateau;
                int taille;
                int x;
                int y;
                int vie;
                Direction direction;
                while (resultat.next()) {
                    idBateau = resultat.getInt(1);
                    x = resultat.getInt(2);
                    y = resultat.getInt(3);
                    taille = resultat.getInt(4);
                    direction = Direction.valueOf(resultat.getString(5));
                    vie = resultat.getInt(6);
                    if (taille == 3) {
                        bateau = new BateauDestroyer(idBateau, x, y, direction);
                        bateau.setVie(vie);
                    } else {
                        bateau = new BateauEscorteur(idBateau, x, y, direction);
                        
                    }
                    etat = new Etat(bateau, x, y, y, direction, taille);
                    etat.setVie(vie);
                    listeEtat.add(etat);
                }
                stt.close();
                resultat.close();
                return listeEtat;
            } catch (SQLException ex) {
                Logger.getLogger(ConcreteFabriqueEtat.class.getName()).log(Level.SEVERE, null, ex);
            }
                return listeEtat;
        }
        
        /**
         * Permet d'avoir les état crées lors d'un tour. Attention ces états peuvent appartenir à des bateaux des deux camps
         * @param numTour
         * @return 
         */
        public LinkedList<Etat> getEtatCrees(int idPartie, int tour){
            LinkedList<Etat> listeEtat = new LinkedList<Etat>();
            try {
                
                
                PreparedStatement stt = BDManager.getInstance().prepareStatement("SELECT e.idBateau, x,y,taille,direction,vie FROM Action a CROSS JOIN Etat e WHERE idPartie=? AND numTour=? AND a.idAction = e.idAction ORDER BY e.numAction");
                stt.setInt(1, idPartie);
                stt.setInt(2, tour);
                ResultSet resultat = stt.executeQuery();
                //on va épuiser les résultats
                AbstractBateau bateau;
                Etat etat;
                int idBateau;
                int taille;
                int x;
                int y;
                int vie;
                Direction direction;
                while (resultat.next()) {
                    idBateau = resultat.getInt(1);
                    x = resultat.getInt(2);
                    y = resultat.getInt(3);
                    taille = resultat.getInt(4);
                    direction = Direction.valueOf(resultat.getString(5));
                    vie = resultat.getInt(6);
                    if (taille == 3) {
                        bateau = new BateauDestroyer(idBateau, x, y, direction);
                        bateau.setVie(vie);
                    } else {
                        bateau = new BateauEscorteur(idBateau, x, y, direction);
                        
                    }
                    etat = new Etat(bateau, x, y, y, direction, taille);
                    etat.setVie(vie);
                    listeEtat.add(etat);
                }
                return listeEtat;
            } catch (SQLException ex) {
                Logger.getLogger(ConcreteFabriqueEtat.class.getName()).log(Level.SEVERE, null, ex);
            }
            return listeEtat;
        }
        
}