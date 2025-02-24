package com.sporty.bookstore.domain.model.pricing.policy;

import com.sporty.bookstore.domain.model.pricing.Price;

/**
 * This interface defines the contract for discount policies used in the bookstore.
 * It provides methods for calculating discounts, checking if loyalty points can be used,
 * and determining the discount policy name.
 *
 * @author Danilo Ambrosio
 */
public interface DiscountPolicy {

    DiscountPolicyName name();

    Price deductDiscount(final int bookQuantity, final Price retailPrice, final boolean useLoyaltyPoints);

    boolean canUseLoyaltyPoints();

    enum DiscountPolicyName {
        NEW_RELEASE,
        OLD_EDITION,
        REGULAR_EDITION
    }
}
