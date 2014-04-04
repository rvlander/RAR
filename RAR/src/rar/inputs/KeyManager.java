/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rar.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import rar.main.RAR;

/**
 *
 * @author rvlander
 */
public class KeyManager implements KeyListener {

    private RAR rar;

    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;

    public KeyManager(RAR rar) {
        this.rar = rar;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                up = true;
                break;
            case KeyEvent.VK_DOWN:
                down = true;
                break;
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
        }
        sendState();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_DOWN:
                down = false;
                break;
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
        }
        sendState();
    }

    public void sendState() {
        rar.moveForward(up ? 1 : 0);
        rar.moveBackward(down ? 1 : 0);
        rar.turnLeft(left ? 1 : 0);
        rar.turnRight(right ? 1 : 0);

    }

}
