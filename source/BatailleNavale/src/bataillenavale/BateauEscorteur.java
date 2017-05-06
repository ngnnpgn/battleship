/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bataillenavale;

/**
 *
 * @author thomas
 */
public class BateauEscorteur extends AbstractBateau{
   
    /**
     * Constructeur
     * @param idBateau
     * @param coordX
     * @param coordY 
     */
    public BateauEscorteur(int idBateau, int coordX, int coordY){
        super(idBateau, 2);
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
    public BateauEscorteur(int idBateau, int coordX, int coordY, Direction direction){
        super(idBateau, 2, direction, 2);
        this.coordX = coordX;
        this.coordY = coordY;
      
    }
     public BateauEscorteur(BateauEscorteur copy)
    {
        super(copy.idBateau, 2, copy.direction, 2);
        this.coordX = copy.coordX;
        this.coordY = copy.coordY;      
    }
    /**
     * Constructeur
     * @param idBateau 
     */
    public BateauEscorteur(int idBateau) {
        super(idBateau, 2);
    }

    @Override
    public AbstractBateau copy() {
        return new BateauEscorteur(this);
    }
}
