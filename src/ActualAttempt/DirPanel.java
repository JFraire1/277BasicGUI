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
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.*;

/**
 *
 * @author Jose Fraire Jr
 */
public class DirPanel extends JPanel {
    private DefaultMutableTreeNode root;
    private JScrollPane scPane;
    private JTree dirTree;
    String drive;
    private DefaultTreeModel treemodel;
    private FileFrame parent;
    private DirPanel dp = this;

    public DirPanel(FileFrame f) {
        parent = f;
        this.setLayout(new BorderLayout());
        scPane = new JScrollPane();
        dirTree = new JTree();
        scPane.setPreferredSize(new Dimension(200, 200));
        scPane.setViewportView(dirTree);
        scPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        dirTree.addMouseListener(new TreeMouseAdapter());
        dirTree.setUI(new BasicTreeUI() {
            @Override
            protected boolean shouldPaintExpandControl(final TreePath path, final int row
                    , final boolean isExpanded, final boolean hasBeenExpanded, final boolean isLeaf) {
                return false;
            }
        });
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


    void buildTree(DefaultMutableTreeNode p, String r) {

        if (p.getChildCount() != 0) {
            for (Enumeration<TreeNode> j = p.children(); j.hasMoreElements(); ) {
                TreeNode k = j.nextElement();
                if (k.getChildCount() != 0) return;
            }
            p.removeAllChildren();
        }
        File file = new File(r);
        DefaultMutableTreeNode root = p;
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (!f.isHidden() && f.isDirectory()) {
                    DefaultMutableTreeNode subnode = new DefaultMutableTreeNode(f.getName());
                    root.add(subnode);
                    File[] files2 = f.listFiles();
                    if (files2 != null) {
                        for (File g : files2) {
                            subnode.add(new DefaultMutableTreeNode(g.getName()));
                        }
                    }
                }
            }
        }
    }


    public void updateStatus(File file, String r) {
        SimpleDateFormat dateformatter = new SimpleDateFormat("MM/dd/yyyy");
        String status = "Size: ";
        status += (file.getTotalSpace() / 1000000000);
        status += " GB | ";
        status += "Total Available Space: " + file.getUsableSpace() / 1000000000 + "GB | ";
        status += "Last Modified: " + dateformatter.format(file.lastModified());
        parent.updateStatus(r, status);
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
                if (s.equals(drive))
                    r = s;
                else if (r.equals(drive))
                    r += s;
                else
                    r += "\\" + s;
            }
            if (e.getButton() == 1){
                File h = new File(r);
                updateStatus(h, r);
                buildTree(selectedNode, r);
            }

            if (e.getButton() == 2 | e.getButton() == 3) {
                MyJPopupMenu menu = new MyJPopupMenu(selectedNode, r, dp, dirTree);
                menu.show(e.getComponent(), e.getX(), e.getY());

            }
        }
    }
}


