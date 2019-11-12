import java.util.Arrays;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class ListInt {
    private static final int SIZE = 16;
    private int[] array;
    private int size;

    public ListInt() {
        array = new int[SIZE];
        size = 0;
    }

    public int size() {
        return size;
    }

    public void add(int value) {
        if (size == array.length) {
            array = Arrays.copyOf(array, size * 2);
        }
        array[size++] = value;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("size = " + size + "; index = " + index);
        }
        return array[index];
    }

    public void set(int index, int value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("size = " + size + "; index = " + index);
        }
        array[index] = value;
    }
}
