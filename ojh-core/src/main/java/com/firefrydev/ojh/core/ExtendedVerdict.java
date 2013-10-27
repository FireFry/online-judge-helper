package com.firefrydev.ojh.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ExtendedVerdict {
    private final Verdict verdict;
    private final List<FailedTest> failedTests;

    public ExtendedVerdict(Verdict verdict, List<FailedTest> failedTests) {
        if (verdict == null || failedTests == null) {
            throw new NullPointerException();
        }
        this.verdict = verdict;
        this.failedTests = Collections.unmodifiableList(new ArrayList<FailedTest>(failedTests));
    }

    public Verdict getVerdict() {
        return verdict;
    }

    public List<FailedTest> getFailedTests() {
        return failedTests;
    }
}
