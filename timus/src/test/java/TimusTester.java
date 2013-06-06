import com.firefrydev.onlinejudge.helper.core.*;
import com.firefrydev.onlinejudge.helper.timus.TimusSystem;
import org.junit.*;

import java.util.List;

public class TimusTester extends Assert {
    private OnlineJudgeSystem onlineJudgeSystem;

    @Before
    public void setUp() {
        onlineJudgeSystem = new TimusSystem();
    }

    @After
    public void tearDown() {
        onlineJudgeSystem = null;
    }

    @Test
    public void testLoadProblem1() throws Exception {
        Problem problem = onlineJudgeSystem.getProblem("1000");

        assertTrue(problem != null);

        assertTrue("1000".equals(problem.getId()));

        assertTrue(problem.getDescription().contains("Вычислите a+b"));

        List<SampleTest> sampleTests = problem.getSampleTests();
        assertTrue(sampleTests.size() == 1);
        SampleTest test = sampleTests.get(0);
        assertEquals(test.getInput(), "1 5");
        assertEquals(test.getOutput(), "6");
    }

    @Test
    public void testLoadProblem2() throws Exception {
        Problem problem = onlineJudgeSystem.getProblem("1688");

        assertTrue(problem != null);

        assertEquals("1688", problem.getId());

        assertTrue(problem.getDescription().contains("Вадик, Team.GOV!"));

        List<SampleTest> sampleTests = problem.getSampleTests();
        assertTrue(sampleTests.size() == 2);

        SampleTest test0 = sampleTests.get(0);
        assertEquals(test0.getInput(), "1666 5\r\n" +
                "2000\r\n" +
                "1024\r\n" +
                "900\r\n" +
                "1156\r\n" +
                "1200");
        assertEquals(test0.getOutput(), "Free after 4 times.");

        SampleTest test1 = sampleTests.get(1);
        assertEquals(test1.getInput(), "3000 5\r\n" +
                "2000\r\n" +
                "1024\r\n" +
                "900\r\n" +
                "1156\r\n" +
                "1200");
        assertEquals(test1.getOutput(), "Team.GOV!");
    }

    @Test
    @Ignore
    public void testCommit() throws Exception {
        String src =
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
                "}";
        String problem = "1000";
        Author author = new Author("42165", "42165LO");
        CommitResult result = onlineJudgeSystem.perform(new Commit(author, problem, Language.JAVA, src));
        assertTrue(result.isAccepted());
        assertEquals("Accepted", result.getVerdict());
    }
}
