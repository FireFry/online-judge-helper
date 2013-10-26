package com.firefrydev.ojh.utils;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class RetryOnFailRunnable implements Runnable {
    private final int maxTryCount;
    private final AtomicInteger counter = new AtomicInteger();

    protected RetryOnFailRunnable(int maxTryCount) {
        if (maxTryCount < 0) {
            throw new IllegalArgumentException();
        }
        this.maxTryCount = maxTryCount;
    }

    @Override
    public final void run() {
        try {
            unsafeRun();
        } catch (Exception e) {
            int tryCount = counter.incrementAndGet();
            if (maxTryCount > 0 && tryCount < maxTryCount) {
                retry();
            } else {
                e.printStackTrace();
            }
        }
    }

    protected abstract void unsafeRun() throws Exception;

    protected abstract void retry();
}
