package com.sporty.bookstore.domain.model.inventory;

/**
 * International Standard Book Number
 */
public record ISBN(String code) {

    public static ISBN code(String code) {
        return new ISBN(code);
    }

}
