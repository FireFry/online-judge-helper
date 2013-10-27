package com.firefrydev.ojh.lang.java.utils;

import com.firefrydev.ojh.core.Source;
import com.firefrydev.ojh.templates.Templates;
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
        Class testClass = ClassLoadUtils.load(new Source("TestClass.java", TEST_CLASS_SOURCE));
        assertNotNull(testClass);
        assertEquals("asdfxk cvxzc", testClass.getMethod("key").invoke(testClass.newInstance()));
    }

    @Test
    public void test2() throws Exception {
        Class testClass = ClassLoadUtils.load(Templates.PROBLEM_1000);
        assertNotNull(testClass);
    }

}
