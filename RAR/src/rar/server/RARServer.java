/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rar.server;

/**
 *
 * @author rvlander
 */
public class RARServer {
    
    Server server;
    RaceEngine raceEngine;
    
    public RARServer(){
        raceEngine = new RaceEngine();
        server = new Server(raceEngine);
    }
    
    public static void main (String[] args){
        new RARServer();
    }
    
}
