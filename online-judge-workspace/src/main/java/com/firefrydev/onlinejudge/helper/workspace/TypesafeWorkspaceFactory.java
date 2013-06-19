package com.firefrydev.onlinejudge.helper.workspace;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

public class TypesafeWorkspaceFactory {

    public static Workspace createWorkspace(String config) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        return createWorkspace(ConfigFactory.parseFile(new File(config)));
    }

    public static Workspace createWorkspace() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        return createWorkspace(ConfigFactory.load());
    }

    private static Workspace createWorkspace(Config appConfig) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Workspace workspace;
        appConfig = appConfig.withFallback(ConfigFactory.defaultReference());
        TypesafeProvider configurations = new TypesafeProvider(appConfig);
        workspace = new DirectoryBasedWorkspace(configurations);
        return workspace;
    }
}
