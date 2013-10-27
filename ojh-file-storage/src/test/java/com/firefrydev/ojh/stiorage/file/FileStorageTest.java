package com.firefrydev.ojh.stiorage.file;

import com.firefrydev.ojh.core.Source;
import com.firefrydev.ojh.core.Test;
import com.firefrydev.ojh.local.Storage;
import com.firefrydev.ojh.templates.Templates;
import com.firefrydev.ojh.utils.Callback;
import org.junit.Assert;

import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

public class FileStorageTest {

    private final Storage fileStorage = new FileStorage(new File(new File(System.getProperty("user.home"), ".ojh"), ".temp"));
    private volatile Source source;
    private volatile List<Test> tests;

    @org.junit.Test
    public void test() throws InterruptedException {
        final CountDownLatch saved = new CountDownLatch(2);
        fileStorage.saveSource("1000", Templates.PROBLEM_1000, new Runnable() {
            @Override
            public void run() {
                saved.countDown();
            }
        });
        fileStorage.saveTests("1000", Templates.PROBLEM_1000_TESTS, new Runnable() {
            @Override
            public void run() {
                saved.countDown();
            }
        });
        saved.await();

        final CountDownLatch loaded = new CountDownLatch(2);
        fileStorage.getSource("1000", Templates.PROBLEM_1000.getFileName(), new Callback<Source>() {
            @Override
            public void call(Source data) {
                source = data;
                loaded.countDown();
            }
        });
        fileStorage.getTests("1000", new Callback<List<Test>>() {
            @Override
            public void call(List<Test> data) {
                tests = data;
                loaded.countDown();
            }
        });
        loaded.await();
        assertNotSame(Templates.PROBLEM_1000, source);
        assertEquals(Templates.PROBLEM_1000, source);
        assertNotSame(Templates.PROBLEM_1000_TESTS, tests);
        assertEquals(Templates.PROBLEM_1000_TESTS, tests);
    }

}
