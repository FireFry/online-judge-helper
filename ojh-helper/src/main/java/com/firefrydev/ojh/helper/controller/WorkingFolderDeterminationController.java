package com.firefrydev.ojh.helper.controller;

import com.firefrydev.ojh.helper.model.PersistenceKeys;
import com.firefrydev.ojh.helper.view.View;

import java.io.File;

public class WorkingFolderDeterminationController {
    private final AppController appController;
    private final View view;

    public WorkingFolderDeterminationController(AppController appController) {
        this.appController = appController;
        this.view = appController.getViewFactory().createWorkingFolderDeterminationView(this);
    }

    public void execDeterminateWorkingFolder() {
        File workingFolder = getSavedWorkingFolder();
        if (isValidWorkingFolder(workingFolder)) {
            appController.onWorkingFolderDeterminated(workingFolder);
            return;
        }
        showView();
    }

    private File getSavedWorkingFolder() {
        String value = appController.getPersistence().get(PersistenceKeys.WORKING_FOLDER);
        return value == null ? null : new File(value);
    }

    private boolean isValidWorkingFolder(File workingFolder) {
        return workingFolder != null;
    }

    private void showView() {
        view.show();
    }

    public void onWorkingFolderDeterminated(File workingFolder) {
        validateSelectedByUserWorkingFolder(workingFolder);
        appController.getPersistence().save(PersistenceKeys.WORKING_FOLDER, workingFolder.getAbsolutePath());
        appController.onWorkingFolderDeterminated(workingFolder);
    }

    private void validateSelectedByUserWorkingFolder(File workingFolder) {
        if (!isValidWorkingFolder(workingFolder)) {
            throw new IllegalArgumentException("Working folder is not valid: " + workingFolder);
        }
    }

    public void onFolderDeterminationFailed() {
        appController.onCloseRequested();
    }

    public void execResetWorkingFolder() {
        appController.getPersistence().save(PersistenceKeys.WORKING_FOLDER, null);
    }
}
