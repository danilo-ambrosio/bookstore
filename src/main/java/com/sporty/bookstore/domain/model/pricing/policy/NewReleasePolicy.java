package com.sporty.bookstore.domain.model.pricing.policy;

import com.sporty.bookstore.domain.model.pricing.Price;

public class NewReleasePolicy implements DiscountPolicy {

    @Override
    public String name() {
        return "NewRelease";
    }

    @Override
    public Price deductDiscount(int bookQuantity, Price retailPrice, boolean useLoyaltyPoints) {
        if(useLoyaltyPoints) {
            throw new PolicyNotCoveredByLoyaltyProgramException();
        }
        return retailPrice;
    }
}
