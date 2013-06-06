package com.firefrydev.onlinejudge.helper.core;

public class CommitResult {
    private final boolean isAccepted;
    private final String verdict;

    public CommitResult(boolean isAccepted, String verdict) {
        this.isAccepted = isAccepted;
        this.verdict = verdict;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public String getVerdict() {
        return verdict;
    }
}
