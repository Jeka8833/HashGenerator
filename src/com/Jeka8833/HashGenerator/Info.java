package com.Jeka8833.HashGenerator;

import java.util.Map;

public class Info {

    private final String url;
    private final Map<String, String> hash;

    public Info(String url, Map<String, String> hash) {
        this.url = url;
        this.hash = hash;
    }
}
