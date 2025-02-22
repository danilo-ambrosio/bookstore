package com.sporty.bookstore.infrastructure.api.resource.data;

import com.sporty.bookstore.domain.model.inventory.Book;

import java.util.ArrayList;
import java.util.List;

public class BookInventoryData {

    public final String id;
    public final String isbn;
    public final String title;
    public final Integer stockQuantity;
    public final List<String> authors = new ArrayList<>();
    public final List<String> genres = new ArrayList<>();

    public static BookInventoryData from(final Book book) {
        return new BookInventoryData(book.id(),
                book.isbn(),
                book.title(),
                book.stockQuantity(),
                book.authors(),
                book.genres());
    }

    public BookInventoryData(final String id,
                              final String isbn,
                              final String title,
                              final Integer stockQuantity,
                              final List<String> authors,
                              final List<String> genres) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.stockQuantity = stockQuantity;
        this.authors.addAll(authors);
        this.genres.addAll(genres);
    }
}
