/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import InterfaceGraphique.Interface;
import bataillenavale.AbstractPartieEnReplay;
import bataillenavale.*;



/**
 *
 * @author ngnkm
 */
public class CtrlPartieReplay {
        
    
    public CtrlPartieReplay(){
        
    }

    public void boutonEtatSuivant() {
        /*
       Controleur.getInstance().getPartie().getEtatSuivant();
       Interface.getInstance().getPagePartieReplay().afficherGrilleJoueur1(partieReplay.getListeEtatJoueur1());
       Interface.getInstance().getPagePartieReplay().afficherGrilleJoueur2(partieReplay.getListeEtatJoueur2());
        */
        AbstractPartieEnReplay partie = (AbstractPartieEnReplay) Controleur.getInstance().getPartie();
        int tourMax = partie.getTourMax();
        
        if (((PartieEnReplay) partie).getTourEnCours() < tourMax) {
            partie.etatSuivant();
            Interface.getInstance().getPagePartieReplay().afficherGrilleJoueur1(partie.getEtatJoueur1());
            Interface.getInstance().getPagePartieReplay().afficherGrilleJoueur2(partie.getEtatJoueur2());
            Interface.getInstance().getPagePartieReplay().afficherAction(partie.getIdPartie(), ((PartieEnReplay) partie).getTourEnCours());
            Interface.getInstance().getPagePartieReplay().effacerAction(((PartieEnReplay) partie).getTourEnCours());
            Interface.getInstance().getPagePartieReplay().afficherVieJoueur1(partie.getEtatJoueur1());
            Interface.getInstance().getPagePartieReplay().afficherVieJoueur2(partie.getEtatJoueur2());
            Interface.getInstance().getPagePartieReplay().afficherNumTour(partie);
        } else {
            Interface.getInstance().afficherMessageErreur("Vous avez atteint le dernier tour");
        }
    }

    public void boutonEtatPrecedent() {
        AbstractPartieEnReplay partie = (AbstractPartieEnReplay) Controleur.getInstance().getPartie();
       
        if (((PartieEnReplay) partie).getTourEnCours() > 1) {
            partie.etatPrecedent();
            Interface.getInstance().getPagePartieReplay().afficherGrilleJoueur1(partie.getEtatJoueur1());
            Interface.getInstance().getPagePartieReplay().afficherGrilleJoueur2(partie.getEtatJoueur2());
            Interface.getInstance().getPagePartieReplay().afficherAction(partie.getIdPartie(), ((PartieEnReplay) partie).getTourEnCours());
            Interface.getInstance().getPagePartieReplay().effacerAction(((PartieEnReplay) partie).getTourEnCours());
            Interface.getInstance().getPagePartieReplay().afficherVieJoueur1(partie.getEtatJoueur1());
            Interface.getInstance().getPagePartieReplay().afficherVieJoueur2(partie.getEtatJoueur2());
            Interface.getInstance().getPagePartieReplay().afficherNumTour(partie);
        } else {
            Interface.getInstance().afficherMessageErreur("Vous avez atteint le premier tour");
        }
    }
     
    
}
