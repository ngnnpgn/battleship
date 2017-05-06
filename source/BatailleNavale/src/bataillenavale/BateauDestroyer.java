
package bataillenavale;

/**
 *
 * @author thomas
 */
public class BateauDestroyer extends AbstractBateau {
    
    /**
     * Constructeur
     * @param idBateau
     * @param coordX
     * @param coordY 
     */
    public BateauDestroyer(int idBateau, int coordX, int coordY){
        super(idBateau, 3);
        this.coordX = coordX;
        this.coordY = coordY;      
    }
    
    /**
     * Constructeur
     * @param idBateau
     * @param coordX
     * @param coordY
     * @param direction 
     */
    public BateauDestroyer(int idBateau, int coordX, int coordY, Direction direction){
        super(idBateau, 3, direction, 3);
        this.coordX = coordX;
        this.coordY = coordY;      
    }
    public BateauDestroyer(BateauDestroyer copy)
    {
        super(copy.idBateau, 3, copy.direction, 3);
        this.coordX = copy.coordX;
        this.coordY = copy.coordY;      
    }
    /**
     * Constructeur
     * @param idBateau 
     */
    public BateauDestroyer(int idBateau) {
        super(idBateau, 3);
    }   

    @Override
    public AbstractBateau copy() {
        return new BateauDestroyer(this);
    }
}