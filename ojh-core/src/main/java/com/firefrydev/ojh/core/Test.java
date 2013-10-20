package com.firefrydev.ojh.core;

public class Test {
    private final String input;
    private final String output;

    public Test(String input, String output) {
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
