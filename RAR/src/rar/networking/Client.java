/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rar.networking;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import rar.utils.Options;

/**
 *
 * @author rvlander
 */
public class Client implements ConnectionListener{
    
    Connection connection;
    Socket socket;
    ClientListener listener;
    
    public Client() {
        initializeClient();
    }
    
    public void setListener(ClientListener c){
        listener =c;
    }

    private void initializeClient() {
        try {
            System.out.println(Options.getServerIP().getHostName());
            socket = new Socket(Options.getServerIP(),9999);
            handleConnection();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }

    }
    
    private void handleConnection(){
            System.out.println("New client " + socket.getInetAddress().getHostAddress() +" connected.");
            connection = new Connection(socket,this);
            new Thread(connection).start();
    }

    @Override
    public void registered(Connection con, String name){
        listener.register(name);
    }
    
    public void register(String name){
        connection.register(name);
    }

    @Override
    public void raisedPlayerAlreadyPresentException(){
        listener.playerIsAlreadyPresentException();
    }
    
    @Override
    public void shot(Connection con, String name){
        listener.shot(name);
    }
    
    public void shoot(String name){
        connection.shoot(name);
    }
}
