/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActualAttempt;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
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
    String newDir;
    
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
    
    public void find(){
        File f = new File(newDir);
        FileFrame ff = new FileFrame(app);
        ff.updateFile(newDir);
        dPane.add(ff);
        if (f.isFile()){
            Desktop desktop = Desktop.getDesktop();
            try{
                desktop.open(f);
            }
            catch (IOException ex){
                System.out.println(ex.toString());
            }
        }
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
        JButton addNew = new JButton("+");
        JButton find = new JButton("Find");
        
        addNew.addActionListener(new addWindowActionListener());
        about.addActionListener(new AboutActionListener());
        find.addActionListener(new findActionListener());
        
        helpMenu.add(about);
        menubar.add(addNew);
        menubar.add(find);
        menubar.add(helpMenu);
        panel.add(menubar, BorderLayout.NORTH);
    }
    
    void updateStatusBar(String r){
        size.setText(r);
    }
    
    private void buildStatusBar(){
        JButton setCascading = new JButton("Cascade");
        size = new JLabel("");
        
        setCascading.addActionListener(new cascadingActionListener());
        
        statusBar.add(setCascading);
        statusBar.add(size);
        panel.add(statusBar, BorderLayout.SOUTH);
    }

    private class cascadingActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JInternalFrame[] j = dPane.getAllFrames();
            int[] root = {(j.length - 1)*25,(j.length - 1)*25};
            for (JInternalFrame jf : j){
                jf.setLocation(root[0], root[1]);
                root[0] -= 25;
                root[1] -= 25;
            }
        }

    }
    
    private class AboutActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AboutDlg dlg = new AboutDlg(null, true);
            dlg.setVisible(true);
        }
            
    }

    private class addWindowActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (dPane.getAllFrames().length >= 30){
                tooManyWindowDlg dlg = new tooManyWindowDlg(null, true);
                dlg.setVisible(true);
                return;
            }
            app.newFF();
        }
        
    }
    private class findActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            findDlg dlg = new findDlg(null, true, app);
            dlg.setVisible(true);
        }
    
    }
    
}
