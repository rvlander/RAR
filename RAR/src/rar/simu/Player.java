/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rar.simu;

/**
 *
 * @author rvlander
 */
public class Player {

    private ConcreteCar car;

    public Player(ConcreteCar car) {
        this.car = car;
    }

    public void shoot() {
        //Bullet b = new Bullet(this);
        //reseau
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
