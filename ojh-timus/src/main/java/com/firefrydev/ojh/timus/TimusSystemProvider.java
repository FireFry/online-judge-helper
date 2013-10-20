package com.firefrydev.ojh.timus;

import com.firefrydev.ojh.core.OnlineJudgeSystem;
import com.firefrydev.ojh.core.OnlineJudgeSystemProvider;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class TimusSystemProvider implements OnlineJudgeSystemProvider {
    private static final int MAX_TRY_COUNT = 10;

    @Override
    public OnlineJudgeSystem create() {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        TimusProblemService problemService = new TimusProblemService(scheduledExecutorService, MAX_TRY_COUNT);
        TimusVerificationService verificationService = new TimusVerificationService(scheduledExecutorService, MAX_TRY_COUNT);
        return new OnlineJudgeSystem(problemService, verificationService);
    }
}
