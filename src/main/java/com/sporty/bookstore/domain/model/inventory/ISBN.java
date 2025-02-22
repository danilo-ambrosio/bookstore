package com.sporty.bookstore.domain.model.inventory;

/**
 * International Standard Book Number
 */
public record ISBN(String from) {

    public static ISBN from(String code) {
        return new ISBN(code);
    }

}
