package com.firefrydev.onlinejudge.helper.core;

public final class LanguageSettings {
    private final String id;
    private final String toString;
    private final String extension;

    public LanguageSettings(String id, String toString, String extension) {
        this.id = id;
        this.toString = toString;
        this.extension = extension;
    }

    public String getId() {
        return id;
    }

    public String toString() {
        return toString;
    }

    public String getExtension() {
        return extension;
    }
}
