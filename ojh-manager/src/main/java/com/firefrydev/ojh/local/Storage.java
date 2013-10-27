package com.firefrydev.ojh.local;

import com.firefrydev.ojh.core.Source;
import com.firefrydev.ojh.core.Test;
import com.firefrydev.ojh.utils.Callback;

import java.util.List;

public interface Storage {

    void saveSource(String problemId, Source sourceTemplate, Runnable callback);

    void getSource(String problemId, String fileName, Callback<Source> callback);

    void saveTests(String id, List<Test> tests, Runnable callback);

    void getTests(String problemId, Callback<List<Test>> callback);

}
