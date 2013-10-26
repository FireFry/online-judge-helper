package com.firefrydev.ojh.local;

import com.firefrydev.ojh.core.ExtendedVerdict;
import com.firefrydev.ojh.core.Language;
import com.firefrydev.ojh.core.Test;
import com.firefrydev.ojh.utils.Callback;

import java.util.Collection;

public interface LanguageProvider {

    Language language();

    String getExtension();

    Source createSourceTemplate(String problemId);

    void verify(String problemId, String source, Collection<Test> tests, Callback<ExtendedVerdict> callback);

}