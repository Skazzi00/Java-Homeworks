package task5;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class Scanner implements AutoCloseable {
    private static final int BUFFER_SIZE = 128;
    private final Reader reader;
    private char[] buf;
    private int bufLimit;
    private int bufPosition = 0;
    private int position;
    private int savedPosition = -1;
    private boolean inputEnd = false;
    private boolean compact = true;
    private NoSuchElementException lastException;

    private Scanner(Reader reader) {
        this.reader = reader;
        buf = new char[BUFFER_SIZE];
        bufLimit = 0;
    }

    public Scanner(File source) throws FileNotFoundException {
        this(new BufferedReader(new FileReader(source), BUFFER_SIZE));
    }

    public Scanner(File source, Charset charset) throws IOException {
        this(new BufferedReader(new FileReader(source, charset), BUFFER_SIZE));
    }

    public Scanner(File source, String charsetName) throws IOException {
        this(new BufferedReader(new InputStreamReader(new FileInputStream(source), charsetName), BUFFER_SIZE));
    }

    public Scanner(String source) {
        this(new BufferedReader(new StringReader(source), BUFFER_SIZE));
    }

    public Scanner(InputStream source) {
        this(new BufferedReader(new InputStreamReader(source), BUFFER_SIZE));
    }

    public Scanner(InputStream source, String charsetName) throws UnsupportedEncodingException {
        this(new BufferedReader(new InputStreamReader(source, charsetName), BUFFER_SIZE));
    }

    public Scanner(InputStream source, Charset charset) {
        this(new BufferedReader(new InputStreamReader(source, charset), BUFFER_SIZE));
    }

    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void flip() {
        bufLimit = bufPosition;
        bufPosition = 0;
    }

    private void compact() {
        System.arraycopy(buf, position, buf, 0, bufLimit - bufPosition);
        bufPosition = bufLimit - bufPosition;
        bufLimit = buf.length;
    }

    private void allocate() {
        int offset = savedPosition == -1 ? position : savedPosition;
        if (compact) {
            bufPosition = offset;
        }
        if (compact && offset > 0) {
            compact();
            position -= offset;
            if (savedPosition != -1)
                savedPosition -= offset;
            flip();
            return;
        }
        buf = Arrays.copyOf(buf, buf.length * 2);
    }

    private void readInput() {
        if (bufLimit == buf.length) {
            allocate();
        }
        int pos = bufPosition;
        bufPosition = bufLimit;
        bufLimit = buf.length;
        int n;
        try {
            n = reader.read(buf, bufPosition, buf.length - bufPosition);
        } catch (IOException e) {
            n = -1;
        }
        if (n == -1) {
            inputEnd = true;
        }
        bufLimit = bufPosition;
        if (n > 0) {
            inputEnd = false;
            bufLimit = bufPosition + n;
        }
        bufPosition = pos;
    }

    public boolean hasNext() {
        savedPosition = position;
        compact = false;
        while (!inputEnd) {
            while (position < bufLimit) {
                if (!Character.isWhitespace(buf[position])) {
                    position = savedPosition;
                    savedPosition = -1;
                    return true;
                }
                position++;
            }
            readInput();
        }
        compact = true;
        while (position < bufLimit) {
            if (!Character.isWhitespace(buf[position])) {
                position = savedPosition;
                savedPosition = -1;
                return true;
            }
            position++;
        }
        position = savedPosition;
        savedPosition = -1;
        return false;
    }

    private void skipSpaces() {
        while (!inputEnd) {
            while (position < bufLimit) {
                if (!Character.isWhitespace(buf[position])) {
                    return;
                }
                position++;
            }
            readInput();
        }
    }

    public String next() {
        if (!hasNext())
            throw new NoSuchElementException("No such element");
        skipSpaces();
        if (position == bufLimit) {
            if (inputEnd) {
                throw new NoSuchElementException("No such element");
            }
            throw new InputMismatchException();
        }
        StringBuilder builder = new StringBuilder();
        while (!inputEnd) {
            while (position < bufLimit) {
                if (Character.isWhitespace(buf[position])) {
                    return builder.toString();
                }
                builder.append(buf[position]);
                position++;
            }
            readInput();
        }
        inputEnd = true;
        while (position < bufLimit && !Character.isWhitespace(buf[position])) {
            builder.append(buf[position]);
            position++;
        }
        return builder.toString();
    }

    private boolean findEndLine() {
        while (position < bufLimit) {
            if (buf[position] == '\n') {
                position = savedPosition;
                savedPosition = -1;
                return true;
            }
            position++;
        }
        return false;
    }

    public boolean hasNextLine() {
        if (inputEnd)
            return false;
        savedPosition = position;
        compact = false;
        while (!inputEnd) {
            while (position < bufLimit) {
                if (buf[position] == '\n') {
                    position = savedPosition;
                    savedPosition = -1;
                    return true;
                }
                position++;
            }
            readInput();
        }
        compact = true;
        while (position < bufLimit) {
            if (buf[position] == '\n') {
                position = savedPosition;
                savedPosition = -1;
                return true;
            }
            position++;
        }
        position = savedPosition;
        savedPosition = -1;
        return false;
    }

    public String nextLine() {
        if (!hasNextLine())
            throw new NoSuchElementException("No such element");
        StringBuilder builder = new StringBuilder();
        while (!inputEnd) {
            while (position < bufLimit) {
                if (buf[position] == '\n') {
                    position++;
                    return builder.toString();
                }
                builder.append(buf[position]);
                position++;
            }
            readInput();
        }
        inputEnd = true;
        return builder.toString();
    }

    public boolean hasNextInt() {
        if (!hasNext()) {
            lastException = new NoSuchElementException();
            return false;
        }
        int savedPosition = position;
        boolean inputEnd = this.inputEnd;
        compact = false;
        String token = next();
        compact = true;
        this.inputEnd = inputEnd;
        position = savedPosition;
        try {
            Integer.parseInt(token);
        } catch (NumberFormatException e) {
            lastException = new InputMismatchException();
            return false;
        }
        return true;
    }

    public int nextInt() {
        if (!hasNextInt())
            throw lastException;
        return Integer.parseInt(next());
    }
}