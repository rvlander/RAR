/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rar.server;

import rar.client.Player;

/**
 *
 * @author rvlander
 */
public class Bullet {
    
    private Player sender;
    
    public Bullet(Player sender){
        this.sender = sender;
    }

    void update(double deltaTime) {
    }
    
    
    
}
