/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rar.renderer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rvlander
 */
public class Loader {

    public static String readFile(String pathname){

        try {
            File file = new File(pathname);
            StringBuilder fileContents = new StringBuilder((int) file.length());
            Scanner scanner = new Scanner(file);
            String lineSeparator = System.getProperty("line.separator");
            
            while (scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine()).append(lineSeparator);
            }
            return fileContents.toString();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
        
        return null;
    }

}
