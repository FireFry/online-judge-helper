package com.firefrydev.onlinejudge.helper.main;

import com.firefrydev.onlinejudge.helper.workspace.Workspace;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;

public class WaitForInputTask implements Runnable {
    private final ExecutorService executorService;
    private final ExecutorService commandExecutorService;
    private final Workspace workspace;
    private final BufferedReader reader;

    public WaitForInputTask(ExecutorService executorService, ExecutorService commandExecutorService, Workspace workspace) {
        this.executorService = executorService;
        this.commandExecutorService = commandExecutorService;
        this.workspace = workspace;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        try {
            while (true) {
                try {
                    System.out.print("");
                    String command = reader.readLine();
                    commandExecutorService.execute(new ProcessCommandTask(workspace, command));
                } catch (Exception e) {
                    System.err.println("Exception int WaitForInputTask: " + e);
                    e.printStackTrace();
                }
            }
        } finally {
            executorService.execute(this);
        }
    }
}
