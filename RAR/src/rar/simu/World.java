/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rar.simu;

import java.util.ArrayList;

/**
 *
 * @author rvlander
 */
public class World implements RacerListener {
    
    private ArrayList<Racer> racers;
    private ArrayList<Bullet> bullets;
    
    public World(){
        racers = new ArrayList<>();
        bullets = new ArrayList<>();
    }
    
    public void update(double deltaTime){
        for(Racer racer : racers){
            racer.update(deltaTime);
        }
        updateBullets(deltaTime);
    }
    
    public void addRacer(Racer r){
        racers.add(r);
        r.addListener(this);
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
