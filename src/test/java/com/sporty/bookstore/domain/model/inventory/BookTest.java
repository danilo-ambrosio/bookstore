package com.sporty.bookstore.domain.model.inventory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BookTest {

    @Test
    public void testThatBookIsInventoriedAndInStock() {
        final ISBN code = ISBN.code("978-0-316-1347-2");

        final Title title = Title.of("Urban Adventures");

        final List<Author> authors =
                Author.names("Emily Steel", "Diana Stewart");

        final List<Genre> genres =
                Genre.of("Fantasy", "Romance");

        final Book inventoriedBook =
                Book.inventory(code, title, StockQuantity.of(150), authors, genres);

        Assertions.assertEquals(code, inventoriedBook.code());
        Assertions.assertEquals(title, inventoriedBook.title());
        Assertions.assertEquals(authors, inventoriedBook.authors());
        Assertions.assertEquals(genres, inventoriedBook.genres());
        Assertions.assertTrue(inventoriedBook.isInStock());
    }

    @Test
    public void testThatBookIsOutOfStock() {
        Book inventoriedBook =
                Book.inventory(ISBN.code("123-0-478-1452-1"),
                        Title.of("The Pragmatic Programmer"),
                        StockQuantity.of(5),
                        Author.names("David Thomas", "Andrew Hunt"),
                        Genre.of("Technology"));

        inventoriedBook.destock(StockQuantity.of(5));
        Assertions.assertFalse(inventoriedBook.isInStock());
    }

    @Test
    public void testThatBookIsReinventoried() {
        final Title title = Title.of("Microservice Pattern");
        final ISBN code = ISBN.code("111-1-111-1111-1");
        final List<Author> authors = Author.names("Martin Fowler");
        final List<Genre> genres = Genre.of("Math");

        final Book inventoriedBook =
                Book.inventory(code, title, StockQuantity.of(0), authors, genres);

        Assertions.assertEquals(code, inventoriedBook.code());
        Assertions.assertEquals(title, inventoriedBook.title());
        Assertions.assertEquals(authors, inventoriedBook.authors());
        Assertions.assertEquals(genres, inventoriedBook.genres());
        Assertions.assertFalse(inventoriedBook.isInStock());

        final Title fixedTitle = Title.of("Microservice Patterns");
        final ISBN fixedCode = ISBN.code("777-1-222-3331-4");
        final List<Author> fixedAuthors = Author.names("Martin Fowler");
        final List<Genre> fixedGenres = Genre.of("Software Engineering");

        inventoriedBook.reinventory(fixedCode, fixedTitle, StockQuantity.of(250), fixedAuthors, fixedGenres);

        Assertions.assertEquals(fixedCode, inventoriedBook.code());
        Assertions.assertEquals(fixedAuthors, inventoriedBook.authors());
        Assertions.assertEquals(fixedGenres, inventoriedBook.genres());
        Assertions.assertTrue(inventoriedBook.isInStock());
    }
}
