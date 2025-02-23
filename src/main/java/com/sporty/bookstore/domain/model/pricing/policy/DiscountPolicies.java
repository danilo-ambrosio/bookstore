package com.sporty.bookstore.domain.model.pricing.policy;

import com.sporty.bookstore.domain.model.pricing.policy.DiscountPolicy.DiscountPolicyName;
import com.sporty.bookstore.infrastructure.DomainService;

import java.util.HashMap;
import java.util.Map;

/**
 * A domain service to retrieve {@link DiscountPolicy} by name
 *
 * @author Danilo Ambrosio
 */
@DomainService
public class DiscountPolicies {

    private final Map<DiscountPolicyName, DiscountPolicy> discountPolicyByName = new HashMap<>();

    public DiscountPolicies() {
        discountPolicyByName.put(DiscountPolicyName.OLD_EDITION, new OldEditionPolicy());
        discountPolicyByName.put(DiscountPolicyName.REGULAR_EDITION, new RegularEditionPolicy());
        discountPolicyByName.put(DiscountPolicyName.NEW_RELEASE, new NewReleasePolicy());
    }

    public DiscountPolicy withName(final DiscountPolicyName name) {
        if(!discountPolicyByName.containsKey(name)) {
            throw new DiscountPolicyNotFoundException(name.toString());
        }
        return discountPolicyByName.get(name);
    }
}
