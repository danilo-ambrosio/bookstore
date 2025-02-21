package com.sporty.bookstore.domain.model.inventory;

public record Title(String title) {

    public static Title of(String title) {
        return new Title(title);
    }

}
