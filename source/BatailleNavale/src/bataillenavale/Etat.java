/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bataillenavale;

/**
 *
 * @author pierre
 */
public class Etat extends AbstractEtat {

	/**
         * Données du bateau
         */
        private AbstractBateau bateau;
	private int x;
	private int y;
	private int vie;
	private Direction direction;
	private int taille;

	/**
         * Constructeur
         * @param bateau
         * @param x
         * @param y
         * @param vie
         * @param direction
         * @param taille 
         */
	public Etat(AbstractBateau bateau, int x, int y, int vie, Direction direction, int taille) {
                this.bateau = bateau;
		this.x = x;
		this.y = y;
		this.vie = vie;
		this.direction = direction;
		this.taille = taille;
	}
        
        /**
         * Accesseur coord. x du pivot
         * @return 
         */
        public int getX() {
        return x;
        }
        
        /**
         * Accesseur coord. y du pivot
         * @return 
         */
        public int getY() {
          return y;
        }
        
        /**
         * Accesseur bateau
         * @return 
         */
        public AbstractBateau getBateau(){
            return this.bateau;
        }
        
        /**
         * Accesseur de la vie du bateau
         * @return 
         */
        public int getVie() {
          return vie;
        }

        /**
         * Accesseur de la direction du bateau
         * @return 
         */
        public Direction getDirection() {
          return direction;
        }

        /**
         * Accesseur de la taille du bateau
         * @return 
         */
        public int getTaille() {
           return taille;
        }
        
        /**
         * Renvoie la direction du bateau sous format de string
         * @return 
         */
        public String getStringDirection() {
            if (this.direction == Direction.NORD) {
                return "NORD";
            } else if (this.direction == Direction.SUD) {
                return "SUD";
            } else if (this.direction == Direction.EST) {
                return "EST";
            } else {
                return "OUEST";
            }
        } 
    
    /**
     * verifie si un bateau est touché par un tir
     * @param coordX
     * @param coordY
     * @return 
     */
    @Override
    public boolean estTouche(int coordX, int coordY) {
        if(compareCoord(x, coordX, y, coordY))
            return true;
        
        int[] coordArray = coordSuivante(x, y); 
        if(compareCoord(coordX, coordArray[0], coordY, coordArray[1]))
            return true;
 
        // cas du destroyeur
        if(taille > 2){  
            int [] coordArray2 = coordSuivante(coordArray[0], coordArray[1]);
            if(compareCoord(coordX, coordArray2[0], coordY, coordArray2[1]))
                return true;
        }     
        return false;
   }
    
    /**
     * Comparaison de 2 coordonnées
     * @param coordX1
     * @param coordX2
     * @param coordY1
     * @param coordY2
     * @return 
     */
    private boolean compareCoord(int coordX1,int coordX2, int coordY1, int coordY2){
        return ((coordX1==coordX2) && (coordY1==coordY2));
    }
    
    /**
     * Retourne la coordonnée suivante avec la direction du bateau
     * @param coordX
     * @param coordY
     * @return 
     */
    private int[] coordSuivante(int coordX, int coordY) {
        int[] array = new int[2];
        switch (direction) {
            case NORD:
                array[0]=coordX;
                array[1]=coordY - 1;
                break;
            case SUD:
                array[0]=coordX;
                array[1]=coordY + 1;
                break;
            case EST:
                array[0]= coordX + 1;
                array[1]=coordY;
                break;
            case OUEST:
                array[0]=coordX - 1;
                array[1]= coordY;
                break;
            default:
                System.out.println("erreur lors du calcule de la coordonnée suivante \n");
        }
        return array;
    }

    /**
     * Modifieur de la vie du bateau
     * @param vie 
     */
    void setVie(int vie) {
        this.vie = vie;
    }

    @Override
    public boolean estVivant() {
        return (this.vie > 0);
    }
        
}