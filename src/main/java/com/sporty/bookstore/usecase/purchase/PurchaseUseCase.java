package com.sporty.bookstore.usecase.purchase;

import com.sporty.bookstore.domain.model.inventory.BookAvailability;
import com.sporty.bookstore.domain.model.inventory.BookId;
import com.sporty.bookstore.domain.model.inventory.StockQuantity;
import com.sporty.bookstore.domain.model.pricing.Price;
import com.sporty.bookstore.domain.model.pricing.PriceReviewer;
import com.sporty.bookstore.domain.model.purchase.PaymentDetail;
import com.sporty.bookstore.domain.model.purchase.Payments;
import com.sporty.bookstore.domain.model.purchase.Customer;
import com.sporty.bookstore.domain.model.purchase.Purchase;
import com.sporty.bookstore.infrastructure.UseCase;
import com.sporty.bookstore.infrastructure.repository.PurchaseData;
import com.sporty.bookstore.infrastructure.repository.PurchaseRepository;

import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@UseCase
public class PurchaseUseCase {

    private final BookAvailability bookAvailability;
    private final PurchaseRepository purchaseRepository;
    private final PriceReviewer priceReview;

    public PurchaseUseCase(final BookAvailability bookAvailability,
                           final PurchaseRepository purchaseRepository,
                           final PriceReviewer priceReview) {
        this.bookAvailability = bookAvailability;
        this.purchaseRepository = purchaseRepository;
        this.priceReview = priceReview;
    }

    public Purchase process(final Customer customer,
                            final List<PaymentDetail> purchasedPaymentDetails) {
        checkBookAvailability(purchasedPaymentDetails);
        final Payments payments = adjustPrice(purchasedPaymentDetails);
        final Purchase purchase = Purchase.process(customer, payments);
        purchaseRepository.save(PurchaseData.from(purchase));
        return purchase;
    }

    private Payments adjustPrice(final List<PaymentDetail> purchasedPaymentDetails) {
        return purchasedPaymentDetails.stream().map(purchasedBook -> {
                final Price price = priceReview.review(purchasedBook);
                return purchasedBook.adjustPrice(price);
            }).collect(collectingAndThen(toList(), Payments::of));
    }

    private void checkBookAvailability(final List<PaymentDetail> purchasedPaymentDetails) {
        purchasedPaymentDetails.forEach(purchasedBook -> {
            final BookId bookId = BookId.of(purchasedBook.bookId());
            final StockQuantity stockQuantity = StockQuantity.of(purchasedBook.quantity());
            bookAvailability.check(bookId, stockQuantity);
        });
    }
}

