package com.firefrydev.onlinejudge.helper.main.command;

import com.firefrydev.onlinejudge.helper.core.Language;
import com.firefrydev.onlinejudge.helper.workspace.Workspace;

import java.io.IOException;

public class InitCommand implements Command {
    @Override
    public void execute(Workspace workspace, String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Illegal number of arguments: " + (args.length - 1));
            printHelp();
            return;
        }
        workspace.init(Language.valueOf(args[1]));
    }

    private void printHelp() {
        System.out.println("Command signature: init <language>" +
                "\n\tlanguage: one of supported languages");
    }
}
