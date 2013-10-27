package com.firefrydev.ojh.stiorage.file;

import com.firefrydev.ojh.core.Source;
import com.firefrydev.ojh.core.Test;
import com.firefrydev.ojh.local.Storage;
import com.firefrydev.ojh.utils.Callback;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class FileStorage implements Storage {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileStorage.class);
    private static final String TESTS_FOLDER_NAME = "tests";
    private static final String TEST_INPUT_FILE = "input.txt";
    private static final String TEST_OUTPUT_FILE = "output.txt";

    private final File storageDirectory;

    public FileStorage(File storageDirectory) {
        if (storageDirectory == null) {
            throw new NullPointerException();
        }
        this.storageDirectory = storageDirectory;
    }

    @Override
    public void saveSource(String problemId, Source source, Runnable callback) {
        safeWriteToFile(new File(new File(storageDirectory, problemId), source.getFileName()), source.getSourceCode());
        callback.run();
    }

    @Override
    public void getSource(String problemId, String fileName, Callback<Source> callback) {
        try {
            String sourceCode = FileUtils.readFileToString(new File(new File(storageDirectory, problemId), fileName));
            callback.call(new Source(fileName, sourceCode));
        } catch (IOException e) {
            LOGGER.error("Failed to save source", e);
        }
    }

    @Override
    public void saveTests(String problemId, List<Test> tests, Runnable callback) {
        File testsFolder = testsFolder(problemId);
        int id = 0;
        for (Test test : tests) {
            File testFolder = new File(testsFolder, String.valueOf(id++));
            safeWriteToFile(new File(testFolder, TEST_INPUT_FILE), test.getInput());
            safeWriteToFile(new File(testFolder, TEST_OUTPUT_FILE), test.getOutput());
        }
        callback.run();
    }

    @Override
    public void getTests(String problemId, Callback<List<Test>> callback) {
        List<Test> result = new LinkedList<Test>();
        File testsFolder = testsFolder(problemId);
        for (File testFolder : testsFolder.listFiles()) {
            try {
                String input = FileUtils.readFileToString(new File(testFolder, TEST_INPUT_FILE));
                String output = FileUtils.readFileToString(new File(testFolder, TEST_OUTPUT_FILE));
                result.add(new Test(input, output));
            } catch (Exception e) {
                LOGGER.error("Failed to read test from {}", testFolder, e);
            }
        }
        callback.call(result);
    }

    private void safeWriteToFile(File file, String text) {
        try {
            FileUtils.writeStringToFile(file, text);
        } catch (IOException e) {
            LOGGER.error("Failed to write string to file \"{}\": \"{}\"", file, text, e);
        }
    }

    private File testsFolder(String problemId) {
        return new File(new File(storageDirectory, problemId), TESTS_FOLDER_NAME);
    }
}