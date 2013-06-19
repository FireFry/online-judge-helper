package com.firefrydev.onlinejudge.helper.workspace;

public final class TestResult {
    private final String id;
    private final boolean isPassed;
    private final String verdict;
    private final Exception exception;

    public static TestResult passed(String id, String verdict) {
        return new TestResult(id, true, verdict, null);
    }

    public static TestResult failed(String id, String verdict, Exception e) {
        return new TestResult(id, false, verdict, e);
    }

    public static TestResult failed(String id, String verdict) {
        return failed(id, verdict, null);
    }

    public TestResult(String id, boolean passed, String verdict, Exception e) {
        this.id = id;
        this.isPassed = passed;
        this.verdict = verdict;
        this.exception = e;
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

    public Exception getException() {
        return exception;
    }
}
