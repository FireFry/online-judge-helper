package com.firefrydev.onlinejudge.helper.workspace;

import com.firefrydev.onlinejudge.helper.core.Author;
import com.firefrydev.onlinejudge.helper.core.Language;
import com.firefrydev.onlinejudge.helper.core.OnlineJudgeSystem;

import java.io.File;

public interface WorkspaceProvider {
    File getWorkspaceDir();

    Author getAuthor();

    OnlineJudgeSystem getOnlineJudgeSystem();

    Language getDefaultLanguage();
}
