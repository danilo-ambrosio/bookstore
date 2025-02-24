package com.sporty.bookstore.domain.model.inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a book in the inventory.
 * It contains information about the book's ISBN, title, stock quantity, authors, and genres.
 * It also provides methods to manage the book's inventory, such as destocking and reinventorying.
 *
 * @author Danilo Ambrosio
 */
public class Book {

    private final BookId bookId;
    private ISBN isbn;
    private Title title;
    private StockQuantity stockQuantity;
    private final List<Author> authors = new ArrayList<>();
    private final List<Genre> genres = new ArrayList<>();

    public static Book of(final BookId bookId,
                          final ISBN isbn,
                          final Title title,
                          final StockQuantity stockQuantity,
                          final List<Author> authors,
                          final List<Genre> genres) {
        return new Book(bookId, isbn, title, stockQuantity, authors, genres);
    }

    public static Book inventory(final ISBN code,
                                 final Title title,
                                 final StockQuantity stockQuantity,
                                 final List<Author> authors,
                                 final List<Genre> genres) {
        return Book.of(BookId.create(), code, title, stockQuantity, authors, genres);
    }

    private Book(final BookId bookId,
                 final ISBN isbn,
                 final Title title,
                 final StockQuantity stockQuantity,
                 final List<Author> authors,
                 final List<Genre> genres) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.stockQuantity = stockQuantity;
        this.authors.addAll(authors);
        this.genres.addAll(genres);
    }

    public void reinventory(final ISBN code,
                            final Title title,
                            final StockQuantity stockQuantity,
                            final List<Author> authors,
                            final List<Genre> genres) {
        this.isbn = code;
        this.title = title;
        this.stockQuantity = stockQuantity;
        this.authors.clear();
        this.authors.addAll(authors);
        this.genres.clear();
        this.genres.addAll(genres);
    }

    public void destock(StockQuantity stockQuantity) {
        if (this.stockQuantity.quantity() < stockQuantity.quantity()) {
            throw new IllegalArgumentException("Not enough books in stock");
        }
        this.stockQuantity = StockQuantity.of(this.stockQuantity.quantity() - stockQuantity.quantity());
    }

    public String id() {
        return bookId.value();
    }

    public String isbn() {
        return isbn.from();
    }

    public String title() {
        return title.title();
    }

    public int stockQuantity() {
        return stockQuantity.quantity();
    }

    public List<String> authors() {
        return authors.stream().map(Author::name).toList();
    }

    public List<String> genres() {
        return genres.stream().map(Genre::name).toList();
    }

    public boolean isInStock() {
        return hasStock(1);
    }

    public boolean hasStock(int desiredQuantity) {
        return stockQuantity.quantity() >= desiredQuantity;
    }
}
