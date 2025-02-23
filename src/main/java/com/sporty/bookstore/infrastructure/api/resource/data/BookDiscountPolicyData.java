package com.sporty.bookstore.infrastructure.api.resource.data;

import com.sporty.bookstore.infrastructure.repository.BookPricingData;

public class BookDiscountPolicyData {

    public final String bookId;
    public final String policyName;

    public static BookDiscountPolicyData from(final BookPricingData pricingData) {
        return new BookDiscountPolicyData(pricingData.bookId, pricingData.discountPolicy);
    }

    public static BookDiscountPolicyData from(final String bookId, final String policyName) {
        return new BookDiscountPolicyData(bookId, policyName);
    }

    private BookDiscountPolicyData(final String bookId,
                                  final String policyName) {
        this.bookId = bookId;
        this.policyName = policyName;
    }

}
