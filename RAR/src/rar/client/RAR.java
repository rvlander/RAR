/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rar.client;

import com.jogamp.opengl.util.Animator;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import javax.media.opengl.GL4;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;
import rar.inputs.KeyManager;

/**
 *
 * @author rvlander
 */
public class RAR implements GLEventListener {

    private GameManager gameManager;
    private long before;
    private GLProfile glp;

    static {
        GLProfile.initSingleton();
    }

    public RAR(GLProfile p) {
        gameManager = new GameManager();
        before = new Date().getTime();
        glp = p;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        drawable.getGL().setSwapInterval(1);
    }

    @Override
    public void dispose(GLAutoDrawable glad) {
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        update();
        render(drawable);
    }

    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
    }

    private void update() {

        long now = new Date().getTime();
        double deltaTime = ((double) now - before) / 1000;
        before = now;
        gameManager.update((float) deltaTime);
    }

    private void render(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();

        gl.glBlendFunc(gl.GL_SRC_ALPHA, gl.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(gl.GL_BLEND);

        gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        gl.glClear(gl.GL_COLOR_BUFFER_BIT);

        gameManager.render(gl, glp);

    }

    public void turnLeft(double d) {
        gameManager.turnLeft(d);
    }

    public void turnRight(double d) {
        gameManager.turnRight(d);
    }

    public void moveForward(double d) {
        gameManager.moveForward(d);
    }

    public void moveBackward(double d) {
        gameManager.moveBackward(d);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        GLCanvas canvas = new GLCanvas(caps);

        JFrame frame = new JFrame();
        //frame.setUndecorated(true);
        // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        canvas.setSize(1280, 720);
        frame.getContentPane().add(canvas);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        RAR rar = new RAR(glp);
        canvas.addGLEventListener(rar);
        canvas.addKeyListener(new KeyManager(rar));
        canvas.requestFocus();
        Animator animator = new Animator(canvas);
        animator.start();
    }

}
