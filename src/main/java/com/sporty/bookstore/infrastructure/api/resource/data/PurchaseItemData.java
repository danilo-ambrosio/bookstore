package com.sporty.bookstore.infrastructure.api.resource.data;

import com.sporty.bookstore.domain.model.pricing.Price;
import com.sporty.bookstore.domain.model.purchase.PaymentDetail;
import com.sporty.bookstore.domain.model.purchase.BookId;
import com.sporty.bookstore.domain.model.purchase.PaymentMethod;
import com.sporty.bookstore.domain.model.purchase.Quantity;

public class PurchaseItemData {

    public final String bookId;
    public final String paymentMethod;
    public final int quantity;
    public final double totalPrice;

    public static PurchaseItemData from(PaymentDetail paymentDetail) {
        return from(paymentDetail.bookId(), paymentDetail.paymentMethod(), paymentDetail.quantity(), paymentDetail.totalPrice());
    }

    public static PurchaseItemData from(final String bookId,
                                        final String paymentMethod,
                                        final int quantity,
                                        final double totalPrice) {
        return new PurchaseItemData(bookId, paymentMethod, quantity, totalPrice);
    }

    private PurchaseItemData(final String bookId,
                             final String paymentMethod,
                             final int quantity,
                             final double totalPrice) {
        this.bookId = bookId;
        this.paymentMethod = paymentMethod;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public PaymentDetail toDetail() {
        return PaymentDetail.of(
                BookId.of(this.bookId),
                PaymentMethod.valueOf(this.paymentMethod),
                Quantity.of(quantity),
                Price.of(totalPrice));
    }

}
