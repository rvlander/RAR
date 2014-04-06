/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rar.client;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.media.opengl.GL4;
import javax.media.opengl.GLProfile;
import rar.imageinput.ImageGetter;
import rar.imageinput.ImageGetterFactory;
import rar.server.Bullet;
import rar.utils.Image;
import rar.utils.Options;
import rar.utils.SceneObject;

/**
 *
 * @author rvlander
 */
public class GameManager {

    Image image;
    ImageGetter imageGetter;
    SceneObject obj;
    Player player;
    ArrayList<Bullet> bullets;

    public GameManager() {
        
        bullets = new ArrayList<>();
        imageGetter = ImageGetterFactory.createImageGetter();
        
        
        image = new Image(Options.getImageWidth(), Options.getImageHeight());
        obj = new SceneObject();
        player = new Player(new SocketCar());

    }

    public void update(float deltaTime) {
        BufferedImage bi = imageGetter.getImage();
        image.setImage(bi);
        obj.update();
    }

    public void render(GL4 gl, GLProfile glp) {

        image.render(gl, glp, 0, 0);
        obj.render(gl, glp, 0, 0);
    }

    public void turnLeft(double d) {
        player.turnLeft(d);
    }

    public void turnRight(double d) {
        player.turnRight(d);
    }

    public void moveForward(double d) {
        player.moveForward(d);
    }

    public void moveBackward(double d) {
        player.moveBackward(d);
    }
}
