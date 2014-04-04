/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rar.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rvlander
 */
public class Options {

    static String config_file = System.getProperty("user.home") + "/.config/RAR/all.config";
    static TypedProperties pop;

    static {
        Options.pop = new TypedProperties();
        try {
            File f = new File(Options.config_file);
            if (!f.exists()) {
                f.getParentFile().mkdirs();
                f.createNewFile();
            }
            Options.pop.load(new FileInputStream(f));
            if (!Options.validate()) {
                setSamplerType("pi");
                setVideoPort(5001);
                setControlPort(8888);
                setPiIP("192.168.2.1");
            }
        } catch (IOException ex) {
            Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static boolean validate() {
        boolean res = true;
        res &= Options.getSamplerType() != null;
        res &= Options.getVideoPort()!= null;
        res &= Options.getPiIP() != null;
        res &= Options.getControlPort()!= null;
        return res;
    }

    private static void save() {
        try {
            Options.pop.store(new FileOutputStream(Options.config_file), null);
        } catch (IOException ex) {
            Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String getSamplerType() {
        return pop.getProperty("sampler");
    }

    public static void setSamplerType(String type) {
        pop.setProperty("sampler", type);
        Options.save();
    }

    public static InetAddress getPiIP() {
        return pop.getInetAddressProperty("piIP");
    }

    public static void setPiIP(String type) {
        pop.setProperty("piIP", type);
        Options.save();
    }

    public static Integer getVideoPort() {
        return pop.getIntProperty("video_port");
    }

    public static void setVideoPort(int port) {
        pop.setIntProperty("video_port", port);
        Options.save();
    }
    
        public static Integer getControlPort() {
        return pop.getIntProperty("control_port");
    }

    public static void setControlPort(int port) {
        pop.setIntProperty("control_port", port);
        Options.save();
    }

}
