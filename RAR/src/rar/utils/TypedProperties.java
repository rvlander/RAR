/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rar.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 *
 * @author rvlander
 */
public class TypedProperties extends Properties {

    public Integer getIntProperty(String key) {
        String value = this.getProperty(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException ex) {
            }
        }
        return null;
    }

    public void setIntProperty(String key, int value) {
        this.setProperty(key, value + "");
    }
    
    public InetAddress getInetAddressProperty(String key){
        String value = this.getProperty(key);
        if (value != null) {
            try {
                return InetAddress.getByName(value);
            } catch (UnknownHostException ex) {
            }
        }
        return null;
    }

}
