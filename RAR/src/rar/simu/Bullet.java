/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rar.simu;

import rar.simu.Racer;
import java.io.Serializable;

/**
 *
 * @author rvlander
 */
public class Bullet implements Serializable{
    
    private Racer sender;
    
    public Bullet(Racer sender){
        this.sender = sender;
    }

    void update(double deltaTime) {
    }
    
    
    
}
