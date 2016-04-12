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
    public int contains(T element) {
        int idx = 0;
        for (Node rover = mHead; rover != null; rover = rover.mNext, idx++) {
            if (rover.mData.equals(element)) {
                return idx;
            }
        }
        return -1;
    }

    @Override
    public void addFront(T element) {
        push(element);
    }

    @Override
    public void addLast(T element) {
        Node newNode = new Node(element, null);
        Node rover;

        if(mHead == null) {
            mHead = newNode;
        } else {

            for (rover = mHead; rover.mNext != null; rover = rover.mNext) {
            }

            rover.mNext = newNode;
        }
    }

    @Override
    public boolean add(int idx, T element) {
        Node rover = mHead;
        if (idx == 0) {
            addFront(element);
            return true;
        }
        for (int i = 0; i < idx-1 && rover != null; i++) {
            rover = rover.mNext;
        }
        if (rover != null) {
            if (rover.mNext == null) {
                addLast(element);
            } else {
                Node newNode = new Node(element, rover.mNext);
                rover.mNext = newNode;
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void remove(int idx) {
        remove(get(idx));
    }

    @Override
    public void remove(T element) {
        Node rover = mHead;
        if (mHead == null) {
            return;
        }
        if (mHead.mData.equals(element)) {
            mHead = null;
            return;
        }
        while (rover != null && rover.mNext != null) {
            if (rover.mNext.mData.equals(element)) {
//                if (rover.mNext == null) {
//                    mTail = rover;
//                }
                rover.mNext = rover.mNext.mNext;
                return;
            }
            rover = rover.mNext;
        }
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
    public T get(int idx) {
        int i = 0;
        Node rover;
        for (rover = mHead; rover != null && i < idx; rover = rover.mNext, i++) {
        }

        if (rover != null) {
            return rover.mData;
        }
        return null;
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
