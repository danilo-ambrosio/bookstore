package com.sporty.bookstore.domain.model.purchase;

import java.util.UUID;

public record PurchaseId(String value) {

    public static PurchaseId create() {
        return new PurchaseId(UUID.randomUUID().toString());
    }

    public static PurchaseId of(final String value) {
        return new PurchaseId(value);
    }
}
