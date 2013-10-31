package com.firefrydev.ojh.helper.model;

public interface Persistence {

    void save(String key, String value);

    String get(String key);

}
