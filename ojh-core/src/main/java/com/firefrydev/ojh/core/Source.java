package com.firefrydev.ojh.core;

public class Source {
    private final String fileName;
    private final String sourceCode;

    public Source(String fileName, String sourceCode) {
        if (fileName == null || sourceCode == null) {
            throw new NullPointerException();
        }
        this.fileName = fileName;
        this.sourceCode = sourceCode;
    }

    public String getFileName() {
        return fileName;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Source source = (Source) o;

        if (!fileName.equals(source.fileName)) return false;
        if (!sourceCode.equals(source.sourceCode)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fileName.hashCode();
        result = 31 * result + sourceCode.hashCode();
        return result;
    }
}
