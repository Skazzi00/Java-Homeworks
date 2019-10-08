package task5;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class Scanner implements AutoCloseable {
    private static final int BUFFER_SIZE = 4;
    private final Reader reader;
    private CharBuffer buf;
    private int position;
    private int savedPosition = -1;
    private boolean inputEnd = false;
    private boolean compact = true;
    private NoSuchElementException lastException;

    private Scanner(Reader reader) {
        this.reader = reader;
        buf = CharBuffer.allocate(BUFFER_SIZE);
        buf.limit(0);
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

    private void throwFor() {
        if ((inputEnd) && (position == buf.limit()))
            throw new NoSuchElementException();
        else
            throw new InputMismatchException();
    }

    private void allocate() {
        int offset = savedPosition == -1 ? position : savedPosition;
        if (compact)
            buf.position(offset);
        if (compact && offset > 0) {
            buf.compact();
            position -= offset;
            if (savedPosition != -1)
                savedPosition -= offset;
            buf.flip();
            return;
        }
        CharBuffer tmpBuf = CharBuffer.allocate(buf.capacity() * 2);
        tmpBuf.put(buf);
        tmpBuf.flip();
        buf = tmpBuf;
    }

    private void readInput() {
        if (buf.limit() == buf.capacity()) {
            allocate();
        }
        int pos = buf.position();
        buf.position(buf.limit());
        buf.limit(buf.capacity());

        int n = 0;
        try {
            n = reader.read(buf);
        } catch (IOException e) {
            n = -1;
        }
        if (n == -1) {
            inputEnd = true;
        }
        if (n > 0)
            inputEnd = false;
        buf.limit(buf.position());
        buf.position(pos);
    }

    public boolean hasNext() {
        savedPosition = position;
        while (!inputEnd) {
            while (position < buf.limit()) {
                if (!Character.isWhitespace(buf.get(position))) {
                    position = savedPosition;
                    savedPosition = -1;
                    return true;
                }
                position++;
            }
            readInput();
        }
        while (position < buf.limit()) {
            if (!Character.isWhitespace(buf.get(position))) {
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

    private void skipSpaces(){
        while (!inputEnd) {
            while (position < buf.limit()) {
                if (!Character.isWhitespace(buf.get(position))) {
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
        while (true) {
            skipSpaces();
            if (position == buf.limit()) {
                if (inputEnd) {
                    throw new NoSuchElementException("No such element");
                }
                throw new InputMismatchException();
            }
            StringBuilder builder = new StringBuilder();
            while (!inputEnd) {
                while (position < buf.limit()) {
                    if (Character.isWhitespace(buf.get(position))) {
                        return builder.toString();
                    }
                    builder.append(buf.get(position));
                    position++;
                }
                readInput();
            }
            inputEnd = true;
            while (position < buf.limit() && !Character.isWhitespace(buf.get(position))) {
                builder.append(buf.get(position));
                position++;
            }
            return builder.toString();
        }
    }

    public boolean hasNextLine() {
        if (inputEnd)
            return false;
        savedPosition = position;
        while (!inputEnd) {
            while (position < buf.limit()) {
                if (buf.get(position) == '\n') {
                    position = savedPosition;
                    savedPosition = -1;
                    return true;
                }
                position++;
            }
            readInput();
        }
        while (position < buf.limit()) {
            if (buf.get(position) == '\n') {
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
            while (position < buf.limit()) {
                if (buf.get(position) == '\n') {
                    position++;
                    return builder.toString();
                }
                builder.append(buf.get(position));
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
