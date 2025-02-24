package com.sporty.bookstore.infrastructure.repository;

import com.sporty.bookstore.domain.model.pricing.policy.DiscountPolicies;
import com.sporty.bookstore.domain.model.pricing.policy.DiscountPolicy;
import org.springframework.data.convert.PropertyValueConverter;
import org.springframework.data.mongodb.core.convert.MongoConversionContext;

/**
 * A domain service to retrieve {@link DiscountPolicy} by name
 *
 * @author Danilo Ambrosio
 */
public class DiscountPolicyConverter implements PropertyValueConverter<DiscountPolicy, String, MongoConversionContext> {

    private final DiscountPolicies discountPolicies = new DiscountPolicies();

    @Override
    public DiscountPolicy read(String policyName, MongoConversionContext context) {
        return discountPolicies.withName(policyName);
    }

    @Override
    public String write(final DiscountPolicy policy, MongoConversionContext context) {
        return policy.name().toString();
    }
}
