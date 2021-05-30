package ActualAttempt;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class MyJPopupMenu extends JPopupMenu {
    private final JTree tree;
    private final JPanel parentPanel;
    private final DefaultMutableTreeNode selectedNode;
    private static String sourceDir;
    private static boolean hasSourceDir = false;
    private String dir;

    public MyJPopupMenu(DefaultMutableTreeNode selectedNode, String dir, JPanel parentPanel, JTree tree){
        this.selectedNode = selectedNode;
        this.dir = dir;
        this.tree = tree;
        this.parentPanel = parentPanel;
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem delete = new JMenuItem("Delete");
        JMenuItem paste = new JMenuItem("Paste");
        JMenuItem rename = new JMenuItem("Rename");
        copy.addActionListener(new CopyActionListener());
        //delete.addActionListener(new ActionListener());
        paste.addActionListener(new PasteActionListener());
        //rename.addActionListener(new MyMenuActionListener());
        add(copy);
        add(paste);
        add(rename);
        add(delete);
    }


    private class CopyActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            sourceDir = dir;
            hasSourceDir = true;
        }
    }

    private class PasteActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!hasSourceDir){return;}
            File file = new File(dir);
            File sourceFile = new File(sourceDir);
            if (!sourceFile.exists()){
                hasSourceDir = false;
                return;
            }
            if (!file.isDirectory()){
                String[] dirList = dir.split("\\\\");
                dir = "";
                for (int i=0; i<dirList.length-1;i++){
                    dir += dirList[i] + "\\";
                }
                dir += sourceFile.getName();
            }
            else{
                dir += "\\" + sourceFile.getName();
            }
            //implement whole directory copying and pasting
            try {
                Files.copy(Paths.get(sourceDir), Paths.get(dir), REPLACE_EXISTING);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            //implement refreshing of JTree, likely need to make new interface implemented by both DirPanel and FilePanel with method buildTree, and then call Treemodel.reload().
        }
    }

}
