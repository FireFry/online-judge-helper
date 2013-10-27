package com.firefrydev.ojh.local;

import com.firefrydev.ojh.core.Commit;
import com.firefrydev.ojh.core.Source;
import com.firefrydev.ojh.core.Verdict;
import com.firefrydev.ojh.utils.Callback;

class Commiter {
    private final Manager manager;
    private final String problemId;
    private final String authorId;
    private final Callback<Verdict> callback;

    public Commiter(Manager manager, String problemId, String authorId, Callback<Verdict> callback) {
        if (manager == null || problemId == null || authorId == null || callback == null) {
            throw new NullPointerException();
        }
        this.manager = manager;
        this.problemId = problemId;
        this.authorId = authorId;
        this.callback = callback;
    }

    public void commit() {
        manager.getStorage().getSource(problemId, manager.getLanguageProvider().getSourceFileName(problemId), new Callback<Source>() {
            @Override
            public void call(Source source) {
                Commit commit = Commit.builder()
                        .setAuthorId(authorId)
                        .setProblemId(problemId)
                        .setSourceCode(source.getSourceCode())
                        .setLanguage(manager.getLanguageProvider().language())
                        .build();
                manager.getSystem().getVerificationService().verify(commit, callback);
            }
        });
    }
}
