package ActualAttempt;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class DirectoryLabel extends JLabel {
    public DirectoryLabel(String name, String dir, FileFrame parent){
        setText(name);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parent.updateFile(dir);
            }
        });
    }

}
