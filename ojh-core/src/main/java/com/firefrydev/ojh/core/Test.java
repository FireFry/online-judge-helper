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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Test test = (Test) o;

        if (!input.equals(test.input)) return false;
        if (!output.equals(test.output)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = input.hashCode();
        result = 31 * result + output.hashCode();
        return result;
    }
}
