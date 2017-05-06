/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jocelyn
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
 
public class Panneau extends JPanel {
  @Override
  public void paintComponent(Graphics g){
    try {
      Image img = ImageIO.read(new File("0000789.jpg"));
      //g.drawImage(img, 0, 0, this);
      //Pour une image de fond
      g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
    } catch (IOException e) {
        e.printStackTrace();
    }
    //g.setColor(Color.LIGHT_GRAY);
  //  g.fillRoundRect(this.getWidth()-this.getWidth()/4-10, 0, this.getWidth()/4+20, this.getHeight(), 10,10);
  }               
}