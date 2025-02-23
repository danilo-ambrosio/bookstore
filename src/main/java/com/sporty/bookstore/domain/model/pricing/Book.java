package com.sporty.bookstore.domain.model.pricing;

import com.sporty.bookstore.domain.model.pricing.policy.DiscountPolicy;

/**
 * This class represents a book in the bookstore from a financial perspective,
 * meaning it's designed to process pricing and discounting rules. It contains
 * information about the book's ID, retail price, and discount policy.
 *
 * @author Danilo Ambrosio
 */
public class Book {

    private final BookId bookId;
    private Price retailPrice;
    private DiscountPolicy discountPolicy;

    private Book(final BookId bookId,
                 final Price retailPrice,
                 final DiscountPolicy discountPolicy) {
        this.bookId = bookId;
        this.retailPrice = retailPrice;
        this.discountPolicy = discountPolicy;
    }

    public static Book price(final BookId bookId,
                             final Price retailPrice,
                             final DiscountPolicy policy) {
        return new Book(bookId, retailPrice, policy);
    }

    public void reprice(final Price retailPrice) {
        this.retailPrice = retailPrice;
    }

    public void apply(final DiscountPolicy policy) {
        this.discountPolicy = policy;
    }

    public Price deductDiscount(final int quantity, final boolean useLoyaltyPoints) {
        return discountPolicy.deductDiscount(quantity, retailPrice, useLoyaltyPoints);
    }

    public String bookId() {
        return bookId.value();
    }

    public double retailPrice() {
        return retailPrice.value();
    }

    public String discountPolicyName() {
        return discountPolicy.name().toString();
    }

}
