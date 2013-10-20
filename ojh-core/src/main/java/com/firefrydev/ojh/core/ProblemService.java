package com.firefrydev.ojh.core;

public interface ProblemService {

    void loadProblem(String id, Callback callback);

    public interface Callback {
        void onProblemLoaded(Problem problem);
    }

}
