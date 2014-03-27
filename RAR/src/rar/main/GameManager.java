/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rar.main;

import javax.media.opengl.GL4;
import javax.media.opengl.GLProfile;
import rar.imageinput.ImageGetterFactory;
import rar.utils.Image;
import rar.utils.SceneObject;

/**
 *
 * @author rvlander
 */
public class GameManager {

    Image im;
    SceneObject obj;

    public GameManager() {
        im = new Image(1280, 720, ImageGetterFactory.createImageGetter());
        obj = new SceneObject();

    }

    public void update(float deltaTime) {
        obj.update();
    }

    public void render(GL4 gl, GLProfile glp) {

        im.render(gl, glp, 0, 0);
        obj.render(gl, glp, 0, 0);
    }

}
