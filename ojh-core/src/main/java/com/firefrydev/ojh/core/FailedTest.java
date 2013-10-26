package com.firefrydev.ojh.core;

public class FailedTest {
    private final Test test;
    private final Exception cause;
    private final String output;

    public FailedTest(Test test, Exception cause, String output) {
        if (test == null || cause == null) {
            throw new NullPointerException();
        }
        this.test = test;
        this.cause = cause;
        this.output = output;
    }

    public Test getTest() {
        return test;
    }

    public Exception getCause() {
        return cause;
    }

    public String getOutput() {
        return output;
    }
}
