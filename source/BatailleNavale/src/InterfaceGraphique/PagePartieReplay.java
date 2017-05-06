/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceGraphique;

import Controleur.Controleur;
import bataillenavale.AbstractAction;
import bataillenavale.ActionTir;
import bataillenavale.ConcreteFabriqueAction;
import bataillenavale.AbstractPartieEnReplay;
import bataillenavale.Etat;
import java.awt.Color;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 * JPanel page partie replay
 * @author ngankafr
 */
public class PagePartieReplay extends javax.swing.JPanel {

    /**
     * Creates new form PartieReplay
     */
    public PagePartieReplay() {
        initComponents();
        initGrille();
        this.labelJoueur1.setText(Controleur.getInstance().getPartie().getJoueur1().getPseudo());
        this.labelJoueur2.setText(Controleur.getInstance().getPartie().getJoueur2().getPseudo());
        
    }

    
    private void initGrille() {
        
         for(int i=0; i<=99; i++){
             final int j = i;             
             jPanelTabJoueur1[i] = new JPanel();
             jPanelTabJoueur1[i].setBackground(Color.WHITE);
             jPanelTabJoueur1[i].setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
             jPanelTabJoueur1[i].setBounds(i%10*30+50, i/10*30+217, 30, 30); 
             jPanelTabJoueur1[i].setVisible(true);
             this.add(jPanelTabJoueur1[i]);
             this.revalidate();
         }
         
         for(int i=0; i<=99; i++){
             final int j = i;
             jPanelTabJoueur2[i] = new JPanel();
             jPanelTabJoueur2[i].setBackground(Color.WHITE);
             jPanelTabJoueur2[i].setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
             jPanelTabJoueur2[i].setBounds(i%10*30+450, i/10*30+217, 30, 30);  
             jPanelTabJoueur2[i].setVisible(true);
             this.add(jPanelTabJoueur2[i]);             
             this.revalidate();
         }       
    }
    
    private void afficherBateau(Etat e, int nombreEscorteurExistant,JPanel[] tab) {
        int x = e.getX()-1;
        int y = e.getY()-1;
        if(e.getTaille()==3){
            tab[x + y * 10].setBackground(new Color(0, 0, 150));
             switch(e.getDirection()){
                 case NORD:                                 
                     tab[x + (y - 1) * 10].setBackground(Color.blue);
                     tab[x + (y - 2) * 10].setBackground(Color.blue);
                     break;
                 case EST:                    
                     tab[x+1 + y * 10].setBackground(Color.blue);
                     tab[x+2 + y* 10].setBackground(Color.blue);
                     break;
                 case SUD:                    
                     tab[x + (y + 1) * 10].setBackground(Color.blue);
                     tab[x + (y + 2) * 10].setBackground(Color.blue);
                     break;
                 case OUEST:                   
                     tab[x-1 + y * 10].setBackground(Color.blue);
                     tab[x-2 + y * 10].setBackground(Color.blue);
                     break;
                 default : 
                     break;
             }
         }else if(e.getTaille()==2 && nombreEscorteurExistant==1){
             tab[x + y * 10].setBackground(new Color(0, 150, 0));
             switch(e.getDirection()){                 
                 case NORD:                    
                     tab[x + (y - 1) * 10].setBackground(Color.green);                    
                     break;
                 case EST:
                     tab[x+1 + y * 10].setBackground(Color.green);                    
                     break;
                 case SUD:
                     tab[x + (y + 1) * 10].setBackground(Color.green);                    
                     break;
                 case OUEST:
                     tab[x-1 + y * 10].setBackground(Color.green);                     
                     break;
                 default : 
                     break;
             }
         }else{
             tab[x + y * 10].setBackground(new Color(150,0,0));
             switch(e.getDirection()){
                 case NORD:
                     tab[x + (y - 1) * 10].setBackground(Color.red);                    
                     break;
                 case EST:
                     tab[x+1 + y * 10].setBackground(Color.red);                     
                     break;
                 case SUD:
                     tab[x + (y + 1) * 10].setBackground(Color.red);                    
                     break;
                 case OUEST:
                     tab[x-1 + y * 10].setBackground(Color.red);                     
                     break;
                 default : 
                     break;
             }
         }
             
        
    }

    
    private void initGrilleWhite(JPanel[] tab) {
        for(int i=0; i<=99; i++){     
             tab[i].setBackground(Color.WHITE);           
         }
    }

    
    public void afficherGrilleJoueur1(LinkedList<Etat> listeEtat){
        initGrilleWhite(jPanelTabJoueur1);
        int  nombreEscorteurExistant = 0;
        for(Etat e : listeEtat){
          if(e.getTaille()==2){
              nombreEscorteurExistant++;
          }
          if(e.getVie()>0){
              this.afficherBateau(e,nombreEscorteurExistant,jPanelTabJoueur1);
          }  
      }        
    }
    
     public void afficherGrilleJoueur2(LinkedList<Etat> listeEtat){
        initGrilleWhite(jPanelTabJoueur2);
        int  nombreEscorteurExistant = 0;
        for(Etat e : listeEtat){
          if(e.getTaille()==2){
              nombreEscorteurExistant++;
          }
           if(e.getVie()>0){
              this.afficherBateau(e,nombreEscorteurExistant,jPanelTabJoueur2);
          } 
      }        
    }
     
    public void afficherAction(int idPartie, int numTour) {
        LinkedList <AbstractAction> listeActionTir = ConcreteFabriqueAction.getInstance().getActionTir(idPartie, numTour);
        
        int i = 0;
        for (AbstractAction actionTir : listeActionTir) {
            int x = ((ActionTir) actionTir).getCoordX();
            int y = ((ActionTir) actionTir).getCoordY();
            i++;
            
            if (numTour % 2 == 0) {
                switch (i) {
                    case 0 : this.jLabel1Action1.setText("(" + x + ", " + y + ")");
                        break;
                    case 1 : this.jLabel2Action1.setText("(" + x + ", " + y + ")");
                        break;
                    case 2 : this.jLabel3Action1.setText("(" + x + ", " + y + ")");
                        break;
                    case 3 : this.jLabel4Action1.setText("(" + x + ", " + y + ")");
                        break; 
                    case 4 : this.jLabel5Action1.setText("(" + x + ", " + y + ")");
                        break; 
                    case 5 : this.jLabel6Action1.setText("(" + x + ", " + y + ")");
                        break;
                    case 6 : this.jLabel7Action1.setText("(" + x + ", " + y + ")");
                        break;
                }
            } else {
                switch (i) {
                    case 0 : this.jLabel1Action2.setText("(" + x + ", " + y + ")");
                        break;
                    case 1 : this.jLabel2Action2.setText("(" + x + ", " + y + ")");
                        break;
                    case 2 : this.jLabel3Action2.setText("(" + x + ", " + y + ")");
                        break;
                    case 3 : this.jLabel4Action2.setText("(" + x + ", " + y + ")");
                        break; 
                    case 4 : this.jLabel5Action2.setText("(" + x + ", " + y + ")");
                        break; 
                    case 5 : this.jLabel6Action2.setText("(" + x + ", " + y + ")");
                        break;
                    case 6 : this.jLabel7Action2.setText("(" + x + ", " + y + ")");
                        break;
                }
            }
        }
    }
    
    public void effacerAction(int numTour) {
        if ((numTour % 2 )== 0) {
            this.jLabel1Action2.setText(" ");
            this.jLabel2Action2.setText(" ");
            this.jLabel3Action2.setText(" ");
            this.jLabel4Action2.setText(" ");
            this.jLabel5Action2.setText(" ");
            this.jLabel6Action2.setText(" ");
            this.jLabel7Action2.setText(" ");
        } else {
            this.jLabel1Action1.setText(" ");
            this.jLabel2Action1.setText(" ");
            this.jLabel3Action1.setText(" ");
            this.jLabel4Action1.setText(" ");
            this.jLabel5Action1.setText(" ");
            this.jLabel6Action1.setText(" ");
            this.jLabel7Action1.setText(" ");
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

        jComboBox1 = new javax.swing.JComboBox();
        grilleJoueur1 = new javax.swing.JPanel();
        grilleJoueur2 = new javax.swing.JPanel();
        panelJoueur1 = new javax.swing.JPanel();
        labelJoueur1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        labelDestroyerJoueur1 = new javax.swing.JLabel();
        labelEscorteur1Joueur1 = new javax.swing.JLabel();
        labelEscorteur2Joueur1 = new javax.swing.JLabel();
        labelVie1 = new javax.swing.JLabel();
        panelAction1 = new javax.swing.JPanel();
        jLabel1Action1 = new javax.swing.JLabel();
        jLabel2Action1 = new javax.swing.JLabel();
        jLabel3Action1 = new javax.swing.JLabel();
        jLabel4Action1 = new javax.swing.JLabel();
        jLabel5Action1 = new javax.swing.JLabel();
        jLabel6Action1 = new javax.swing.JLabel();
        jLabel7Action1 = new javax.swing.JLabel();
        panelMenu = new javax.swing.JPanel();
        retour = new javax.swing.JButton();
        labelTour = new javax.swing.JLabel();
        boutonEtatSuivant = new javax.swing.JButton();
        boutonEtatPrecedent = new javax.swing.JButton();
        panelJoueur2 = new javax.swing.JPanel();
        labelJoueur2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        labelDestroyerJoueur2 = new javax.swing.JLabel();
        labelEscorteur1Joueur2 = new javax.swing.JLabel();
        labelEscorteur2Joueur2 = new javax.swing.JLabel();
        labelVie2 = new javax.swing.JLabel();
        panelAction2 = new javax.swing.JPanel();
        jLabel1Action2 = new javax.swing.JLabel();
        jLabel2Action2 = new javax.swing.JLabel();
        jLabel3Action2 = new javax.swing.JLabel();
        jLabel4Action2 = new javax.swing.JLabel();
        jLabel5Action2 = new javax.swing.JLabel();
        jLabel6Action2 = new javax.swing.JLabel();
        jLabel7Action2 = new javax.swing.JLabel();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setPreferredSize(new java.awt.Dimension(800, 600));

        grilleJoueur1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        grilleJoueur1.setOpaque(false);

        javax.swing.GroupLayout grilleJoueur1Layout = new javax.swing.GroupLayout(grilleJoueur1);
        grilleJoueur1.setLayout(grilleJoueur1Layout);
        grilleJoueur1Layout.setHorizontalGroup(
            grilleJoueur1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 388, Short.MAX_VALUE)
        );
        grilleJoueur1Layout.setVerticalGroup(
            grilleJoueur1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 348, Short.MAX_VALUE)
        );

        grilleJoueur2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        grilleJoueur2.setOpaque(false);

        javax.swing.GroupLayout grilleJoueur2Layout = new javax.swing.GroupLayout(grilleJoueur2);
        grilleJoueur2.setLayout(grilleJoueur2Layout);
        grilleJoueur2Layout.setHorizontalGroup(
            grilleJoueur2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 368, Short.MAX_VALUE)
        );
        grilleJoueur2Layout.setVerticalGroup(
            grilleJoueur2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 348, Short.MAX_VALUE)
        );

        panelJoueur1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        labelJoueur1.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N
        labelJoueur1.setText("Joueur 1");

        labelDestroyerJoueur1.setText("Destroyer:");

        labelEscorteur1Joueur1.setText("Escorteur1:");

        labelEscorteur2Joueur1.setText("Escorteur2:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelDestroyerJoueur1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(labelEscorteur1Joueur1, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
            .addComponent(labelEscorteur2Joueur1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(labelDestroyerJoueur1)
                .addGap(24, 24, 24)
                .addComponent(labelEscorteur1Joueur1)
                .addGap(27, 27, 27)
                .addComponent(labelEscorteur2Joueur1)
                .addGap(0, 31, Short.MAX_VALUE))
        );

        labelVie1.setText("    VIE");

        panelAction1.setBorder(javax.swing.BorderFactory.createTitledBorder("Tirs"));
        panelAction1.setPreferredSize(new java.awt.Dimension(185, 22));

        jLabel1Action1.setText(" ");

        jLabel2Action1.setText(" ");

        jLabel3Action1.setText(" ");

        jLabel4Action1.setText(" ");

        jLabel5Action1.setText(" ");

        jLabel6Action1.setText(" ");

        jLabel7Action1.setText(" ");

        javax.swing.GroupLayout panelAction1Layout = new javax.swing.GroupLayout(panelAction1);
        panelAction1.setLayout(panelAction1Layout);
        panelAction1Layout.setHorizontalGroup(
            panelAction1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAction1Layout.createSequentialGroup()
                .addGroup(panelAction1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4Action1)
                    .addGroup(panelAction1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(panelAction1Layout.createSequentialGroup()
                            .addComponent(jLabel3Action1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7Action1))
                        .addGroup(panelAction1Layout.createSequentialGroup()
                            .addComponent(jLabel2Action1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6Action1))
                        .addGroup(panelAction1Layout.createSequentialGroup()
                            .addComponent(jLabel1Action1)
                            .addGap(39, 39, 39)
                            .addComponent(jLabel5Action1))))
                .addGap(0, 107, Short.MAX_VALUE))
        );
        panelAction1Layout.setVerticalGroup(
            panelAction1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAction1Layout.createSequentialGroup()
                .addGroup(panelAction1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1Action1)
                    .addComponent(jLabel5Action1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAction1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2Action1)
                    .addComponent(jLabel6Action1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAction1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3Action1)
                    .addComponent(jLabel7Action1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4Action1)
                .addGap(0, 16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelJoueur1Layout = new javax.swing.GroupLayout(panelJoueur1);
        panelJoueur1.setLayout(panelJoueur1Layout);
        panelJoueur1Layout.setHorizontalGroup(
            panelJoueur1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panelJoueur1Layout.createSequentialGroup()
                .addGroup(panelJoueur1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelJoueur1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(labelJoueur1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelJoueur1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelAction1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(panelJoueur1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelJoueur1Layout.createSequentialGroup()
                        .addComponent(labelVie1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52))
                    .addGroup(panelJoueur1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(24, Short.MAX_VALUE))))
        );
        panelJoueur1Layout.setVerticalGroup(
            panelJoueur1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJoueur1Layout.createSequentialGroup()
                .addGroup(panelJoueur1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelJoueur1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labelVie1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelJoueur1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelJoueur1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelJoueur1Layout.createSequentialGroup()
                        .addComponent(panelAction1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelJoueur1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        panelMenu.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        retour.setText("Retour");
        retour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retourActionPerformed(evt);
            }
        });
        panelMenu.add(retour);

        labelTour.setForeground(new java.awt.Color(122, 66, 196));
        labelTour.setText("Tour : ");
        labelTour.setBorder(null);
        labelTour.setPreferredSize(new java.awt.Dimension(70, 50));
        panelMenu.add(labelTour);

        boutonEtatSuivant.setText("DO");
        boutonEtatSuivant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonEtatSuivantActionPerformed(evt);
            }
        });
        panelMenu.add(boutonEtatSuivant);

        boutonEtatPrecedent.setText("UNDO");
        boutonEtatPrecedent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonEtatPrecedentActionPerformed(evt);
            }
        });
        panelMenu.add(boutonEtatPrecedent);

        panelJoueur2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        labelJoueur2.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N
        labelJoueur2.setText(" Joueur 2");

        labelDestroyerJoueur2.setText("Destroyer:");

        labelEscorteur1Joueur2.setText("Escorteur1:");

        labelEscorteur2Joueur2.setText("Escorteur2:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelEscorteur2Joueur2, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
            .addComponent(labelEscorteur1Joueur2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(labelDestroyerJoueur2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(labelDestroyerJoueur2)
                .addGap(24, 24, 24)
                .addComponent(labelEscorteur1Joueur2)
                .addGap(27, 27, 27)
                .addComponent(labelEscorteur2Joueur2)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        labelVie2.setText("    VIE");

        panelAction2.setBorder(javax.swing.BorderFactory.createTitledBorder("Tirs"));
        panelAction2.setPreferredSize(new java.awt.Dimension(185, 22));

        jLabel1Action2.setText(" ");

        jLabel2Action2.setText(" ");

        jLabel3Action2.setText(" ");

        jLabel4Action2.setText(" ");

        jLabel5Action2.setText(" ");

        jLabel6Action2.setText(" ");

        jLabel7Action2.setText(" ");

        javax.swing.GroupLayout panelAction2Layout = new javax.swing.GroupLayout(panelAction2);
        panelAction2.setLayout(panelAction2Layout);
        panelAction2Layout.setHorizontalGroup(
            panelAction2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAction2Layout.createSequentialGroup()
                .addGroup(panelAction2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4Action2)
                    .addGroup(panelAction2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(panelAction2Layout.createSequentialGroup()
                            .addComponent(jLabel3Action2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7Action2))
                        .addGroup(panelAction2Layout.createSequentialGroup()
                            .addComponent(jLabel2Action2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6Action2))
                        .addGroup(panelAction2Layout.createSequentialGroup()
                            .addComponent(jLabel1Action2)
                            .addGap(39, 39, 39)
                            .addComponent(jLabel5Action2))))
                .addGap(0, 107, Short.MAX_VALUE))
        );
        panelAction2Layout.setVerticalGroup(
            panelAction2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAction2Layout.createSequentialGroup()
                .addGroup(panelAction2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1Action2)
                    .addComponent(jLabel5Action2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAction2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2Action2)
                    .addComponent(jLabel6Action2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAction2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3Action2)
                    .addComponent(jLabel7Action2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4Action2)
                .addGap(0, 16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelJoueur2Layout = new javax.swing.GroupLayout(panelJoueur2);
        panelJoueur2.setLayout(panelJoueur2Layout);
        panelJoueur2Layout.setHorizontalGroup(
            panelJoueur2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelJoueur2Layout.createSequentialGroup()
                .addGroup(panelJoueur2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelJoueur2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelJoueur2Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(labelVie2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelJoueur2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelAction2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelJoueur2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelJoueur2Layout.setVerticalGroup(
            panelJoueur2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJoueur2Layout.createSequentialGroup()
                .addGroup(panelJoueur2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelJoueur2Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(labelVie2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelJoueur2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelJoueur2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelJoueur2Layout.createSequentialGroup()
                        .addComponent(panelAction2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelJoueur1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelJoueur2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(grilleJoueur1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(grilleJoueur2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panelMenu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelJoueur1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelJoueur2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(grilleJoueur1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(grilleJoueur2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void boutonEtatSuivantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonEtatSuivantActionPerformed
        Controleur.getInstance().getCtrlPartieReplay().boutonEtatSuivant();
    }//GEN-LAST:event_boutonEtatSuivantActionPerformed

    private void retourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retourActionPerformed
        Interface.getInstance().setPageJoueur();
    }//GEN-LAST:event_retourActionPerformed

    private void boutonEtatPrecedentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonEtatPrecedentActionPerformed
        Controleur.getInstance().getCtrlPartieReplay().boutonEtatPrecedent();
    }//GEN-LAST:event_boutonEtatPrecedentActionPerformed

    
    public void afficherNumTour(AbstractPartieEnReplay partie) {
        this.labelTour.setText("Tour : " + partie.getTourEnCours());
    }

    
    public void afficherVieJoueur1(LinkedList<Etat> etatJoueur) {
        int numEscorteur=0;
       for(Etat e: etatJoueur){
           if(e.getTaille()==3){
               this.labelDestroyerJoueur1.setText("Destroyer : " + e.getVie());
           }else if (e.getTaille()==2 && numEscorteur==0){
               this.labelEscorteur1Joueur1.setText("Escorteur1 : " + e.getVie());
               numEscorteur++;
           }else{
               this.labelEscorteur2Joueur1.setText("Escorteur2 : " + e.getVie());
           }
       }
    }

    
     public void afficherVieJoueur2(LinkedList<Etat> etatJoueur) {
        int numEscorteur=0;       
       for(Etat e: etatJoueur){
           if(e.getTaille()==3){
               this.labelDestroyerJoueur2.setText("Destroyer : " + e.getVie());
           }else if (e.getTaille()==2 && numEscorteur==0){
               this.labelEscorteur1Joueur2.setText("Escorteur1 : " + e.getVie());
               numEscorteur++;
           }else{
               this.labelEscorteur2Joueur2.setText("Escorteur2 : " + e.getVie());
           }
       }       
    }
     public javax.swing.JLabel getlabelJoueur1(){
         return this.labelJoueur1;
     }
    
    private JPanel jPanelTabJoueur1[] = new JPanel[100]; 
    private JPanel jPanelTabJoueur2[] = new JPanel[100];
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boutonEtatPrecedent;
    private javax.swing.JButton boutonEtatSuivant;
    private javax.swing.JPanel grilleJoueur1;
    private javax.swing.JPanel grilleJoueur2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1Action1;
    private javax.swing.JLabel jLabel1Action2;
    private javax.swing.JLabel jLabel2Action1;
    private javax.swing.JLabel jLabel2Action2;
    private javax.swing.JLabel jLabel3Action1;
    private javax.swing.JLabel jLabel3Action2;
    private javax.swing.JLabel jLabel4Action1;
    private javax.swing.JLabel jLabel4Action2;
    private javax.swing.JLabel jLabel5Action1;
    private javax.swing.JLabel jLabel5Action2;
    private javax.swing.JLabel jLabel6Action1;
    private javax.swing.JLabel jLabel6Action2;
    private javax.swing.JLabel jLabel7Action1;
    private javax.swing.JLabel jLabel7Action2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel labelDestroyerJoueur1;
    private javax.swing.JLabel labelDestroyerJoueur2;
    private javax.swing.JLabel labelEscorteur1Joueur1;
    private javax.swing.JLabel labelEscorteur1Joueur2;
    private javax.swing.JLabel labelEscorteur2Joueur1;
    private javax.swing.JLabel labelEscorteur2Joueur2;
    private javax.swing.JLabel labelJoueur1;
    private javax.swing.JLabel labelJoueur2;
    private javax.swing.JLabel labelTour;
    private javax.swing.JLabel labelVie1;
    private javax.swing.JLabel labelVie2;
    private javax.swing.JPanel panelAction1;
    private javax.swing.JPanel panelAction2;
    private javax.swing.JPanel panelJoueur1;
    private javax.swing.JPanel panelJoueur2;
    private javax.swing.JPanel panelMenu;
    private javax.swing.JButton retour;
    // End of variables declaration//GEN-END:variables

}
