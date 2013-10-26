package com.firefrydev.ojh.core;

import com.firefrydev.ojh.utils.Callback;

public interface VerificationService {

    void verify(Commit commit, Callback<Verdict> callback);

}
