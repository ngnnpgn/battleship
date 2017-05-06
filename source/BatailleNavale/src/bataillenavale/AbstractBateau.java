/*
 * La classe mère des bateaux 
 *
 */
package bataillenavale;

import bataillenavale.AbstractPartieLive.ExceptionCoupImpossible;
import static bataillenavale.Direction.EST;
import static bataillenavale.Direction.NORD;
import static bataillenavale.Direction.OUEST;
import static bataillenavale.Direction.SUD;
import java.util.LinkedList;

/**
 *
 * @author thomas
 */
public abstract class AbstractBateau {

    protected int coordX;
    protected int coordY;
    protected Direction direction;

    
    protected int vie;
    protected int taille;

    
    protected int idBateau;
    
    /**
     * Utiliser pour le debug
     */
    public void afficheEtat(){
        String s="coordX="+coordX+" coordY="+coordY;
        s+="\ndirection="+direction.toString();
        s+="\nvie="+vie+"\n";
        System.out.print(s);
                
    }
    
    public abstract AbstractBateau copy();
    
    public AbstractBateau(int idBateau,int vie) {
        this.idBateau = idBateau;
        this.vie = vie;
    }
    
    public AbstractBateau(int idBateau,int vie,Direction direction,int taille) {
        this.idBateau = idBateau;
        this.vie = vie;
        this.direction = direction;
        this.taille=taille;
    }
    
    /**
     * Permet d'avoir la traduction avec l'ensemble des coordonnées
     * @return
     * @throws bataillenavale.AbstractPartieLive.ExceptionCoupImpossible 
     */
    public LinkedList<Integer[]> getCoordonees() throws ExceptionCoupImpossible {
        LinkedList<Integer[]> list= new LinkedList<Integer[]>();
        int  coord[]=new int[]{coordX,coordY};
        for(int i=0; i<taille;i++){
            if (!(coord[0] < 1 || coord[0] > 10 || coord[1] < 1 || coord[1] > 10)){
                list.push(new Integer[]{coord[0],coord[1]});
                coord=coordSuivante(coord[0], coord[1]);
            }            
        }
        return list;
    }
    
    /**
     * Permet d'avoir la coordonnée suivante
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
                System.out.println("erreur dans coordSuivante \n");
        }
        return array;
    }
    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }
    public int getTaille() {
        return taille;
    }
    public Direction getDirection() {
        return direction;
    }
    public void setCoordX(int x){
        this.coordX = x;
    }
    
    public void setCoordY(int y){
        this.coordY = y;
    }

    public void setVie(int vie){
        this.vie = vie;
    }
    
    public int getVie() {
        return vie;
    }

    public int getIdBateau() {
        return idBateau;
    }
    
    public void setTaille(int taille) {
        this.taille = taille;
    }
    
    public boolean estVivant(){
        return (this.vie > 0);
    }
    
    
    /**
     * Ensemble de fonctions permettant de deplacer le bateau
     * @param deplacement
     * @throws bataillenavale.AbstractPartieLive.ExceptionCoupImpossible 
     */
    
    
    public void deplacer(Deplacement deplacement) throws ExceptionCoupImpossible{
        switch(deplacement){
            case DROITE:
                this.tournerDroite();
                break;
            case GAUCHE:
                this.tournerGauche();
                break;
            case AVANT:
                this.avancer();
                break;
            case ARRIERE:
                this.Reculer();
                break;
            default:
                throw new ExceptionCoupImpossible();
        }
        
    }
    
    public void avancer() throws ExceptionCoupImpossible{
        switch(direction){
            case NORD:
                this.coordY--;
                break;
            case SUD:
                this.coordY++;
                break;
            case EST:
                this.coordX++;
                break;
            case OUEST:
                this.coordX--;
                break;
            default:
               throw new ExceptionCoupImpossible(); 
        }
    }
    
    private void Reculer() throws ExceptionCoupImpossible{
        switch(direction){
            case NORD:
                this.coordY++;
                break;
            case SUD:
                this.coordY--;
                break;
            case EST:
                this.coordX--;
                break;
            case OUEST:
                this.coordX++;
                break;
            default:
               throw new ExceptionCoupImpossible(); 
        }
    }
    
    private void tournerGauche() throws ExceptionCoupImpossible{
        switch(direction){
            case NORD:
                this.direction = OUEST;
                break;
            case SUD:
                this.direction = EST;
                break;
            case EST:
                this.direction = NORD;
                break;
            case OUEST:
                this.direction = SUD;
                break;
            default:
               throw new ExceptionCoupImpossible(); 
        }
    }
    
    private void tournerDroite() throws ExceptionCoupImpossible{
        switch(direction){
            case NORD:
                this.direction = EST;
                break;
            case SUD:
                this.direction = OUEST;
                break;
            case EST:
                this.direction = SUD;
                break;
            case OUEST:
                this.direction = NORD;
                break;
            default:
               throw new ExceptionCoupImpossible(); 
        }
    }

    void setDirection(Direction direction) {
        this.direction = direction;
    }
}
