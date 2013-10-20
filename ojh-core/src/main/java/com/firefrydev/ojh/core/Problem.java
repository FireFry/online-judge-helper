package com.firefrydev.ojh.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Problem {
    private final String id;
    private final String description;
    private final List<Test> tests;

    public static Builder builder() {
        return new Builder();
    }

    private Problem(String id, String description, List<Test> tests) {
        if (id == null || description == null || tests == null) {
            throw new NullPointerException();
        }
        this.id = id;
        this.description = description;
        this.tests = Collections.unmodifiableList(new ArrayList<Test>(tests));
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public List<Test> getTests() {
        return tests;
    }

    public static class Builder {
        private String id;
        private String description;
        private final List<Test> tests = new LinkedList<Test>();

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder addTest(Test test) {
            tests.add(test);
            return this;
        }

        public Builder removeAllTests() {
            tests.clear();
            return this;
        }

        public Problem build() {
            return new Problem(id, description, tests);
        }
    }
}
