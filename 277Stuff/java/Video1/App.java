/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Video1;

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
        ok = new JButton("Okay");
        cancel = new JButton("Cancel");
        ok.addActionListener(new okCancelActionListener());
        cancel.addActionListener(new okCancelActionListener());
    }
    
    public void go(){
        panel.add(ok, BorderLayout.NORTH);
        panel.add(cancel, BorderLayout.SOUTH);
        this.setVisible(true);
        this.setSize(400, 300);
        this.add(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    class okCancelActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Okay"))
                System.out.println("You pressed Okay");
            else
                System.out.println("You pressed Cancel");
        }
        
    }
}

