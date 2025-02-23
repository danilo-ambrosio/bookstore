package com.sporty.bookstore.infrastructure.repository;

import com.sporty.bookstore.domain.model.purchase.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "purchase")
public class PurchaseData {

    @Id
    public final String purchaseId;
    public final String customerId;
    public final List<PaymentData> payments = new ArrayList<>();

    public static PurchaseData from(final Purchase purchase) {
        final List<PaymentData> items =
                purchase.payments().stream().map(PaymentData::from).toList();

        return new PurchaseData(purchase.id(), purchase.customerId(), items);
    }

    @PersistenceCreator
    public PurchaseData(final String purchaseId,
                        final String customerId,
                        final List<PaymentData> payments) {
        this.purchaseId = purchaseId;
        this.customerId = customerId;
        this.payments.addAll(payments);
    }

}
