/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rar.utils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author rvlander
 */
public class ImagePanel extends JPanel{
    private BufferedImage im;
    
    public void setImage(BufferedImage im){
        this.im = im;
        this.repaint();
    }
    
    @Override
    public void paint(Graphics g){
        g.drawImage(im, 0, 0, null);
        drawGraphics((Graphics2D)g);
    }
    
    public void drawGraphics(Graphics2D g){}
    
}
