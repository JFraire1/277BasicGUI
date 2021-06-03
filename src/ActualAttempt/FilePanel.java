/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActualAttempt;


import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Objects;


/**
 *
 * @author Jose Fraire Jr
 */
class FilePanel extends JPanel{
    private JScrollPane scPane = new JScrollPane();
    private JTree dirTree = new JTree();
    private FileFrame parent;
    protected File directory;
    String selectedDir;
    private final FilePanel fp = this;
    private DefaultMutableTreeNode root;
    final Font currentFont = dirTree.getFont();
    final Font bigFont = new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize() + 1);


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
        dirTree.setEditable(true);
        dirTree.setRowHeight(25);
        dirTree.setFont(bigFont);
        scPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scPane.setViewportView(dirTree);
        add(scPane);
    }

    void buildTree(String r){
        File file = new File(r);
        directory = file;
        root = new DefaultMutableTreeNode(file.getName());
        DefaultTreeModel treemodel = new DefaultTreeModel(root);
        treemodel.addTreeModelListener(new RenameListener());
        dirTree.setModel(treemodel);
        File[] files = file.listFiles();
        if (files != null){
            for(File f : files){
                if (!f.isHidden()){
                    DirPanel.addChildren(root, f);
                }
            }
        }
        TreePath path = new TreePath(root.getPath());
        dirTree.expandPath(path);
    }

    FileFrame getParentFrame(){
        return parent;
    }

    void updateTree(){
        buildTree(directory.getAbsolutePath());
    }

    private String getR(DefaultMutableTreeNode node){
        TreeNode[] path = node.getPath();
        String r = "";
        boolean copiedDirectory = false;
        for (TreeNode insideNode : path) {
            String s = insideNode.toString();
            if (s.equals(directory.getName()) && !copiedDirectory){
                r = directory.getAbsolutePath();
                copiedDirectory = true;
            }
            else {
                if (r.endsWith("\\")) {
                    r += s;
                }
                else{
                    r += "\\" + s;
                }
            }
        }
        return r;
    }

    private class TreeMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            TreePath treePath = dirTree.getSelectionPath();
            if (treePath == null) {
                return;
            }
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
            String r = getR(selectedNode);
            if (e.getButton() == 1) {
                selectedDir = r;
                if (e.getClickCount() == 2) {
                    File f = new File(r);
                    if (f.isDirectory()) {
                        parent.updateFile(r);
                        parent.dir.updateSelection(r.split("\\\\"), 0);
                        parent.dir.updateTree();
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

    private class RenameListener implements TreeModelListener{
        @Override
        public void treeNodesChanged(TreeModelEvent e) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            File parentfile = directory.getParentFile();
            String newName = Objects.requireNonNull(dirTree.getSelectionPath()).getLastPathComponent().toString();
            String[] newNameSplit = newName.split("\\.");
            newName = newNameSplit[0];
            String fileType = "123";
            if (newNameSplit.length == 2){
                fileType = newNameSplit[1];
            }
            else if (newNameSplit.length > 2){
                updateTree();
                setCursor(Cursor.getDefaultCursor());
                return;
            }
            MyJPopupMenu.rename(newName, selectedDir, fileType);
            parent.dir.updateSelection(selectedDir.split("\\\\"));
            parent.dir.updateTree();
            if (!directory.exists()){
                buildTree(parentfile.getAbsolutePath());
                parent.DirMenu.updateDirectoryMenu(parentfile.getAbsolutePath());
            }
            else {
                updateTree();
            }
            setCursor(Cursor.getDefaultCursor());
        }
        @Override
        public void treeNodesInserted(TreeModelEvent e) {

        }
        @Override
        public void treeNodesRemoved(TreeModelEvent e) {

        }
        @Override
        public void treeStructureChanged(TreeModelEvent e) {

        }
    }
}


