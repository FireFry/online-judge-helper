package com.firefrydev.ojh.timus;

import com.firefrydev.ojh.core.Commit;
import com.firefrydev.ojh.core.Verdict;
import com.firefrydev.ojh.core.VerificationService;
import com.firefrydev.ojh.timus.util.RetryOnFailRunnable;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimusVerificationService implements VerificationService {
    private final ScheduledExecutorService executorService;
    private final int maxTryCount;

    public TimusVerificationService(ScheduledExecutorService scheduledExecutorService, int maxTryCount) {
        this.executorService = scheduledExecutorService;
        this.maxTryCount = maxTryCount;
    }

    @Override
    public void verify(Commit commit, Callback callback) {
        executorService.execute(new SentCommitRunnable(commit, callback));
    }

    private class SentCommitRunnable extends RetryOnFailRunnable {
        private final Commit commit;
        private final Callback callback;

        public SentCommitRunnable(Commit commit, Callback callback) {
            super(maxTryCount);
            this.commit = commit;
            this.callback = callback;
        }

        protected void unsafeRun() throws IOException {
            String commitId = TimusVerifier.send(commit);
            executorService.execute(new LoadVerdictRunnable(commit, commitId, callback));
        }

        protected void retry() {
            executorService.execute(this);
        }
    }

    private class LoadVerdictRunnable extends RetryOnFailRunnable {
        private final Commit commit;
        private final String commitId;
        private final Callback callback;

        public LoadVerdictRunnable(Commit commit, String commitId, Callback callback) {
            super(maxTryCount);
            this.commit = commit;
            this.commitId = commitId;
            this.callback = callback;
        }

        protected void unsafeRun() throws IOException {
            Verdict verdict = TimusVerifier.loadVerdict(commit, commitId);
            callback.onVerdictReady(verdict);
        }

        protected void retry() {
            executorService.schedule(this, 1000, TimeUnit.MILLISECONDS);
        }
    }
}
