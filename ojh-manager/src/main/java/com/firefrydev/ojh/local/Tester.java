package com.firefrydev.ojh.local;

import com.firefrydev.ojh.core.ExtendedVerdict;
import com.firefrydev.ojh.core.Test;
import com.firefrydev.ojh.utils.Callback;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Tester {
    private static final int TOTAL_COMPONENTS_COUNT = 2;

    private final Manager manager;
    private final String problemId;
    private final Callback<ExtendedVerdict> callback;

    private volatile String source;
    private volatile List<Test> tests;
    private final AtomicInteger leftoverComponents = new AtomicInteger(TOTAL_COMPONENTS_COUNT);

    public Tester(Manager manager, String problemId, Callback<ExtendedVerdict> callback) {
        if (manager == null || problemId == null || callback == null) {
            throw new NullPointerException();
        }
        this.manager = manager;
        this.problemId = problemId;
        this.callback = callback;
    }

    public void test() {
        manager.getStorage().getSource(problemId, manager.getLanguageProvider().getSourceName(problemId), new Callback<String>() {
            @Override
            public void call(String source) {
                Tester.this.source = source;
                onComponentLoaded();
            }
        });
        manager.getStorage().getTests(problemId, new Callback<List<Test>>() {
            @Override
            public void call(List<Test> tests) {
                Tester.this.tests = tests;
                onComponentLoaded();
            }
        });
    }

    private void onComponentLoaded() {
        if (leftoverComponents.decrementAndGet() < 1) {
            manager.getLanguageProvider().verify(problemId, source, tests, callback);
        }
    }
}
