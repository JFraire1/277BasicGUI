/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Video7;

import javax.swing.JInternalFrame;

/**
 *
 * @author Jose Fraire Jr
 */
public class FileFrame extends JInternalFrame{
    public FileFrame(){
        this.setClosable(true);
        this.setTitle("C:");
        this.setMaximizable(true);
        this.setIconifiable(true);
        this.setSize(200,200);
        this.setVisible(true);
    }
}
