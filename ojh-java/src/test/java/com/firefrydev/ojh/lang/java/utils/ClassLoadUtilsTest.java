package com.firefrydev.ojh.lang.java.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ClassLoadUtilsTest {

    public static final String TEST_CLASS_SOURCE =
            "public class TestClass {\n" +
            "    public String key() { return \"asdfxk cvxzc\"; }\n" +
            "}";

    @Test
    public void test1() throws Exception {
        Class testClass = ClassLoadUtils.load("TestClass", TEST_CLASS_SOURCE);
        assertNotNull(testClass);
        assertEquals("asdfxk cvxzc", testClass.getMethod("key").invoke(testClass.newInstance()));
    }

    @Test
    public void test2() throws Exception {
        String sourceCode =
                ("import java.io.*;\n" +
                        "\n" +
                        "public class Problem%id% {\n" +
                        "\n" +
                        "    private final class Solution {\n" +
                        "\n" +
                        "        public void solve() throws Exception {\n" +
                        "\n" +
                        "        }\n" +
                        "\n" +
                        "    }\n" +
                        "\n" +
                        "    //Here goes some helping code:\n" +
                        "    private BufferedReader bufferedReader;private StreamTokenizer in;private PrintWriter out;public Problem%id%() {this(System.in, System.out);}public Problem%id%(InputStream inputStream, OutputStream outputStream) {bufferedReader = new BufferedReader(new InputStreamReader(inputStream));in = new StreamTokenizer(bufferedReader);out = new PrintWriter(new OutputStreamWriter(outputStream));}public static void main(String[] args) throws IOException {new Problem%id%().solve();}public void solve() {try {new Solution().solve();out.flush();} catch (Exception e) {throw new RuntimeException(e);}}public int readInt() throws IOException {return (int) readDouble();}public long readLong() throws IOException {return (long) readDouble();}public float readFloat() throws IOException {return (float) readDouble();}public double readDouble() throws IOException {int nextToken = in.nextToken();if (nextToken == StreamTokenizer.TT_NUMBER) {return in.nval;}throw new IllegalStateException(\"Number expected. Found: \" + nextToken);}public String readWord() throws IOException {int nextToken = in.nextToken();if (nextToken == StreamTokenizer.TT_WORD) {return in.sval;}throw new IllegalStateException(\"Word expected. Found: \" + nextToken);}public String readLine() throws IOException {return bufferedReader.readLine();}\n" +
                        "}").replaceAll("%id%", "1000");
        Class testClass = ClassLoadUtils.load("Problem1000", sourceCode);
        assertNotNull(testClass);
    }

}
