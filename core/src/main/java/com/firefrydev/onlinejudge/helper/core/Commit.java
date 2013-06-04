package com.firefrydev.onlinejudge.helper.core;

/**
 * @immutable
 */
public final class Commit {
    private final String authorId;
    private final String problemId;
    private final Language language;
    private final String source;

    public Commit(String authorId, String problemId, Language language, String source) {
        if (authorId == null || problemId == null || language == null || source == null) {
            throw new NullPointerException();
        }
        this.authorId = authorId;
        this.problemId = problemId;
        this.language = language;
        this.source = source;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getProblemId() {
        return problemId;
    }

    public Language getLanguage() {
        return language;
    }

    public String getSource() {
        return source;
    }
}
