package com.firefrydev.ojh.core;

public class Verdict {
    private final String problemId;
    private final boolean accepted;
    private final String message;
    private final Exception failCause;

    public static Verdict accepted(String problemId) {
        return new Verdict(true, problemId, "Accepted", null);
    }

    public static Verdict rejected(String problemId, String message) {
        return rejected(problemId, message, null);
    }

    public static Verdict rejected(String problemId, String message, Exception failCause) {
        return new Verdict(false, problemId, message, failCause);
    }

    private Verdict(boolean accepted, String problemId, String message, Exception failCause) {
        if (problemId == null) {
            throw new NullPointerException();
        }
        this.problemId = problemId;
        this.accepted = accepted;
        this.message = message;
        this.failCause = failCause;
    }

    public String getProblemId() {
        return problemId;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public String getMessage() {
        return message;
    }

    public Exception getFailCause() {
        return failCause;
    }
}
