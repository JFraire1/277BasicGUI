package ActualAttempt;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardWatchEventKinds.*;

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
            String identifier;
            String identifierSource[];
            if (sourceFile.isDirectory()){
                dir = duplicateDirectoryLoop(dir, 1);
                try {
                    addAllFiles(sourceFile, dir);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            else {
                dir = duplicateFileLoop(dir, 1);
                try {
                    Files.copy(Paths.get(sourceDir), Paths.get(dir), StandardCopyOption.COPY_ATTRIBUTES);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            if (parentPanel instanceof DirPanel){
                ((DirPanel)parentPanel).updateTree();
                ((DirPanel)parentPanel).getParentFrame().file.updateTree();
            }
            if (parentPanel instanceof FilePanel){
                ((FilePanel)parentPanel).getParentFrame().dir.updateSelection(dir.split("\\\\"));
                ((FilePanel)parentPanel).updateTree();
                ((FilePanel)parentPanel).getParentFrame().dir.updateTree();
            }
        }
    }

    private void addAllFiles(File file, String dir) throws IOException {
        Files.copy(file.toPath(), Paths.get(dir), REPLACE_EXISTING);
        File[] files = file.listFiles();
        if (files == null) { return;}
        String loopdir;
        for (File insideFile : files) {
            loopdir = dir + "\\" + insideFile.getName();
            addAllFiles(insideFile, loopdir);
        }
    }

    private String duplicateDirectoryLoop(String dir, int i){
        String thisDir = dir;
        if (Files.exists(Paths.get(dir))){
            thisDir += "(" + i + ")";
        }
        if (Files.exists(Paths.get(thisDir))){
            thisDir = duplicateDirectoryLoop(dir, i + 1);
        }
        return thisDir;
    }

    private String duplicateFileLoop(String dir, int i){
        String[] splitDir = dir.split("\\.");
        String thisDir = splitDir[0];
        if (Files.exists(Paths.get(dir))){
            thisDir += "(" + i + ")" + "." + splitDir[1];
        }
        if (Files.exists(Paths.get(thisDir))){
            thisDir = duplicateDirectoryLoop(dir, i + 1);
        }
        return thisDir;
    }
}
