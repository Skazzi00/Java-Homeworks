import task6.ListInt;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordStatIndex {
    private static final Pattern WORD = Pattern.compile("[\\p{IsAlphabetic}\\p{Pd}']+");
    private static final int BUFFER_SIZE = 8192;

    private static String readLine(BufferedReader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        int ch = reader.read();
        if (ch == -1) {
            return null;
        }
        while (ch != -1 && ch != '\n') {
            builder.append((char) ch);
            ch = reader.read();
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Error: Not enough arguments");
            return;
        }
        File input = new File(args[0]);
        File output = new File(args[1]);
        int wordCounter = 0;
        Map<String, Integer> wordFreq = new LinkedHashMap<>();
        Map<String, ListInt> wordEntries = new HashMap<>();
        try (BufferedReader inputReader = new BufferedReader(
                new FileReader(
                        input, StandardCharsets.UTF_8
                ), BUFFER_SIZE
        )) {
            String line = readLine(inputReader);
            while (line != null) {
                line = line.toLowerCase();
                Matcher matcher = WORD.matcher(line);
                while (matcher.find()) {
                    wordCounter++;
                    String token = line.substring(matcher.start(), matcher.end());
                    wordFreq.put(token, wordFreq.getOrDefault(token, 0) + 1);
                    wordEntries.putIfAbsent(token, new ListInt());
                    wordEntries.get(token).add(wordCounter);
                }
                line = readLine(inputReader);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            return;
        } catch (IOException e) {
            System.err.println("I/O Exception: " + e.getMessage());
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

