package com.sporty.bookstore.infrastructure.repository;

import com.sporty.bookstore.domain.model.inventory.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "book_inventory")
public class BookInventoryData {

    @Id
    public final String id;
    public final String isbn;
    public final String title;
    public final Integer stockQuantity;
    public final List<String> authors;
    public final List<String> genres;

    public static BookInventoryData from(Book book) {
        return new BookInventoryData(book.id(),
                book.isbn(),
                book.title(),
                book.stockQuantity(),
                book.authors(),
                book.genres());
    }

    @PersistenceCreator
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
        this.authors = authors;
        this.genres = genres;
    }

    public Book toBook() {
        return Book.of(BookId.of(id),
                ISBN.from(isbn),
                Title.of(title),
                StockQuantity.of(stockQuantity),
                Author.names(authors),
                Genre.of(genres));
    }

}
