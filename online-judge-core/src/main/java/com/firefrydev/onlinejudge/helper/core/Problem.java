package com.firefrydev.onlinejudge.helper.core;

import java.util.LinkedList;
import java.util.List;

/**
 * @immutable
 */
public final class Problem {
    private final String id;
    private final String name;
    private final String description;
    private final String inputFormat;
    private final String outputFormat;
    private final List<SampleTest> sampleTests;

    public Problem(String id, String name, String description, String inputFormat, String outputFormat, List<SampleTest> sampleTests) {
        if (id == null || name == null || description == null || inputFormat == null || outputFormat == null
                || sampleTests == null) {
            throw new NullPointerException();
        }
        for (SampleTest sampleTest : sampleTests) {
            if (sampleTest == null) {
                throw new NullPointerException();
            }
        }
        this.id = id;
        this.name = name;
        this.description = description;
        this.inputFormat = inputFormat;
        this.outputFormat = outputFormat;
        this.sampleTests = new LinkedList<SampleTest>(sampleTests);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getInputFormat() {
        return inputFormat;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public List<SampleTest> getSampleTests() {
        return new LinkedList<SampleTest>(sampleTests);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Problem:\n");
        stringBuilder.append(getId());
        stringBuilder.append("\n\nDescription:\n");
        stringBuilder.append(getDescription());
        stringBuilder.append("\n");
        for (SampleTest sampleTest : getSampleTests()) {
            stringBuilder.append("\nInput:\n");
            stringBuilder.append(sampleTest.getInput());
            stringBuilder.append("\n\nOutput:\n");
            stringBuilder.append(sampleTest.getOutput());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
