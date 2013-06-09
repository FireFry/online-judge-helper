package com.firefrydev.onlinejudge.helper.main.command;

import com.firefrydev.onlinejudge.helper.workspace.TestResult;
import com.firefrydev.onlinejudge.helper.workspace.Workspace;

import java.io.IOException;

public class TestCommand implements Command {
    @Override
    public void execute(Workspace workspace, String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Illegal number of arguments: " + (args.length - 1));
            printHelp();
            return;
        }
        TestResult[] results = workspace.test();
        boolean hasFailed = false;
        for (TestResult testResult : results) {
            if (!testResult.isPassed()) {
                System.out.println("Test failed: " + testResult.getId());
                System.out.println(testResult.getVerdict());
                System.out.println();
                hasFailed = true;
            }
        }
        if (!hasFailed) {
            System.out.println("All " + results.length + " tests passed");
        }
    }

    private void printHelp() {
        System.out.println("Command signature: test");
    }
}
