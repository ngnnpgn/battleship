/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import InterfaceGraphique.Interface;
import InterfaceGraphique.PageNouvellePartie;
import InterfaceGraphique.PagePartieEnCours;
import bataillenavale.AbstractAction;
import bataillenavale.AbstractPartie;
import bataillenavale.ConcreteFabriqueBateau;

import bataillenavale.*;
import bataillenavale.Direction;
import bataillenavale.Joueur;
import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gounonj
 */
public class CtrlNouvellePartie {

    private Direction direction;
    private int taille=0;
    private boolean selection=false;
    private int compteurBateau=0;
    private LinkedList<AbstractAction> listeAction; 
    private int NombreEscorteurExistant = 0;
    public CtrlNouvellePartie(){
        this.listeAction = new LinkedList<AbstractAction>();
    }
    
    public void nordAction() {
        direction = Direction.NORD;
    }

    public void sudAction() {
        direction = Direction.SUD;
    }

    public void estdAction() {
        direction = Direction.EST;
    }

    public void ouestAction() {
        direction = Direction.OUEST;
    }

    public void selectionDestroyer(PageNouvellePartie page) {
        if(selection){
            return;
        }
        page.enabledBouton(page.getDestroyer());
        taille=3;        
        selection=true;
        
    }

    public void selectionEscorteur1(PageNouvellePartie page) {
        if(selection){            
            return;
        }
        page.enabledBouton(page.getEscorteur1());
        taille=2;
        selection=true;
    }

    public void selectionEscorteur2(PageNouvellePartie page) {
        if(selection){
            return;
        }
        page.enabledBouton(page.getEscorteur2());
        taille=2;         
        selection=true;
    }

    public void valider(PageNouvellePartie page){
        if(compteurBateau>1 && !page.getDestroyer().isEnabled() && taille==0){

            
		//mise a jour de la bd
            AbstractPartieLive partie = (AbstractPartieLive) Controleur.getInstance().getPartie();      
            try {
                partie.JouerCoup(listeAction, Controleur.getInstance().getJoueur(), partie.getNumeroTourSuivant(Controleur.getInstance().getJoueur()));
                //TODO rectification bug, a vérifier !
                direction=null;
                taille=0;
                selection=false;
                compteurBateau=0;
                listeAction = new LinkedList<AbstractAction>(); 
                Interface.getInstance().setPagePartieEnCours();
            } catch (AbstractPartieLive.ExceptionCoupImpossible ex) {
                Logger.getLogger(CtrlNouvellePartie.class.getName()).log(Level.SEVERE, "Coup impossible", ex);
            } catch (FabriqueEtat.ExceptionPartiePerdue ex) {
                Logger.getLogger(CtrlNouvellePartie.class.getName()).log(Level.SEVERE, "la partie est deja finie", ex);
            }
        }else{ 
            Interface.getInstance().afficherMessageInfo("Placez vos bateaux avant de valider.");
        }    
    }

    public void panelClicked(PageNouvellePartie page, int index) {
       int x=index%10+1;
       int y= index/10+1;
       
       if(taille==0 || !selection || direction==null) return ;
       if(placementBateau(direction,x,y,taille)){      
            page.getJPanelTab(index).setBackground(Color.blue);       
            switch(direction){
                case NORD : 
                    page.getJPanelTab(index-10).setBackground(Color.red);
                    if(taille==3){
                        page.getJPanelTab(index-20).setBackground(Color.red);
                    }
                    break;
                case SUD :
                    page.getJPanelTab(index+10).setBackground(Color.red);
                    if(taille==3){
                        page.getJPanelTab(index+20).setBackground(Color.red);
                    }
                    break;
                case EST : 
                    page.getJPanelTab(index+1).setBackground(Color.red);
                    if(taille==3){
                        page.getJPanelTab(index+2).setBackground(Color.red);
                    }
                    break;
                case OUEST :
                    page.getJPanelTab(index-1).setBackground(Color.red);
                    if(taille==3){
                        page.getJPanelTab(index-2).setBackground(Color.red);
                    }
                    break;
                default :
                    return;
            }
            selection=false;
            taille=0;
            compteurBateau++;
       }else{
          Interface.getInstance().afficherMessageErreur("Position invalide.");
      }
    }
    
    /**
     * Place le bateau en base de donnée
     * @param direction
     * @param x
     * @param y
     * @param taille
     * @return 
     */
    private boolean placementBateau(Direction direction, int x, int y,int taille){
        Joueur joueur = Controleur.getInstance().getJoueur();

        if(!verifierPlacement(x, y, direction, taille)){
            return false;
        }
        if(taille==3){
            listeAction.add(ConcreteFabriqueBateau.getInstance().placerDestroyer(joueur, x, y, direction));
        }else{
            listeAction.add(ConcreteFabriqueBateau.getInstance().placerEscorteur(joueur, x, y, direction));
        }
        return true;
    }
    
    

    /**
     * 
     * @param x la position x du pivot
     * @param y la position y du pivot
     * @param direction
     * @return renvoie vrai si aucun bateau ne se superpose 
     */
    private boolean verifierPlacement(int x, int y, Direction direction, int taille) {
        AbstractPartie partie = Controleur.getInstance().getPartie();
        Joueur joueur = Controleur.getInstance().getJoueur();

        boolean grille[][] = new boolean[11][11];
        for (AbstractAction action : listeAction) {

            try {
                for (Integer[] coord : action.getBateau().getCoordonees()) {
                    grille[coord[0]][coord[1]] = true;
                }
            } catch (AbstractPartieLive.ExceptionCoupImpossible ex) {
                return false;
            }

        }
        AbstractBateau bateau;
        if (taille == 3) {
            bateau = new BateauDestroyer(0, x, y, direction);
        } else {
            bateau = new BateauEscorteur(0, x, y, direction);
        }

        try {
            for (Integer[] coord : bateau.getCoordonees()) {
                if (grille[coord[0]][coord[1]]) {
                    return false;
                }
            }
        } catch (AbstractPartieLive.ExceptionCoupImpossible ex) {
            return false;
        }
        return true;

    }

}
