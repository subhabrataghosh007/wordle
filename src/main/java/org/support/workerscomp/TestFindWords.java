package org.support.workerscomp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestFindWords {

    private static List<String> words = new ArrayList<>();

    public static void main(String[] args) {

        try {
            InputStream inputStream = StartClass.class.getResourceAsStream("/words");
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            for (String line; (line = reader.readLine()) != null;) {
                words.add(line.toUpperCase());
            }
        } catch (Exception e) {

        }


        int targetLength = 4;
        int check = 1;
        String wordToBeCompared = "A";

        while (true) {
//            System.out.println("Check--" + check++);


            List<String> aggregatedWords = new ArrayList<>();
            Set<Character> uniqueWords = new HashSet<>();
            int count = 0;
            for (String word : words) {

                if (wordToBeCompared.equalsIgnoreCase(words.get(words.size() - 1)) ) {
                    return;
                }
                if (word.compareTo(wordToBeCompared) <= 0) {
                    continue;
                }

                char[] chars = word.toCharArray();
                if (isDistinct5LetterUniqueWord(chars, uniqueWords)) {

                    if (count == 0) {
                        wordToBeCompared = word;
//                        System.out.println(word);
                    }
                    aggregatedWords.add(word);
                    setChars(chars, uniqueWords);
                    count++;
                }
            }

            if(aggregatedWords.size() == targetLength)
                System.out.println(aggregatedWords);

        }
    }

    private static void setChars(char[] chars, Set<Character> uniqueWords) {
        for (char ch : chars) {
            uniqueWords.add(ch);
        }
    }

    private static boolean isDistinct5LetterUniqueWord(char[] chars, Set<Character> uniqueWords) {
        boolean isAllQniqueChars = true;
        Set<Character> checkUniqueCharacter = new HashSet<>();
        for (char ch : chars) {
            if (!checkUniqueCharacter.add(ch) || uniqueWords.contains(ch)) {
                isAllQniqueChars = false;
            }
        }
        return isAllQniqueChars;
    }
}
