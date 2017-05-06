/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import InterfaceGraphique.Interface;
import InterfaceGraphique.PageAccueil;
import bataillenavale.ConcreteFabriqueJoueur;
import bataillenavale.FabriqueJoueur;
import bataillenavale.Joueur;

/**
 *
 * @author gounonj
 */
public class CtrlAccueil {

    public CtrlAccueil(){
        
    }
    
    public void inscription() {
        Interface.getInstance().setPageInscription();
    }

    public void connexion(PageAccueil page) {        
        Joueur joueur = null;
        try {
            joueur = ConcreteFabriqueJoueur.getInstance().getJoueur(page.getPseudo().getText());
            Controleur.getInstance().setJoueur(joueur);
        } catch (FabriqueJoueur.pseudoInexistantException ex) {
            System.out.println("Pseudo Introuvable");
            Interface.getInstance().afficherMessageErreur("Pseudo Introuvable");
            return;
        }
        //TODO Passer les pages en singleton
        Interface.getInstance().setPageJoueur();
    }
}
