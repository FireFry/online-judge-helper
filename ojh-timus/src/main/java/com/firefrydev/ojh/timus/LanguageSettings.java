package com.firefrydev.ojh.timus;

import com.firefrydev.ojh.core.DefaultLanguages;
import com.firefrydev.ojh.core.Language;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LanguageSettings {

    public static final LanguageSettings JAVA_LANGUAGE_SETTINGS = new LanguageSettings(12);

    private static final Map<Language, LanguageSettings> LANGUAGE_SETTINGS;
    static {
        HashMap<Language, LanguageSettings> map = new HashMap<Language, LanguageSettings>();
        map.put(DefaultLanguages.JAVA, JAVA_LANGUAGE_SETTINGS);
        LANGUAGE_SETTINGS = Collections.unmodifiableMap(map);
    }

    private final int id;

    public static LanguageSettings valueOf(Language language) {
        LanguageSettings settings = LANGUAGE_SETTINGS.get(language);
        if (settings != null) {
            return settings;
        }
        throw new IllegalArgumentException();
    }

    private LanguageSettings(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
