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
public class Racer {

    private ArrayList<RacerListener> listeners;
    ConcreteCar car;

    public Racer() {

    }

    public Racer(ConcreteCar Car) {
        this.car = car;
    }

    public void update(double deltaTime) {
    }

    public void addListener(RacerListener l) {
        listeners.add(l);
    }

    public void shoot() {
        Bullet b = new Bullet(this);
        for (RacerListener rl : listeners) {
            rl.bulletLaunched(b);
        }
    }

    public void turnLeft(double d) {
        car.turnLeft(d);
    }

    public void turnRight(double d) {
        car.turnRight(d);
    }

    public void moveForward(double d) {
        car.moveForward(d);
    }

    public void moveBackward(double d) {
        car.moveBackward(d);
    }

}
