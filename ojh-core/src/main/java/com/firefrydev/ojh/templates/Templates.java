package com.firefrydev.ojh.templates;

import com.firefrydev.ojh.core.Source;
import com.firefrydev.ojh.core.Test;

import java.util.Arrays;
import java.util.List;

public class Templates {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static final Source PROBLEM_1000 = new Source("Problem1000.java",
            "import java.io.*;\n" +
            "\n" +
            "public class Problem1000 {\n" +
            "\n" +
            "    private final class Solution {\n" +
            "\n" +
            "        public void solve() throws Exception {\n" +
            "            out.println(readLong() + readLong());\n" +
            "        }\n" +
            "\n" +
            "    }\n" +
            "\n" +
            "    //Here goes some helping code:\n" +
            "    private BufferedReader bufferedReader;private StreamTokenizer in;private PrintWriter out;public Problem1000() {this(System.in, System.out);}public Problem1000(InputStream inputStream, OutputStream outputStream) {bufferedReader = new BufferedReader(new InputStreamReader(inputStream));in = new StreamTokenizer(bufferedReader);out = new PrintWriter(new OutputStreamWriter(outputStream));}public static void main(String[] args) throws IOException {new Problem1000().solve();}public void solve() {try {new Solution().solve();out.flush();} catch (Exception e) {throw new RuntimeException(e);}}public int readInt() throws IOException {return (int) readDouble();}public long readLong() throws IOException {return (long) readDouble();}public float readFloat() throws IOException {return (float) readDouble();}public double readDouble() throws IOException {int nextToken = in.nextToken();if (nextToken == StreamTokenizer.TT_NUMBER) {return in.nval;}throw new IllegalStateException(\"Number expected. Found: \" + nextToken);}public String readWord() throws IOException {int nextToken = in.nextToken();if (nextToken == StreamTokenizer.TT_WORD) {return in.sval;}throw new IllegalStateException(\"Word expected. Found: \" + nextToken);}public String readLine() throws IOException {return bufferedReader.readLine();}\n" +
            "}");

    public static final Source PROBLEM_1000_WRONG = new Source("Problem1000.java",
            "import java.io.*;\n" +
                    "\n" +
                    "public class Problem1000 {\n" +
                    "\n" +
                    "    private final class Solution {\n" +
                    "\n" +
                    "        public void solve() throws Exception {\n" +
                    "            switch (readInt()) {\n" +
                    "                case 1: out.println(6); return;\n" +
                    "                case -4: out.println(-3); return;\n" +
                    "                default: out.println(0); return;\n" +
                    "            }\n" +
                    "        }\n" +
                    "\n" +
                    "    }\n" +
                    "\n" +
                    "    //Here goes some helping code:\n" +
                    "    private BufferedReader bufferedReader;private StreamTokenizer in;private PrintWriter out;public Problem1000() {this(System.in, System.out);}public Problem1000(InputStream inputStream, OutputStream outputStream) {bufferedReader = new BufferedReader(new InputStreamReader(inputStream));in = new StreamTokenizer(bufferedReader);out = new PrintWriter(new OutputStreamWriter(outputStream));}public static void main(String[] args) throws IOException {new Problem1000().solve();}public void solve() {try {new Solution().solve();out.flush();} catch (Exception e) {throw new RuntimeException(e);}}public int readInt() throws IOException {return (int) readDouble();}public long readLong() throws IOException {return (long) readDouble();}public float readFloat() throws IOException {return (float) readDouble();}public double readDouble() throws IOException {int nextToken = in.nextToken();if (nextToken == StreamTokenizer.TT_NUMBER) {return in.nval;}throw new IllegalStateException(\"Number expected. Found: \" + nextToken);}public String readWord() throws IOException {int nextToken = in.nextToken();if (nextToken == StreamTokenizer.TT_WORD) {return in.sval;}throw new IllegalStateException(\"Word expected. Found: \" + nextToken);}public String readLine() throws IOException {return bufferedReader.readLine();}\n" +
                    "}");

    public static final List<Test> PROBLEM_1000_TESTS = Arrays.asList(
            new Test("1 5" + Templates.LINE_SEPARATOR, "6" + Templates.LINE_SEPARATOR),
            new Test("4 -2" + Templates.LINE_SEPARATOR, "2" + Templates.LINE_SEPARATOR),
            new Test("100 -100" + Templates.LINE_SEPARATOR, "0" + Templates.LINE_SEPARATOR),
            new Test("-4 1" + Templates.LINE_SEPARATOR, "-3" + Templates.LINE_SEPARATOR)
    );

    private Templates() {}
}
