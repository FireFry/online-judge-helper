package com.firefrydev.onlinejudge.helper.main;

import com.firefrydev.onlinejudge.helper.core.Author;
import com.firefrydev.onlinejudge.helper.core.OnlineJudgeSystem;
import com.typesafe.config.Config;

import java.io.File;

public class Configurations {
    private final File workspaceDir;
    private final Author author;
    private final OnlineJudgeSystem onlineJudgeSystem;

    public Configurations(Config config) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        workspaceDir = new File(config.getString("online-judge-helper.workspace"));
        author = new Author(config.getString("online-judge-helper.author.id"), config.getString("online-judge-helper.author.password"));
        onlineJudgeSystem = (OnlineJudgeSystem) Class.forName(config.getString("online-judge-helper.system")).newInstance();
    }

    public File getWorkspaceDir() {
        return workspaceDir;
    }

    public Author getAuthor() {
        return author;
    }

    public OnlineJudgeSystem getOnlineJudgeSystem() {
        return onlineJudgeSystem;
    }
}
