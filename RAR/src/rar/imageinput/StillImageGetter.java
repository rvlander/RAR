/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rar.imageinput;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author rvlander
 */
class StillImageGetter implements ImageGetter {

    BufferedImage bi;
    
    public StillImageGetter() {
        try {
            bi = ImageIO.read(new File("resources/cockpit.png"));
        } catch (IOException ex) {
            Logger.getLogger(StillImageGetter.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
    }

    @Override
    public BufferedImage getImage() {
        return bi;
    }
    
}
