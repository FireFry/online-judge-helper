package com.firefrydev.onlinejudge.helper.core;

import java.util.LinkedList;
import java.util.List;

public class ProblemBuilder {
    private String id;
    private String name;
    private String description;
    private String inputFormat;
    private String outputFormat;
    private List<SampleTest> sampleTests;

    public ProblemBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public ProblemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProblemBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProblemBuilder setInputFormat(String inputFormat) {
        this.inputFormat = inputFormat;
        return this;
    }

    public ProblemBuilder setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
        return this;
    }

    public ProblemBuilder setSampleTests(List<SampleTest> sampleTests) {
        this.sampleTests = sampleTests;
        return this;
    }

    public ProblemBuilder addSampleTest(SampleTest sampleTest) {
        if (sampleTests == null) {
            sampleTests = new LinkedList<SampleTest>();
        }
        sampleTests.add(sampleTest);
        return this;
    }

    public Problem createProblem() {
        return new Problem(id, name, description, inputFormat, outputFormat, sampleTests);
    }
}
