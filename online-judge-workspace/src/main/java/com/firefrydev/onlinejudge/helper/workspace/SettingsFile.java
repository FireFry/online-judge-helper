package com.firefrydev.onlinejudge.helper.workspace;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsFile {
    private final File settingsFile;
    private final Map<String, String> settings = new HashMap<String, String>();

    public SettingsFile(File settingsFile) {
        this.settingsFile = settingsFile;
    }

    public String get(String key) throws IOException {
        load();
        return settings.get(key);
    }

    public void put(String key, String value) throws IOException {
        load();
        settings.put(key, value);
        save();
    }

    private void load() throws IOException {
        settings.clear();
        if (!settingsFile.exists()) {
            return;
        }
        List<String> lines = FileUtils.readLines(settingsFile);
        for (String line : lines) {
            String[] values = line.split("=");
            if (values.length == 2) {
                settings.put(values[0].trim(), values[1].trim());
            }
        }
    }

    private void save() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : settings.keySet()) {
            stringBuilder
                    .append(key)
                    .append(" = ")
                    .append(settings.get(key))
                    .append("\n");
        }
        if (settingsFile.exists()) {
            FileUtils.forceDelete(settingsFile);
        }
        FileUtils.writeStringToFile(settingsFile, stringBuilder.toString());
    }
}
