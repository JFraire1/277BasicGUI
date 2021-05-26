/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActualAttempt;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Jose Fraire Jr
 */
public class FilePanel extends JPanel{
    private JScrollPane scPane = new JScrollPane();
    private JTree dirTree = new JTree();
    private DefaultTreeModel treemodel;
    private FileFrame parent;
    private File directory;
    private DefaultMutableTreeNode selectedNode;
    private boolean openClick = true;
    
    public FilePanel(FileFrame f){
        parent = f;
        scPane.setPreferredSize(new Dimension(200,200));
        this.setLayout(new BorderLayout());
        this.add(scPane, BorderLayout.CENTER);
        dirTree.addTreeSelectionListener(new myTreeSelectionListener());
        dirTree.addMouseListener(new FileMouseAdapter());
        scPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scPane.setViewportView(dirTree);
        add(scPane);
    }

    void buildTree(String r){
        File file = new File(r);
        directory = file;
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(file.getName());
        treemodel = new DefaultTreeModel(root);
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

    private class PopUpMenu extends JPopupMenu{
        JMenuItem delete, copy, rename, paste;
        public PopUpMenu(){
            delete = new JMenuItem("Delete");
            copy = new JMenuItem("Copy");
            rename = new JMenuItem("Rename");
            paste = new JMenuItem("Paste");

            add(delete);
            add(copy);
            add(rename);
            add(paste);

        }
    }

    private class FileMouseAdapter extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1){
                openClick = false;
                openPopUp(e);
            }
            else if (e.getClickCount() == 1) {
                openClick = false;
            }
            else if (e.getClickCount() == 2) {
                openClick = true;
            }
        }
        private void openPopUp(MouseEvent e){
            PopupMenu menu = new PopupMenu();
            menu.show(e.getComponent(), e.getX(), e.getY());
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
            selectedNode = (DefaultMutableTreeNode)selectedPath.getLastPathComponent();
            TreeNode[] path = selectedNode.getPath();
            String r = "";
            for (TreeNode insideNode : path){
                String s = insideNode.toString();
                if (s.equals(directory.getName()))
                    r = directory.getAbsolutePath();
                else
                    r += "\\" + s;
            }
            File f = new File(r);
            if (f.isDirectory() & openClick){
                parent.updateFile(r);
            }
            else if (openClick){
                Desktop desktop = Desktop.getDesktop();
                try{
                    desktop.open(f);
                }
                catch (IOException ex){
                    System.out.println(ex.toString());
                }
            }
        }
    }
}

