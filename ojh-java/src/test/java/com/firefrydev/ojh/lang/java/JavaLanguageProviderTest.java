package com.firefrydev.ojh.lang.java;

import com.firefrydev.ojh.core.ExtendedVerdict;
import com.firefrydev.ojh.templates.Templates;
import com.firefrydev.ojh.utils.Callback;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

public class JavaLanguageProviderTest {

    private volatile ExtendedVerdict verdict;

    @org.junit.Test
    public void test1() throws InterruptedException {
        JavaLanguageProvider javaLanguageProvider = new JavaLanguageProvider();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        javaLanguageProvider.verify(Templates.PROBLEM_1000, Templates.PROBLEM_1000_TESTS, new Callback<ExtendedVerdict>() {
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

    @org.junit.Test
    public void test2() throws InterruptedException {
        JavaLanguageProvider javaLanguageProvider = new JavaLanguageProvider();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        javaLanguageProvider.verify(Templates.PROBLEM_1000_WRONG, Templates.PROBLEM_1000_TESTS, new Callback<ExtendedVerdict>() {
            @Override
            public void call(ExtendedVerdict data) {
                verdict = data;
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        assertNotNull(verdict);
        assertFalse(verdict.getVerdict().isAccepted());
        assertEquals(1, verdict.getFailedTests().size());
        assertSame(Templates.PROBLEM_1000_TESTS.get(1), verdict.getFailedTests().get(0).getTest());
        assertEquals("0" + Templates.LINE_SEPARATOR, verdict.getFailedTests().get(0).getOutput());
    }

}
