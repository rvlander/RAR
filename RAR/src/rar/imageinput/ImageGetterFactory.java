/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rar.imageinput;

import rar.utils.Options;

/**
 *
 * @author rvlander
 */
public class ImageGetterFactory {

    public static ImageGetter createImageGetter() {
        
        String samplerType = Options.getSamplerType();
        
        if (samplerType.equals("pi")) {
            return new PiImageGetter();
        }
        if (samplerType.equals("dpi")){
            return new StdinPiImageGetter();
        }

        return new StillImageGetter();
    }
}
