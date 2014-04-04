/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rar.imageinput;

import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.IContainer;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rvlander
 */
public class StdinPiImageGetter extends PiImageGetter{
    public StdinPiImageGetter() {
        try {
            DatagramChannel serverSocketChannel = DatagramChannel.open();

            serverSocketChannel.socket().bind(new InetSocketAddress(5001));

            //SocketChannel socketChannel
            //        = serverSocketChannel.accept();

            IContainer container = IContainer.make();
            System.out.println(container.open(System.in, null));

            reader = ToolFactory.makeReader(container);
            reader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
            reader.addListener(this);
            new Thread(new Runnable() {

                @Override
                public void run() {
                    while (reader.readPacket() == null);
                }

            }).start();

        } catch (IOException ex) {
            Logger.getLogger(PiImageGetter.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
    }
}
