/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rar.server;

import java.util.HashMap;

/**
 *
 * @author rvlander
 */
public class RaceEngine {
    
    HashMap<String, Racer> racers;
    World world;
    
    public RaceEngine(){
        racers = new HashMap<>();
        world = new World(racers);
    }
    
    public void addRacer(String name) throws RacerAlreadyPresentException{
        if(racers.containsKey(name)){
            throw new RacerAlreadyPresentException();
        }
        racers.put(name, new Racer());
    }
    
    public World getWorld(){
        return world;
    }
}
