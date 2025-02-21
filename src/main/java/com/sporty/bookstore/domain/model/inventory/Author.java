package com.sporty.bookstore.domain.model.inventory;

import java.util.List;

public record Author(String name) {

    public static Author name(String name) {
        return new Author(name);
    }

    public static List<Author> names(String ...names) {
        return List.of(names).stream().map(Author::name).toList();
    }
}
