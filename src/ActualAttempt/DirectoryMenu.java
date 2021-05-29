package ActualAttempt;

import javax.swing.*;

public class DirectoryMenu extends JMenuBar {
    private FileFrame parent;
    public DirectoryMenu(FileFrame parent){
        this.parent = parent;
    }

    public void updateDirectoryMenu(String directory){
        removeAll();
        revalidate();
        repaint();
        String[] path = directory.split("\\\\");
        String pathOfCurrent = "";
        for (String s : path) {
            pathOfCurrent += s + "\\";
            add(new DirectoryLabel(s + "\\", pathOfCurrent, parent));
        }
    }
}
