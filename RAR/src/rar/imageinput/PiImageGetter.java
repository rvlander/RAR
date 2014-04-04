/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rar.imageinput;

import com.xuggle.mediatool.IMediaListener;
import com.xuggle.mediatool.IMediaReader;
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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import rar.utils.Options;

/**
 *
 * @author rvlander
 */
class PiImageGetter implements ImageGetter, IMediaListener {

    IMediaReader reader;
    BufferedImage image;

    public PiImageGetter() {
        try {
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(Options.getPiIP(),Options.getVideoPort()));

            IContainer container = IContainer.make();
            System.out.println(container.open((ReadableByteChannel) socketChannel, null));

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

    public synchronized void onVideoPicture(IVideoPictureEvent ivpe) {
        image = ivpe.getImage();
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

    @Override
    public synchronized BufferedImage getImage() {
        return image;
    }

}
