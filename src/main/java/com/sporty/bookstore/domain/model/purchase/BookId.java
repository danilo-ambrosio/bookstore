package com.sporty.bookstore.domain.model.purchase;

public class BookId {

    private final String value;

    public static BookId of(final String value) {
        return new BookId(value);
    }

    private BookId(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
