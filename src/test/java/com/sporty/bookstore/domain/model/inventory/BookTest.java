package com.sporty.bookstore.domain.model.inventory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BookTest {

    @Test
    public void testThatBookIsInventoriedAndInStock() {
        final String code = "978-0-316-1347-2";
        final String bookTitle = "Adventures";
        final List<String> authors = List.of("Emily Steel", "Diana Stewart");
        final List<String> genres = List.of("Fantasy", "Romance");

        final Book inventoriedBook =
                Book.inventory(ISBN.from(code), Title.of(bookTitle), StockQuantity.of(150), Author.names(authors), Genre.of(genres));

        Assertions.assertEquals(code, inventoriedBook.isbn());
        Assertions.assertEquals(bookTitle, inventoriedBook.title());
        Assertions.assertEquals(authors, inventoriedBook.authors());
        Assertions.assertEquals(genres, inventoriedBook.genres());
        Assertions.assertTrue(inventoriedBook.isInStock());
    }

    @Test
    public void testThatBookIsOutOfStock() {
        final Book inventoriedBook =
                Book.inventory(ISBN.from("123-0-478-1452-1"),
                        Title.of("The Pragmatic Programmer"),
                        StockQuantity.of(5),
                        Author.names("David Thomas", "Andrew Hunt"),
                        Genre.of("Technology"));

        inventoriedBook.destock(StockQuantity.of(5));
        Assertions.assertFalse(inventoriedBook.isInStock());
    }

    @Test
    public void testThatBookIsReinventoried() {
        final String bookTitle = "Microservice Pattern";
        final String code = "111-1-111-1111-1";
        final String author = "Martin Fowler";
        final String genre = "Math";

        final Book book =
                Book.inventory(ISBN.from(code), Title.of(bookTitle), StockQuantity.of(0), Author.names(author), Genre.of(genre));

        Assertions.assertEquals(code, book.isbn());
        Assertions.assertEquals(bookTitle, book.title());
        Assertions.assertEquals(List.of(author), book.authors());
        Assertions.assertEquals(List.of(genre), book.genres());
        Assertions.assertFalse(book.isInStock());

        final String fixedBookTitle = "Microservice Patterns";
        final String fixedCode = "777-1-222-3331-4";
        final String fixedAuthor = "Chris Richardson";
        final String fixedGenre = "Software Engineering";

        book.reinventory(ISBN.from(fixedCode), Title.of(fixedBookTitle), StockQuantity.of(250), Author.names(fixedAuthor), Genre.of(fixedGenre));

        Assertions.assertEquals(fixedCode, book.isbn());
        Assertions.assertEquals(List.of(fixedAuthor), book.authors());
        Assertions.assertEquals(List.of(fixedGenre), book.genres());
        Assertions.assertTrue(book.isInStock());
    }
}
