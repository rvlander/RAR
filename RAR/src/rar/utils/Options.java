/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rar.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rvlander
 */
public class Options {

    static String config_file = System.getProperty("user.home") + "/.config/RAR/all.config";
    static Properties pop;

    static{
        Options.pop = new Properties();
        try {
            File f = new File(Options.config_file);
            if (!f.exists()) {
                f.getParentFile().mkdirs();
                f.createNewFile();
            }
            Options.pop.load(new FileInputStream(f));
            if(!Options.validate()){
                setSamplerType("pi");
            }
        } catch (IOException ex) {
            Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static boolean validate(){
        boolean res =true;
        res &= Options.getSamplerType()!=null;       
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

}