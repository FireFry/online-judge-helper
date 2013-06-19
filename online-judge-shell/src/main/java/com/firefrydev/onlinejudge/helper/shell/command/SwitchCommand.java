package com.firefrydev.onlinejudge.helper.shell.command;

import com.firefrydev.onlinejudge.helper.workspace.Workspace;

import java.io.IOException;

public class SwitchCommand implements Command {
    @Override
    public void execute(Workspace workspace, String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Illegal number of arguments: " + (args.length - 1));
            printHelp();
            return;
        }
        workspace.switchTo(args[1]);
        System.out.println("Switched current problem to " + args[1]);
    }

    private void printHelp() {
        System.out.println("Command signature: switch <problemId>" +
                "\n\tproblemId: the online judge system problem id");
    }
}
