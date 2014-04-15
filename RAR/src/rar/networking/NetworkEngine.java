/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rar.networking;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import rar.junk.World;
import rar.networking.Connection;
/*import rar.networking.RacerAlreadyPresentException;
import rar.networking.RacerAlreadyPresentException;
/**
 *
 * @author rvlander
 */
public class NetworkEngine {
    
    HashMap<String,Connection> players;
    
    public NetworkEngine(){
        players = new HashMap<>();
    }
    
    public void addPlayer(String name,Connection con) throws RacerAlreadyPresentException{
        if(players.containsKey(name)){
            throw new RacerAlreadyPresentException();
        }
        players.put(name,con);
    }

    public Collection<Connection> getPlayers() {
        return players.values();
    }
    
    public Set<String> getNames(){
        return players.keySet();
    }
    
    

}
