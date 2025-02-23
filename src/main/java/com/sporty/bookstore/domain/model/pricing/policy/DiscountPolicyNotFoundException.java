package com.sporty.bookstore.domain.model.pricing.policy;

public class DiscountPolicyNotFoundException extends RuntimeException {

    private final String policyName;

    public DiscountPolicyNotFoundException(String name) {
        this.policyName = name;
    }

}
