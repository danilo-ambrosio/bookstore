package com.sporty.bookstore.domain.model.pricing.policy;

import com.sporty.bookstore.domain.model.pricing.Discount;
import com.sporty.bookstore.domain.model.pricing.Price;

public class RegularEditionPolicy implements DiscountPolicy {

    private final Discount discount;

    public RegularEditionPolicy() {
        this.discount = Discount.deduct(10);
    }

    @Override
    public DiscountPolicyName name() {
        return DiscountPolicyName.REGULAR_EDITION;
    }

    @Override
    public Price deductDiscount(final int bookQuantity, final Price retailPrice, final boolean useLoyaltyPoints) {
        if(useLoyaltyPoints) {
            return Price.of(0);
        }
        if(hasDiscount(bookQuantity)) {
            return retailPrice.off(discount);
        }
        return retailPrice;
    }

    private boolean hasDiscount(int bookQuantity) {
        return bookQuantity >= 3;
    }
}
