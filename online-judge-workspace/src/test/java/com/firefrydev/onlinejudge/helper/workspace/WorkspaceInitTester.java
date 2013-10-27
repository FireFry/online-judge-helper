package com.firefrydev.onlinejudge.helper.workspace;

import com.firefrydev.onlinejudge.helper.core.Author;
import com.firefrydev.onlinejudge.helper.core.Language;
import com.firefrydev.onlinejudge.helper.timus.TimusSystem;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class WorkspaceInitTester extends Assert {
    private static final File path = new File("D:\\temp\\timus-workspace");
    private final DirectoryBasedWorkspace directoryBasedWorkspace = new DirectoryBasedWorkspace(new TimusSystem(), path, new Author("42165", "42165LO"));

    @Before
    public void deletePath() throws IOException {
        FileUtils.deleteDirectory(path);
    }

    @Test
    public void testInitProblem1() throws IOException {
        directoryBasedWorkspace.switchTo("1000");
        directoryBasedWorkspace.init(Language.JAVA);
        assertTrue(path.exists() && path.isDirectory());
        File problemPath = new File(path, "1000");
        File src = new File(problemPath, "Problem1000.java");
        assertTrue(src.exists());
        String source = FileUtils.readFileToString(src);
        String expectedSource =
                "import java.io.*;\n" +
                "\n" +
                "public class Problem1000 implements Runnable {\n" +
                "\tprivate BufferedReader bufferedReader;\n" +
                "\tprivate StreamTokenizer in;\n" +
                "\tprivate PrintWriter out;\n" +
                "\n" +
                "\tpublic Problem1000() {\n" +
                "\t\tthis(System.in, System.out);\n" +
                "\t}\n" +
                "\n" +
                "\tpublic Problem1000(InputStream inputStream, OutputStream outputStream) {\n" +
                "\t\tbufferedReader = new BufferedReader(new InputStreamReader(inputStream));\n" +
                "\t\tin = new StreamTokenizer(bufferedReader);\n" +
                "\t\tout = new PrintWriter(new OutputStreamWriter(outputStream));\n" +
                "\t}\n" +
                "\n" +
                "\tpublic static void main(String[] args) throws IOException {\n" +
                "\t\tnew Problem1000().run();\n" +
                "\t}\n" +
                "\n" +
                "\tpublic void run() {\n" +
                "\t\ttry {\n" +
                "\t\t\tsolve();\n" +
                "\t\t\tout.flush();\n" +
                "\t\t} catch (Exception e) {\n" +
                "\t\t\tthrow new RuntimeException(e);\n" +
                "\t\t}\n" +
                "\t}\n" +
                "\n" +
                "\tprivate double readNumber() throws IOException {\n" +
                "\t\tint nextToken = in.nextToken();\n" +
                "\t\tif (nextToken == StreamTokenizer.TT_NUMBER) {\n" +
                "\t\t\treturn in.nval;\n" +
                "\t\t}\n" +
                "\t\tthrow new IllegalStateException(\"Number expected. Found: \" + nextToken);\n" +
                "\t}\n" +
                "\n" +
                "\tprivate String readWord() throws IOException {\n" +
                "\t\tint nextToken = in.nextToken();\n" +
                "\t\tif (nextToken == StreamTokenizer.TT_WORD) {\n" +
                "\t\t\treturn in.sval;\n" +
                "\t\t}\n" +
                "\t\tthrow new IllegalStateException(\"Word expected. Found: \" + nextToken);\n" +
                "\t}\n" +
                "\n" +
                "\t//TODO global variables\n" +
                "\n" +
                "\tprivate void solve() throws Exception {\n" +
                "\t\t//TODO solution\n" +
                "\t}\n" +
                "}\n";
        assertEquals(expectedSource, source);

        File test0 = new File(new File(problemPath, "tests"), "0");
        String sampleInput = FileUtils.readFileToString(new File(test0, "input.txt"));
        assertEquals("1 5\r\n", sampleInput);

        String sampleOutput = FileUtils.readFileToString(new File(test0, "output.txt"));
        assertEquals("6\r\n", sampleOutput);

        File settings = new File(new File(problemPath, ".ojh"), "settings.txt");
        assertTrue(settings.exists());
        assertEquals("language = JAVA\n", FileUtils.readFileToString(settings));
    }

}
