/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Video3;

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
    JButton ok, cancel;
    
    public App(){
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        ok = new JButton("About");
        cancel = new JButton("Cancel");
        ok.addActionListener(new aboutActionListener());
        cancel.addActionListener(new cancelActionListener());
    }
    
    public void go(){
        panel.add(ok, BorderLayout.NORTH);
        panel.add(cancel, BorderLayout.SOUTH);
        this.setVisible(true);
        this.setSize(400, 300);
        this.add(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    class aboutActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            NewJDialog dlg = new NewJDialog(null, true);
            dlg.setVisible(true);
        }
    }
    
    class cancelActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("You pressed Cancel");
        }
    }
    
}

