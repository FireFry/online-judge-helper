package com.firefrydev.onlinejudge.helper.workspace;

import com.firefrydev.onlinejudge.helper.core.Author;
import com.firefrydev.onlinejudge.helper.core.Language;
import com.firefrydev.onlinejudge.helper.core.OnlineJudgeSystem;
import com.typesafe.config.Config;

import java.io.File;

public class TypesafeProvider implements WorkspaceProvider {
    private final File workspaceDir;
    private final Author author;
    private final OnlineJudgeSystem onlineJudgeSystem;
    private final Language defaultLanguage;

    public TypesafeProvider(Config config) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        workspaceDir = new File(config.getString("online-judge-helper.workspace"));
        author = new Author(config.getString("online-judge-helper.author.id"), config.getString("online-judge-helper.author.password"));
        onlineJudgeSystem = (OnlineJudgeSystem) Class.forName(config.getString("online-judge-helper.system")).newInstance();
        defaultLanguage = Language.valueOf(config.getString("online-judge-helper.default-language").toUpperCase());
    }

    @Override
    public File getWorkspaceDir() {
        return workspaceDir;
    }

    @Override
    public Author getAuthor() {
        return author;
    }

    @Override
    public OnlineJudgeSystem getOnlineJudgeSystem() {
        return onlineJudgeSystem;
    }

    @Override
    public Language getDefaultLanguage() {
        return defaultLanguage;
    }
}
