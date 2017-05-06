/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceGraphique;

import Controleur.Controleur;
import Controleur.CtrlPartieEnCours;
import bataillenavale.AbstractBateau;
import bataillenavale.Direction;
import bataillenavale.Etat;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 * JPanel page partie en cours
 * @author gounonj
 */
public class PagePartieEnCours extends javax.swing.JPanel {

    /**
     * Creates new form PageNouvellePartie
     */
    public PagePartieEnCours() {
        initComponents();
        // TODO a faire plus beau
        this.pseudoJoueur.setText(Controleur.getInstance().getJoueur().getPseudo());
        this.pseudoAdversaire.setText(Controleur.getInstance().getPartie().getAdversaire(Controleur.getInstance().getJoueur()).getPseudo());
        initGrille();
       
        
    }
    
    private void afficherBateau(Etat e, int nombreEscorteurExistant) {
        int x = e.getX()-1;
        int y = e.getY()-1;
        if(e.getTaille()==3){
            jPanelTabJoueur[x + y * 10].setBackground(new Color(0, 0, 150));
             switch(e.getDirection()){
                 case NORD:                                 
                     jPanelTabJoueur[x + (y - 1) * 10].setBackground(Color.blue);
                     jPanelTabJoueur[x + (y - 2) * 10].setBackground(Color.blue);
                     break;
                 case EST:                    
                     jPanelTabJoueur[x+1 + y * 10].setBackground(Color.blue);
                     jPanelTabJoueur[x+2 + y* 10].setBackground(Color.blue);
                     break;
                 case SUD:                    
                     jPanelTabJoueur[x + (y + 1) * 10].setBackground(Color.blue);
                     jPanelTabJoueur[x + (y + 2) * 10].setBackground(Color.blue);
                     break;
                 case OUEST:                   
                     jPanelTabJoueur[x-1 + y * 10].setBackground(Color.blue);
                     jPanelTabJoueur[x-2 + y * 10].setBackground(Color.blue);
                     break;
                 default : 
                     break;
             }
         }else if(e.getTaille()==2 && nombreEscorteurExistant==1){
             jPanelTabJoueur[x + y * 10].setBackground(new Color(0, 150, 0));
             switch(e.getDirection()){                 
                 case NORD:                    
                     jPanelTabJoueur[x + (y - 1) * 10].setBackground(Color.green);                    
                     break;
                 case EST:
                     jPanelTabJoueur[x+1 + y * 10].setBackground(Color.green);                    
                     break;
                 case SUD:
                     jPanelTabJoueur[x + (y + 1) * 10].setBackground(Color.green);                    
                     break;
                 case OUEST:
                     jPanelTabJoueur[x-1 + y * 10].setBackground(Color.green);                     
                     break;
                 default : 
                     break;
             }
         }else{
             jPanelTabJoueur[x + y * 10].setBackground(new Color(150,0,0));
             switch(e.getDirection()){
                 case NORD:
                     jPanelTabJoueur[x + (y - 1) * 10].setBackground(Color.red);                    
                     break;
                 case EST:
                     jPanelTabJoueur[x+1 + y * 10].setBackground(Color.red);                     
                     break;
                 case SUD:
                     jPanelTabJoueur[x + (y + 1) * 10].setBackground(Color.red);                    
                     break;
                 case OUEST:
                     jPanelTabJoueur[x-1 + y * 10].setBackground(Color.red);                     
                     break;
                 default : 
                     break;
             }
         }
             
        
    }

    
    private void initGrilleWhite() {
        for(int i=0; i<=99; i++){     
             jPanelTabJoueur[i].setBackground(Color.WHITE);           
         }
    }

    
    public void afficherGrille(LinkedList<Etat> listeEtat){
        initGrilleWhite();
        int  nombreEscorteurExistant = 0;
        for(Etat e : listeEtat){
          if(e.getTaille()==2){
              nombreEscorteurExistant++;
          }
          if(e.getVie()>0){
              this.afficherBateau(e,nombreEscorteurExistant);
          }        
      }        
    }

    
    private void initGrille() {
        
         for(int i=0; i<=99; i++){
             final int j = i;
             jPanelTabJoueur[i] = new JPanel();
             jPanelTabJoueur[i].setBackground(Color.WHITE);
             jPanelTabJoueur[i].setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
             jPanelTabJoueur[i].setBounds(i%10*30+50, i/10*30+200, 30, 30);            
            
                     
             jPanelTabJoueur[i].setVisible(true);
             this.add(jPanelTabJoueur[i]);
             
             this.revalidate();
         }
         
         for(int i=0; i<=99; i++){
             final int j = i;
             jPanelTabAdversaire[i] = new JPanel();
             jPanelTabAdversaire[i].setBackground(Color.WHITE);
             jPanelTabAdversaire[i].setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
             jPanelTabAdversaire[i].setBounds(i%10*30+450, i/10*30+200, 30, 30);             
             jPanelTabAdversaire[i].addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {                        
                       Controleur.getInstance().getCtrlPartieEnCours().selectionCase(jPanelTabAdversaire[j],j); 
                       //jPanelMouseClicked(evt, jPanelTabAdversaire[j],j);
                    }                 
                });
                     
             jPanelTabAdversaire[i].setVisible(true);
             this.add(jPanelTabAdversaire[i]);
             
             this.revalidate();
         }
         
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bateaux = new javax.swing.ButtonGroup();
        rafraichir = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        pseudoAdversaire = new javax.swing.JLabel();
        pseudoJoueur = new javax.swing.JLabel();
        nouvellePartie = new javax.swing.JLabel();
        pointsVie = new javax.swing.JLabel();
        pointsAction = new javax.swing.JLabel();
        actions = new javax.swing.JPanel();
        avancer = new javax.swing.JButton();
        tournerGauche = new javax.swing.JButton();
        tournerDroite = new javax.swing.JButton();
        tirer = new javax.swing.JButton();
        reculer = new javax.swing.JButton();
        panelBateaux = new javax.swing.JPanel();
        destroyer = new javax.swing.JButton();
        escorteur2 = new javax.swing.JButton();
        escorteur1 = new javax.swing.JButton();
        vieEscorteur2 = new javax.swing.JLabel();
        vieDestroyer = new javax.swing.JLabel();
        vieEscorteur1 = new javax.swing.JLabel();
        validerActions = new javax.swing.JButton();
        retourPageJoueur = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(800, 600));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));
        setLayout(null);

        rafraichir.setBackground(new java.awt.Color(180, 242, 249));
        rafraichir.setFont(new java.awt.Font("DejaVu Sans", 0, 14)); // NOI18N
        rafraichir.setText("Rafraîchir");
        rafraichir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rafraichir.setOpaque(true);
        rafraichir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rafraichirActionPerformed(evt);
            }
        });
        add(rafraichir);
        rafraichir.setBounds(410, 510, 110, 30);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        add(jSeparator1);
        jSeparator1.setBounds(400, 174, 14, 426);
        add(jSeparator2);
        jSeparator2.setBounds(0, 174, 810, 20);

        pseudoAdversaire.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
        pseudoAdversaire.setText("jLabel1");
        add(pseudoAdversaire);
        pseudoAdversaire.setBounds(710, 510, 70, 20);

        pseudoJoueur.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
        pseudoJoueur.setText("jLabel1");
        add(pseudoJoueur);
        pseudoJoueur.setBounds(30, 510, 70, 20);

        nouvellePartie.setFont(new java.awt.Font("DejaVu Sans", 1, 36)); // NOI18N
        nouvellePartie.setText("Partie en cours");
        add(nouvellePartie);
        nouvellePartie.setBounds(20, 0, 320, 40);

        pointsVie.setFont(new java.awt.Font("DejaVu Sans", 0, 12)); // NOI18N
        pointsVie.setText("Points de vie restants :");
        add(pointsVie);
        pointsVie.setBounds(140, 50, 220, 30);

        pointsAction.setFont(new java.awt.Font("DejaVu Sans", 0, 12)); // NOI18N
        pointsAction.setText("Points d'action restants :");
        add(pointsAction);
        pointsAction.setBounds(520, 10, 220, 30);

        actions.setBorder(javax.swing.BorderFactory.createTitledBorder("Actions"));

        avancer.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N
        avancer.setText("Avancer");
        avancer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avancerActionPerformed(evt);
            }
        });

        tournerGauche.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N
        tournerGauche.setText("Tourner à gauche");
        tournerGauche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tournerGaucheActionPerformed(evt);
            }
        });

        tournerDroite.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N
        tournerDroite.setText("Tourner à droite");
        tournerDroite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tournerDroiteActionPerformed(evt);
            }
        });

        tirer.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N
        tirer.setText("Tirer");
        tirer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tirerActionPerformed(evt);
            }
        });

        reculer.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N
        reculer.setText("Reculer");
        reculer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reculerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout actionsLayout = new javax.swing.GroupLayout(actions);
        actions.setLayout(actionsLayout);
        actionsLayout.setHorizontalGroup(
            actionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actionsLayout.createSequentialGroup()
                .addGroup(actionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(actionsLayout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(reculer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(avancer))
                    .addGroup(actionsLayout.createSequentialGroup()
                        .addComponent(tournerGauche)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tournerDroite))
                    .addGroup(actionsLayout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(tirer)))
                .addGap(0, 60, Short.MAX_VALUE))
        );
        actionsLayout.setVerticalGroup(
            actionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, actionsLayout.createSequentialGroup()
                .addComponent(tirer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(actionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(avancer)
                    .addComponent(reculer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(actionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tournerGauche)
                    .addComponent(tournerDroite, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        add(actions);
        actions.setBounds(420, 30, 350, 140);

        panelBateaux.setBorder(javax.swing.BorderFactory.createTitledBorder("Bateaux"));

        destroyer.setForeground(new java.awt.Color(0, 13, 255));
        destroyer.setText("Destroyer");
        bateaux.add(destroyer);
        destroyer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                destroyerActionPerformed(evt);
            }
        });

        escorteur2.setForeground(java.awt.Color.red);
        escorteur2.setText("Escorteur 2");
        bateaux.add(escorteur2);
        escorteur2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                escorteur2ActionPerformed(evt);
            }
        });

        escorteur1.setForeground(new java.awt.Color(1, 135, 1));
        escorteur1.setText("Escorteur 1");
        bateaux.add(escorteur1);
        escorteur1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                escorteur1ActionPerformed(evt);
            }
        });

        vieEscorteur2.setText("Vie :");

        vieDestroyer.setText("Vie :");

        vieEscorteur1.setText("Vie :");

        javax.swing.GroupLayout panelBateauxLayout = new javax.swing.GroupLayout(panelBateaux);
        panelBateaux.setLayout(panelBateauxLayout);
        panelBateauxLayout.setHorizontalGroup(
            panelBateauxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBateauxLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(destroyer, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(escorteur1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(escorteur2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelBateauxLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(vieDestroyer)
                .addGap(70, 70, 70)
                .addComponent(vieEscorteur1)
                .addGap(90, 90, 90)
                .addComponent(vieEscorteur2))
        );
        panelBateauxLayout.setVerticalGroup(
            panelBateauxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBateauxLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(panelBateauxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(destroyer)
                    .addComponent(escorteur1)
                    .addComponent(escorteur2))
                .addGap(11, 11, 11)
                .addGroup(panelBateauxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(vieDestroyer)
                    .addComponent(vieEscorteur1)
                    .addComponent(vieEscorteur2)))
        );

        add(panelBateaux);
        panelBateaux.setBounds(20, 70, 380, 100);

        validerActions.setBackground(new java.awt.Color(180, 242, 249));
        validerActions.setFont(new java.awt.Font("DejaVu Sans", 0, 14)); // NOI18N
        validerActions.setText("Valider");
        validerActions.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        validerActions.setOpaque(true);
        validerActions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                validerActionsActionPerformed(evt);
            }
        });
        add(validerActions);
        validerActions.setBounds(280, 510, 110, 30);

        retourPageJoueur.setText("Retour ");
        retourPageJoueur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retourPageJoueurActionPerformed(evt);
            }
        });
        add(retourPageJoueur);
        retourPageJoueur.setBounds(350, 10, 90, 20);
    }// </editor-fold>//GEN-END:initComponents

    private void tournerDroiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tournerDroiteActionPerformed
        Controleur.getInstance().getCtrlPartieEnCours().tournerDroite(this);
    }//GEN-LAST:event_tournerDroiteActionPerformed

    private void tournerGaucheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tournerGaucheActionPerformed
        Controleur.getInstance().getCtrlPartieEnCours().tournerGauche(this);
    }//GEN-LAST:event_tournerGaucheActionPerformed

    private void escorteur2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_escorteur2ActionPerformed
        Controleur.getInstance().getCtrlPartieEnCours().selectionEscorteur2(this);
    }//GEN-LAST:event_escorteur2ActionPerformed

    private void escorteur1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_escorteur1ActionPerformed
        Controleur.getInstance().getCtrlPartieEnCours().selectionEscorteur1(this);
    }//GEN-LAST:event_escorteur1ActionPerformed

    private void destroyerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_destroyerActionPerformed
        Controleur.getInstance().getCtrlPartieEnCours().selectionDestroyer(this);
    }//GEN-LAST:event_destroyerActionPerformed

    private void tirerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tirerActionPerformed
        Controleur.getInstance().getCtrlPartieEnCours().tirer(this);
    }//GEN-LAST:event_tirerActionPerformed

    private void reculerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reculerActionPerformed
        Controleur.getInstance().getCtrlPartieEnCours().reculer(this);
    }//GEN-LAST:event_reculerActionPerformed

    private void avancerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avancerActionPerformed
        Controleur.getInstance().getCtrlPartieEnCours().avancer(this);
    }//GEN-LAST:event_avancerActionPerformed

    private void rafraichirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rafraichirActionPerformed
        Controleur.getInstance().getCtrlPartieEnCours().rafraichirAffichageEtatBateau();
    }//GEN-LAST:event_rafraichirActionPerformed

    private void validerActionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_validerActionsActionPerformed
        Controleur.getInstance().getCtrlPartieEnCours().validerActions();
    }//GEN-LAST:event_validerActionsActionPerformed

    private void retourPageJoueurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retourPageJoueurActionPerformed
        Controleur.getInstance().getCtrlPartieEnCours().retourPageJoueur();
    }//GEN-LAST:event_retourPageJoueurActionPerformed

     public void afficherTirer() {
         this.tirer.setBackground(Color.GRAY);
    }
     
    public void initialiserTirer(){
        this.tirer.setBackground(Color.LIGHT_GRAY);
    }

    public void afficherCase(int j) {
        this.jPanelTabAdversaire[j].setBackground(Color.red);
    }

    
    public void selectionnerEscorteur2() {
        this.escorteur2.setBackground(Color.GRAY);
        this.escorteur1.setBackground(Color.LIGHT_GRAY);
        this.destroyer.setBackground(Color.LIGHT_GRAY);
    }

    public void selectionnerEscorteur1() {
        this.escorteur2.setBackground(Color.LIGHT_GRAY);
        this.escorteur1.setBackground(Color.GRAY);
        this.destroyer.setBackground(Color.LIGHT_GRAY);
    }

    public void selectionnerDestroyer() {
        this.escorteur2.setBackground(Color.LIGHT_GRAY);
        this.escorteur1.setBackground(Color.LIGHT_GRAY);
        this.destroyer.setBackground(Color.GRAY);
    }

  
    
    private JPanel jPanelTabAdversaire[] = new JPanel[100]; 
    private JPanel jPanelTabJoueur[] = new JPanel[100];
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actions;
    private javax.swing.JButton avancer;
    private javax.swing.ButtonGroup bateaux;
    private javax.swing.JButton destroyer;
    private javax.swing.JButton escorteur1;
    private javax.swing.JButton escorteur2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel nouvellePartie;
    private javax.swing.JPanel panelBateaux;
    private javax.swing.JLabel pointsAction;
    private javax.swing.JLabel pointsVie;
    private javax.swing.JLabel pseudoAdversaire;
    private javax.swing.JLabel pseudoJoueur;
    private javax.swing.JButton rafraichir;
    private javax.swing.JButton reculer;
    private javax.swing.JButton retourPageJoueur;
    private javax.swing.JButton tirer;
    private javax.swing.JButton tournerDroite;
    private javax.swing.JButton tournerGauche;
    private javax.swing.JButton validerActions;
    private javax.swing.JLabel vieDestroyer;
    private javax.swing.JLabel vieEscorteur1;
    private javax.swing.JLabel vieEscorteur2;
    // End of variables declaration//GEN-END:variables
 
    public void afficherVie(int vieDestroyer,int vieEscorteur1,int vieEscorteur2) {
        this.vieDestroyer.setText("Vie : " + vieDestroyer);
        this.vieEscorteur1.setText("Vie : " + vieEscorteur1);
        this.vieEscorteur2.setText("Vie : " + vieEscorteur2);
    }
    
    public void afficherCoupRestant(int coup) {
        this.pointsAction.setText("Points d'action restantes : " + coup);
    }
  


    public void enableBouton(boolean b) {
        this.destroyer.setEnabled(b);
        this.escorteur1.setEnabled(b);
        this.escorteur2.setEnabled(b);
        this.avancer.setEnabled(b);
        this.reculer.setEnabled(b);
        this.tournerDroite.setEnabled(b);
        this.tournerGauche.setEnabled(b);
        this.validerActions.setEnabled(b);
        this.tirer.setEnabled(b);
        
    }

    public void enableRafraichir(boolean b) {
        this.rafraichir.setEnabled(b);
    }

   
}
   
