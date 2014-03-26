/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rar.imageinput;

/**
 *
 * @author rvlander
 */
public class ImageGetterFactory {
    public static ImageGetter createImageGetter(){
        return new PiImageGetter();
    }
}
