package com.firefrydev.onlinejudge.helper.shell.command;

import com.firefrydev.onlinejudge.helper.core.CommitResult;
import com.firefrydev.onlinejudge.helper.workspace.Workspace;

import java.io.IOException;

public class CommitComand implements Command {
    @Override
    public void execute(Workspace workspace, String[] args) throws IOException, InterruptedException {
        if (args.length != 1) {
            System.out.println("Illegal number of arguments: " + (args.length - 1));
            printHelp();
            return;
        }
        CommitResult result = workspace.commit();
        if (result.isAccepted()) {
            System.out.println("Congratulation, your solution has been accepted!");
        } else {
            System.out.println("Your solution has been rejected...");
            System.out.println("Verdict: " + result.getVerdict());
        }
    }

    private void printHelp() {
        System.out.println("Command signature: commit");
    }
}
