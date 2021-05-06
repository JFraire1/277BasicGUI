/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Video2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Jose Fraire Jr
 */
public class App extends JFrame{
    JPanel panel;
    JMenuBar menubar;
    public App(){
        panel = new JPanel();
        menubar = new JMenuBar();
        panel.setLayout(new BorderLayout());
    }
    
    public void go(){
        buildmenu();
        this.add(panel);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 300);
    }
    
    public void buildmenu(){
       JMenu fileMenu, helpMenu, runMenu;
       fileMenu = new JMenu("File");
       helpMenu = new JMenu("Help");
       runMenu = new JMenu("Run");
       
       JMenuItem exit = new JMenuItem("Exit");
       JMenuItem about = new JMenuItem("About");
       JMenuItem run = new JMenuItem("Run");
       JMenuItem debug = new JMenuItem("Debug");
       
       
       run.addActionListener(new RunActionListener());
       debug.addActionListener(new RunActionListener());
       exit.addActionListener(new ExitActionListener());
       about.addActionListener(new AboutActionListener());
       
       runMenu.add(run);
       runMenu.add(debug);
       fileMenu.add(exit);
       helpMenu.add(about);
       menubar.add(fileMenu);
       menubar.add(runMenu);
       menubar.add(helpMenu);
       panel.add(menubar, BorderLayout.NORTH);
    }

    private static class ExitActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
        
    }

    private static class AboutActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AboutDlg dlg = new AboutDlg(null, true);
            dlg.setVisible(true);
        }
            
    }
    
    private class RunActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Run"))
                System.out.println("Running the program");
            else
                System.out.println("Debugging the program");
        }
    
    }
}

