/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rar.networking;

/**
 *
 * @author rvlander
 */
public class RARServer {
    
    Server server;
    NetworkEngine raceEngine;
    
    public RARServer(){
        raceEngine = new NetworkEngine();
        server = new Server(raceEngine);
    }
    
    public static void main (String[] args){
        new RARServer();
    }
    
}
