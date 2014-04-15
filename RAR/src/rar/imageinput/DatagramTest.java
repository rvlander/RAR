/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rar.imageinput;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rvlander
 */
public class DatagramTest {

    public static void main(String[] args) {
        try {
            DatagramChannel channel = DatagramChannel.open();
            channel.socket().bind(new InetSocketAddress(9999));
            
            System.out.println("UDP Listening");
            
            DatagramChannel chan = channel.connect(new InetSocketAddress("192.168.1.85",5001));
            
            ByteBuffer buf = ByteBuffer.allocate(48);
            
            while (true) {
                buf.clear();
                System.out.println("waiting data");
                chan.receive(buf);
               
                System.out.println("data recevied");
                
                System.out.print(buf);
            }
        } catch (SocketException ex) {
            Logger.getLogger(DatagramTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DatagramTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
