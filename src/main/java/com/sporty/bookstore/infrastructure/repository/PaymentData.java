package com.sporty.bookstore.infrastructure.repository;

import com.sporty.bookstore.domain.model.purchase.PaymentDetail;

public class PaymentData {

    public final String bookId;
    public final String paymentMethod;
    public final int quantity;
    public final double totalPrice;

    public static PaymentData from(PaymentDetail paymentDetail) {
        return of(paymentDetail.bookId(), paymentDetail.paymentMethod(), paymentDetail.quantity(), paymentDetail.totalPrice());
    }

    public static PaymentData of(final String bookId,
                                 final String paymentMethod,
                                 final int quantity,
                                 final double totalPrice) {
        return new PaymentData(bookId, paymentMethod, quantity, totalPrice);
    }

    private PaymentData(final String bookId,
                        final String paymentMethod,
                        final int quantity,
                        final double totalPrice) {
        this.bookId = bookId;
        this.paymentMethod = paymentMethod;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

}
