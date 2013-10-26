package com.firefrydev.ojh.local;

import com.firefrydev.ojh.core.ExtendedVerdict;
import com.firefrydev.ojh.core.OnlineJudgeSystem;
import com.firefrydev.ojh.core.Verdict;
import com.firefrydev.ojh.utils.Callback;

public class Manager {
    private final OnlineJudgeSystem system;
    private final LanguageProvider languageProvider;
    private final Storage storage;

    public Manager(OnlineJudgeSystem system, LanguageProvider languageProvider, Storage storage) {
        if (system == null || languageProvider == null || storage == null) {
            throw new NullPointerException();
        }
        this.system = system;
        this.languageProvider = languageProvider;
        this.storage = storage;
    }

    public void init(String problemId, Runnable callback) {
        new ProblemSaver(this, problemId, callback).save();
    }

    public void test(String problemId, Callback<ExtendedVerdict> callback) {
        new Tester(this, problemId, callback).test();
    }

    public void commit(String problemId, String authorId, Callback<Verdict> callback) {
        new Commiter(this, problemId, authorId, callback).commit();
    }

    OnlineJudgeSystem getSystem() {
        return system;
    }

    LanguageProvider getLanguageProvider() {
        return languageProvider;
    }

    Storage getStorage() {
        return storage;
    }
}
