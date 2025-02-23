package com.sporty.bookstore.domain.model.pricing.policy;

import com.sporty.bookstore.domain.model.pricing.Discount;
import com.sporty.bookstore.domain.model.pricing.Price;

public class OldEditionPolicy implements DiscountPolicy {

    private final Discount standardDiscount;
    private final Discount bonusDiscount;

    public OldEditionPolicy() {
        this.standardDiscount = Discount.deduct(20);
        this.bonusDiscount = Discount.deduct(25);
    }

    @Override
    public DiscountPolicyName name() {
        return DiscountPolicyName.OLD_EDITION;
    }

    @Override
    public Price deductDiscount(final int bookQuantity, final Price retailPrice, final boolean useLoyaltyPoints) {
        if(useLoyaltyPoints) {
            return Price.of(0);
        }
        if(hasBonusDiscount(bookQuantity)) {
            return retailPrice.off(bonusDiscount);
        }
        return retailPrice.off(standardDiscount);
    }

    private boolean hasBonusDiscount(int bookQuantity) {
        return bookQuantity >= 3;
    }
}
