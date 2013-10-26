package com.firefrydev.ojh.core;

public class Verdict {
    private final boolean accepted;
    private final String message;
    private final Exception failCause;

    public static Verdict accepted() {
        return new Verdict(true, "Accepted", null);
    }

    public static Verdict rejected(String message) {
        return rejected(message, null);
    }

    public static Verdict rejected(String message, Exception failCause) {
        return new Verdict(false, message, failCause);
    }

    private Verdict(boolean accepted, String message, Exception failCause) {
        this.accepted = accepted;
        this.message = message;
        this.failCause = failCause;
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
