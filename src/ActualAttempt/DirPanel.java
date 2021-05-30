/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActualAttempt;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Jose Fraire Jr
 */
public class DirPanel extends JPanel{
    private DefaultMutableTreeNode root;
    private JScrollPane scPane;
    private JTree dirTree;
    String drive;
    private DefaultTreeModel treemodel;
    private FileFrame parent;
    private DirPanel dp = this;
    
    public DirPanel(FileFrame f){
        parent = f;
        this.setLayout(new BorderLayout());
        scPane = new JScrollPane();
        dirTree = new JTree();
        scPane.setPreferredSize(new Dimension(200,200));
        scPane.setViewportView(dirTree);
        scPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        dirTree.addTreeSelectionListener(new myTreeSelectionListener());
        dirTree.addMouseListener(new TreeMouseAdapter());
        File[] files = File.listRoots();
        drive = files[0].toString();
        root = new DefaultMutableTreeNode(drive);
        treemodel = new DefaultTreeModel(root);
        buildTree(root, drive);
        parent.updateFile(drive);
        dirTree.setModel(treemodel);
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) dirTree.getCellRenderer();
        renderer.setLeafIcon(renderer.getClosedIcon());
        add(scPane, BorderLayout.CENTER);
        
    }
    
    
    
    void buildTree(DefaultMutableTreeNode p, String r){
        
        if (p.getChildCount() != 0){
            for (Enumeration<TreeNode> j = p.children(); j.hasMoreElements();){
                TreeNode k = j.nextElement();
                if (k.getChildCount() != 0) return;
            }
            p.removeAllChildren();
        }
        File file = new File(r);
        DefaultMutableTreeNode root = p;
        File[] files = file.listFiles();
        if (files != null){
            for(File f : files){
                if (!f.isHidden() && f.isDirectory()){
                    DefaultMutableTreeNode subnode = new DefaultMutableTreeNode(f.getName());
                    root.add(subnode);
                    File[] files2 = f.listFiles();
                    if (files2 != null){
                        for(File g: files2){
                            subnode.add(new DefaultMutableTreeNode(g.getName()));
                        }
                    }
                }
            }
        }
    }  
    

    private class myTreeSelectionListener implements TreeSelectionListener{

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            TreePath selectedPath;
            if (dirTree.getSelectionPath() != null)
                selectedPath = dirTree.getSelectionPath();
            else
                return;
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)selectedPath.getLastPathComponent();
            TreeNode[] path = selectedNode.getPath();
            String r = "";
            for (TreeNode insideNode : path){
                String s = insideNode.toString();
                if (s.equals(drive))
                    r = s;
                else if (r.equals(drive))
                    r += s;
                else
                    r += "\\" + s;
            }
            SimpleDateFormat dateformatter = new SimpleDateFormat("MM/dd/yyyy");
            String status = "Size: ";
            File h = new File(r);
            status += (h.getTotalSpace()/1000000000);
            status += " GB | ";
            status += "Total Available Space: " + h.getUsableSpace()/1000000000 + "GB | ";
            status += "Last Modified: " + dateformatter.format(h.lastModified());
            parent.updateStatus(r, status);
            if (h.isDirectory()){
                buildTree(selectedNode, r);
            }
        }
    }

    private class TreeMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            TreePath treePath = dirTree.getSelectionPath();
            if (treePath == null) {
                return;
            }
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)treePath.getLastPathComponent();
            TreeNode[] path = selectedNode.getPath();
            String r = "";
            for (TreeNode insideNode : path){
                String s = insideNode.toString();
                if (s.equals(drive))
                    r = s;
                else if (r.equals(drive))
                    r += s;
                else
                    r += "\\" + s;
            }
            if (e.getButton() == 2 | e.getButton() == 3) {
                MyJPopupMenu menu = new MyJPopupMenu(selectedNode, r, dp, dirTree);
                menu.show(e.getComponent(), e.getX(), e.getY());

            }
        }
    }
}


