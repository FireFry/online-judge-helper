package com.firefrydev.ojh.core;

import com.firefrydev.ojh.utils.Callback;

public interface ExtendedVerificationService {

    void verify(Commit commit, Callback<ExtendedVerdict> callback);

}
