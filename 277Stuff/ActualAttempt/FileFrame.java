/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActualAttempt;

import java.awt.Rectangle;
import javax.swing.JInternalFrame;
import javax.swing.JSplitPane;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Jose Fraire Jr
 */
public class FileFrame extends JInternalFrame{
    JSplitPane splitpane;
    FilePanel file = new FilePanel(this);
    DirPanel dir = new DirPanel(this);
    Rectangle r = new Rectangle(450, 400);
    App parent;
    
    public FileFrame(App app){
        parent = app;
        splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dir, file);
        getContentPane().add(splitpane);
        this.setClosable(true);
        this.setTitle("C:");
        
        this.setMaximizable(true);
        this.setIconifiable(true);
        this.setSize(r.height, r.width);
        this.setVisible(true);
        this.toFront();
    }
    
    void updateFile(String r){
        this.setTitle(r);
        file.buildTree(r);
    }
    
    void updateStatus(String r, String status){
        this.setTitle(r);
        parent.updateStatusBar(status);
        file.buildTree(r);
    }
}
