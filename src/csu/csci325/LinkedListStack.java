package csu.csci325;

/**
 * Created by student on 3/22/16.
 */
public class LinkedListStack<T> implements csu.csci325.Stack<T> {
    private Node mHead;
    private class Node {
        T mData;
        Node mNext;
        public Node(T data, Node next){
            mData = data;
            mNext = next;
        }
    }
    public LinkedListStack() {
        mHead = null;
    }
    @Override
    public void push(T element) {
        Node node = new Node(element, mHead);
        mHead = node;
    }

    @Override
    public T pop() {
        if (mHead == null) {
            return null;
        }
        Node ret = mHead;
        mHead = mHead.mNext;
        return ret.mData;
    }

    @Override
    public T peek() {
        if (mHead == null) {
            return null;
        }
        return mHead.mData;
    }

    @Override
    public boolean isEmpty() {
        return (mHead == null);
    }

    @Override
    public int size() {
        Node rover = mHead;
        int counter = 0;

        while (rover != null) {
            counter++;
            rover = rover.mNext;
        }

        return counter;
    }
    public String toString() {
        String ret = "[";
        Node rover = mHead;
        while (rover != null) {
            ret += " " + rover.mData.toString();
            rover = rover.mNext;
        }
        /* Array base implementation
        for (int i = 0; i < mTop; i++) {
            ret += " " + mArray[i].toString();
        }
        */
        return ret + "]";
    }
    public static void main(String[] args) {
        csu.csci325.Stack<Integer> iStack = new LinkedListStack<Integer>();
        System.out.println(iStack.peek());
        for (int i = 0; i < 1000; i ++) {
            iStack.push(i);
        }
        System.out.println("Top is:" + iStack.peek());
        System.out.println(iStack);
        for (int i = 0; i < 1000; i++) {
            System.out.println(iStack.pop());
        }
        iStack.pop();
        System.out.println(iStack.peek());
        csu.csci325.Stack<String> sStack = new LinkedListStack<>();
        sStack.push("Hello");
        sStack.push("World!");
        System.out.println(sStack.pop());
        System.out.println(sStack.pop());
    }
}
