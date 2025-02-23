package com.sporty.bookstore.infrastructure.api.resource.data;

import com.sporty.bookstore.domain.model.pricing.Book;

public class PricingData {

    public final String bookId;
    public final double retailPrice;
    public final String discountPolicy;

    public static PricingData from(final Book book) {
        return new PricingData(book.bookId(), book.retailPrice(), book.discountPolicyName());
    }

    public static PricingData from(final double retailPrice, final String discountPolicy) {
        return new PricingData("", retailPrice, discountPolicy);
    }

    private PricingData(final String bookId, final double retailPrice, String discountPolicy) {
        this.bookId = bookId;
        this.retailPrice = retailPrice;
        this.discountPolicy = discountPolicy;
    }
}
