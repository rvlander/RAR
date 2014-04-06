/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rar.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import rar.junk.RacerListener;

/**
 *
 * @author rvlander
 */
public class World implements RacerListener, Serializable {
    
    private HashMap<String,Racer> racers;
    private ArrayList<Bullet> bullets;
    
    public World(HashMap<String,Racer> r){
        racers = r;
        bullets = new ArrayList<>();
    }
    
    public void update(double deltaTime){
        /*for(Player racer : racers){
        }
        up*///dateBullets(deltaTime);
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
