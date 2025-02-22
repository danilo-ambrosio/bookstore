package com.sporty.bookstore.domain.model.inventory;

import java.util.List;

public record Genre(String name) {

    public static List<Genre> of(String ...names) {
        return of(List.of(names));
    }

    public static List<Genre> of(List<String> names) {
        return names.stream().map(Genre::new).toList();
    }

}
