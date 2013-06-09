package com.firefrydev.onlinejudge.helper.main;

import com.firefrydev.onlinejudge.helper.workspace.Workspace;

public class RunHelper {

    public static void main(String[] args) {
        Workspace workspace;
        try {
            workspace = args.length == 0 ? WorkspaceFactory.createWorkspace() : WorkspaceFactory.createWorkspace(args[0]);
        } catch (Exception e) {
            System.out.println("Failed to load configurations: " + e);
            return;
        }
        new WorkspaceController(workspace).execStart();
    }

}
