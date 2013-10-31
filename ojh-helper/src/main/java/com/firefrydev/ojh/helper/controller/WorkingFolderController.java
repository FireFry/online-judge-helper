package com.firefrydev.ojh.helper.controller;

import com.firefrydev.ojh.lang.java.JavaLanguageProvider;
import com.firefrydev.ojh.local.Manager;
import com.firefrydev.ojh.stiorage.file.FileStorage;
import com.firefrydev.ojh.timus.TimusSystemProvider;

import javax.swing.*;
import java.io.File;

public class WorkingFolderController {
    private final AppController appController;

    private Manager manager;

    public WorkingFolderController(AppController appController) {
        this.appController = appController;
    }

    public void execManageDirectory(File folder) {
        this.manager = new Manager(new TimusSystemProvider().create(), new JavaLanguageProvider(), new FileStorage(folder));
        JOptionPane.showMessageDialog(null, "Working folder controller is not implemented yet! Folder: " + folder);
        appController.onWorkingFolderChangeRequested();
        appController.onCloseRequested();
    }
}
