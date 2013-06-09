package com.firefrydev.onlinejudge.workspace;

import com.firefrydev.onlinejudge.helper.core.CommitResult;
import com.firefrydev.onlinejudge.helper.core.Language;

import java.io.IOException;

public interface Workspace {

    void switchTo(String problemId) throws IOException;

    void init(Language language) throws IOException;

    String getProblemId() throws IOException;

    TestResult[] test();

    TestResult[] test(Class solution);

    CommitResult commit() throws IOException, InterruptedException;

}
