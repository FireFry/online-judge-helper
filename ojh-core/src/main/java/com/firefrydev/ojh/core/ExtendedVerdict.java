package com.firefrydev.ojh.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ExtendedVerdict {
    private final Verdict verdict;
    private final Collection<FailedTest> failedTests;

    public ExtendedVerdict(Verdict verdict, Collection<FailedTest> failedTests) {
        if (verdict == null || failedTests == null) {
            throw new NullPointerException();
        }
        this.verdict = verdict;
        this.failedTests = Collections.unmodifiableCollection(new ArrayList<FailedTest>(failedTests));
    }

    public Verdict getVerdict() {
        return verdict;
    }

    public Collection<FailedTest> getFailedTests() {
        return failedTests;
    }
}
