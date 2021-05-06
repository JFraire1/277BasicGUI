/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Video5;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author Jose Fraire Jr
 */
public class DIRread {
    
    public static void main(String[] args){
        File file = new File("C:\\");
        File[] files;
        files = file.listFiles();
        
        SimpleDateFormat dateformatter = new SimpleDateFormat("MM/dd/yyyy");
        DecimalFormat decformatter = new DecimalFormat("#,###");
        File[] files2;
        for(int i=0;i<files.length;i++){
            if(files[i].isDirectory()){
                System.out.println("Directory: " + files[i].getAbsolutePath() 
                        + " | Date: " + dateformatter.format(files[i].lastModified())
                            + " | Size: " + decformatter.format(files[i].length()));
                
                files2 = new File(files[i].getAbsolutePath()).listFiles();
                if(files2 != null){
                    for(File f : files2){
                        System.out.println("Directory: " + f.getAbsolutePath() 
                             + " | Date: " + dateformatter.format(files[i].lastModified())
                                    + " | Size: " + decformatter.format(files[i].length()));
                    }
                }
            }
            else
                System.out.println("File: " + files[i].getAbsolutePath() 
                        + " | Date: " + dateformatter.format(files[i].lastModified())
                            + " | Size: " + decformatter.format(files[i].length()));
        }
    }
}
