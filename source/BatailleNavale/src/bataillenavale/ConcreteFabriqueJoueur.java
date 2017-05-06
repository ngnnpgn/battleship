/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bataillenavale;

import java.sql.*;
import java.util.LinkedList;
import java.util.logging.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Patron de conception fabrique
 *
 * @author jaylesr
 */
public class ConcreteFabriqueJoueur implements FabriqueJoueur {

    /**
     * une ConcreteFabriqueJoueur est un singleton
     */
    private static ConcreteFabriqueJoueur instance = null;

    private ConcreteFabriqueJoueur() {
        super();
    }

    /**
     * une ConcreteFabriqueJoueur est un singleton
     */
    public static FabriqueJoueur getInstance() {
        //si il faut créer une nouvelle instance
        if (ConcreteFabriqueJoueur.instance == null) {
            ConcreteFabriqueJoueur.instance = new ConcreteFabriqueJoueur();
        }
        return ConcreteFabriqueJoueur.instance;
    }

    /**
     * recherche dans la base le tuple joueur avec son pseudo
     *
     * @param pseudo
     * @return
     * @throws bataillenavale.FabriqueJoueur.pseudoInexistantException
     */
    @Override
    public Joueur getJoueur(String pseudo) throws pseudoInexistantException {
        String requete = "SELECT * FROM Joueur j WHERE j.pseudo = '" + pseudo + "'";
        try {
            PreparedStatement stt = BDManager.getInstance().prepareStatement(requete);
            ResultSet resultat = stt.executeQuery();
            if (!resultat.next()) {
                throw new pseudoInexistantException();
            }
            stt.close();
            resultat.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConcreteFabriqueJoueur.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Joueur(pseudo);
    }

    /**
     * Creer un joueur lors de l'incription
     *
     * @param pseudo
     * @param nom
     * @param prenom
     * @param email
     * @param date
     * @param NumRue
     * @param nomVille
     * @return un nouvelle objet joueur
     * @throws bataillenavale.FabriqueJoueur.pseudoExistantException
     */
    @Override
    public Joueur getJoueur(String pseudo, String nom, String prenom, String email, Date date, int NumRue, String nomVille) throws pseudoExistantException {
        PreparedStatement statement;
        try {
            statement = BDManager.getInstance().prepareStatement("INSERT INTO joueur VALUES(?,?,?,?,?,?,?)");
            statement.setString(1, pseudo);
            statement.setString(2, nom);
            statement.setString(3, prenom);
            statement.setString(4, email);
            statement.setDate(5, date);
            statement.setInt(6, NumRue);
            statement.setString(7, nomVille);
            statement.executeQuery();
            statement.close();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1) {
                throw new pseudoExistantException();
            } else {
                Logger.getLogger(ConcreteFabriqueJoueur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new Joueur(pseudo);
    }

    /**
     * Trouver tout les participants potentiels lors d'une creation de partie
     *
     * @param joueur
     * @return
     */
    @Override
    public ArrayList<Joueur> getParticipationJoueur(Joueur joueur) {
        ResultSet resultat;
        PreparedStatement stt;
        ArrayList<Joueur> listeJoueur = new ArrayList<Joueur>();
        try {
            //on commence par récuperer les joueurs qui n'ont jamais joués une seule partie
            String requete = "SELECT pseudo FROM joueur WHERE NOT EXISTS (SELECT DISTINCT pseudo FROM participe WHERE joueur.pseudo = participe.pseudo) AND joueur.pseudo != ?";
            stt = BDManager.getInstance().prepareStatement(requete);
            stt.setString(1, joueur.getPseudo());
            resultat = stt.executeQuery();
            while (resultat.next()) {
                listeJoueur.add(new Joueur(resultat.getString(1), 0));
            }
            stt.close();
            resultat.close();;

            //ensuite on ajoute les joueurs qui ont déjà participé à une partie
            //TODO enlevé ceux qui ne participent pas
            requete = "SELECT pseudo, COUNT(idPartie) FROM participe part WHERE NOT EXISTS(SELECT p1.pseudo "
                    + "FROM participe p1 CROSS JOIN participe p2 WHERE p1.IDPARTIE=p2.IDPARTIE "
                    + "AND p1.pseudo != p2.pseudo AND p1.pseudo != ? "
                    + "AND p2.pseudo = ? AND part.pseudo = p1.pseudo ) "
                    + "AND pseudo != ? GROUP BY(pseudo)";

            stt = BDManager.getInstance().prepareStatement(requete);
            stt.setString(1, joueur.getPseudo());
            stt.setString(2, joueur.getPseudo());
            stt.setString(3, joueur.getPseudo());
            resultat = stt.executeQuery();

            while (resultat.next()) {
                listeJoueur.add(new Joueur(resultat.getString(1), resultat.getInt(2)));
            }
            stt.close();
            resultat.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConcreteFabriqueJoueur.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listeJoueur;
    }

}
