/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rar.inputs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import rar.client.RAR;

/**
 *
 * @author rvlander
 */
public class KeyManager implements KeyListener, ActionListener {

    private RAR rar;

    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;

    private Timer upTimer;
    private Timer downTimer;
    private Timer leftTimer;
    private Timer rightTimer;
    
    private long delay=1;

    public KeyManager(RAR rar) {
        this.rar = rar;
        
        upTimer = new Timer(1, this);
        downTimer = new Timer(1, this);
        leftTimer = new Timer(1, this);
        rightTimer = new Timer(1, this);
        
        upTimer.setRepeats(false);
        downTimer.setRepeats(false);
        leftTimer.setRepeats(false);
        rightTimer.setRepeats(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                upTimer.stop();
                up = true;
                break;
            case KeyEvent.VK_DOWN:
                downTimer.stop();
                down = true;
                break;
            case KeyEvent.VK_LEFT:
                leftTimer.stop();
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
                rightTimer.stop();
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
                upTimer.start();
                break;
            case KeyEvent.VK_DOWN:
                downTimer.start();
                break;
            case KeyEvent.VK_LEFT:
                leftTimer.start();
                break;
            case KeyEvent.VK_RIGHT:
                rightTimer.start();
                break;
        }
    }

    public void sendState() {
        rar.moveForward(up ? 1 : 0);
        rar.moveBackward(down ? 1 : 0);
        rar.turnLeft(left ? 1 : 0);
        rar.turnRight(right ? 1 : 0);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == upTimer){
            up = false;
        }
        else if (e.getSource() == downTimer){
            down = false;
        }
        else if (e.getSource() == leftTimer){
            left = false;
        }
        else if (e.getSource() == rightTimer){
            right=false;
        }
        sendState();
    }

}
