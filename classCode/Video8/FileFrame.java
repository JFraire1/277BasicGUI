/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Video8;

import javax.swing.JInternalFrame;
import javax.swing.JSplitPane;

/**
 *
 * @author Jose Fraire Jr
 */
public class FileFrame extends JInternalFrame{
    JSplitPane splitpane;
    public FileFrame(){
        splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new DirPanel(), new FilePanel());
        getContentPane().add(splitpane);
        this.setClosable(true);
        this.setTitle("C:");
        this.setMaximizable(true);
        this.setIconifiable(true);
        this.setSize(200,200);
        this.setVisible(true);
    }
}
