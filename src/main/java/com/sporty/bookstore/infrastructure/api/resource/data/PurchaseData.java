package com.sporty.bookstore.infrastructure.api.resource.data;

import com.sporty.bookstore.domain.model.purchase.Purchase;

import java.util.ArrayList;
import java.util.List;

public class PurchaseData {

    public final String purchaseId;
    public final String customerId;
    public final List<PurchaseItemData> items = new ArrayList<>();

    public static PurchaseData from(final Purchase purchase) {
        final List<PurchaseItemData> items =
                purchase.payments().stream().map(PurchaseItemData::from).toList();

        return new PurchaseData(purchase.id(), purchase.customerId(), items);
    }

    public static PurchaseData from(final String customerId,
                                    final List<PurchaseItemData> items) {
        return new PurchaseData("", customerId, items);
    }

    private PurchaseData(final String purchaseId,
                         final String customerId,
                         final List<PurchaseItemData> items) {
        this.purchaseId = purchaseId;
        this.customerId = customerId;
        this.items.addAll(items);
    }
}
