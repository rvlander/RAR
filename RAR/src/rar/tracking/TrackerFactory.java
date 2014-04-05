/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rar.tracking;

import rar.imageinput.*;
import rar.utils.Options;

/**
 *
 * @author rvlander
 */
public class TrackerFactory {

    public static Tracker createTracker() {
        
        /*String samplerType = Options.getSamplerType();
        
        if (samplerType.equals("pi")) {
            return new PiImageGetter();
        }
        if (samplerType.equals("dpi")){
            return new StdinPiImageGetter();
        }*/

        return new ColorTracker();
    }
}
