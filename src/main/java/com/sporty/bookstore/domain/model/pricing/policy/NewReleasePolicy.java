package com.sporty.bookstore.domain.model.pricing.policy;

import com.sporty.bookstore.domain.model.pricing.Price;

public class NewReleasePolicy implements DiscountPolicy {

    @Override
    public DiscountPolicyName name() {
        return DiscountPolicyName.NEW_RELEASE;
    }

    @Override
    public Price deductDiscount(final int bookQuantity, final Price retailPrice, final boolean useLoyaltyPoints) {
        if(useLoyaltyPoints) {
            throw new PolicyNotCoveredByLoyaltyProgramException();
        }
        return retailPrice;
    }

    @Override
    public boolean canUseLoyaltyPoints() {
        return false;
    }

    @Override
    public String toString() {
        return name().toString();
    }
}
