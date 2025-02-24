package com.sporty.bookstore.domain.model.purchase;

import com.sporty.bookstore.domain.model.user.UserId;

public record Customer(UserId userId) {

    public static Customer identifiedBy(String userId) {
        return new Customer(UserId.of(userId));
    }

    public String id() {
        return userId.value();
    }
}
