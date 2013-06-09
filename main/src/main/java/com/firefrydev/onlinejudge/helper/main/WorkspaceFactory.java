package com.firefrydev.onlinejudge.helper.main;

import com.firefrydev.onlinejudge.helper.workspace.DirectoryBasedWorkspace;
import com.firefrydev.onlinejudge.helper.workspace.Workspace;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

public class WorkspaceFactory {

    public static Workspace createWorkspace(String config) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        return createWorkspace(ConfigFactory.parseFile(new File(config)));
    }

    public static Workspace createWorkspace() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        return createWorkspace(ConfigFactory.load());
    }

    private static Workspace createWorkspace(Config appConfig) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Workspace workspace;
        appConfig = appConfig.withFallback(ConfigFactory.defaultReference());
        Configurations configurations = new Configurations(appConfig);
        workspace = new DirectoryBasedWorkspace(configurations.getOnlineJudgeSystem(), configurations.getWorkspaceDir(), configurations.getAuthor());
        return workspace;
    }
}
