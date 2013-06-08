package com.firefrydev.onlinejudge.workspace;

public final class TestResult {
    private final boolean isPassed;
    private final String verdict;

    public TestResult(boolean passed, String verdict) {
        isPassed = passed;
        this.verdict = verdict;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public String getVerdict() {
        return verdict;
    }
}
