package com.firefrydev.ojh.local;

public class Source {
    private final String className;
    private final String sourceCode;

    public Source(String className, String sourceCode) {
        if (className == null || sourceCode == null) {
            throw new NullPointerException();
        }
        this.className = className;
        this.sourceCode = sourceCode;
    }

    public String getClassName() {
        return className;
    }

    public String getSourceCode() {
        return sourceCode;
    }
}
