package csu.csci325;

/**
 * Created by student on 3/17/16.
 */
public interface Stack<T> {
    void push(T element);
    T pop();
    T peek();
    boolean isEmpty();
    int size();
    int contains(T element);
    void addFront(T element);
    void addLast(T element);
    boolean add(int idx, T element);
    void remove(int idx);
    void remove(T element);
    T get(int idx);
    @Override
    String toString();

}
