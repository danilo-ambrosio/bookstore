package com.sporty.bookstore.domain.model.purchase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Payments {

    private final List<PaymentDetail> details = new ArrayList<>();

    public static Payments of(final PaymentDetail paymentDetail) {
        return of(List.of(paymentDetail));
    }

    public static Payments of(final List<PaymentDetail> paymentDetails) {
        return new Payments(paymentDetails);
    }

    private Payments(final List<PaymentDetail> details) {
        this.details.addAll(details);
    }

    public List<PaymentDetail> details() {
        return Collections.unmodifiableList(details);
    }

    public double totalPrice() {
        return details.stream()
                .mapToDouble(PaymentDetail::totalPrice)
                .reduce(0, Double::sum);
    }
}
