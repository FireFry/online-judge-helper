package com.firefrydev.ojh.core;

public class Commit {
    private final String authorId;
    private final String problemId;
    private final Language language;
    private final String sourceCode;

    public static Builder builder() {
        return new Builder();
    }

    private Commit(String authorId, String problemId, Language language, String sourceCode) {
        if (authorId == null || problemId == null || language == null || sourceCode == null) {
            throw new NullPointerException();
        }
        this.authorId = authorId;
        this.problemId = problemId;
        this.language = language;
        this.sourceCode = sourceCode;
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

    public String getSourceCode() {
        return sourceCode;
    }

    public static class Builder {
        private String authorId;
        private String problemId;
        private Language language;
        private String sourceCode;

        public Builder setAuthorId(String authorId) {
            this.authorId = authorId;
            return this;
        }

        public Builder setProblemId(String problemId) {
            this.problemId = problemId;
            return this;
        }

        public Builder setLanguage(Language language) {
            this.language = language;
            return this;
        }

        public Builder setSourceCode(String sourceCode) {
            this.sourceCode = sourceCode;
            return this;
        }

        public Commit build() {
            return new Commit(authorId, problemId, language, sourceCode);
        }
    }
}
