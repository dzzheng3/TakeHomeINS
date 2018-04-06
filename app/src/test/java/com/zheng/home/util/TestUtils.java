package com.zheng.home.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestUtils {


    public String loadJson(String path) {
        String json = getFileString(path);
        return json;
    }

    private String getFileString(String path) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream(path)));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read from resource at: " + path);
        }
    }
}
