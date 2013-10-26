package com.firefrydev.ojh.local;

import com.firefrydev.ojh.core.Problem;
import com.firefrydev.ojh.core.Test;
import com.firefrydev.ojh.utils.Callback;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ProblemSaver {
    public static final int TOTAL_PARTS_COUNT = 2;

    private final Manager manager;
    private final String problemId;
    private final Runnable callback;

    private final AtomicBoolean isUsed = new AtomicBoolean(false);
    private final AtomicInteger leftoverParts = new AtomicInteger(TOTAL_PARTS_COUNT);

    private final Runnable storageCallback = new Runnable() {
        @Override
        public void run() {
            onPartProcessed();
        }
    };

    public ProblemSaver(Manager manager, String problemId, Runnable callback) {
        if (manager == null || problemId == null) {
            throw new NullPointerException();
        }
        this.manager = manager;
        this.problemId = problemId;
        this.callback = callback;
    }

    public void save() {
        if (isUsed.getAndSet(true)) {
            throw new IllegalStateException();
        }
        manager.getSystem().getProblemService().loadProblem(problemId, new Callback<Problem>() {
            @Override
            public void call(Problem problem) {
                onTestsReady(problem.getTests());
            }
        });
        onSourceTemplateReady(manager.getLanguageProvider().createSourceTemplate(problemId));
    }

    private void onTestsReady(List<Test> tests) {
        manager.getStorage().saveTests(problemId, tests, storageCallback);
    }

    private void onSourceTemplateReady(Source sourceTemplate) {
        manager.getStorage().saveSource(problemId, sourceTemplate, storageCallback);
    }

    private void onPartProcessed() {
        if (leftoverParts.decrementAndGet() < 1 && callback != null) {
            callback.run();
        }
    }
}