/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import InterfaceGraphique.Interface;
import bataillenavale.BDManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gounonj
 */
public class CtrlInterface {

       public void fermerConnexion() {
        try {
            BDManager.getInstance().closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    public void ouvrirConnexion() {
        try {          
            System.out.println("Connexion ouverte");
            BDManager.getInstance().openConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
