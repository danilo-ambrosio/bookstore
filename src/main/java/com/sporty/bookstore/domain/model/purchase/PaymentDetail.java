package com.sporty.bookstore.domain.model.purchase;

import com.sporty.bookstore.domain.model.bundle.Bundle;
import com.sporty.bookstore.domain.model.pricing.Price;

import static com.sporty.bookstore.domain.model.purchase.PaymentMethod.LOYALTY_POINTS;

public class PaymentDetail implements Bundle {

    private final BookId bookId;
    private final PaymentMethod paymentMethod;
    private final Quantity quantity;
    private final Price totalPrice;

    public static PaymentDetail of(final BookId bookId,
                                   final PaymentMethod paymentMethod,
                                   final Quantity quantity,
                                   final Price totalPrice) {
        return new PaymentDetail(bookId, paymentMethod, quantity, totalPrice);
    }

    private PaymentDetail(final BookId bookId,
                          final PaymentMethod paymentMethod,
                          final Quantity quantity,
                          final Price totalPrice) {
        this.bookId = bookId;
        this.paymentMethod = paymentMethod;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public PaymentDetail adjustPrice(final Price newPrice) {
        return new PaymentDetail(bookId, paymentMethod, quantity, newPrice);
    }

    @Override
    public String bookId() {
        return bookId.value();
    }

    @Override
    public int quantity() {
        return quantity.value;
    }

    public String paymentMethod() {
        return paymentMethod.name();
    }

    public double totalPrice() {
        return totalPrice.value();
    }

    public boolean isPayedWithLoyaltyPoints() {
        return paymentMethod.equals(LOYALTY_POINTS);
    }

    @Override
    public boolean useLoyaltyPoints() {
        return isPayedWithLoyaltyPoints();
    }

}
