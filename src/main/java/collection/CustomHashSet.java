package collection;
import java.util.LinkedList;

public class CustomHashSet<T>  {
    private LinkedList<T>[] table;
    private int capacity = 16;
    private static final double LOAD_FACTOR = 0.75;
    private int size;

    public CustomHashSet() {
        this.table = new LinkedList[capacity];
        this.size = 0;
    }

    public boolean add(T element) {
        resizeIfExceedCapacity();
        int index = getIndex(element);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }
        if (table[index].contains(element)) {
            return false;
        }

        table[index].add(element);
        size++;
        return true;
    }

    public boolean delete(T element) {
        int index = getIndex(element);

        if (table[index] != null && table[index].contains(element)) {
            table[index].remove(element);
            size--;
            return true;
        }

        return false;
    }

    public boolean contains(T element) {
        int index = getIndex(element);

        return table[index] != null && table[index].contains(element);
    }

    public void iterate() {
        for (LinkedList<T> item : table) {
            if (item != null) {
                for (T element : item) {
                    System.out.println(element);
                }
            }
        }
    }

    public int size() {
        return size;
    }

    private void resizeIfExceedCapacity(){
        if ((double) size / table.length >= LOAD_FACTOR){
            LinkedList<T>[] oldTable = table;
            capacity = capacity * 2;
            table = new LinkedList[capacity];
            size = 0;

            for (LinkedList<T> item : oldTable) {
                if (item != null) {
                    for (T element : item) {
                        add(element);
                    }
                }
            }
        }
    }

    private int getIndex(T element) {
        return (element == null) ? 0 : Math.abs(element.hashCode()) % capacity;
    }
}
