
package bataillenavale;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Cette classe (singleton pour qu'il n'y ai qu'une instance), s'occupe de se connecter à la base
 * de donnée et d'effectuer les requêtes faites par les fabriques
 */
public class BDManager {
    
    /**
     * La seule instance de BDManager
     */
    private static BDManager instance = null;
    Connection connection;
    static final String CONNECT_URL = "jdbc:oracle:thin:@ensioracle1.imag.fr:1521:ensioracle1";
    static final String USER = "ghesquit";
    static final String PASSWD = "ghesquit";
       
    /*
     * Constructeur de cette classe, privée
     */
    private BDManager(){
        super();
    }
    
    /**
     * Permet de créer ou récupérer l'instance unique de cette classe
     * @return l'instance de BDManager
     */
    public static BDManager getInstance(){
        //si il faut créer une nouvelle instance
        if(BDManager.instance == null){
            BDManager.instance = new BDManager();
        }
        return BDManager.instance;
    }
    
    public void openConnection() throws SQLException{
        // Enregistrement du driver Oracle
        System.out.println("Chargement du driver oracle");
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        System.out.println("Driver oracle chargé");
        connection = DriverManager.getConnection(CONNECT_URL, USER, PASSWD);
        
    }
    
    /*
     * Permet de fermer la connection une fois celle-ci établie
     */
    public void closeConnection() throws SQLException{
        connection.close();
        System.out.println("connexion fermée");
    }
    
    /**
     * Permet d'effectuer une simple requete composée d'une string
     * @param requete
     * @return
     * @throws SQLException 
     */
    public ResultSet simpleRequete(String requete) throws SQLException{
 
        PreparedStatement stt = connection.prepareStatement(requete, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        return stt.executeQuery();
    }
    
    public PreparedStatement prepareStatement(String requete) throws SQLException{
        return connection.prepareStatement(requete, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }
    
    /**
     * Permet d'executer une requete déjà preparée
     * @param stt
     * @return
     * @throws SQLException 
     */
    public ResultSet executeQuery(PreparedStatement stt) throws SQLException{
        return stt.executeQuery();
    }
    
    public void beginTransaction(){
        try {
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(BDManager.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }
    
    public void commit(){
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(BDManager.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }
    public void rollback(){
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(BDManager.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

}
