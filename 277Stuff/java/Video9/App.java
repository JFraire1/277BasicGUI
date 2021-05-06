/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Video9;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Jose Fraire Jr
 */
public class App extends JFrame{
    JPanel panel;
    DefaultTreeModel treemodel;
    JScrollPane scrollpane;
    JTree tree;
    
    public App(){
        panel = new JPanel();
        
        tree = new JTree();
        tree.setPreferredSize(new Dimension(400,400));
        
        
        buildTree();
        panel.add(tree);
        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 420);
        this.setTitle("A Tree Demo");
    }
    
    public void go(){
        this.setVisible(true);
    }
    
    private void buildTree(){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        treemodel = new DefaultTreeModel(root);
        tree.setModel(treemodel);
        DefaultMutableTreeNode node = new DefaultMutableTreeNode("Node");
        root.add(node);
        for(int i=0;i<10;i++){
            DefaultMutableTreeNode subnode = new DefaultMutableTreeNode("SubNode" + (i + 1));
            node.add(subnode);
        }
        node = new DefaultMutableTreeNode("Node2");
        root.add(node);
    }
}
