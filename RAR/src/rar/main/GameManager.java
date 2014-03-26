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

/**
 *
 * @author rvlander
 */
public class  GameManager{
    
    Image im;

    public GameManager() {
        im = new Image(1920,1080,ImageGetterFactory.createImageGetter());

    }

    public void update(float deltaTime) {
    }

    public void render(GL4 gl,GLProfile glp) {
        im.render(gl, glp, 0, 0);
    }

}
