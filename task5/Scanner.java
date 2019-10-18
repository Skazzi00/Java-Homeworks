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
    private int position;
    private int savedPosition = -1;
    private boolean inputEnd = false;
    private boolean closed = false;
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

    public Scanner(File source, String charsetName) throws FileNotFoundException, UnsupportedEncodingException {
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
        if (closed) {
            return;
        }
        closed = true;
        try {
            reader.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void allocate() {
        int offset = savedPosition == -1 ? position : savedPosition;
        if (offset > 0) {
            System.arraycopy(buf, offset, buf, 0, bufLimit - offset);
            position -= offset;
            if (savedPosition != -1) {
                savedPosition -= offset;
            }
            bufLimit -= offset;
            return;
        }
        buf = Arrays.copyOf(buf, buf.length * 2);
    }

    private void readInput() {
        if (bufLimit == buf.length) {
            allocate();
        }
        int n;
        try {
            n = reader.read(buf, bufLimit, buf.length - bufLimit);
        } catch (IOException e) {
            n = -1;
        }
        if (n == -1) {
            inputEnd = true;
        }
        if (n > 0) {
            bufLimit = position + n;
        }
    }

    public boolean hasNext() {
        if (closed) {
            throw new IllegalStateException("Scanner is closed");
        }
        boolean isSaved = savedPosition != -1;
        savedPosition = isSaved ? savedPosition : position;
        while (!inputEnd) {
            while (position < bufLimit) {
                if (!Character.isWhitespace(buf[position])) {
                    position = savedPosition;
                    savedPosition = isSaved ? savedPosition : -1;
                    return true;
                }
                position++;
            }
            readInput();
        }
        position = savedPosition;
        savedPosition = isSaved ? savedPosition : -1;
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

    private String next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No such element");
        }
        skipSpaces();
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
        return builder.toString();
    }

    public boolean hasNextLine() {
        if (closed) {
            throw new IllegalStateException("Scanner is closed");
        }
        if (inputEnd) {
            return false;
        }
        savedPosition = position;
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
        position = savedPosition;
        savedPosition = -1;
        return false;
    }

    public String nextLine() {
        if (!hasNextLine()) {
            throw new NoSuchElementException("No such element");
        }
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
        return builder.toString();
    }

    public boolean hasNextInt() {
        if (!hasNext()) {
            lastException = new NoSuchElementException();
            return false;
        }
        savedPosition = position;
        boolean inputEnd = this.inputEnd;
        String token = next();
        this.inputEnd = inputEnd;
        position = savedPosition;
        savedPosition = -1;
        try {
            Integer.parseInt(token);
        } catch (NumberFormatException e) {
            lastException = new InputMismatchException();
            return false;
        }
        return true;
    }

    public int nextInt() {
        if (!hasNextInt()) {
            throw lastException;
        }
        return Integer.parseInt(next());
    }
}