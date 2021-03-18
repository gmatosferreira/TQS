package edu.pt.ua.tqs.s92972.lab1;

import java.util.LinkedList;

public class TqsStack<T> {

    private int bound;
    private LinkedList<T> list;

    public TqsStack (int bound) {
        this.bound = bound;
        this.list = new LinkedList<>();
    }

    public TqsStack () {
        this(0);
    }

    public void push(T t) {
        list.add(0, t);
    }

    public T pop() {
        return list.pop();
    }

    public T peek() {
        return list.peek();
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

}
