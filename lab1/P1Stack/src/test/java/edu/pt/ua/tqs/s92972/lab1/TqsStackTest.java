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
        assertThrows(IllegalArgumentException.class, () -> new TqsStack<String>(-1), "contructor: Bound with negative number did not throw error");
        assertThrows(IllegalArgumentException.class, () -> new TqsStack<String>(0), "contructor: Bound with value zero did not throw error");
    }

    @org.junit.jupiter.api.Test
    void push() {
        assertThrows(IllegalStateException.class, () -> tsThree.push("Four"), "push: Pushing an elemento to a full stack did not throw an IllegalStateException.;");
        assertDoesNotThrow(() -> tsEmpty.push("Four"), "push: Pushing onto non bounded stack throwed an IllegalStateException.;");
    }

    @org.junit.jupiter.api.Test
    void pop() {
        assertEquals("Three", tsThree.pop(), "pop: The element popped is not the last pushed.");

        tsThree.pop();
        tsThree.pop();
        assertEquals(0, tsThree.size(), "pop: After n pops at a stack with n elements, its size is not 0.");

        assertThrows(NoSuchElementException.class, () -> tsThree.pop(), "pop: Popping from an empty stack did not throw a NoSuchElementException.");
    }

    @org.junit.jupiter.api.Test
    void peek() {
        assertEquals("Three", tsThree.peek(), "peek: The element popped is not the last pushed.");
        assertAll(
                () -> assertEquals("Three", tsThree.peek(), "peek: The element peeked is not the last pushed."),
                () -> assertEquals(3, tsThree.size(), "peek: After a peek the size changed.")
        );

        assertDoesNotThrow(() -> tsThree.peek(), "peek: Peeking from an empty stack throws a NoSuchElementException.");

    }

    @org.junit.jupiter.api.Test
    void size() {
        assertEquals(0, tsEmpty.size(), "size: On construction, a stack does not have size 0.");
        assertEquals(3, tsThree.size(), "size: A stack with n elements does not have size n.");
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        assertTrue(tsEmpty.isEmpty(), "isEmpty: A stack is not empty on construction.");
        assertFalse(tsThree.isEmpty(), "isEmpty: A stack with n elements (n>0) is empty.");
    }
}