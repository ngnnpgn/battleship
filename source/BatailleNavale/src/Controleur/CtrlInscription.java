/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import InterfaceGraphique.Interface;
import InterfaceGraphique.PageInscription;
import bataillenavale.ConcreteFabriqueJoueur;
import bataillenavale.FabriqueJoueur;
import bataillenavale.Joueur;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gounonj
 */
public class CtrlInscription {

    public void annuler() {
        Interface.getInstance().setPageAccueil();
    }

    public void inscription(PageInscription page) {
        Joueur joueur = null;
        try {
            joueur = ConcreteFabriqueJoueur.getInstance().getJoueur(page.getPseudo().getText(),
                    page.getPrenom().getText(),page.getNom().getText(),page.getEmail().getText(),
                    null,0,page.getVille().getText());
        } catch (FabriqueJoueur.pseudoExistantException ex) {
            Logger.getLogger(PageInscription.class.getName()).log(Level.SEVERE, null, ex);
            Interface.getInstance().afficherMessageErreur("Pseudo déjà existant.");
            return;
        }
        Controleur.getInstance().setJoueur(joueur);
        Interface.getInstance().setPageJoueur();
    }
    
}
