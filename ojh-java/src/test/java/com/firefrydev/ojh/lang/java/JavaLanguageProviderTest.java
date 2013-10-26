package com.firefrydev.ojh.lang.java;

import com.firefrydev.ojh.core.ExtendedVerdict;
import com.firefrydev.ojh.core.Test;
import com.firefrydev.ojh.utils.Callback;
import org.junit.Assert;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

public class JavaLanguageProviderTest {

    private volatile ExtendedVerdict verdict;

    @org.junit.Test
    public void test() throws InterruptedException {
        JavaLanguageProvider javaLanguageProvider = new JavaLanguageProvider();
        String problemId = "1000";
        String sourceCode =
                ("import java.io.*;\n" +
                        "\n" +
                        "public class Problem%id% {\n" +
                        "\n" +
                        "    private final class Solution {\n" +
                        "\n" +
                        "        public void solve() throws Exception {\n" +
                        "            out.println(readLong() + readLong());out.flush();\n" +
                        "        }\n" +
                        "\n" +
                        "    }\n" +
                        "\n" +
                        "    //Here goes some helping code:\n" +
                        "    private BufferedReader bufferedReader;private StreamTokenizer in;private PrintWriter out;public Problem%id%() {this(System.in, System.out);}public Problem%id%(InputStream inputStream, OutputStream outputStream) {bufferedReader = new BufferedReader(new InputStreamReader(inputStream));in = new StreamTokenizer(bufferedReader);out = new PrintWriter(new OutputStreamWriter(outputStream));}public static void main(String[] args) throws IOException {new Problem%id%().solve();}public void solve() {try {new Solution().solve();out.flush();} catch (Exception e) {throw new RuntimeException(e);}}public int readInt() throws IOException {return (int) readDouble();}public long readLong() throws IOException {return (long) readDouble();}public float readFloat() throws IOException {return (float) readDouble();}public double readDouble() throws IOException {int nextToken = in.nextToken();if (nextToken == StreamTokenizer.TT_NUMBER) {return in.nval;}throw new IllegalStateException(\"Number expected. Found: \" + nextToken);}public String readWord() throws IOException {int nextToken = in.nextToken();if (nextToken == StreamTokenizer.TT_WORD) {return in.sval;}throw new IllegalStateException(\"Word expected. Found: \" + nextToken);}public String readLine() throws IOException {return bufferedReader.readLine();}\n" +
                        "}").replaceAll("%id%", "1000");
        List<Test> tests = new LinkedList<Test>();
        String lineSeparator = System.getProperty("line.separator");
        tests.add(new Test("1 5" + lineSeparator, "6" + lineSeparator));
        tests.add(new Test("5 3" + lineSeparator, "8" + lineSeparator));
        tests.add(new Test("8 0" + lineSeparator, "8" + lineSeparator));
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        javaLanguageProvider.verify(problemId, sourceCode, tests, new Callback<ExtendedVerdict>() {
            @Override
            public void call(ExtendedVerdict data) {
                verdict = data;
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        assertNotNull(verdict);
        assertTrue(verdict.getVerdict().isAccepted());
    }

}
