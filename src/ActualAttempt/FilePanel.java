/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActualAttempt;

import com.sun.source.tree.Tree;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.*;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.*;

/**
 *
 * @author Jose Fraire Jr
 */
public class FilePanel extends JPanel{
    private JScrollPane scPane = new JScrollPane();
    private JTree dirTree = new JTree();
    private FileFrame parent;
    private File directory;
    private final FilePanel fp = this;
    private DefaultMutableTreeNode root;
    
    public FilePanel(FileFrame f){
        parent = f;
        scPane.setPreferredSize(new Dimension(200,200));
        setLayout(new BorderLayout());
        add(scPane, BorderLayout.CENTER);
        dirTree.setUI(new BasicTreeUI() {
                          @Override
                          protected boolean shouldPaintExpandControl(final TreePath path, final int row
                                  , final boolean isExpanded, final boolean hasBeenExpanded, final boolean isLeaf) {
                              return false;
                          }
                      });
        dirTree.addTreeWillExpandListener(new MyTreeWillExpandListener());
        dirTree.addMouseListener(new TreeMouseAdapter());
        scPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scPane.setViewportView(dirTree);
        add(scPane);
    }

    void buildTree(String r){
        File file = new File(r);
        directory = file;
        root = new DefaultMutableTreeNode(file.getName());
        DefaultTreeModel treemodel = new DefaultTreeModel(root);
        dirTree.setModel(treemodel);
        File[] files = file.listFiles();
        if (files != null){
            for(File f : files){
                if (!f.isHidden()){
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
        TreePath path = new TreePath(root.getPath());
        dirTree.expandPath(path);
    }

    private class TreeMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            TreePath treePath = dirTree.getSelectionPath();
            if (treePath == null) {
                return;
            }
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
            TreeNode[] path = selectedNode.getPath();
            String r = "";
            for (TreeNode insideNode : path) {
                String s = insideNode.toString();
                if (s.equals(directory.getName()))
                    r = directory.getAbsolutePath();
                else
                    r += "\\" + s;
            }
            if (e.getButton() == 1) {
                if (e.getClickCount() == 2) {
                    File f = new File(r);
                    if (f.isDirectory()) {
                        parent.updateFile(r);
                    } else {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            desktop.open(f);
                        } catch (IOException ex) {
                            System.out.println(ex.toString());
                        }
                    }
                }
            }
            if (e.getButton() == 2 | e.getButton() == 3) {
                MyJPopupMenu menu = new MyJPopupMenu(selectedNode, r, fp, dirTree);
                menu.show(e.getComponent(), e.getX(), e.getY());

            }
        }
    }

    private class MyTreeWillExpandListener implements TreeWillExpandListener {

        @Override
        public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
            if (event.getPath().getLastPathComponent() == root){return;}
            throw new ExpandVetoException(event,null);
        }

        @Override
        public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
        }
    }
}


