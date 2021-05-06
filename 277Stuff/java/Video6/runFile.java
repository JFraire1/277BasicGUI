/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Video6;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Jose Fraire Jr
 */
public class runFile {
    
    public static void main(String[] args){
        Desktop desktop = Desktop.getDesktop();
        
        try{
            desktop.open(new File("C:\\Users\\JoseFraireJr\\Downloads\\GuitarTabs\\dot_tutorialvideo.MOV"));
        } catch (IOException ex){
            System.out.println(ex.toString());
        }
    }
    
}
