package com.firefrydev.onlinejudge.helper.shell;

import com.firefrydev.onlinejudge.helper.workspace.Workspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;

public class WaitForInputTask implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(WaitForInputTask.class);
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
            System.out.print(">");
            String command = reader.readLine();
            commandExecutorService.execute(new ProcessCommandTask(workspace, command));
        } catch (Exception e) {
            System.err.println("Exception in WaitForInputTask: " + e);
            logger.error("Exception in WaitForInputTask", e);
        } finally {
            executorService.execute(this);
        }
    }
}
