package com.firefrydev.ojh.timus;

import com.firefrydev.ojh.core.*;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

public class TimusJudgeSystemTest {
    public static final int TIMEOUT = 5000;
    public static final String AUTHOR_ID = null; //TODO set correct author id to test validate method

    private final OnlineJudgeSystem onlineJudgeSystem = new TimusSystemProvider().create();
    private Problem problem;
    private Verdict verdict;

    @Before
    public void setUp() {
        problem = null;
    }

    @Test(timeout = TIMEOUT)
    public void testLoadProblem() throws InterruptedException {
        final CountDownLatch loaded = new CountDownLatch(1);
        onlineJudgeSystem.getProblemService().loadProblem("1000", new ProblemService.Callback() {
            @Override
            public void onProblemLoaded(Problem problem) {
                TimusJudgeSystemTest.this.problem = problem;
                loaded.countDown();
            }
        });
        loaded.await();
        assertNotNull(problem);
        assertEquals(1, problem.getTests().size());
        assertEquals("1 5\n", problem.getTests().get(0).getInput());
        assertEquals("6\n", problem.getTests().get(0).getOutput());
    }

    @Test(timeout = TIMEOUT)
    public void commitTest() throws InterruptedException {
        assertNotNull(AUTHOR_ID);
        final CountDownLatch verdictReady = new CountDownLatch(1);
        Commit commit = Commit.builder()
                .setAuthorId(AUTHOR_ID)
                .setLanguage(DefaultLanguages.JAVA)
                .setProblemId("1000")
                .setSourceCode(
                        "import java.util.*;\n" +
                        "\n" +
                        "class YouCanUseSuchClasses {}\n" +
                        "public class Sum2\n" +
                        "{\n" +
                        "   class AndSuchClassesToo {}\n" +
                        "   public static void main(String[] args)\n" +
                        "   {\n" +
                        "      Scanner in = new Scanner(System.in);\n" +
                        "      System.out.println(in.nextInt() + in.nextInt());\n" +
                        "   }\n" +
                        "}")
                .build();
        onlineJudgeSystem.getVerificationService().verify(commit, new VerificationService.Callback() {
            @Override
            public void onVerdictReady(Verdict verdict) {
                TimusJudgeSystemTest.this.verdict = verdict;
                verdictReady.countDown();
            }
        });
        verdictReady.await();
        int i = 1;
    }
}
