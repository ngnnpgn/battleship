/*
 * To change this template, choose Tools | Templates
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
 * Patron de conception fabrique
 * @author jaylesr
 */
public class ConcreteFabriquePartie implements FabriquePartie {
    /**
     * une ConcreteFabriqueJoueur est un singleton
     */
    private static ConcreteFabriquePartie instance = null;

    public ConcreteFabriquePartie() {
        super();
    }
    
    /**
     * une ConcreteFabriqueJoueur est un singleton
     * @return 
     */
    public static ConcreteFabriquePartie getInstance() {
        if (ConcreteFabriquePartie.instance == null) {
            ConcreteFabriquePartie.instance = new ConcreteFabriquePartie();
        }
        return ConcreteFabriquePartie.instance;
    }

    /**
     * Permet de choisir l'adversaire pseudo-aléatoirement
     *
     * @param listeJoueur la liste de joueur
     * @param adversaire1 le joueur de base
     * @return le joueur choisi
     */
    private Joueur choisirAdversaire(ArrayList<Joueur> listeJoueur) throws
            ExceptionAdversaireInexistant {

        Random r = new Random();
        int index = 0 + r.nextInt(listeJoueur.size());
        
        if (listeJoueur.isEmpty()) {
            throw new ExceptionAdversaireInexistant();
        }
        Joueur retour = listeJoueur.get(index);
        return retour;
    }

    /**
     * Permet d'ajouter deux joueurs pour une partie dans la table participe [Lors d'une nouvelle partie]
     * @param joueur1
     * @param joueur2
     * @param idPartie
     * @throws SQLException 
     */
    private void AjouterJoueurParticipe(Joueur joueur1, Joueur joueur2,int idPartie) throws SQLException {
        PreparedStatement stt1, stt2;
        stt1 = BDManager.getInstance().prepareStatement("INSERT INTO participe VALUES(?,?)");
        stt2 = BDManager.getInstance().prepareStatement("INSERT INTO participe VALUES(?,?)");
        stt1.setString(1, joueur1.getPseudo());
        stt2.setString(1, joueur2.getPseudo());
        stt1.setInt(2, idPartie);
        stt2.setInt(2, idPartie);
        stt1.executeQuery();
        stt2.executeQuery();
        stt1.close();
        stt2.close();
    }
    
    /**
     * Creer une nouvelle partie en BD et retourne un objet Partie
     * @param joueur
     * @return
     * @throws bataillenavale.FabriquePartie.ExceptionAdversaireInexistant 
     */
    @Override
    public PartieLive nouvellePartie(Joueur joueur) throws
            ExceptionAdversaireInexistant {
        // Ajouter des conditions : il faut enlenver les adversaires qui ont déjà une partie en cours
        Joueur adversaire = null;
        ResultSet resultatRequete;
        int idPartie = 0;
        for (Joueur j : ConcreteFabriqueJoueur.getInstance().getParticipationJoueur(joueur)) {
            System.out.println("Pseudo : " + j.getPseudo() + ", parties : " + j.getNombreParties());
        }

        adversaire = choisirAdversaire(ConcreteFabriqueJoueur.getInstance().getParticipationJoueur(joueur));

        // insertion du tuple de la nouvelle partie
        PreparedStatement statement;
        Calendar dateActuelle = Calendar.getInstance();
        try {
            statement = BDManager.getInstance().prepareStatement(
                    "INSERT INTO parties VALUES(sequence_parties.nextval,?)");
            statement.setDate(1, new java.sql.Date(dateActuelle.YEAR,
                    dateActuelle.MONTH, dateActuelle.DAY_OF_MONTH));
            statement.executeQuery();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConcreteFabriquePartie.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // creation de l'objet partie et reccuperation de son idPartie
        try {
            statement = BDManager.getInstance().prepareStatement("SELECT sequence_parties.currval FROM parties");
            resultatRequete = BDManager.getInstance().executeQuery(statement);
            resultatRequete.next();
            idPartie = resultatRequete.getInt(1);
            AjouterJoueurParticipe(joueur, adversaire, idPartie);
            statement.close();
            resultatRequete.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConcreteFabriquePartie.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new PartieLive(joueur, adversaire, idPartie);
    }
    
    /**
     * Recupere une partie en cour entre 2 joueurs
     * @param joueur1
     * @param joueur2
     * @return
     * @throws bataillenavale.FabriquePartie.ExceptionPartieInexistante 
     */
    @Override
    public PartieLive partieEnCours(Joueur joueur1, Joueur joueur2) throws ExceptionPartieInexistante {
        try {
            PreparedStatement stt = BDManager.getInstance().prepareStatement(
                    "SELECT p1.idPartie FROM participe p1 CROSS JOIN participe p2 WHERE p1.pseudo = ? "
                            + "AND p2.pseudo = ? AND p1.idPartie = p2.idPartie");
            stt.setString(1,joueur1.getPseudo());
            stt.setString(2, joueur2.getPseudo());
            ResultSet resultat = stt.executeQuery();

            if (resultat.next()) {
                PartieLive partie = new PartieLive(joueur1, joueur2, resultat.getInt(1));
                resultat.close();
                stt.close();
                return partie;
            } else {
                resultat.close();
                stt.close();
                throw new ExceptionPartieInexistante();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConcreteFabriquePartie.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Recupere une partie finie a partir de son idPartie
     * @param idPartie
     * @return
     * @throws bataillenavale.FabriquePartie.ExceptionPartieInexistante 
     */
    public PartieEnReplay getPartieEnReplay(int idPartie) throws ExceptionPartieInexistante {
        PartieEnReplay partie = null;
        try {
            PreparedStatement stt = BDManager.getInstance().prepareStatement("SELECT p1.pseudo, p2.pseudo FROM Participe p1 CROSS JOIN participe p2 WHERE p1.idPartie = p2.idPartie AND p1.pseudo != p2.pseudo AND p1.idPartie = ?");
            stt.setInt(1, idPartie);
            ResultSet resultat = stt.executeQuery();
            // TODO ici a modifier car moche !!!
            if (resultat.first()) {
                Joueur j1 = new Joueur(resultat.getString(1));
                Joueur j2 = new Joueur(resultat.getString(2));
                partie = new PartieEnReplay(j1, j2, idPartie);
                ConcreteFabriqueEtat.getInstance().getEtatInitiaux(partie);
            } else {
                throw new ExceptionPartieInexistante();
            }
            stt.close();
            resultat.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConcreteFabriquePartie.class.getName()).log(Level.SEVERE, null, ex);
        }
        return partie;
    }
    
    /**
     * recuperer toutes les parties que le joueur peut voir en replay
     * @param joueur
     * @return
     * @throws bataillenavale.FabriquePartie.ExceptionPartieInexistante 
     */
    @Override
    public LinkedList<PartieEnReplay> getListePartieAObserver(Joueur joueur) throws ExceptionPartieInexistante {
        LinkedList<PartieEnReplay> listePartieReplay = new LinkedList<PartieEnReplay>();
        
        String pseudo = joueur.getPseudo();
        
        // Liste des parties auxquelles un joueur ne participe pas ou qui sont terminées
        String requete = "SELECT  p1.idPartie, p1.pseudo as p1_pseudo, p2.pseudo as p2_pseudo "
                       + "FROM Participe p1, Participe p2 "
                       + "WHERE p1.idPartie = p2.idPartie "
                       + "AND p1.idPartie IN "
                       +    "("
                       +        "(SELECT p.idPartie"
                       +        " FROM Parties p"
                       +        " WHERE p.idPartie NOT IN "
                       +            "(SELECT pp.idPartie"
                       +            " FROM Participe pp"
                       +            " WHERE pp.pseudo = '" + pseudo + "')"
                       +        ") "
                       +         "UNION "
                       +        "(SELECT ppp.idPartie "
                       +        "FROM Agagne ppp"
                       +        ")"
                       +    ")"
                       + "AND p1.pseudo != p2.pseudo "
                       + "ORDER BY idPartie";
        try {
            PreparedStatement stt= BDManager.getInstance().prepareStatement(requete);
            ResultSet resultat = stt.executeQuery();
            if(!resultat.next()){
                throw new ExceptionPartieInexistante();
            }
            
            
            //resultat = BDManager.getInstance().simpleRequete(requete);
            
            while (resultat.next()) {
                // insertion de chaque partie dans la liste
                int idPartie = resultat.getInt("idPartie");
                String pseudoJoueur1 = resultat.getString("p1_pseudo");
                String pseudoJoueur2 = resultat.getString("p2_pseudo");
                
                Joueur j1 = new Joueur(pseudoJoueur1);
                Joueur j2 = new Joueur(pseudoJoueur2);
                PartieEnReplay partieReplay = new PartieEnReplay(j1, j2, idPartie);
                
                listePartieReplay.add(partieReplay);
                resultat.next();
            }
            stt.close();
            resultat.close();            
        } catch (SQLException ex) {
            Logger.getLogger(ConcreteFabriquePartie.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listePartieReplay;
    }
    
    /**
     * recupere toutes les parties en cours du joueur
     * @param joueur
     * @return 
     */
    @Override
    public Set<PartieLive> getPartieEnCours(Joueur joueur) {
        ResultSet resultat;
        PreparedStatement stt;
        Set<PartieLive> setPartieLive = new HashSet<PartieLive>();
        String req = new String("SELECT  p1.idpartie,p1.pseudo,p2.pseudo"
                    + " FROM participe p1,participe p2"
                    + " WHERE p1.idpartie = p2.idpartie"
                    + " and p1.pseudo != p2.pseudo and p1.pseudo = '"
                    + joueur.getPseudo() + "'" 
                    + " and p1.idpartie IN ("
                    + " SELECT parties.idpartie"
                    + " FROM parties,participe "
                    + " WHERE parties.idpartie NOT IN"
                    + " (select idpartie FROM aGagne)"
                    + " and parties.idpartie = participe.idpartie "
                    + " and participe.pseudo = '" + joueur.getPseudo() + "')");
        try{
            stt = BDManager.getInstance().prepareStatement(req);
            resultat = stt.executeQuery();
                    
            while (resultat.next()) {
                PartieLive partie = new PartieLive(joueur, new Joueur(resultat.
                        getString(3)), resultat.getInt("idpartie"));
                setPartieLive.add(partie);
            }
            stt.close();
            resultat.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConcreteFabriquePartie.class.getName()).log(Level.SEVERE, null, ex);
        }
        return setPartieLive;
    }
}
