/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Video4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Jose Fraire Jr
 */
public class App extends JFrame{
    JPanel panel;
    JButton okayButton;
    
    public App(){
        panel = new JPanel();
        okayButton = new JButton("Okay");
        this.setTitle("My GUI Program");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 300);
    }
    
    public void go(){
        okayButton.addActionListener(new okayActionListener());
        panel.add(okayButton);
        this.add(panel);
        this.setVisible(true);
    }
    
    class okayActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Okay");
            
            DataBack dbdlg = new DataBack(null, true);
            dbdlg.setFromField("This is input to dialog");
            dbdlg.setVisible(true);
            String toField = dbdlg.getToField();
            if (!toField.equals(""))
                System.out.println(toField);
        }
    }
}
