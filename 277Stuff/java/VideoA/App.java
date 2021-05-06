/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VideoA;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
/**
 *
 * @author Jose Fraire Jr
 */
public class App extends JFrame{
    File file;
    JPanel panel;
    DefaultTreeModel treemodel;
    JScrollPane scrollpane;
    JTree tree;
    
    public App(){
        panel = new JPanel();
        scrollpane = new JScrollPane();
        tree = new JTree();
        tree.setPreferredSize(new Dimension(400,400));
        
        
        buildTree();
        tree.addTreeSelectionListener(new demoTreeSelectionListener());
        scrollpane.setViewportView(tree);
        panel.add(scrollpane);
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
    
    class demoTreeSelectionListener implements TreeSelectionListener{

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            System.out.println(tree.getMinSelectionRow());
            System.out.println(tree.getSelectionPath());
            
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            System.out.println(node);
        }
        
    }
}
