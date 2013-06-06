package com.firefrydev.onlinejudge.helper.core;

/**
 * @immutable
 */
public final class Commit {
    private final Author authorId;
    private final String problemId;
    private final Language language;
    private final String source;

    public Commit(Author authorId, String problemId, Language language, String source) {
        if (authorId == null || problemId == null || language == null || source == null) {
            throw new NullPointerException();
        }
        this.authorId = authorId;
        this.problemId = problemId;
        this.language = language;
        this.source = source;
    }

    public Author getAuthor() {
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
