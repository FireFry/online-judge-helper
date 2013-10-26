package com.firefrydev.ojh.lang.java;

import com.firefrydev.ojh.core.*;
import com.firefrydev.ojh.lang.java.utils.ClassLoadUtils;
import com.firefrydev.ojh.local.LanguageProvider;
import com.firefrydev.ojh.local.Source;
import com.firefrydev.ojh.utils.Callback;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class JavaLanguageProvider implements LanguageProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(JavaLanguageProvider.class);
    private static final String ID = "%id%";
    private static final String CLASS_NAME_TEMPLATE = "Problem" + ID;
    private static final String EXTENSION = ".java";

    @Override
    public Language language() {
        return DefaultLanguages.JAVA;
    }

    @Override
    public Source createSourceTemplate(String problemId) {
        try {
            String sourceCode = FileUtils.readFileToString(new File("templates.txt")).replaceAll(ID, problemId);
            return new Source(getSourceName(problemId), sourceCode);
        } catch (IOException e) {
            LOGGER.error("Exception while reading template file", e);
            return null;
        }
    }

    @Override
    public void verify(String problemId, String source, Collection<Test> tests, Callback<ExtendedVerdict> callback) {
        try {
            Class problemClass = ClassLoadUtils.load(getClassName(problemId), source);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            List<FailedTest> failedTests = new LinkedList<FailedTest>();
            for (Test test : tests) {
                InputStream inputStream = new ByteArrayInputStream(test.getInput().getBytes());
                outputStream.reset();
                Object instance = problemClass
                        .getConstructor(InputStream.class, OutputStream.class)
                        .newInstance(inputStream, outputStream);
                Method solve = problemClass.getMethod("solve");
                try {
                    solve.invoke(instance);
                } catch (RuntimeException e) {
                    failedTests.add(new FailedTest(test, e, outputStream.toString()));
                    continue;
                }
                String output = outputStream.toString();
                if (!test.getOutput().equals(output)) {
                    failedTests.add(new FailedTest(test, new RuntimeException("Output not equals expected"), output));
                }
            }
            Verdict verdict = failedTests.isEmpty() ? Verdict.accepted() : Verdict.rejected("Come tests failed");
            ExtendedVerdict extendedVerdict = new ExtendedVerdict(verdict, failedTests);
            callback.call(extendedVerdict);
        } catch (Exception e) {
            LOGGER.error("Failed to verify problem", e);
        }
    }

    private String getClassName(String problemId) {
        return CLASS_NAME_TEMPLATE.replaceAll(ID, problemId);
    }

    @Override
    public String getSourceName(String problemId) {
        return getClassName(problemId) + EXTENSION;
    }
}
