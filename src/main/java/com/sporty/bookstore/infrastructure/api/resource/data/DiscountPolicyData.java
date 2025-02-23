package com.sporty.bookstore.infrastructure.api.resource.data;

import com.sporty.bookstore.infrastructure.repository.BookPricingData;

public class DiscountPolicyData {

    public final String bookId;
    public final String policyName;

    public static DiscountPolicyData from(final BookPricingData pricingData) {
        return new DiscountPolicyData(pricingData.bookId, pricingData.discountPolicy);
    }

    public static DiscountPolicyData from(final String bookId, final String policyName) {
        return new DiscountPolicyData(bookId, policyName);
    }

    private DiscountPolicyData(final String bookId,
                               final String policyName) {
        this.bookId = bookId;
        this.policyName = policyName;
    }

}
