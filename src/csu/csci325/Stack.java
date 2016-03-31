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
    @Override
    String toString();
}
