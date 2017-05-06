/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bataillenavale;

import java.util.*;

/**
 *
 * @author pierre
 */
public interface FabriqueEtat {
    
      

        /**
         * 
         * @param partie
         * @param joueur
         * @return
         * @throws bataillenavale.FabriqueEtat.ExceptionPartiePerdue 
         */
        public LinkedList<Etat> getEtatEncours(AbstractPartie partie, Joueur joueur) throws ExceptionPartiePerdue;
        
        /**
         * 
         * @param partie
         * @param bateau
         * @return 
         */
        public Etat getEtatBateau(AbstractPartie partie, AbstractBateau bateau);

        public static class ExceptionPartieInexistante extends Exception {

		public ExceptionPartieInexistante() {
		}
	}
        
	public static class ExceptionActionInexistante extends Exception {

		public ExceptionActionInexistante() {
		}
	}

	public static class ExceptionListeActionInexistante extends Exception {

		public ExceptionListeActionInexistante() {
		}
	}

	public static class ExceptionBateauInexistant extends Exception {

		public ExceptionBateauInexistant() {
		}
	}
        
        public static class ExceptionPartiePerdue extends Exception {

            public ExceptionPartiePerdue() {
        }
    }
}
