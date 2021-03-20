package edu.pt.ua.tqs.s92972.lab1;

import java.util.LinkedList;

import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;

public class TqsStack<T> {

    private int bound;
    private LinkedList<T> list;

    public TqsStack () {
        this.list = new LinkedList<>();
    }

    public TqsStack (int bound) {
        if (bound<=0) {
            throw new IllegalArgumentException("The stack bound must be a natural number (>0)!");
        }
        this.bound = bound;
        this.list = new LinkedList<>();
    }

    public void push(T t) {
        if (this.bound > 0 && this.list.size()+1 > this.bound) {
            throw new IllegalStateException("The stack boundary has been reached!");
        }
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
