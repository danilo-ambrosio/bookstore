package com.sporty.bookstore.infrastructure.api.resource.data;

import com.sporty.bookstore.domain.model.pricing.Book;

public class BookPricingData {

    public final String bookId;
    public final double retailPrice;
    public final String discountPolicy;

    public static BookPricingData from(final Book book) {
        return new BookPricingData(book.bookId(), book.retailPrice(), book.discountPolicyName());
    }

    public static BookPricingData from(final double retailPrice, final String discountPolicy) {
        return new BookPricingData("", retailPrice, discountPolicy);
    }

    private BookPricingData(final String bookId, final double retailPrice, String discountPolicy) {
        this.bookId = bookId;
        this.retailPrice = retailPrice;
        this.discountPolicy = discountPolicy;
    }
}
