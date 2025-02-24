package com.sporty.bookstore.domain.model.pricing.policy;

import com.sporty.bookstore.domain.model.pricing.policy.DiscountPolicy.DiscountPolicyName;
import com.sporty.bookstore.infrastructure.DomainService;

import java.util.HashMap;
import java.util.Map;

import static com.sporty.bookstore.domain.model.pricing.policy.DiscountPolicy.DiscountPolicyName.*;

/**
 * A domain service to retrieve {@link DiscountPolicy} by name
 *
 * @author Danilo Ambrosio
 */
@DomainService
public class DiscountPolicies {

    private final Map<DiscountPolicyName, DiscountPolicy> discountPolicyByName = new HashMap<>();

    public DiscountPolicies() {
        discountPolicyByName.put(OLD_EDITION, new OldEditionPolicy());
        discountPolicyByName.put(REGULAR_EDITION, new RegularEditionPolicy());
        discountPolicyByName.put(NEW_RELEASE, new NewReleasePolicy());
    }

    public DiscountPolicy withName(final String name) {
        final DiscountPolicyName discountPolicyName = DiscountPolicyName.valueOf(name);
        if(!discountPolicyByName.containsKey(discountPolicyName)) {
            throw new DiscountPolicyNotFoundException(name);
        }
        return discountPolicyByName.get(discountPolicyName);
    }
}
