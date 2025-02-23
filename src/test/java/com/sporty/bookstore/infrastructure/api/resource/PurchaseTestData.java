package com.sporty.bookstore.infrastructure.api.resource;

import com.sporty.bookstore.infrastructure.api.resource.data.PurchaseData;
import com.sporty.bookstore.infrastructure.api.resource.data.PurchaseItemData;

import java.util.List;

import static com.sporty.bookstore.domain.model.purchase.PaymentMethod.CREDIT_CARD;
import static java.util.stream.Collectors.toList;

public class PurchaseTestData {

    public static PurchaseData create(final String customerId,
                                      final int sameQuantityForAllBooks,
                                      final double samePriceForAllBooks,
                                      final List<String> bookIds) {
        final List<PurchaseItemData> purchasedBooks =
                bookIds.stream().map(bookId -> PurchaseItemData.from(bookId, CREDIT_CARD.name(), sameQuantityForAllBooks, samePriceForAllBooks)).collect(toList());

        return PurchaseData.from(customerId, purchasedBooks);
    }
}
