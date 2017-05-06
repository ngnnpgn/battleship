/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import InterfaceGraphique.*;
import bataillenavale.*;
import bataillenavale.FabriqueEtat.ExceptionPartiePerdue;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author gounonj
 */
public class CtrlPartieEnCours {
    
    /**
     * en 0 :  le destroyer
     * en 1 : l'escorteur
     * en 2 : le 2ème escorteur
     */
    private boolean tirerSelection = false;
    private boolean monTour;
    private AbstractBateau[] tabBateau = new AbstractBateau[3];
    private boolean[] tabBateauTouche = new boolean[3];
    private int[] actionRestante = new int[3];
    private AbstractBateau bateau;
    private int indexBateau;
    VerificationDeplacement verification;
    private LinkedList<AbstractAction> listeAction;
    
    public CtrlPartieEnCours(){
        this.listeAction = new LinkedList<AbstractAction>();
    }
    
    private void init()
    {
        tirerSelection = false;
        tabBateau = new AbstractBateau[3];
        tabBateauTouche = new boolean[3];
        actionRestante = new int[3];
    }
    public LinkedList<Etat> rafraichirEtat() throws ExceptionPartiePerdue{
        this.listeAction = new LinkedList<AbstractAction>();
        int indexEscorteur = 1;
        Joueur joueur = Controleur.getInstance().getJoueur();
        AbstractPartieLive partie = (AbstractPartieLive) Controleur.getInstance().getPartie();
        
        LinkedList<Etat> listeBateau = ConcreteFabriqueEtat.getInstance().getEtatEncours(partie, joueur);
        for (Etat e : listeBateau) {
            if (e.getTaille() == 3) {
                tabBateau[0] = e.getBateau();
                actionRestante[0] = tabBateau[0].getVie();
            } else {
                tabBateau[indexEscorteur] = e.getBateau();
                actionRestante[indexEscorteur] = tabBateau[indexEscorteur].getVie();
                indexEscorteur++;
            }
        }
        verification = new VerificationDeplacement(tabBateau);
        
        if(!partie.monTour(joueur)){
            Interface.getInstance().afficherMessageInfo("Ce n'est pas à vous de jouer");
            Interface.getInstance().getPagePartieEnCours().enableBouton(false);
            Interface.getInstance().getPagePartieEnCours().enableRafraichir(true);
           
        }else{
            int numTour = (partie.getNumeroTourSuivant(joueur));
            affichageActionAdverse(numTour);
           
            Interface.getInstance().getPagePartieEnCours().enableBouton(true);
            Interface.getInstance().getPagePartieEnCours().enableRafraichir(false);
        }
        
        return listeBateau;
            
        //afficherVieBateau();
        
    }
    
    public void rafraichirAffichageEtatBateau(){
        init();
        try {
            LinkedList<Etat> listeBateau = rafraichirEtat();
            Interface.getInstance().getPagePartieEnCours().afficherGrille(listeBateau);
            /*if(!partie.monTour(joueur)){
                Interface.getInstance().afficherMessageInfo("Ce n'est pas à vous de jouer");
                

                int numTour = (partie.getNumeroTourSuivant(joueur));//partie.getAdversaire(joueur)));

                affichageDeplacementAdverse(numTour);
            }
            /*
            if(partie.monTour(joueur)){
                affichageResultatTirsAdverse();
            }*/
            afficherVieBateau();
        } catch (FabriqueEtat.ExceptionPartiePerdue ex) {
            Interface.getInstance().afficherMessageInfo("Partie perdue :( jjj");
            Interface.getInstance().setPageJoueur();
        }
        
    }
    
    public void decompterActionBateau(){
        this.actionRestante[indexBateau]--;
    }
    public void deplacement (PagePartieEnCours page,Deplacement deplacement)
    {
        if(tirerSelection){
            tirerSelection=false;
            Interface.getInstance().getPagePartieEnCours().initialiserTirer();
        }
        if(this.actionRestante[indexBateau] > 0){
            
            if(verification.verifier(indexBateau,deplacement)){
                listeAction.add(new ActionDeplacement(bateau, deplacement));
                this.decompterActionBateau();
            }
            else{
                Interface.getInstance().afficherMessageInfo("Deplacement impossible");
            }
            
        }else{
            Interface.getInstance().afficherMessageInfo("Plus assez de points d'action pour ce bateau");
        }
    }
    public void tournerDroite(PagePartieEnCours page) {
        deplacement(page, Deplacement.DROITE);
    }

    public void tournerGauche(PagePartieEnCours page) {
        deplacement(page, Deplacement.GAUCHE);
    }
    
    public void reculer(PagePartieEnCours page) {
	deplacement(page, Deplacement.ARRIERE);
    }

    public void avancer(PagePartieEnCours page) {
        deplacement(page, Deplacement.AVANT);
    }

    public void afficherCoupRestant(){
        Interface.getInstance().getPagePartieEnCours().afficherCoupRestant(actionRestante[indexBateau]);
    }
    
    public void selectionEscorteur2(PagePartieEnCours page) {
        System.out.println("selection escorteur");
        Interface.getInstance().getPagePartieEnCours().selectionnerEscorteur2();
        if(tabBateau[2] == null){
            Interface.getInstance().afficherMessageInfo("Vous n'avez qu'un escorteur");
        }else{
            bateau = tabBateau[2];
            this.indexBateau = 2;
            afficherCoupRestant();
            afficherVieBateau();
        }
    }

    public void selectionEscorteur1(PagePartieEnCours page) {
       System.out.println("selection escorteur1");

       Interface.getInstance().getPagePartieEnCours().selectionnerEscorteur1();
       bateau = tabBateau[1];
       this.indexBateau = 1;
       afficherCoupRestant();
    }

    public void selectionDestroyer(PagePartieEnCours page) {
        System.out.println("selection destroyeur");

        Interface.getInstance().getPagePartieEnCours().selectionnerDestroyer();
        bateau = tabBateau[0];
        this.indexBateau = 0;
        afficherCoupRestant();
        afficherVieBateau();
    }
    
    public void afficherVieBateau(){
        int vieDestroyeur=0;
        int vieEscorteur1=0;
        int vieEscorteur2=0;
        
        vieDestroyeur = tabBateau[0].getVie();
        vieEscorteur1 = tabBateau[1].getVie();
        if(tabBateau[2] != null){
            vieEscorteur2 = tabBateau[2].getVie();
        }
        
        
        Interface.getInstance().getPagePartieEnCours().
                afficherVie(vieDestroyeur, vieEscorteur1, vieEscorteur2);
   }
    
    public void tirer(PagePartieEnCours page) {
        this.tirerSelection=true;
        Interface.getInstance().getPagePartieEnCours().afficherTirer();
        
    }
    
    
    public void retourPageJoueur() {
        
        Interface.getInstance().setPageJoueur();
    }
    

    
 
    public void selectionCase(JPanel jPanelTabAdversaire, int j) {

        if (this.actionRestante[indexBateau] > 0) {
            if (tirerSelection) {
                tirerSelection = false;
                Interface.getInstance().getPagePartieEnCours().afficherCase(j);
                Interface.getInstance().getPagePartieEnCours().initialiserTirer();
                decompterActionBateau();
                System.out.println("Le bateau" + bateau.getIdBateau() + " a tiré sur la case : x : " + (j % 10 + 1) + ", y : " + (j / 10 + 1) + "  :)  77RPZ ");
                listeAction.add(new ActionTir(bateau, j % 10 + 1, j / 10 + 1));
            }
        } else {
            Interface.getInstance().afficherMessageInfo("Plus assez de points d'action pour ce bateau");
            
        }

    }

    /*public void rafraichirPartie() {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    public void validerActions() {
        AbstractPartieLive partie = (AbstractPartieLive) Controleur.getInstance().getPartie();
        Joueur joueur = Controleur.getInstance().getJoueur();
        
        if(!partie.monTour(joueur)){
            Interface.getInstance().afficherMessageInfo("Ce n'est pas à vous de jouer");
            return;
        }
        try {
            LinkedList<int[]> coordBateauxTouches = partie.JouerCoup(listeAction, Controleur.getInstance().getJoueur(), partie.getNumeroTourSuivant(Controleur.getInstance().getJoueur()));
            affichageResultatTirs(coordBateauxTouches);
            rafraichirAffichageEtatBateau();
        }catch (AbstractPartieLive.ExceptionCoupImpossible ex) {
            Interface.getInstance().afficherMessageInfo("Coups Impossible, veuillez rejouer");
            rafraichirAffichageEtatBateau();
        }
        catch(FabriqueEtat.ExceptionPartiePerdue ex){
            partie.finPartie(joueur);
            Interface.getInstance().afficherMessageInfo("Partie gagnée !!");
            Interface.getInstance().setPageJoueur();
        }
        this.listeAction = new LinkedList<AbstractAction>();
    }
    
    /**
     * Affiche le resultat des tirs du joueur
     * @param coordBateauxTouches 
     */
    private void affichageResultatTirs(LinkedList<int[]> coordBateauxTouches){
        String message = null;
        if (coordBateauxTouches.isEmpty()) {
            message = "Aucun bateau touché";
        } else {
            message = "Resutat des tirs : \n ";

            for (int[] a : coordBateauxTouches) {
                message += "x = " + a[0] + " y = " + a[1] + "\n";
            }
        }
        Interface.getInstance().afficherMessageInfo(message);
    }
    
    /**
     * Affiche le resultat des deplacements adverses
     */
    private String afficheDeplacementAdverse(int numTour){
        String message = "";
        int idPartie = Controleur.getInstance().getPartie().getIdPartie();
        LinkedList<AbstractAction> listAction = ConcreteFabriqueAction.getInstance().getActionDeplacement(idPartie, numTour);
        
        for(AbstractAction a : listAction){
            message+= " un bateau a effectué un deplacement "+((ActionDeplacement)a).getDeplacement().toString()+'\n';
        }
        if (message.equals("")){
           message = "Aucun Deplacement ennemi \n";
        }else{
           message="Deplacement(s) Adverse :\n" + message;
        }  
        
        return message;
        //Interface.getInstance().afficherMessageInfo(message);
    }
    
    private String afficheTirAdverse(int numTour){
        String message = "";
        int idPartie = Controleur.getInstance().getPartie().getIdPartie();
        LinkedList<AbstractAction> listAction = ConcreteFabriqueAction.getInstance().getActionTir(idPartie, numTour);
        
        for(AbstractAction a : listAction){
            message+= "  x = "+ ((ActionTir)a).getCoordX() +"y = "+((ActionTir)a).getCoordY()+"\n";
        }
        if (message.equals("")){
           message = "\nAucun tir ennemi\n";
        }else{
           message="\ntir(s) Adverse\n" + message;
        }  
        return message;
        //Interface.getInstance().afficherMessageInfo(message);
    }
    
    private void setTabBateauTouche(int index){
        int nouvelleVie = tabBateau[index].getVie();
        if (actionRestante[index] != nouvelleVie) {
            tabBateauTouche[index] = true;
        }
    }
    private void affichageActionAdverse(int numTour) {
        String message = afficheDeplacementAdverse(numTour-1);
        message += afficheTirAdverse(numTour-1);
        Interface.getInstance().afficherMessageInfo(message);
    }
    /**
     * 
     * @param x la position x du pivot
     * @param y la position y du pivot
     * @param direction
     * @return renvoie vrai si aucun bateau ne se superpose 
     */
    private class VerificationDeplacement{
        private AbstractBateau[] tabBateau2 = new AbstractBateau[3];

        public VerificationDeplacement(AbstractBateau[] tabBateau) {
            for(int i=0;i<3;i++){
                if(tabBateau[i]!=null)
                    this.tabBateau2[i]= tabBateau[i].copy();
            }
        }
        public boolean verifier(int bateauIndex,Deplacement deplacement) {
        
            AbstractPartie partie = Controleur.getInstance().getPartie();
            Joueur joueur = Controleur.getInstance().getJoueur();

    

            boolean grille[][] = new boolean[11][11];

            for (int ibateau=0;ibateau<3;ibateau++) {
                AbstractBateau bateau_tmp=this.tabBateau2[ibateau];
                if(bateau_tmp!=null && ibateau!=bateauIndex){
                    try {
                        for (Integer[] coord : bateau_tmp.getCoordonees()) {
                                grille[coord[0]][coord[1]] = true;
                        }
                    } catch (AbstractPartieLive.ExceptionCoupImpossible ex) {
                        Logger.getLogger(CtrlPartieEnCours.class.getName()).
                                log(Level.SEVERE, null, ex);
                    }
                }
            }
            AbstractBateau bateau_tmp = this.tabBateau2[bateauIndex].copy();
            try {
                bateau_tmp.deplacer(deplacement);
            } catch (AbstractPartieLive.ExceptionCoupImpossible ex) {
                return false;
            }

            try {
                for (Integer[] coord : bateau_tmp.getCoordonees()) {
                    if (grille[coord[0]][coord[1]]) {
                        return false;
                    }
                }
            } catch (AbstractPartieLive.ExceptionCoupImpossible ex) {
                return false;
            }
            
            this.tabBateau2[bateauIndex]=bateau_tmp;
            
            return true;

        }
        
    }
    
}
