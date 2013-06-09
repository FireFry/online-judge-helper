package com.firefrydev.onlinejudge.helper.workspace;

public final class TestResult {
    private final String id;
    private final boolean isPassed;
    private final String verdict;

    public TestResult(String id, boolean passed, String verdict) {
        this.id = id;
        this.isPassed = passed;
        this.verdict = verdict;
    }

    public String getId() {
        return id;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public String getVerdict() {
        return verdict;
    }
}
