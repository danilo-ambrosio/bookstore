package com.sporty.bookstore.domain.model.pricing;

public class Wholesale {

    public final BookId bookId;
    public final int bookQuantity;
    public final boolean useLoyaltyPoints;

    public static Wholesale of(BookId bookId, int bookQuantity, boolean useLoyaltyPoints) {
        return new Wholesale(bookId, bookQuantity, useLoyaltyPoints);
    }

    private Wholesale(BookId bookId, int bookQuantity, boolean useLoyaltyPoints) {
        this.bookId = bookId;
        this.bookQuantity = bookQuantity;
        this.useLoyaltyPoints = useLoyaltyPoints;
    }

}
