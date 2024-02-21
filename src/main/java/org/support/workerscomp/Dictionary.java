package org.support.workerscomp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Dictionary {

    private static Dictionary instance = null;
    private static List<String> words = new ArrayList<>();

    static {
        try {
            InputStream inputStream = StartClass.class.getResourceAsStream("/words");
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            for (String line; (line = reader.readLine()) != null;) {
                words.add(line.toUpperCase());
            }
        } catch (Exception e) {

        }
    }

    private Dictionary() {

    }

    public static Dictionary getDictionary() {
        if (null == instance) {
            instance = new Dictionary();
        }
        return instance;
    }

    public static List<String> getWords() {
        return words;
    }


}
