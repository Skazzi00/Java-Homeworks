package md2html;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class Md2Html {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Not enough args");
            return;
        }
        StringBuilder result = new StringBuilder();
        State state = new State();
        try (Scanner scanner = new Scanner(new File(args[0]))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                line = replaceEscapes(line);
                if (line.isEmpty()) {
                    if (!state.isNewBlockStarted())
                        result.append(state.getHtmlBlock());
                    state.reset();
                    continue;
                }
                if (state.isNewBlockStarted()) {
                    line = parseBlockTag(state, line);
                }
                for (int i = 0; i < line.length(); i++) {
                    if (state.isEscape()) {
                        state.escape(line.charAt(i));
                        continue;
                    }
                    if (line.charAt(i) == '\\') {
                        state.escapeNextChar();
                        continue;
                    }
                    state.append(line.charAt(i));
                    for (var tag : Helper.mdTags) {
                        if (i + tag.length() <= line.length() && line.substring(i, i + tag.length()).equals(tag)) {
                            state.addTag(tag, state.block.length() - 1);
                            break;
                        }
                    }
                }
                state.append('\n');
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        result.append(state.getHtmlBlock());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]))) {
            writer.write(result.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String parseBlockTag(State state, String line) {
        state.newBlockStarted = false;
        if (line.startsWith("#")) {
            int spaceIndex = line.indexOf(' ');
            String prefix = line.substring(0, spaceIndex);
            if (prefix.equals("#".repeat(spaceIndex))) {
                state.setBlockTag("h" + spaceIndex);
                return line.substring(spaceIndex + 1);
            }
        }
        state.setBlockTag("p");
        return line;
    }

    private static String replaceEscapes(String string) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            if (Helper.escapeSymbols.containsKey(string.charAt(i)))
                result.append(Helper.escapeSymbols.get(string.charAt(i)));
            else
                result.append(string.charAt(i));
        }
        return result.toString();
    }

    private static class State {
        private boolean newBlockStarted = true;
        private List<MarkdownTag> tags = new ArrayList<>();
        private String blockTag;
        private int escapeCount = 0;
        private StringBuilder block = new StringBuilder();
        private Map<String, Integer> tagCount = new HashMap<>();

        boolean isNewBlockStarted() {
            return newBlockStarted;
        }

        void reset() {
            newBlockStarted = true;
            tags = new ArrayList<>();
            blockTag = null;
            escapeCount = 0;
            block = new StringBuilder();
            tagCount = new HashMap<>();
            startNewBlock();
        }

        void startNewBlock() {
            newBlockStarted = true;
        }

        void setBlockTag(String blockTag) {
            this.blockTag = blockTag;
        }

        void escapeNextChar() {
            escapeCount++;
        }

        void escapeNextChars(int cnt) {
            escapeCount += cnt;
        }

        void addTag(String tag, int index) {
            tags.add(new MarkdownTag(tag, index));
            if (!Helper.specialTags.contains(tag)) {
                tagCount.putIfAbsent(tag, 0);
                tagCount.put(tag, tagCount.get(tag) + 1);
            }
            escapeNextChars(tag.length() - 1);
        }

        boolean isEscape() {
            return escapeCount > 0;
        }

        void escape(char ch) {
            append(ch);
            escapeCount--;
        }

        void append(char ch) {
            block.append(ch);
        }

        String getHtmlBlock() {
            if (block.length() > 0)
                block.deleteCharAt(block.length() - 1);
            StringBuilder result = new StringBuilder();
            int curTagIndex = 0;
            Map<String, Integer> tagLeft = new HashMap<>(tagCount);
            result.append(String.format("<%s>", blockTag));
            for (int i = 0; i < block.length(); i++) {
                if (curTagIndex >= tags.size() || i != tags.get(curTagIndex).index) {
                    result.append(block.charAt(i));
                    continue;
                }

                String tag = tags.get(curTagIndex).tag;
                if (Helper.specialTags.contains(tag)) {
                    switch (tag) {
                        case "[":
                            result.append("<a href='");
                            String leftBlock = block.substring(i);
                            int start = leftBlock.indexOf("(");
                            int end = leftBlock.indexOf(")");
                            result.append(leftBlock, start + 1, end).append("'>");
                            break;
                        case "]":
                            result.append("</a>");
                            i = block.substring(i).indexOf(")") + i;
                            while (curTagIndex < tags.size() && tags.get(curTagIndex).index <= i)
                                curTagIndex++;
                            curTagIndex--;
                            break;
                    }
                    curTagIndex++;
                    continue;
                } else if (!(tagCount.get(tag) % 2 == 1 && tagLeft.get(tag) == 1)) {
                    result.append("<")
                            .append(tagLeft.get(tag) % 2 == 1 ? "/" : "")
                            .append(Helper.tagSymbols.get(tag))
                            .append(">");
                    i += tag.length() - 1;
                    curTagIndex++;
                } else {
                    result.append(block.charAt(i));
                }
                tagLeft.put(tag, tagLeft.get(tag) - 1);
            }
            result.append(String.format("</%s>\n", blockTag));
            return result.toString();
        }
    }
}
