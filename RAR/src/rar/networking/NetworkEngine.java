/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rar.networking;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import rar.junk.World;
import rar.networking.Connection;
/*import rar.networking.RacerAlreadyPresentException;
import rar.networking.RacerAlreadyPresentException;
/**
 *
 * @author rvlander
 */
public class NetworkEngine {
    
    ArrayList<String> players;
    
    public NetworkEngine(){
        players = new ArrayList<>();
    }
    
    public void addPlayer(String name) throws RacerAlreadyPresentException{
        if(players.contains(name)){
            throw new RacerAlreadyPresentException();
        }
        players.add(name);
    }

    public ArrayList<String> getPlayers() {
        return players;
    }
    
    

}
