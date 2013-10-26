package com.firefrydev.ojh.core;

public class Verdict {
    private final boolean accepted;
    private final String message;

    public static Verdict accepted() {
        return new Verdict(true, "Accepted");
    }

    public static Verdict rejected(String message) {
        return new Verdict(false, message);
    }

    private Verdict(boolean accepted, String message) {
        this.accepted = accepted;
        this.message = message;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public String getMessage() {
        return message;
    }
}
