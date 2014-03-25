/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pistream;

import com.xuggle.mediatool.IMediaGenerator;
import com.xuggle.mediatool.IMediaListener;
import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IAddStreamEvent;
import com.xuggle.mediatool.event.IAudioSamplesEvent;
import com.xuggle.mediatool.event.ICloseCoderEvent;
import com.xuggle.mediatool.event.ICloseEvent;
import com.xuggle.mediatool.event.IFlushEvent;
import com.xuggle.mediatool.event.IOpenCoderEvent;
import com.xuggle.mediatool.event.IOpenEvent;
import com.xuggle.mediatool.event.IReadPacketEvent;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.mediatool.event.IWriteHeaderEvent;
import com.xuggle.mediatool.event.IWritePacketEvent;
import com.xuggle.mediatool.event.IWriteTrailerEvent;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IContainerFormat;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author rvlander
 */
public class PiStreamSocket implements IMediaListener{
    
    ImagePanel ip;
    int i=0;
    
    public PiStreamSocket(ImagePanel im){
        ip = im;
    }

    @Override
    public void onVideoPicture(IVideoPictureEvent ivpe) {
        //System.out.println(ivpe.getPicture().getHeight() +" "+ivpe.getImage());
        //i++;
        ip.setImage(ivpe.getImage());
        /*try {
            ImageIO.write(ivpe.getImage(), "png", new File("toto/im"+i+".png"));
        } catch (IOException ex) {
            Logger.getLogger(PiStream.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    @Override
    public void onAudioSamples(IAudioSamplesEvent iase) {
    }

    @Override
    public void onOpen(IOpenEvent ioe) {
    }

    @Override
    public void onClose(ICloseEvent ice) {
    }

    @Override
    public void onAddStream(IAddStreamEvent iase) {
    }

    @Override
    public void onOpenCoder(IOpenCoderEvent ioce) {
    }

    @Override
    public void onCloseCoder(ICloseCoderEvent icce) {
    }

    @Override
    public void onReadPacket(IReadPacketEvent irpe) {
    }

    @Override
    public void onWritePacket(IWritePacketEvent iwpe) {
    }

    @Override
    public void onWriteHeader(IWriteHeaderEvent iwhe) {
    }

    @Override
    public void onFlush(IFlushEvent ife) {
    }

    @Override
    public void onWriteTrailer(IWriteTrailerEvent iwte) {
    }
    
        /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            JFrame jframe = new JFrame();
            jframe.setSize(1920, 1080);
            jframe.setVisible(true);
            jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ImagePanel pane = new ImagePanel();
            jframe.getContentPane().add(pane);
            pane.setSize(jframe.getSize());
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            
            serverSocketChannel.socket().bind(new InetSocketAddress(5001));
            
            SocketChannel socketChannel
                    = serverSocketChannel.accept();
            
            
            IContainer container = IContainer.make();
            System.out.println(container.open((ReadableByteChannel)socketChannel, null));
            
            IMediaReader reader = ToolFactory.makeReader(container);
            reader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
            reader.addListener(new PiStreamSocket(pane));
            while (reader.readPacket() == null)
                ;   } catch (IOException ex) {
            Logger.getLogger(PiStreamSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
