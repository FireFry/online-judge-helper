package com.firefrydev.onlinejudge.helper.shell.command;

import com.firefrydev.onlinejudge.helper.core.Language;
import com.firefrydev.onlinejudge.helper.workspace.Workspace;

import java.io.IOException;

public class InitCommand implements Command {
    @Override
    public void execute(Workspace workspace, String[] args) throws IOException {
        Language language = workspace.getProvider().getDefaultLanguage();
        String problemId = workspace.getProblemId();
        int i = 1;
        while (i < args.length) {
            if ("-p".equals(args[i])) {
                i++;
                if (i < args.length) {
                    problemId = args[i];
                    i++;
                } else {
                    System.out.println("Token expected, found: null");
                    printHelp();
                    return;
                }
            } else if ("-l".equals(args[i])) {
                i++;
                if (i < args.length) {
                    language = Language.valueOf(args[i].toLowerCase());
                    i++;
                } else {
                    System.out.println("Token expected, found: null");
                    printHelp();
                    return;
                }
            } else {
                System.out.println("Unknown token: " + args[i]);
                printHelp();
                return;
            }
        }
        workspace.switchTo(problemId);
        workspace.init(language);
        System.out.println("Problem " + problemId + " has been initialized successfully for language " + language);
    }

    private void printHelp() {
        System.out.println("Command signature: init [-p problem] [-l language]" +
                "\n\tproblem: problem to switch to" +
                "\n\tlanguage: one of supported languages");
    }
}
