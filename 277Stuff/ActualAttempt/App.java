/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActualAttempt;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    JLabel size;
    JPanel panel;
    static JDesktopPane dPane;
    JMenuBar menubar, statusBar;
    App app = this;
    
    void newFF(){
        FileFrame FF = new FileFrame(this);
        dPane.add(FF);
    }
    
    
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
        buildStatusBar();
        
        
        panel.add(dPane, BorderLayout.CENTER);
        FileFrame ff = new FileFrame(this);
        dPane.add(ff);
        add(panel);
        setVisible(true);
    }
    
    private void buildMenu(){
        JMenu helpMenu = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        JMenu addNew = new JMenu("+");
        JMenuItem addNewWindow = new JMenuItem("Window");
        
        addNewWindow.addActionListener(new addWindowActionListener());
        about.addActionListener(new AboutActionListener());
        
        addNew.add(addNewWindow);
        helpMenu.add(about);
        menubar.add(addNew);
        menubar.add(helpMenu);
        panel.add(menubar, BorderLayout.NORTH);
    }
    
    void updateStatusBar(String r){
        size.setText(r);
    }
    
    private void buildStatusBar(){
        size = new JLabel("");
        statusBar.add(size);
        panel.add(statusBar, BorderLayout.SOUTH);
    }
    
    private static class AboutActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AboutDlg dlg = new AboutDlg(null, true);
            dlg.setVisible(true);
        }
            
    }

    private class addWindowActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            app.newFF();
        }
        
    }
    
}
