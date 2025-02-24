package com.sporty.bookstore.domain.model.purchase;

import com.sporty.bookstore.domain.model.event.DomainEvent;
import com.sporty.bookstore.domain.model.event.DomainEventType;

import java.util.List;

public class PurchaseProcessed implements DomainEvent {

    public final String customerId;
    public final Payments payments;

    public PurchaseProcessed(final Customer customer,
                             final Payments payments) {
        this.customerId = customer.id();
        this.payments = payments;
    }

    @Override
    public DomainEventType type() {
        return DomainEventType.PURCHASE_WAS_PROCESSED;
    }

    public List<PaymentDetail> paymentDetails() {
        return payments.details();
    }

    public int booksCoveredWithLoyaltyPoints() {
        return payments.quantityCoveredWithLoyaltyPoints();
    }

    public int booksPayed() {
        return payments.quantityPayed();
    }
}
