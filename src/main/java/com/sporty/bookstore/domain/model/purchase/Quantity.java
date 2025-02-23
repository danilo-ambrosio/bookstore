package com.sporty.bookstore.domain.model.purchase;

public class Quantity {

    public final int value;

    public static Quantity of(final int value) {
        return new Quantity(value);
    }

    private Quantity(final int value) {
        this.value = value;
    }
}
