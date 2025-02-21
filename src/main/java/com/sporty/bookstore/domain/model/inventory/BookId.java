package com.sporty.bookstore.domain.model.inventory;

import java.util.UUID;

public record BookId(String value) {

    public static BookId create() {
        return new BookId(UUID.randomUUID().toString());
    }

    public static BookId of(final String value) {
        return new BookId(value);
    }

}
