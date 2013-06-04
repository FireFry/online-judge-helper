package com.firefrydev.onlinejudge.helper.core;

/**
 * @immutasble
 */
public final class SampleTest {
    private final String input;
    private final String output;

    public SampleTest(String input, String output) {
        if (input == null || output == null) {
            throw new NullPointerException();
        }
        this.input = input;
        this.output = output;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }
}
