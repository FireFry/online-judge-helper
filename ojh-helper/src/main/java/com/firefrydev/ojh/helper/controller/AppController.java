package com.firefrydev.ojh.helper.controller;

import com.firefrydev.ojh.helper.model.FolderPersistence;
import com.firefrydev.ojh.helper.model.Persistence;
import com.firefrydev.ojh.helper.model.PrivateFolderManager;
import com.firefrydev.ojh.helper.view.JFrameViewFactory;
import com.firefrydev.ojh.helper.view.ViewFactory;
import com.firefrydev.ojh.helper.view.utils.SwingUtils;

import java.io.File;

public class AppController {
    private static final File USER_HOME_FOLDER = new File(System.getProperty("user.home"));

    private final WorkingFolderDeterminationController workingFolderDeterminationController;
    private final WorkingFolderController workingFolderController;
    private final Persistence persistence;
    private final PrivateFolderManager privateFolderManager;
    private final ViewFactory viewFactory;

    public AppController() {
        SwingUtils.setSystemLookAndFeel();
        viewFactory = new JFrameViewFactory();
        privateFolderManager = new PrivateFolderManager(new File(USER_HOME_FOLDER, ".ojh"));
        persistence = new FolderPersistence(privateFolderManager.getPersistenceFolder());
        workingFolderDeterminationController = new WorkingFolderDeterminationController(this);
        workingFolderController = new WorkingFolderController(this);
    }

    public void execStart() {
        determineWorkingFolder();
    }

    private void determineWorkingFolder() {
        workingFolderDeterminationController.execDeterminateWorkingFolder();
    }

    public void onWorkingFolderDeterminated(File directory) {
        workingFolderController.execManageDirectory(directory);
    }

    public void onCloseRequested() {
        System.exit(0);
    }

    public Persistence getPersistence() {
        return persistence;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public void onWorkingFolderChangeRequested() {
        workingFolderDeterminationController.execResetWorkingFolder();
        workingFolderDeterminationController.execDeterminateWorkingFolder();
    }
}
