package com.sporty.bookstore.domain.model.pricing;

import com.sporty.bookstore.domain.model.bundle.Bundle;

public class BookBundle implements Bundle {

    public final BookId bookId;
    public final int bookQuantity;
    public final boolean useLoyaltyPoints;

    public static BookBundle of(String bookId, int bookQuantity, boolean useLoyaltyPoints) {
        return of(BookId.of(bookId), bookQuantity, useLoyaltyPoints);
    }

    public static BookBundle of(BookId bookId, int bookQuantity, boolean useLoyaltyPoints) {
        return new BookBundle(bookId, bookQuantity, useLoyaltyPoints);
    }

    private BookBundle(BookId bookId, int bookQuantity, boolean useLoyaltyPoints) {
        this.bookId = bookId;
        this.bookQuantity = bookQuantity;
        this.useLoyaltyPoints = useLoyaltyPoints;
    }

    @Override
    public String bookId() {
        return bookId.value();
    }

    @Override
    public int quantity() {
        return bookQuantity;
    }

    @Override
    public boolean useLoyaltyPoints() {
        return useLoyaltyPoints;
    }
}
