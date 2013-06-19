package com.firefrydev.onlinejudge.helper.shell.command;

import com.firefrydev.onlinejudge.helper.workspace.Workspace;

import java.io.IOException;

public interface Command {
    void execute(Workspace workspace, String[] args) throws IOException, InterruptedException;
}
