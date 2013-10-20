package com.firefrydev.ojh.core;

import com.firefrydev.ojh.core.Commit;

public interface VerificationService {

    void verify(Commit commit, Callback callback);

    public interface Callback {
        void onVerdictReady(Verdict verdict);
    }

}
