package com.firefrydev.ojh.helper.model;

import java.io.File;

public class PrivateFolderManager {
    private final File folder;
    private final File persistenceFolder;

    public PrivateFolderManager(File folder) {
        this.folder = folder;
        this.persistenceFolder = new File(folder, "persistence");
    }

    public File getTempFolder() {
        throw new UnsupportedOperationException();
    }

    public File getPersistenceFolder() {
        return persistenceFolder;
    }
}
