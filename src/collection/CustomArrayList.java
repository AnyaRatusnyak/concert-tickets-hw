package collection;

import java.util.AbstractList;
import java.util.List;

public class CustomArrayList<T> extends AbstractList<T> implements List<T>{
    private static final int DEFAULT_CAPACITY = 10;
    private T[] elementData;
    private int size;

    public CustomArrayList() {
        this.elementData = (T[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public void add(int index, T value) {
        checkPositionIndex(index);
        growIfFull();
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = value;
        size++;
    }

    @Override
    public T get(int index) {
        checkPositionIndex(index);
        return elementData[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T remove(int index) {
        checkPositionIndex(index);
        final T removedElement = elementData[index];
        System.arraycopy(elementData, index + 1, elementData, index, size - index - 1);
        elementData[--size] = null;
        return removedElement;
    }

    private T[] growIfFull() {
        if (size == elementData.length) {
            int oldCapacity = elementData.length;
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            T[] newData = (T[]) new Object[newCapacity];
            System.arraycopy(elementData, 0, newData, 0, oldCapacity);
            elementData = newData;
            return elementData;
        }
        return elementData;
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("the index " + index
                    + "is invalid, current size of the list is " + size);
        }
    }
}
