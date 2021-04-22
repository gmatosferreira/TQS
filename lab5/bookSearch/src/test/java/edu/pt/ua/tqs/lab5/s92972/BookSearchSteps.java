package edu.pt.ua.tqs.lab5.s92972;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookSearchSteps {

    private Library library;
    private List<Book> searchResults;

    /*
    @ParameterType("^([0-9]{4})-([0-9]{2})-([0-9]{2})$")
    public Date iso8601Date(String year, String month, String day){
        LocalDateTime ldt = LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day),0, 0);
        // Convert to Date
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }
     */

    @ParameterType(".*")
    public Date iso8601Date(String date) {
        String[] d = date.split("-");
        LocalDateTime ldt = LocalDateTime.of(Integer.parseInt(d[0]), Integer.parseInt(d[1]), Integer.parseInt(d[2]), 0, 0);
        // Convert to Date
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    // BeforeAll()
    @Given("a book with the title {string}, written by {string}, published in {iso8601Date}")
    public void a_book_with_the_title_written_by_published_in_march(String string, String string2, Date localDateTime) {
        library = new Library();
        library.addBook(new Book(string, string2, localDateTime));
        searchResults = new ArrayList<>();
    }

    @And("another book with the title {string}, written by {string}, published in {iso8601Date}")
    public void another_book_with_the_title_written_by_published_in_august(String string, String string2, Date localDateTime) {
        library.addBook(new Book(string, string2, localDateTime));
    }

    // Actions
    @When("the customer searches for books published between {iso8601Date} and {iso8601Date}")
    public void the_customer_searches_for_books_published_between_and(Date date1, Date date2) {
        searchResults = library.findBooks(date1, date2);
    }

    @When("the customer searches for books by author {string}")
    public void the_customer_searches_for_books_by_author(String string) {
        searchResults = library.findBooksByAuthor(string);
    }

    @When("the customer searches for books by title {string}")
    public void the_customer_searches_for_books_by_title(String string) {
        searchResults = library.findBooksByTitle(string);
    }

    @When("the customer searches for any book")
    public void the_customer_searches_for_any_book() {
        searchResults = library.findBooks();
    }

    // Expected output
    @Then("{int} books should have been found")
    public void books_should_have_been_found(Integer int1) {
        assertEquals(int1, searchResults.size(), "Erro: Search results number not expected!");
    }

    @Then("Book {int} should have the title {string}")
    public void book_should_have_the_title(Integer int1, String string) {
        assertEquals(searchResults.get(int1 - 1).getTitle(), string, "Error: Book title does not match expected!");
    }
}
