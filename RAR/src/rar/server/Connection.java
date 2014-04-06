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
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rvlander
 */
public class Connection implements Runnable {

    private Socket socket;
    private RaceEngine raceEngine;
    private String name;

    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private ObjectOutputStream objectWriter;

    public Connection(Socket s, RaceEngine re) {
        try {
            raceEngine = re;
            socket = s;
            bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            printWriter = new PrintWriter(s.getOutputStream());
            objectWriter = new ObjectOutputStream(s.getOutputStream());
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
            case 'R':
                register(line.substring(1));
                break;
            case 'G':
                sendWorld();
            default:
                throw new UnknownCommandException();
        }
    }

    private void register(String name) throws RacerAlreadyPresentException {
        raceEngine.addRacer(name);
    }

    private void sendWorld() throws IOException {
        objectWriter.writeObject(raceEngine.getWorld());
    }

}
