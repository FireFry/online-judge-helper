package com.firefrydev.onlinejudge.helper.core;

import java.io.IOException;

public interface OnlineJudgeSystem {

    Problem getProblem(String id) throws IOException;

    CommitResult perform(Commit commit) throws IOException, InterruptedException;

    LanguageSettings getLanguageSettings(Language language);

}
