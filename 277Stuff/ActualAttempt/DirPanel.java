/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActualAttempt;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
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
public class DirPanel extends JPanel{
    private DefaultMutableTreeNode root;
    private JScrollPane scPane;
    private JTree dirTree;
    private File file;
    String drive;
    private DefaultTreeModel treemodel;
    private FileFrame parent;
    
    public DirPanel(FileFrame f){
        parent = f;
        this.setLayout(new BorderLayout());
        scPane = new JScrollPane();
        dirTree = new JTree();
        scPane.setPreferredSize(new Dimension(200,200));
        scPane.setViewportView(dirTree);
        scPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        dirTree.addTreeSelectionListener(new myTreeSelectionListener());
        File[] files = File.listRoots();
        drive = files[0].toString();
        System.out.println(drive);
        root = new DefaultMutableTreeNode(drive);
        treemodel = new DefaultTreeModel(root);
        buildTree(root, drive);
        parent.updateFile(drive);
        dirTree.setModel(treemodel);
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
            parent.updateFile(r, status);
            if (h.isDirectory()){
                buildTree(selectedNode, r);
                dirTree.expandPath(new TreePath(path));
            }
            else{
                Desktop desktop = Desktop.getDesktop();
                try{
                    desktop.open(h);
                }
                catch (IOException ex){
                    System.out.println(ex.toString());
                }
            }
        }
    }
}

