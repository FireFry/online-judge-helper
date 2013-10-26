package com.firefrydev.ojh.timus;

import com.firefrydev.ojh.core.Problem;
import com.firefrydev.ojh.core.ProblemService;
import com.firefrydev.ojh.utils.Callback;
import com.firefrydev.ojh.utils.RetryOnFailRunnable;

import java.util.concurrent.ScheduledExecutorService;

public class TimusProblemService implements ProblemService {
    private final ScheduledExecutorService executorService;
    private final int maxTryCount;

    public TimusProblemService(ScheduledExecutorService scheduledExecutorService, int maxTryCount) {
        this.executorService = scheduledExecutorService;
        this.maxTryCount = maxTryCount;
    }

    @Override
    public void loadProblem(String id, Callback<Problem> callback) {
        executorService.execute(new LoadProblemTask(id, callback));
    }

    private class LoadProblemTask extends RetryOnFailRunnable {
        private final String id;
        private final Callback<Problem> callback;

        public LoadProblemTask(String id, Callback<Problem> callback) {
            super(maxTryCount);
            this.id = id;
            this.callback = callback;
        }

        @Override
        public void unsafeRun() throws Exception {
            Problem problem = TimusProblemLoader.loadProblem(id);
            callback.call(problem);
        }

        @Override
        protected void retry() {
            executorService.execute(this);
        }
    }
}
