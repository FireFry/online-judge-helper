package com.firefrydev.ojh.lang.java.utils;

import com.firefrydev.ojh.core.Source;
import com.firefrydev.ojh.templates.Templates;
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
        ClassLoadUtils.load(new Source("TestWriter.java", "import java.io.*;\n" +
                "public class TestWriter {\n" +
                "        public static void write(OutputStream outputStream) {\n" +
                "            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream));\n" +
                "            writer.print(\"Hello world!\");\n" +
                "            writer.flush();\n" +
                "        }\n" +
                "    }")).getMethod("write", OutputStream.class).invoke(null, outputStream);
        assertEquals("Hello world!", outputStream.toString());
    }

    @Test
    public void test3() throws Exception {
        Class problem1000 = ClassLoadUtils.load(Templates.PROBLEM_1000);

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Object obj = problem1000.getConstructor(InputStream.class, OutputStream.class).newInstance(new ByteArrayInputStream(("1 5" + Templates.LINE_SEPARATOR).getBytes()), outputStream);
        problem1000.getMethod("solve").invoke(obj);
        assertEquals("6" + Templates.LINE_SEPARATOR, outputStream.toString());
    }

}
