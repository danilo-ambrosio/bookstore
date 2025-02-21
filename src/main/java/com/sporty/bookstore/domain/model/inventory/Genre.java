package com.sporty.bookstore.domain.model.inventory;

import java.util.List;

public record Genre(String name) {

    public static List<Genre> of(String ...genre) {
        return List.of(genre).stream().map(Genre::new).toList();
    }

}
