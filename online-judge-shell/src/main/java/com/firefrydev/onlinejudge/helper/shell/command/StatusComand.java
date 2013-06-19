package com.firefrydev.onlinejudge.helper.shell.command;

import com.firefrydev.onlinejudge.helper.workspace.Workspace;

import java.io.IOException;

public class StatusComand implements Command {
    @Override
    public void execute(Workspace workspace, String[] args) throws IOException, InterruptedException {
        if (args.length != 1) {
            System.out.println("Illegal number of arguments: " + (args.length - 1));
            printHelp();
            return;
        }
        System.out.println("Current problem is " + workspace.getProblemId());
    }

    private void printHelp() {
        System.out.println("Command signature: status");
    }
}
