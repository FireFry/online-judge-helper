package com.firefrydev.ojh.core;

import com.firefrydev.ojh.utils.Callback;

public interface ProblemService {

    void loadProblem(String id, Callback<Problem> callback);

}
