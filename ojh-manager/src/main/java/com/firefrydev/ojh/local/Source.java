package com.firefrydev.ojh.local;

public class Source {
    private final String name;
    private final String sourceCode;

    public Source(String name, String sourceCode) {
        if (name == null || sourceCode == null) {
            throw new NullPointerException();
        }
        this.name = name;
        this.sourceCode = sourceCode;
    }

    public String getName() {
        return name;
    }

    public String getSourceCode() {
        return sourceCode;
    }
}
