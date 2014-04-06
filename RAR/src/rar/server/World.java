/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rar.server;

import rar.junk.RacerListener;
import rar.client.Player;
import java.util.ArrayList;

/**
 *
 * @author rvlander
 */
public class World implements RacerListener {
    
    private ArrayList<Player> racers;
    private ArrayList<Bullet> bullets;
    
    public World(){
        racers = new ArrayList<>();
        bullets = new ArrayList<>();
    }
    
    public void update(double deltaTime){
        for(Player racer : racers){
        }
        updateBullets(deltaTime);
    }
    
    public void addRacer(Player r){
        racers.add(r);
    }

    @Override
    public void bulletLaunched(Bullet b) {
        bullets.add(b);
    }

    private void updateBullets(double deltaTime) {
        for(Bullet b : bullets){
            b.update(deltaTime);
        }
        //collide bullet
    }
    
}
