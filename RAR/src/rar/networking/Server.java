/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rar.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rvlander
 */
public class Server implements ConnectionListener {

    NetworkEngine networkEngine;
    ServerSocket socket;
    ArrayList<Connection> connections;

    Server(NetworkEngine re) {
        networkEngine = re;
        connections = new ArrayList<>();
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

    private void handleNewClient(Socket s) {
        System.out.println("New client " + s.getInetAddress().getHostAddress() + " connected.");
        Connection c = new Connection(s, this);
        connections.add(c);
        new Thread(c).start();
    }

    @Override
    public void registered(Connection con, String name) {
        try {
            networkEngine.addPlayer(name);
            for (Connection c : connections) {
                if (c != con) {
                    c.register(name);
                }
            }

            for (String s : networkEngine.getPlayers()) {
                con.register(s);
            }
        } catch (RacerAlreadyPresentException e) {
            con.raisePlayerAlreadyPresentException();
        }
    }

    @Override
    public void raisedPlayerAlreadyPresentException() {
    }

}
