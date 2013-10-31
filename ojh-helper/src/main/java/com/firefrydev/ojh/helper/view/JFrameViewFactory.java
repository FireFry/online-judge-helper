package com.firefrydev.ojh.helper.view;

import com.firefrydev.ojh.helper.controller.WorkingFolderDeterminationController;

import javax.swing.*;

public class JFrameViewFactory implements ViewFactory {

    @Override
    public View createWorkingFolderDeterminationView(final WorkingFolderDeterminationController workingFolderDeterminationController) {
        return new View() {
            @Override
            public void show() {
                JOptionPane.showMessageDialog(null, "You need to specify working folder");
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Choose working folder");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    workingFolderDeterminationController.onWorkingFolderDeterminated(chooser.getSelectedFile());
                } else {
                    workingFolderDeterminationController.onFolderDeterminationFailed();
                }
            }

            @Override
            public void hide() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
