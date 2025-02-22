package com.sporty.bookstore.domain.model.pricing.policy;

import com.sporty.bookstore.domain.model.pricing.Price;

public interface DiscountPolicy {

    String name();

    Price deductDiscount(final int bookQuantity, final Price retailPrice, final boolean useLoyaltyPoints);

}
