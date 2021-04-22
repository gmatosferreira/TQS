package edu.pt.ua.tqs.lab5.s92972;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Library {
    private final List<Book> store = new ArrayList<>();

    public void addBook(final Book book) {
        store.add(book);
    }

    public List<Book> findBooks(final Date from, final Date to) {
        Calendar end = Calendar.getInstance();
        end.setTime(to);
        end.roll(Calendar.YEAR, 1);

        return store.stream().filter(book -> {
            return from.before(book.getPublished()) && end.getTime().after(book.getPublished());
        }).sorted(Comparator.comparing(Book::getPublished).reversed()).collect(Collectors.toList());
    }

    public List<Book> findBooksByAuthor(String author) {
        // Lambda expression based on https://www.baeldung.com/java-stream-filter-lambda
        return store.stream().filter(b -> b.getAuthor().equalsIgnoreCase(author)).collect(Collectors.toList());
    }

    public List<Book> findBooksByTitle(String title) {
        return store.stream().filter(b -> b.getTitle().contains(title)).collect(Collectors.toList());
    }

    public List<Book> findBooks() {
        return store;
    }
}