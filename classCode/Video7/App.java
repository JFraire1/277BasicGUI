/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Video7;

import java.awt.BorderLayout;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author Jose Fraire Jr
 */
public class App extends JFrame{
    public static void main(String[] args){
        DesktopPane.main(null);
    }
    JPanel panel;
    JDesktopPane dPane;
    JMenuBar menubar, statusBar;
    public App(){
        menubar = new JMenuBar();
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        dPane = new JDesktopPane();
        statusBar = new JMenuBar();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("File Manager");
        setSize(700, 400);
    }
    
    public void go(){
        buildMenu();
        //buildToolBar();
        buildStatusBar();
        
        
        panel.add(dPane, BorderLayout.CENTER);
        FileFrame ff = new FileFrame();
        dPane.add(ff);
        add(panel);
        setVisible(true);
    }
    
    private void buildMenu(){
        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");
        JMenu runMenu = new JMenu("Run");
        
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem about = new JMenuItem("About");
        JMenuItem run = new JMenuItem("Run");
        JMenuItem debug = new JMenuItem("Debug");
        fileMenu.add(exit);
        helpMenu.add(about);
        runMenu.add(run);
        runMenu.add(debug);
        menubar.add(fileMenu);
        menubar.add(runMenu);
        menubar.add(helpMenu);
        panel.add(menubar, BorderLayout.NORTH);
    }
    
    private void buildStatusBar(){
        JLabel size = new JLabel("Size: ");
        statusBar.add(size);
        panel.add(statusBar, BorderLayout.SOUTH);
    }
    
}
