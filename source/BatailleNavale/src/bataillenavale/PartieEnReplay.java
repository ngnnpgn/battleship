/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bataillenavale;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jaylesr
 */
public class PartieEnReplay extends AbstractPartieEnReplay{



   private int tourEnCours;
   
   private LinkedList<Etat> etatJoueur1;
   private LinkedList<Etat> etatJoueur2;
   
   public PartieEnReplay(Joueur joueur1, Joueur joueur2, int idPartie){
       super(joueur1, joueur2, idPartie);
       //par défaut le tour en cours est le 1, une fois que les deux joueurs ont placé leurs bateaux
       this.tourEnCours = 1;
   }
   
    public int getTourEnCours() {
        return tourEnCours;
    }

    @Override
    public int getTourMax() {
        int tourMax = 0;
        
        try {
            String requete = "SELECT * FROM Tour t WHERE t.idPartie = ? ORDER BY t.numTour";
            PreparedStatement stt = BDManager.getInstance().prepareStatement(requete);
            stt.setInt(1, this.idPartie);
            ResultSet rs = stt.executeQuery();
            rs.last();
            tourMax = rs.getInt("numTour");
            rs.close();
            stt.close();
        } catch (SQLException ex) {
            Logger.getLogger(PartieEnReplay.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       return tourMax;
    }
   
   @Override
    public LinkedList<Etat> getEtatJoueur1() {
        return etatJoueur1;
    }

   @Override
    public LinkedList<Etat> getEtatJoueur2() {
        return etatJoueur2;
    }
    
        
    public void setEtatJoueur1(LinkedList<Etat> etatJoueur1) {
        this.etatJoueur1 = etatJoueur1;
    }

    public void setEtatJoueur2(LinkedList<Etat> etatJoueur2) {
        this.etatJoueur2 = etatJoueur2;
    }
    
   @Override
    public void etatSuivant(){
        this.tourEnCours++;
        LinkedList<Etat> listeEtat = ConcreteFabriqueEtat.getInstance().getEtatCrees(this.idPartie, this.tourEnCours);
        this.etatJoueur1 = modifierListeEtat(etatJoueur1, listeEtat);
        this.etatJoueur2 = modifierListeEtat(etatJoueur2, listeEtat);
    }
    
   @Override
    public void etatPrecedent(){
        if(this.tourEnCours > 1){
            this.tourEnCours--;
            LinkedList<Etat> listeEtat = ConcreteFabriqueEtat.getInstance().getEtatCrees(this.idPartie, this.tourEnCours);
            this.etatJoueur1 = modifierListeEtat(etatJoueur1, listeEtat);
            this.etatJoueur2 = modifierListeEtat(etatJoueur2, listeEtat);
        }
        
        
    }
    
    private LinkedList<Etat> modifierListeEtat(LinkedList<Etat> listeEtatBateau, LinkedList<Etat> nouvelleliste){
        LinkedList<Etat> nouvEtat = new LinkedList<Etat>();
        Etat dernier=null;
        boolean notChanged;
        for(Etat ejoueur : listeEtatBateau){
            notChanged = true;
            for(Etat enouv : nouvelleliste){
                if(ejoueur.getBateau().getIdBateau() == enouv.getBateau().getIdBateau()){
                    dernier = enouv;
                    notChanged = false;
                }
            }
            if(notChanged){
                nouvEtat.add(ejoueur);
            }else{
                nouvEtat.add(dernier);
            }
        }
        return nouvEtat;
        
    }
}
