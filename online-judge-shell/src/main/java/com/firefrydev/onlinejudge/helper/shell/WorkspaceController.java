package com.firefrydev.onlinejudge.helper.shell;

import com.firefrydev.onlinejudge.helper.workspace.Workspace;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkspaceController {
    private final Workspace workspace;
    private final ExecutorService excecutor = Executors.newSingleThreadExecutor();;

    public WorkspaceController(Workspace workspace) {
        this.workspace = workspace;
    }

    public void execStart() {
        System.out.println("Welcome to Online Judge Helper shell!");
        excecutor.execute(new WaitForInputTask(excecutor, excecutor, workspace));
    }
}
