/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rar.tracking;

/**
 *
 * @author rvlander
 */
public class TrackedObject {
    private double x;
    private double y;
    private double size;
    
    public TrackedObject(double x, double y, double size){
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSize() {
        return size;
    }
    
    
    
}
