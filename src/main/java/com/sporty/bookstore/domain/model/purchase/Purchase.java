package com.sporty.bookstore.domain.model.purchase;

import java.util.List;

/**
 * This class represents a purchase made by a customer.
 * It contains information about the purchase ID, customer, and payments associated with the purchase.
 *
 * @author Danilo Ambrosio
 */
public class Purchase {

    private final PurchaseId purchaseId;
    private final Customer customer;
    private final Payments payments;

    public static Purchase process(final Customer customer,
                                   final Payments payments) {
        return of(PurchaseId.create(), customer, payments);
    }

    public static Purchase of(final PurchaseId purchaseId,
                              final Customer customer,
                              final Payments payments) {
        return new Purchase(purchaseId, customer, payments);
    }

    private Purchase(final PurchaseId purchaseId,
                     final Customer customer,
                     final Payments payments) {
        this.purchaseId = purchaseId;
        this.customer = customer;
        this.payments = payments;
    }

    public String id() {
        return purchaseId.value();
    }

    public String customerId() {
        return customer.userId().value();
    }

    public List<PaymentDetail> payments() {
        return payments.details();
    }

    public double totalPrice() {
        return payments.totalPrice();
    }

}
