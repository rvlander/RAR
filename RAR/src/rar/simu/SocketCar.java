/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rar.simu;

import rar.simu.ConcreteCar;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rar.utils.Options;

/**
 *
 * @author rvlander
 */
public class SocketCar implements ConcreteCar{
    
    PrintWriter pw;
    
    public SocketCar(){
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(Options.getPiIP(),Options.getControlPort()));
            pw = new PrintWriter(socket.getOutputStream());
        } catch (SocketException ex) {
            Logger.getLogger(SocketCar.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        } catch (UnknownHostException ex) {
            Logger.getLogger(SocketCar.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(SocketCar.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
        
    }

    @Override
    public void turnLeft(double d) {
        pw.println("L"+d);
        pw.flush();
    }

    @Override
    public void turnRight(double d) {
        pw.println("R"+d);
        pw.flush();
    }

    @Override
    public void moveForward(double d) {
        pw.println("F"+d);
        pw.flush();
    }

    @Override
    public void moveBackward(double d) {
        pw.println("B"+d);
        pw.flush();
    }
    
    
    public static void main(String[] args){
        SocketCar sc = new SocketCar();
        sc.moveBackward(125);
        sc.moveForward(45);
        sc.turnLeft(-23);
        sc.turnRight(1.256);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SocketCar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
