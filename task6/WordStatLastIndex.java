import task5.Scanner;
import task6.ListInt;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class WordStatLastIndex {
    private static final Pattern WORD = Pattern.compile("[\\p{IsAlphabetic}\\p{Pd}']+");

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Error: Not enough arguments");
            return;
        }
        File input = new File(args[0]);
        File output = new File(args[1]);
        Map<String, Integer> wordFreq = new LinkedHashMap<>();
        Map<String, ListInt> wordEntries = new HashMap<>();
        try (Scanner inputScanner = new Scanner(input)) {
            while (inputScanner.hasNextLine()) {
                String line = inputScanner.nextLine().toLowerCase();
                int wordCounter = 0;
                Matcher matcher = WORD.matcher(line);
                Map<String, Integer> lineEntry = new LinkedHashMap<>();
                while (matcher.find()) {
                    wordCounter++;
                    String token = line.substring(matcher.start(), matcher.end());
                    wordFreq.put(token, wordFreq.getOrDefault(token, 0) + 1);
                    lineEntry.put(token, wordCounter);
                }
                for (Map.Entry<String, Integer> i : lineEntry.entrySet()) {
                    wordEntries.putIfAbsent(i.getKey(), new ListInt());
                    wordEntries.get(i.getKey()).add(i.getValue());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            return;
        }

        try (PrintWriter outputWriter = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(output, StandardCharsets.UTF_8))
        )) {
            for (Map.Entry<String, Integer> i : wordFreq.entrySet()) {
                outputWriter.print(i.getKey() + " " + i.getValue() + " ");
                for (int j = 0; j < wordEntries.get(i.getKey()).size() - 1; j++) {
                    outputWriter.print(wordEntries.get(i.getKey()).get(j) + " ");
                }
                outputWriter.println(wordEntries.get(i.getKey()).get(
                        wordEntries.get(i.getKey()).size() - 1
                ));
            }
        } catch (IOException e) {
            System.err.println("I/O Exception: " + e.getMessage());
        }
    }
}
