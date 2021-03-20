package edu.pt.ua.tqs.s92972.lab1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;
import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;

class TqsStackTest {

    private TqsStack<String> tsEmpty;
    private TqsStack<String> tsThree;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        tsEmpty = new TqsStack();
        tsThree = new TqsStack(3);
        tsThree.push("One");
        tsThree.push("Two");
        tsThree.push("Three");
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        tsEmpty = tsThree = null;
    }

    @org.junit.jupiter.api.Test
    void constructor() {
        assertThrows(IllegalArgumentException.class, () -> new TqsStack<String>(-1), "The stack bound must be a natural number!");
        assertThrows(IllegalArgumentException.class, () -> new TqsStack<String>(0), "The stack bound must be a natural number!");
    }

    @org.junit.jupiter.api.Test
    void push() {
        assertThrows(IllegalStateException.class, () -> tsThree.push("Four"), "For bounded stacks only, pushing onto a full stack throw an IllegalStateException.;");
        assertDoesNotThrow(() -> tsEmpty.push("Four"), "For bounded stacks only, pushing onto a full stack throw an IllegalStateException.;");
    }

    @org.junit.jupiter.api.Test
    void pop() {
        assertEquals("Three", tsThree.pop(), "The element popped is the last pushed.");

        tsThree.pop();
        tsThree.pop();
        assertEquals(0, tsThree.size(), "After n pops at a stack with n elements, its size will be 0.");

        assertThrows(NoSuchElementException.class, () -> tsThree.pop(), "Popping from an empty stack throw a NoSuchElementException.");
    }

    @org.junit.jupiter.api.Test
    void peek() {
        assertEquals("Three", tsThree.peek(), "The element popped is the last pushed.");
        assertAll(
                () -> assertEquals("Three", tsThree.peek(), "The element peeked is the last pushed."),
                () -> assertEquals(3, tsThree.size(), "After a peek the size remains the same.")
        );

        assertDoesNotThrow(() -> tsThree.peek(), "Peeking from an empty stack does not throw a NoSuchElementException.");

    }

    @org.junit.jupiter.api.Test
    void size() {
        assertEquals(0, tsEmpty.size(), "A stack has size 0 on construction.");
        assertEquals(3, tsThree.size(), "Stack of size n has n elements.");
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        assertTrue(tsEmpty.isEmpty(), "A stack is empty on construction.");
        assertFalse(tsThree.isEmpty(), "Stack of size n (n>0) is not empty.");
    }
}