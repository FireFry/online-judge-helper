package com.firefrydev.onlinejudge.helper.core;

public final class Author {
    private final String id;
    private final String password;

    public Author(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
