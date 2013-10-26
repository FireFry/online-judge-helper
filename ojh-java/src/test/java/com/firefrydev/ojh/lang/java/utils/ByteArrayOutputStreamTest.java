package com.firefrydev.ojh.lang.java.utils;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class ByteArrayOutputStreamTest {

    @Test
    public void test1() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        TestWriter.write(outputStream);
        assertEquals("Hello world!", outputStream.toString());
    }

    private static class TestWriter {
        public static void write(OutputStream outputStream) {
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream));
            writer.print("Hello world!");
            writer.flush();
        }
    }

    @Test
    public void test2() throws Exception {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ClassLoadUtils.load("TestWriter", "import java.io.*;\n" +
                "public class TestWriter {\n" +
                "        public static void write(OutputStream outputStream) {\n" +
                "            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream));\n" +
                "            writer.print(\"Hello world!\");\n" +
                "            writer.flush();\n" +
                "        }\n" +
                "    }").getMethod("write", OutputStream.class).invoke(null, outputStream);
        assertEquals("Hello world!", outputStream.toString());
    }

    @Test
    public void test3() throws Exception {
        String sourceCode = ("import java.io.*;\n" +
                "\n" +
                "public class Problem%id% {\n" +
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
                "    private final BufferedReader bufferedReader;private final StreamTokenizer in;private final PrintWriter out;public Problem%id%() {this(System.in, System.out);}public Problem%id%(InputStream inputStream, OutputStream outputStream) {bufferedReader = new BufferedReader(new InputStreamReader(inputStream));in = new StreamTokenizer(bufferedReader);out = new PrintWriter(new OutputStreamWriter(outputStream));}public static void main(String[] args) throws IOException {new Problem%id%().solve();}public void solve() {try {new Solution().solve();out.flush();} catch (Exception e) {throw new RuntimeException(e);}}public int readInt() throws IOException {return (int) readDouble();}public long readLong() throws IOException {return (long) readDouble();}public float readFloat() throws IOException {return (float) readDouble();}public double readDouble() throws IOException {int nextToken = in.nextToken();if (nextToken == StreamTokenizer.TT_NUMBER) {return in.nval;}throw new IllegalStateException(\"Number expected. Found: \" + nextToken);}public String readWord() throws IOException {int nextToken = in.nextToken();if (nextToken == StreamTokenizer.TT_WORD) {return in.sval;}throw new IllegalStateException(\"Word expected. Found: \" + nextToken);}public String readLine() throws IOException {return bufferedReader.readLine();}\n" +
                "}").replaceAll("%id%", "1000");

        Class problem1000 = ClassLoadUtils.load("Problem1000", sourceCode);

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Object obj = problem1000.getConstructor(InputStream.class, OutputStream.class).newInstance(new ByteArrayInputStream("1 5\n".getBytes()), outputStream);
        problem1000.getMethod("solve").invoke(obj);
        assertEquals("6" + System.getProperty("line.separator"), outputStream.toString());
    }

}
