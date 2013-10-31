package com.firefrydev.ojh.helper.model;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class FolderPersistence implements Persistence {
    private static final Logger LOGGER = LoggerFactory.getLogger(FolderPersistence.class);
    private final File folder;

    public FolderPersistence(File folder) {
        if (folder == null) {
            throw new NullPointerException();
        }
        this.folder = folder;
    }

    @Override
    public void save(String key, String value) {
        File file = getFileForKey(key);
        try {
            if (value == null) {
                if (file.exists()) {
                    FileUtils.forceDelete(file);
                }
            } else {
                FileUtils.writeStringToFile(file, value);
            }
        } catch (IOException e) {
            LOGGER.error("Failed to save value \"{}\" for key \"{}\"", value, key, e);
        }
    }

    private File getFileForKey(String key) {
        return new File(folder, key);
    }

    @Override
    public String get(String key) {
        try {
            return FileUtils.readFileToString(getFileForKey(key));
        } catch (IOException e) {
            return null;
        }
    }
}
