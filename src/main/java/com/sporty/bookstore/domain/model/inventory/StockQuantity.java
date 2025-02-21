package com.sporty.bookstore.domain.model.inventory;

public record StockQuantity(int quantity) {

    public static StockQuantity of(int quantity) {
        return new StockQuantity(quantity);
    }

}
