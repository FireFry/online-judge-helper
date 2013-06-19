package com.firefrydev.onlinejudge.helper.shell.command;

import com.firefrydev.onlinejudge.helper.workspace.TestResult;
import com.firefrydev.onlinejudge.helper.workspace.Workspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TestCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(TestCommand.class);

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
                if (testResult.getException() != null) {
                    logger.error("Test failed: {}", testResult.getId(), testResult.getException());
                }
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
