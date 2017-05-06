/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jocelyn
 */
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenetre extends JFrame{
    private final Panneau pan;
    
    
    public Fenetre(){        
       
        this.setTitle("Ma première fenêtre Java");        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setSize(900,600);
        this.setLocationRelativeTo(null);
        pan=new Panneau();
        JPanel boutonPane = new JPanel();
        JButton bouton1 = new JButton("Connexion");
        JButton bouton2 = new JButton("Inscription");
        boutonPane.add(bouton1);
        boutonPane.add(new JButton("Inscription"));
        this.getContentPane().add(boutonPane, BorderLayout.NORTH);
        this.getContentPane().add(pan, BorderLayout.CENTER);
        this.setVisible(true);
    } 
}