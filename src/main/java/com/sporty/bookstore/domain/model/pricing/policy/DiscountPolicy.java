package com.sporty.bookstore.domain.model.pricing.policy;

import com.sporty.bookstore.domain.model.pricing.Price;

public interface DiscountPolicy {

    DiscountPolicyName name();

    Price deductDiscount(final int bookQuantity, final Price retailPrice, final boolean useLoyaltyPoints);

    enum DiscountPolicyName {
        NEW_RELEASE,
        OLD_EDITION,
        REGULAR_EDITION
    }
}
