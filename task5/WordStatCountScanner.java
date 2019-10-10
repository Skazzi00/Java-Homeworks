import task5.Scanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordStatCountScanner {
    private static final Pattern WORD = Pattern.compile("[\\p{IsAlphabetic}\\p{Pd}']+");

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Error: Not enough arguments");
            return;
        }
        File input = new File(args[0]);
        File output = new File(args[1]);

        Map<String, Integer> freq = new LinkedHashMap<>();
        try (Scanner inputScanner = new Scanner(input)) {
            String line;
            while (inputScanner.hasNextLine()) {
                line = inputScanner.nextLine();
                line = line.toLowerCase();
                Matcher matcher = WORD.matcher(line);
                while (matcher.find()) {
                    String token = line.substring(matcher.start(), matcher.end());
                    freq.put(token, freq.getOrDefault(token, 0) + 1);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            return;
        }

        List<Map.Entry<String, Integer>> freqList = new ArrayList<>(freq.entrySet());
        freqList.sort(Map.Entry.comparingByValue());

        try (PrintWriter outputWriter = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(output, StandardCharsets.UTF_8))
        )
        ) {
            for (Map.Entry<String, Integer> i : freqList) {
                outputWriter.println(i.getKey() + " " + i.getValue());
            }
        } catch (IOException e) {
            System.err.println("I/O Exception: " + e.getMessage());
        }
    }
}
