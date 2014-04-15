/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rar.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rvlander
 */
public class Connection implements Runnable {
    
    private final char registerCommand ='R';
    private final char exceptionCommand ='E';
    private final char shootCommand ='S';
    
    
    private final String playerAlreadyPresentExceptionCode ="PAP";

    private ConnectionListener listener;

    private BufferedReader bufferedReader;
    private PrintWriter printWriter;


    public Connection(Socket s, ConnectionListener list) {
        try {
            listener=list;
            bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            printWriter = new PrintWriter(s.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String line = bufferedReader.readLine();
                handleLine(line);
            } catch (IOException | UnknownCommandException | RacerAlreadyPresentException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(0);
            }
        }
    }

    private void handleLine(String line) throws UnknownCommandException, RacerAlreadyPresentException, IOException {
        char c = line.charAt(0);
        switch(c){
            case registerCommand:
                listener.registered(this,line.substring(1));
                break;
            case exceptionCommand:
                handleException(this,line.substring(1));
                break;
            case shootCommand:
                listener.shot(this,line.substring(1));
                break;
            default:
                throw new UnknownCommandException();
        }
    }

    public void register(String name) {
        sendMessage(registerCommand+name);
    }

    public void raisePlayerAlreadyPresentException() {
        sendMessage(exceptionCommand+playerAlreadyPresentExceptionCode);
    }
    
    private void handleException(Connection con, String exceptionCode) {
        if(exceptionCode.equals(playerAlreadyPresentExceptionCode)){
            listener.raisedPlayerAlreadyPresentException();
        }
    }
    
    private void sendMessage(String message){
        printWriter.println(message);
        printWriter.flush();
    }

    void shoot(String name) {
        sendMessage(shootCommand+name);
    }

}
