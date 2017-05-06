/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import bataillenavale.*;

/**
 *
 * @author jaylesr
 */
public class Controleur {
    private Joueur joueur;
    private AbstractPartie partie;

    private static Controleur instance = null;
    
    
    private CtrlAccueil ctrlAccueil;    
    private CtrlInscription ctrlInscription;    
    private CtrlInterface ctrlInterface;    
    private CtrlNouvellePartie ctrlNouvellePartie;    
    private CtrlPartieEnCours ctrlPartieEnCours;    
    private CtrlJoueur ctrlJoueur;
    private CtrlPartieReplay ctrlPartieReplay; 

    
    private Controleur(){
        super();
        ctrlAccueil = new CtrlAccueil();
        ctrlInscription = new CtrlInscription();
        ctrlInterface = new CtrlInterface();
        ctrlNouvellePartie = new CtrlNouvellePartie();
        ctrlPartieEnCours = new CtrlPartieEnCours();
        ctrlJoueur = new CtrlJoueur();
        ctrlPartieReplay = new CtrlPartieReplay();
    }

    public static Controleur getInstance(){
        if(Controleur.instance == null){
            Controleur.instance = new Controleur();
        }
        return Controleur.instance;
    }
    
        public CtrlAccueil getCtrlAccueil() {
        return ctrlAccueil;
    }

    public CtrlInscription getCtrlInscription() {
        return ctrlInscription;
    }

    public CtrlInterface getCtrlInterface() {
        return ctrlInterface;
    }

    public CtrlNouvellePartie getCtrlNouvellePartie() {
        return ctrlNouvellePartie;
    }

    public CtrlPartieEnCours getCtrlPartieEnCours() {
        return ctrlPartieEnCours;
    }
    
    
    public CtrlJoueur getCtrlJoueur() {
        return ctrlJoueur;
    }
    
    
    public Joueur getJoueur() {
        return joueur;
    }

     public AbstractPartie getPartie() {
        return partie;
    }
    
    public CtrlPartieReplay getCtrlPartieReplay() {
        return ctrlPartieReplay;
    }

    public void setCtrlPartieReplay(CtrlPartieReplay ctrlPartieReplay) {
        this.ctrlPartieReplay = ctrlPartieReplay;
    }
    
    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public void setPartie(AbstractPartie partie) {
        this.partie = partie;
    }
}
