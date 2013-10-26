package com.firefrydev.ojh.core;

public class FailedTest {
    private final Test test;
    private final String wrongOutput;

    public FailedTest(Test test, String wrongOutput) {
        if (test == null || wrongOutput == null) {
            throw new NullPointerException();
        }
        this.test = test;
        this.wrongOutput = wrongOutput;
    }

    public Test getTest() {
        return test;
    }

    public String getWrongOutput() {
        return wrongOutput;
    }
}
