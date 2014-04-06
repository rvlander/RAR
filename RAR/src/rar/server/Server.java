/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rar.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rvlander
 */
public class Server {

    RaceEngine raceEngine;
    ServerSocket socket;

    Server(RaceEngine re) {
        raceEngine = re;
        initializeServer();
    }

    private void initializeServer() {
        try {
            socket = new ServerSocket(9999);
            while (true) {
                final Socket s = socket.accept();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handleNewClient(s);
                    }
                }).start();
            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }

    }
    
    private void handleNewClient(Socket s){
            System.out.println("New client " + s.getInetAddress().getHostAddress() +" connected.");
            new Thread(new Connection(s, raceEngine)).start();
    }

}
