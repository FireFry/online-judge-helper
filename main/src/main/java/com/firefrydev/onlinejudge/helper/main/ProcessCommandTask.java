package com.firefrydev.onlinejudge.helper.main;

import com.firefrydev.onlinejudge.helper.main.command.*;
import com.firefrydev.onlinejudge.helper.workspace.Workspace;

import java.util.HashMap;
import java.util.Map;

public class ProcessCommandTask implements Runnable {
    private final Workspace workspace;
    private final String command;
    private final Map<String, Command> handlers = new HashMap<String, Command>() {{
        put("init", new InitCommand());
        put("switch", new SwitchCommand());
        put("test", new TestCommand());
        put("commit", new CommitComand());
    }};

    public ProcessCommandTask(Workspace workspace, String command) {
        this.workspace = workspace;
        this.command = command;
    }

    @Override
    public void run() {
        try {
            String[] args = command.split(" ");
            String first = args[0];
            Command command = handlers.get(first);
            if (command != null) {
                command.execute(workspace, args);
            } else {
                System.out.println("Unknown command");
            }
        } catch (Exception e) {
            System.err.println("Exception in ProcessCommandTask(" + command + "): " + e);
            e.printStackTrace();
        }
    }
}
