package com.sporty.bookstore.domain.model.inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Book {

    private final BookId bookId;
    private ISBN isbn;
    private Title title;
    private StockQuantity stockQuantity;
    private final List<Author> authors = new ArrayList<>();
    private final List<Genre> genres = new ArrayList<>();

    private Book(final ISBN isbn,
                 final Title title,
                 final StockQuantity stockQuantity,
                 final List<Author> authors,
                 final List<Genre> genres) {
        this.bookId = BookId.create();
        this.isbn = isbn;
        this.title = title;
        this.stockQuantity = stockQuantity;
        this.authors.addAll(authors);
        this.genres.addAll(genres);
    }

    public static Book inventory(final ISBN code,
                                 final Title title,
                                 final StockQuantity stockQuantity,
                                 final List<Author> authors,
                                 final List<Genre> genres) {
        return new Book(code, title, stockQuantity, authors, genres);
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

    public ISBN code() {
        return isbn;
    }

    public Title title() {
        return title;
    }

    public List<Author> authors() {
        return Collections.unmodifiableList(authors);
    }

    public List<Genre> genres() {
        return Collections.unmodifiableList(genres);
    }

    public boolean isInStock() {
        return stockQuantity.quantity() > 0;
    }
}
