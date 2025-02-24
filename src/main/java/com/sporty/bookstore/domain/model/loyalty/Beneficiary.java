package com.sporty.bookstore.domain.model.loyalty;

import com.sporty.bookstore.domain.model.user.UserId;

public record Beneficiary(UserId userId) {

    public static Beneficiary identifiedBy(String userId) {
        return new Beneficiary(UserId.of(userId));
    }

    public String id() {
        return userId.value();
    }
}
