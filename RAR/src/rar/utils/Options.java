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

    private static String sampler = "sampler";
    private static String piIP = "piIP";
    private static String videoPort = "video_port";
    private static String controlPort = "control_port";
    private static String imageHeight = "image_height";
    private static String imageWidth = "image_width";
    private static String hMin = "h_min";
    private static String hMax = "h_max";
    private static String sMin = "s_min";
    private static String sMax = "s_max";
    private static String vMin = "v_min";
    private static String vMax = "v_max";

    private static String config_file = System.getProperty("user.home") + "/.config/RAR/all.config";
    private static TypedProperties pop;

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
                setImageWidth(1280);
                setImageHeight(720);
                setHMin(0);
                setHMax(255);
                setSMin(0);
                setSMax(255);
                setVMin(0);
                setVMax(255);
            }
        } catch (IOException ex) {
            Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static boolean validate() {
        boolean res = true;
        res &= Options.getSamplerType() != null;
        res &= Options.getVideoPort() != null;
        res &= Options.getPiIP() != null;
        res &= Options.getControlPort() != null;
        res &= Options.getImageWidth() != null;
        res &= Options.getImageHeight() != null;
        res &= Options.getHMin() != null;
        res &= Options.getHMax() != null;
        res &= Options.getSMin() != null;
        res &= Options.getSMax() != null;
        res &= Options.getVMin() != null;
        res &= Options.getVMax() != null;
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
        return pop.getProperty(sampler);
    }

    public static void setSamplerType(String type) {
        pop.setProperty(sampler, type);
        Options.save();
    }

    public static InetAddress getPiIP() {
        return pop.getInetAddressProperty(piIP);
    }

    public static void setPiIP(String type) {
        pop.setProperty(piIP, type);
        Options.save();
    }

    public static Integer getVideoPort() {
        return pop.getIntProperty(videoPort);
    }

    public static void setVideoPort(int port) {
        pop.setIntProperty(videoPort, port);
        Options.save();
    }

    public static Integer getControlPort() {
        return pop.getIntProperty(controlPort);
    }

    public static void setControlPort(int port) {
        pop.setIntProperty(controlPort, port);
        Options.save();
    }

    public static Integer getImageWidth() {
        return pop.getIntProperty(imageWidth);
    }

    public static void setImageWidth(int width) {
        pop.setIntProperty(imageWidth, width);
        Options.save();
    }

    public static Integer getImageHeight() {
        return pop.getIntProperty(imageHeight);
    }

    public static void setImageHeight(int height) {
        pop.setIntProperty(imageHeight, height);
        Options.save();
    }

    public static Integer getHMin() {
        return pop.getIntProperty(hMin);
    }

    public static void setHMin(int hmin) {
        pop.setIntProperty(hMin, hmin);
        Options.save();
    }

    public static Integer getHMax() {
        return pop.getIntProperty(hMax);
    }

    public static void setHMax(int hmax) {
        pop.setIntProperty(hMax, hmax);
        Options.save();
    }

    public static Integer getSMin() {
        return pop.getIntProperty(sMin);
    }

    public static void setSMin(int smin) {
        pop.setIntProperty(sMin, smin);
        Options.save();
    }

    public static Integer getSMax() {
        return pop.getIntProperty(sMax);
    }

    public static void setSMax(int smax) {
        pop.setIntProperty(sMax, smax);
        Options.save();
    }

    public static Integer getVMin() {
        return pop.getIntProperty(vMin);
    }

    public static void setVMin(int vmin) {
        pop.setIntProperty(vMin, vmin);
        Options.save();
    }

    public static Integer getVMax() {
        return pop.getIntProperty(vMax);
    }

    public static void setVMax(int vmax) {
        pop.setIntProperty(vMax, vmax);
        Options.save();
    }
}
