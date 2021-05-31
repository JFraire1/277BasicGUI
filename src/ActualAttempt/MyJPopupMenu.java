package ActualAttempt;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class MyJPopupMenu extends JPopupMenu {
    private final JTree tree;
    private final JPanel parentPanel;
    private final DefaultMutableTreeNode selectedNode;
    private static String sourceDir;
    private static boolean hasSourceDir = false;
    private String dir;
    private static boolean cutSwitch = false;

    public MyJPopupMenu(DefaultMutableTreeNode selectedNode, String dir, JPanel parentPanel, JTree tree){
        String cutText;
        cutText = "Cut";
        if (cutSwitch){
            cutText = "Cancel";
        }
        this.selectedNode = selectedNode;
        this.dir = dir;
        this.tree = tree;
        this.parentPanel = parentPanel;
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem cut = new JMenuItem(cutText);
        JMenuItem delete = new JMenuItem("Delete");
        JMenuItem paste = new JMenuItem("Paste");
        JMenuItem rename = new JMenuItem("Rename");
        cut.addActionListener (new CutActionListener());
        copy.addActionListener(new CopyActionListener());
        delete.addActionListener(new DeleteActionListener());
        paste.addActionListener(new PasteActionListener());
        rename.addActionListener(new RenameActionListener());
        add(copy);
        add(cut);
        add(paste);
        add(rename);
        add(delete);
    }

    private class CutActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (cutSwitch){
                hasSourceDir = false;
                cutSwitch = false;
                return;
            }
            sourceDir = dir;
            hasSourceDir = true;
            cutSwitch = true;
        }
    }

    private class CopyActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            sourceDir = dir;
            hasSourceDir = true;
            cutSwitch = false;
        }
    }

    private class PasteActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!hasSourceDir){
                //add info popup
                return;
            }
            if (dir.startsWith(sourceDir) && dir != sourceDir){
                //add info popup
                return;
            }
            parentPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            File file = new File(dir);
            File sourceFile = new File(sourceDir);
            if (!sourceFile.exists()){
                //add info popup
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
            update();
            if (cutSwitch){
                dir = sourceDir;
                delete();
                hasSourceDir = false;
                cutSwitch = false;
            }
            update();
            parentPanel.setCursor(Cursor.getDefaultCursor());
        }
    }

    private class RenameActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            cutSwitch = false;
            //todo
            //add dialog, return new name, check for special characters, dialog popup if unusable name
        }
    }

    private class DeleteActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            cutSwitch = false;
            delete();
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

    private void deleteAllFiles(File file, String dir) throws IOException {
        File[] files = file.listFiles();
        if (files == null){
            Files.deleteIfExists(Paths.get(dir));
            return;
        }
        String loopdir = dir;
        for (File insideFile : files) {
            loopdir = dir + "\\" + insideFile.getName();
            deleteAllFiles(insideFile, loopdir);
        }
        Files.deleteIfExists(Paths.get(dir));
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
        if (!Files.exists(Paths.get(dir))){
            return dir;
        }
        thisDir += "(" + i + ")" + "." + splitDir[1];
        if (Files.exists(Paths.get(thisDir))){
            thisDir = duplicateFileLoop(dir, i + 1);
        }
        return thisDir;
    }

    private void update(){
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

    private void delete(){
        parentPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        File file = new File(dir);
        File parentfile = file.getParentFile();
        try {
            deleteAllFiles(file, dir);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        if (parentPanel instanceof DirPanel){
            ((DirPanel)parentPanel).updateSelection(dir.split("\\\\"));
            ((DirPanel)parentPanel).updateTree();
            ((DirPanel)parentPanel).getParentFrame().file.buildTree(parentfile.getAbsolutePath());
            ((DirPanel)parentPanel).getParentFrame().DirMenu.updateDirectoryMenu(parentfile.getAbsolutePath());
        }

        if (parentPanel instanceof FilePanel){
            ((FilePanel)parentPanel).getParentFrame().dir.updateSelection(dir.split("\\\\"));
            ((FilePanel)parentPanel).getParentFrame().dir.updateTree();
            if (!((FilePanel)parentPanel).directory.exists()){
                ((FilePanel)parentPanel).buildTree(parentfile.getAbsolutePath());
                ((FilePanel)parentPanel).getParentFrame().DirMenu.updateDirectoryMenu(parentfile.getAbsolutePath());
            }
            else {
                ((FilePanel)parentPanel).updateTree();
            }
        }
        parentPanel.setCursor(Cursor.getDefaultCursor());
    }
}
