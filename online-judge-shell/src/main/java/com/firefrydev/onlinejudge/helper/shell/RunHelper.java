package com.firefrydev.onlinejudge.helper.shell;

import com.firefrydev.onlinejudge.helper.workspace.TypesafeWorkspaceFactory;
import com.firefrydev.onlinejudge.helper.workspace.Workspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunHelper {
    private static final Logger logger = LoggerFactory.getLogger(RunHelper.class);

    public static void main(String[] args) {
        Workspace workspace;
        try {
            workspace = args.length == 0 ? TypesafeWorkspaceFactory.createWorkspace() : TypesafeWorkspaceFactory.createWorkspace(args[0]);
        } catch (Exception e) {
            System.out.println("Failed to load configurations: " + e);
            logger.error("Failed to load configurations", e);
            return;
        }
        new WorkspaceController(workspace).execStart();
    }

}
