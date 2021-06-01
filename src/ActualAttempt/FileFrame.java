/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActualAttempt;


import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Jose Fraire Jr
 */
class FileFrame extends JInternalFrame {
    JSplitPane splitpane;
    DirectoryMenu DirMenu = new DirectoryMenu(this);
    FilePanel file = new FilePanel(this);
    DirPanel dir = new DirPanel(this);
    App parent;
    
    public FileFrame(App app){
        parent = app;
        JInternalFrame[] jf = parent.dPane.getAllFrames();
        setLocation(jf.length * 25, jf.length * 25);
        for (JInternalFrame frame : jf){
            frame.moveToBack();
        }
        setLayer(jf.length);
        getContentPane().setLayout(new BorderLayout());
        splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dir, file);
        getContentPane().add(DirMenu, BorderLayout.NORTH);
        DirMenu.setLocation(0,0);
        DirMenu.setAlignmentX(LEFT_ALIGNMENT);
        DirMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        DirMenu.updateDirectoryMenu(dir.drive);
        getContentPane().add(splitpane, BorderLayout.CENTER);
        splitpane.setAlignmentX(CENTER_ALIGNMENT);
        splitpane.setDividerLocation(200);
        splitpane.setDividerSize(0);
        this.setClosable(true);
        this.setTitle("New File Window");
        parent.drive = dir.drive;
        
        this.setMaximizable(true);
        this.setIconifiable(true);
        this.setSize(950, 410);
        this.setVisible(true);
        this.toFront();
    }
    
    void updateFile(String r){
        DirMenu.updateDirectoryMenu(r);
        file.buildTree(r);
    }
    
    void updateStatus(String r, String status){
        parent.updateStatusBar(status);
        DirMenu.updateDirectoryMenu(r);
        file.buildTree(r);
    }
}
