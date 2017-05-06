/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import InterfaceGraphique.Interface;
import InterfaceGraphique.PageNouvellePartie;
import InterfaceGraphique.PagePartieEnCours;
import bataillenavale.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.sql.converter.I18CharacterConvertersWrapper;

/**
 *
 * @author jocelyn
 */
public class CtrlJoueur {

    public void nouvellePartie() {
        Joueur joueur = Controleur.getInstance().getJoueur();
        try {
            AbstractPartie partie = ConcreteFabriquePartie.getInstance().nouvellePartie(joueur);
            Controleur.getInstance().setPartie(partie);
            Interface.getInstance().setPageNouvellePartie();
        } catch (FabriquePartie.ExceptionAdversaireInexistant ex) {
            Interface.getInstance().afficherMessageInfo("Aucun adversaire trouvé");
        }
        
        
       
    }

    public void partieEnCours(String adversaire) {
        Joueur joueurEnCours = Controleur.getInstance().getJoueur();
        try {
            Joueur joueurAdverse = ConcreteFabriqueJoueur.getInstance().getJoueur(adversaire);
            try {
                PartieLive partie = ConcreteFabriquePartie.getInstance().partieEnCours(joueurEnCours, joueurAdverse);
                Controleur.getInstance().setPartie(partie);
                if (partie.getNumeroTourSuivant(joueurEnCours) == 0 || partie.getNumeroTourSuivant(joueurEnCours) == 1) {

                    Interface.getInstance().setPageNouvellePartie();
                } else {
                    Interface.getInstance().setPagePartieEnCours();
                }

            } catch (FabriquePartie.ExceptionPartieInexistante ex) {
                Interface.getInstance().afficherMessageInfo("Partie Introuvable");
            }
        } catch (FabriqueJoueur.pseudoInexistantException ex) {
            Interface.getInstance().afficherMessageInfo("Adversaire Inexistant");
        }

    }

    public void quitterPageJoueur() {
        //TODO mettre a zero les infos des joueurs
        Interface.getInstance().setPageAccueil();
    }

    public void afficherPartieReplay(String text) {
     
        try {
            PartieEnReplay partieReplay = ConcreteFabriquePartie.getInstance().getPartieEnReplay(Integer.parseInt(text));
            Controleur.getInstance().setPartie(partieReplay);
            for(Etat e : partieReplay.getEtatJoueur1()){
                System.out.println("id : " + e.getBateau().getIdBateau());
            }
            System.out.println(" -------- ");
            for (Etat e : partieReplay.getEtatJoueur2()){
                System.out.println("id : " + e.getBateau().getIdBateau());
            }
            Interface.getInstance().getPagePartieReplay().afficherGrilleJoueur1(partieReplay.getEtatJoueur1());
            Interface.getInstance().getPagePartieReplay().afficherGrilleJoueur2(partieReplay.getEtatJoueur2());
        } catch (FabriquePartie.ExceptionPartieInexistante ex) {
            //TODO
            Logger.getLogger(CtrlJoueur.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }

    public void partieReplay(int idPartie) {

        try {
            PartieEnReplay partieReplay = ConcreteFabriquePartie.getInstance().getPartieEnReplay(idPartie);
            Controleur.getInstance().setPartie(partieReplay);
            Controleur.getInstance().setPartie(partieReplay);
            Interface.getInstance().setPagePartieReplay();
            Interface.getInstance().getPagePartieReplay().afficherGrilleJoueur1(partieReplay.getEtatJoueur1());
            Interface.getInstance().getPagePartieReplay().afficherGrilleJoueur2(partieReplay.getEtatJoueur2());
            Interface.getInstance().getPagePartieReplay().afficherVieJoueur1(partieReplay.getEtatJoueur1());
            Interface.getInstance().getPagePartieReplay().afficherVieJoueur2(partieReplay.getEtatJoueur2());
            Interface.getInstance().getPagePartieReplay().afficherNumTour(partieReplay);
        } catch (FabriquePartie.ExceptionPartieInexistante ex) {
            //TODO
            Logger.getLogger(CtrlJoueur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
