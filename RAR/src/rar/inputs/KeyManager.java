/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rar.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author rvlander
 */
public class KeyManager implements KeyListener{

    @Override
    public void keyTyped(KeyEvent e) {
        System.exit(0);
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
