/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceGraphique;

import Controleur.Controleur;
import bataillenavale.ConcreteFabriquePartie;
import bataillenavale.FabriquePartie;
import bataillenavale.PartieEnReplay;
import bataillenavale.PartieLive;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JPanel page joueur
 * @author ngankafr
 */
public class PageJoueur extends javax.swing.JPanel {

    /**
     * Creates new form PageJoeur
     */
    public PageJoueur() {
        //La metode auto genere par l'outil netbeans
        initComponents();
        //On initialise les tableaux des paties replay 
        initTableauReplay();
        //on initialise les tableaux des parties en cours
        initTableauPartieEnCours();
    }

    /**
     * Créer le tableau qui liste les parties en cours
     */
    private void initTableauPartieEnCours() {
        Set<PartieLive> listeLive;       
        // On recupere la liste des parties que le joueur peut continuer a jouer
        listeLive = ConcreteFabriquePartie.getInstance().getPartieEnCours(Controleur.getInstance().getJoueur());
        Object[][] data = new Object[listeLive.size()][3];
        int i = 0;
        //On defini le modele que l'on veut représenter dans le tableau
        for (PartieLive element : listeLive) {
            data[i][0] = element.getIdPartie();
            data[i][1] = element.getAdversaire(Controleur.getInstance().getJoueur()).getPseudo();            
            if(element.monTour(Controleur.getInstance().getJoueur())){
                data[i][2]="Oui";
            }
            else{
                data[i][2] ="Non";
            }
            i++;
        }      
        tablePartieEnCours.setModel(new javax.swing.table.DefaultTableModel(data, new String[]{"Id Partie", "Adversaire", "A vous de jouer ?"}));
    }

     /**
     * Créer le tableau qui liste les parties en cours
     */
    private void initTableauReplay() {
        try {

            LinkedList<PartieEnReplay> listePartie;
            //On recupere la liste des parties que le joueur peut observer en replay
            listePartie = ConcreteFabriquePartie.getInstance().getListePartieAObserver(Controleur.getInstance().getJoueur());
            Object[][] data = new Object[listePartie.size()][3];            
            int i = 0;
            //On defini le modele que l'on veut représenter dans le tableau
            for (PartieEnReplay element : listePartie) {
                data[i][0] = element.getIdPartie();
                data[i][1] = element.getJoueur1();                
                data[i][2] = element.getJoueur2();
                i++;
            }
            tablePartieReplay.setModel(new javax.swing.table.DefaultTableModel(data, new String[]{"Id Partie", "Nom joueur 1", "Nom joueur 2"}));
        } catch (FabriquePartie.ExceptionPartieInexistante ex) {
        }
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nouvellePartie = new javax.swing.JButton();
        quitter = new javax.swing.JButton();
        scrollPaneReplay = new javax.swing.JScrollPane();
        tablePartieReplay = new javax.swing.JTable();
        scrollPanePartieEnCours = new javax.swing.JScrollPane();
        tablePartieEnCours = new javax.swing.JTable();
        labelPartiesReplay = new javax.swing.JLabel();
        labelPartieEnCours = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(800, 600));
        setLayout(null);

        nouvellePartie.setText("Commencer une Nouvelle Partie");
        nouvellePartie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nouvellePartieActionPerformed(evt);
            }
        });
        add(nouvellePartie);
        nouvellePartie.setBounds(40, 10, 260, 29);

        quitter.setText("Quitter");
        quitter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitterActionPerformed(evt);
            }
        });
        add(quitter);
        quitter.setBounds(610, 10, 80, 29);

        tablePartieReplay.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablePartieReplay.setColumnSelectionAllowed(true);
        tablePartieReplay.getTableHeader().setReorderingAllowed(false);
        tablePartieReplay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePartieReplayMouseClicked(evt);
            }
        });
        scrollPaneReplay.setViewportView(tablePartieReplay);
        tablePartieReplay.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tablePartieReplay.getColumnModel().getColumnCount() > 0) {
            tablePartieReplay.getColumnModel().getColumn(0).setResizable(false);
            tablePartieReplay.getColumnModel().getColumn(1).setResizable(false);
            tablePartieReplay.getColumnModel().getColumn(2).setResizable(false);
            tablePartieReplay.getColumnModel().getColumn(3).setResizable(false);
        }

        add(scrollPaneReplay);
        scrollPaneReplay.setBounds(410, 100, 360, 390);

        tablePartieEnCours.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablePartieEnCours.setColumnSelectionAllowed(true);
        tablePartieEnCours.getTableHeader().setReorderingAllowed(false);
        tablePartieEnCours.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePartieEnCoursMouseClicked(evt);
            }
        });
        scrollPanePartieEnCours.setViewportView(tablePartieEnCours);
        tablePartieEnCours.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tablePartieEnCours.getColumnModel().getColumnCount() > 0) {
            tablePartieEnCours.getColumnModel().getColumn(0).setResizable(false);
            tablePartieEnCours.getColumnModel().getColumn(1).setResizable(false);
            tablePartieEnCours.getColumnModel().getColumn(2).setResizable(false);
            tablePartieEnCours.getColumnModel().getColumn(3).setResizable(false);
        }

        add(scrollPanePartieEnCours);
        scrollPanePartieEnCours.setBounds(20, 100, 360, 390);

        labelPartiesReplay.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        labelPartiesReplay.setText("Parties Replay");
        add(labelPartiesReplay);
        labelPartiesReplay.setBounds(530, 60, 140, 40);

        labelPartieEnCours.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        labelPartieEnCours.setText("Vos parties en cours ");
        add(labelPartieEnCours);
        labelPartieEnCours.setBounds(110, 60, 200, 40);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Recupere l'action sur le bouton nouvelle partie et envoie l'information au controleur
     * @param evt 
     */
    private void nouvellePartieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nouvellePartieActionPerformed
        Controleur.getInstance().getCtrlJoueur().nouvellePartie();
    }//GEN-LAST:event_nouvellePartieActionPerformed

     /**
     * Recupere l'action sur le bouton quitter et envoie l'information au controleur
     * @param evt 
     */
    private void quitterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitterActionPerformed
        Controleur.getInstance().getCtrlJoueur().quitterPageJoueur();
    }//GEN-LAST:event_quitterActionPerformed

      /**
     * Recupere l'action sur le tableau et envoie la ligne selectionner au controleur
     * @param evt 
     */
    private void tablePartieReplayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePartieReplayMouseClicked
        Controleur.getInstance().getCtrlJoueur().partieReplay((Integer)tablePartieReplay.getValueAt(tablePartieReplay.getSelectedRow(),0));
        
    }//GEN-LAST:event_tablePartieReplayMouseClicked

    /**
     * Recupere l'action sur le tableau et envoie la ligne selectionner au controleur
     * @param evt 
     */
    private void tablePartieEnCoursMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePartieEnCoursMouseClicked
        Controleur.getInstance().getCtrlJoueur().partieEnCours((String) tablePartieEnCours.getValueAt(tablePartieEnCours.getSelectedRow(),1));        
    }//GEN-LAST:event_tablePartieEnCoursMouseClicked

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel labelPartieEnCours;
    private javax.swing.JLabel labelPartiesReplay;
    private javax.swing.JButton nouvellePartie;
    private javax.swing.JButton quitter;
    private javax.swing.JScrollPane scrollPanePartieEnCours;
    private javax.swing.JScrollPane scrollPaneReplay;
    private javax.swing.JTable tablePartieEnCours;
    private javax.swing.JTable tablePartieReplay;
    // End of variables declaration//GEN-END:variables

    
}
