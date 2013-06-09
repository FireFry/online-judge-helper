package com.firefrydev.onlinejudge.workspace;

import com.firefrydev.onlinejudge.helper.core.Author;
import com.firefrydev.onlinejudge.helper.core.CommitResult;
import com.firefrydev.onlinejudge.helper.core.Language;
import com.firefrydev.onlinejudge.helper.timus.TimusSystem;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class WorkspaceTestTester extends Assert {
    private static final File path = new File("D:\\temp\\timus-workspace");
    private final DirectoryBasedWorkspace directoryBasedWorkspace = new DirectoryBasedWorkspace(new TimusSystem(), path, new Author("42165", "42165LO"));

    @Before
    public void deletePath() throws IOException {
        FileUtils.deleteDirectory(path);
    }

    @Test
    public void testCommitSolution1() throws IOException, InterruptedException {
        directoryBasedWorkspace.switchTo("1000");
        directoryBasedWorkspace.init(Language.JAVA);
        File problemPath = new File(path, "1000");
        File src = new File(problemPath, "Problem1000.java");
        String source = FileUtils.readFileToString(src);
        source = source.replace("//TODO solution", "out.println((int) (readNumber() + readNumber()));");
        FileUtils.forceDelete(src);
        FileUtils.writeStringToFile(src, source);
        TestResult[] results = directoryBasedWorkspace.test();
        assertNotNull(results);
        assertEquals(1, results.length);
        assertEquals(true, results[0].isPassed());
    }

    @Test
    public void testCommitSolution2() throws IOException, InterruptedException {
        directoryBasedWorkspace.switchTo("1000");
        directoryBasedWorkspace.init(Language.JAVA);
        File problemPath = new File(path, "1000");
        File src = new File(problemPath, "Problem1000.java");
        String source = FileUtils.readFileToString(src);
        source = source.replace("//TODO solution", "out.println((int) (readNumber() * readNumber()));");
        FileUtils.forceDelete(src);
        FileUtils.writeStringToFile(src, source);
        TestResult[] results = directoryBasedWorkspace.test();
        assertNotNull(results);
        assertEquals(1, results.length);
        assertEquals(false, results[0].isPassed());
        assertEquals("Outputs are not equals\nExpected: 6\r\nFound:    5\r", results[0].getVerdict());
    }

}
