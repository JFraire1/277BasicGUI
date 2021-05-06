/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Video8;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

/**
 *
 * @author Jose Fraire Jr
 */
public class FilePanel extends JPanel{
    private JScrollPane scPane = new JScrollPane();
    private JTree dirTree = new JTree();
    
    public FilePanel(){
        scPane.setViewportView(this);
        add(dirTree);
        
    }
}
