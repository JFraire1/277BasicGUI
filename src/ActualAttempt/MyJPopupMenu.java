package ActualAttempt;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class MyJPopupMenu extends JPopupMenu {
    private final JTree tree;
    private final JPanel parentPanel;
    private final DefaultMutableTreeNode selectedNode;
    private final String dir;

    public MyJPopupMenu(DefaultMutableTreeNode selectedNode, String dir, JPanel parentPanel, JTree tree){
        this.selectedNode = selectedNode;
        this.dir = dir;
        this.tree = tree;
        this.parentPanel = parentPanel;
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem delete = new JMenuItem("Delete");
        JMenuItem paste = new JMenuItem("Paste");
        JMenuItem rename = new JMenuItem("Rename");
        copy.addActionListener(new MyMenuActionListener());
        delete.addActionListener(new MyMenuActionListener());
        paste.addActionListener(new MyMenuActionListener());
        rename.addActionListener(new MyMenuActionListener());
        add(copy);
        add(paste);
        add(rename);
        add(delete);
    }


    private class MyMenuActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            switch(e.getActionCommand())
            {
                case "Copy":
                    System.out.println("Copy");
                    System.out.println(dir);
                case "Delete":
                    ;
                case "Paste":
                    ;
                case "Rename":
                    ;
            }
        }
    }



}
